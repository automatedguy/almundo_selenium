package com.almundo.browser.automation.pages;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.BasePage.BasePage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 13/10/16.
 */
public class LandingPage extends TestBaseSetup {

    public LandingPage(WebDriver iDriver) {
        this.driver = iDriver;
    }

    //############################################### Locators ##############################################

    @FindBy(linkText = "Argentina")
    private WebElement argentinaLnk;

    @FindBy(linkText = "Colombia")
    private WebElement colombiaLnk;

    @FindBy(linkText = "Mexico")
    private WebElement mexicoLnk;

    //############################################### Actions ###############################################

    public BasePage selectCountryPage(String country){
        try {
            switch (country) {
                case "ARGENTINA":
                    argentinaLnk.click();
                    break;
                case "COLOMBIA":
                    colombiaLnk.click();
                    break;
                case "MEXICO":
                    mexicoLnk.click();
                    break;
            }
        }catch(NoSuchElementException ouch){
            /* If the test retries will be redirected to the corresponding country page */
            /* Hence no need to select country this time */
            logger.warn("Landing page not available.");
        }
        return initBasePage();
    }
}
