package com.almundo.browser.automation.pages;

import com.almundo.browser.automation.locators.HomePageMap;
import com.almundo.browser.automation.locators.VuelosPageMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public class VuelosPage {

    private static WebElement element = null;

    public static WebElement vuelosPageOriginFlightsTxtBox(WebDriver driver){
        element = driver.findElement(VuelosPageMap.ORIGIN_FLIGHTS_TXT.getBy());
        return element;
    }

    public static WebElement vuelosPageDestinationFlightsTxtBox(WebDriver driver){
        element = driver.findElement(VuelosPageMap.DESTINATION_FLIGHTS_TXT.getBy());
        return element;
    }

    public static WebElement vuelosPageBuscarBtn(WebDriver driver){
        element = driver.findElement(HomePageMap.BUSCAR_BTN.getBy());
        return element;
    }

}
