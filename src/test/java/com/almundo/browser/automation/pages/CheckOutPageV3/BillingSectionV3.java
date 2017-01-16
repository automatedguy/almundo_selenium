package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.utils.JsonRead;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by gabrielcespedes on 29/11/16.
 */
public class BillingSectionV3 extends CheckOutPage {

    public BillingSectionV3(WebDriver driver) {
        super(driver);
    }

    private static JSONObject billingsList = null;
    public static JSONObject billingData = null;

    //############################################### Locators ##############################################

    @FindBy(id = "fiscal_document")
    private WebElement billing_fiscal_document;

    @FindBy(id = "fiscal_name")
    private WebElement fiscal_name;

    @FindBy(id = "billing_fiscal_type")
    private WebElement billing_fiscal_type;

    @FindBy(id = "billing_document_type")
    private WebElement billing_document_type;

    @FindBy(id = "street")
    private WebElement billing_address;

    @FindBy(id = "number")
    private WebElement address_number;

    @FindBy(id = "floor")
    private WebElement address_floor;

    @FindBy(id = "department")
    private WebElement address_department;

    @FindBy(id = "postal_code")
    private WebElement address_postal_code;

    @FindBy(id = "address_state")
    private WebElement address_state;

    @FindBy(id = "city")
    private WebElement address_city;


    //############################################### Actions ##############################################

    public BillingSectionV3 selectBillingFiscalType(String billingFiscalType) {
        logger.info("Selecting Situación: [" + billingFiscalType + "]");
        Select SITUACION_FISCAL_SELECT = new Select(billing_fiscal_type);
        SITUACION_FISCAL_SELECT.selectByVisibleText(billingFiscalType);
        return this;
    }

    public BillingSectionV3 selectBillingDocumentType(String billingDocumentType){
        logger.info("Selecting Tipo de Documento: [" + billingDocumentType + "]");
        Select tipoDeDocumento = new Select(billing_document_type);
        tipoDeDocumento.selectByVisibleText(billingDocumentType);
        return this;
    }

    public BillingSectionV3 setBillingFiscalDocument(String cuil) {
        logger.info("Entering CUIL/CUIT: [" + cuil + "]");
        this.billing_fiscal_document.clear();
        this.billing_fiscal_document.sendKeys(cuil);
        return this;
    }

    public BillingSectionV3 setBillingFiscalName(String billingFiscalName) {
        logger.info("Entering Nombre o Razón Social: [" + billingFiscalName + "]");
        fiscal_name.clear();
        fiscal_name.sendKeys(billingFiscalName);
        return this;
    }

    public BillingSectionV3 setBillingAddress(String billingAddress) {
        logger.info("Entering Domicilio: [" + billingAddress + "]");
        billing_address.clear();
        billing_address.sendKeys(billingAddress);
        return this;
    }

    public BillingSectionV3 setAddressNumber(String addressNumber) {
        logger.info("Entering Número: [" + addressNumber + "]");
        address_number.clear();
        address_number.sendKeys(addressNumber);
        return this;
    }

    public BillingSectionV3 setAddressFloor(String addressFloor) {
        logger.info("Entering Piso: [" + addressFloor + "]");
        address_floor.clear();
        address_floor.sendKeys(addressFloor);
        return this;
    }

    public BillingSectionV3 setAddressDepartment(String addressDepartment) {
        logger.info("Entering Departamento: [" + addressDepartment + "]");
        address_department.clear();
        address_department.sendKeys(addressDepartment);
        return this;
    }

    public BillingSectionV3 setAddressPostalCode(String addressPostalCcode) {
        logger.info("Entering Código Postal: [" + addressPostalCcode + "]");
        address_postal_code.clear();
        address_postal_code.sendKeys(addressPostalCcode);
        return this;
    }

    public BillingSectionV3 setAddressState(String addressState) {
        logger.info("Selecting Provincia: [" + addressState + "]");
        Select addressStateSelect =  new Select(address_state);
        addressStateSelect.selectByVisibleText(addressState);
        return this;
    }

    public BillingSectionV3 setAddressCity(String addressCity) {
        logger.info("Entering Ciudad: [" + addressCity + "]");
        address_city.clear();
        address_city.sendKeys(addressCity);
        return this;
    }


    public static void getBillingList()  {
        billingsList = JsonRead.getJsonDataObject(jsonDataObject, "billings", countryPar.toLowerCase() + "_data.json");
    }

    public static void getBillingData(String dataSet)  {
        billingData = JsonRead.getJsonDataObject(billingsList, dataSet, countryPar.toLowerCase() + "_data.json");
    }
}