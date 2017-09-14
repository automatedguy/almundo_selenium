package com.almundo.browser.automation.pages.CheckOutPageV3;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

import static com.almundo.browser.automation.utils.Constants.*;

/**
 * Created by leandro.efron on 25/11/2016.
 */
public class PaymentSectionComboV3 extends CheckOutPageV3 {

    public PaymentSectionComboV3(WebDriver driver) {
        super(driver);
    }

    private WebElement paymentSelected = null;
    private WebElement bankSelected = null;

    JSONObject paymentDataObject = new JSONObject();

    //############################################### Locators ##############################################

    @FindBy(css = ".option-section input[value='creditCard']")
    public WebElement creditCardRb;

    @FindBy(css = ".option-section input[value='transfer']")
    public WebElement trasnferRb;

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

    public PaymentSelectorV3 paymentSelectorV3() {
        return initPaymentSelectorV3();
    }

    //############################################### Actions ###############################################

    public PaymentSectionComboV3 populatePaymentSectionV3(String paymentData, String container) {
        dataManagement.getPaymentList();

        WebElement creditCardDdl =  driver.findElement(By.cssSelector(container + " am-credit-cards-combo div:nth-child(1) > div > select"));
        WebElement bankDdl =  driver.findElement(By.cssSelector(container + " am-credit-cards-combo div:nth-child(2) > div > select"));
        WebElement paymentDdl =  driver.findElement(By.cssSelector(container + " am-credit-cards-combo div:nth-child(3) > div > select"));

        Select creditCardSelect = new Select(creditCardDdl);
        Select bankSelect = new Select(bankDdl);
        Select paymentSelect = new Select(paymentDdl);

        logger.info("------------- Selecting type of Payment "+ container + "-------------");

        if(paymentData.equals("random")) {
            List<WebElement> availableCardsElements = creditCardSelect.getOptions();
            availableCardsElements.remove(0);

            for (WebElement availableCard : availableCardsElements) {
                if (availableCard.getText().equals("Visa")) {
                    paymentDataObject = dataManagement.getPaymentData(VISA_1);
                    setCreditCardCombo(creditCardSelect, "Visa");
                    break;
                } else if (availableCard.getText().equals("Mastercard")) {
                    paymentDataObject = dataManagement.getPaymentData(MASTER_1);
                    setCreditCardCombo(creditCardSelect, "Mastercard");
                    break;
                } else if (availableCard.getText().equals("American Express")) {
                    paymentDataObject = dataManagement.getPaymentData(AMEX_1);
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
        return this;
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
                if(countryPar.equals(MEXICO)){
                    logger.info("Selecting Bank: [" + bankSelect.getOptions().get(1).getText() + "]");
                    bankSelect.selectByIndex(1);
                } else {
                    logger.info("Selecting Bank: [" + bankName + "]");
                    bankSelect.selectByValue(bankName);
                    if(bankSelect.getFirstSelectedOption().getText().contains("TodoPago")){
                        logger.info("[TodoPago] is enabled for this form of payment.");
                    }
                }
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
}