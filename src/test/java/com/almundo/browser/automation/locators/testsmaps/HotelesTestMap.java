package com.almundo.browser.automation.locators.testsmaps;

import org.openqa.selenium.By;

/**
 * Created by gabrielcespedes on 24/10/16.
 */
public enum HotelesTestMap {
    DESTINATION_CITY_SUG(By.xpath("//*[@id=\"main-content\"]/div[1]/am-searchbox/div/div/form/div[1]/div[2]/div[1]/div/am-autocomplete/div/ul/li[1]/span"));
    private By name;
    HotelesTestMap(By locator) {this.name = locator; }
    public By getBy() { return name; }
}