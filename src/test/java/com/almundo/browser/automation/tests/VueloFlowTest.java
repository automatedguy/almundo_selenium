package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.PaymentPage.PaymentPage;
import com.almundo.browser.automation.pages.ResultsPage.VuelosResultsPage;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.pages.PaymentPage.PaymentPage.getPassengersListObject;
import static com.almundo.browser.automation.pages.PaymentPage.PaymentPage.passengersList;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class VueloFlowTest extends TestBaseSetup {

    private VuelosResultsPage vuelosResultsPage = null;
    private PaymentPage paymentPage = null;

    private JSONObject vuelosList = null;
    private JSONObject vuelo = null;

    private JSONObject billingData = null;
    private JSONObject contactData = null;
    private JSONObject creditCardData = null;
    private JSONObject passengerData = null;

    private JSONArray passengerJsonList = new JSONArray();

    private String originAuto;
    private String originFull;
    private String destinationAuto;
    private String destinationFull;
    private int startDate;
    private int endDate;
    private int adults;
    private int childs;
    private String flightClass;

    @BeforeClass
    private void getVuelosListDataObject() {
        vuelosList = JsonRead.getJsonDataObject(jsonDataObject, "vuelos", countryPar.toLowerCase() + "_data.json");
    }

    private void getVueloDataObject(String combination) {
        vuelo = JsonRead.getJsonDataObject(vuelosList, combination, countryPar.toLowerCase() + "_data.json");

        originAuto = vuelo.get("originAuto").toString();
        originFull = vuelo.get("originFull").toString();

        destinationAuto = vuelo.get("destinationAuto").toString();
        destinationFull = vuelo.get("destinationFull").toString();

        startDate = Integer.parseInt(vuelo.get("startDate").toString());
        endDate = Integer.parseInt(vuelo.get("endDate").toString());

        adults = Integer.parseInt(vuelo.get("adults").toString());
        childs = Integer.parseInt(vuelo.get("childs").toString());

        flightClass = vuelo.get("flightClass").toString();
    }

    private void getPassengerDataObject(String dataSet)  {
        passengerData = JsonRead.getJsonDataObject(passengersList, dataSet, countryPar.toLowerCase() + "_data.json");
        passengerJsonList.add(passengerData);
    }

    private void getCreditCardDataObject(String dataSet)  {
        creditCardData = JsonRead.getJsonDataObject(PaymentPage.getCreditCardListObject(), dataSet, countryPar.toLowerCase() + "_data.json");
    }

    private void getBillingDataObject(String dataSet)  {
        billingData = JsonRead.getJsonDataObject(PaymentPage.getBillingListObject(), dataSet, countryPar.toLowerCase() + "_data.json");
    }

    private void getContactDataObject(String dataSet)  {
        contactData = JsonRead.getJsonDataObject(PaymentPage.getContactsListObject(), dataSet, countryPar.toLowerCase() + "_data.json");
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void vueloReservationFirstOptionFlow() throws InterruptedException {
        getVueloDataObject("miami_10days_2adults_2childs_turista");
        getBillingDataObject("local_Billing");
        getContactDataObject("contact_cell_phone");
        getCreditCardDataObject("amex");

        getPassengersListObject();
        getPassengerDataObject("adult_male_passport_native");
        getPassengerDataObject("adult_male_passport_native");
        getPassengerDataObject("child_male_passport_native");
        getPassengerDataObject("child_male_passport_native");

        PageUtils.waitElementForVisibility(driver, basePage.vuelosIcon, 10, "Vuelos icon");
        basePage.vuelosIcon.click();

        basePage.vuelosDataTrip().setOrigin(originAuto, originFull);
        basePage.vuelosDataTrip().setDestination(destinationAuto, destinationFull);

        basePage.vuelosDataTrip().selectDateFromCalendar(basePage.vuelosDataTrip().departureFlightsCalendar, startDate);
        basePage.vuelosDataTrip().selectDateFromCalendar(basePage.vuelosDataTrip().arrivalFlightsCalendar, endDate);

        numPassengers = basePage.vuelosDataTrip().selectPassenger(adults, childs);

        basePage.vuelosDataTrip().selectClass(flightClass);

        vuelosResultsPage = basePage.vuelosDataTrip().clickBuscarBtn();

        Assert.assertTrue(vuelosResultsPage.vacancy());

        vuelosResultsPage.clickTicketIdaRdb();
        vuelosResultsPage.clickTicketVuelta();
        paymentPage = vuelosResultsPage.clickComprarBtn(0);
        paymentPage.populatePaymentPage(billingData, contactData, creditCardData, passengerJsonList, numPassengers);
    }

}