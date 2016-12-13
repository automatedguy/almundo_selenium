package com.almundo.browser.automation.flows;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.locators.flows.VueloFlowMap;
import com.almundo.browser.automation.pages.PaymentPage.PaymentPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class VueloFlow extends TestBaseSetup {

    public VueloFlow(WebDriver driver) {
        super.driver = driver;
    }

    public PaymentPage doVueloReservationFlow(WebDriver driver) throws InterruptedException {

        PageUtils.waitElementLocatedforVisibility(driver, VueloFlowMap.TICKET_IDA_RDB.getBy(), 60, "Ticket Ida radio button");

        PageUtils.clickOn(driver, VueloFlowMap.TICKET_IDA_RDB.getBy());
        PageUtils.clickOn(driver, VueloFlowMap.TICKET_VUELTA_RDB.getBy());
        PageUtils.clickOn(driver, VueloFlowMap.COMPRAR_BTN.getBy());

        return new PaymentPage(driver);
    }

}
