package com.almundo.browser.automation.locators.pages;

import org.openqa.selenium.By;

/**
 * Created by gabrielcespedes on 13/10/16.
 */
public enum LandingPageMap {
    ARGENTINA_LINK(By.linkText("Argentina")),
    COLOMBIA_LINK(By.linkText("Colombia")),
    MEXICO_LINK(By.linkText("Mexico"));
    private By name;
    LandingPageMap(By locator) {this.name = locator; }
    public By getBy() { return name; }
}
