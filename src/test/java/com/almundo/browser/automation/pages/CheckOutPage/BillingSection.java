package com.almundo.browser.automation.pages.CheckOutPage;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by gabrielcespedes on 29/11/16.
 */
public class BillingSection extends CheckOutPage {

    public BillingSection(WebDriver driver) {
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

    public BillingSection populateBillingSection(JSONObject billingData) {
        if (isElementRequiered(checkOutPageElements, "BillingInfoSection")) {

            logger.info("------------- Filling Billing Section -------------");

            if (isElementRequiered(checkOutPageElements, "fiscal_name")) {
                setBillingFiscalName(billingData.get("fiscal_name").toString());
            }
            if (isElementRequiered(checkOutPageElements, "billing_fiscal_type")){
                selectBillingFiscalType(billingData.get("billing_fiscal_type").toString());
            }
            if (isElementRequiered(checkOutPageElements, "billing_document_type")){
                selectBillingDocumentType(billingData.get("billing_document_type").toString());
            }

            setBillingFiscalDocument(billingData.get("billing_fiscal_document").toString());
            setBillingAddress(billingData.get("billing_address").toString());

            if (isElementRequiered(checkOutPageElements, "address_number")){
                setAddressNumber(billingData.get("address_number").toString());
            }
            if (isElementRequiered(checkOutPageElements, "address_floor")) {
                setAddressFloor(billingData.get("address_floor").toString());
            }
            if (isElementRequiered(checkOutPageElements, "address_department")) {
                setAddressDepartment(billingData.get("address_department").toString());
            }
            if (isElementRequiered(checkOutPageElements, "address_postal_code")) {
                setAddressPostalCode(billingData.get("address_postal_code").toString());
            }

            setAddressState(billingData.get("address_state").toString());
            setAddressCity(billingData.get("address_city").toString());
        }
        return this;
    }

    private BillingSection selectBillingFiscalType(String billingFiscalType) {
        logger.info("Selecting Situación: [" + billingFiscalType + "]");
        Select SITUACION_FISCAL_SELECT = new Select(billing_fiscal_type);
        SITUACION_FISCAL_SELECT.selectByVisibleText(billingFiscalType);
        return this;
    }

    private BillingSection selectBillingDocumentType(String billingDocumentType){
        logger.info("Selecting Tipo de Documento: [" + billingDocumentType + "]");
        Select tipoDeDocumento = new Select(billing_document_type);
        tipoDeDocumento.selectByVisibleText(billingDocumentType);
        return this;
    }

    private BillingSection setBillingFiscalDocument(String cuil) {
        logger.info("Entering CUIL/CUIT: [" + cuil + "]");
        this.billing_fiscal_document.clear();
        this.billing_fiscal_document.sendKeys(cuil);
        return this;
    }

    private BillingSection setBillingFiscalName(String billingFiscalName) {
        logger.info("Entering Nombre o Razón Social: [" + billingFiscalName + "]");
        fiscal_name.clear();
        fiscal_name.sendKeys(billingFiscalName);
        return this;
    }

    private BillingSection setBillingAddress(String billingAddress) {
        logger.info("Entering Domicilio: [" + billingAddress + "]");
        billing_address.clear();
        billing_address.sendKeys(billingAddress);
        return this;
    }

    private BillingSection setAddressNumber(String addressNumber) {
        logger.info("Entering Número: [" + addressNumber + "]");
        address_number.clear();
        address_number.sendKeys(addressNumber);
        return this;
    }

    private BillingSection setAddressFloor(String addressFloor) {
        logger.info("Entering Piso: [" + addressFloor + "]");
        address_floor.clear();
        address_floor.sendKeys(addressFloor);
        return this;
    }

    private BillingSection setAddressDepartment(String addressDepartment) {
        logger.info("Entering Departamento: [" + addressDepartment + "]");
        address_department.clear();
        address_department.sendKeys(addressDepartment);
        return this;
    }

    private BillingSection setAddressPostalCode(String addressPostalCcode) {
        logger.info("Entering Código Postal: [" + addressPostalCcode + "]");
        address_postal_code.clear();
        address_postal_code.sendKeys(addressPostalCcode);
        return this;
    }

    private BillingSection setAddressState(String addressState) {
        logger.info("Entering Provincia: [" + addressState + "]");
        address_state.clear();
        address_state.sendKeys(addressState);
        return this;
    }

    private BillingSection setAddressCity(String addressCity) {
        logger.info("Entering Ciudad: [" + addressCity + "]");
        address_city.clear();
        address_city.sendKeys(addressCity);
        return this;
    }
}