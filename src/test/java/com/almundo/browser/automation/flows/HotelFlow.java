package com.almundo.browser.automation.flows;

import com.almundo.browser.automation.base.PageBaseSetup;
import com.almundo.browser.automation.locators.flows.HotelFlowMap;
import com.almundo.browser.automation.pages.PaymentPage;
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

        WebElement reservarAhora = driver.findElement(HotelFlowMap.RESERVAR_AHORA_BTN.getBy());

        do {
            try {
                System.out.println("Now trying to find reservar button ...........");
                waitForElement(reservarAhora, 10, 1000);
                System.out.println("Now clicking on reservar.......................");
                reservarAhora.click();

            } catch (Exception someException) {
                System.out.println("The other reservation flow :)");
                exit = true;
            }
        } while (!exit);
        return this;
    }
}