package com.almundo.browser.automation.locators.flows;

import org.openqa.selenium.By;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public enum VueloFlowMap {
    ORIGIN_FLIGHTS_TXT(By.id("origin-flights")),
    DESTINATION_FLIGHTS_TXT(By.id("destination-flights")),
    TICKET_IDA_RDB(By.id("option-inbound-00")),
    TICKET_VUELTA_RDB(By.id("option-inbound-01")),
    COMPRAR_BTN(By.xpath("//span[contains(.,'Comprar')]"));
    private By name;
    VueloFlowMap(By locator) {this.name = locator; }
    public By getBy() { return name; }
}