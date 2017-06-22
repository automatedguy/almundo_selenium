package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

/**
 * Created by leandro.efron on 25/11/2016.
 */
public class PaymentSectionV3 extends CheckOutPageV3 {

    public PaymentSectionV3(WebDriver driver) {
        super(driver);
    }

    private WebElement paymentSelected = null;
    private WebElement bankSelected = null;

    private DataManagement dataManagement = new DataManagement();
    JSONObject paymentDataObject = new JSONObject();

    //############################################### Locators ##############################################

    @FindBy(css = ".option-section input[value='creditCard']")
    public WebElement creditCardRb;

    @FindBy(css = ".option-section input[value='transfer']")
    public WebElement trasnferRb;

    @FindBy(css = ".card-container-1")
    public WebElement credit_card_container_1;

    @FindBy(css = ".card-container-2")
    public WebElement credit_card_container_2;

    @FindBy(id = "several-cards")
    public WebElement serveralCardsCbx;

    @FindBy(id = "cbo_credit_card")
    private WebElement creditCardDdl;

    @FindBy(id = "cbo_financial_entity")
    public WebElement bankDdl;

    @FindBy(id = "cbo_installment")
    public WebElement paymentDdl;

    @FindBy(css = "credit-card-form > div > div > div > div:nth-child(3) > div > input")
    public WebElement card_holder;

    @FindBy(id = "card_number")
    private WebElement card_number;

    @FindBy(id = "card_expire")
    public WebElement card_expire;

    @FindBy(css = "div:nth-child(4)>div:nth-child(1)>div>div.col-6-xs.month-container>select")
    private WebElement month_card_expire;

    @FindBy(css = "div:nth-child(4)>div:nth-child(1)>div>div:nth-child(2)>select")
    private WebElement year_card_expire;

    @FindBy(css = "credit-card-form > div > div > div > div:nth-child(4) > div:nth-child(2) > input")
    private WebElement security_code;

    @FindBy(id = "documentType")
    private WebElement documentType;

    @FindBy(id = "document_number")
    private WebElement document_number;

    @FindBy(css = ".change-card")
    public WebElement changeCardLnk;

    public PaymentSelectorV3 paymentSelectorV3() {
        return initPaymentSelectorV3();
    }

    //############################################### Actions ###############################################

    public void clickChangeCardLink() {
        PageUtils.scrollToElement(driver, changeCardLnk);
        logger.info("Clicking on Cambiar Forma de Pago link");
        changeCardLnk.click();
    }

    public PaymentSectionV3 populatePaymentSectionV3(String paymentData, String container) {
        dataManagement.getPaymentList();

        selectPaymentOption();

        Select creditCardSelect = new Select(creditCardDdl);
        Select bankSelect = new Select(bankDdl);
        Select paymentSelect = new Select(paymentDdl);

        logger.info("------------- Selecting type of Payment "+ container + "-------------");

        if(paymentData.equals("random")) {
            List<WebElement> availableCardsElements = creditCardSelect.getOptions();
            availableCardsElements.remove(0);

            for (WebElement availableCard : availableCardsElements) {
                if (availableCard.getText().equals("Visa")) {
                    paymentDataObject = dataManagement.getPaymentData("1_visa_visa");
                    setCreditCardCombo(creditCardSelect, "Visa");
                    break;
                } else if (availableCard.getText().equals("Mastercard")) {
                    paymentDataObject = dataManagement.getPaymentData("1_master_master");
                    setCreditCardCombo(creditCardSelect, "Mastercard");
                    break;
                } else if (availableCard.getText().equals("American Express")) {
                    paymentDataObject = dataManagement.getPaymentData("1_amex_amex");
                    setCreditCardCombo(creditCardSelect, "American Express");
                    break;
                }
            }
            setBankCombo(bankSelect, "random");
        } else {
            paymentDataObject = dataManagement.getPaymentData(paymentData);
            setCreditCardCombo(creditCardSelect, paymentDataObject.get("credit_card_name").toString());
            setBankCombo(bankSelect, paymentDataObject.get("credit_card_code").toString());
        }

        if(paymentDdl.isDisplayed()) {
            setPaymentCombo(paymentSelect);
        }

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

        return this;
    }


    public void clickSeveralCardsCbx () {
        if (!serveralCardsCbx.isSelected()) {
            logger.info("Clicking on [Con 2 Tarjetas] checkbox");
            serveralCardsCbx.click();
        }
        PageUtils.waitElementForVisibility(driver, credit_card_container_1, 15, "Card container 1");
        PageUtils.waitElementForVisibility(driver, credit_card_container_2, 15, "Card container 2");
    }

    public void setPayment(String paymentNumber, String container) {
        PageUtils.waitElementForVisibility(driver, By.cssSelector(container + " .payment .monthly-payment>strong"), 40, "Payments");
        List<WebElement> results = driver.findElements(By.cssSelector(container + " .payment .monthly-payment>strong"));
        List<WebElement> payments = driver.findElements(By.cssSelector(container + " .payment"));

        for (int i = 0; i < results.size(); ++i) {
            if (results.get(i).getText().equals(paymentNumber)) {
                logger.info("Selecting Payment Quantity: [" + paymentNumber + "]");
                PageUtils.scrollToElement(driver, results.get(i));
                results.get(i).click();
                paymentSelected = payments.get(i);
                break;
            }
        }

    }

    public void setBank(String bankName, String container) {
        List<WebElement> results = paymentSelected.findElements(By.cssSelector(container + " .header-bank .logo"));
        List<WebElement> banks = paymentSelected.findElements(By.cssSelector(container + " .bank"));
        boolean isBankSelected = false;

        for (int i = 0; i < results.size(); ++i) {
            if (results.get(i).getAttribute("alt").equals(bankName)) {
                logger.info("Selecting Bank: [" + bankName + "]");
                PageUtils.scrollToElement(driver, results.get(i));
                PageUtils.waitImplicitly(1000);
                results.get(i).click();
                isBankSelected = true;
                bankSelected = banks.get(i);
                break;
            }
        }
        if(isBankSelected == false) {
            Assert.fail("Bank was not selected");
        }
    }

    public void setCreditCard(String cardName, String container) {
        List<WebElement> cardNames = bankSelected.findElements(By.cssSelector(container + " .cards .logo .logo"));

        for(WebElement cardNameResult : cardNames){
            if (cardNameResult.getAttribute("alt").equals(cardName)) {
                logger.info("Selecting Card: [" + cardName + "]");
                PageUtils.scrollToElement(driver, cardNameResult);
                cardNameResult.click();
                break;
            }
        }
    }

    private void setCreditCardCombo(Select creditCardSelect, String cardName) {
        logger.info("Selecting Card: [" + cardName + "]");
        creditCardSelect.selectByVisibleText(cardName);
    }

    private void setBankCombo(Select bankSelect, String bankName) {

        switch(bankName){
            case "random":
                int random;
                do{
                    random = new Random().nextInt(bankSelect.getOptions().size());
                } while(bankSelect.getOptions().size()!= 1 && random==0);

                logger.info("Selecting Bank: [" + bankSelect.getOptions().get(random).getText() + "]");
                bankSelect.selectByIndex(random);
                break;

            default:
                logger.info("Selecting Bank: [" + bankName + "]");
                bankSelect.selectByValue(bankName);
        }

    }

    private void setPaymentCombo(Select paymentSelect) {
        int random;
        do{
            random = new Random().nextInt(paymentSelect.getOptions().size());
        } while(paymentSelect.getOptions().size()!= 1 && random==0);

        logger.info("Selecting Payment: [" + paymentSelect.getOptions().get(random).getText() + "]");
        paymentSelect.selectByIndex(random);
    }

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


    public PaymentSectionV3 selectPaymentOption() {
        paymentSelectorV3().selectOneCreditCardRdb();
        return this;
    }

    public void selectPayment(String paymentOptionSelected){
        List<WebElement> paymentOptions = driver.findElements(By.cssSelector(".option-section input"));
        for(WebElement paymentOption : paymentOptions){
            if(paymentOption.getAttribute("value").equals(paymentOptionSelected)){
                paymentOption.click();
                break;
            }
        }
    }
}