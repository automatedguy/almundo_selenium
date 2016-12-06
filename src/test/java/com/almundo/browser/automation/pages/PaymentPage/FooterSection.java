package com.almundo.browser.automation.pages.PaymentPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 06/12/16.
 */
public class FooterSection extends PaymentPage {

    public FooterSection(WebDriver driver) {
        super(driver);
    }

    //############################################### Locators ##############################################

    @FindBy(id = "read")
    private WebElement readCbx;

    @FindBy(id = "accepted")
    private WebElement acceptedCbx;

    //############################################### Actions ##############################################

    public FooterSection acceptTermsAndConditions() {
        logger.info("Checking Terms and Conditions Check Box...");
        readCbx.click();
        return this;
    }

    public FooterSection acceptItinerary() {
        logger.info("Checking Itinerary Check Box...");
        acceptedCbx.click();
        return this;
    }
}
