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
public class DebitCardDataV3 extends CheckOutPageV3 {

    public DebitCardDataV3(WebDriver driver) {
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

    @FindBy(css = "#tipoDocCbx")
    private WebElement tipoDeDocumentDdl;

    @FindBy(css = "#nroDocTxt")
    private WebElement numeroDeDocumentoTxt;

    @FindBy(css = "#emailTxt")
    private WebElement emailTxt;

    /************************** Actions ***********************/

    private DebitCardDataV3 selectMedioDePago(String medioDePago){
        Select medioDePagoSelect = new Select(medioDePagoDdl);
        logger.info("Selecting [Medio de pago]: [" + medioDePago + "]");
        medioDePagoSelect.selectByVisibleText(medioDePago);
        return this;
    }

    private DebitCardDataV3 selectBanco(String banco){
        Select bancoSelect = new Select(bancoDdl);
        logger.info("Selecting [Banco]: [" + banco + "]");
        bancoSelect.selectByVisibleText(banco);
        return this;
    }

    private DebitCardDataV3 enterNumeroDeTarjeta(String numeroDeTarjeta){
        logger.info("Entering [Número de tarjeta]: [" + numeroDeTarjeta + "]");
        numeroDeTarjetaTxt.sendKeys(numeroDeTarjeta);
        return this;
    }

    private DebitCardDataV3 enterTitularDeLaTarjeta(String titularDeLaTarjeta){
        logger.info("Entering [Titular de la tarjeta]: [" + titularDeLaTarjeta + "]");
        titularDeLaTarjetaTxt.sendKeys(titularDeLaTarjeta);
        return this;
    }

    private DebitCardDataV3 enterFechVencMes(String mesVencimiento){
        logger.info("Selecting [Fecha de vencimiento - Mes]: [" + mesVencimiento + "]");
        fechaDeVencimientoMesTxt.sendKeys(mesVencimiento);
        return this;
    }

    private DebitCardDataV3 enterFechVenAno(String anoVencimiento){
        logger.info("Selecting [Fecha de vencimiento - Año]: [" + anoVencimiento + "]");
        fechaDeVencimientoAnoTxt.sendKeys(anoVencimiento);
        return this;
    }

    private DebitCardDataV3 enterTipoDeDocumento(String tipoDeDocumento){
        logger.info("Selecting [Tipo de doc.]: [" + tipoDeDocumento + "]");
        tipoDeDocumentDdl.sendKeys(tipoDeDocumento);
        return this;
    }

    private DebitCardDataV3 enterNumeroDeDocumento(String numeroDeDocumento){
        logger.info("Entering [Número de doc.]: [" + numeroDeDocumento + "]");
        numeroDeDocumentoTxt.sendKeys(numeroDeDocumento);
        return this;
    }

    private DebitCardDataV3 enterEmail(String email){
        logger.info("Entering [E-mail]: [" + email + "]");
        emailTxt.sendKeys(email);
        return this;
    }

    private JSONObject getPaymentData(String paymentData){
        logger.info("Getting payment data for: " + "[" + paymentData + "]");
        dataManagement.getPaymentList();
        return dataManagement.getPaymentData(paymentData);
    }

    public DebitCardDataV3 populateDebitCardData(String paymentData){
        paymentDataObject = getPaymentData(paymentData);
        waitElementForVisibility(driver, medioDePagoDdl, 5, "Debit Card Section.");
        waitImplicitly(3000);
        selectMedioDePago(paymentDataObject.get("Medio_de_pago").toString());
        selectBanco(paymentDataObject.get("Banco").toString());
        enterNumeroDeTarjeta(paymentDataObject.get("Numero_de_tarjeta").toString());
        enterTitularDeLaTarjeta(paymentDataObject.get("Titular_de_la_tarjeta").toString());
        enterFechVencMes(paymentDataObject.get("Fecha_de_vencimiento_mes").toString());
        enterFechVenAno(paymentDataObject.get("Fecha_de_vencimiento_ano").toString());
        enterTipoDeDocumento(paymentDataObject.get("Tipo_de_doc").toString());
        enterNumeroDeDocumento(paymentDataObject.get("Numero_de_doc").toString());
        enterEmail(paymentDataObject.get("E-mail").toString());
        return this;
    }
}