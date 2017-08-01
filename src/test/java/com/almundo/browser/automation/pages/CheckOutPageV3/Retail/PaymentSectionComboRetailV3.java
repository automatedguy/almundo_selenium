package com.almundo.browser.automation.pages.CheckOutPageV3.Retail;

import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.PaymentSelectorV3;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;

/**
 * Created by leandro.efron on 25/11/2016.
 */
public class PaymentSectionComboRetailV3 extends CheckOutPageV3 {

    public PaymentSectionComboRetailV3(WebDriver driver) {
        super(driver);
    }
    JSONObject paymentDataObject = new JSONObject();

    //############################################### Locators ##############################################

    public PaymentSelectorV3 paymentSelectorV3() {
        return initPaymentSelectorV3();
    }

    //############################################### Actions ###############################################

    public PaymentSectionComboRetailV3 populatePaymentSectionV3(String paymentData) {
        dataManagement.getPaymentList();

        WebElement creditCardDdl =  driver.findElement(By.cssSelector("am-credit-cards-combo div:nth-child(1) > div > select"));
        WebElement bankDdl =  driver.findElement(By.cssSelector("am-credit-cards-combo div:nth-child(2) > div > select"));
        WebElement paymentDdl =  driver.findElement(By.cssSelector("am-credit-cards-combo div:nth-child(3) > div > select"));

        Select creditCardSelect = new Select(creditCardDdl);
        Select bankSelect = new Select(bankDdl);
        Select paymentSelect = new Select(paymentDdl);

        paymentDataObject = dataManagement.getPaymentData(paymentData);
        setCreditCardCombo(creditCardSelect, paymentDataObject.get("credit_card_name").toString());
        setBankCombo(bankSelect, paymentDataObject.get("credit_card_code").toString());

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
        logger.info("Selecting Bank: [" + bankName + "]");
        bankSelect.selectByValue(bankName);
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