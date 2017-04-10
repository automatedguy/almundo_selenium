package com.almundo.browser.automation.tests.Hotels;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.HotelsDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.ResultsPage.HotelsDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.HotelsResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.*;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class FlowCheckOutV3Test extends TestBaseSetup {

    private HotelsResultsPage hotelsResultsPage = null;
    private HotelsDetailPage hotelsDetailPage = null;
    private CheckOutPageV3 checkOutPage = null;

    private HotelsDataTrip hotelsDataTrip = null;
    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initItineraryData() {
        dataManagement.getHotelsItineraryData();
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
    public void dom_Booking_Flow() {
        logTestTitle("Hotel Flow - Domestic - 15 days - 2 Adults - 1 Room - " + countryPar );
        PageUtils.waitElementForVisibility(driver, basePage.hotelsIcon, 10, "Hoteles icon");

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
        checkOutPage = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);

        dataManagement.getPassengerData("adult_female_native");
        dataManagement.getPassengerData("adult_female_native");
        replaceChkOutV2Url();

        checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                                          dataManagement.getPaymentData("1_amex_amex"),
                                          dataManagement.getBillingData("local_Billing_v2"),
                                          dataManagement.getContactData("contact_phone"),
                                          "HotelsCheckOutPageDomesticV3");
    }
}
