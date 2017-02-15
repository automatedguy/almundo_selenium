package com.almundo.browser.automation.tests.Trips;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.VueloHotelDataTrip;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPage.ConfirmationPage;
import com.almundo.browser.automation.pages.ResultsPage.VueloHotelDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.VueloHotelResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class SucursalesFlowTest extends TestBaseSetup {

    private VueloHotelResultsPage vueloHotelResultsPage = null;
    private VueloHotelDetailPage vueloHotelDetailPage = null;
    private CheckOutPage checkOutPage = null;
    private ConfirmationPage confirmationPage = null;

    private VueloHotelDataTrip vueloHotelDataTrip = null;
    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initDataLists() {
        dataManagement.getVueloHotelDataTripList();
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
    public void vueloHotelDomReservationFlow() {
        logTestTitle("Vuelo+Hotel Flow - Domestic - 20 days - 2 Adults/1 Child - 1 Room - " + countryPar );

        PageUtils.waitElementForVisibility(driver, basePage.vueloHotelIcon, 10, "Vuelo+Hotel icon");
        basePage.vueloHotelIcon.click();

        dataManagement.getVueloHotelDataTripItinerary("domestic02_20days_2adults_1childs_1room");

        vueloHotelDataTrip = basePage.vueloHotelDataTrip();

        vueloHotelDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        vueloHotelDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);

        vueloHotelDataTrip.selectDateFromCalendar(vueloHotelDataTrip.departureCalendar, dataManagement.startDate);
        vueloHotelDataTrip.selectDateFromCalendar(vueloHotelDataTrip.arrivalCalendar, dataManagement.endDate);

        vueloHotelDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);

        vueloHotelResultsPage = basePage.vueloHotelDataTrip().clickBuscarBtn();

        Assert.assertTrue(vueloHotelResultsPage.vacancy());

        vueloHotelResultsPage.clickElegirBtn(0);
        vueloHotelDetailPage = vueloHotelResultsPage.clickContinuarBtn();
        vueloHotelDetailPage.clickVerHabitacionBtn();

        checkOutPage = vueloHotelDetailPage.clickComprarBtn(0);

        dataManagement.getPassengerData("adult_female_foreign");
        dataManagement.getPassengerData("adult_female_foreign");
        dataManagement.getPassengerData("child_female_native");

        checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                                          dataManagement.getPaymentData("deposit"),
                                          dataManagement.getBillingData("local_Billing_sucursales"),
                                          dataManagement.getContactData("contact_cell_phone"),
                                          "VueloHotelCheckOutPageDomesticSucursal");

/*        confirmationPage = checkOutPage.clickComprarBtn();
        Assert.assertTrue(confirmationPage.confirmationOk());*/
    }
}
