package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.utils.JsonRead;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Created by gabrielcespedes on 29/11/16.
 */
public class BillingSectionV3 extends CheckOutPageV3 {

    public BillingSectionV3(WebDriver driver) {
        super(driver);
    }

    private static JSONObject billingsList = null;
    public static JSONObject billingData = null;

    //############################################### Locators ##############################################

    @FindBy(id = "fiscal_name")
    private WebElement fiscalNameTxt;

    @FindBy(css = "billing-form > div > div:nth-child(2) > div.schema-form-section.col-12-xs.col-12-sm.col-3-md > div > select")
    private WebElement billingFiscalTypeDdl;

    @FindBy(css = "billing-form > div > div:nth-child(2) > div.schema-form-section.col-12-xs.col-12-sm.col-4-md > div > select")
    private WebElement billingDocumentType;

    @FindBy(id = "fiscal_document")
    private WebElement billingFiscalDocumentTxt;

    @FindBy(id = "street")
    private WebElement billingAddressTxt;

    @FindBy(css = "billing-form #number")
    private WebElement addressNumberTxt;

    @FindBy(id = "floor")
    private WebElement addressFloorTxt;

    @FindBy(id = "department")
    private WebElement addressDepartmentTxt;

    @FindBy(id = "postal_code")
    private WebElement addressPostalCodeTxt;

    @FindBy(id = "state")
    private WebElement addressStateDdl;

    @FindBy(id = "city")
    private WebElement addressCityTxt;

    //############################################### Actions ##############################################

    public BillingSectionV3 populateBillingSection(JSONObject billingData) {
        if (isElementRequiered(checkOutPageElements, "BillingInfoSection")) {
            logger.info("------------- Filling Billing Section -------------");
            if (isElementRequiered(checkOutPageElements, "fiscal_name")) {
                setBillingFiscalNameTxt(billingData.get("fiscal_name").toString());
            }
            if (isElementRequiered(checkOutPageElements, "billing_fiscal_type")){
                selectBillingFiscalType(billingData.get("billing_fiscal_type").toString());
            }
            if (isElementRequiered(checkOutPageElements, "billing_document_type")){
                selectBillingDocumentType(billingData.get("billing_document_type").toString());
            }
            setBillingFiscalDocumentTxt(billingData.get("billing_fiscal_document").toString());
            setBillingAddressTxt(billingData.get("billing_address").toString());
            setAddressNumberTxt(billingData.get("address_number").toString());
            setAddressFloorTxt(billingData.get("address_floor").toString());
            setAddressDepartmentTxt(billingData.get("address_department").toString());
            setAddressPostalCodeTxt(billingData.get("address_postal_code").toString());
            setAddressStateDdl(billingData.get("address_state").toString());
            setAddressCityTxt(billingData.get("address_city").toString());
        }
        return this;
    }

    public BillingSectionV3 setBillingFiscalNameTxt(String billingFiscalName) {
        logger.info("Entering Nombre o Razón Social: [" + billingFiscalName + "]");
        fiscalNameTxt.clear();
        fiscalNameTxt.sendKeys(billingFiscalName);
        return this;
    }

    public BillingSectionV3 selectBillingFiscalType(String billingFiscalType) {
        logger.info("Selecting Situación: [" + billingFiscalType + "]");
        Select SITUACION_FISCAL_SELECT = new Select(this.billingFiscalTypeDdl);
        SITUACION_FISCAL_SELECT.selectByVisibleText(billingFiscalType);
        return this;
    }

    public BillingSectionV3 selectBillingDocumentType(String billingDocumentType){
        logger.info("Selecting Tipo de Documento: [" + billingDocumentType + "]");
        Select tipoDeDocumento = new Select(this.billingDocumentType);
        tipoDeDocumento.selectByVisibleText(billingDocumentType);
        return this;
    }

    public BillingSectionV3 setBillingFiscalDocumentTxt(String cuil) {
        logger.info("Entering CUIL/CUIT: [" + cuil + "]");
        this.billingFiscalDocumentTxt.clear();
        this.billingFiscalDocumentTxt.sendKeys(cuil);
        return this;
    }

    public BillingSectionV3 setBillingAddressTxt(String billingAddress) {
        logger.info("Entering Domicilio: [" + billingAddress + "]");
        this.billingAddressTxt.clear();
        this.billingAddressTxt.sendKeys(billingAddress);
        return this;
    }

    public BillingSectionV3 setAddressNumberTxt(String addressNumber) {
        logger.info("Entering Número: [" + addressNumber + "]");
        List<WebElement> addressNumberList = driver.findElements(By.id("number"));
        addressNumberList.get(addressNumberList.size() -2).sendKeys(addressNumber);
        return this;
    }

    public BillingSectionV3 setAddressFloorTxt(String addressFloor) {
        logger.info("Entering Piso: [" + addressFloor + "]");
        this.addressFloorTxt.clear();
        this.addressFloorTxt.sendKeys(addressFloor);
        return this;
    }

    public BillingSectionV3 setAddressDepartmentTxt(String addressDepartment) {
        logger.info("Entering Departamento: [" + addressDepartment + "]");
        this.addressDepartmentTxt.clear();
        this.addressDepartmentTxt.sendKeys(addressDepartment);
        return this;
    }

    public BillingSectionV3 setAddressPostalCodeTxt(String addressPostalCcode) {
        logger.info("Entering Código Postal: [" + addressPostalCcode + "]");
        addressPostalCodeTxt.clear();
        addressPostalCodeTxt.sendKeys(addressPostalCcode);
        return this;
    }

    public BillingSectionV3 setAddressStateDdl(String addressStateDdl) {
        logger.info("Selecting Provincia: [" + addressStateDdl + "]");
        Select addressStateSelect =  new Select(this.addressStateDdl);
        addressStateSelect.selectByVisibleText(addressStateDdl);
        return this;
    }

    public BillingSectionV3 setAddressCityTxt(String addressCity) {
        logger.info("Entering Ciudad: [" + addressCity + "]");
        this.addressCityTxt.clear();
        this.addressCityTxt.sendKeys(addressCity);
        return this;
    }

    public static void getBillingList()  {
        billingsList = JsonRead.getJsonDataObject(jsonDataObject, "billings", countryPar.toLowerCase() + "_data.json");
    }

    public static void getBillingData(String dataSet)  {
        billingData = JsonRead.getJsonDataObject(billingsList, dataSet, countryPar.toLowerCase() + "_data.json");
    }
}