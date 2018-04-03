package com.almundo.browser.automation.pages.CheckOutPageV3;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static com.almundo.browser.automation.utils.PageUtils.waitElementForClickable;
import static com.almundo.browser.automation.utils.PageUtils.waitSelectContainsResults;
import static com.almundo.browser.automation.utils.PageUtils.waitWithTryCatch;

public class FloridaCreditCard extends CheckOutPageV3 {

    public FloridaCreditCard(WebDriver driver) {
        super(driver);
    }

    JSONObject paymentDataObject = new JSONObject();

    /********************* Locators *******************/

    @FindBy(css = "#am-retail-payment am-fops-multiple-payment am-one-card-ts div:nth-child(1) > div:nth-child(1) > label > input")
    private List<WebElement> importeTarjetaInput;

    @FindBy(css = "#paymentOptionSection div:nth-child(2) > input")
    private List<WebElement> numeroTarjetaInput;

    @FindBy(css = "#paymentOptionSection div:nth-child(2) > div:nth-child(1) > select")
    private List<WebElement> tarjetaSelect;

    @FindBy(css = "#paymentOptionSection div:nth-child(2) > div:nth-child(2) > select")
    private List<WebElement> bancoSelect;

    @FindBy(css = "#paymentOptionSection div:nth-child(3) > div > select")
    private List<WebElement> cuotasSelect;

    @FindBy(css = "#am-retail-payment am-fops-multiple-payment am-one-card-ts div:nth-child(4) .container-field input")
    private List<WebElement> titularTarjetaInput;

    @FindBy(css = "#paymentOptionSection div:nth-child(4) > div:nth-child(2) > select")
    private List<WebElement> fechVencMesSelect;

    @FindBy(css = "#paymentOptionSection div:nth-child(4) > div:nth-child(3) > select")
    private List<WebElement> getFechVencAnoSelect;

    @FindBy(css = "#paymentOptionSection div:nth-child(5) input")
    private List<WebElement> codigoSeguridadInput;

    /********************* Actions *******************/

    private FloridaCreditCard setImporteTarjeta(String importeTarjeta, int index){
        logger.info("Setting [Importe a pagar con la tarjeta]: [" + importeTarjeta + "]");
        importeTarjetaInput.get(index).clear();
        importeTarjetaInput.get(index).sendKeys(importeTarjeta);
        return this;
    }

    private FloridaCreditCard setNumeroTarjeta(String numeroTarjeta, int index){
        logger.info("Setting [Número de tarjeta]: [" + numeroTarjeta + "]");
        waitElementForClickable(driver, numeroTarjetaInput.get(index), 10, "Numero de tarjeta.");
        numeroTarjetaInput.get(index).clear();
        numeroTarjetaInput.get(index).sendKeys(numeroTarjeta);
        return this;
    }

    private FloridaCreditCard setTarjeta(String tarjeta, int index){
        logger.info("Selecting [Tarjeta]: [" + tarjeta + "]");
        Select tarjetaDdl = new Select(tarjetaSelect.get(index));
        waitSelectContainsResults(tarjetaDdl, "Tarjeta Select", 10, 3 );
        tarjetaDdl.selectByVisibleText(tarjeta);
        return this;
    }

    private FloridaCreditCard setBanco(String banco, int index){
        logger.info("Selecting [Banco]: [" + banco + "]");
        Select bancoDdl =  new Select(bancoSelect.get(index));
        waitSelectContainsResults(bancoDdl, "Banco Select", 10, 2 );
        bancoDdl.selectByVisibleText(banco);
        return this;
    }

    private FloridaCreditCard setCuotas(int index){
        Select cuotasDdl =  new Select(cuotasSelect.get(index));
        waitSelectContainsResults(cuotasDdl, "Cuotas Select", 10, 2 );
        logger.info("Selecting [Cuotas] first Option: [" + cuotasDdl.getOptions().get(1).getText() + "]");
        cuotasDdl.selectByIndex(1);
        return this;
    }

    private FloridaCreditCard setTitularTarjeta(String titularTarjeta, int index){
        logger.info("Setting [Titular de tarjeta]: [" + titularTarjeta + "]");
        titularTarjetaInput.get(index).clear();
        titularTarjetaInput.get(index).sendKeys(titularTarjeta);
        return this;
    }

    private FloridaCreditCard setFechVencMes(String vencMes, int index){
        logger.info("Setting [Fecha de vencimiento -  Mes]: [" + vencMes + "]");
        Select fechaVencMesDdl =  new Select(fechVencMesSelect.get(index));
        fechaVencMesDdl.selectByVisibleText(vencMes);
        return this;
    }

    private FloridaCreditCard setFechVencAno(String vencAno, int index){
        logger.info("Setting [Fecha de vencimiento - Año]: [" + vencAno + "]");
        Select fechaVencAnoDdl = new Select(getFechVencAnoSelect.get(index));
        fechaVencAnoDdl.selectByVisibleText(vencAno);
        return this;
    }

    private FloridaCreditCard setCodigoSeguridad(String codigoSeguridad, int index){
        logger.info("Setting [Código de seguridad]: [" + codigoSeguridad + "]");
        codigoSeguridadInput.get(index).clear();
        codigoSeguridadInput.get(index).sendKeys(codigoSeguridad);
        return this;
    }

    public FloridaCreditCard populateCreditCardInfo(String paymentData, String priceToPay, int index, boolean isLastPayment){
        logger.info("Setting Credit Card Info...");
        dataManagement.setPaymentList();

        paymentDataObject = dataManagement.setPaymentData(paymentData);

        if(inputDef.isRequired("payments","credit_card_number",0)){
            if(!isLastPayment){
                setImporteTarjeta(priceToPay, index);
            }
            setNumeroTarjeta(paymentDataObject.get("card_number").toString(), index);
            setTarjeta(paymentDataObject.get("credit_card_name").toString(), index);
            setBanco(paymentDataObject.get("bank_name_two_cards").toString(), index);
            setCuotas(index);
            setTitularTarjeta(paymentDataObject.get("card_holder").toString(), index);
            setFechVencMes(paymentDataObject.get("month_card_expire").toString(), index);
            setFechVencAno(paymentDataObject.get("year_card_expire").toString(), index);
            setCodigoSeguridad(paymentDataObject.get("security_code").toString(), index);
        }
        return this;
    }
}