package com.almundo.browser.automation.tests.Cars;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.AutosDataTrip;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPage.ConfirmationPage;
import com.almundo.browser.automation.pages.ResultsPage.AutosResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 20/12/16.
 */
public class SucursalesFlowTest extends TestBaseSetup {

    private AutosResultsPage autosResultsPage = null;
    private CheckOutPage checkOutPage = null;
    private ConfirmationPage confirmationPage = null;

    private AutosDataTrip autosDataTrip = null;
    private DataManagement dataManagement = new DataManagement();

    //TODO: overload populateCheckOutPage
    private int carDrivers = 1;

    @BeforeClass
    private void initDataTripList() {
        dataManagement.getAutosDataTripList();
        dataManagement.getPassengersList();
        dataManagement.getPaymentList();
        dataManagement.getBillingList();
        dataManagement.getContactList();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void autosDomReservationFlow() {
        logTestTitle("Autos Flow - Domestic - 10 days - " + countryPar );

        PageUtils.waitElementForVisibility(driver, basePage.autosIcon, 10, "Autos icon");
        basePage.autosIcon.click();

        dataManagement.getAutosDataTripItinerary("capital_10days_entre_21_24");

        autosDataTrip = basePage.autosDataTrip();

        autosDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        autosDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);

        autosDataTrip.selectDateFromCalendar(autosDataTrip.pickUpDateCalendar, dataManagement.startDate);
        autosDataTrip.selectDateFromCalendar(autosDataTrip.dropOffDateCalendar, dataManagement.endDate);

        autosDataTrip.selectPickUpTime(dataManagement.pickUpTime);
        autosDataTrip.selectDropOffTime(dataManagement.dropOffTime);

        autosDataTrip.selectAgeRange(dataManagement.ageRange);

        autosResultsPage = autosDataTrip.clickBuscarBtn();

        checkOutPage = autosResultsPage.clickReservarAhoraBtn();

        dataManagement.getPassengerData("adult_male_native");

        checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                dataManagement.getPaymentData("deposit"),
                dataManagement.getBillingData("local_Billing_sucursales"),
                dataManagement.getContactData("contact_cell_phone"),
                "AutosCheckOutPageSucursales");
    }
}