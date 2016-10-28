package com.almundo.browser.automation.locators;

import org.openqa.selenium.By;

/**
 * Created by gabrielcespedes on 13/10/16.
 */
public enum HomePageMap {
    HOTELES_ICO(By.cssSelector("span.icon.hotels")),
    VUELOS_ICO(By.cssSelector("span.icon.flights")),
    VUELO_HOTEL_ICO(By.cssSelector("span.icon.trips")),
    INGRESAR(By.cssSelector("span.ng-binding.ng-scope")),
    PAQUETES_ICO(By.cssSelector("span.icon.packages")),
    BUSCAR_BTN(By.xpath("//button[contains(.,'Buscar')]"));
    private By name;
    HomePageMap(By locator) {this.name = locator; }
    public By getBy() { return name; }
}
