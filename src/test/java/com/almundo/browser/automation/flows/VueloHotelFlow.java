package com.almundo.browser.automation.flows;

import com.almundo.browser.automation.base.PageBaseSetup;
import com.almundo.browser.automation.locators.flows.VueloHotelFlowMap;
import com.almundo.browser.automation.pages.PaymentPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class VueloHotelFlow extends PageBaseSetup {

    public VueloHotelFlow(WebDriver driver) {
        super.driver = driver;
    }

    public PaymentPage paymentPage = new PaymentPage(driver);

    public VueloHotelFlow doVueloHotelReservationFlow(WebDriver driver) throws InterruptedException {

        waitForVisibilityOfElementLocated(driver, 30, VueloHotelFlowMap.CONTINUAR_BTN.getBy());
        clickOn(driver, VueloHotelFlowMap.CONTINUAR_BTN.getBy());

        waitForVisibilityOfElementLocated(driver, 60, VueloHotelFlowMap.VER_HABITACION_BTN.getBy());
        waitForElementToBeClickcable(driver, 60, VueloHotelFlowMap.VER_HABITACION_BTN.getBy());

        WebElement verHabitacionBtn = driver.findElement(VueloHotelFlowMap.VER_HABITACION_BTN.getBy());
        waitForElement(verHabitacionBtn, 60, 1000);
        verHabitacionBtn.click();

        WebElement comprarBtn = driver.findElement(VueloHotelFlowMap.COMPRAR_BTN.getBy());
        waitForElement(comprarBtn, 10, 1000);
        clickOn(driver, VueloHotelFlowMap.COMPRAR_BTN.getBy());

        return this;
    }

}