package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.PaymentPage.PaymentPage;
import com.almundo.browser.automation.pages.ResultsPage.VuelosResultsPage;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class VueloFlowTest extends TestBaseSetup {

    private JSONObject vuelosList = null;
    private JSONObject vuelo = null;

    VuelosResultsPage vuelosResultsPage = null;
    PaymentPage paymentPage = null;

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

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void vueloReservationFirstOptionFlow() throws InterruptedException {
        getVueloDataObject("miami_10days_2adults_2childs_turista");

        PageUtils.waitElementForVisibility(driver, basePage.vuelosIcon, 10, "Vuelos icon");
        basePage.vuelosIcon.click();

        basePage.vuelosDataTrip().setOrigin(originAuto, originFull);
        basePage.vuelosDataTrip().setDestination(destinationAuto, destinationFull);

        basePage.vuelosDataTrip().selectDateFromCalendar(basePage.vuelosDataTrip().departureFlightsCalendar, startDate);
        basePage.vuelosDataTrip().selectDateFromCalendar(basePage.vuelosDataTrip().arrivalFlightsCalendar, endDate);

        numPassengers = basePage.vuelosDataTrip().selectPassenger(adults, childs);

        basePage.vuelosDataTrip().selectClass(flightClass);

        vuelosResultsPage = basePage.vuelosDataTrip().clickBuscarBtn();

        if (basePage.vuelosDataTrip().nothingFound()) {
          System.out.println("Nothing Found: VUELOS");
        } else {
            vuelosResultsPage.ticketIdaRdbClick();
            vuelosResultsPage.ticketVueltaClick();
            paymentPage = vuelosResultsPage.comprarBtnClick(1);
            paymentPage.populatePaymentPage(numPassengers);
        }
    }

}