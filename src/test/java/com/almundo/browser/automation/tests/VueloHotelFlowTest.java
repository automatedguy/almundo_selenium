package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.flows.VueloHotelFlow;
import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class VueloHotelFlowTest extends TestBaseSetup {

    public VueloHotelFlow vueloHotelFlow = new VueloHotelFlow(driver);
    private BasePage basePage = null;

    private JSONObject vueloHotelList = null;
    private JSONObject vueloHotel = null;

    private String originAuto;
    private String originFull;
    private By originFullFinal;
    private String destinationAuto;
    private String destinationFull;
    private By destinationFullFinal;
    private int startDate;
    private int endDate;
    private int adults;
    private int childs;
    private int rooms;

    @BeforeClass
    private void getVuelosListDataObject() {
        vueloHotelList = (JSONObject) dataTestObject.get("vueloHotel");
    }

    private void getVueloHotelDataObject(String combination) {
        vueloHotel = (JSONObject) vueloHotelList.get(combination);

        originAuto = vueloHotel.get("originAuto").toString();
        originFull = vueloHotel.get("originFull").toString();
        originFullFinal = By.xpath(String.format("//span[contains(.,'%s')]", originFull));

        destinationAuto = vueloHotel.get("destinationAuto").toString();
        destinationFull = vueloHotel.get("destinationFull").toString();
        destinationFullFinal = By.xpath(String.format("//span[contains(.,'%s')]", destinationFull));

        startDate = Integer.parseInt(vueloHotel.get("startDate").toString());
        endDate = Integer.parseInt(vueloHotel.get("endDate").toString());

        adults = Integer.parseInt(vueloHotel.get("adults").toString());
        childs = Integer.parseInt(vueloHotel.get("childs").toString());

        rooms = Integer.parseInt(vueloHotel.get("rooms").toString());

    }

    @BeforeMethod
    private void initBasePageObject() {
        basePage = initBasePage();
    }

    @Test
    public void vueloHotelReservationFirstOptionFlow() throws InterruptedException {
        getVueloHotelDataObject("miami_10days_2adults_2childs_1room");

        PageUtils.waitElementForVisibility(driver, basePage.vueloHotelIcon, 10, "Vuelo+Hotel icon");
        basePage.vueloHotelIcon.click();

        basePage.vueloHotelDataTrip().setOrigin(originAuto);
        basePage.vueloHotelDataTrip().selectFromAutoCompleteSuggestions(originFullFinal);

        basePage.vueloHotelDataTrip().setDestination(destinationAuto);
        basePage.vueloHotelDataTrip().selectFromAutoCompleteSuggestions(destinationFullFinal);

        basePage.vueloHotelDataTrip().selectDateFromCalendar(basePage.vueloHotelDataTrip().departureCalendar, startDate);
        basePage.vueloHotelDataTrip().selectDateFromCalendar(basePage.vueloHotelDataTrip().arrivalCalendar, endDate);

        numPassengers = basePage.vueloHotelDataTrip().selectPassenger(adults, childs, rooms);

        basePage.vueloHotelDataTrip().buscarBtn.click();

        if(vueloHotelFlow.nothingFound(driver)){
            System.out.println("Nothing Found: VUELO + HOTEL");
        }
        else {
            vueloHotelFlow.doVueloHotelReservationFlow(driver);
            vueloHotelFlow.paymentPage.populatePaymentPage(driver, numPassengers);
        }
    }
}
