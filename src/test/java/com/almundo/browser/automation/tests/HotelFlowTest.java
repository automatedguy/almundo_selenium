package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.flows.HotelFlow;
import com.almundo.browser.automation.locators.flows.BaseFlowMap;
import com.almundo.browser.automation.locators.flows.HotelFlowMap;
import com.almundo.browser.automation.pages.PaymentPage;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 04/11/16.
 */


public class HotelFlowTest extends TestBaseSetup {

    public HotelFlow hotelFlow = new HotelFlow(driver);

    @Test
    public void hotelReservationFirstOptionFlow() throws InterruptedException {

        hotelFlow.waitForVisibilityOfElementLocated(driver, 15, BaseFlowMap.HOTELES_ICO.getBy());
        hotelFlow.clickOn(driver, BaseFlowMap.HOTELES_ICO.getBy());

        hotelFlow.enterText(driver, destinationAutoComplete, HotelFlowMap.DESTINATION_TXT.getBy());
        hotelFlow.waitForVisibilityOfElementLocated(driver, 10, DESTINATION_FULL_PAR);
        hotelFlow.selectFromAutoCompleteSuggestions(driver, DESTINATION_FULL_PAR);

        hotelFlow.selectDateFromCalendar(driver, HotelFlowMap.HOTEL_FECHA_SALIDA_CAL.getBy(), departureDate);
        hotelFlow.selectDateFromCalendar(driver, HotelFlowMap.HOTEL_FECHA_REGRESO_CAL.getBy(), returnDate);

        hotelFlow.clickOn(driver, BaseFlowMap.BUSCAR_BTN.getBy());

        hotelFlow.doHotelReservationFlow(driver);

        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.populatePaymentInfo(driver, numPassengers);
    }
}
