package com.almundo.browser.automation.locators.testsmaps;

import org.openqa.selenium.By;

import static com.almundo.browser.automation.base.TestBaseSetup.destinationFullTextStr;
import static com.almundo.browser.automation.base.TestBaseSetup.originFullTextStr;

/**
 * Created by gabrielcespedes on 02/11/16.
 */
public enum TestInputMap {
    ORIGIN_FULL_PAR(By.xpath(originFullTextStr)),
    DESTINATION_FULL_PAR(By.xpath(destinationFullTextStr));
    private By name;
    TestInputMap(By locator) {this.name = locator; }
    public By getBy() { return name; }
}
