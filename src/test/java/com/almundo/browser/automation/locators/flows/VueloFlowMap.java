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
    COMPRAR_BTN(By.xpath("//span[contains(.,'Comprar')]")),
    VUELO_FECHA_SALIDA_CAL(By.id("departure-flights")),
    VUELO_FECHA_REGRESO_CAL(By.id("arrival-flights")),

    // flight class options dll
    CLASE_DDL(By.name("class-flights")),

    //passengers-distribution
    SUB_ADULT_BTN(By.cssSelector(".row-adults>.sub")),
    ADD_ADULT_BTN(By.cssSelector(".row-adults>.add")),
    SUB_CHILD_BTN(By.cssSelector(".row-youngers>.sub")),
    ADD_CHILD_BTN(By.cssSelector(".row-youngers>.add"));

    private By name;
    VueloFlowMap(By locator) {this.name = locator; }
    public By getBy() { return name; }
}