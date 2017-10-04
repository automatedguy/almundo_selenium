package com.almundo.browser.automation.pages.CheckOutPageV3;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static com.almundo.browser.automation.utils.PageUtils.*;

/**
 * Created by gabrielcespedes on 24/08/17.
 */
public class PaymentTwoCreditCardsV3 extends CheckOutPageV3 {

    public PaymentTwoCreditCardsV3(WebDriver driver) {
        super(driver);
    }

    JSONObject paymentDataObject = new JSONObject();

    private int cuotasIndex = 1;
    private boolean secondPayment = false;

    /**************************** Static Locators **********************************/

    @FindBy(css = "#am-split-payment-web am-payment-web div:nth-child(1) > div:nth-child(1) > input")
    private WebElement importeTarjetaTxt;

    @FindBy(css = "#am-split-payment-web div:nth-child(1) am-payment-web div:nth-child(1) > div:nth-child(2) > input")
    private List<WebElement> numeroDeTarjetaList;

    String tarjetaLocator = "#am-split-payment-web div:nth-child(1) am-payment-web div:nth-child(2) > div:nth-child(1) > select";
    @FindBy(css = "#am-split-payment-web div:nth-child(1) am-payment-web div:nth-child(2) > div:nth-child(1) > select")
    private List<WebElement> tarjetaList;

    @FindBy(css = "#am-split-payment-web div:nth-child(1) am-payment-web div:nth-child(2) > div:nth-child(2) > select")
    private List<WebElement> bancoList;

    @FindBy(css = "#am-split-payment-web div:nth-child(1) am-payment-web div:nth-child(3) > div > select")
    private WebElement firstCuotaElement;

    @FindBy(css = "#am-split-payment-web div:nth-child(2) am-payment-web div:nth-child(3) > div > select")
    private WebElement secondCuotaElement;

    @FindBy(css = "#am-split-payment-web am-payment-web div:nth-child(4) > .container-field > input")
    private List<WebElement> titularDeTarjetaList;

    @FindBy(css = "#am-split-payment-web am-payment-web div:nth-child(4) > div:nth-child(2) > select")
    private List<WebElement> fechaDeVencimientoMesList;

    @FindBy(css = "#am-split-payment-web am-payment-web div:nth-child(4) > div:nth-child(3) > select")
    private List<WebElement> fechaDeVencimientoAnoList;

    @FindBy(css = "#am-split-payment-web am-payment-web div:nth-child(5) > div > div:nth-child(1) > input")
    private List<WebElement> codigoDeSeguridadList;

    /**************************** Dynamic Actions **********************************/

    private PaymentTwoCreditCardsV3 setImporteTarjeta(String importeTarjeta){
        waitImplicitly(2000);
        logger.info("Entering [Importe a pagar con la tarjeta]: [" + importeTarjeta + "]");
        importeTarjetaTxt.sendKeys(importeTarjeta);
        return this;
    }

    private PaymentTwoCreditCardsV3 setNumeroTarjeta(String numeroTarjeta, int indexTarjeta){
        waitImplicitly(2000);
        logger.info("Entering [Número de tarjeta ]: [" + numeroTarjeta + "]");
        numeroDeTarjetaList.get(indexTarjeta).sendKeys(numeroTarjeta);
        return this;
    }

    private PaymentTwoCreditCardsV3 selectTarjeta(String tarjeta, int indexTarjeta){
        waitImplicitly(2000);
        // waitWithTryCatch(driver,)
        Select tarjetaSelect = new Select (tarjetaList.get(indexTarjeta));
        logger.info("Selecting [Tarjeta]: [" + tarjeta + "]");
        tarjetaSelect.selectByVisibleText(tarjeta);
        return this;
    }

    private PaymentTwoCreditCardsV3 selectBanco(String banco, int indexTarjeta){
        waitImplicitly(2000);
        Select bancoSelect = new Select (bancoList.get(indexTarjeta));
        logger.info("Selecting [Banco]: [" + banco + "]");
        bancoSelect.selectByVisibleText(banco);
        return this;
    }

    private PaymentTwoCreditCardsV3 selectCuotas(){
        waitImplicitly(2000);
        Select cuotaSelect;
        if(!secondPayment) {
            cuotaSelect = new Select(firstCuotaElement);
        }
        else{
            cuotaSelect = new Select(secondCuotaElement);
        }
        logger.info("Selecting [Cuotas]: [" + cuotaSelect.getOptions().get(1).getText() + "]");
        cuotaSelect.selectByIndex(1);
        secondPayment = true;
        return this;
    }

    private PaymentTwoCreditCardsV3 setTitularTarjeta(String titularTarjeta, int indexTarjeta){
        waitImplicitly(2000);
        logger.info("Entering [Titular de tarjeta]: [" + titularTarjeta + "]");
        titularDeTarjetaList.get(indexTarjeta).sendKeys(titularTarjeta);
        return this;
    }

    private PaymentTwoCreditCardsV3 selectVencMes(String mes, int indexTarjeta){
        waitImplicitly(2000);
        Select fechaDeVencimientoMes = new Select(fechaDeVencimientoMesList.get(indexTarjeta));
        logger.info("Selecting [Fecha de vencimiento - Mes]: [" + mes + "]");
        fechaDeVencimientoMes.selectByVisibleText(mes);
        return this;
    }

    private PaymentTwoCreditCardsV3 selectVencAno(String ano, int indexTarjeta){
        waitImplicitly(2000);
        Select fechaDeVencimientoAno = new Select(fechaDeVencimientoAnoList.get(indexTarjeta));
        logger.info("Selecting [Fecha de vencimiento - Año]: [" + ano + "]");
        fechaDeVencimientoAno.selectByVisibleText(ano);
        return this;
    }

    private PaymentTwoCreditCardsV3 setCodigoDeSeguridad(String codigoDeSeguridad, int indexTarjeta){
        waitImplicitly(2000);
        logger.info("Entering [Código de seguridad]: [" + codigoDeSeguridad + "]");
        codigoDeSeguridadList.get(indexTarjeta).sendKeys(codigoDeSeguridad);
        return this;
    }

    public PaymentTwoCreditCardsV3 populateTwoCreditCards(List<String> paymentDataList, int totalPrice){
        int container = 0;
        int paymentAmount = totalPrice / paymentDataList.size();
        dataManagement.getPaymentList();
        setImporteTarjeta(String.valueOf(paymentAmount));
        logger.info("Populating 2 Credit Cards Payments");
        for(String paymentData : paymentDataList) {
            logger.info("Populating credit card N°: [" + (container + 1) + "]");
            paymentDataObject = dataManagement.getPaymentData(paymentData);
            setNumeroTarjeta(paymentDataObject.get("card_number").toString(), container);
            selectTarjeta(paymentDataObject.get("credit_card_name").toString(), container);
            selectBanco(paymentDataObject.get("bank_name_two_cards").toString(), container);
            selectCuotas();
            setTitularTarjeta(paymentDataObject.get("card_holder").toString(), container);
            selectVencMes(paymentDataObject.get("month_card_expire").toString(), container);
            selectVencAno(paymentDataObject.get("year_card_expire").toString(), container);
            setCodigoDeSeguridad(paymentDataObject.get("security_code").toString(), container);
            container = container + 1;
        }
        return this;
    }
}
