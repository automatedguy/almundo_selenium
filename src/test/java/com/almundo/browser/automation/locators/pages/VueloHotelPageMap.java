package com.almundo.browser.automation.locators.pages;

import org.openqa.selenium.By;

import static com.almundo.browser.automation.base.TestBaseSetup.destinationFullTextStr;
import static com.almundo.browser.automation.base.TestBaseSetup.originFullTextStr;

/**
 * Created by gabrielcespedes on 01/11/16.
 */
public enum VueloHotelPageMap {
    ORIGIN_FLIGHTS_TXT(By.id("origin-trips")),
    DESTINATION_FLIGHTS_TXT(By.id("destination-trips")),

    ORIGIN_FULL_PAR(By.xpath(originFullTextStr)),
    DESTINATION_FULL_PAR(By.xpath(destinationFullTextStr));

    private By name;
    VueloHotelPageMap(By locator) {this.name = locator; }
    public By getBy() { return name; }

}
