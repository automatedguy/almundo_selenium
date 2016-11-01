package com.almundo.browser.automation.locators.pages;

import org.openqa.selenium.By;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public enum HotelesPageMap {

    DESTINATION_TXT(By.id("destination-hotels")),
    VER_HOTEL_BTN(By.cssSelector("a.button.button--lg.button--secondary.button--block.button-detail")),
    VER_HABITACIONES_BTN(By.cssSelector("a.button.button--lg.button--secondary.ng-scope")),
    RESERVAR_AHORA_BTN(By.cssSelector("button.button.button--lg.button--secondary.ng-scope")),
    RESERVAR_AHORA2_BTN(By.cssSelector("span.button.button--secondary.button--lg.button--block"));

    private By name;
    HotelesPageMap(By locator) {this.name = locator; }
    public By getBy() { return name; }
}
