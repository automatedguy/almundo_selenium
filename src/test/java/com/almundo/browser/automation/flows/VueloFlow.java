package com.almundo.browser.automation.flows;

import com.almundo.browser.automation.base.PageBaseSetup;
import com.almundo.browser.automation.locators.flows.VueloFlowMap;
import com.almundo.browser.automation.pages.PaymentPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class VueloFlow extends PageBaseSetup {

    public VueloFlow(WebDriver driver) {
        super.driver = driver;
    }

    public PaymentPage paymentPage = new PaymentPage(driver);

    public VueloFlow doVueloReservationFlow(WebDriver driver) throws InterruptedException {

        waitForVisibilityOfElementLocated(driver, 60,VueloFlowMap.TICKET_IDA_RDB.getBy());

        WebElement ticketsRadioButton = driver.findElement(VueloFlowMap.TICKET_IDA_RDB.getBy());
        waitForElement(ticketsRadioButton, 60, 1000);

        clickOn(driver, VueloFlowMap.TICKET_IDA_RDB.getBy());
        clickOn(driver, VueloFlowMap.TICKET_VUELTA_RDB.getBy());
        clickOn(driver, VueloFlowMap.COMPRAR_BTN.getBy());

        return this;
    }
}
