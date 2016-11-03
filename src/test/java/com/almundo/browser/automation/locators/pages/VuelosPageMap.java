package com.almundo.browser.automation.locators.pages;

import org.openqa.selenium.By;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public enum VuelosPageMap {
    ORIGIN_FLIGHTS_TXT(By.id("origin-flights")),
    DESTINATION_FLIGHTS_TXT(By.id("destination-flights")),
    TIPO_DE_VUELO_DDL(By.xpath("//select[@name='type-flights']")),
    TICKET_IDA_RDB(By.id("option-inbound-00")),
    TICKET_VUELTA_RDB(By.id("option-inbound-01")),
    // COMPRAR_BTN(By.xpath("//*[@id=\"main-content\"]/div[3]/div[2]/div/p/span"));
    COMPRAR_BTN(By.xpath("//span[contains(.,'Comprar')]"));
    private By name;
    VuelosPageMap(By locator) {this.name = locator; }
    public By getBy() { return name; }
}