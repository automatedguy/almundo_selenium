package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPage.ConfirmationPage;
import com.almundo.browser.automation.pages.CheckOutPage.PassengerSection;
import com.almundo.browser.automation.pages.ResultsPage.VuelosResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class VueloSucursalesFlowTest extends TestBaseSetup {

    private VuelosResultsPage vuelosResultsPage = null;
    private CheckOutPage checkOutPage = null;
    private ConfirmationPage confirmationPage = null;

    @BeforeClass
    private void initDataTripList() {
        basePage = new BasePage(driver);
        basePage.vuelosDataTrip().getVuelosDataTripList();

        checkOutPage = initCheckOutPage();
        checkOutPage.passengerSection().getPassengersList();
        checkOutPage.paymentSection().getPaymentList();
        checkOutPage.billingSection().getBillingList();
        checkOutPage.contactSection().getContactList();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        PassengerSection.passengerJsonList = new JSONArray();

    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void vueloDomReservationFlow() {
        logTestTitle("Vuelo Flow - Domestic - 20 days - 2 Adults - Todas - " + countryPar );

        PageUtils.waitElementForVisibility(driver, basePage.vuelosIcon, 10, "Vuelos icon");
        basePage.vuelosIcon.click();

        basePage.vuelosDataTrip().getVuelosDataTripItinerary("domestic_30days_2adults_todas");

        basePage.vuelosDataTrip().setOrigin(basePage.vuelosDataTrip().originAuto, basePage.vuelosDataTrip().originFull);
        basePage.vuelosDataTrip().setDestination(basePage.vuelosDataTrip().destinationAuto, basePage.vuelosDataTrip().destinationFull);

        basePage.vuelosDataTrip().selectDateFromCalendar(basePage.vuelosDataTrip().departureFlightsCalendar, basePage.vuelosDataTrip().startDate);
        basePage.vuelosDataTrip().selectDateFromCalendar(basePage.vuelosDataTrip().arrivalFlightsCalendar, basePage.vuelosDataTrip().endDate);

        numPassengers = basePage.vuelosDataTrip().selectPassenger(basePage.vuelosDataTrip().adults, basePage.vuelosDataTrip().childs);
        basePage.vuelosDataTrip().selectChildAgeRange(basePage.vuelosDataTrip().childAgeRange);

        basePage.vuelosDataTrip().selectClass(basePage.vuelosDataTrip().flightClass);

        vuelosResultsPage = basePage.vuelosDataTrip().clickBuscarBtn();

        Assert.assertTrue(vuelosResultsPage.vacancy());

        vuelosResultsPage.clickTicketIdaRdb();
        vuelosResultsPage.clickTicketVuelta();
        checkOutPage = vuelosResultsPage.clickComprarBtn(2);

        checkOutPage.passengerSection().getPassengerData("adult_female_foreign");
        checkOutPage.passengerSection().getPassengerData("adult_female_foreign");

        checkOutPage.paymentSection().getPaymentData("deposit");
        checkOutPage.billingSection().getBillingData("local_Billing_sucursales");
        checkOutPage.contactSection().getContactData("contact_phone");

        checkOutPage.populateCheckOutPage(numPassengers,
                checkOutPage.passengerSection().passengerJsonList,
                checkOutPage.paymentSection().paymentData,
                checkOutPage.billingSection().billingData,
                checkOutPage.contactSection().contactData,
                "VueloHotelCheckOutPageDomesticSucursal", true);

/*        confirmationPage = checkOutPage.clickComprarBtn();
        Assert.assertTrue(confirmationPage.confirmationOk());*/
    }
}