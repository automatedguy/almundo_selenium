package com.almundo.browser.automation.locators;

import org.openqa.selenium.By;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public enum VuelosPageMap {
    ORIGIN_FLIGHTS_TXT(By.id("origin-flights")),
    DESTINATION_FLIGHTS_TXT(By.id("destination-flights"));
    private By name;
    VuelosPageMap(By locator) {this.name = locator; }
    public By getBy() { return name; }
}