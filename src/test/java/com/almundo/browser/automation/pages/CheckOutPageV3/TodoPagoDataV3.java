package com.almundo.browser.automation.pages.CheckOutPageV3;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static com.almundo.browser.automation.utils.PageUtils.waitElementForVisibility;
import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

/**
 * Created by gabrielcespedes on 28/06/17.
 */
public class TodoPagoDataV3 extends CheckOutPageV3 {

    public TodoPagoDataV3(WebDriver driver) {
        super(driver);
    }

    JSONObject paymentDataObject = new JSONObject();

    /************************** Locators ***********************/

    @FindBy(css = "#formaDePagoCbx")
    private WebElement medioDePagoDdl;

    @FindBy(css = "#bancoCbx")
    private WebElement bancoDdl;

    @FindBy(css = "#numeroTarjetaTxt")
    private WebElement numeroDeTarjetaTxt;

    @FindBy(css = "#apynTxt")
    private WebElement titularDeLaTarjetaTxt;

    @FindBy(css = "#mesTxt")
    private WebElement fechaDeVencimientoMesTxt;

    @FindBy(css = "#anioTxt")
    private WebElement fechaDeVencimientoAnoTxt;

    @FindBy(css = "#codigoSeguridadTxt")
    private WebElement codigoDeSeguridadTxt;

    @FindBy(css = "#tipoDocCbx")
    private WebElement tipoDeDocumentDdl;

    @FindBy(css = "#nroDocTxt")
    private WebElement numeroDeDocumentoTxt;

    @FindBy(css = "#emailTxt")
    private WebElement emailTxt;

    /************************** Actions ***********************/

    private TodoPagoDataV3 selectMedioDePago(String medioDePago){
        Select medioDePagoSelect = new Select(medioDePagoDdl);
        logger.info("Selecting [Medio de pago]: [" + medioDePago + "]");
        medioDePagoSelect.selectByVisibleText(medioDePago);
        return this;
    }

    private TodoPagoDataV3 selectBanco(String banco){
        Select bancoSelect = new Select(bancoDdl);
        logger.info("Selecting [Banco]: [" + banco + "]");
        bancoSelect.selectByVisibleText(banco);
        return this;
    }

    private TodoPagoDataV3 enterNumeroDeTarjeta(String numeroDeTarjeta){
        logger.info("Entering [Número de tarjeta]: [" + numeroDeTarjeta + "]");
        numeroDeTarjetaTxt.sendKeys(numeroDeTarjeta);
        return this;
    }

    private TodoPagoDataV3 enterTitularDeLaTarjeta(String titularDeLaTarjeta){
        logger.info("Entering [Titular de la tarjeta]: [" + titularDeLaTarjeta + "]");
        titularDeLaTarjetaTxt.sendKeys(titularDeLaTarjeta);
        return this;
    }

    private TodoPagoDataV3 enterFechVencMes(String mesVencimiento){
        logger.info("Selecting [Fecha de vencimiento - Mes]: [" + mesVencimiento + "]");
        fechaDeVencimientoMesTxt.sendKeys(mesVencimiento);
        return this;
    }

    private TodoPagoDataV3 enterFechVenAno(String anoVencimiento){
        logger.info("Selecting [Fecha de vencimiento - Año]: [" + anoVencimiento + "]");
        fechaDeVencimientoAnoTxt.sendKeys(anoVencimiento);
        return this;
    }

    private TodoPagoDataV3 enterCodigoDeSeguridad(String codigoDeSeguridad){
        logger.info("Entering [Código de seguridad]: [" + codigoDeSeguridad + "]");
        codigoDeSeguridadTxt.sendKeys(codigoDeSeguridad);
        return this;
    }

    private TodoPagoDataV3 enterTipoDeDocumento(String tipoDeDocumento){
        logger.info("Selecting [Tipo de doc.]: [" + tipoDeDocumento + "]");
        tipoDeDocumentDdl.sendKeys(tipoDeDocumento);
        return this;
    }

    private TodoPagoDataV3 enterNumeroDeDocumento(String numeroDeDocumento){
        logger.info("Entering [Número de doc.]: [" + numeroDeDocumento + "]");
        numeroDeDocumentoTxt.sendKeys(numeroDeDocumento);
        return this;
    }

    private TodoPagoDataV3 enterEmail(String email){
        logger.info("Entering [E-mail]: [" + email + "]");
        emailTxt.sendKeys(email);
        return this;
    }

    private JSONObject getPaymentData(String paymentData){
        logger.info("Getting payment data for: " + "[" + paymentData + "]");
        dataManagement.setPaymentList();
        return dataManagement.setPaymentData(paymentData);
    }

    public TodoPagoDataV3 populateTodoPagoData(String paymentData){
        paymentDataObject = getPaymentData(paymentData);
        waitElementForVisibility(driver, medioDePagoDdl, 5, "Todo Pago Data");
        waitImplicitly(3000);
        selectMedioDePago(paymentDataObject.get("Medio_de_pago").toString());
        selectBanco(paymentDataObject.get("Banco").toString());
        enterNumeroDeTarjeta(paymentDataObject.get("Numero_de_tarjeta").toString());
        enterTitularDeLaTarjeta(paymentDataObject.get("Titular_de_la_tarjeta").toString());
        enterFechVencMes(paymentDataObject.get("Fecha_de_vencimiento_mes").toString());
        enterFechVenAno(paymentDataObject.get("Fecha_de_vencimiento_ano").toString());
        enterCodigoDeSeguridad(paymentDataObject.get("security_code").toString());
        enterTipoDeDocumento(paymentDataObject.get("Tipo_de_doc").toString());
        enterNumeroDeDocumento(paymentDataObject.get("Numero_de_doc").toString());
        enterEmail(paymentDataObject.get("E-mail").toString());
        return this;
    }
}