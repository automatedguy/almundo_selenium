package com.almundo.browser.automation.pages.CheckOutPageV3;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class FloridaCreditCard extends CheckOutPageV3 {

    public FloridaCreditCard(WebDriver driver) {
        super(driver);
    }

    JSONObject paymentDataObject = new JSONObject();

    /********************* Locators *******************/

    @FindBy(css = "#paymentOptionSection div:nth-child(1) > label > input")
    private WebElement importeTarjetaInput;

    @FindBy(css = "#paymentOptionSection div:nth-child(2) > input")
    private WebElement numeroTarjetaInput;

    @FindBy(css = "#paymentOptionSection div:nth-child(2) > div:nth-child(1) > select")
    private WebElement tarjetaSelect;

    @FindBy(css = "#paymentOptionSection div:nth-child(2) > div:nth-child(2) > select")
    private WebElement bancoSelect;

    @FindBy(css = "#paymentOptionSection div:nth-child(3) > div > select")
    private WebElement cuotasSelect;

    @FindBy(css = "#paymentOptionSection div:nth-child(4) input")
    private WebElement titularTarjetaInput;

    @FindBy(css = "#paymentOptionSection div:nth-child(4) > div:nth-child(2) > select")
    private WebElement fechVencMesSelect;

    @FindBy(css = "#paymentOptionSection div:nth-child(4) > div:nth-child(3) > select")
    private WebElement getFechVencAnoSelect;

    @FindBy(css = "#paymentOptionSection div:nth-child(5) input")
    private WebElement codigoSeguridadInput;

    /********************* Actions *******************/

    private FloridaCreditCard setImporteTarjeta(String importeTarjeta){
        logger.info("Setting [Importe a pagar con la tarjeta]: [" + importeTarjeta + "]");
        importeTarjetaInput.clear();
        importeTarjetaInput.sendKeys(importeTarjeta);
        return this;
    }

    private FloridaCreditCard setNumeroTarjeta(String numeroTarjeta){
        logger.info("Setting [Número de tarjeta]: [" + numeroTarjeta + "]");
        numeroTarjetaInput.clear();
        numeroTarjetaInput.sendKeys(numeroTarjeta);
        return this;
    }

    private FloridaCreditCard setTarjeta(String tarjeta){
        logger.info("Selecting [Tarjeta]: [" + tarjeta + "]");
        Select tarjetaDdl = new Select(tarjetaSelect);
        tarjetaDdl.selectByVisibleText(tarjeta);
        return this;
    }

    private FloridaCreditCard setBanco(String banco){
        logger.info("Selecting [Banco]: [" + banco + "]");
        Select bancoDdl =  new Select(bancoSelect);
        bancoDdl.selectByVisibleText(banco);
        return this;
    }

    private FloridaCreditCard setCuotas(){
        Select cuotasDdl =  new Select(cuotasSelect);
        logger.info("Selecting [Cuotas] first Option: [" + cuotasDdl.getOptions().get(0));
        cuotasDdl.selectByIndex(0);
        return this;
    }

    private FloridaCreditCard setTitularTarjeta(String titularTarjeta){
        logger.info("Setting [Titular de tarjeta]: " + titularTarjeta + "]");
        titularTarjetaInput.clear();
        titularTarjetaInput.sendKeys(titularTarjeta);
        return this;
    }

    private FloridaCreditCard setFechVencMes(String vencMes){
        logger.info("Setting [Fecha de vencimiento -  Mes]: [" + vencMes + "]");
        Select fechaVencMesDdl =  new Select(fechVencMesSelect);
        fechaVencMesDdl.selectByVisibleText(vencMes);
        return this;
    }

    private FloridaCreditCard setFechVencAno(String vencAno){
        logger.info("Setting [Fecha de vencimiento - Año]: [" + vencAno + "]");
        Select fechaVencAnoDdl = new Select(getFechVencAnoSelect);
        fechaVencAnoDdl.selectByVisibleText(vencAno);
        return this;
    }

    private FloridaCreditCard setCodigoSeguridad(String codigoSeguridad){
        logger.info("Setting [Código de seguridad]: [" + codigoSeguridad + "]");
        codigoSeguridadInput.clear();
        codigoSeguridadInput.sendKeys(codigoSeguridad);
        return this;
    }

    public FloridaCreditCard populateCreditCardInfo(String paymentData){
        logger.info("");
        dataManagement.setPaymentList();

        paymentDataObject = dataManagement.setPaymentData(paymentData);

        if(inputDef.isRequired("payments","credit_card_number",0)){
            setNumeroTarjeta(paymentDataObject.get("card_number").toString());}


        return this;
    }
}