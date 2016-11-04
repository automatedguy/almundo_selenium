package com.almundo.browser.automation.flows;

import com.almundo.browser.automation.base.PageBaseSetup;
import com.almundo.browser.automation.locators.flows.HotelFlowMap;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class HotelFlow extends PageBaseSetup {
    public HotelFlow(WebDriver driver) {
        super.driver = driver;
    }

    boolean exit = false;

    public HotelFlow doHotelReservationFlow(WebDriver driver) {

        try {
            waitForVisibilityOfElementLocated(driver, 30, HotelFlowMap.VER_HOTEL_BTN.getBy());
            clickOn(driver, HotelFlowMap.VER_HOTEL_BTN.getBy());
        } catch (TimeoutException timeOut) {
            waitForVisibilityOfElementLocated(driver, 30, HotelFlowMap.VER_HABITACIONES_BTN.getBy());
            clickOn(driver, HotelFlowMap.VER_HABITACIONES_BTN.getBy());
        }

        try {
            waitForVisibilityOfElementLocated(driver, 30, HotelFlowMap.VER_HABITACIONES_BTN.getBy());
            clickOn(driver, HotelFlowMap.VER_HABITACIONES_BTN.getBy());
        } catch (TimeoutException timeOut) {
            waitForVisibilityOfElementLocated(driver, 20, HotelFlowMap.RESERVAR_AHORA_BTN.getBy());
            clickOn(driver, HotelFlowMap.RESERVAR_AHORA_BTN.getBy());
        }

        do {
            try {
                waitForVisibilityOfElementLocated(driver, 20, HotelFlowMap.RESERVAR_AHORA_BTN.getBy());
                clickOn(driver, HotelFlowMap.RESERVAR_AHORA_BTN.getBy());
            } catch (TimeoutException timeOut) {
                System.out.println("The other reservation flow :)");
                exit = true;
            }
        } while (!exit);

        driver.navigate().refresh();
        return this;
    }
}