package com.almundo.browser.automation.pages;

import com.almundo.browser.automation.locators.HomePageMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by gabrielcespedes on 13/10/16.
 */
public class HomePage {

    private static WebElement element = null;

    public static WebElement ingresarLnk(WebDriver driver){
        element = driver.findElement(HomePageMap.INGRESAR.getBy());
        return element;
    }

    public static WebElement hotelesTab(WebDriver driver){
        element = driver.findElement(HomePageMap.HOTELES_ICO.getBy());
        return element;
    }

    public static WebElement vuelosTab(WebDriver driver){
        element = driver.findElement(HomePageMap.VUELOS_ICO.getBy());
        return element;
    }

    public static WebElement vueloHotelTab(WebDriver driver){
        element = driver.findElement(HomePageMap.VUELO_HOTEL_ICO.getBy());
        return element;
    }

}
