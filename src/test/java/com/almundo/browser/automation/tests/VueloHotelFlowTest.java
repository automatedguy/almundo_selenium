package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.PaymentPage.PaymentPage;
import com.almundo.browser.automation.pages.ResultsPage.VueloHotelDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.VueloHotelResultsPage;
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

public class VueloHotelFlowTest extends TestBaseSetup {

    private VueloHotelResultsPage vueloHotelResultsPage = null;
    private VueloHotelDetailPage vueloHotelDetailPage = null;
    private PaymentPage paymentPage = null;

    private JSONObject vueloHotelList = null;
    private JSONObject vueloHotel = null;

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
    private int rooms;

    @BeforeClass
    private void getVuelosListDataObject() {
        vueloHotelList = JsonRead.getJsonDataObject(jsonDataObject, "vueloHotel", countryPar.toLowerCase() + "_data.json");
    }

    private void getVueloHotelDataObject(String combination) {
        vueloHotel = JsonRead.getJsonDataObject(vueloHotelList, combination, countryPar.toLowerCase() + "_data.json");

        originAuto = vueloHotel.get("originAuto").toString();
        originFull = vueloHotel.get("originFull").toString();

        destinationAuto = vueloHotel.get("destinationAuto").toString();
        destinationFull = vueloHotel.get("destinationFull").toString();

        startDate = Integer.parseInt(vueloHotel.get("startDate").toString());
        endDate = Integer.parseInt(vueloHotel.get("endDate").toString());

        adults = Integer.parseInt(vueloHotel.get("adults").toString());
        childs = Integer.parseInt(vueloHotel.get("childs").toString());

        rooms = Integer.parseInt(vueloHotel.get("rooms").toString());

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
    public void vueloHotelReservationFirstOptionFlow() throws InterruptedException {
        getVueloHotelDataObject("miami_10days_2adults_2childs_1room");
        getBillingDataObject("local_Billing");
        getContactDataObject("contact_cell_phone");
        getCreditCardDataObject("amex");

        getPassengersListObject();
        getPassengerDataObject("adult_male_passport_native");
        getPassengerDataObject("adult_male_passport_native");
        getPassengerDataObject("child_male_passport_native");
        getPassengerDataObject("child_male_passport_native");

        PageUtils.waitElementForVisibility(driver, basePage.vueloHotelIcon, 10, "Vuelo+Hotel icon");
        basePage.vueloHotelIcon.click();

        basePage.vueloHotelDataTrip().setOrigin(originAuto, originFull);
        basePage.vueloHotelDataTrip().setDestination(destinationAuto, destinationFull);

        basePage.vueloHotelDataTrip().selectDateFromCalendar(basePage.vueloHotelDataTrip().departureCalendar, startDate);
        basePage.vueloHotelDataTrip().selectDateFromCalendar(basePage.vueloHotelDataTrip().arrivalCalendar, endDate);

        numPassengers = basePage.vueloHotelDataTrip().selectPassenger(adults, childs, rooms);

        vueloHotelResultsPage = basePage.vueloHotelDataTrip().clickBuscarBtn();

        Assert.assertTrue(vueloHotelResultsPage.vacancy());

        vueloHotelResultsPage.clickElegirBtn(0);
        vueloHotelDetailPage = vueloHotelResultsPage.clickContinuarBtn();
        vueloHotelDetailPage.clickVerHabitacionBtn();
        paymentPage = vueloHotelDetailPage.clickComprarBtn(0);
        paymentPage.populatePaymentPage(billingData, contactData, creditCardData, passengerJsonList, numPassengers);
    }
}
