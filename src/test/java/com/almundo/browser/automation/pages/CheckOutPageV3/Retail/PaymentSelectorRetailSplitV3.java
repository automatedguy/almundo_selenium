package com.almundo.browser.automation.pages.CheckOutPageV3.Retail;

import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static com.almundo.browser.automation.utils.PageUtils.*;

/**
 * Created by gabrielcespedes on 22/06/17.
 */
public class PaymentSelectorRetailSplitV3 extends CheckOutPageV3 {

    public PaymentSelectorRetailSplitV3(WebDriver iDriver) {
        super(iDriver);
    }

    private boolean isLastPayment;
    private int decreaseContainer = 0;
    JSONObject paymentDataObject = new JSONObject();

    /**************************** Locators **********************************/

    @FindBy(css = "#am-split-payment form-payment-split div:nth-child(2) div.container-form-payment > select")
    private List<WebElement> medioDePago;

    @FindBy(css = "#am-split-payment div:nth-child(1) > div:nth-child(1) > select")
    private Select tarjetaSelect;

    @FindBy(css = "#am-split-payment div:nth-child(1) > div:nth-child(2) > select")
    private Select bancoSelect;

    @FindBy(css = "#am-split-payment div:nth-child(4) > div:nth-child(2) > div:nth-child(1)  > div:nth-child(2) > select")
    private List<WebElement> fechaDeVencimientoMes;

    @FindBy(css = "#am-split-payment div:nth-child(4) > div:nth-child(2) > div:nth-child(1)  > div:nth-child(3) > select")
    private List<WebElement> fechaDeVencimientoAno;

    @FindBy(css = "#am-split-payment div:nth-child(4) > div:nth-child(2) > div:nth-child(2) > input")
    private List<WebElement> codigoTxt;

    @FindBy(css = "#am-split-payment > div > div:nth-child(2) > div:nth-child(2) > div > input")
    private WebElement agregarOtroMedioDePago;

    @FindBy(css = "#am-split-payment form-payment-split div:nth-child(2) > credit-cards-split div.container-description div.total-description > span.price-description.col-4-xs")
    private WebElement totalPagar;

    /**************************** Deposit / Transfers  Locators **********************************/

    @FindBy(css = "#am-split-payment form-payment-split div:nth-child(2) .container-amounts input")
    private WebElement importeDepTranfTxt;

    @FindBy(css = "#am-split-payment form-payment-split div:nth-child(2) .container-charges select")
    private WebElement cargosPercepcionesGeneradosDepTranfTxt;

    /**************************** Actions **********************************/

    private PaymentSelectorRetailSplitV3 selectMedioDePago(String paymentForm, int container) {
        logger.info("Selecting Payment Form: " + "[" + paymentForm + "]");
        Select medioDePagoSelect = new  Select (medioDePago.get(container));
        medioDePagoSelect.selectByVisibleText(paymentForm);
        waitImplicitly(4000);
        return this;
    }

    private PaymentSelectorRetailSplitV3 enterNumeroDeTarjeta(String numeroDeTarjeta, int container){
        WebElement numeroDeTarjetaTxt= driver.findElement(By.cssSelector("#am-split-payment form-payment-split:nth-child( " + (container+1) +") div:nth-child(2) .container-credit-card input"));
        logger.info("Entering [Número de tarjeta]: " + "[" + numeroDeTarjeta + "]");
        numeroDeTarjetaTxt.sendKeys(numeroDeTarjeta);
        waitImplicitly(2000);
        return this;
    }

    private PaymentSelectorRetailSplitV3 selectTarjeta(String targeta){
        logger.info("Selecting [Tarjeta]: " + "[" + targeta + "]");
        tarjetaSelect.selectByVisibleText(targeta);
        return this;
    }

    private PaymentSelectorRetailSplitV3 selectBanco(String banco){
        logger.info("Selecting [Banco]: " + "[" + banco + "]");
        bancoSelect.selectByVisibleText(banco);
        return this;
    }

    private PaymentSelectorRetailSplitV3 enterImporte(String importe, int container){
        WebElement importeTxt = driver.findElement(By.cssSelector("#am-split-payment form-payment-split:nth-child(" + (container+1) + ") div:nth-child(2) credit-cards-split div:nth-child(2) div:nth-child(1) input"));
        logger.info("Entering [Importe]: " + "[" + importe + "]");
        importeTxt.clear();
        importeTxt.sendKeys(importe);
        waitImplicitly(2000);
        return this;
    }

    private PaymentSelectorRetailSplitV3 selectCargosPercepcionesGenerados(String cargosPercepciones, int container){
        logger.info("Selecting [Cargos/Percepciones generados]: " + "[" + cargosPercepciones + "]");
        WebElement cargosPercepcionesGenerados = driver.findElement(By.cssSelector("#am-split-payment form-payment-split:nth-child(" + (container+1) +") div:nth-child(2) credit-cards-split div:nth-child(2) > div:nth-child(2) select"));
        Select cargosPercepcionesGeneradosSelect =  new Select (cargosPercepcionesGenerados);
        cargosPercepcionesGeneradosSelect.selectByVisibleText(cargosPercepciones);
        waitImplicitly(5000);
        return this;
    }

    private PaymentSelectorRetailSplitV3 selectCargosPercepcionesGeneradosCash(String cargosPercepciones, int container){
        logger.info("Selecting [Cargos/Percepciones generados]: " + "[" + cargosPercepciones + "]");
        WebElement cargosPercepcionesGenerados = driver.findElement(By.cssSelector("#am-split-payment form-payment-split:nth-child(" + (container+1) +") div:nth-child(2) .container-charges > select"));
        Select cargosPercepcionesGeneradosSelect =  new Select (cargosPercepcionesGenerados);
        cargosPercepcionesGeneradosSelect.selectByVisibleText(cargosPercepciones);
        waitImplicitly(5000);
        return this;
    }

    private PaymentSelectorRetailSplitV3 selectCuotas(String cuotas, int paymentAmount, int container){
        logger.info("Selecting [Cuotas]: " + "[" + cuotas + "]");
        waitImplicitly(4000);
        List<WebElement> cuotasDdl = driver.findElements(By.cssSelector("#am-split-payment form-payment-split div:nth-child(2) > credit-cards-split div:nth-child(3) > div:nth-child(1) > select"));
        Select cuotasSelect = new Select (cuotasDdl.get(container));
        if(false) {
            String currency = getCountryCurrency();
            String totalPagarCuota = setTotalCuota(paymentAmount);
            String cuotasFinal = cuotas + " de " + currency + " " + totalPagarCuota + " (Total: " + currency + " " + totalPagarCuota + ")";
            cuotasSelect.selectByVisibleText(cuotasFinal);
        }else{
            logger.warn("Selected the following option, because it is available: " + cuotasSelect.getOptions().get(1).getText());
            cuotasSelect.selectByIndex(1);
        }
        return this;
    }

    private PaymentSelectorRetailSplitV3 enterTitularDeLaTarjeta(String titularTarjeta, int container){
        logger.info("Entering [Titular de la tarjeta]: " + "[" + titularTarjeta + "]");
        List<WebElement> titularDeLaTarjetaTxt = driver.findElements(By.cssSelector("#am-split-payment form-payment-split div:nth-child(2) > credit-cards-split div:nth-child(4) > div:nth-child(1) > input"));
        titularDeLaTarjetaTxt.get(container).sendKeys(titularTarjeta);
        return this;
    }

    private PaymentSelectorRetailSplitV3 selectFechaDeVencimientoMes(String mes, int container){
        logger.info("Selecting [Fecha de vencimiento - Mes]: " + "[" + mes + "]");
        Select fechaDeVencimientoMesSelect =  new Select (fechaDeVencimientoMes.get(container));
        fechaDeVencimientoMesSelect.selectByVisibleText(mes);
        return this;
    }

    private PaymentSelectorRetailSplitV3 selectFechaDeVencimientoAno(String ano, int container){
        logger.info("Selecting [Fecha de vencimiento - Año]: " + "[" + ano + "]");
        Select fechaDeVencimientoAnoSelect =  new Select (fechaDeVencimientoAno.get(container));
        fechaDeVencimientoAnoSelect.selectByVisibleText(ano);
        return this;
    }

    private PaymentSelectorRetailSplitV3 enterCodigo(String codigo, int container){
        logger.info("Entering [Código]: " + codigo);
        codigoTxt.get(container).sendKeys(codigo);
        return this;
    }

    public PaymentSelectorRetailSplitV3 agregarOtroMedioDePagoClick(){
        scrollToElement(driver, agregarOtroMedioDePago);
        waitElementForClickable(driver, agregarOtroMedioDePago, 5, "[Agregar Medio de pago] button");
        logger.info("Clicking on: [Agregar Medio de pago]");
        agregarOtroMedioDePago.click();
        waitImplicitly(2000);
        return this;
    }

    private String getRemainingAmount(int container){
        String remainingAmount = driver.findElement(By.cssSelector("#am-split-payment div:nth-child(2) div:nth-child(1) form-payment-split:nth-child(" + (container+1)+ ") div:nth-child(1) span.col-8-xs")).getText();
        return StringUtils.substringBetween(remainingAmount, getCountryCurrency(), ")").replaceAll("\\s","");
    }

    /**************************** Deposit / Transfers Actions **********************************/

    private PaymentSelectorRetailSplitV3 populateDepositTranfPaymentInfo(String depositTranfAmount, int container){
        if (!isLastPayment) {
            logger.info("Entering amount [" + depositTranfAmount + "]");
            importeDepTranfTxt.sendKeys(depositTranfAmount);
        }
        else{
            logger.info("Remaininng Amount to Pay: [" + getRemainingAmount(container) + "]");
        }
        selectCargosPercepcionesGeneradosCash("Incluirlos en el importe", container);
        return this;
    }

    /**************************** Credit Card Actions **********************************/

    private PaymentSelectorRetailSplitV3 populateCreditCardPaymentInfo(int paymentAmount, int container){
        enterNumeroDeTarjeta(paymentDataObject.get("card_number").toString(), container);
        if (!isLastPayment) {
            enterImporte(String.valueOf(paymentAmount), container);
        }
        selectCargosPercepcionesGenerados("Sumarlos al importe", container);
        if (!isLastPayment) {
            selectCuotas(paymentDataObject.get("payment_qty").toString(), paymentAmount, (container - decreaseContainer));
        } else {
            selectCuotas(paymentDataObject.get("payment_qty").toString(), Integer.valueOf(getRemainingAmount(container).replaceAll("\\.", "")), (container - decreaseContainer));
        }
        enterTitularDeLaTarjeta(paymentDataObject.get("card_holder").toString(), (container - decreaseContainer));
        selectFechaDeVencimientoMes(paymentDataObject.get("month_card_expire").toString(), (container - decreaseContainer));
        selectFechaDeVencimientoAno(paymentDataObject.get("year_card_expire").toString(), (container - decreaseContainer));
        enterCodigo(paymentDataObject.get("security_code").toString(), (container - decreaseContainer));
        decreaseContainer = 0;
        return this;
    }

    public PaymentSelectorRetailSplitV3 populateSplittedPaymentInfo(List<String> paymentDataList, int  totalPrice){
        int container = 0;
        int paymentAmount = totalPrice / paymentDataList.size();
        dataManagement.getPaymentList();
        for(String paymentData : paymentDataList) {
            isLastPayment = paymentDataList.size() <= (container+1);
            paymentDataObject = dataManagement.getPaymentData(paymentData);
            logger.info("------------- Filling Payment Section -------------");
            logger.info("Getting payment data for: " + "[" + paymentData + "]");
            switch(paymentData){
                case "cash" :
                    selectMedioDePago("Efectivo", container);
                    populateDepositTranfPaymentInfo(String.valueOf(paymentAmount), container);
                    break;
                case "deposit" :
                    selectMedioDePago("Depósito", container);
                    populateDepositTranfPaymentInfo(String.valueOf(paymentAmount), container);
                    break;
                case "transfer" :
                    selectMedioDePago("Transferencia", container);
                    populateDepositTranfPaymentInfo(String.valueOf(paymentAmount), container);
                    break;
                default:
                    selectMedioDePago("Tarjeta de crédito", container);
                    populateCreditCardPaymentInfo(paymentAmount, container);
                    break;
            }
            container = container + 1;
            if(!isLastPayment) {
                agregarOtroMedioDePagoClick();
            }
        }
        return this;
    }
}