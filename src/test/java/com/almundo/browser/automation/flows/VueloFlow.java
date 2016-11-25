package com.almundo.browser.automation.flows;

import com.almundo.browser.automation.base.PageBaseSetup;
import com.almundo.browser.automation.locators.flows.VueloFlowMap;
import com.almundo.browser.automation.pages.PaymentPageSections.PaymentPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

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

        clickOn(driver, VueloFlowMap.TICKET_IDA_RDB.getBy());
        clickOn(driver, VueloFlowMap.TICKET_VUELTA_RDB.getBy());
        clickOn(driver, VueloFlowMap.COMPRAR_BTN.getBy());

        return this;
    }

    public VueloFlow selectFlightClass(WebDriver driver, String flightClass){

         Select claseVueloDdl = new Select(driver.findElement(VueloFlowMap.CLASE_DDL.getBy()));
         claseVueloDdl.selectByVisibleText(flightClass);

        return this;
    }
}
