package com.almundo.browser.automation.pages.CheckOutPage;

import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

import static com.almundo.browser.automation.utils.PageUtils.waitElementForClickable;
import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

/**
 * Created by leandro.efron on 25/11/2016.
 */
public class PaymentSection extends CheckOutPage {

    public PaymentSection(WebDriver driver) {
        super(driver);
    }

    private static WebElement paymentQtySelected = null;
    private static WebElement creditCardSelected = null;

    //############################################### Locators ##############################################

    @FindBy(id = "card_holder")
    public WebElement card_holder;

    @FindBy(id = "card_number")
    private WebElement card_number;

    @FindBy(id = "card_expire")
    public WebElement card_expire;

    @FindBy(id = "month_card_expire")
    private WebElement month_card_expire;

    @FindBy(id = "year_card_expire")
    private WebElement year_card_expire;

    @FindBy(id = "security_code")
    private WebElement security_code;

    @FindBy(id = "documentType")
    private WebElement documentType;

    @FindBy(id = "document_number")
    private WebElement document_number;

    @FindBy(id = "cardselect")
    public WebElement creditCardDdl;

    @FindBy(id = "cantselect")
    public WebElement paymentQtyDdl;

    @FindBy(css = "#changeFOP")
    public WebElement changeCardLnk;

    //############################################### Actions ###############################################

    public PaymentSection populatePaymentSection(JSONObject paymentData, String product){
        selectPaymentQty(paymentData.get("payment_qty").toString());
        selectCreditCard(paymentData.get("credit_card_code").toString());
        selectBank(paymentData.get("credit_card_name").toString());
        setCardHolder(paymentData.get("card_holder").toString());
        setCardNumber(paymentData.get("card_number").toString());
        if(product.contains("Hotels") || product.contains("Cars") || product.contains("Flights")) {
            selectMonthCardExpiration(paymentData.get("month_card_expire").toString());
            selectYearCardExpiration(paymentData.get("year_card_expire").toString());
        }else {
            setCardExpiration(paymentData.get("card_expire").toString());
        }
        setSecurityCode(paymentData.get("security_code").toString());
        if(isElementRequiered(checkOutPageElements, "documentType")) {
            selectDocumentType(paymentData.get("documentType").toString());
        }
        if(isElementRequiered(checkOutPageElements, "document_number_card")) {
            setDocumentNumber(paymentData.get("document_number").toString());
        }
        return this;
    }

    public void selectPaymentQty(String qty) {
        List<WebElement> paymentList = driver.findElements(By.cssSelector(".payment"));
        boolean found = false;

        for (WebElement payment : paymentList) {
            if(payment.getText().replace("\n", " ").contains(qty)) {
                PageUtils.scrollToElement(driver, payment);
                PageUtils.scrollToCoordinate(driver, -230);
                logger.info("Selecting [" + qty + "] payment/s option");
                payment.click();
                found = true;
                paymentQtySelected = payment;
                break;
            }
        }
        Assert.assertTrue(found, "Payment [" + qty + "] " + "is not displayed");
    }

    public void selectCreditCard(String creditCardCode) {
        List<WebElement> creditCardList = driver.findElements(By.cssSelector(".payment-method-info .logo"));
        boolean found = false;
        for (WebElement creditCard : creditCardList) {
            if(creditCard.getAttribute("alt").equals(creditCardCode)) {
                logger.info("Getting [" + creditCard.getAttribute("alt").toString() + "] credit card row");
                creditCard.click();
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, "Credit Card [" + creditCardCode + "] " + "is not displayed");
    }

    public void selectBank(String bankName) {
        List<WebElement> bankList = driver.findElements(By.cssSelector(".bank .header-bank .logo-banks"));
        boolean found = false;

        for (WebElement bank : bankList) {
            if(bank.getAttribute("alt").toString().equals(bankName)) {
                logger.info("Selecting [" + bank.getAttribute("alt").toString() + "] bank option");
                bank.click();
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, "Bank [" + bankName + "] " + "is not displayed");
    }

    private void setCardHolder(String cardHolder) {
        logger.info("Entering Titular de la tarjeta: [" + cardHolder + "]");
        card_holder.clear();
        card_holder.sendKeys(cardHolder);
    }

    private void setCardNumber(String cardNumber) {
        logger.info("Entering Número de tu tarjeta: [" + cardNumber + "]");
        card_number.clear();
        card_number.sendKeys(cardNumber);
    }

    private void setCardExpiration(String expDate) {
        logger.info("Entering Fecha de vencimiento: [" + expDate + "]");
        card_expire.clear();
        card_expire.sendKeys(expDate);
    }

    private void selectMonthCardExpiration(String monthCardExpiration) {
        logger.info("Selecting Fecha de vencimiento - Mes: [" + monthCardExpiration + "]");
        Select selectMonthCardExpiration = new Select (month_card_expire);
        selectMonthCardExpiration.selectByVisibleText(monthCardExpiration);
    }

    private void selectYearCardExpiration(String yearCardExpiration) {
        logger.info("Selecting Fecha de vencimiento - Año: [" + yearCardExpiration + "]");
        Select selectYearCardExpiration = new Select (year_card_expire);
        selectYearCardExpiration.selectByVisibleText(yearCardExpiration);
    }

    private void setSecurityCode(String code) {
        logger.info("Entering Código de Seguridad: [" + code + "]");
        security_code.clear();
        security_code.sendKeys(code);
    }

    private void selectDocumentType(String documentTypeSelected) {
        logger.info("Selecting Tipo de Documento: [" + documentTypeSelected + "]");
        Select documentTypeSelect = new Select(documentType);
        documentTypeSelect.selectByVisibleText(documentTypeSelected);
    }

    private void setDocumentNumber(String documentNumber) {
        logger.info("Entering Número de Documento: [" + documentNumber + "]");
        document_number.clear();
        document_number.sendKeys(documentNumber);
    }

    public void selectOtherPayment(String creditCardName, String paymentQty) {
        logger.info("Selecting Credit Card: [" + creditCardName + "]");
        Select selectCreditCard = new Select (creditCardDdl);
        selectCreditCard.selectByVisibleText(creditCardName);

        logger.info("Selecting Payment Quantity: [" + paymentQty + "]");
        Select selectPaymentQty = new Select (paymentQtyDdl);
        selectPaymentQty.selectByVisibleText(paymentQty);
    }

    public PaymentSection selectPaymentOption(JSONObject paymentData, String product) {
        switch(paymentData.get("credit_card_name").toString()){
            case "cash":
                logger.info("------------- Payment Option: CASH  -------------");
                selectPayment("Pago en efectivo");
                break;
            case "deposit":
                logger.info("------------- Payment Option: DEPOSIT  -------------");
                selectPayment("Depósito");
                break;
            case "transfer":
                logger.info("------------- Payment Option: TRANSFER  -------------");
                selectPayment("Transferencia");
                break;
            case "booking24":
                logger.info("------------- Payment Option: BOOKING 24HS.  -------------");
                selectPayment("Reserva por 24 hs.");
                break;
            default:
                logger.info("------------- Payment Option: CREDIT CARD  -------------");
                populatePaymentSection(paymentData, product);
        }
        return this;
    }

    public PaymentSection selectPayment(String paymentOptionSelected){
        List<WebElement> paymentOptions = driver.findElements(By.cssSelector(".label--inline-block.epp-space-right-16.cards__definition__label.text--bold"));
        for(WebElement paymentOption : paymentOptions){
            if(paymentOption.getText().equals(paymentOptionSelected)){
                paymentOption.click();
                break;
            }
        }
        return this;
    }

    public PaymentSection clickChangeCardLink(){
        waitElementForClickable(driver, changeCardLnk, 10, "Cambiar Forma de Pago link");
        waitImplicitly(1000);
        PageUtils.scrollToElement(driver, changeCardLnk);
        logger.info("Clicking on Cambiar Forma de Pago link");
        changeCardLnk.click();
        return this;
    }
}