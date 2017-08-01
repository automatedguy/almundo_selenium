package com.almundo.browser.automation.pages.CheckOutPageV3.Retail;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

/**
 * Created by gabrielcespedes on 22/06/17.
 */
public class PaymentSelectorRetailV3 extends BasePage {

    public PaymentSelectorRetailV3(WebDriver iDriver) {
        super(iDriver);
    }

    /**************************** Locators **********************************/

    @FindBy(css = "#am-split-payment div:nth-child(1) > label")
    private WebElement deposit;

    @FindBy(css = "#am-split-payment div:nth-child(2) > label")
    private WebElement transfer;

    @FindBy(css ="#am-split-payment .credit-card > label")
    private WebElement creditCard;

    /**************************** Actions **********************************/

    public PaymentSelectorRetailV3 selectDepositRdb(){
        logger.info("Selecting payment type: [Depósito]" );
        deposit.click();
        waitImplicitly(3000);
        return this;
    }

    public PaymentSelectorRetailV3 selectTransferRdb(){
        logger.info("Selecting payment type: [Transferencia]");
        transfer.click();
        waitImplicitly(3000);
        return this;
    }

    public PaymentSelectorRetailV3 selectCreditRbd(){
        logger.info("Selecting payment type: [Tarjeta de crédito]");
        creditCard.click();
        return this;
    }
}