package com.almundo.browser.automation.pages.CheckOutPage;

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
public class CreditCardSection extends CheckOutPage {

    public CreditCardSection(WebDriver driver) {
        super(driver);
    }

    public static JSONObject creditCardList = null;
    public static JSONObject creditCardData;

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

    @FindBy(css = "div:nth-child(1) > div > div.epp-air-left-24.epp-air-right-24.epp-air-top-16.epp-air-bottom-16 > div > label")
    private WebElement pagoEnEfectivoLbl;

    @FindBy(css = "div:nth-child(2) > div > div.epp-air-left-24.epp-air-right-24.epp-air-top-16.epp-air-bottom-16 > div > label")
    private WebElement depositoLbl;

    @FindBy(css = "div:nth-child(3) > div > div.epp-air-left-24.epp-air-right-24.epp-air-top-16.epp-air-bottom-16 > div > label")
    private WebElement transferenciaLbl;

    @FindBy(css = "div:nth-child(4) > div > div.epp-air-left-24.epp-air-right-24.epp-air-top-16.epp-air-bottom-16 > div > label")
    private WebElement reservaPor24hsLbl;



    //############################################### Actions ###############################################

    public CreditCardSection clickPagoEnEfectivoLbl(){
        logger.info("Click: Selecting Pago en Efectivo");
        pagoEnEfectivoLbl.click();
        return this;
    }

    public CreditCardSection clickDepositoLbl(){
        logger.info("Click: Selecting Depósito");
        depositoLbl.click();
        return this;
    }

    public CreditCardSection clickTransferenciaLbl(){
        logger.info("Click: Selecting Transferencia");
        transferenciaLbl.click();
        return this;
    }

    public CreditCardSection clickReservaPor24hsLbl(){
        logger.info("Click: Selecting Reserva por 24 hs.");
        reservaPor24hsLbl.click();
        return this;
    }

    public CreditCardSection selectPaymentOption(String paymentOptionSelected){
        List<WebElement> paymentOptions = driver.findElements(By.cssSelector(".label--inline-block.epp-space-right-16.cards__definition__label.text--bold"));
        for(WebElement paymentOption : paymentOptions){
            if(paymentOption.getText().equals(paymentOptionSelected)){
                paymentOption.click();
                break;
            }
        }
        return this;
    }

    public void selectPaymentQtyOption(int index) {
        List<WebElement> results = driver.findElements(By.cssSelector(".cards__definition__header>div:nth-of-type(1)>.display-table>p:nth-of-type(1)"));

        PageUtils.scrollToElement(driver, results.get(0));
        PageUtils.scrollToCoordinate(driver, -200);
        results.get(index).click();
    }

    public void selectBankOption(String cardName) {
        List<WebElement> cardNames = driver.findElements(By.cssSelector(".cards__definition__banks>div>p>label>span"));
        List<WebElement> radioButtons = driver.findElements(By.cssSelector(".cards__definition__banks>div>p>input"));

        for (int i = 0; i < cardNames.size(); ++i) {
            WebElement cardNameElement = cardNames.get(i);
            WebElement radioButtonElement = radioButtons.get(i);

            if (cardNameElement.getText().equals(cardName)) {
                logger.info("Selecting card name: [" + cardName + "]");
                while (!radioButtonElement.isSelected()){
                    radioButtonElement.click();
                }
                break;
            }
        }
    }

    public void setCardHolder(String cardHolder) {
        logger.info("Entering Titular de la tarjeta: [" + cardHolder + "]");
        card_holder.clear();
        card_holder.sendKeys(cardHolder);
    }

    public void setCardNumber(String cardNumber) {
        logger.info("Entering Número de tu tarjeta: [" + cardNumber + "]");
        card_number.clear();
        card_number.sendKeys(cardNumber);
    }

    public void setCardExpiration(String expDate) {
        logger.info("Entering Fecha de vencimiento: [" + expDate + "]");
        card_expire.clear();
        card_expire.sendKeys(expDate);
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


    public static void getCreditCardList()  {
        creditCardList = JsonRead.getJsonDataObject(jsonDataObject, "creditcard", countryPar.toLowerCase() + "_data.json");
    }

    public static void getCreditCardData(String dataSet)  {
        creditCardData = JsonRead.getJsonDataObject(creditCardList, dataSet, countryPar.toLowerCase() + "_data.json");
    }
}