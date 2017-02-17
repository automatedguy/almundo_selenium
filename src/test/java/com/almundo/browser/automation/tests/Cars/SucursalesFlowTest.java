package com.almundo.browser.automation.tests.Cars;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.CarsDataTrip;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPage.ConfirmationPage;
import com.almundo.browser.automation.pages.ResultsPage.CarsResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 20/12/16.
 */
public class SucursalesFlowTest extends TestBaseSetup {

    private CarsResultsPage carsResultsPage = null;
    private CheckOutPage checkOutPage = null;
    private ConfirmationPage confirmationPage = null;

    private CarsDataTrip carsDataTrip = null;
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
    public void suc_Dom_Booking_Flow() {
        logTestTitle("Sucursales Autos Flow - Domestic - 10 days - " + countryPar );
        PageUtils.waitElementForVisibility(driver, basePage.carsIcon, 10, "Autos icon");
        basePage.carsIcon.click();
        dataManagement.getAutosDataTripItinerary("capital_10days_entre_21_24");
        carsDataTrip = basePage.carsDataTrip();
        carsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        carsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        carsDataTrip.selectDateFromCalendar(carsDataTrip.pickUpDateCalendar, dataManagement.startDate);
        carsDataTrip.selectDateFromCalendar(carsDataTrip.dropOffDateCalendar, dataManagement.endDate);
        carsDataTrip.selectPickUpTime(dataManagement.pickUpTime);
        carsDataTrip.selectDropOffTime(dataManagement.dropOffTime);
        carsDataTrip.selectAgeRange(dataManagement.ageRange);
        carsResultsPage = carsDataTrip.clickBuscarBtn();
        checkOutPage = carsResultsPage.clickReservarAhoraBtn();
        dataManagement.getPassengerData("adult_male_native");
        checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                dataManagement.getPaymentData("deposit"),
                dataManagement.getBillingData("local_Billing_sucursales"),
                dataManagement.getContactData("contact_cell_phone"),
                "CarsCheckOutPageSucursales");
    }
}