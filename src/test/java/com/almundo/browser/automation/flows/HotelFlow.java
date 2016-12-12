package com.almundo.browser.automation.flows;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.locators.flows.HotelFlowMap;
import com.almundo.browser.automation.pages.PaymentPage.PaymentPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class HotelFlow extends TestBaseSetup {

    public HotelFlow(WebDriver driver) {
        super.driver = driver;
    }

    public PaymentPage doHotelReservationFlow(WebDriver driver) throws InterruptedException {
        boolean exit = false;

        try {
            //PageUtils.waitForVisibilityOfElementLocated(driver, 30, HotelFlowMap.VER_HOTEL_BTN.getBy());
            PageUtils.waitElementForClickable(driver, HotelFlowMap.VER_HOTEL_BTN.getBy(), 10, "Ver Hotel button");
            PageUtils.clickOn(driver, HotelFlowMap.VER_HOTEL_BTN.getBy());
        } catch (TimeoutException timeOut) {
            //PageUtils.waitForVisibilityOfElementLocated(driver, 30, HotelFlowMap.VER_HABITACIONES_BTN.getBy());
            PageUtils.waitElementForClickable(driver, HotelFlowMap.VER_HABITACIONES_BTN.getBy(), 10, "Ver Habitaciones button");
            PageUtils.clickOn(driver, HotelFlowMap.VER_HABITACIONES_BTN.getBy());
        }

        try {
            //PageUtils.waitForVisibilityOfElementLocated(driver, 30, HotelFlowMap.VER_HABITACIONES_BTN.getBy());
            PageUtils.waitElementForClickable(driver, By.cssSelector(".price-total--ctn>div>a"), 20, "Ver Habitaciones button");
            PageUtils.clickOn(driver, HotelFlowMap.VER_HABITACIONES_BTN.getBy());
        } catch (TimeoutException timeOut) {
            //PageUtils.waitForVisibilityOfElementLocated(driver, 20, HotelFlowMap.RESERVAR_AHORA_BTN.getBy());
            PageUtils.waitElementForClickable(driver, HotelFlowMap.RESERVAR_AHORA_BTN.getBy(), 10, "Reservar Ahora button");
            PageUtils.clickOn(driver, HotelFlowMap.RESERVAR_AHORA_BTN.getBy());
        }

        //WebElement reservarAhora = driver.findElement(HotelFlowMap.RESERVAR_AHORA_BTN.getBy());
        logger.info("Waiting for RESERVAR button...");
        PageUtils.waitElementForClickable(driver, By.cssSelector("detail-cluster:nth-of-type(1)>.detail-cluster>div:nth-of-type(3)>.button"), 20, "Reservar Ahora button");
        WebElement reservarAhora = driver.findElement(By.cssSelector("detail-cluster:nth-of-type(1)>.detail-cluster>div:nth-of-type(3)>.button"));
        logger.info("clicking on RESERVAR button...");
        reservarAhora.click();

//        do {
//            try {
//                logger.info("Waiting for RESERVAR button...");
//                PageUtils.waitElementForClickable(driver, By.cssSelector("detail-cluster:nth-of-type(1)>.detail-cluster>div:nth-of-type(3)>.button"), 20, "Reservar Ahora button");
//                WebElement reservarAhora = driver.findElement(By.cssSelector("detail-cluster:nth-of-type(1)>.detail-cluster>div:nth-of-type(3)>.button"));
//                logger.info("clicking on RESERVAR button...");
//                reservarAhora.click();
//
//            } catch (Exception someException) {
//                logger.warn("RESERVAR not found.");
//                exit = true;
//            }
//        } while (!exit);
        return new PaymentPage(driver);
    }

}