package com.almundo.browser.automation.pages.CheckOutPageV3;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static com.almundo.browser.automation.utils.PageUtils.getCountryCurrency;

/**
 * Created by gabrielcespedes on 24/08/17.
 */
public class PaymentTwoCreditCardsV3 extends CheckOutPageV3 {

    public PaymentTwoCreditCardsV3(WebDriver driver) {
        super(driver);
    }

    JSONObject paymentDataObject = new JSONObject();

    /**************************** Static Locators **********************************/

    @FindBy(css = "am-form-split-web:nth-child(1) div:nth-child(1) > input")
    private WebElement importeTarjetaTxt;

    /**************************** Dynamic Actions **********************************/

    private PaymentTwoCreditCardsV3 setImporteTarjeta(String importeTarjeta){
        logger.info("Entering [Importe a pagar con la tarjeta 1]: [" + importeTarjeta + "]");
        importeTarjetaTxt.sendKeys(importeTarjeta);
        return this;
    }

    private PaymentTwoCreditCardsV3 setNumeroTarjeta(String numeroTarjeta, int indexTarjeta){
        WebElement numeroDeTarjetaTxt =
                driver.findElement(By.cssSelector("am-form-split-web:nth-child(" + indexTarjeta + ") div:nth-child(2) > input"));
        logger.info("Entering [Número de tarjeta ]: [" + numeroTarjeta + "]");
        numeroDeTarjetaTxt.sendKeys(numeroTarjeta);
        return this;
    }

    private PaymentTwoCreditCardsV3 selectTarjeta(String tarjeta, int indexTarjeta){
        WebElement tarjetaDdl =
                driver.findElement(By.cssSelector("am-form-split-web:nth-child("+ indexTarjeta +") div:nth-child(3) > select"));
        Select tarjetaSelect = new Select (tarjetaDdl);
        logger.info("Selecting [Tarjeta]: [" + tarjeta + "]");
        tarjetaSelect.selectByVisibleText(tarjeta);
        return this;
    }

    private PaymentTwoCreditCardsV3 selectBanco(String banco, int indexTarjeta){
        WebElement bancoDdl =
                driver.findElement(By.cssSelector("am-form-split-web:nth-child("+ indexTarjeta +") div:nth-child(4) > select"));
        Select bancoSelect = new Select (bancoDdl);
        logger.info("Selecting [Banco]: [" + banco + "]");
        bancoSelect.selectByVisibleText(banco);
        return this;
    }

    private PaymentTwoCreditCardsV3 selectCuotas(String cuotas, int paymentAmount, int indexTarjeta){
        WebElement cuotasDdl =
                driver.findElement(By.cssSelector("am-form-split-web:nth-child("+indexTarjeta+") div:nth-child(5) > select"));
        Select cuotasSelect = new Select (cuotasDdl);
        String currency = getCountryCurrency();
        String cuotasFinal = cuotas + " de " + currency + " " + paymentAmount + " (Total a pagar: " + currency + " " + paymentAmount + ")";
        logger.info("Selecting [Cuotas]: [" + cuotasFinal + "]");
        cuotasSelect.selectByVisibleText(cuotasFinal);
        return this;
    }

    private PaymentTwoCreditCardsV3 setTitularTarjeta(String titularTarjeta, int indexTarjeta){
        WebElement titularTarjetaTxt =
                driver.findElement(By.cssSelector("am-form-split-web:nth-child("+ indexTarjeta + ") div:nth-child(7) > input"));
        logger.info("Entering [Titular de tarjeta]: [" + titularTarjeta + "]");
        titularTarjetaTxt.sendKeys(titularTarjeta);
        return this;
    }

    private PaymentTwoCreditCardsV3 selectVencMes(String mes, int indexTarjeta){
        WebElement fechaDeVencimientoMesDdl =
                driver.findElement(By.cssSelector("am-form-split-web:nth-child("+ indexTarjeta +") div:nth-child(8) > select"));
        Select vencMesSelect = new Select(fechaDeVencimientoMesDdl);
        logger.info("Selecting [Fecha de vencimiento - Mes]: [" + mes + "]");
        vencMesSelect.selectByVisibleText(mes);
        return this;
    }

    private PaymentTwoCreditCardsV3 selectVencAno(String ano, int indexTarjeta){
        WebElement fechaDeVencimientoAnoDdl =
                driver.findElement(By.cssSelector("am-form-split-web:nth-child("+ indexTarjeta +") div:nth-child(9) > select"));
        Select vencAnoSelect =  new Select(fechaDeVencimientoAnoDdl);
        logger.info("Selecting [Fecha de vencimiento - Año]: [" + ano + "]");
        vencAnoSelect.selectByVisibleText(ano);
        return this;
    }

    private PaymentTwoCreditCardsV3 setCodigoDeSeguridad(String codigoDeSeguridad, int indexTarjeta){
        WebElement codigoDeSeguridadTxt =
                driver.findElement(By.cssSelector("am-form-split-web:nth-child(" + indexTarjeta + ") div:nth-child(10) input"));
        logger.info("Entering [Código de seguridad]: [" + codigoDeSeguridad + "]");
        codigoDeSeguridadTxt.sendKeys(codigoDeSeguridad);
        return this;
    }

    public PaymentTwoCreditCardsV3 populateTwoCreditCards(List<String> paymentDataList, int totalPrice){
        int container = 1;
        int paymentAmount = totalPrice / paymentDataList.size();
        dataManagement.getPaymentList();
        setImporteTarjeta(String.valueOf(paymentAmount));
        logger.info("Populating 2 Credit Cards Payments");
        for(String paymentData : paymentDataList) {
            logger.info("Populating credit card N°: [" + container + "]");
            paymentDataObject = dataManagement.getPaymentData(paymentData);
            setNumeroTarjeta(paymentDataObject.get("card_number").toString(), container);
            selectTarjeta(paymentDataObject.get("credit_card_name").toString(), container);
            //selectBanco(paymentDataObject.get("bank_name").toString(), container);
            selectCuotas(paymentDataObject.get("payment_qty").toString(), paymentAmount, container);
            setTitularTarjeta(paymentDataObject.get("card_holder").toString(), container);
            selectVencMes(paymentDataObject.get("month_card_expire").toString(), container);
            selectVencAno(paymentDataObject.get("year_card_expire").toString(), container);
            setCodigoDeSeguridad(paymentDataObject.get("security_code").toString(), container);
            container = container + 1;
        }
        return this;
    }
}
