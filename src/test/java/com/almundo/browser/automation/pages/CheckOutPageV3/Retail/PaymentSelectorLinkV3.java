package com.almundo.browser.automation.pages.CheckOutPageV3.Retail;

import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.almundo.browser.automation.utils.Constants.CUSTOMER_EMAIL;

public class PaymentSelectorLinkV3 extends CheckOutPageV3 {

    public PaymentSelectorLinkV3(WebDriver driver) {
        super(driver);
    }

    /**************************** Locators **********************************/

    @FindBy(css = "#am-split-payment form-client-payment div:nth-child(1) > input")
    private WebElement emailDelCliente;

    @FindBy(css = "#am-split-payment form-client-payment .send-button-container > button")
    private WebElement enviarButton;

    @FindBy(css = "#am-split-payment > div > div:nth-child(2) > div > form-client-payment > div > div > form > div:nth-child(2) > div > div")
    private WebElement linkDePagoLnk;


    /**************************** Actions **********************************/

    private PaymentSelectorLinkV3 enterEmailDelCliente(){
        logger.info("Entering [Email Del Cliente]: " + CUSTOMER_EMAIL);
        emailDelCliente.sendKeys(CUSTOMER_EMAIL);
        return this;
    }

    private PaymentSelectorLinkV3 clickEnviarButton(){
        logger.info("Clicking on [Enviar] button.");
        enviarButton.click();
        return this;
    }

    private String retrieveLinkDePago(){
        logger.info("Getting [Link de pago]");
        String linkDePago = linkDePagoLnk.getText();
        logger.info("[Link de pago] : " + linkDePago);
        return linkDePago;
    }

    public PaymentSelectorLinkV3 populateLinkDePagoInfo(){

        return this;
    }
}