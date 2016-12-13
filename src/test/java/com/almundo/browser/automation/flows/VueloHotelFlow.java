package com.almundo.browser.automation.flows;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.locators.flows.VueloHotelFlowMap;
import com.almundo.browser.automation.pages.PaymentPage.PaymentPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class VueloHotelFlow extends TestBaseSetup {

    public VueloHotelFlow(WebDriver driver) {
        super.driver = driver;
    }

    public PaymentPage doVueloHotelReservationFlow(WebDriver driver) throws InterruptedException {

        PageUtils.waitElementForClickable(driver, VueloHotelFlowMap.CONTINUAR_BTN.getBy(), 60, "Continuar button");
        PageUtils.clickOn(driver, VueloHotelFlowMap.CONTINUAR_BTN.getBy());

        //PageUtils.waitElementLocatedforVisibility(driver, VueloHotelFlowMap.VER_HABITACION_BTN.getBy(), 60, "Ver Habitación button");
        //PageUtils.waitForElementToBeClickcable(driver, 60, VueloHotelFlowMap.VER_HABITACION_BTN.getBy());

        //WebElement verHabitacionBtn = driver.findElement(VueloHotelFlowMap.VER_HABITACION_BTN.getBy());
        //PageUtils.waitElementForVisibility(driver, verHabitacionBtn, 30, "Ver Habitación button");

        PageUtils.waitElementForClickable(driver, VueloHotelFlowMap.VER_HABITACION_BTN.getBy(), 30, "Ver Habitación button");
        PageUtils.clickOn(driver, VueloHotelFlowMap.VER_HABITACION_BTN.getBy());
        //PageUtils.waitForElement(verHabitacionBtn, 60, 1000);
        //verHabitacionBtn.click();

        PageUtils.waitElementForClickable(driver, VueloHotelFlowMap.COMPRAR_BTN.getBy(), 30, "Comprar button");
        PageUtils.clickOn(driver, VueloHotelFlowMap.COMPRAR_BTN.getBy());
        //PageUtils.waitForElementToBeClickcable(driver, 30, VueloHotelFlowMap.COMPRAR_BTN.getBy());
        //WebElement comprarBtn = driver.findElement(VueloHotelFlowMap.COMPRAR_BTN.getBy());
        //PageUtils.waitForElement(comprarBtn, 10, 1000);

        return new PaymentPage(driver);
    }

}
