package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

/**
 * Created by gabrielcespedes on 29/11/16.
 */
public class BillingSectionV3 extends CheckOutPageV3 {

    public BillingSectionV3(WebDriver driver) {
        super(driver);
    }

    //############################################### Locators ##############################################

    @FindBy(id = "fiscal_name")
    private WebElement fiscalNameTxt;

    @FindBy(id = "fiscal_type")
    private WebElement billingFiscalTypeDdl;

    @FindBy(css = "billing-section #document_type")
    private WebElement billingDocumentTypeDdl;

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

    @FindBy(css = "billing-section div:nth-child(2) > input")
    private WebElement enableBillingRdb;

    //############################################### Actions ##############################################

    public BillingSectionV3 populateBillingSection(JSONObject billingData) {
        if (inputDef.isRequired("billings")) {

            if(countryPar.equals("MEXICO")){
                clickEnableBillingRdb();
            }

            logger.info("------------- Filling Billing Section -------------");

            if(inputDef.isRequired("billings","fiscal_name",0)){
                setBillingFiscalName(billingData.get("fiscal_name").toString());}

            if(inputDef.isRequired("billings","fiscal_type",0)){
                setBillingFiscalType(billingData.get("billing_fiscal_type").toString());}

            if(inputDef.isRequired("billings","document_type",0)){
                if(PageUtils.isElementPresent(billingDocumentTypeDdl)) {
                    setBillingDocumentType(billingData.get("billing_document_type").toString());}
            }

            if(inputDef.isRequired("billings","fiscal_document",0)){
                setBillingFiscalDocument(billingData.get("billing_fiscal_document").toString());}

            if(inputDef.isRequired("billings", "address", "street")){
                setBillingAddress(billingData.get("billing_address").toString());}

            if(inputDef.isRequired("billings", "address","number")){
                setAddressNumber(billingData.get("address_number").toString());}

            if(inputDef.isRequired("billings", "address","floor")){
                setAddressFloor(billingData.get("address_floor").toString());}

            if(inputDef.isRequired("billings", "address","department")){
                setAddressDepartment(billingData.get("address_department").toString());}

            if(inputDef.isRequired("billings", "address","postal_code")){
                setAddressPostalCode(billingData.get("address_postal_code").toString());}

            if(inputDef.isRequired("billings", "address","states")){
                setAddressState(billingData.get("address_state").toString());}

            if(inputDef.isRequired("billings", "address","city")){
                setAddressCity(billingData.get("address_city").toString());}
        }
        return this;
    }


    private BillingSectionV3 setBillingFiscalName(String billingFiscalName) {
        logger.info("Entering Nombre o Razón Social: [" + billingFiscalName + "]");
        this.fiscalNameTxt.clear();
        this.fiscalNameTxt.sendKeys(billingFiscalName);
        return this;
    }

    private BillingSectionV3 setBillingFiscalType(String billingFiscalType) {
        if(billingFiscalTypeDdl.isEnabled()) {
            logger.info("Selecting Situación Fiscal: [" + billingFiscalType + "]");
            Select situacionFiscalSelect = new Select(this.billingFiscalTypeDdl);
            situacionFiscalSelect.selectByVisibleText(billingFiscalType);
        } else {
            logger.info("Situación Fiscal is disabled by default: [" + billingFiscalTypeDdl.getText() + "]");
        }
        return this;
    }

    private BillingSectionV3 setBillingDocumentType(String billingDocumentType){
        if(billingDocumentTypeDdl.isEnabled()) {
            logger.info("Selecting Tipo de Documento: [" + billingDocumentType + "]");
            Select tipoDeDocumento = new Select(this.billingDocumentTypeDdl);
            tipoDeDocumento.selectByVisibleText(billingDocumentType);
        } else {
            logger.info("Tipo de Documento is disabled by default: [" + billingDocumentTypeDdl.getText() + "]");
        }
        return this;
    }

    private BillingSectionV3 setBillingFiscalDocument(String cuil) {
        logger.info("Entering CUIL/CUIT: [" + cuil + "]");
        this.billingFiscalDocumentTxt.clear();
        this.billingFiscalDocumentTxt.sendKeys(cuil);
        return this;
    }

    private BillingSectionV3 setBillingAddress(String billingAddress) {
        logger.info("Entering Domicilio: [" + billingAddress + "]");
        this.billingAddressTxt.clear();
        this.billingAddressTxt.sendKeys(billingAddress);
        return this;
    }

    private BillingSectionV3 setAddressNumber(String addressNumber) {
        logger.info("Entering Número: [" + addressNumber + "]");
        this.addressNumberTxt.clear();
        this.addressNumberTxt.sendKeys(addressNumber);
        return this;
    }

    private BillingSectionV3 setAddressFloor(String addressFloor) {
        logger.info("Entering Piso: [" + addressFloor + "]");
        this.addressFloorTxt.clear();
        this.addressFloorTxt.sendKeys(addressFloor);
        return this;
    }

    private BillingSectionV3 setAddressDepartment(String addressDepartment) {
        logger.info("Entering Departamento: [" + addressDepartment + "]");
        this.addressDepartmentTxt.clear();
        this.addressDepartmentTxt.sendKeys(addressDepartment);
        return this;
    }

    private BillingSectionV3 setAddressPostalCode(String addressPostalCcode) {
        logger.info("Entering Código Postal: [" + addressPostalCcode + "]");
        this.addressPostalCodeTxt.clear();
        this.addressPostalCodeTxt.sendKeys(addressPostalCcode);
        return this;
    }

    private BillingSectionV3 setAddressState(String addressStateDdl) {
        logger.info("Selecting Provincia: [" + addressStateDdl + "]");
        Select addressStateSelect =  new Select(this.addressStateDdl);
        addressStateSelect.selectByVisibleText(addressStateDdl);
        return this;
    }

    private BillingSectionV3 setAddressCity(String addressCity) {
        logger.info("Entering Ciudad: [" + addressCity + "]");
        this.addressCityTxt.clear();
        this.addressCityTxt.sendKeys(addressCity);
        return this;
    }

    private BillingSectionV3 clickEnableBillingRdb(){
        logger.info("Enabling Billing For: [" + countryPar + "]");
        enableBillingRdb.click();
        waitImplicitly(1000);
        return this;
    }
}