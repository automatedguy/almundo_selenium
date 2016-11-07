package com.almundo.browser.automation.flows;

import com.almundo.browser.automation.base.PageBaseSetup;
import com.almundo.browser.automation.locators.flows.VueloHotelFlowMap;
import org.openqa.selenium.WebDriver;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class VueloHotelFlow extends PageBaseSetup {

    public String TRIPS_FECHA_SALIDA_CAL = "departure-trips";
    public String TRIPS_FECHA_REGRESO_CAL = "arrival-trips";

    public VueloHotelFlow(WebDriver driver) {
        super.driver = driver;
    }

    public VueloHotelFlow doVueloHotelReservationFlow(WebDriver driver) throws InterruptedException {

        waitForVisibilityOfElementLocated(driver, 30, VueloHotelFlowMap.CONTINUAR_BTN.getBy());
        clickOn(driver, VueloHotelFlowMap.CONTINUAR_BTN.getBy());

        waitForVisibilityOfElementLocated(driver, 30, VueloHotelFlowMap.VER_HABITACION_BTN.getBy());
        Thread.sleep(7000);
        clickOn(driver, VueloHotelFlowMap.VER_HABITACION_BTN.getBy());

        waitForVisibilityOfElementLocated(driver, 30, VueloHotelFlowMap.COMPRAR_BTN.getBy());
        Thread.sleep(1000);
        clickOn(driver, VueloHotelFlowMap.COMPRAR_BTN.getBy());

        return this;
    }

}
