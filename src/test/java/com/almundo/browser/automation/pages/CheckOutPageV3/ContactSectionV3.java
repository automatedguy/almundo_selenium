package com.almundo.browser.automation.pages.CheckOutPageV3;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static com.almundo.browser.automation.utils.Constants.VALUE;

/**
 * Created by leandro.efron on 6/12/2016.
 */
public class ContactSectionV3 extends CheckOutPageV3 {

    public ContactSectionV3(WebDriver driver) {
        super(driver);
    }

    //############################################### Locators ##############################################

    @FindBy(css = "contact-form #email")
    public WebElement emailTxt;

    @FindBy(css = "contact-form #email_confirmation")
    public WebElement repEmailTxt;

    @FindBy(css = "contact-form #telephone_type")
    public WebElement phoneTypeDdl;

    @FindBy(css = "contact-form #country_code")
    public WebElement countryCodeTxt;

    @FindBy(css = "contact-form div > div div.row > div:nth-child(1) div:nth-child(2) input")
    public WebElement countryCodeStgTxt;

    @FindBy(css = "contact-form #area_code")
    public WebElement areaCodeTxt;

    @FindBy(css =  "contact-form div:nth-child(1) div:nth-child(3) input")
    public WebElement areaCodeStgTxt;

    @FindBy(css = "contact-form #number")
    public WebElement phoneNumberTxt;

    @FindBy(css = "contact-form div:nth-child(1) div:nth-child(4) input")
    public WebElement phoneNumberStgTxt;

    //############################################### Actions ###############################################

    public ContactSectionV3 populateContactSection(JSONObject contactData) {
        logger.info("------------- Filling Contact Section -------------");

        if(inputDef.isRequired("contacts","email",0)){
            setEmail(contactData.get("email").toString());}

        if(inputDef.isRequired("contacts","email",0)){
            setRepEmail(contactData.get("rep_email").toString());}

        if(inputDef.isRequired("contacts", "telephones", "telephone_type",0)){
            setPhoneType(contactData.get("tel").toString());}

        if(inputDef.isRequired("contacts", "telephones", "country_code",0)){
            setCountryCode(contactData.get("country_code").toString());}

        if(inputDef.isRequired("contacts", "telephones", "area_code",0)){
            setAreaCode(contactData.get("area").toString());}

        if(inputDef.isRequired("contacts", "telephones", "number",0)){
            setPhoneNumber(contactData.get("phone_number").toString());}

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

    private void setPhoneType(String phoneType) {
        logger.info("Selecting Teléfono: [" + phoneType + "]");
        Select selectPhoneType = new Select (phoneTypeDdl);
        selectPhoneType.selectByVisibleText(phoneType);
    }

    @SuppressWarnings("Duplicates")
    private void setCountryCode(String countryCode) {
        logger.info("Entering Código de país: [" + countryCode + "]");
        if(baseURL.contains("st.")){
            countryCodeStgTxt.clear();
            countryCodeStgTxt.sendKeys(countryCode);
        }else {
            countryCodeTxt.clear();
            countryCodeTxt.sendKeys(countryCode);
        }
    }

    @SuppressWarnings("Duplicates")
    private void setAreaCode(String areaCode) {
        logger.info("Selecting Cód. Area: [" + areaCode + "]");
        if(baseURL.contains("st.")) {
            areaCodeStgTxt.clear();
            areaCodeStgTxt.sendKeys(areaCode);
        }else{
            areaCodeTxt.clear();
            areaCodeTxt.sendKeys(areaCode);
        }
    }

    @SuppressWarnings("Duplicates")
    private void setPhoneNumber(String phoneNumber) {
        logger.info("Entering Número de Teléfono: [" + phoneNumber + "]");
        if(baseURL.contains("st.")) {
            phoneNumberStgTxt.clear();
            phoneNumberStgTxt.sendKeys(phoneNumber);
        }else{
            phoneNumberTxt.clear();
            phoneNumberTxt.sendKeys(phoneNumber);
        }
    }

    public String getContactEmail(){
        logger.info("Retrieving Entered Contact Email: [" + emailTxt.getAttribute(VALUE).toString() + "]");
        return emailTxt.getAttribute(VALUE).toString();
    }
}
