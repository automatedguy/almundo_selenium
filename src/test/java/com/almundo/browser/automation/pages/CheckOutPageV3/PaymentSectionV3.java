package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.utils.PageUtils;
import junit.framework.Assert;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

/**
 * Created by leandro.efron on 25/11/2016.
 */
public class PaymentSectionV3 extends CheckOutPageV3 {

    public PaymentSectionV3(WebDriver driver) {
        super(driver);
    }

    private static WebElement paymentSelected = null;
    private static WebElement bankSelected = null;

    private DataManagement dataManagement = new DataManagement();
    JSONObject paymentDataObject = new JSONObject();

    //############################################### Locators ##############################################

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

    //############################################### Actions ###############################################


    public PaymentSectionV3 populatePaymentSectionNew(String paymentData, String container) {

        dataManagement.getPaymentList();

        Select creditCardSelect = new Select(creditCardDdl);
        Select bankSelect = new Select(bankDdl);
        Select paymentSelect = new Select(paymentDdl);

        logger.info("------------- Selecting type of Payment "+ container + "-------------");

        if(paymentData.equals("random")) {
            List<WebElement> availableCardsElements = creditCardSelect.getOptions();

            for (WebElement availableCard : availableCardsElements) {
                if (availableCard.getText().equals("Visa")) {
                    selectCreditCardCombo(creditCardSelect, "Visa");
                    paymentDataObject = dataManagement.getPaymentData("1_visa_visa");
                    break;
                } else if (availableCard.getText().equals("Mastercard")) {
                    selectCreditCardCombo(creditCardSelect, "Mastercard");
                    paymentDataObject = dataManagement.getPaymentData("1_master_master");
                    break;
                } else if (availableCard.getText().equals("American Express")) {
                    selectCreditCardCombo(creditCardSelect, "American Express");
                    paymentDataObject = dataManagement.getPaymentData("1_amex_amex");
                    break;
                }
            }


        } else {
            paymentDataObject = dataManagement.getPaymentData(paymentData);

            selectCreditCardCombo(creditCardSelect, paymentDataObject.get("credit_card_name").toString());
        }

        selectBankCombo(bankSelect);
        selectPaymentCombo(paymentSelect);

        logger.info("------------- Filling Payment Section -------------");
        setCardNumber(paymentDataObject.get("card_number").toString(), container);
        setCardHolder(paymentDataObject.get("card_holder").toString(), container);
        selectMonthCardExpiration(paymentDataObject.get("month_card_expire").toString(), container);
        selectYearCardExpiration(paymentDataObject.get("year_card_expire").toString(), container);
        setSecurityCode(paymentDataObject.get("security_code").toString(), container);
        if(isElementRequiered(checkOutPageElements, "documentType")) {
            selectDocumentType(paymentDataObject.get("documentType").toString(), container);
        }
        if(isElementRequiered(checkOutPageElements, "document_number_card")) {
            setDocumentNumber(paymentDataObject.get("document_number").toString(), container);
        }
        return this;
    }

    public PaymentSectionV3 populatePaymentSection(JSONObject paymentData, String container) {

        dataManagement.getPaymentList();

        Select creditCardSelect = new Select(creditCardDdl);
        Select bankSelect = new Select(bankDdl);
        Select paymentSelect = new Select(paymentDdl);

        logger.info("------------- Selecting type of Payment "+ container + "-------------");

        if(paymentData.toString().contains("random")) {
            List<WebElement> availableCardsElements = creditCardSelect.getOptions();

            for (WebElement availableCard : availableCardsElements) {
                if (availableCard.getText().equals("Visa")) {
                    selectCreditCardCombo(creditCardSelect, "Visa");
                    paymentData = dataManagement.getPaymentData("1_visa_visa");
                    break;
                } else if (availableCard.getText().equals("Mastercard")) {
                    selectCreditCardCombo(creditCardSelect, "Mastercard");
                    paymentData = dataManagement.getPaymentData("1_master_master");
                    break;
                } else if (availableCard.getText().equals("American Express")) {
                    selectCreditCardCombo(creditCardSelect, "American Express");
                    paymentData = dataManagement.getPaymentData("1_amex_amex");
                    break;
                }
            }

            selectBankCombo(bankSelect);
            selectPaymentCombo(paymentSelect);
        } else {
            selectPayment(paymentData.get("payment_qty").toString(), container);
            selectBank(paymentData.get("bank_name").toString(), container);
            selectCreditCard(paymentData.get("credit_card_name").toString(), container);

        }

        logger.info("------------- Filling Payment Section -------------");
        setCardNumber(paymentData.get("card_number").toString(), container);
        setCardHolder(paymentData.get("card_holder").toString(), container);
        selectMonthCardExpiration(paymentData.get("month_card_expire").toString(), container);
        selectYearCardExpiration(paymentData.get("year_card_expire").toString(), container);
        setSecurityCode(paymentData.get("security_code").toString(), container);
        if(isElementRequiered(checkOutPageElements, "documentType")) {
            selectDocumentType(paymentData.get("documentType").toString(), container);
        }
        if(isElementRequiered(checkOutPageElements, "document_number_card")) {
            setDocumentNumber(paymentData.get("document_number").toString(), container);
        }
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

    public void selectPayment(String paymentNumber, String container) {
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
        PageUtils.waitImplicitly(1000);
    }

    public void selectBank(String bankName, String container) {
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

    public void selectCreditCard(String cardName, String container) {
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

    public void selectCreditCardCombo(Select creditCardSelect, String cardName) {
        logger.info("Selecting Card: [" + cardName + "]");
        creditCardSelect.selectByVisibleText(cardName);
    }

    public void selectBankCombo(Select bankSelect) {
        int random=0;
        do{
            random = new Random().nextInt(bankSelect.getOptions().size());
        } while(bankSelect.getOptions().size()!= 1 && random==0);

        logger.info("Selecting Bank: [" + bankSelect.getOptions().get(random).getText() + "]");
        bankSelect.selectByIndex(random);
    }

    public void selectPaymentCombo(Select paymentSelect) {
        int random=0;
        do{
            random = new Random().nextInt(paymentSelect.getOptions().size());
        } while(paymentSelect.getOptions().size()!= 1 && random==0);

        logger.info("Selecting Payment: [" + paymentSelect.getOptions().get(random).getText() + "]");
        paymentSelect.selectByIndex(random);
    }

    public void setCardNumber(String cardNumber, String container) {
        WebElement cardNumberField = driver.findElement(By.cssSelector(container + " #credit_card_number"));
        logger.info("Entering Número de tu tarjeta: [" + cardNumber + "]");
        cardNumberField.clear();
        cardNumberField.sendKeys(cardNumber);
    }

    public void setCardHolder(String cardHolder, String container) {
        WebElement cardHolderField = driver.findElement(By.cssSelector(container + " #credit_card_owner"));
        logger.info("Entering Titular de la tarjeta: [" + cardHolder + "]");
        cardHolderField.clear();
        cardHolderField.sendKeys(cardHolder);
    }

    public void selectMonthCardExpiration(String monthCardExpiration, String container) {
        WebElement cardMonthExpField = driver.findElement(By.cssSelector(container + " .month"));
        logger.info("Selecting Fecha de vencimiento - Mes: [" + monthCardExpiration + "]");
        Select selectMonthCardExpiration = new Select (cardMonthExpField);
        selectMonthCardExpiration.selectByVisibleText(monthCardExpiration);
    }

    public void selectYearCardExpiration(String yearCardExpiration, String container) {
        WebElement cardYearExpField = driver.findElement(By.cssSelector(container + " .year"));
        logger.info("Selecting Fecha de vencimiento - Año: [" + yearCardExpiration + "]");
        Select selectYearCardExpiration = new Select (cardYearExpField);
        selectYearCardExpiration.selectByVisibleText(yearCardExpiration);
    }

    public void setSecurityCode(String code, String container) {
        WebElement securityCodeField = driver.findElement(By.cssSelector(container + " #credit_card_security_code"));
        logger.info("Entering Código de Seguridad: [" + code + "]");
        securityCodeField.clear();
        securityCodeField.sendKeys(code);
    }

    public void selectDocumentType(String documentType, String container) {
        WebElement documentTypeField = driver.findElement(By.cssSelector(container + " #document_type"));
        logger.info("Selecting Tipo de Documento: [" + documentType + "]");
        Select documentTypeSelect = new Select(documentTypeField);
        documentTypeSelect.selectByVisibleText(documentType);
    }

    public void setDocumentNumber(String documentNumber, String container) {
        WebElement documentNumberField = driver.findElement(By.cssSelector(container + " #number"));
        logger.info("Entering Número de Documento: [" + documentNumber + "]");
        documentNumberField.clear();
        documentNumberField.sendKeys(documentNumber);
    }
}