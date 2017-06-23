package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.utils.PageUtils;
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

    private WebElement paymentSelected = null;
    private WebElement bankSelected = null;

    private List<WebElement> selectedPaymentQty = null;

    /**************************** Actions **********************************/

    public void populatePaymentSectionV3(String paymentData, String container){
        dataManagement.getPaymentList();
        paymentDataObject = dataManagement.getPaymentData(paymentData);
        selectPaymentQty(paymentDataObject.get("payment_qty").toString(), container);
        selectBankEntity(paymentDataObject.get("bank_name").toString(), container);
        selectCreditCard(paymentDataObject.get("credit_card_name").toString(), container);
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

    public void selectBankEntity(String bankName, String container){
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
    }

    public void selectPaymentQty(String qty, String container) {
        PageUtils.waitElementForVisibility(driver, By.cssSelector(container + " .payment .monthly-payment>strong"), 40, "Payments");
        List<WebElement> results = driver.findElements(By.cssSelector(container + " .payment .monthly-payment>strong"));
        List<WebElement> payments = driver.findElements(By.cssSelector(container + " .payment"));

        for (int i = 0; i < results.size(); ++i) {
            if (results.get(i).getText().equals(qty)) {
                logger.info("Selecting Payment Quantity: [" + qty + "]");
                PageUtils.scrollToElement(driver, results.get(i));
                results.get(i).click();
                paymentSelected = payments.get(i);
                break;
            }
        }
    }
}