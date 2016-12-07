package com.almundo.browser.automation.pages.PaymentPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by leandro.efron on 6/12/2016.
 */
public class ContactInfoSection extends PaymentPage {

    public ContactInfoSection(WebDriver driver) {
        super(driver);
    }

    //############################################### Locators ##############################################

    @FindBy(id = "email")
    public WebElement emailTxt;

    @FindBy(id = "rep_email")
    public WebElement repEmailTxt;

    @FindBy(id = "susc")
    public WebElement subscriptionCbx;

    @FindBy(id = "tel0")
    public WebElement phoneTypeDdl;

    @FindBy(id = "country_code_0")
    public WebElement countryCodeTxt;

    @FindBy(id = "area0")
    public WebElement areaCodeTxt;

    @FindBy(id = "phone_number0")
    public WebElement phoneNumberTxt;

    @FindBy(linkText = "Agregar otro teléfono")
    private WebElement addPhoneLnk;

    //############################################### Actions ###############################################

    public void setEmail(String email) {
        logger.info("Entering Email: [" + email + "]");
        emailTxt.clear();
        emailTxt.sendKeys(email);
    }

    public void setRepEmail(String repEmail) {
        logger.info("Entering Repeat Email: [" + repEmail + "]");
        repEmailTxt.clear();
        repEmailTxt.sendKeys(repEmail);
    }

    public void selectPhoneType(String phoneType) {
        logger.info("Selecting phone type: [" + phoneType + "]");
        Select selectPhoneType = new Select (phoneTypeDdl);
        selectPhoneType.selectByVisibleText(phoneType);
    }

    public void setCountryCode(String countryCode) {
        logger.info("Entering country code: [" + countryCode + "]");

        if (countryCodeTxt.getText().isEmpty()) {
            countryCodeTxt.sendKeys(countryCode);
        }
    }

    public void setAreaCode(String areaCode) {
        logger.info("Selecting area code: [" + areaCode + "]");
        areaCodeTxt.clear();
        areaCodeTxt.sendKeys(areaCode);
    }

    public void setPhoneNumber(String phoneNumber) {
        logger.info("Entering phone number: [" + phoneNumber + "]");
        phoneNumberTxt.clear();
        phoneNumberTxt.sendKeys(phoneNumber);
    }

}
