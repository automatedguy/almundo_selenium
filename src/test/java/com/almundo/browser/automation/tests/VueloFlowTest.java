package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.flows.VueloFlow;
import com.almundo.browser.automation.locators.flows.BaseFlowMap;
import com.almundo.browser.automation.locators.flows.VueloFlowMap;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class VueloFlowTest extends TestBaseSetup {

    public VueloFlow vueloFlow = new VueloFlow(driver);

    private JSONObject vuelosList = null;
    private JSONObject vuelo = null;

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
    private String flightClass;

    @BeforeClass
    private void getVuelosListDataObject() {
        vuelosList = (JSONObject) dataTestObject.get("vuelos");
    }

    private void getVueloDataObject(String combination) {
        vuelo = (JSONObject) vuelosList.get(combination);

        originAuto = vuelo.get("originAuto").toString();
        originFull = vuelo.get("originFull").toString();
        originFullFinal = By.xpath(String.format("//span[contains(.,'%s')]", originFull));

        destinationAuto = vuelo.get("destinationAuto").toString();
        destinationFull = vuelo.get("destinationFull").toString();
        destinationFullFinal = By.xpath(String.format("//span[contains(.,'%s')]", destinationFull));

        startDate = Integer.parseInt(vuelo.get("startDate").toString());
        endDate = Integer.parseInt(vuelo.get("endDate").toString());

        adults = Integer.parseInt(vuelo.get("adults").toString());
        childs = Integer.parseInt(vuelo.get("childs").toString());

        flightClass = vuelo.get("flightClass").toString();
    }

    @Test
    public void vueloReservationFirstOptionFlow() throws InterruptedException {
        getVueloDataObject("miami_1adult_1week");

        vueloFlow.waitForVisibilityOfElementLocated(driver, 15, BaseFlowMap.VUELOS_ICO.getBy());
        vueloFlow.clickOn(driver, BaseFlowMap.VUELOS_ICO.getBy());

        vueloFlow.enterText(driver, originAuto, VueloFlowMap.ORIGIN_FLIGHTS_TXT.getBy());
        vueloFlow.waitForVisibilityOfElementLocated(driver, 10, originFullFinal);
        vueloFlow.selectFromAutoCompleteSuggestions(driver, originFullFinal);

        vueloFlow.enterText(driver, destinationAuto, VueloFlowMap.DESTINATION_FLIGHTS_TXT.getBy());
        vueloFlow.waitForVisibilityOfElementLocated(driver, 10, destinationFullFinal);
        vueloFlow.selectFromAutoCompleteSuggestions(driver, destinationFullFinal);

        vueloFlow.selectDateFromCalendar(driver, VueloFlowMap.VUELO_FECHA_SALIDA_CAL.getBy(), startDate);
        vueloFlow.selectDateFromCalendar(driver, VueloFlowMap.VUELO_FECHA_REGRESO_CAL.getBy(), endDate);

        numPassengers = vueloFlow.selectPassenger(driver, adults, childs);

        vueloFlow.selectFlightClass(driver, flightClass);

        vueloFlow.clickOn(driver, BaseFlowMap.BUSCAR_BTN.getBy());

        if (vueloFlow.nothingFound(driver)) {
            System.out.println("Nothing Found: VUELOS");
        } else {
            vueloFlow.doVueloReservationFlow(driver);
            vueloFlow.paymentPage.populatePaymentInfo(driver, numPassengers);
        }
    }

}