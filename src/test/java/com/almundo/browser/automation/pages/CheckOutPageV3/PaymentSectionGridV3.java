package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

import static com.almundo.browser.automation.utils.PageUtils.scrollToElement;
import static com.almundo.browser.automation.utils.PageUtils.waitElementForClickable;
import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

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

    /***************************  Locators **********************************/

    @FindBy(css = ".change-card")
    public WebElement changeCardLnk;

    /**************************** Actions **********************************/

    public void populatePaymentSectionV3(String paymentData, String container){
        dataManagement.setPaymentList();
        paymentDataObject = dataManagement.setPaymentData(paymentData);
        selectPaymentQty(paymentDataObject.get("payment_qty").toString(), container);
        selectBankEntity(paymentDataObject.get("bank_name").toString(), container);
        selectCreditCard(paymentDataObject.get("credit_card_name").toString(), container);
    }

    public void selectCreditCard(String cardName, String container) {
        List<WebElement> cardNames = bankSelected.findElements(By.cssSelector(container + " .cards .logo"));
        for(WebElement cardNameResult : cardNames){
            if (cardNameResult.getAttribute("alt").equalsIgnoreCase(cardName)) {
                logger.info("Selecting Card: [" + cardName + "]");
                waitElementForClickable(driver, cardNameResult, 5, "Credit Card Clickable.");
                PageUtils.scrollToElement(driver, cardNameResult);
                cardNameResult.click();
                // The line below is for selecting radio button directly
                //driver.findElement(By.className("option")).click();
                break;
            }
        }
    }

    private WebElement findBank(List<WebElement> results, List<WebElement> banks, String bankName ){
        int i;
        for (i = 0; i < results.size(); ++i) {
            if (results.get(i).getAttribute("alt").equalsIgnoreCase(bankName)) {
                logger.info("Selecting Bank: [" + bankName + "]");
                PageUtils.scrollToElement(driver, results.get(i));
                PageUtils.waitImplicitly(1000);
                results.get(i).click();
                break;
            }
        }
        if(banks.get(i) == null){
            return null;
        }else {
            return banks.get(i);
        }
    }

    public void selectBankEntity(String bankName, String container){
        List<WebElement> results = paymentSelected.findElements(By.cssSelector(container + " .header-bank .logo"));
        List<WebElement> banks = paymentSelected.findElements(By.cssSelector(container + " .bank"));
        bankSelected = findBank(results, banks, bankName);
    }

    private WebElement findPaymentQty(List<WebElement> results, List<WebElement> payments, String qty){
        int i;
        for (i = 0; i < results.size(); ++i) {
            if (results.get(i).getText().equals(qty)) {
                logger.info("Selecting Payment Quantity: [" + qty + "]");
                PageUtils.scrollToElement(driver, results.get(i));
                PageUtils.waitImplicitly(2000);
                scrollToElement(driver, results.get(i));
                if(changeFop){
                    results.get(1).click();
                    changeFop = false;
                }
                results.get(i).click();
                break;
            }
        }
        return payments.get(i);
    }

    public void selectPaymentQty(String qty, String container) {
        PageUtils.waitElementForVisibility(driver, By.cssSelector(container + " .payment .monthly-payment>strong"), 40, "Payments");
        List<WebElement> results = driver.findElements(By.cssSelector(container + " .payment .monthly-payment>strong"));
        List<WebElement> payments = driver.findElements(By.cssSelector(container + " .payment"));
        paymentSelected = findPaymentQty(results, payments, qty);
    }

    /****************** REWARDS related methods ****************************/

    public void clickChangeCardLink() {
        waitElementForClickable(driver, changeCardLnk, 10, "Cambiar Forma de Pago link");
        waitImplicitly(1000);
        PageUtils.scrollToElement(driver, changeCardLnk);
        logger.info("Clicking on Cambiar Forma de Pago link");
        changeCardLnk.click();
    }

    public void setPayment(String paymentNumber, String container) {
        PageUtils.waitElementForVisibility(driver, By.cssSelector(container + " .payment .monthly-payment>strong"), 40, "Payments");
        List<WebElement> results = driver.findElements(By.cssSelector(container + " .payment .monthly-payment>strong"));
        List<WebElement> payments = driver.findElements(By.cssSelector(container + " .payment"));
        paymentSelected = findPaymentQty(results, payments, paymentNumber);
    }

    public void setBank(String bankName, String container) {
        List<WebElement> results = paymentSelected.findElements(By.cssSelector(container + " .header-bank .logo"));
        List<WebElement> banks = paymentSelected.findElements(By.cssSelector(container + " .bank"));
        if((bankSelected = findBank(results, banks, bankName)) == null) {
            Assert.fail("Bank was not selected");
        }
    }

    public void setCreditCard(String cardName, String container) {
        List<WebElement> cardNames = bankSelected.findElements(By.cssSelector(container + " .cards .logo"));
        for(WebElement cardNameResult : cardNames){
            if (cardNameResult.getAttribute("alt").equals(cardName)) {
                logger.info("Selecting Card: [" + cardName + "]");
                PageUtils.scrollToElement(driver, cardNameResult);
                PageUtils.waitImplicitly(1000);
                cardNameResult.click();
                break;
            }
        }
    }
}