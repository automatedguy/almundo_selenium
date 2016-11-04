package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.flows.VueloHotelFlow;
import com.almundo.browser.automation.locators.flows.BaseFlowMap;
import com.almundo.browser.automation.locators.flows.VueloHotelFlowMap;
import com.almundo.browser.automation.locators.testsmaps.testParametersMap;
import com.almundo.browser.automation.pages.PaymentPage;
import com.almundo.browser.automation.utils.PageUtils;
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

        if(PageUtils.nothingFound(driver)){
            System.out.println("Nothing Found: VUELO + HOTEL");
        }
        else {

            vueloHotelFlow.waitForVisibilityOfElementLocated(driver, 30, VueloHotelFlowMap.CONTINUAR_BTN.getBy());
            vueloHotelFlow.clickOn(driver, VueloHotelFlowMap.CONTINUAR_BTN.getBy());

            vueloHotelFlow.waitForVisibilityOfElementLocated(driver, 30, VueloHotelFlowMap.VER_HABITACION_BTN.getBy());
            Thread.sleep(7000);
            vueloHotelFlow.clickOn(driver, VueloHotelFlowMap.VER_HABITACION_BTN.getBy());

            vueloHotelFlow.waitForVisibilityOfElementLocated(driver, 30, VueloHotelFlowMap.COMPRAR_BTN.getBy());
            Thread.sleep(1000);
            vueloHotelFlow.clickOn(driver, VueloHotelFlowMap.COMPRAR_BTN.getBy());

            PaymentPage paymentPage = new PaymentPage(driver);
            paymentPage.populatePaymentInfo(driver);

        }

        Thread.sleep(7000);
    }
}
