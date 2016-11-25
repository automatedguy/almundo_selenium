package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.flows.VueloHotelFlow;
import com.almundo.browser.automation.locators.flows.BaseFlowMap;
import com.almundo.browser.automation.locators.flows.VueloHotelFlowMap;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class VueloHotelFlowTest extends TestBaseSetup {

    public VueloHotelFlow vueloHotelFlow = new VueloHotelFlow(driver);

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

    private void getVueloDataObject(String combination) {
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

    @Test
    public void vueloHotelReservationFirstOptionFlow() throws InterruptedException {
        getVueloDataObject("miami_10days_2adults_2childs_1room");

        vueloHotelFlow.waitForVisibilityOfElementLocated(driver, 15, BaseFlowMap.VUELO_HOTEL_ICO.getBy());
        vueloHotelFlow.clickOn(driver, BaseFlowMap.VUELO_HOTEL_ICO.getBy());

        vueloHotelFlow.enterText(driver, originAuto, VueloHotelFlowMap.ORIGIN_FLIGHTS_TXT.getBy());
        vueloHotelFlow.waitForVisibilityOfElementLocated(driver, 10, originFullFinal);
        vueloHotelFlow.selectFromAutoCompleteSuggestions(driver, originFullFinal);

        vueloHotelFlow.enterText(driver, destinationAuto, VueloHotelFlowMap.DESTINATION_FLIGHTS_TXT.getBy());
        vueloHotelFlow.waitForVisibilityOfElementLocated(driver, 10, destinationFullFinal);
        vueloHotelFlow.selectFromAutoCompleteSuggestions(driver, destinationFullFinal);

        vueloHotelFlow.selectDateFromCalendar(driver, VueloHotelFlowMap.TRIPS_FECHA_SALIDA_CAL.getBy(), startDate);
        vueloHotelFlow.selectDateFromCalendar(driver, VueloHotelFlowMap.TRIPS_FECHA_REGRESO_CAL.getBy(), endDate);

        numPassengers = vueloHotelFlow.selectPassenger(driver, adults, childs, rooms);

        vueloHotelFlow.clickOn(driver, BaseFlowMap.BUSCAR_BTN.getBy());

        if(vueloHotelFlow.nothingFound(driver)){
            System.out.println("Nothing Found: VUELO + HOTEL");
        }
        else {
            vueloHotelFlow.doVueloHotelReservationFlow(driver);
            vueloHotelFlow.paymentPage.passengersSection.populatePassenger(driver, numPassengers);
        }
    }
}
