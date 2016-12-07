package com.almundo.browser.automation.flows;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.locators.flows.VueloHotelFlowMap;
import com.almundo.browser.automation.pages.PaymentPage.PaymentPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class VueloHotelFlow extends TestBaseSetup {

    public VueloHotelFlow(WebDriver driver) {
        super.driver = driver;
    }

    public PaymentPage doVueloHotelReservationFlow(WebDriver driver) throws InterruptedException {

        PageUtils.waitForVisibilityOfElementLocated(driver, 30, VueloHotelFlowMap.CONTINUAR_BTN.getBy());
        PageUtils.clickOn(driver, VueloHotelFlowMap.CONTINUAR_BTN.getBy());

        PageUtils.waitForVisibilityOfElementLocated(driver, 60, VueloHotelFlowMap.VER_HABITACION_BTN.getBy());
        PageUtils.waitForElementToBeClickcable(driver, 60, VueloHotelFlowMap.VER_HABITACION_BTN.getBy());

        WebElement verHabitacionBtn = driver.findElement(VueloHotelFlowMap.VER_HABITACION_BTN.getBy());
        PageUtils.waitForElement(verHabitacionBtn, 60, 1000);
        verHabitacionBtn.click();

        WebElement comprarBtn = driver.findElement(VueloHotelFlowMap.COMPRAR_BTN.getBy());
        PageUtils.waitForElement(comprarBtn, 10, 1000);
        PageUtils.clickOn(driver, VueloHotelFlowMap.COMPRAR_BTN.getBy());

        return new PaymentPage(driver);
    }

}
