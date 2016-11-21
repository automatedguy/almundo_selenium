package com.almundo.browser.automation.locators.flows;

import org.openqa.selenium.By;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public enum HotelFlowMap {

    DESTINATION_TXT(By.id("destination-hotels")),
    VER_HOTEL_BTN(By.cssSelector("a.button.button--lg.button--secondary.button--block.button-detail")),
    VER_HABITACIONES_BTN(By.cssSelector(".button.button--lg.button--secondary")),
    RESERVAR_AHORA_BTN(By.xpath("//span[contains(.,'Reservar')]")),

    HOTEL_FECHA_SALIDA_CAL(By.id("checkin-hotels")),
    HOTEL_FECHA_REGRESO_CAL(By.id("checkout-hotels")),

    //rooms-distribution
    SUB_ROOM_BTN(By.cssSelector(".row-rooms>.sub")),
    ADD_ROOM_BTN(By.cssSelector(".row-rooms>.add")),
    SUB_ADULT_BTN(By.cssSelector(".row-room-details>div:nth-of-type(1)>.sub")),
    ADD_ADULT_BTN(By.cssSelector(".row-room-details>div:nth-of-type(1)>.add")),
    SUB_CHILD_BTN(By.cssSelector(".row-room-details>div:nth-of-type(2)>.sub")),
    ADD_CHILD_BTN(By.cssSelector(".row-room-details>div:nth-of-type(2)>.add"));

    private By name;
    HotelFlowMap(By locator) {this.name = locator; }
    public By getBy() { return name; }
}
