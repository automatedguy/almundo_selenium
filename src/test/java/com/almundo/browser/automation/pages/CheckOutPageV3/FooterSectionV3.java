package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by gabrielcespedes on 06/12/16.
 */
public class FooterSectionV3 extends CheckOutPage {

    public FooterSectionV3(WebDriver driver) {
        super(driver);
    }

    //############################################### Locators ##############################################

    @FindBy(id = "accept")
    private WebElement readCbx;

    @FindBy(id = "acceptItinerary")
    private WebElement acceptedCbx;

    @FindBy(css = ".error-modal .accept-button")
    private WebElement confirmar;

    //############################################### Actions ##############################################

    public FooterSectionV3 acceptTermsAndConditions() {
        logger.info("Checking Terms and Conditions Check Box...");
        readCbx.click();
        return this;
    }

    public FooterSectionV3 acceptItinerary() {
        logger.info("Checking Accept Itinerary Check Box...");
        acceptedCbx.click();
        return this;
    }

    public FooterSectionV3 clickConfirmarBtn() {
        PageUtils.waitElementForVisibility(driver, confirmar, 10, "Confirmar Button");
        PageUtils.waitImplicitly(500);
        logger.info("Clicking on Confirmar button...");
        confirmar.click();
        return this;
    }

    public FooterSectionV3 setEmailTxt(String email) {
        logger.info("Filling Agent Email Adress...");
        List<WebElement> emailAgent = driver.findElements(By.id("email"));
        emailAgent.get(1).sendKeys(email);
        return this;
    }
}
