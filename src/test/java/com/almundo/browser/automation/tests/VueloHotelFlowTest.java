package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.flows.VueloHotelFlow;
import com.almundo.browser.automation.locators.flows.BaseFlowMap;
import com.almundo.browser.automation.locators.flows.VueloHotelFlowMap;
import com.almundo.browser.automation.pages.PaymentPage;
import com.almundo.browser.automation.utils.MyRetryAnalyzer;
import com.almundo.browser.automation.utils.MyTestListenerAdapter;
import com.almundo.browser.automation.utils.PageUtils;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

@Listeners({MyTestListenerAdapter.class})
public class VueloHotelFlowTest extends TestBaseSetup {

    public VueloHotelFlow vueloHotelFlow = new VueloHotelFlow(driver);

    @Test(retryAnalyzer=MyRetryAnalyzer.class)
    public void vueloHotelReservationFirstOptionFlow() throws InterruptedException {

        vueloHotelFlow.waitForVisibilityOfElementLocated(driver, 15, BaseFlowMap.VUELO_HOTEL_ICO.getBy());
        vueloHotelFlow.clickOn(driver, BaseFlowMap.VUELO_HOTEL_ICO.getBy());

        vueloHotelFlow.enterText(driver, originAutoComplete, VueloHotelFlowMap.ORIGIN_FLIGHTS_TXT.getBy());
        vueloHotelFlow.waitForVisibilityOfElementLocated(driver, 10, ORIGIN_FULL_PAR);
        vueloHotelFlow.selectFromAutoCompleteSuggestions(driver, ORIGIN_FULL_PAR);

        vueloHotelFlow.enterText(driver, destinationAutoComplete, VueloHotelFlowMap.DESTINATION_FLIGHTS_TXT.getBy());
        vueloHotelFlow.waitForVisibilityOfElementLocated(driver, 10, DESTINATION_FULL_PAR);
        vueloHotelFlow.selectFromAutoCompleteSuggestions(driver, DESTINATION_FULL_PAR);

        vueloHotelFlow.selectDateFromCalendar(driver, vueloHotelFlow.TRIPS_FECHA_SALIDA_CAL, departureDate);
        vueloHotelFlow.selectDateFromCalendar(driver, vueloHotelFlow.TRIPS_FECHA_REGRESO_CAL, returnDate);

        vueloHotelFlow.clickOn(driver, BaseFlowMap.BUSCAR_BTN.getBy());

        if(PageUtils.nothingFound(driver)){

            System.out.println("Nothing Found: VUELO + HOTEL");

        }
        else {

            vueloHotelFlow.doVueloHotelReservationFlow(driver);

            PaymentPage paymentPage = new PaymentPage(driver);
            paymentPage.populatePaymentInfo(driver, numPassengers);

        }
    }
}
