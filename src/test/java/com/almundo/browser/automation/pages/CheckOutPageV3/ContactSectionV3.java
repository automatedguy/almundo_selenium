package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.utils.JsonRead;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Created by leandro.efron on 6/12/2016.
 */
public class ContactSectionV3 extends CheckOutPage {

    public ContactSectionV3(WebDriver driver) {
        super(driver);
    }

    private static JSONObject contactList = null;
    public static JSONObject contactData = null;

    //############################################### Locators ##############################################

    @FindBy(id = "email")
    public WebElement emailTxt;

    @FindBy(id = "email_confirmation")
    public WebElement repEmailTxt;

    @FindBy(id = "susc")
    public WebElement subscriptionCbx;

    @FindBy(css = "contact-form > div > div:nth-child(2) > div > div > div.row > div:nth-child(1) > div > div:nth-child(1) > div > select")
    public WebElement phoneTypeDdl;

    @FindBy(id = "country_code")
    public WebElement countryCodeTxt;

    @FindBy(id = "area_code")
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
        logger.info("Entering Número: [" + phoneNumber + "]");
        List<WebElement> phoneNumberList = driver.findElements(By.id("number"));
        phoneNumberList.get(phoneNumberList.size() -1).sendKeys(phoneNumber);
    }


    public static void getContactList()  {
        contactList = JsonRead.getJsonDataObject(jsonDataObject, "contacts", countryPar.toLowerCase() + "_data.json");
    }

    public static void getContactData(String dataSet)  {
        contactData = JsonRead.getJsonDataObject(contactList, dataSet, countryPar.toLowerCase() + "_data.json");
    }

}
