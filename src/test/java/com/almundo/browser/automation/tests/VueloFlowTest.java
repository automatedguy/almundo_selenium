package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.flows.VueloFlow;
import com.almundo.browser.automation.locators.flows.BaseFlowMap;
import com.almundo.browser.automation.locators.flows.VueloFlowMap;
import com.almundo.browser.automation.pages.PaymentPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class VueloFlowTest extends TestBaseSetup {

    public VueloFlow vueloFlow = new VueloFlow(driver);

    @Test
    public void vueloReservationFirstOptionFlow() throws InterruptedException {

        vueloFlow.waitForVisibilityOfElementLocated(driver, 15, BaseFlowMap.VUELOS_ICO.getBy());
        vueloFlow.clickOn(driver, BaseFlowMap.VUELOS_ICO.getBy());

        vueloFlow.enterText(driver, originAutoComplete, VueloFlowMap.ORIGIN_FLIGHTS_TXT.getBy());
        vueloFlow.waitForVisibilityOfElementLocated(driver, 10, ORIGIN_FULL_PAR);
        vueloFlow.selectFromAutoCompleteSuggestions(driver, ORIGIN_FULL_PAR);

        vueloFlow.enterText(driver, destinationAutoComplete, VueloFlowMap.DESTINATION_FLIGHTS_TXT.getBy());
        vueloFlow.waitForVisibilityOfElementLocated(driver, 10, DESTINATION_FULL_PAR);
        vueloFlow.selectFromAutoCompleteSuggestions(driver, DESTINATION_FULL_PAR);

        vueloFlow.selectDateFromCalendar(driver, vueloFlow.VUELO_FECHA_SALIDA_CAL, departureDate);
        vueloFlow.selectDateFromCalendar(driver, vueloFlow.VUELO_FECHA_REGRESO_CAL, returnDate);

        vueloFlow.clickOn(driver, BaseFlowMap.BUSCAR_BTN.getBy());

        numPassengers = numPassengers - 1;

        if (PageUtils.nothingFound(driver)) {

            System.out.println("Nothing Found: VUELOS");

        } else {

            vueloFlow.doVueloReservationFlow(driver);

            PaymentPage paymentPage = new PaymentPage(driver);
            paymentPage.populatePaymentInfo(driver, numPassengers);
        }
    }

}