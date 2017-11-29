package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.almundo.browser.automation.utils.PageUtils.*;

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

    @FindBy(css = "payment-promocode div:nth-child(1) input")
    private WebElement tengoUnCupon;

    @FindBy(css = "payment-promocode form div input")
    private WebElement promocodeTxt;

    @FindBy(css = "payment-promocode div button")
    private WebElement aplicarBtn;

    /**************************** Actions **********************************/

    public PaymentSelectorV3 selectOneCreditCardRdb(){
        try {
            scrollToElement(driver, oneCreditCardRdb);
            waitImplicitly(1000);
            scrollToElement(driver, oneCreditCardRdb);
            waitElementForClickable(driver, oneCreditCardRdb, 5, "[One Credit Card Radio Button.");
            logger.info("Selecting payment with one credit card");
            oneCreditCardRdb.click();
            waitImplicitly(4000);
        }catch (NoSuchElementException ouch){
            logger.warn("One credit card selector not present, trying to continue anyway.");
        }
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

    public String setPromocode(String paymentData){
        if(paymentData.contains("promocode$")){
            logger.info("Clicking on [Tengo un cupón de descuento]");
            tengoUnCupon.click();
            waitImplicitly(1000);
            logger.info("Entering [Promocode]: [pacman]");
            promocodeTxt.sendKeys("pacman");
            logger.info("Clicking on [Aplicar]");
            aplicarBtn.click();
            paymentData = paymentData.replace("promocode$","");
            waitImplicitly(4000);
            initBreakDownSectionV3().getFinalPrice();
        } else {
            logger.info("Not entering promocode");
        }
        return paymentData;
    }
}