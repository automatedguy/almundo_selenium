package com.almundo.browser.automation.pages.CheckOutPageV3;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static com.almundo.browser.automation.utils.PageUtils.randomString;

public class EmergencyContact extends CheckOutPageV3{

    public EmergencyContact(WebDriver driver) {
        super(driver);
    }

    /**************************** Locators **********************************/

    @FindBy(css = "emergency-contact-form #first_name")
    private WebElement nombreTxt;

    @FindBy(css = "emergency-contact-form #last_name")
    private WebElement apellidoTxt;

    @FindBy(css = "emergency-contact-form #telephone_type")
    public WebElement phoneTypeDdl;

    @FindBy(css = "emergency-contact-form #country_code")
    public WebElement countryCodeTxt;

    @FindBy(css = "emergency-contact-form #area_code")
    public WebElement areaCodeTxt;

    @FindBy(css = "emergency-contact-form #number")
    public WebElement numeroTxt;

    /**************************** Actions **********************************/

    private EmergencyContact setNombre(String nombre) {
        logger.info("Entering Nombre: [" + nombre + "]");
        nombreTxt.clear();
        nombreTxt.sendKeys(nombre);
        return this;
    }

    private EmergencyContact setApellido(String apellido) {
        logger.info("Entering Apellido: [" + apellido + "]");
        apellidoTxt.clear();
        apellidoTxt.sendKeys(apellido);
        return this;
    }

    private EmergencyContact setPhoneType(String phoneType) {
        logger.info("Selecting Teléfono: [" + phoneType + "]");
        Select selectPhoneType = new Select (phoneTypeDdl);
        selectPhoneType.selectByVisibleText(phoneType);
        return this;
    }

    private EmergencyContact setCountryCode(String countryCode) {
        logger.info("Entering Código de país: [" + countryCode + "]");
        countryCodeTxt.clear();
        countryCodeTxt.sendKeys(countryCode);
        return this;
    }

    private EmergencyContact setAreaCode(String areaCode) {
        logger.info("Selecting Cód. Area: [" + areaCode + "]");
        areaCodeTxt.clear();
        areaCodeTxt.sendKeys(areaCode);
        return this;
    }

    private EmergencyContact setPhoneNumber(String phoneNumber) {
        logger.info("Entering Número de Teléfono: [" + phoneNumber + "]");
        numeroTxt.clear();
        numeroTxt.sendKeys(phoneNumber);
        return this;
    }

    public EmergencyContact populateEmergencyContact(JSONObject emergencyContactData) {
        if (inputDef.isRequired("emergency_contacts")) {
            logger.info("------------- Filling Contact Section -------------");

            if (inputDef.isRequired("emergency_contacts", "first_name", 0)) {
                setNombre(randomString(10)); }

            if (inputDef.isRequired("emergency_contacts", "last_name", 0)) {
                setApellido(randomString(10)); }

            if (inputDef.isRequired("emergency_contacts", "telephone", "telephone_type")) {
                setPhoneType(emergencyContactData.get("tel").toString()); }

            if (inputDef.isRequired("emergency_contacts", "telephone", "country_code")) {
                setCountryCode(emergencyContactData.get("country_code").toString()); }

            if (inputDef.isRequired("emergency_contacts", "telephone", "area_code")) {
                setAreaCode(emergencyContactData.get("area").toString()); }

            if (inputDef.isRequired("emergency_contacts", "telephone", "number")) {
                setPhoneNumber(emergencyContactData.get("phone_number").toString()); }
        }
        return this;
    }

}
