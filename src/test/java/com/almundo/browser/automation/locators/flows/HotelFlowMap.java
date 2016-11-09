package com.almundo.browser.automation.locators.flows;

import org.openqa.selenium.By;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public enum HotelFlowMap {

    DESTINATION_TXT(By.id("destination-hotels")),
    VER_HOTEL_BTN(By.cssSelector("a.button.button--lg.button--secondary.button--block.button-detail")),
    VER_HABITACIONES_BTN(By.cssSelector("a.button.button--lg.button--secondary.ng-scope")),
    RESERVAR_AHORA_BTN(By.xpath("//span[contains(.,'Reservar')]")),
    HOTEL_FECHA_SALIDA_CAL(By.id("checkin-hotels")),
    HOTEL_FECHA_REGRESO_CAL(By.id("checkout-hotels"));

    private By name;
    HotelFlowMap(By locator) {this.name = locator; }
    public By getBy() { return name; }
}
