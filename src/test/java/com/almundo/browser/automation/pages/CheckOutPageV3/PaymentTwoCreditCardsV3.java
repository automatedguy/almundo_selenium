package com.almundo.browser.automation.pages.CheckOutPageV3;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 24/08/17.
 */
public class PaymentTwoCreditCardsV3 extends CheckOutPageV3 {

    public PaymentTwoCreditCardsV3(WebDriver driver) {
        super(driver);
    }

    /**************************** Locators Tarjetas **********************************/

    @FindBy(css = "am-form-split-web:nth-child(1) div:nth-child(1) > input")
    private WebElement importeTarjetaTxt;

    @FindBy(css ="am-form-split-web:nth-child(1) div:nth-child(2) > input")
    private WebElement numeroDeTarjetaTxt;

    @FindBy(css = "am-form-split-web:nth-child(1) div:nth-child(3) > select")
    private WebElement tarjetaDdl;

    @FindBy(css = "am-form-split-web:nth-child(1) div:nth-child(4) > select")
    private WebElement bancoTxt;

    @FindBy(css = "am-form-split-web:nth-child(1) div:nth-child(5) > select")
    private WebElement cuotasDdl;

    @FindBy(css = "am-form-split-web:nth-child(1) div:nth-child(7) > input")
    private WebElement titularTarjetaTxt;

    @FindBy(css = "am-form-split-web:nth-child(1) div:nth-child(8) > select")
    private WebElement fechaDeVencimientoMesDdl;

    @FindBy(css = "am-form-split-web:nth-child(1) div:nth-child(9) > select")
    private WebElement fechaDeVencimientoAnoDdl;

    @FindBy(css = "am-form-split-web:nth-child(1) div:nth-child(10) input")
    private WebElement codigoDeSeguridadTxt;
    /**************************** Actions **********************************/

    public PaymentTwoCreditCardsV3 setImporteTarjeta(String importeTarjeta){
        logger.info("Entering [Importe a pagar con la tarjeta 1]: " + importeTarjeta);
        importeTarjetaTxt.sendKeys(importeTarjeta);
        return this;
    }

    public PaymentTwoCreditCardsV3 setNumeroTarjeta(String numeroTarjeta, String indexTarjeta){
        logger.info("Entering [Número de tarjeta ]: " + numeroTarjeta);
        return this;
    }

    public PaymentTwoCreditCardsV3 selectTarjeta(String tarjeta, String indexTarjeta){
        logger.info("Selecting [Tarjeta]: " + tarjeta);
        return this;
    }

    public PaymentTwoCreditCardsV3 selectBanco(String banco, String indexTarjeta){
        logger.info("Selecting [Banco]: " + banco);
        return this;
    }

    public PaymentTwoCreditCardsV3 selectCuotas(String cuotas, String indexTarjeta){
        logger.info("Selecting [Cuotas]: " + cuotas);
        return this;
    }

    public PaymentTwoCreditCardsV3 setTitularTarjeta(String titularTarjeta, String indexTarjeta){
        logger.info("Entering [Titular de tarjeta]: " + titularTarjeta);
        return this;
    }

    public PaymentTwoCreditCardsV3 selectVencMes(String mes, String indexTarjeta){
        logger.info("Selecting [Fecha de vencimiento - Mes]: " + mes);
        return this;
    }

    public PaymentTwoCreditCardsV3 selectVencAno(String ano, String indexTarjeta){
        logger.info("Selecting [Fecha de vencimiento - Año]: " + ano);
        return this;
    }

    public PaymentTwoCreditCardsV3 setCodigoDeSeguridad(String codigoDeSeguridad, String indexTarjeta){
        logger.info("Entering [Código de seguridad]: " + codigoDeSeguridad);
        return this;
    }
}
