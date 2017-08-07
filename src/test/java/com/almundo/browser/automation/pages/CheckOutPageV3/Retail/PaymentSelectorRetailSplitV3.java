package com.almundo.browser.automation.pages.CheckOutPageV3.Retail;

import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static com.almundo.browser.automation.utils.PageUtils.getCountryCurrency;
import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

/**
 * Created by gabrielcespedes on 22/06/17.
 */
public class PaymentSelectorRetailSplitV3 extends CheckOutPageV3 {

    public PaymentSelectorRetailSplitV3(WebDriver iDriver) {
        super(iDriver);
    }

    JSONObject paymentDataObject = new JSONObject();

    /**************************** Locators **********************************/

    @FindBy(css = "#am-split-payment .container-form-payment > select")
    private WebElement medioDePago;

    @FindBy(css = "#am-split-payment .container-credit-card input")
    private WebElement numeroDeTarjetaTxt;

    @FindBy(css = "#am-split-payment div:nth-child(1) > div:nth-child(1) > select")
    private Select tarjetaSelect;

    @FindBy(css = "#am-split-payment div:nth-child(1) > div:nth-child(2) > select")
    private Select bancoSelect;

    @FindBy(css = "#am-split-payment div:nth-child(2) > div:nth-child(1) > input")
    private WebElement importeTxt;

    @FindBy(css = "#am-split-payment div:nth-child(2) > div:nth-child(2) > select")
    private WebElement cargosPercepcionesGenerados;

    @FindBy(css = "#am-split-payment div:nth-child(2) > credit-cards-split > div > div:nth-child(3) select")
    private WebElement cuotasDdl;

    @FindBy(css = "#am-split-payment div:nth-child(4) > div:nth-child(1) > input")
    private WebElement titularDeLaTarjetaTxt;

    @FindBy(css = "#am-split-payment div:nth-child(4) > div:nth-child(2) > div:nth-child(1)  > div:nth-child(2) > select")
    private WebElement fechaDeVencimientoMes;

    @FindBy(css = "#am-split-payment div:nth-child(4) > div:nth-child(2) > div:nth-child(1)  > div:nth-child(3) > select")
    private WebElement fechaDeVencimientoAno;

    @FindBy(css = "#am-split-payment div:nth-child(4) > div:nth-child(2) > div:nth-child(2) > input")
    private WebElement codigoTxt;

    @FindBy(css = "#am-split-payment > div > div:nth-child(2) > div:nth-child(2) > div > input")
    private WebElement agregarOtroMedioDePago;

    @FindBy(css = "#am-split-payment form-payment-split div:nth-child(2) > credit-cards-split div.container-description div.total-description > span.price-description.col-4-xs")
    private WebElement totalPagar;

    /**************************** Actions **********************************/

    private PaymentSelectorRetailSplitV3 selectMedioDePago(String paymentForm) {
        logger.info("Selecting Payment Form: " + "[" + paymentForm + "]");
        Select medioDePagoSelect = new  Select (medioDePago);
        medioDePagoSelect.selectByVisibleText(paymentForm);
        waitImplicitly(1000);
        return this;
    }

    private PaymentSelectorRetailSplitV3 enterNumeroDeTarjeta(String numeroDeTarjeta){
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

    private PaymentSelectorRetailSplitV3 enterImporte(String importe){
        logger.info("Entering [Importe]: " + "[" + importe + "]");
        importeTxt.sendKeys(importe);
        waitImplicitly(1000);
        return this;
    }

    private PaymentSelectorRetailSplitV3 selectCargosPercepcionesGenerados(String cargosPercepciones){
        logger.info("Selecting [Cargos/Percepciones generados]: " + "[" + cargosPercepciones + "]");
        Select cargosPercepcionesGeneradosSelect =  new Select (cargosPercepcionesGenerados);
        cargosPercepcionesGeneradosSelect.selectByVisibleText(cargosPercepciones);
        waitImplicitly(1000);
        return this;
    }

    private String totalCuota(int halPrice){
        StringBuilder str = new StringBuilder(Integer.toString(halPrice));
        int idx = str.length() - 3;
        while (idx > 0)
        {
            str.insert(idx, ".");
            idx = idx - 3;
        }
        return str.toString();
    }

    private PaymentSelectorRetailSplitV3 selectCuotas(String cuotas, int halfPrice){
        logger.info("Selecting [Cuotas]: " + "[" + cuotas + "]");
        Select cuotasSelect = new Select (cuotasDdl);
        String currency = getCountryCurrency();
        String totalPagarCuota = totalCuota(halfPrice);
        String cuotasFinal = cuotas + " de " + currency + " " + totalPagarCuota + " (Total: " + currency + " " + totalPagarCuota + ")";
        cuotasSelect.selectByVisibleText(cuotasFinal);
        return this;
    }

    private PaymentSelectorRetailSplitV3 enterTitularDeLaTarjeta(String titularTarjeta){
        logger.info("Entering [Titular de la tarjeta]: " + "[" + titularTarjeta + "]");
        titularDeLaTarjetaTxt.sendKeys(titularTarjeta);
        return this;
    }

    private PaymentSelectorRetailSplitV3 selectFechaDeVencimientoMes(String mes){
        logger.info("Selecting [Fecha de vencimiento - Mes]: " + "[" + mes + "]");
        Select fechaDeVencimientoMesSelect =  new Select (fechaDeVencimientoMes);
        fechaDeVencimientoMesSelect.selectByVisibleText(mes);
        return this;
    }

    private PaymentSelectorRetailSplitV3 selectFechaDeVencimientoAno(String ano){
        logger.info("Selecting [Fecha de vencimiento - Año]: " + "[" + ano + "]");
        Select fechaDeVencimientoAnoSelect =  new Select (fechaDeVencimientoAno);
        fechaDeVencimientoAnoSelect.selectByVisibleText(ano);
        return this;
    }

    private PaymentSelectorRetailSplitV3 enterCodigo(String codigo){
        logger.info("Entering [Código]: " + codigo);
        codigoTxt.sendKeys(codigo);
        return this;
    }

    public PaymentSelectorRetailSplitV3 agregarOtroMedioDePagoClick(){
        logger.info("Clicking on: [Agregar Medio de pago]");
        agregarOtroMedioDePago.click();
        return this;
    }

    public PaymentSelectorRetailSplitV3 populateSplittedCreditCardData(String paymentData, int  halfPrice){
        logger.info("Getting payment data for: " + "[" + paymentData + "]");
        dataManagement.getPaymentList();
        paymentDataObject = dataManagement.getPaymentData(paymentData);

        logger.info("------------- Filling Payment Section -------------");

        selectMedioDePago("Tarjeta de crédito");

        if(inputDef.isRequired("payments","credit_card_number",0)){
            enterNumeroDeTarjeta(paymentDataObject.get("card_number").toString());}

        // selectTarjeta(paymentDataObject.get("credit_card_name").toString());
        // selectBanco(paymentDataObject.get("bank_name").toString());

        enterImporte(String.valueOf(halfPrice));
        selectCargosPercepcionesGenerados("Incluirlos en el importe");
        selectCuotas(paymentDataObject.get("payment_qty").toString(), halfPrice);
        enterTitularDeLaTarjeta(paymentDataObject.get("card_holder").toString());
        selectFechaDeVencimientoMes(paymentDataObject.get("month_card_expire").toString());
        selectFechaDeVencimientoAno(paymentDataObject.get("year_card_expire").toString());
        enterCodigo(paymentDataObject.get("security_code").toString());
        return this;
    }
}