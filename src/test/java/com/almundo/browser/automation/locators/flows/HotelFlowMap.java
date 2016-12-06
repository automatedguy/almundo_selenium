package com.almundo.browser.automation.locators.flows;

import org.openqa.selenium.By;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public enum HotelFlowMap {

    VER_HOTEL_BTN(By.cssSelector("a.button.button--lg.button--secondary.button--block.button-detail")),
    VER_HABITACIONES_BTN(By.cssSelector(".button.button--lg.button--secondary")),
    RESERVAR_AHORA_BTN(By.xpath("//span[contains(.,'Reservar')]"));

    private By name;
    HotelFlowMap(By locator) {this.name = locator; }
    public By getBy() { return name; }
}
