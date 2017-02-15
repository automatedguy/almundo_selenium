package com.almundo.browser.automation.pages.CheckOutPageV3;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by leandro.efron on 6/12/2016.
 */
public class ContactSectionV3 extends CheckOutPageV3 {

    public ContactSectionV3(WebDriver driver) {
        super(driver);
    }

    //############################################### Locators ##############################################

    @FindBy(id = "email")
    public WebElement emailTxt;

    @FindBy(id = "email_confirmation")
    public WebElement repEmailTxt;

    @FindBy(name = "telephone_type")
    public WebElement phoneTypeDdl;

    @FindBy(id = "country_code")
    public WebElement countryCodeTxt;

    @FindBy(id = "area_code")
    public WebElement areaCodeTxt;

    @FindBy(css = "contact-form #number")
    public WebElement phoneNumberTxt;

    //############################################### Actions ###############################################

    public ContactSectionV3 populateContactSection(JSONObject contactData) {
        logger.info("------------- Filling Contact Section -------------");
        setEmail(contactData.get("email").toString());
        setRepEmail(contactData.get("rep_email").toString());
        selectPhoneType(contactData.get("tel").toString());
        setCountryCode(contactData.get("country_code").toString());
        setAreaCode(contactData.get("area").toString());
        setPhoneNumber(contactData.get("phone_number").toString());
        return this;
    }

    public void setEmail(String email) {
        logger.info("Entering Email: [" + email + "]");
        emailTxt.clear();
        emailTxt.sendKeys(email);
    }

    public void setRepEmail(String repEmail) {
        logger.info("Entering Repetí tu email: [" + repEmail + "]");
        repEmailTxt.clear();
        repEmailTxt.sendKeys(repEmail);
    }

    public void selectPhoneType(String phoneType) {
        logger.info("Selecting Teléfono: [" + phoneType + "]");
        Select selectPhoneType = new Select (phoneTypeDdl);
        selectPhoneType.selectByVisibleText(phoneType);
    }

    public void setCountryCode(String countryCode) {
        logger.info("Entering Código de país: [" + countryCode + "]");
        countryCodeTxt.clear();
        countryCodeTxt.sendKeys(countryCode);
    }

    public void setAreaCode(String areaCode) {
        logger.info("Selecting Cód. Area: [" + areaCode + "]");
        areaCodeTxt.clear();
        areaCodeTxt.sendKeys(areaCode);
    }

    public void setPhoneNumber(String phoneNumber) {
        logger.info("Entering Número de Teléfono: [" + phoneNumber + "]");
        phoneNumberTxt.clear();
        phoneNumberTxt.sendKeys(phoneNumber);
    }

}
