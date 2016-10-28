package com.almundo.browser.automation.pages;

import com.almundo.browser.automation.locators.HomePageMap;
import com.almundo.browser.automation.locators.HotelesPageMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public class HotelesPage {
    private static WebElement element = null;

    public static WebElement hotelDestinationTxtBox(WebDriver driver){
        element = driver.findElement(HotelesPageMap.DESTINATION_TXT.getBy());
        return element;
    }

    public static WebElement buscarBtn(WebDriver driver){
        element = driver.findElement(HomePageMap.BUSCAR_BTN.getBy());
        return element;
    }

    public static WebElement verHotelBtn(WebDriver driver){
        element = driver.findElement(HotelesPageMap.VER_HOTEL_BTN.getBy());
        return element;
    }

    public static WebElement reservarAhoraBtn(WebDriver driver){
        element = driver.findElement(HotelesPageMap.RESERVAR_AHORA_BTN.getBy());
        return element;
    }

    public static WebElement selectCityFromAutoCompleteSuggestions(WebDriver driver, By city){
        WebElement selectedCity = driver.findElement(city);
        selectedCity.click();
        return element;
    }
}
