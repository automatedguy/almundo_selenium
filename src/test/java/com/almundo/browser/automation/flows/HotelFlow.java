package com.almundo.browser.automation.flows;

import com.almundo.browser.automation.base.PageBaseSetup;
import com.almundo.browser.automation.locators.flows.HotelFlowMap;
import com.almundo.browser.automation.pages.PaymentPage.PaymentPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class HotelFlow extends PageBaseSetup {

    public HotelFlow(WebDriver driver) {
        super.driver = driver;
    }

    public PaymentPage paymentPage = new PaymentPage(driver);

    boolean exit = false;

    public HotelFlow doHotelReservationFlow(WebDriver driver) throws InterruptedException {

        try {
            PageUtils.waitForVisibilityOfElementLocated(driver, 30, HotelFlowMap.VER_HOTEL_BTN.getBy());
            PageUtils.clickOn(driver, HotelFlowMap.VER_HOTEL_BTN.getBy());
        } catch (TimeoutException timeOut) {
            PageUtils.waitForVisibilityOfElementLocated(driver, 30, HotelFlowMap.VER_HABITACIONES_BTN.getBy());
            PageUtils.clickOn(driver, HotelFlowMap.VER_HABITACIONES_BTN.getBy());
        }

        try {
            PageUtils.waitForVisibilityOfElementLocated(driver, 30, HotelFlowMap.VER_HABITACIONES_BTN.getBy());
            PageUtils.clickOn(driver, HotelFlowMap.VER_HABITACIONES_BTN.getBy());
        } catch (TimeoutException timeOut) {
            PageUtils.waitForVisibilityOfElementLocated(driver, 20, HotelFlowMap.RESERVAR_AHORA_BTN.getBy());
            PageUtils.clickOn(driver, HotelFlowMap.RESERVAR_AHORA_BTN.getBy());
        }

        WebElement reservarAhora = driver.findElement(HotelFlowMap.RESERVAR_AHORA_BTN.getBy());

        do {
            try {
                logger.info("Waiting for RESERVAR button...");
                PageUtils.waitForElement(reservarAhora, 10, 1000);
                logger.info("clicking on RESERVAR button...");
                reservarAhora.click();

            } catch (Exception someException) {
                logger.warn("RESERVAR not found.");
                exit = true;
            }
        } while (!exit);
        return this;
    }
}