package com.almundo.browser.automation.pages.PaymentPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by gabrielcespedes on 29/11/16.
 */
public class BillingInfoSection extends PaymentPage {

    public BillingInfoSection(WebDriver driver) {
        super(driver);
    }

    //############################################### Locators ##############################################

    @FindBy(id = "billing_fiscal_document")
    private WebElement billing_fiscal_document;

    @FindBy(id = "fiscal_name")
    private WebElement fiscal_name;

    @FindBy(id = "billing_fiscal_type")
    private WebElement billing_fiscal_type;

    @FindBy(id = "billing_document_type")
    private WebElement billing_document_type;

    @FindBy(id = "billing_address")
    private WebElement billing_address;

    @FindBy(id = "address_number")
    private WebElement address_number;

    @FindBy(id = "address_floor")
    private WebElement address_floor;

    @FindBy(id = "address_department")
    private WebElement address_department;

    @FindBy(id = "address_postal_code")
    private WebElement address_postal_code;

    @FindBy(id = "address_state")
    private WebElement address_state;

    @FindBy(id = "address_city")
    private WebElement address_city;


    //############################################### Actions ##############################################

    public BillingInfoSection selectBillingFiscalType(String billingFiscalType) {
        logger.info("Situación fiscal drop down list is enabled, selecting...");
        Select SITUACION_FISCAL_SELECT = new Select(billing_fiscal_type);
        SITUACION_FISCAL_SELECT.selectByVisibleText(billingFiscalType);
        return this;
    }

    public BillingInfoSection selectBillingDocumentType(String billingDocumentType){
        logger.info("Tipo de Documento drop down list is enabled, selecting...");
        Select TIPO_DE_DOCUMENTO_SELECT = new Select(billing_document_type);
        TIPO_DE_DOCUMENTO_SELECT.selectByVisibleText(billingDocumentType);
        return this;
    }

    public BillingInfoSection setBillingFiscalDocument(String cuil) {
        logger.info("Entering CUIL/CUIT number: [" + cuil + "]");
        billing_fiscal_document.clear();
        billing_fiscal_document.sendKeys(cuil);
        return this;
    }

    public BillingInfoSection setBillingFiscalName(String billingFiscalName) {
        logger.info("Entering Nombre o Razon Social: [" + billingFiscalName + "]");
        fiscal_name.clear();
        fiscal_name.sendKeys(billingFiscalName);
        return this;
    }

    public BillingInfoSection setBillingAddress(String billingAddress) {
        logger.info("Entering Nombre o Razon Social: [" + billingAddress + "]");
        billing_address.clear();
        billing_address.sendKeys(billingAddress);
        return this;
    }

    public BillingInfoSection setAddressNumber(String addressNumber) {
        logger.info("Entering Nombre o Razon Social: [" + addressNumber + "]");
        address_number.clear();
        address_number.sendKeys(addressNumber);
        return this;
    }

    public BillingInfoSection setAddressFloor(String addressFloor) {
        logger.info("Entering Nombre o Razon Social: [" + addressFloor + "]");
        address_floor.clear();
        address_floor.sendKeys(addressFloor);
        return this;
    }

    public BillingInfoSection setAddressDepartment(String addressDepartment) {
        logger.info("Entering Nombre o Razon Social: [" + addressDepartment + "]");
        address_department.clear();
        address_department.sendKeys(addressDepartment);
        return this;
    }

    public BillingInfoSection setAddressPostalCode(String addressPostalCcode) {
        logger.info("Entering Nombre o Razon Social: [" + addressPostalCcode + "]");
        address_postal_code.clear();
        address_postal_code.sendKeys(addressPostalCcode);
        return this;
    }

    public BillingInfoSection setAddressState(String addressState) {
        logger.info("Entering Nombre o Razon Social: [" + addressState + "]");
        address_state.clear();
        address_state.sendKeys(addressState);
        return this;
    }

    public BillingInfoSection setAddressCity(String addressCity) {
        logger.info("Entering Nombre o Razon Social: [" + addressCity + "]");
        address_city.clear();
        address_city.sendKeys(addressCity);
        return this;
    }


    public BillingInfoSection populateBillingInfo(){

        logger.info("Populating billing information fields requiered...");

        setBillingFiscalName("Nombre o Razon Social");
        selectBillingFiscalType("Persona natural");
        selectBillingDocumentType("Cédula de Ciudadanía");
        setBillingFiscalDocument("20285494568");
        setBillingAddress("Domicilo");
        setAddressNumber("7550");
        setAddressFloor("10");
        setAddressDepartment("A");
        setAddressPostalCode("1009");
        setAddressState("Buenos Aires");
        setAddressCity("CABA");
        return this;
    }
}