package com.almundo.browser.automation.pages.CheckOutPageV3;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FloridaPaymentSection extends CheckOutPageV3 {

    public FloridaPaymentSection(WebDriver driver) {
        super(driver);
    }

    @FindBy(css ="#paymentOptionSection am-retail-payment am-fops-multiple-payment .buttons-container button:nth-child(1)")
    private WebElement tarjetaDeCredito;

    @FindBy(css ="#paymentOptionSection am-retail-payment am-fops-multiple-payment .buttons-container button:nth-child(2)")
    private WebElement tarjetaDeDebito;

    @FindBy(css ="#paymentOptionSection am-retail-payment am-fops-multiple-payment .buttons-container button:nth-child(3)")
    private WebElement otroMedioDePago;

    @FindBy(css ="#am-retail-payment .tab-payment.col-4-md.ng-scope.col-6-xs.col-6-sm > span")
    private WebElement linkDePago;

    @FindBy(css = "#am-retail-payment form-client-payment-retail input")
    private WebElement emailDelClienteInput;

    @FindBy(css = "#am-retail-payment form-client-payment-retail .send-button-container > button")
    private WebElement enviarBtn;

    public FloridaPaymentSection tarjetaDeCreditoClick(){
        logger.info("Selecting: [Tarjeta De Crédito]");
        tarjetaDeCredito.click();
        return this;
    }

    public FloridaPaymentSection tarjetaDeDebitoClick(){
        logger.info("Selecting: [Tarjeta De Débito]");
        tarjetaDeDebito.click();
        return this;
    }

    public FloridaPaymentSection otroMedioDePagoClick(String otro){
        logger.info("Selecting: [Otro medio de pago]: [" + otro + "]");
        otroMedioDePago.click();
        return this;
    }

    public FloridaPaymentSection linkDePagoClick(){
        logger.info("Selecting: [Link De Pago]");
        linkDePago.click();
        return this;
    }

    public FloridaPaymentSection enterEmailDelCliente(String emailDelCliente){
        logger.info("Entering [Email del cliente] :[" + emailDelCliente + "]");
        emailDelClienteInput.clear();
        emailDelClienteInput.sendKeys(emailDelCliente);
        return this;
    }

    public FloridaPaymentSection enviarBtnClick(){
        logger.info("Clicking on [Enviar] button");
        enviarBtn.click();
        return this;
    }
}