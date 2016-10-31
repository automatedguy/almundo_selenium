package com.almundo.browser.automation.pages;

import com.almundo.browser.automation.locators.pages.HomePageMap;
import com.almundo.browser.automation.locators.pages.HotelesPageMap;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
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

    public static WebElement verHabitacionesBtn(WebDriver driver){
        element = driver.findElement(HotelesPageMap.VER_HABITACIONES_BTN.getBy());
        return element;
    }

    public static WebElement reservarAhoraBtn(WebDriver driver){
        element = driver.findElement(HotelesPageMap.RESERVAR_AHORA_BTN.getBy());
        return element;
    }

    public static WebElement reservarAhora2Btn(WebDriver driver){
        element = driver.findElement(HotelesPageMap.RESERVAR_AHORA2_BTN.getBy());
        return element;
    }

    public static WebElement selectCityFromAutoCompleteSuggestions(WebDriver driver, By city){
        WebElement selectedCity = driver.findElement(city);
        selectedCity.click();
        return element;
    }

    public static void doHotelReservationFlow(WebDriver driver) throws InterruptedException {
        try {
            PageUtils.waitForVisibilityOfElementLocated(driver, 30, HotelesPageMap.VER_HOTEL_BTN.getBy());
            HotelesPage.verHotelBtn(driver).click();
            PageUtils.waitForSaucePicture(10000);
        }
        catch (TimeoutException timeOut) {
            PageUtils.waitForVisibilityOfElementLocated(driver, 30, HotelesPageMap.VER_HABITACIONES_BTN.getBy());
            HotelesPage.verHabitacionesBtn(driver).click();
            PageUtils.waitForSaucePicture(10000);
        }

        try {
            PageUtils.waitForVisibilityOfElementLocated(driver, 30, HotelesPageMap.VER_HABITACIONES_BTN.getBy());
            HotelesPage.verHabitacionesBtn(driver).click();
            PageUtils.waitForSaucePicture(10000);
        }
        catch (TimeoutException timeOut) {
            PageUtils.waitForVisibilityOfElementLocated(driver, 30, HotelesPageMap.RESERVAR_AHORA_BTN.getBy());
            HotelesPage.reservarAhoraBtn(driver).click();
            PageUtils.waitForSaucePicture(10000);
        }

        try {
            PageUtils.waitForVisibilityOfElementLocated(driver, 30, HotelesPageMap.RESERVAR_AHORA_BTN.getBy());
            HotelesPage.reservarAhoraBtn(driver).click();
            PageUtils.waitForSaucePicture(10000);
        }
        catch (TimeoutException timeOut) {
            System.out.println("The other reservation flow :)");
            try{
                PageUtils.waitForVisibilityOfElementLocated(driver, 30, HotelesPageMap.RESERVAR_AHORA2_BTN.getBy());
                HotelesPage.reservarAhora2Btn(driver).click();
                PageUtils.waitForSaucePicture(10000);
            }
            catch (TimeoutException timeOut2) {
                System.out.println("Something is wrong in the test flow");
                PageUtils.waitForSaucePicture(10000);
            }
        }
        finally {
            PaymentPage.populatePassenger(driver, 2);
            PageUtils.waitForSaucePicture(10000);
        }
    }


}
