package com.almundo.browser.automation.tests.Rewards;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPage.PaymentSection;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.PaymentSectionV3;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.almundo.browser.automation.utils.Constants.ICBC_URL;
import static com.almundo.browser.automation.utils.Constants.PROD_URL;

/**
 * Created by leandro.efron on 10/3/2017.
 */
public class ICBCPriceCompare extends TestBaseSetup {

    private CheckOutPage checkOutPage = null;
    private CheckOutPageV3 checkOutPageV3 = null;
    private PaymentSection paymentSection = null;
    private PaymentSectionV3 paymentSectionV3 = null;

    SoftAssert softAssert = new SoftAssert();

    int almundo_visa_1;
    int almundo_visa_6;
    int almundo_visa_12;
    int almundo_visa_18;

    int icbc_visa_1;
    int icbc_visa_6;
    int icbc_visa_12;
    int icbc_visa_18;

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
        logTestTitle("ICBC Store - LATAM");
        checkOutPageV3 = openAlmundoCart(cartId);
        paymentSectionV3 = checkOutPageV3.paymentSection();

        selectPaymentV3("1","ICBC", "Visa");
        almundo_visa_1 = checkOutPageV3.getTotalPrice();

        paymentSectionV3.clickChangeCardLink();

        selectPaymentV3("1","Mastercard", "Mastercard");
        almundo_master_1 = checkOutPageV3.getTotalPrice();

        paymentSectionV3.clickChangeCardLink();

        selectPaymentV3("12","ICBC", "Visa");
        almundo_visa_12 = checkOutPageV3.getTotalPrice();

        paymentSectionV3.clickChangeCardLink();

        selectPaymentV3("12","ICBC", "Mastercard");
        almundo_master_12 = checkOutPageV3.getTotalPrice();

        paymentSectionV3.clickChangeCardLink();

        selectPaymentV3("18","Visa", "Visa");
        almundo_visa_18 = checkOutPageV3.getTotalPrice();

        paymentSectionV3.clickChangeCardLink();

        selectPaymentV3("18","Mastercard", "Mastercard");
        almundo_master_18 = checkOutPageV3.getTotalPrice();

        checkOutPage = openIcbcCart(cartIdICBC);
        paymentSection = checkOutPage.paymentSection();
        PageUtils.waitElementForVisibility(driver, By.cssSelector(".cards__definition__header"), 40, "Payments rows");

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

        selectCardAndBank("VI", "ICBC");
        icbc_visa_18 = checkOutPage.getTotalPrice();

        selectCardAndBank("CA", "ICBC");
        icbc_master_18 = checkOutPage.getTotalPrice();

        logger.info("******************************************************** INICIO DE PRUEBAS ********************************************************");
        printItineraryData();
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

        logger.info("VISA - 18 Cuotas - Almundo: [" + almundo_visa_18  + "] -----> No se está seleccionando un banco en este caso");
        logger.info("VISA - 18 Cuotas - ICBC   : [" + icbc_visa_18  + "]" + "\n");
        softAssert.assertTrue(almundo_visa_18 == icbc_visa_18, "VISA - 18 Cuotas - Prices are not equal: Almundo [" + almundo_visa_18 + "] - ICBC [" + icbc_visa_18 + "]");

        logger.info("MASTERCARD - 18 Cuotas - Almundo: [" + almundo_master_18  + "] -----> No se está seleccionando un banco en este caso");
        logger.info("MASTERCARD - 18 Cuotas - ICBC   : [" + icbc_master_18  + "]" + "\n");
        softAssert.assertTrue(almundo_master_18 == icbc_master_18, "MASTERCARD - 18 Cuotas - Prices are not equal: Almundo [" + almundo_master_18 + "] - ICBC [" + icbc_master_18 + "]");
        logger.info("********************************************************** FIN DE PRUEBAS **********************************************************");

        softAssert.assertAll();
    }

    @Test
    public void aerolineasArgentinas() {
        logTestTitle("ICBC Store - Aerolíneas Argentinas");
        checkOutPageV3 = openAlmundoCart(cartId);
        paymentSectionV3 = checkOutPageV3.paymentSection();

        selectPaymentV3("1","ICBC", "Visa");
        almundo_visa_1 = checkOutPageV3.getTotalPrice();

        paymentSectionV3.clickChangeCardLink();

        selectPaymentV3("1","ICBC", "Mastercard");
        almundo_master_1 = checkOutPageV3.getTotalPrice();

        paymentSectionV3.clickChangeCardLink();

        selectPaymentV3("6","ICBC", "Visa");
        almundo_visa_6 = checkOutPageV3.getTotalPrice();

        paymentSectionV3.clickChangeCardLink();

        selectPaymentV3("6","ICBC", "Mastercard");
        almundo_master_6 = checkOutPageV3.getTotalPrice();

        paymentSectionV3.clickChangeCardLink();

        selectPaymentV3("12","ICBC", "Visa");
        almundo_visa_12 = checkOutPageV3.getTotalPrice();

        paymentSectionV3.clickChangeCardLink();

        selectPaymentV3("12","ICBC", "Mastercard");
        almundo_master_12 = checkOutPageV3.getTotalPrice();

        checkOutPage = openIcbcCart(cartIdICBC);
        paymentSection = checkOutPage.paymentSection();
        PageUtils.waitElementForVisibility(driver, By.cssSelector(".cards__definition__header"), 40, "Payments rows");

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

        logger.info("******************************************************** INICIO DE PRUEBAS ********************************************************");
        printItineraryData();
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
        logger.info("********************************************************** FIN DE PRUEBAS **********************************************************");
        
        softAssert.assertAll();
    }

    @Test
    public void airCanada() {
        logTestTitle("ICBC Store - Air Canada - ");
        checkOutPageV3 = openAlmundoCart(cartId);
        paymentSectionV3 = checkOutPageV3.paymentSection();

        selectPaymentV3("1","ICBC", "Visa");
        almundo_visa_1 = checkOutPageV3.getTotalPrice();

        paymentSectionV3.clickChangeCardLink();

        selectPaymentV3("1","Mastercard", "Mastercard");
        almundo_master_1 = checkOutPageV3.getTotalPrice();

        paymentSectionV3.clickChangeCardLink();

        selectPaymentV3("12","ICBC", "Visa");
        almundo_visa_12 = checkOutPageV3.getTotalPrice();

        paymentSectionV3.clickChangeCardLink();

        selectPaymentV3("12","ICBC", "Mastercard");
        almundo_master_12 = checkOutPageV3.getTotalPrice();

        checkOutPage = openIcbcCart(cartIdICBC);
        paymentSection = checkOutPage.paymentSection();
        PageUtils.waitElementForVisibility(driver, By.cssSelector(".cards__definition__header"), 40, "Payments rows");

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

        logger.info("******************************************************** INICIO DE PRUEBAS ********************************************************");
        printItineraryData();
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
        logger.info("********************************************************** FIN DE PRUEBAS **********************************************************");

        softAssert.assertAll();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    private CheckOutPageV3 openAlmundoCart(String cartId){
        logger.info("Navigating to: [" + PROD_URL + "checkout/" + cartId + "]");
        driver.navigate().to(PROD_URL + "checkout/" + cartId);
        return initCheckOutPageV3();
    }

    private CheckOutPage openIcbcCart(String cartId){
        logger.info("Navigating to: [" + ICBC_URL + "cart/v2/" + cartId + "]");
        driver.navigate().to(ICBC_URL + "cart/v2/" + cartId);
        return initCheckOutPage();
    }

    private void selectPaymentV3(String payment, String bankName, String cardName) {
        paymentSectionV3.setPayment(payment, ".card-container-1");
        paymentSectionV3.setBank(bankName, ".card-container-1");
        paymentSectionV3.setCreditCard(cardName, ".card-container-1");
    }

    private void selectCardAndBank(String creditCard, String bankName) {
        paymentSection.selectCreditCard(creditCard);
        paymentSection.selectBank(bankName);
    }

    private void printItineraryData() {
        logger.info("--------------------------------------------------------------------------");
        logger.info("Aerolínea: [" + checkOutPage.airlineName.getText() + "]");
        logger.info("Fecha Salida: [" + checkOutPage.startDate.getText() + "]");
        logger.info("Fecha Vuelta: [" + checkOutPage.endDate.getText() + "]");
        logger.info("Desde: [" + checkOutPage.originAirport.getText() + "]");
        logger.info("Hacia: [" + checkOutPage.destinationAirport.getText() + "]");
        logger.info("--------------------------------------------------------------------------");
    }
}
