package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.data.DataManagement;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by gabrielcespedes on 23/06/17.
 */
public class PaymentSectionGridV3 extends CheckOutPageV3{

    public PaymentSectionGridV3(WebDriver driver) {
        super(driver);
    }

    private DataManagement dataManagement = new DataManagement();
    JSONObject paymentDataObject = new JSONObject();

    /**************************** Locators **********************************/

    private List<WebElement> selectedPaymentQty = null;

    /**************************** Actions **********************************/

    public void populatePaymentSection(String paymentData){
        dataManagement.getPaymentList();
        paymentDataObject = dataManagement.getPaymentData(paymentData);
        selectedPaymentQty = selectPaymentQty(paymentDataObject.get("payment_qty").toString());
        selectBankEntity(paymentDataObject.get("bank_name").toString(), selectedPaymentQty);
    }

    public void selectCard(){

    }

    public void selectBankEntity(String cardName, List<WebElement> selectedPaymentQty){
        List<WebElement> bankList = selectedPaymentQty;
        for(WebElement bank : bankList ){
            if(bank.getAttribute("alt").toString().equals(cardName)){
                logger.info("Selecting [" + cardName + "] Card");
                bank.click();
            }
        }
    }

    public List<WebElement> selectPaymentQty(String qty) {
        List<WebElement> paymentList = driver.findElements(By.cssSelector(".quantity"));
        List<WebElement> paymentSelected = null;
        for (WebElement payment : paymentList) {
            if(payment.getText().equals(qty)) {
                logger.info("Selecting [" + qty + "] payment/s option");
                payment.click();
                paymentSelected = payment.findElements(By.cssSelector(".logo.logo-banks"));
                break;
            }
        }
        return paymentSelected;
    }
}