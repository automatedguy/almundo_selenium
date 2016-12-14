package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.PaymentPage.PaymentPage;
import com.almundo.browser.automation.pages.ResultsPage.VueloHotelDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.VueloHotelResultsPage;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class VueloHotelFlowTest extends TestBaseSetup {

    VueloHotelResultsPage vueloHotelResultsPage = null;
    VueloHotelDetailPage vueloHotelDetailPage = null;
    PaymentPage paymentPage = null;

    private JSONObject vueloHotelList = null;
    private JSONObject vueloHotel = null;

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

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void vueloHotelReservationFirstOptionFlow() throws InterruptedException {
        getVueloHotelDataObject("miami_10days_2adults_2childs_1room");

        PageUtils.waitElementForVisibility(driver, basePage.vueloHotelIcon, 10, "Vuelo+Hotel icon");
        basePage.vueloHotelIcon.click();

        basePage.vueloHotelDataTrip().setOrigin(originAuto, originFull);
        basePage.vueloHotelDataTrip().setDestination(destinationAuto, destinationFull);

        basePage.vueloHotelDataTrip().selectDateFromCalendar(basePage.vueloHotelDataTrip().departureCalendar, startDate);
        basePage.vueloHotelDataTrip().selectDateFromCalendar(basePage.vueloHotelDataTrip().arrivalCalendar, endDate);

        numPassengers = basePage.vueloHotelDataTrip().selectPassenger(adults, childs, rooms);

        basePage.vueloHotelDataTrip().buscarBtn.click();

        vueloHotelResultsPage = basePage.vueloHotelDataTrip().clickBuscarBtn();

        if(basePage.nothingFound()){
            System.out.println("Nothing Found: VUELO + HOTEL");
        }
        else {
            vueloHotelResultsPage.elegirBtnClick(0);
            vueloHotelDetailPage = vueloHotelResultsPage.continuarBtnClick();
            vueloHotelDetailPage.verHabitacionBtnClick();
            paymentPage = vueloHotelDetailPage.comprarBtnClick(0);
            paymentPage.populatePaymentPage(numPassengers);
        }
    }
}
