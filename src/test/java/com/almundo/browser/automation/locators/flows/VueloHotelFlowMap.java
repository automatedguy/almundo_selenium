package com.almundo.browser.automation.locators.flows;

import org.openqa.selenium.By;

/**
 * Created by gabrielcespedes on 01/11/16.
 */
public enum VueloHotelFlowMap {
    ORIGIN_FLIGHTS_TXT(By.id("origin-trips")),
    DESTINATION_FLIGHTS_TXT(By.id("destination-trips")),

    CONTINUAR_BTN(By.id("continue-v2")),
    VER_HABITACION_BTN(By.xpath("//button[contains(.,'Ver habitación')]")),

    COMPRAR_BTN(By.cssSelector("button.select-room-button.button.button--md.button--secondary"));

    private By name;
    VueloHotelFlowMap(By locator) {this.name = locator; }
    public By getBy() { return name; }

}
