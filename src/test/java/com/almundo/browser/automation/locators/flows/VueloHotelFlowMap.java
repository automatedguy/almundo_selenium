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
    COMPRAR_BTN(By.cssSelector("button.select-room-button.button.button--md.button--secondary")),
    TRIPS_FECHA_SALIDA_CAL(By.id("departure-trips")),
    TRIPS_FECHA_REGRESO_CAL(By.id("arrival-trips"));

    private By name;
    VueloHotelFlowMap(By locator) {this.name = locator; }
    public By getBy() { return name; }

}
