package com.almundo.browser.automation.locators.testsmaps;

import com.almundo.browser.automation.base.TestBaseSetup;
import org.openqa.selenium.By;

/**
 * Created by gabrielcespedes on 02/11/16.
 */
public enum TestInputMap {
    ORIGIN_FULL_PAR(By.xpath(TestBaseSetup.originFullTextStr)),
    DESTINATION_FULL_PAR(By.xpath(TestBaseSetup.destinationFullTextStr));
    private By name;
    TestInputMap(By locator) {this.name = locator; }
    public By getBy() { return name; }
}
