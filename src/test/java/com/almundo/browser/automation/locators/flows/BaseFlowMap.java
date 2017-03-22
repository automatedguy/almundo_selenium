package com.almundo.browser.automation.locators.flows;

import org.openqa.selenium.By;

/**
 * Created by gabrielcespedes on 13/10/16.
 */
public enum BaseFlowMap {
    HOTELES_ICO(By.cssSelector("span.icon.hotels")),
    VUELOS_ICO(By.cssSelector("span.icon.flights")),
    VUELO_HOTEL_ICO(By.cssSelector("span.icon.trips")),
    INGRESAR(By.cssSelector("span.ng-binding.ng-scope")),
    CALENDAR_CAL(By.xpath("//div[@class='ui-datepicker-title']")),
    CALENDAR_NEXT_CAL(By.xpath("//span[@class='ui-icon ui-icon-circle-triangle-e']")),
    AVAILABLE_DATES_CAL(By.cssSelector(".ui-datepicker-calendar>tbody>tr>td>a")),
    PERSONAS_TXT(By.cssSelector(".search__input")),
    LISTO_BTN(By.cssSelector(".button.button--sm")),
    BUSCAR_BTN(By.xpath("//button[contains(.,'Buscar')]"));
    private By name;
    BaseFlowMap(By locator) {this.name = locator; }
    public By getBy() { return name; }
}
