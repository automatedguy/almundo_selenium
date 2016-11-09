package com.almundo.browser.automation.pages;

import com.almundo.browser.automation.base.PageBaseSetup;
import com.almundo.browser.automation.locators.pages.LandingPageMap;
import org.openqa.selenium.NoSuchElementException;
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
        try {
            switch (country) {
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
        }catch(NoSuchElementException ouch){
            /* If the test retries will be redirected to the corresponding country page */
            /* Hence no need to select country this time */
            System.out.println("Already been here.....");
        }
        return this;
    }
}
