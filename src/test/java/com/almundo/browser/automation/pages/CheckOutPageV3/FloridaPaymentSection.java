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

    public void tarjetaDeCreditoClick(){
        logger.info("Selecting: [Tarjeta De Crédito]");
        tarjetaDeCredito.click();
    }

    public void tarjetaDeDebitoClick(){
        logger.info("Selecting: [Tarjeta De Débito]");
        tarjetaDeDebito.click();
    }

    public void otroMedioDePagoClick(String otro){
        logger.info("Selecting: [Otro medio de pago]: [" + otro + "]");
        otroMedioDePago.click();
    }
}