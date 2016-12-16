package com.almundo.browser.automation.pages.CheckOutPage;

import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 06/12/16.
 */
public class FooterSection extends CheckOutPage {

    public FooterSection(WebDriver driver) {
        super(driver);
    }

    //############################################### Locators ##############################################

    @FindBy(id = "read")
    private WebElement readCbx;

    @FindBy(id = "accepted")
    private WebElement acceptedCbx;

    @FindBy(css = ".button.button--secondary.button--md")
    private WebElement confirmar;

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

    public FooterSection confirmarClick() {
        PageUtils.waitElementForVisibility(driver, confirmar, 10, "Confirmar Button");
        logger.info("Clicking on Confirmar button...");
        confirmar.click();
        return this;
    }
}
