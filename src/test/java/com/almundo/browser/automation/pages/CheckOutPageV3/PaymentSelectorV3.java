package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.almundo.browser.automation.utils.PageUtils.scrollToElement;
import static com.almundo.browser.automation.utils.PageUtils.waitElementForClickable;
import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

/**
 * Created by gabrielcespedes on 22/06/17.
 */
public class PaymentSelectorV3 extends BasePage {

    public PaymentSelectorV3(WebDriver iDriver) {
        super(iDriver);
    }

    /**************************** Locators **********************************/

    @FindBy(css = "#lblPaymentOption1")
    private WebElement oneCreditCardRdb;

    @FindBy(css = "#lblPaymentOption12")
    private WebElement twoCreditCardsRdb;

    @FindBy(css ="#lblPaymentOption2")
    private WebElement visaDebit;

    /**************************** Actions **********************************/

    public PaymentSelectorV3 selectOneCreditCardRdb(){
        logger.info("Selecting payment with one credit card");
        oneCreditCardRdb.click();
        waitImplicitly(4000);
        return this;
    }

    public PaymentSelectorV3 selectTwoCreditCardsRdb(){
        scrollToElement(driver, twoCreditCardsRdb);
        waitImplicitly(1000);
        scrollToElement(driver, twoCreditCardsRdb);
        waitElementForClickable(driver, twoCreditCardsRdb, 5 , "[Two Credit Card Radio Button.");
        logger.info("Selecting payment with two credit cards");
        twoCreditCardsRdb.click();
        waitImplicitly(4000);
        return this;
    }

    public PaymentSelectorV3 selectVisaDebit(){
        logger.info("Selecting payment with visa debit card");
        visaDebit.click();
        return this;
    }

    public boolean selectOneCreditCardRdbIsDisplayed(){
        boolean doClick = false;
        try {
            if (oneCreditCardRdb.isDisplayed()) {
                logger.info("Payment type selector is displayed.");
                doClick = true;
            }
        }catch(NoSuchElementException ouch){
            logger.info("Payment type selector is not displayed.");
            doClick = false;
        }
        return doClick;
    }

    public boolean selectTwoCreditCardsRdbIsDisplayed(){
        boolean doClick = false;
        try {
            if (twoCreditCardsRdb.isDisplayed()) {
                logger.info("Payment type selector for 2 cards is displayed.");
                doClick = true;
            }
        }catch(NoSuchElementException ouch){
            logger.info("Payment type selector for 2 cards is not displayed.");
            doClick = false;
        }
        return doClick;
    }
}