package com.almundo.browser.automation.pages.CheckOutPageV3;

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

    //############################################### Locators ##############################################

    @FindBy(css = ".card-container-1")
    public WebElement credit_card_container_1;

    @FindBy(css = ".card-container-2")
    public WebElement credit_card_container_2;

    @FindBy(id = "several-cards")
    public WebElement serveralCardsCbx;

    //@FindBy(id = "cbo_credit_card")
    @FindBy(id = "card-combo")
    public Select cardComboDdl;

    //@FindBy(id = "cbo_financial_entity")
    @FindBy(id = "banco-combo")
    public Select bancoComboDdl;

    //@FindBy(id = "cbo_installment")
    @FindBy(id = "cuotas-combo")
    public Select cuotasComboDdl;

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


    public PaymentSectionV3 populatePaymentSection(JSONObject paymentData, String container, String product) {
        logger.info("------------- Selecting type of Payment "+ container + "-------------");
        selectPayment(paymentData.get("payment_qty").toString(), container);
        selectBank(paymentData.get("bank_name").toString(), container);
        selectCreditCard(paymentData.get("credit_card_name").toString(), container);
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

    public void selectCreditCardNew(String cardName, String container) {
        if(cardName!=null) {
            logger.info("Selecting Credit Card: [" + cardName + "]");
            cardComboDdl.selectByVisibleText(cardName);

        } else {
            int random = new Random().nextInt(cardComboDdl.getOptions().size());
            cardComboDdl.selectByIndex(random);
            logger.info("Selecting Credit Card: [" + cardComboDdl.getFirstSelectedOption().getText() + "]");
        }
    }

    public void selectPaymentNew(String paymentNumber, String container) {
        if(paymentNumber!=null) {
            logger.info("Selecting Payment: [" + paymentNumber + "]");
            cuotasComboDdl.selectByVisibleText(paymentNumber);

        } else {
            int random = new Random().nextInt(cuotasComboDdl.getOptions().size());
            cuotasComboDdl.selectByIndex(random);
            logger.info("Selecting Payment: [" + cuotasComboDdl.getFirstSelectedOption().getText() + "]");
        }
    }

    public void selectBankNew(String bankName, String container) {
        if(bankName!=null) {
            bancoComboDdl.selectByVisibleText(bankName);
            logger.info("Selecting Bank: [" + bankName + "]");
        } else {
            int random = new Random().nextInt(bancoComboDdl.getOptions().size());
            bancoComboDdl.selectByIndex(random);
            logger.info("Selecting Bank: [" + bancoComboDdl.getFirstSelectedOption().getText() + "]");
        }
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