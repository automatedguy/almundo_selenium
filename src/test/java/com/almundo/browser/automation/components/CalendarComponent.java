package com.almundo.browser.automation.components;

import com.almundo.browser.automation.locators.components.CalendarComponentMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by gabrielcespedes on 31/10/16.
 */
public class CalendarComponent {

    private static WebElement element = null;

    public static WebElement salidaCalendar(WebDriver driver){
        element = driver.findElement(CalendarComponentMap.CALENDARIO_SALIDA.getBy());
        return element;
    }

    public static WebElement regresoCalendar(WebDriver driver){
        element = driver.findElement(CalendarComponentMap.CALENDARIO_REGRESO.getBy());
        return element;
    }

    public static WebElement salidaTriangleCalendar(WebDriver driver){
        element = driver.findElement(CalendarComponentMap.CALENDARIO_SALIDA_TRIANGLE.getBy());
        return element;
    }

    public static WebElement regresoTriangleCalendar(WebDriver driver){
        element = driver.findElement(CalendarComponentMap.CALENDARIO_REGRESO_TRIANGLE.getBy());
        return element;
    }

    public static WebElement salidaDateCalendar(WebDriver driver){
        element = driver.findElement(CalendarComponentMap.CALENDARIO_SALIDA_FECHA.getBy());
        return element;
    }

    public static WebElement regresoDateCalendar(WebDriver driver){
        element = driver.findElement(CalendarComponentMap.CALENDARIO_REGRESO_FECHA.getBy());
        return element;
    }

}
