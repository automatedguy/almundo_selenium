package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by gabrielcespedes on 26/06/17.
 */
public class VisaDebitCardDataV3 extends CheckOutPageV3 {

    public VisaDebitCardDataV3(WebDriver driver) {
        super(driver);
    }

    private DataManagement dataManagement = new DataManagement();

    /********************* Locators *********************/

    JSONObject paymentDataObject = new JSONObject();

    /********************* Actions *********************/
    private void setCardNumber(String cardNumber) {
        PageUtils.waitElementForVisibility(driver, By.cssSelector("#credit_card_number"), 5, "Debit Card Number field");
        WebElement cardNumberField = driver.findElement(By.cssSelector("#credit_card_number"));
        logger.info("Entering Número de tu tarjeta: [" + cardNumber + "]");
        cardNumberField.clear();
        cardNumberField.sendKeys(cardNumber);
    }

    private void setCardHolder(String cardHolder) {
        WebElement cardHolderField = driver.findElement(By.cssSelector("#credit_card_owner"));
        logger.info("Entering Titular de la tarjeta: [" + cardHolder + "]");
        cardHolderField.clear();
        cardHolderField.sendKeys(cardHolder);
    }

    private void setMonthCardExpiration(String monthCardExpiration) {
        WebElement cardMonthExpField = driver.findElement(By.cssSelector("#visaDebit .container-month > select"));
        logger.info("Selecting Fecha de vencimiento - Mes: [" + monthCardExpiration + "]");
        Select selectMonthCardExpiration = new Select (cardMonthExpField);
        selectMonthCardExpiration.selectByVisibleText(monthCardExpiration);
    }

    private void setYearCardExpiration(String yearCardExpiration) {
        WebElement cardYearExpField = driver.findElement(By.cssSelector("#visaDebit .container-year > select"));
        logger.info("Selecting Fecha de vencimiento - Año: [" + yearCardExpiration + "]");
        Select selectYearCardExpiration = new Select (cardYearExpField);
        selectYearCardExpiration.selectByVisibleText(yearCardExpiration);
    }

    private void setSecurityCode(String code) {
        WebElement securityCodeField = driver.findElement(By.cssSelector("#credit_card_security_code"));
        logger.info("Entering Código de Seguridad: [" + code + "]");
        securityCodeField.clear();
        securityCodeField.sendKeys(code);
    }

    private void selectDocumentType(String documentType) {
        WebElement documentTypeField = driver.findElement(By.cssSelector("#document_type"));
        logger.info("Selecting Tipo de Documento: [" + documentType + "]");
        Select documentTypeSelect = new Select(documentTypeField);
        documentTypeSelect.selectByVisibleText(documentType);
    }

    private void setDocumentNumber(String documentNumber) {
        WebElement documentNumberField = driver.findElement(By.cssSelector("#number"));
        logger.info("Entering Número de Documento: [" + documentNumber + "]");
        documentNumberField.clear();
        documentNumberField.sendKeys(documentNumber);
    }

    public void populateDebitCardData(String paymentData){
        logger.info("Getting payment data for: " + "[" + paymentData + "]");
        dataManagement.setPaymentList();
        paymentDataObject = dataManagement.setPaymentData(paymentData);

        logger.info("------------- Filling Payment Section -------------");
        if(inputDef.isRequired("payments","credit_card_number",0)){
            setCardNumber(paymentDataObject.get("Numero_de_tarjeta").toString());}

        if(inputDef.isRequired("payments","credit_card_owner",0)){
            setCardHolder(paymentDataObject.get("Titular_de_la_tarjeta").toString());}

        if(inputDef.isRequired("payments","credit_card_expiration",0)){
            setMonthCardExpiration(paymentDataObject.get("Fecha_de_vencimiento_mes").toString());
            setYearCardExpiration(paymentDataObject.get("Fecha_de_vencimiento_ano").toString());}

        if(inputDef.isRequired("payments","credit_card_security_code",0)){
            setSecurityCode(paymentDataObject.get("codigo_seguridad").toString());}

        if (inputDef.isRequired("payments", "document", 0)) {
            selectDocumentType(paymentDataObject.get("Tipo_de_doc").toString());
            setDocumentNumber(paymentDataObject.get("Numero_de_doc").toString());
        }
    }
}