package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.PaymentPage.PaymentPage;
import com.almundo.browser.automation.pages.ResultsPage.HotelesDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.HotelesResultsPage;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class HotelFlowTest extends TestBaseSetup {

    private HotelesResultsPage hotelesResultsPage = null;
    private HotelesDetailPage hotelesDetailPage = null;
    private PaymentPage paymentPage = null;

    private JSONObject hotelesList = null;
    private JSONObject hotelData = null;

    private JSONObject billingData = null;
    private JSONObject contactData = null;
    private JSONObject creditCardData = null;
    private JSONObject passengerData = null;

    private JSONArray passengerList = new JSONArray();

    private String destinationAuto;
    private String destinationFull;
    private int startDate;
    private int endDate;
    private int adults;
    private int childs;
    private int rooms;

    @BeforeClass
    private void getHotelesListDataObject() {
        hotelesList = JsonRead.getJsonDataObject(jsonDataObject, "hoteles", countryPar.toLowerCase() + "_data.json");
    }

    private void getHotelDataObject(String dataSet) {
        hotelData = JsonRead.getJsonDataObject(hotelesList, dataSet, countryPar.toLowerCase() + "_data.json");

        destinationAuto = hotelData.get("destinationAuto").toString();
        destinationFull = hotelData.get("destinationFull").toString();

        startDate = Integer.parseInt(hotelData.get("startDate").toString());
        endDate = Integer.parseInt(hotelData.get("endDate").toString());

        adults = Integer.parseInt(hotelData.get("adults").toString());
        childs = Integer.parseInt(hotelData.get("childs").toString());

        rooms = Integer.parseInt(hotelData.get("rooms").toString());
    }

    private void getBillingDataObject(String dataSet)  {
        billingData = JsonRead.getJsonDataObject(PaymentPage.getBillingListObject(), dataSet, countryPar.toLowerCase() + "_data.json");
    }

    private void getContactDataObject(String dataSet)  {
        contactData = JsonRead.getJsonDataObject(PaymentPage.getContactsListObject(), dataSet, countryPar.toLowerCase() + "_data.json");
    }

    private void getCreditCardDataObject(String dataSet)  {
        creditCardData = JsonRead.getJsonDataObject(PaymentPage.getCreditCardListObject(), dataSet, countryPar.toLowerCase() + "_data.json");
    }

    private void getPassengersDataObject(String dataSet)  {
        passengerData = JsonRead.getJsonDataObject(PaymentPage.getPassengersListObject(), dataSet, countryPar.toLowerCase() + "_data.json");
        logger.info("passengerData Info:" + passengerData);
        passengerList.add(passengerData);
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void hotelReservationFirstOptionFlow() throws InterruptedException {
        getHotelDataObject("miami_10days_2adults_2childs_1room");
        getBillingDataObject("local_Billing");
        getContactDataObject("contact_cell_phone");
        getCreditCardDataObject("amex");

        getPassengersDataObject("adult_female_dni_native");
        getPassengersDataObject("adult_female_dni_native");
        getPassengersDataObject("child_female_dni_native");
        getPassengersDataObject("child_female_dni_native");

        logger.info("Passenger LIst: " + passengerList);

        PageUtils.waitElementForVisibility(driver, basePage.hotelesIcon, 10, "Hoteles icon");
        basePage.hotelesIcon.click();

        basePage.hotelesDataTrip().setDestination(destinationAuto, destinationFull);

        basePage.hotelesDataTrip().selectDateFromCalendar(basePage.hotelesDataTrip().checkinCalendar, startDate);
        basePage.hotelesDataTrip().selectDateFromCalendar(basePage.hotelesDataTrip().checkoutCalendar, endDate);

        numPassengers = basePage.hotelesDataTrip().selectPassenger(adults, childs, rooms);

        hotelesResultsPage = basePage.hotelesDataTrip().clickBuscarBtn();

        Assert.assertTrue(hotelesResultsPage.vacancy());

        hotelesDetailPage = hotelesResultsPage.clickVerHotelBtn(0);
        hotelesDetailPage.clickVerHabitacionesBtn();

        paymentPage = hotelesDetailPage.clickReservarAhoraBtn();
        paymentPage.populatePaymentPage(billingData, contactData, creditCardData, passengerList,numPassengers);

    }
}
