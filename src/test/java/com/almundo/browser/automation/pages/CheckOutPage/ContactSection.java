package com.almundo.browser.automation.pages.CheckOutPage;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by leandro.efron on 6/12/2016.
 */
public class ContactSection extends CheckOutPage {

    public ContactSection(WebDriver driver) {
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

    public ContactSection populateContactSection(JSONObject contactData) {
        logger.info("------------- Filling Contact Section -------------");
        setEmail(contactData.get("email").toString());
        setRepEmail(contactData.get("rep_email").toString());
        selectPhoneType(contactData.get("tel").toString());
        setCountryCode(contactData.get("country_code").toString());
        if (isElementRequiered(checkOutPageElements, "area")) {
            setAreaCode(contactData.get("area").toString());
        }
        setPhoneNumber(contactData.get("phone_number").toString());
        return this;
    }

    private void setEmail(String email) {
        logger.info("Entering Email: [" + email + "]");
        emailTxt.clear();
        emailTxt.sendKeys(email);
    }

    private void setRepEmail(String repEmail) {
        logger.info("Entering Repetí tu email: [" + repEmail + "]");
        repEmailTxt.clear();
        repEmailTxt.sendKeys(repEmail);
    }

    private void selectPhoneType(String phoneType) {
        logger.info("Selecting Teléfono: [" + phoneType + "]");
        Select selectPhoneType = new Select (phoneTypeDdl);
        selectPhoneType.selectByVisibleText(phoneType);
    }

    private void setCountryCode(String countryCode) {
        logger.info("Entering Código de país: [" + countryCode + "]");
        countryCodeTxt.clear();
        countryCodeTxt.sendKeys(countryCode);
    }

    private void setAreaCode(String areaCode) {
        logger.info("Selecting Cód. Area: [" + areaCode + "]");
        areaCodeTxt.clear();
        areaCodeTxt.sendKeys(areaCode);
    }

    private void setPhoneNumber(String phoneNumber) {
        logger.info("Entering Número: [" + phoneNumber + "]");
        phoneNumberTxt.clear();
        phoneNumberTxt.sendKeys(phoneNumber);
    }
}
