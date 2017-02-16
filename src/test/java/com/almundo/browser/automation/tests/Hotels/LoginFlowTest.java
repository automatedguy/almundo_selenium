package com.almundo.browser.automation.tests.Hotels;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.HotelsDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
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

public class LoginFlowTest extends TestBaseSetup {

    private HotelsResultsPage hotelsResultsPage = null;
    private HotelsDetailPage hotelsDetailPage = null;
    private CheckOutPage checkOutPage = null;

    private HotelsDataTrip hotelsDataTrip = null;
    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initDataLists() {
        dataManagement.getHotelesDataTripList();
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
    public void hotelIntLoginReservationFlow() {
        logTestTitle("Hotel Flow - International - 10 days - 2 Adults/2 Childs - 1 Room - " + countryPar );

        PageUtils.waitElementForVisibility(driver, basePage.hotelesIcon, 10, "Hoteles icon");
        basePage.hotelesIcon.click();

        dataManagement.getHotelDataTripItinerary("miami_10days_2adults_2childs_1room");

        hotelsDataTrip = basePage.hotelesDataTrip();

        hotelsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);

        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.startDate);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);

        hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);

        hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

        Assert.assertTrue(hotelsResultsPage.vacancy());

        hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(0);
        hotelsDetailPage.clickVerHabitacionesBtn();

        checkOutPage = hotelsDetailPage.clickReservarAhoraBtn();

        dataManagement.getPassengerData("adult_female_native");
        dataManagement.getPassengerData("adult_female_native");
        dataManagement.getPassengerData("child_female_native");
        dataManagement.getPassengerData("child_female_native");

        checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                                          dataManagement.getPaymentData("1_amex_amex"),
                                          dataManagement.getBillingData("local_Billing_v2"),
                                          dataManagement.getContactData("contact_cell_phone"),
                                          "HotelesCheckOutPageInternational");
    }

    @Test
    public void hotelDomLoginReservationFlow() {
        logTestTitle("Hotel Flow - Domestic - 15 days - 2 Adults - 1 Room - " + countryPar );

        PageUtils.waitElementForVisibility(driver, basePage.hotelesIcon, 10, "Hoteles icon");
        basePage.hotelesIcon.click();

        dataManagement.getHotelDataTripItinerary("domestic01_15days_2adults_1room");

        hotelsDataTrip = basePage.hotelesDataTrip();

        hotelsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);

        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.startDate);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);

        hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);

        hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

        Assert.assertTrue(hotelsResultsPage.vacancy());

        hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(0);
        hotelsDetailPage.clickVerHabitacionesBtn();

        checkOutPage = hotelsDetailPage.clickReservarAhoraBtn();

        dataManagement.getPassengerData("adult_female_native");
        dataManagement.getPassengerData("adult_female_native");

        checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                                          dataManagement.getPaymentData("1_amex_amex"),
                                          dataManagement.getBillingData("local_Billing_v2"),
                                          dataManagement.getContactData("contact_phone"),
                                          "HotelesCheckOutPageDomestic");
    }
}
