package com.almundo.browser.automation.locators.components;

import org.openqa.selenium.By;

/**
 * Created by gabrielcespedes on 31/10/16.
 */
public enum CalendarComponentMap {
    CALENDARIO_SALIDA(By.xpath("//input[@id='departure-flights']")),
    CALENDARIO_SALIDA_TRIANGLE(By.xpath("//span[@class='ui-icon ui-icon-circle-triangle-e']")),
    CALENDARIO_SALIDA_FECHA(By.cssSelector("a.ui-state-default")),

    CALENDARIO_REGRESO(By.xpath("//input[@id='arrival-flights']")),
    CALENDARIO_REGRESO_TRIANGLE(By.xpath("//span[@class='ui-icon ui-icon-circle-triangle-e']")),
    CALENDARIO_REGRESO_FECHA(By.cssSelector("a.ui-state-default"));

    private By name;
    CalendarComponentMap(By locator) {this.name = locator; }
    public By getBy() { return name; }
}
