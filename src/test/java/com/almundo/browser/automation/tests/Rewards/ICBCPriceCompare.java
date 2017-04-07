package com.almundo.browser.automation.tests.Rewards;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPage.PaymentSection;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.almundo.browser.automation.utils.Constants.ICBC_URL;
import static com.almundo.browser.automation.utils.Constants.PROD_URL;

/**
 * Created by leandro.efron on 10/3/2017.
 */
public class ICBCPriceCompare extends TestBaseSetup {

    private CheckOutPage checkOutPage = null;
    private PaymentSection paymentSection = null;

    SoftAssert softAssert = new SoftAssert();

    int almundo_visa_1;
    int almundo_visa_6;
    int almundo_visa_12;

    int icbc_visa_1;
    int icbc_visa_6;
    int icbc_visa_12;

    int almundo_master_1;
    int almundo_master_6;
    int almundo_master_12;
    int almundo_master_18;

    int icbc_master_1;
    int icbc_master_6;
    int icbc_master_12;
    int icbc_master_18;

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void latam() {
        logTestTitle("ICBC Store - LATAM - " + countryPar );
        checkOutPage = openAlmundoCart(cartId);

        paymentSection = checkOutPage.paymentSection();

        paymentSection.selectPaymentQty("1 cuota");

        selectCardAndBank("VI", "Visa");
        almundo_visa_1 = checkOutPage.getTotalPrice();

        selectCardAndBank("CA", "Mastercard");
        almundo_master_1 = checkOutPage.getTotalPrice();

        paymentSection.selectPaymentQty("12 cuotas");

        selectCardAndBank("VI", "ICBC");
        almundo_visa_12 = checkOutPage.getTotalPrice();

        selectCardAndBank("CA", "ICBC");
        almundo_master_12 = checkOutPage.getTotalPrice();

        paymentSection.selectOtherPayment("Mastercard", "18");
        almundo_master_18 = checkOutPage.getTotalPrice();

        checkOutPage = openIcbcCart(cartIdICBC);

        paymentSection.selectPaymentQty("1 cuota");

        selectCardAndBank("VI", "ICBC");
        icbc_visa_1 = checkOutPage.getTotalPrice();

        selectCardAndBank("CA", "ICBC");
        icbc_master_1 = checkOutPage.getTotalPrice();

        paymentSection.selectPaymentQty("12 cuotas");

        selectCardAndBank("VI", "ICBC");
        icbc_visa_12 = checkOutPage.getTotalPrice();

        selectCardAndBank("CA", "ICBC");
        icbc_master_12 = checkOutPage.getTotalPrice();

        paymentSection.selectPaymentQty("18 cuotas");

        selectCardAndBank("CA", "ICBC");
        icbc_master_18 = checkOutPage.getTotalPrice();

        logger.info("----------------------------------------------------------------");
        logger.info("Aerolínea: [" + checkOutPage.airlineName.getText() + "]");
        logger.info("Fecha Salida: [" + checkOutPage.startDate.getText() + "]");
        logger.info("Fecha Vuelta: [" + checkOutPage.endDate.getText() + "]");
        logger.info("Desde: [" + checkOutPage.originAirport.getText() + "]");
        logger.info("Hacia: [" + checkOutPage.destinationAirport.getText() + "]");
        logger.info("----------------------------------------------------------------");
        logger.info("VISA - 1 Cuota - Almundo: [" + almundo_visa_1 + "]");
        logger.info("VISA - 1 Cuota - ICBC   : [" + icbc_visa_1 + "]" + "\n");
        softAssert.assertTrue(almundo_visa_1 == icbc_visa_1, "VISA - 1 Cuota - Prices are not equal: Almundo [" + almundo_visa_1 + "] - ICBC [" + icbc_visa_1 + "]");

        logger.info("MASTERCARD - 1 Cuota - Almundo: [" + almundo_master_1 + "]");
        logger.info("MASTERCARD - 1 Cuota - ICBC   : [" + icbc_master_1  + "]" + "\n");
        softAssert.assertTrue(almundo_master_1 == icbc_master_1, "MASTERCARD - 1 Cuota - Prices are not equal: Almundo [" + almundo_master_1 + "] - ICBC [" + icbc_master_1 + "]");

        logger.info("VISA - 12 Cuotas - Almundo: [" + almundo_visa_12 + "]");
        logger.info("VISA - 12 Cuotas - ICBC   : [" + icbc_visa_12  + "]" + "\n");
        softAssert.assertTrue(almundo_visa_12 == icbc_visa_12, "VISA - 12 Cuotas - Prices are not equal: Almundo [" + almundo_visa_12 + "] - ICBC [" + icbc_visa_12 + "]");

        logger.info("MASTERCARD - 12 Cuotas - Almundo: [" + almundo_master_12 + "]");
        logger.info("MASTERCARD - 12 Cuotas - ICBC   : [" + icbc_master_12  + "]" + "\n");
        softAssert.assertTrue(almundo_master_12 == icbc_master_12, "MASTERCARD - 12 Cuotas - Prices are not equal: Almundo [" + almundo_master_12 + "] - ICBC [" + icbc_master_12 + "]");

        logger.info("MASTERCARD - 18 Cuotas - Almundo: [" + almundo_master_18  + "] -----> No se está seleccionando un banco en este caso");
        logger.info("MASTERCARD - 18 Cuotas - ICBC   : [" + icbc_master_18  + "]" + "\n");
        softAssert.assertTrue(almundo_master_18 == icbc_master_18, "MASTERCARD - 18 Cuotas - Prices are not equal: Almundo [" + almundo_master_18 + "] - ICBC [" + icbc_master_18 + "]");

        softAssert.assertAll();
    }

    @Test
    public void aerolineasArgentinas() {
        logTestTitle("ICBC Store - Aerolíneas Argentinas - " + countryPar );
        checkOutPage = openAlmundoCart(cartId);
        paymentSection = checkOutPage.paymentSection();

        paymentSection.selectPaymentQty("1 cuota");

        selectCardAndBank("VI", "Visa");
        almundo_visa_1 = checkOutPage.getTotalPrice();

        selectCardAndBank("CA", "Mastercard");
        almundo_master_1 = checkOutPage.getTotalPrice();

        paymentSection.selectPaymentQty("6 cuotas");

        selectCardAndBank("VI", "ICBC");
        almundo_visa_6 = checkOutPage.getTotalPrice();

        selectCardAndBank("CA", "ICBC");
        almundo_master_6 = checkOutPage.getTotalPrice();

        paymentSection.selectPaymentQty("12 cuotas");

        selectCardAndBank("VI", "ICBC");
        almundo_visa_12 = checkOutPage.getTotalPrice();

        selectCardAndBank("CA", "Mastercard");
        almundo_master_12 = checkOutPage.getTotalPrice();

        checkOutPage = openIcbcCart(cartIdICBC);

        paymentSection.selectPaymentQty("1 cuota");

        selectCardAndBank("VI", "ICBC");
        icbc_visa_1 = checkOutPage.getTotalPrice();

        selectCardAndBank("CA", "ICBC");
        icbc_master_1 = checkOutPage.getTotalPrice();

        paymentSection.selectPaymentQty("6 cuotas");

        selectCardAndBank("VI", "ICBC");
        icbc_visa_6 = checkOutPage.getTotalPrice();

        selectCardAndBank("CA", "ICBC");
        icbc_master_6 = checkOutPage.getTotalPrice();

        paymentSection.selectPaymentQty("12 cuotas");

        selectCardAndBank("VI", "ICBC");
        icbc_visa_12 = checkOutPage.getTotalPrice();

        selectCardAndBank("CA", "ICBC");
        icbc_master_12 = checkOutPage.getTotalPrice();

        logger.info("----------------------------------------------------------------");
        logger.info("Aerolínea: [" + checkOutPage.airlineName.getText() + "]");
        logger.info("Fecha Salida: [" + checkOutPage.startDate.getText() + "]");
        logger.info("Fecha Vuelta: [" + checkOutPage.endDate.getText() + "]");
        logger.info("Desde: [" + checkOutPage.originAirport.getText() + "]");
        logger.info("Hacia: [" + checkOutPage.destinationAirport.getText() + "]");
        logger.info("----------------------------------------------------------------");
        logger.info("VISA - 1 Cuota - Almundo: [" + almundo_visa_1 + "]");
        logger.info("VISA - 1 Cuota - ICBC   : [" + icbc_visa_1 + "]" + "\n");
        softAssert.assertTrue(almundo_visa_1 == icbc_visa_1, "VISA - 1 Cuota - Prices are not equal: Almundo [" + almundo_visa_1 + "] - ICBC [" + icbc_visa_1 + "]");

        logger.info("MASTERCARD - 1 Cuota - Almundo: [" + almundo_master_1 + "]");
        logger.info("MASTERCARD - 1 Cuota - ICBC   : [" + icbc_master_1  + "]" + "\n");
        softAssert.assertTrue(almundo_master_1 == icbc_master_1, "MASTERCARD - 1 Cuota - Prices are not equal: Almundo [" + almundo_master_1 + "] - ICBC [" + icbc_master_1 + "]");

        logger.info("VISA - 6 Cuotas - Almundo: [" + almundo_visa_6 + "]");
        logger.info("VISA - 6 Cuotas - ICBC   : [" + icbc_visa_6  + "]" + "\n");
        softAssert.assertTrue(almundo_visa_6 == icbc_visa_6, "VISA - 6 Cuotas - Prices are not equal: Almundo [" + almundo_visa_6 + "] - ICBC [" + icbc_visa_6 + "]");

        logger.info("MASTERCARD - 6 Cuotas - Almundo: [" + almundo_master_6 + "]");
        logger.info("MASTERCARD - 6 Cuotas - ICBC   : [" + icbc_master_6  + "]" + "\n");
        softAssert.assertTrue(almundo_master_6 == icbc_master_6, "MASTERCARD - 6 Cuotas - Prices are not equal: Almundo [" + almundo_master_6 + "] - ICBC [" + icbc_master_6 + "]");

        logger.info("VISA - 12 Cuotas - Almundo: [" + almundo_visa_12 + "]");
        logger.info("VISA - 12 Cuotas - ICBC   : [" + icbc_visa_12  + "]" + "\n");
        softAssert.assertTrue(almundo_visa_12 == icbc_visa_12, "VISA - 12 Cuotas - Prices are not equal: Almundo [" + almundo_visa_12 + "] - ICBC [" + icbc_visa_12 + "]");

        logger.info("MASTERCARD - 12 Cuotas - Almundo: [" + almundo_master_12 + "] -----> No se está seleccionando un banco en este caso");
        logger.info("MASTERCARD - 12 Cuotas - ICBC   : [" + icbc_master_12  + "]" + "\n");
        softAssert.assertTrue(almundo_master_12 == icbc_master_12, "MASTERCARD - 12 Cuotas - Prices are not equal: Almundo [" + almundo_master_12 + "] - ICBC [" + icbc_master_12 + "]");

        softAssert.assertAll();
    }

    @Test
    public void airCanada() {
        logTestTitle("ICBC Store - Air Canada - " + countryPar );
        checkOutPage = openAlmundoCart(cartId);
        paymentSection = checkOutPage.paymentSection();

        paymentSection.selectPaymentQty("1 cuota");

        selectCardAndBank("VI", "Visa");
        almundo_visa_1 = checkOutPage.getTotalPrice();

        selectCardAndBank("CA", "Mastercard");
        almundo_master_1 = checkOutPage.getTotalPrice();

        paymentSection.selectPaymentQty("12 cuotas");

        selectCardAndBank("VI", "ICBC");
        almundo_visa_12 = checkOutPage.getTotalPrice();

        selectCardAndBank("CA", "ICBC");
        almundo_master_12 = checkOutPage.getTotalPrice();

        checkOutPage = openIcbcCart(cartIdICBC);

        paymentSection.selectPaymentQty("1 cuota");

        selectCardAndBank("VI", "ICBC");
        icbc_visa_1 = checkOutPage.getTotalPrice();

        selectCardAndBank("CA", "ICBC");
        icbc_master_1 = checkOutPage.getTotalPrice();

        paymentSection.selectPaymentQty("12 cuotas");

        selectCardAndBank("VI", "ICBC");
        icbc_visa_12 = checkOutPage.getTotalPrice();

        selectCardAndBank("CA", "ICBC");
        icbc_master_12 = checkOutPage.getTotalPrice();

        logger.info("----------------------------------------------------------------");
        logger.info("Aerolínea: [" + checkOutPage.airlineName.getText() + "]");
        logger.info("Fecha Salida: [" + checkOutPage.startDate.getText() + "]");
        logger.info("Fecha Vuelta: [" + checkOutPage.endDate.getText() + "]");
        logger.info("Desde: [" + checkOutPage.originAirport.getText() + "]");
        logger.info("Hacia: [" + checkOutPage.destinationAirport.getText() + "]");
        logger.info("----------------------------------------------------------------");
        logger.info("VISA - 1 Cuota - Almundo: [" + almundo_visa_1 + "]");
        logger.info("VISA - 1 Cuota - ICBC   : [" + icbc_visa_1 + "]" + "\n");
        softAssert.assertTrue(almundo_visa_1 == icbc_visa_1, "VISA - 1 Cuota - Prices are not equal: Almundo [" + almundo_visa_1 + "] - ICBC [" + icbc_visa_1 + "]");

        logger.info("MASTERCARD - 1 Cuota - Almundo: [" + almundo_master_1 + "]");
        logger.info("MASTERCARD - 1 Cuota - ICBC   : [" + icbc_master_1  + "]" + "\n");
        softAssert.assertTrue(almundo_master_1 == icbc_master_1, "MASTERCARD - 1 Cuota - Prices are not equal: Almundo [" + almundo_master_1 + "] - ICBC [" + icbc_master_1 + "]");

        logger.info("VISA - 12 Cuotas - Almundo: [" + almundo_visa_12 + "]");
        logger.info("VISA - 12 Cuotas - ICBC   : [" + icbc_visa_12  + "]" + "\n");
        softAssert.assertTrue(almundo_visa_12 == icbc_visa_12, "VISA - 12 Cuotas - Prices are not equal: Almundo [" + almundo_visa_12 + "] - ICBC [" + icbc_visa_12 + "]");

        logger.info("MASTERCARD - 12 Cuotas - Almundo: [" + almundo_master_12 + "]");
        logger.info("MASTERCARD - 12 Cuotas - ICBC   : [" + icbc_master_12  + "]" + "\n");
        softAssert.assertTrue(almundo_master_12 == icbc_master_12, "MASTERCARD - 12 Cuotas - Prices are not equal: Almundo [" + almundo_master_12 + "] - ICBC [" + icbc_master_12 + "]");

        softAssert.assertAll();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    private CheckOutPage openAlmundoCart(String cartId){
        logger.info("Navigating to: [" + PROD_URL + "cart/v2/" + cartId + "]");
        driver.get(PROD_URL + "cart/v2/" + cartId);
        return initCheckOutPage();
    }

    private CheckOutPage openIcbcCart(String cartId){
        logger.info("Navigating to: [" + ICBC_URL + "cart/v2/" + cartId + "]");
        driver.get(ICBC_URL + "cart/v2/" + cartId);
        return initCheckOutPage();
    }

    private void selectCardAndBank(String creditCard, String bankName) {
        paymentSection.selectCreditCard(creditCard);
        paymentSection.selectBank(bankName);
    }
}
