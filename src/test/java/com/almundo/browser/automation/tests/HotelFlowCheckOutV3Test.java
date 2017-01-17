package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPage.PassengerSection;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.ResultsPage.HotelesDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.HotelesResultsPage;
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

public class HotelFlowCheckOutV3Test extends TestBaseSetup {

    private HotelesResultsPage hotelesResultsPage = null;
    private HotelesDetailPage hotelesDetailPage = null;
    private CheckOutPageV3 checkOutPage = null;

    @BeforeClass
    private void initDataTripList() {
        basePage = new BasePage(driver);
        basePage.hotelesDataTrip().getHotelesDataTripList();

        checkOutPage = initCheckOutPageV3();
        checkOutPage.passengerSection().getPassengersList();
        checkOutPage.creditCardSectionV3().getCreditCardList();
        checkOutPage.billingSectionV3().getBillingList();
        checkOutPage.contactSectionV3().getContactList();
    }

    @BeforeMethod
    private void closeLoginPopUp(){
        LoginPopUp loginPopUp = new LoginPopUp(driver);
        loginPopUp.clickCloseLoginBtn();
    }


    @AfterMethod
    private void cleanPassengerJsonList() {
        PassengerSection.passengerJsonList = new JSONArray();

    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void hotelDomReservationFlow() {
        logTestTitle("Hotel Flow - Domestic - 15 days - 2 Adults - 1 Room - " + countryPar );

        PageUtils.waitElementForVisibility(driver, basePage.hotelesIcon, 10, "Hoteles icon");
        basePage.hotelesIcon.click();

        basePage.hotelesDataTrip().getHotelDataTripItinerary("domestic01_15days_2adults_1room");

        basePage.hotelesDataTrip().setDestination(basePage.hotelesDataTrip().destinationAuto, basePage.hotelesDataTrip().destinationFull);

        basePage.hotelesDataTrip().selectDateFromCalendar(basePage.hotelesDataTrip().checkinCalendar, basePage.hotelesDataTrip().startDate);
        basePage.hotelesDataTrip().selectDateFromCalendar(basePage.hotelesDataTrip().checkoutCalendar, basePage.hotelesDataTrip().endDate);

        numPassengers = basePage.hotelesDataTrip().selectPassenger(basePage.hotelesDataTrip().adults, basePage.hotelesDataTrip().childs, basePage.hotelesDataTrip().rooms);

        hotelesResultsPage = basePage.hotelesDataTrip().clickBuscarBtn();

        Assert.assertTrue(hotelesResultsPage.vacancy());

        hotelesDetailPage = hotelesResultsPage.clickVerHotelBtn(0);
        hotelesDetailPage.clickVerHabitacionesBtn();

        checkOutPage = hotelesDetailPage.clickReservarAhoraV3Btn();

        checkOutPage.passengerSection().getPassengerData("adult_female_native");
        checkOutPage.passengerSection().getPassengerData("adult_female_native");

        checkOutPage.creditCardSectionV3().getCreditCardData("amex");
        checkOutPage.billingSectionV3().getBillingData("local_Billing_v2");
        checkOutPage.contactSectionV3().getContactData("contact_phone");

        checkOutPage.populateCheckOutPage(numPassengers,
                checkOutPage.passengerSection().passengerJsonList,
                checkOutPage.creditCardSectionV3().creditCardData,
                checkOutPage.billingSectionV3().billingData,
                checkOutPage.contactSectionV3().contactData, "HotelesCheckOutPageDomesticV3");


    }
}
