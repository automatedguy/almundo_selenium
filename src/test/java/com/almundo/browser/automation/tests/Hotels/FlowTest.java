package com.almundo.browser.automation.tests.Hotels;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.HotelsDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPage.ConfirmationPage;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ConfirmationPageV3;
import com.almundo.browser.automation.pages.ResultsPage.HotelsDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.HotelsResultsPage;
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

    private HotelsResultsPage hotelsResultsPage = null;
    private HotelsDetailPage hotelsDetailPage = null;

    private HotelsDataTrip hotelsDataTrip = null;
    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initDataLists() {
        dataManagement.getHotelsData();
    }

    @BeforeMethod
    private void closeLoginPopUp(){
        LoginPopUp loginPopUp = initLoginPopUp();
        loginPopUp.clickCloseLoginBtn();
        basePage.clickHotelsBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void int_Booking_Flow() {
        logTestTitle("Hotel Flow - Int - 10 days - 2 Adults/2 Childs - 1 Room - " + countryPar );

        dataManagement.getHotelsDataTripItinerary("miami_10days_2adults_2childs_1room");

        hotelsDataTrip = basePage.hotelsDataTrip();
        hotelsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.startDate);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);
        hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
        hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

        Assert.assertTrue(hotelsResultsPage.vacancy());
        hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

        PageUtils.switchToNewTab(driver);
        hotelsDetailPage.clickVerHabitacionesBtn();

        dataManagement.getPassengerData("adult_female_native");
        dataManagement.getPassengerData("adult_female_native");
        dataManagement.getPassengerData("child_female_native");
        dataManagement.getPassengerData("child_female_native");

        if(countryPar.equals("ARGENTINA")) {
            CheckOutPageV3 checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);
            replaceChkOutV2Url();
            checkOutPageV3.populateCheckOutPage(dataManagement.passengerJsonList,
                                                dataManagement.getPaymentData("1_visa_visa"),
                                                dataManagement.getBillingData("local_Billing"),
                                                dataManagement.getContactData("contact_cell_phone"),
                                                "HotelsCheckOutPageInternationalV3");

            ConfirmationPageV3 confirmationPage = checkOutPageV3.clickComprarBtn();
            Assert.assertTrue(confirmationPage.confirmationOk());

        } else {
            CheckOutPage checkOutPage = hotelsDetailPage.clickReservarAhoraBtn(FIRST_OPTION);
            checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                                              dataManagement.getPaymentData("1_visa_visa"),
                                              dataManagement.getBillingData("local_Billing"),
                                              dataManagement.getContactData("contact_cell_phone"),
                                              "HotelsCheckOutPageInternational");

            ConfirmationPage confirmationPage = checkOutPage.clickComprarBtn();
            Assert.assertTrue(confirmationPage.confirmationOk());
        }


    }

    @Test
    public void dom_Booking_Flow() {
        logTestTitle("Hotel Flow - Dom - 15 days - 2 Adults - 1 Room - " + countryPar );

        dataManagement.getHotelsDataTripItinerary("domestic01_15days_2adults_1room");

        hotelsDataTrip = basePage.hotelsDataTrip();
        hotelsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.startDate);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);
        hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
        hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

        Assert.assertTrue(hotelsResultsPage.vacancy());
        hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

        PageUtils.switchToNewTab(driver);
        hotelsDetailPage.clickVerHabitacionesBtn();

        dataManagement.getPassengerData("adult_female_native");
        dataManagement.getPassengerData("adult_female_native");

        if(countryPar.equals("ARGENTINA")) {
            CheckOutPageV3 checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);
            replaceChkOutV2Url();
            checkOutPageV3.populateCheckOutPage(dataManagement.passengerJsonList,
                                                dataManagement.getPaymentData("1_amex_amex"),
                                                dataManagement.getBillingData("local_Billing"),
                                                dataManagement.getContactData("contact_phone"),
                                                "HotelsCheckOutPageDomesticV3");
        } else {
            CheckOutPage checkOutPage = hotelsDetailPage.clickReservarAhoraBtn(FIRST_OPTION);
            checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                                              dataManagement.getPaymentData("1_amex_amex"),
                                              dataManagement.getBillingData("local_Billing"),
                                              dataManagement.getContactData("contact_phone"),
                                              "HotelsCheckOutPageDomestic");


        }


    }
}
