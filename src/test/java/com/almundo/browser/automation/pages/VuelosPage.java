package com.almundo.browser.automation.pages;

import com.almundo.browser.automation.locators.flows.BaseFlowMap;
import com.almundo.browser.automation.locators.flows.VuelosFlowMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public class VuelosPage {

    private static WebElement element = null;

    public static String VUELOS_FECHA_SALIDA_CAL = "departure-flights";
    public static String VUELOS_FECHA_REGRESO_CAL = "arrival-flights";

    public static WebElement vuelosPageOriginFlightsTxtBox(WebDriver driver){
        element = driver.findElement(VuelosFlowMap.ORIGIN_FLIGHTS_TXT.getBy());
        return element;
    }

    public static WebElement vuelosPageDestinationFlightsTxtBox(WebDriver driver){
        element = driver.findElement(VuelosFlowMap.DESTINATION_FLIGHTS_TXT.getBy());
        return element;
    }

    public static WebElement vuelosPageBuscarBtn(WebDriver driver){
        element = driver.findElement(BaseFlowMap.BUSCAR_BTN.getBy());
        return element;
    }

    public static WebElement tipoVueloDdl(WebDriver driver){
        element = driver.findElement(VuelosFlowMap.TIPO_DE_VUELO_DDL.getBy());
        return element;
    }

    public static WebElement idaRdb(WebDriver driver){
        element = driver.findElement(VuelosFlowMap.TICKET_IDA_RDB.getBy());
        return element;
    }

    public static WebElement vueltaRdb(WebDriver driver){
        element = driver.findElement(VuelosFlowMap.TICKET_VUELTA_RDB.getBy());
        return element;
    }

    public static WebElement comprarBtn(WebDriver driver){
        element = driver.findElement(VuelosFlowMap.COMPRAR_BTN.getBy());
        return element;
    }

    public static void comprarTickets(WebDriver driver){
        idaRdb(driver).click();
        vueltaRdb(driver).click();
        comprarBtn(driver).click();
    }
}
