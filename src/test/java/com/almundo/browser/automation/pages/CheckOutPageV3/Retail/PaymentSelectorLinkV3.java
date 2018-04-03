package com.almundo.browser.automation.pages.CheckOutPageV3.Retail;

import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.almundo.browser.automation.utils.Constants.CUSTOMER_EMAIL;
import static com.almundo.browser.automation.utils.PageUtils.waitWithTryCatch;

public class PaymentSelectorLinkV3 extends CheckOutPageV3 {

    public PaymentSelectorLinkV3(WebDriver driver) {
        super(driver);
    }

    /**************************** Locators **********************************/

    @FindBy(css = "#am-split-payment form-client-payment div:nth-child(1) > input")
    private WebElement emailDelCliente;

    @FindBy(css = "#am-split-payment form-client-payment .send-button-container > button")
    private WebElement enviarButton;

    private final String linkDePagoCss = "#am-split-payment > div > div:nth-child(2) > div > form-client-payment > div > div > form > div:nth-child(2) > div > div";
    @FindBy(css = linkDePagoCss)
    private WebElement linkDePagoLnk;

    private final String linkDePagoNewCss = "#am-retail-payment form-client-payment-retail .link-container";
    @FindBy(css = linkDePagoNewCss)
    private WebElement linkDePagoNewLnk;


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

    @SuppressWarnings("Duplicates")
    private String retrieveTinyURL(){
        waitWithTryCatch(driver, linkDePagoCss,"Link de pago", 5);
        logger.info("Getting [Link de pago]");
        String linkDePago = linkDePagoLnk.getText().replace("Copy","");
        logger.info("[Link de pago] : " + linkDePago);
        return linkDePago.replace("Link de pago: ","");
    }

    @SuppressWarnings("Duplicates")
    private String retrieveTinyNewURL(){
        waitWithTryCatch(driver, linkDePagoNewCss,"Link de pago", 5);
        logger.info("Getting [Link de pago]");
        String linkDePagoNew = linkDePagoNewLnk.getText().replace("Copiar","");
        logger.info("[Link de pago] : " + linkDePagoNew);
        return linkDePagoNew.replace("Link de pago: ","");
    }


    public String populateLinkDePagoInfo(){
        enterEmailDelCliente();
        clickEnviarButton();
        String actualCheckoutUrl = driver.getCurrentUrl();
        driver.navigate().to(retrieveTinyURL());
        return actualCheckoutUrl;
    }

    public String populateLinkDePago(){
        String actualCheckoutUrl = driver.getCurrentUrl();
        driver.navigate().to(retrieveTinyNewURL());
        return actualCheckoutUrl;
    }


}