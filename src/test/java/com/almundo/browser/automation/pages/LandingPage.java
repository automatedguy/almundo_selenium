package com.almundo.browser.automation.pages;

import com.almundo.browser.automation.locators.pages.LandingPageMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by gabrielcespedes on 13/10/16.
 */
public class LandingPage {

    private static WebElement element = null;

    public static WebElement goToBaseUrl(WebDriver driver, String appURL){
        driver.get(appURL);
        return element;
    }

    public static WebElement argentinaLink(WebDriver driver){
        element = driver.findElement(LandingPageMap.ARGENTINA_LINK.getBy());
        return element;
    }

    public static WebElement colombiaLink(WebDriver driver){
        element = driver.findElement(LandingPageMap.COLOMBIA_LINK.getBy());
        return element;
    }

    public static WebElement mexicoLink(WebDriver driver){
        element = driver.findElement(LandingPageMap.MEXICO_LINK.getBy());
        return element;
    }

    public static WebElement selectCountryPage(WebDriver driver, String country){
        switch(country) {
            case "ARGENTINA":
                LandingPage.argentinaLink(driver).click();
                break;
            case "COLOMBIA":
                LandingPage.colombiaLink(driver).click();
                break;
            case "MEXICO":
                LandingPage.mexicoLink(driver).click();
                break;
        }
        return element;
    }

}
