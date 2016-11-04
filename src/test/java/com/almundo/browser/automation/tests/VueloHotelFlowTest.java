package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.flows.VueloHotelFlow;
import com.almundo.browser.automation.locators.flows.BaseFlowMap;
import com.almundo.browser.automation.locators.flows.VueloHotelFlowMap;
import com.almundo.browser.automation.locators.testsmaps.testParametersMap;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class VueloHotelFlowTest extends TestBaseSetup {

    public VueloHotelFlow vueloHotelFlow = new VueloHotelFlow(driver);

    @Test
    public void vueloHotelReservationFirstOptionFlow() throws InterruptedException {

        vueloHotelFlow.waitForVisibilityOfElementLocated(driver, 15, BaseFlowMap.VUELO_HOTEL_ICO.getBy());
        vueloHotelFlow.clickOn(driver, BaseFlowMap.VUELO_HOTEL_ICO.getBy());

        vueloHotelFlow.enterText(driver, originAutoComplete, VueloHotelFlowMap.ORIGIN_FLIGHTS_TXT.getBy());
        vueloHotelFlow.waitForVisibilityOfElementLocated(driver, 10, testParametersMap.ORIGIN_FULL_PAR.getBy());
        vueloHotelFlow.selectFromAutoCompleteSuggestions(driver, testParametersMap.ORIGIN_FULL_PAR.getBy());

        vueloHotelFlow.enterText(driver, destinationAutoComplete, VueloHotelFlowMap.DESTINATION_FLIGHTS_TXT.getBy());
        vueloHotelFlow.waitForVisibilityOfElementLocated(driver, 10, testParametersMap.DESTINATION_FULL_PAR.getBy());
        vueloHotelFlow.selectFromAutoCompleteSuggestions(driver, testParametersMap.DESTINATION_FULL_PAR.getBy());

        vueloHotelFlow.selectDateFromCalendar(driver, vueloHotelFlow.TRIPS_FECHA_SALIDA_CAL, departureDate);
        vueloHotelFlow.selectDateFromCalendar(driver, vueloHotelFlow.TRIPS_FECHA_REGRESO_CAL, returnDate);

        vueloHotelFlow.clickOn(driver, BaseFlowMap.BUSCAR_BTN.getBy());

        Thread.sleep(7000);
    }
}
