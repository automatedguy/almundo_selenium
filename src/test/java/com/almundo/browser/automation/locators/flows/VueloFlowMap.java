package com.almundo.browser.automation.locators.flows;

import org.openqa.selenium.By;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public enum VueloFlowMap {
    TICKET_IDA_RDB(By.id("option-inbound-00")),
    TICKET_VUELTA_RDB(By.id("option-inbound-01")),
    COMPRAR_BTN(By.xpath("//span[contains(.,'Comprar')]")),

    // flight class options dll
    CLASE_DDL(By.name("class-flights"));

    private By name;
    VueloFlowMap(By locator) {this.name = locator; }
    public By getBy() { return name; }
}