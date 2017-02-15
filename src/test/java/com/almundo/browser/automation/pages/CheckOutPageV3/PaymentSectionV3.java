package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.PageUtils;
import junit.framework.Assert;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Created by leandro.efron on 25/11/2016.
 */
public class PaymentSectionV3 extends CheckOutPage {

    public PaymentSectionV3(WebDriver driver) {
        super(driver);
    }

    public static JSONObject paymentList = null;
    public static JSONObject paymentData;

    private static WebElement paymentSelected = null;
    private static WebElement bankSelected = null;

    //############################################### Locators ##############################################

    @FindBy(css = ".card-container-1")
    public WebElement credit_card_container_1;

    @FindBy(css = ".card-container-2")
    public WebElement credit_card_container_2;

    @FindBy(id = "several-cards")
    public WebElement serveralCardsCbx;

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







    public void clickSeveralCardsCbx () {
        if (!serveralCardsCbx.isSelected()) {
            logger.info("Clicking on [Con 2 Tarjetas] checkbox");
            serveralCardsCbx.click();
        }
        PageUtils.waitElementForVisibility(driver, credit_card_container_1, 15, "Card container 1");
        PageUtils.waitElementForVisibility(driver, credit_card_container_2, 15, "Card container 2");
    }

    public void selectPayment(String paymentNumber, String container) {
//        WebElement paymentElement = driver.findElement(By.cssSelector(".installment-" + paymentOption));
//        logger.info("Selecting Payment Quantity: [" + paymentOption + "]");
//        PageUtils.scrollToElement(driver, paymentElement);
//        paymentElement.click();
//
//        PageUtils.waitImplicitly(1000);


        /////2
//        List<WebElement> results = driver.findElements(By.cssSelector("div[class^='first-row installment-']"));
//
//        for (int i = 0; i < results.size(); ++i) {
//            if (results.get(i).getText().equals(paymentQty)) {
//                logger.info("Selecting Payment Quantity: [" + paymentQty + "]");
//                PageUtils.scrollToElement(driver, results.get(i));
//                results.get(i).click();
//                paymentSelected = payments.get(i);
//                break;
//            }
//        }


        //////3
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

    public void setCardNumber(String cardNumber, String container) {
        WebElement cardNumberField = driver.findElement(By.cssSelector(container + " #card_number"));
        logger.info("Entering Número de tu tarjeta: [" + cardNumber + "]");
        cardNumberField.clear();
        cardNumberField.sendKeys(cardNumber);
    }

    public void setCardHolder(String cardHolder, String container) {
        WebElement cardHolderField = driver.findElement(By.cssSelector(container + " #card_holder"));
        logger.info("Entering Titular de la tarjeta: [" + cardHolder + "]");
        cardHolderField.clear();
        cardHolderField.sendKeys(cardHolder);
    }

    public void setCardExpiration(String expDate) {
        logger.info("Entering Fecha de vencimiento: [" + expDate + "]");
        List<WebElement> creditCardFieldList = driver.findElements(By.cssSelector(".field.ng-pristine.ng-untouched.ng-empty.ng-invalid.ng-invalid-required.ng-valid-pattern.ng-valid-maxlength"));
        creditCardFieldList.get(0).clear();
        creditCardFieldList.get(0).sendKeys(expDate);
    }

    public void selectMonthCardExpiration(String monthCardExpiration, String container) {
        WebElement cardMonthExpField = driver.findElement(By.cssSelector(container + " #card_month_expire"));
        logger.info("Selecting Fecha de vencimiento - Mes: [" + monthCardExpiration + "]");
        Select selectMonthCardExpiration = new Select (cardMonthExpField);
        selectMonthCardExpiration.selectByVisibleText(monthCardExpiration);
    }

    public void selectYearCardExpiration(String yearCardExpiration, String container) {
        WebElement cardYearExpField = driver.findElement(By.cssSelector(container + " #card_year_expire"));
        logger.info("Selecting Fecha de vencimiento - Año: [" + yearCardExpiration + "]");
        Select selectYearCardExpiration = new Select (cardYearExpField);
        selectYearCardExpiration.selectByVisibleText(yearCardExpiration);
    }

    public void setSecurityCode(String code, String container) {
        WebElement securityCodeField = driver.findElement(By.cssSelector(container + " #security_code"));
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

    public static void getPaymentList()  {
        paymentList = JsonRead.getJsonDataObject(jsonDataObject, "payment", countryPar.toLowerCase() + "_data.json");
    }

    public static void getPaymentData(String dataSet)  {
        paymentData = JsonRead.getJsonDataObject(paymentList, dataSet, countryPar.toLowerCase() + "_data.json");
    }
}