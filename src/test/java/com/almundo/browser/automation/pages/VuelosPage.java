package com.almundo.browser.automation.pages;

import com.almundo.browser.automation.locators.pages.HomePageMap;
import com.almundo.browser.automation.locators.pages.VuelosPageMap;
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

    public static WebElement tipoVueloDdl(WebDriver driver){
        element = driver.findElement(VuelosPageMap.TIPO_DE_VUELO_DDL.getBy());
        return element;
    }

    public static WebElement idaRdb(WebDriver driver){
        element = driver.findElement(VuelosPageMap.TICKET_IDA_RDB.getBy());
        return element;
    }

    public static WebElement vueltaRdb(WebDriver driver){
        element = driver.findElement(VuelosPageMap.TICKET_VUELTA_RDB.getBy());
        return element;
    }

    public static WebElement comprarBtn(WebDriver driver){
        element = driver.findElement(VuelosPageMap.COMPRAR_BTN.getBy());
        return element;
    }

    public static void comprarTickets(WebDriver driver){
        idaRdb(driver).click();
        vueltaRdb(driver).click();
        comprarBtn(driver).click();
    }
}
