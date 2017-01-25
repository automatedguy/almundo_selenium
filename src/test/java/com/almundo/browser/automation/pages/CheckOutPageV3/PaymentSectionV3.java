package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.PageUtils;
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

    private WebElement paymentSelected = null;
    private WebElement bankSelected = null;

    //############################################### Locators ##############################################

    @FindBy(css = "credit-card-grid > div > div:nth-child(1) > div:nth-child(22) > div > div > div.cards.ng-scope.in.collapse > label")
    public WebElement creditCardPayment;

    @FindBy(css = "credit-card-form > div > div > div > div:nth-child(3) > div > input")
    public WebElement card_holder;

    @FindBy(css = "credit-card-form > div > div > div > div:nth-child(2) > div > label > input")
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

    public void selectPaymentQtyOption(int index) {
        //List<WebElement> results = driver.findElements(By.cssSelector(".monthly-payment"));
        List<WebElement> results = driver.findElements(By.cssSelector("..payment"));

        PageUtils.scrollToElement(driver, results.get(0));
        PageUtils.scrollToCoordinate(driver, -200);
        results.get(index).click();
    }

    public void selectPayment(String paymentNumber) {
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
        List<WebElement> results = driver.findElements(By.cssSelector(".payment .monthly-payment>strong"));
        List<WebElement> payments = driver.findElements(By.cssSelector(".payment"));

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

    public void selectBank(String bankName) {
        List<WebElement> results = paymentSelected.findElements(By.cssSelector(".header-bank .logo"));
        List<WebElement> banks = paymentSelected.findElements(By.cssSelector(".bank"));

        for (int i = 0; i < results.size(); ++i) {
            if (results.get(i).getAttribute("alt").equals(bankName)) {
                logger.info("Selecting Bank: [" + bankName + "]");
                PageUtils.scrollToElement(driver, results.get(i));
                results.get(i).click();
                bankSelected = banks.get(i);
                break;
            }
        }
    }

    public void selectCreditCard(String cardName) {
        List<WebElement> cardNames = bankSelected.findElements(By.cssSelector(".cards .logo .logo"));

        for(WebElement cardNameResult : cardNames){
            if (cardNameResult.getAttribute("alt").equals(cardName)) {
                logger.info("Selecting Card: [" + cardName + "]");
                PageUtils.scrollToElement(driver, cardNameResult);
                cardNameResult.click();
                break;
            }
        }
    }

    public void selectBankOption(String cardName) {
        List<WebElement> cardNames = driver.findElements(By.cssSelector(".logo.ng-scope"));

        for (int i = 0; i < cardNames.size(); ++i) {
            WebElement cardNameElement = cardNames.get(i);

            PageUtils.scrollToCoordinate(driver, -200);
            if (cardNameElement.getAttribute("alt").equals(cardName)) {
                logger.info("Selecting card provider: [" + cardName + "]");
                cardNameElement.click();
                PageUtils.waitImplicitly(1000);
                logger.info("Selecting card name: [" + cardName + "]");
                creditCardPayment.click();
                break;
            }
        }
    }

    public void setCardNumber(String cardNumber) {
        logger.info("Entering Número de tu tarjeta: [" + cardNumber + "]");
        card_number.clear();
        card_number.sendKeys(cardNumber);
    }

    public void setCardHolder(String cardHolder) {
        logger.info("Entering Titular de la tarjeta: [" + cardHolder + "]");
        card_holder.clear();
        card_holder.sendKeys(cardHolder);
    }

    public void setCardExpiration(String expDate) {
        logger.info("Entering Fecha de vencimiento: [" + expDate + "]");
        List<WebElement> creditCardFieldList = driver.findElements(By.cssSelector(".field.ng-pristine.ng-untouched.ng-empty.ng-invalid.ng-invalid-required.ng-valid-pattern.ng-valid-maxlength"));
        creditCardFieldList.get(0).clear();
        creditCardFieldList.get(0).sendKeys(expDate);
    }

    public void selectMonthCardExpiration(String monthCardExpiration) {
        logger.info("Selecting Fecha de vencimiento - Mes: [" + monthCardExpiration + "]");
        Select selectMonthCardExpiration = new Select (month_card_expire);
        selectMonthCardExpiration.selectByVisibleText(monthCardExpiration);
    }

    public void selectYearCardExpiration(String yearCardExpiration) {
        logger.info("Selecting Fecha de vencimiento - Año: [" + yearCardExpiration + "]");
        Select selectYearCardExpiration = new Select (year_card_expire);
        selectYearCardExpiration.selectByVisibleText(yearCardExpiration);
    }

    public void setSecurityCode(String code) {
        logger.info("Entering Código de Seguridad: [" + code + "]");
        security_code.clear();
        security_code.sendKeys(code);
    }

    public void selectDocumentType(String documentTypeSelected) {
        logger.info("Selecting Tipo de Documento: [" + documentTypeSelected + "]");
        Select documentTypeSelect = new Select(documentType);
        documentTypeSelect.selectByVisibleText(documentTypeSelected);
    }

    public void setDocumentNumber(String documentNumber) {
        logger.info("Entering Número de Documento: [" + documentNumber + "]");
        document_number.clear();
        document_number.sendKeys(documentNumber);
    }

    public static void getPaymentList()  {
        paymentList = JsonRead.getJsonDataObject(jsonDataObject, "payment", countryPar.toLowerCase() + "_data.json");
    }

    public static void getPaymentData(String dataSet)  {
        paymentData = JsonRead.getJsonDataObject(paymentList, dataSet, countryPar.toLowerCase() + "_data.json");
    }
}