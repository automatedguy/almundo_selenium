package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.flows.VueloHotelFlow;
import com.almundo.browser.automation.locators.flows.BaseFlowMap;
import com.almundo.browser.automation.locators.flows.VueloHotelFlowMap;
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
        vueloHotelFlow.waitForVisibilityOfElementLocated(driver, 10, ORIGIN_FULL_PAR);
        vueloHotelFlow.selectFromAutoCompleteSuggestions(driver, ORIGIN_FULL_PAR);

        vueloHotelFlow.enterText(driver, destinationAutoComplete, VueloHotelFlowMap.DESTINATION_FLIGHTS_TXT.getBy());
        vueloHotelFlow.waitForVisibilityOfElementLocated(driver, 10, DESTINATION_FULL_PAR);
        vueloHotelFlow.selectFromAutoCompleteSuggestions(driver, DESTINATION_FULL_PAR);

        vueloHotelFlow.selectDateFromCalendar(driver, VueloHotelFlowMap.TRIPS_FECHA_SALIDA_CAL.getBy(), departureDate);
        vueloHotelFlow.selectDateFromCalendar(driver, VueloHotelFlowMap.TRIPS_FECHA_REGRESO_CAL.getBy(), returnDate);

        vueloHotelFlow.clickOn(driver, BaseFlowMap.BUSCAR_BTN.getBy());

        if(vueloHotelFlow.nothingFound(driver)){
            System.out.println("Nothing Found: VUELO + HOTEL");
        }
        else {
            vueloHotelFlow.doVueloHotelReservationFlow(driver);
            vueloHotelFlow.paymentPage.populatePaymentInfo(driver, numPassengers);
        }
    }
}