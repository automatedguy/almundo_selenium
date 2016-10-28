package com.almundo.browser.automation.locators;

import org.openqa.selenium.By;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public enum HotelesPageMap {

    DESTINATION_TXT(By.id("destination-hotels")),
    VER_HOTEL_BTN(By.xpath("/html/body/div[1]/main/section/hotel[1]/article/div[2]/div/a")),
    RESERVAR_AHORA_BTN(By.xpath("/html/body/main/header-detail/div[2]/div/div/div[2]/div/best-room-pricebox/div/div/div[2]/button"));

    private By name;
    HotelesPageMap(By locator) {this.name = locator; }
    public By getBy() { return name; }
}
