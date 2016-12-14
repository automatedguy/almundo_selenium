package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.PaymentPage.PaymentPage;
import com.almundo.browser.automation.pages.ResultsPage.HotelesDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.HotelesResultsPage;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.PageUtils;
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
    private JSONObject hotel = null;

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

    private void getHotelDataObject(String combination) {
        hotel = JsonRead.getJsonDataObject(hotelesList, combination, countryPar.toLowerCase() + "_data.json");

        destinationAuto = hotel.get("destinationAuto").toString();
        destinationFull = hotel.get("destinationFull").toString();

        startDate = Integer.parseInt(hotel.get("startDate").toString());
        endDate = Integer.parseInt(hotel.get("endDate").toString());

        adults = Integer.parseInt(hotel.get("adults").toString());
        childs = Integer.parseInt(hotel.get("childs").toString());

        rooms = Integer.parseInt(hotel.get("rooms").toString());
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void hotelReservationFirstOptionFlow() throws InterruptedException {
        getHotelDataObject("miami_10days_2adults_2childs_1room");

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
        paymentPage.populatePaymentPage(numPassengers);

    }
}
