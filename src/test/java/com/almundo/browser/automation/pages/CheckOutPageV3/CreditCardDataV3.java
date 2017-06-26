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
public class CreditCardDataV3 extends CheckOutPageV3 {

    public CreditCardDataV3(WebDriver driver) {
        super(driver);
    }

    private DataManagement dataManagement = new DataManagement();

    /********************* Locators *********************/

    JSONObject paymentDataObject = new JSONObject();

    /********************* Actions *********************/
    private void setCardNumber(String cardNumber, String container) {
        PageUtils.waitElementForVisibility(driver, By.cssSelector(container + " #credit_card_number"), 5, "Credit Card field");
        WebElement cardNumberField = driver.findElement(By.cssSelector(container + " #credit_card_number"));
        logger.info("Entering Número de tu tarjeta: [" + cardNumber + "]");
        cardNumberField.clear();
        cardNumberField.sendKeys(cardNumber);
    }

    private void setCardHolder(String cardHolder, String container) {
        WebElement cardHolderField = driver.findElement(By.cssSelector(container + " #credit_card_owner"));
        logger.info("Entering Titular de la tarjeta: [" + cardHolder + "]");
        cardHolderField.clear();
        cardHolderField.sendKeys(cardHolder);
    }

    private void setMonthCardExpiration(String monthCardExpiration, String container) {
        WebElement cardMonthExpField = driver.findElement(By.cssSelector(container + " .month"));
        logger.info("Selecting Fecha de vencimiento - Mes: [" + monthCardExpiration + "]");
        Select selectMonthCardExpiration = new Select (cardMonthExpField);
        selectMonthCardExpiration.selectByVisibleText(monthCardExpiration);
    }

    private void setYearCardExpiration(String yearCardExpiration, String container) {
        WebElement cardYearExpField = driver.findElement(By.cssSelector(container + " .year"));
        logger.info("Selecting Fecha de vencimiento - Año: [" + yearCardExpiration + "]");
        Select selectYearCardExpiration = new Select (cardYearExpField);
        selectYearCardExpiration.selectByVisibleText(yearCardExpiration);
    }

    private void setSecurityCode(String code, String container) {
        WebElement securityCodeField = driver.findElement(By.cssSelector(container + " #credit_card_security_code"));
        logger.info("Entering Código de Seguridad: [" + code + "]");
        securityCodeField.clear();
        securityCodeField.sendKeys(code);
    }

    private void selectDocumentType(String documentType, String container) {
        WebElement documentTypeField = driver.findElement(By.cssSelector(container + " #document_type"));
        logger.info("Selecting Tipo de Documento: [" + documentType + "]");
        Select documentTypeSelect = new Select(documentTypeField);
        documentTypeSelect.selectByVisibleText(documentType);
    }

    private void setDocumentNumber(String documentNumber, String container) {
        WebElement documentNumberField = driver.findElement(By.cssSelector(container + " #number"));
        logger.info("Entering Número de Documento: [" + documentNumber + "]");
        documentNumberField.clear();
        documentNumberField.sendKeys(documentNumber);
    }

    public void populateCreditCardData(String paymentData, String container){

        logger.info("Getting payment data for: " + "[" + paymentData + "]");
        dataManagement.getPaymentList();
        paymentDataObject = dataManagement.getPaymentData(paymentData);

        logger.info("------------- Filling Payment Section -------------");
        if(inputDef.isRequired("payments","credit_card_number",0)){
            setCardNumber(paymentDataObject.get("card_number").toString(), container);}

        if(inputDef.isRequired("payments","credit_card_owner",0)){
            setCardHolder(paymentDataObject.get("card_holder").toString(), container);}

        if(inputDef.isRequired("payments","credit_card_expiration",0)){
            setMonthCardExpiration(paymentDataObject.get("month_card_expire").toString(), container);
            setYearCardExpiration(paymentDataObject.get("year_card_expire").toString(), container);}

        if(inputDef.isRequired("payments","credit_card_security_code",0)){
            setSecurityCode(paymentDataObject.get("security_code").toString(), container);}

        if(inputDef.isRequired("payments","document",0)){
            selectDocumentType(paymentDataObject.get("documentType").toString(), container);
            setDocumentNumber(paymentDataObject.get("document_number").toString(), container);}
    }
}
