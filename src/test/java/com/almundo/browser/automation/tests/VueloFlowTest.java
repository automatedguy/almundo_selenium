package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.ResultsPage.VuelosResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class VueloFlowTest extends TestBaseSetup {

    private VuelosResultsPage vuelosResultsPage = null;
    private CheckOutPage checkOutPage = null;

    private JSONArray passengerJsonList = new JSONArray();

    @BeforeClass
    private void initDataTripList() {
        basePage = new BasePage(driver);
        basePage.vuelosDataTrip().getVuelosDataTripList();

        checkOutPage = initCheckOutPage();
        checkOutPage.passengerSection().getPassengersList();
        checkOutPage.creditCardSection().getCreditCardList();
        checkOutPage.billingSection().getBillingList();
        checkOutPage.contactSection().getContactList();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void vueloReservationFirstOptionFlow() throws InterruptedException {

        PageUtils.waitElementForVisibility(driver, basePage.vuelosIcon, 10, "Vuelos icon");
        basePage.vuelosIcon.click();

        basePage.vuelosDataTrip().getVuelosDataTripItinerary("miami_10days_2adults_2childs_turista");

        basePage.vuelosDataTrip().setOrigin(basePage.vuelosDataTrip().originAuto, basePage.vuelosDataTrip().originFull);
        basePage.vuelosDataTrip().setDestination(basePage.vuelosDataTrip().destinationAuto, basePage.vuelosDataTrip().destinationFull);

        basePage.vuelosDataTrip().selectDateFromCalendar(basePage.vuelosDataTrip().departureFlightsCalendar, basePage.vuelosDataTrip().startDate);
        basePage.vuelosDataTrip().selectDateFromCalendar(basePage.vuelosDataTrip().arrivalFlightsCalendar, basePage.vuelosDataTrip().endDate);

        numPassengers = basePage.vuelosDataTrip().selectPassenger(basePage.vuelosDataTrip().adults, basePage.vuelosDataTrip().childs);

        basePage.vuelosDataTrip().selectClass(basePage.vuelosDataTrip().flightClass);

        vuelosResultsPage = basePage.vuelosDataTrip().clickBuscarBtn();

        Assert.assertTrue(vuelosResultsPage.vacancy());

        vuelosResultsPage.clickTicketIdaRdb();
        vuelosResultsPage.clickTicketVuelta();
        checkOutPage = vuelosResultsPage.clickComprarBtn(0);

        checkOutPage.passengerSection().getPassengerData("adult_male_passport_native");
        checkOutPage.passengerSection().getPassengerData("adult_male_passport_native");
        checkOutPage.passengerSection().getPassengerData("child_male_passport_native");
        checkOutPage.passengerSection().getPassengerData("child_male_passport_native");

        checkOutPage.creditCardSection().getCreditCardData("amex");
        checkOutPage.billingSection().getBillingData("local_Billing");
        checkOutPage.contactSection().getContactData("contact_cell_phone");

        checkOutPage.populateCheckOutPage(numPassengers,
                checkOutPage.passengerSection().passengerJsonList,
                checkOutPage.creditCardSection().creditCardData,
                checkOutPage.billingSection().billingData,
                checkOutPage.contactSection().contactData);

    }

}