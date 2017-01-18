package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPage.ConfirmationPage;
import com.almundo.browser.automation.pages.CheckOutPage.PassengerSection;
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

public class VueloHotelFlowCheckOutV3Test extends TestBaseSetup {

    private VueloHotelResultsPage vueloHotelResultsPage = null;
    private VueloHotelDetailPage vueloHotelDetailPage = null;
    private CheckOutPageV3 checkOutPage = null;
    private ConfirmationPage confirmationPage = null;

    @BeforeClass
    private void initDataTripList() {
        basePage = new BasePage(driver);
        basePage.vueloHotelDataTrip().getVueloHotelDataTripList();

        checkOutPage = initCheckOutPageV3();
        checkOutPage.passengerSectionV3().getPassengersList();
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
    public void vueloHotelIntReservationFlow() {
        logTestTitle("Vuelo+Hotel Flow - Domestic - 20 days - 2 Adults - 1 Room - " + countryPar );

        PageUtils.waitElementForVisibility(driver, basePage.vueloHotelIcon, 10, "Vuelo+Hotel icon");
        basePage.vueloHotelIcon.click();

        basePage.vueloHotelDataTrip().getVueloHotelDataTripItinerary("int02_20days_2adults_1childs_1room");

        basePage.vueloHotelDataTrip().setOrigin(basePage.vueloHotelDataTrip().originAuto, basePage.vueloHotelDataTrip().originFull);
        basePage.vueloHotelDataTrip().setDestination(basePage.vueloHotelDataTrip().destinationAuto, basePage.vueloHotelDataTrip().destinationFull);

        basePage.vueloHotelDataTrip().selectDateFromCalendar(basePage.vueloHotelDataTrip().departureCalendar, basePage.vueloHotelDataTrip().startDate);
        basePage.vueloHotelDataTrip().selectDateFromCalendar(basePage.vueloHotelDataTrip().arrivalCalendar, basePage.vueloHotelDataTrip().endDate);

        numPassengers = basePage.vueloHotelDataTrip().selectPassenger(basePage.vueloHotelDataTrip().adults,
                basePage.vueloHotelDataTrip().childs,
                basePage.vueloHotelDataTrip().rooms);

        vueloHotelResultsPage = basePage.vueloHotelDataTrip().clickBuscarBtn();

        Assert.assertTrue(vueloHotelResultsPage.vacancy());

        vueloHotelResultsPage.clickElegirBtn(0);
        vueloHotelDetailPage = vueloHotelResultsPage.clickContinuarBtn();
        vueloHotelDetailPage.clickVerHabitacionBtn();

        checkOutPage = vueloHotelDetailPage.clickComprarV3Btn(0);

        checkOutPage.passengerSectionV3().getPassengerData("adult_female_foreign");
        checkOutPage.passengerSectionV3().getPassengerData("adult_female_foreign");
        checkOutPage.passengerSectionV3().getPassengerData("child_female_native");

        checkOutPage.creditCardSectionV3().getCreditCardData("amex");
        checkOutPage.billingSectionV3().getBillingData("local_Billing_v2");
        checkOutPage.contactSectionV3().getContactData("contact_cell_phone");


        checkOutPage.populateCheckOutPage(numPassengers,
                checkOutPage.passengerSectionV3().passengerJsonList,
                checkOutPage.creditCardSectionV3().creditCardData,
                checkOutPage.billingSectionV3().billingData,
                checkOutPage.contactSectionV3().contactData, "VueloHotelCheckOutPageDomesticV3");

/*        confirmationPage = checkOutPage.clickComprarBtn();
        Assert.assertTrue(confirmationPage.confirmationOk());*/
    }
}
