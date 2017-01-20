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
 * Created by gabrielcespedes on 29/11/16.
 */
public class BillingSectionV3 extends CheckOutPage {

    public BillingSectionV3(WebDriver driver) {
        super(driver);
    }

    private static JSONObject billingsList = null;
    public static JSONObject billingData = null;

    //############################################### Locators ##############################################

    @FindBy(id = "fiscalNameTxt")
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

    @FindBy(css = "billing-form > div > div:nth-child(5) > div.schema-form-section.col-12-xs.col-12-sm.col-7-md > div > select")
    private WebElement addressStateDdl;

    @FindBy(id = "city")
    private WebElement addressCityTxt;


    //############################################### Actions ##############################################

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

    public BillingSectionV3 setBillingAddressTxt(String billingAddressTxt) {
        logger.info("Entering Domicilio: [" + billingAddressTxt + "]");
        this.billingAddressTxt.clear();
        this.billingAddressTxt.sendKeys(billingAddressTxt);
        return this;
    }

    public BillingSectionV3 setAddressNumberTxt(String addressNumberTxt) {
        logger.info("Entering Número: [" + addressNumberTxt + "]");
        List<WebElement> addressNumberList = driver.findElements(By.id("number"));
        addressNumberList.get(addressNumberList.size() -2).sendKeys(addressNumberTxt);
        return this;
    }

    public BillingSectionV3 setAddressFloorTxt(String addressFloorTxt) {
        logger.info("Entering Piso: [" + addressFloorTxt + "]");
        this.addressFloorTxt.clear();
        this.addressFloorTxt.sendKeys(addressFloorTxt);
        return this;
    }

    public BillingSectionV3 setAddressDepartmentTxt(String addressDepartmentTxt) {
        logger.info("Entering Departamento: [" + addressDepartmentTxt + "]");
        this.addressDepartmentTxt.clear();
        this.addressDepartmentTxt.sendKeys(addressDepartmentTxt);
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

    public BillingSectionV3 setAddressCityTxt(String addressCityTxt) {
        logger.info("Entering Ciudad: [" + addressCityTxt + "]");
        this.addressCityTxt.clear();
        this.addressCityTxt.sendKeys(addressCityTxt);
        return this;
    }
    
    public static void getBillingList()  {
        billingsList = JsonRead.getJsonDataObject(jsonDataObject, "billings", countryPar.toLowerCase() + "_data.json");
    }

    public static void getBillingData(String dataSet)  {
        billingData = JsonRead.getJsonDataObject(billingsList, dataSet, countryPar.toLowerCase() + "_data.json");
    }
}