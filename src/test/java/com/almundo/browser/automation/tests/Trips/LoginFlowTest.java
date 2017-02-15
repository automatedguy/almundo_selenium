package com.almundo.browser.automation.tests.Trips;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.BasePage.VueloHotelDataTrip;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.ResultsPage.VueloHotelDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.VueloHotelResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class LoginFlowTest extends TestBaseSetup {

    private VueloHotelResultsPage vueloHotelResultsPage = null;
    private VueloHotelDetailPage vueloHotelDetailPage = null;
    private CheckOutPage checkOutPage = null;

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

    @BeforeMethod
    private void doLogin(){
        LoginPopUp loginPopUp = initLoginPopUp();
        loginPopUp.setLoginEmailTxt("automationthings@gmail.com");
        loginPopUp.setLoginPasswordTxt("gabi1981ce");
        basePage = loginPopUp.clickIngresarBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void vueloHotelIntLoginReservationFlow() {
        logTestTitle("Vuelo+Hotel Flow - International - 10 days - 2 Adults/2 Childs - 1 Room - " + countryPar );

        PageUtils.waitElementForVisibility(driver, basePage.vueloHotelIcon, 10, "Vuelo+Hotel icon");
        basePage.vueloHotelIcon.click();

        dataManagement.getVueloHotelDataTripItinerary("miami_10days_2adults_2childs_1room");

        vueloHotelDataTrip = basePage.vueloHotelDataTrip();

        vueloHotelDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        vueloHotelDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);

        vueloHotelDataTrip.selectDateFromCalendar(vueloHotelDataTrip.departureCalendar, dataManagement.startDate);
        vueloHotelDataTrip.selectDateFromCalendar(vueloHotelDataTrip.arrivalCalendar, dataManagement.endDate);

        vueloHotelDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);

        vueloHotelResultsPage = vueloHotelDataTrip.clickBuscarBtn();

        Assert.assertTrue(vueloHotelResultsPage.vacancy());

        vueloHotelResultsPage.clickElegirBtn(0);
        vueloHotelDetailPage = vueloHotelResultsPage.clickContinuarBtn();
        vueloHotelDetailPage.clickVerHabitacionBtn();

        checkOutPage = vueloHotelDetailPage.clickComprarBtn(0);

        dataManagement.getPassengerData("adult_male_native");
        dataManagement.getPassengerData("adult_male_native");
        dataManagement.getPassengerData("child_male_native");
        dataManagement.getPassengerData("child_male_native");

        checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                                          dataManagement.getPaymentData("1_amex_amex"),
                                          dataManagement.getBillingData("local_Billing"),
                                          dataManagement.getContactData("contact_cell_phone"),
                                          "VueloHotelCheckOutPageInternational");
    }

    @Test
    public void vueloHotelDomLoginReservationFlow() {
        logTestTitle("Vuelo+Hotel Flow - Domestic - 20 days - 2 Adults/1 Child - 1 Room - " + countryPar );

        PageUtils.waitElementForVisibility(driver, basePage.vueloHotelIcon, 10, "Vuelo+Hotel icon");
        basePage.vueloHotelIcon.click();

        dataManagement.getVueloHotelDataTripItinerary("domestic01_15days_2adults_1childs_1room");

        vueloHotelDataTrip = basePage.vueloHotelDataTrip();

        vueloHotelDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        vueloHotelDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);

        vueloHotelDataTrip.selectDateFromCalendar(vueloHotelDataTrip.departureCalendar, dataManagement.startDate);
        vueloHotelDataTrip.selectDateFromCalendar(vueloHotelDataTrip.arrivalCalendar, dataManagement.endDate);

        vueloHotelDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);

        vueloHotelResultsPage = vueloHotelDataTrip.clickBuscarBtn();

        Assert.assertTrue(vueloHotelResultsPage.vacancy());

        vueloHotelResultsPage.clickElegirBtn(0);
        vueloHotelDetailPage = vueloHotelResultsPage.clickContinuarBtn();
        vueloHotelDetailPage.clickVerHabitacionBtn();

        checkOutPage = vueloHotelDetailPage.clickComprarBtn(0);

        dataManagement.getPassengerData("adult_female_foreign");
        dataManagement.getPassengerData("adult_female_foreign");
        dataManagement.getPassengerData("child_female_native");

        checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                                          dataManagement.getPaymentData("1_amex_amex"),
                                          dataManagement.getBillingData("local_Billing"),
                                          dataManagement.getContactData("contact_cell_phone"),
                                          "VueloHotelCheckOutPageDomestic");
    }
}
