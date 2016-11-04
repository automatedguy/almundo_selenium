package com.almundo.browser.automation.pages;

import com.almundo.browser.automation.locators.flows.BaseFlowMap;
import com.almundo.browser.automation.locators.flows.VueloHotelFlowMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by gabrielcespedes on 01/11/16.
 */
public class VueloHotelPage {

    private static WebElement element = null;



    public static WebElement originFlightTxt(WebDriver driver){
        element = driver.findElement(VueloHotelFlowMap.ORIGIN_FLIGHTS_TXT.getBy());
        return element;
    }

    public static WebElement destinationFlightTxt(WebDriver driver){
        element = driver.findElement(VueloHotelFlowMap.DESTINATION_FLIGHTS_TXT.getBy());
        return element;
    }

    public static WebElement buscarBtn(WebDriver driver){
        element = driver.findElement(BaseFlowMap.BUSCAR_BTN.getBy());
        return element;
    }

    public static WebElement continuarBtn(WebDriver driver){
        element = driver.findElement(VueloHotelFlowMap.CONTINUAR_BTN.getBy());
        return element;
    }

    public static WebElement verHabitacionBtn(WebDriver driver){
        element = driver.findElement(VueloHotelFlowMap.VER_HABITACION_BTN.getBy());
        return element;
    }

    public static WebElement comprarBtn(WebDriver driver){
        element = driver.findElement(VueloHotelFlowMap.COMPRAR_BTN.getBy());
        return element;
    }
}
