package com.almundo.browser.automation.pages.PaymentPageSections;

import com.almundo.browser.automation.base.TestBaseSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by leandro.efron on 25/11/2016.
 */
public class TypeOfPaymentSection extends TestBaseSetup {

    public WebDriver driver;

    public TypeOfPaymentSection(WebDriver iDriver) {
        this.driver = iDriver;
    }

    //############################################### Locators ##############################################

    @FindBy(id = "card_holder")
    private WebElement card_holder;

    @FindBy(id = "card_number")
    private WebElement card_number;

    @FindBy(id = "card_expire")
    private WebElement card_expire;

    @FindBy(id = "security_code")
    private WebElement security_code;

    @FindBy(id = "documentType")
    private WebElement documentType;

    @FindBy(id = "document_number")
    private WebElement document_number;


    //############################################### Actions ###############################################

    public void selectPaymentQtyOption(int index) {
        List<WebElement> results = driver.findElements(By.cssSelector(".cards__definition__header>div:nth-of-type(1)>.display-table>p:nth-of-type(1)"));

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
                radioButtonElement.click();
                break;
            }
        }
    }

    public void setCardHolder(String cardHolder) {
        logger.info("Entering Card Holder: [" + cardHolder + "]");
        card_holder.clear();
        card_holder.sendKeys(cardHolder);
    }

    public void setCardNumber(String cardNumber) {
        logger.info("Entering Card Number: [" + cardNumber + "]");
        card_number.clear();
        card_number.sendKeys(cardNumber);
    }

    public void setCardExpiration(String expDate) {
        logger.info("Entering Card Expiration: [" + expDate + "]");
        card_expire.clear();
        card_expire.sendKeys(expDate);
    }

    public void setSecurityCode(String code) {
        logger.info("Entering Security Code: [" + code + "]");
        security_code.clear();
        security_code.sendKeys(code);
    }

}