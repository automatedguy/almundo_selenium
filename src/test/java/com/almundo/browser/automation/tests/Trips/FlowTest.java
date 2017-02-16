package com.almundo.browser.automation.tests.Trips;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.BasePage.VueloHotelDataTrip;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPage.ConfirmationPage;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
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

public class FlowTest extends TestBaseSetup {

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

    @BeforeMethod
    private void closeLoginPopUp(){
        LoginPopUp loginPopUp = initLoginPopUp();
        loginPopUp.clickCloseLoginBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void int_Booking_Flow() {
        logTestTitle("Trips Flow - Int - 10 days - 2 Adults/2 Childs - 1 Room - " + countryPar );

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

        if(countryPar.equals("ARGENTINA")) {
            CheckOutPageV3 checkOutPageV3 = initCheckOutPageV3();
            replaceUrl();

            checkOutPageV3.populateCheckOutPage(dataManagement.passengerJsonList,
                    dataManagement.getPaymentData("1_amex_amex"),
                    dataManagement.getBillingData("local_Billing"),
                    dataManagement.getContactData("contact_cell_phone"),
                    "VueloHotelCheckOutPageInternational");
        } else {
            checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                    dataManagement.getPaymentData("1_amex_amex"),
                    dataManagement.getBillingData("local_Billing"),
                    dataManagement.getContactData("contact_cell_phone"),
                    "VueloHotelCheckOutPageInternational");
        }
    }

    @Test
    public void dom_Booking_Flow() {
        logTestTitle("Trips Flow - Domestic - 20 days - 2 Adults/1 Child - 1 Room - " + countryPar );

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

        if(countryPar.equals("ARGENTINA")) {
            CheckOutPageV3 checkOutPageV3 = initCheckOutPageV3();
            replaceUrl();

            checkOutPageV3.populateCheckOutPage(dataManagement.passengerJsonList,
                                              dataManagement.getPaymentData("1_amex_amex"),
                                              dataManagement.getBillingData("local_Billing"),
                                              dataManagement.getContactData("contact_cell_phone"),
                                              "VueloHotelCheckOutPageDomestic");
        } else {
            checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                                              dataManagement.getPaymentData("1_amex_amex"),
                                              dataManagement.getBillingData("local_Billing"),
                                              dataManagement.getContactData("contact_cell_phone"),
                                              "VueloHotelCheckOutPageDomestic");
        }
    }
}
