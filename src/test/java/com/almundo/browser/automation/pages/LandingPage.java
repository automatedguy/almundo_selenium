package com.almundo.browser.automation.pages;

import com.almundo.browser.automation.base.PageBaseSetup;
import com.almundo.browser.automation.locators.pages.LandingPageMap;
import org.openqa.selenium.WebDriver;

/**
 * Created by gabrielcespedes on 13/10/16.
 */
public class LandingPage extends PageBaseSetup {

    public LandingPage(WebDriver driver){
        super.driver = driver;
    }

    public LandingPage goToBaseUrl(WebDriver driver, String appURL){
        driver.get(appURL);
        return this;
    }

    public LandingPage clickArgentinaLink(WebDriver driver){
        driver.findElement(LandingPageMap.ARGENTINA_LINK.getBy()).click();
        return this;
    }

    public LandingPage clickColombiaLink(WebDriver driver){
        driver.findElement(LandingPageMap.COLOMBIA_LINK.getBy()).click();
        return this;
    }

    public LandingPage clickMexicoLink(WebDriver driver){
        driver.findElement(LandingPageMap.MEXICO_LINK.getBy()).click();
        return this;
    }

    public LandingPage selectCountryPage(WebDriver driver, String country){
        switch(country) {
            case "ARGENTINA":
                clickArgentinaLink(driver);
                break;
            case "COLOMBIA":
                clickColombiaLink(driver);
                break;
            case "MEXICO":
                clickMexicoLink(driver);
                break;
        }
        return this;
    }
}
