package com.almundo.browser.automation.pages;

import com.almundo.browser.automation.locators.pages.HomePageMap;
import com.almundo.browser.automation.locators.pages.VueloHotelPageMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by gabrielcespedes on 01/11/16.
 */
public class VueloHotelPage {

    private static WebElement element = null;

    public static WebElement originFlightTxt(WebDriver driver){
        element = driver.findElement(VueloHotelPageMap.ORIGIN_FLIGHTS_TXT.getBy());
        return element;
    }

    public static WebElement destinationFlightTxt(WebDriver driver){
        element = driver.findElement(VueloHotelPageMap.DESTINATION_FLIGHTS_TXT.getBy());
        return element;
    }

    public static WebElement buscarBtn(WebDriver driver){
        element = driver.findElement(HomePageMap.BUSCAR_BTN.getBy());
        return element;
    }
}
