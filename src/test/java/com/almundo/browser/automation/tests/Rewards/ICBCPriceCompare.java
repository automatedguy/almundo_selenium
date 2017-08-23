package com.almundo.browser.automation.tests.Rewards;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPage.PaymentSection;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.PaymentSectionGridV3;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.almundo.browser.automation.utils.Constants.ICBC_URL;
import static com.almundo.browser.automation.utils.Constants.PROD_URL;
import static com.almundo.browser.automation.utils.Constants.Results.FAILED;
import static com.almundo.browser.automation.utils.Constants.Results.PASSED;

/**
 * Created by leandro.efron on 10/3/2017.
 */
public class ICBCPriceCompare extends TestBaseSetup {

    private CheckOutPage checkOutPage = null;
    private PaymentSection paymentSection = null;

    private CheckOutPageV3 checkOutPageV3 = null;
    private PaymentSectionGridV3 paymentSectionGridV3 = null;

    private int difference = 0;
    private boolean failedTest = false;

    SoftAssert softAssert = new SoftAssert();

    int almundo_visa_1 = 0;
    int almundo_visa_6 = 0;
    int almundo_visa_12 = 0;

    int icbc_visa_1 = 0;
    int icbc_visa_6 = 0;
    int icbc_visa_12 = 0;

    int almundo_master_1 = 0;
    int almundo_master_6 = 0;
    int almundo_master_12 = 0;

    int icbc_master_1 = 0;
    int icbc_master_6 = 0;
    int icbc_master_12 = 0;

    final String VISA = "Visa";
    final String MASTERCARD = "Mastercard";
    final String BANCO_ICBC = "Banco ICBC";

    @AfterMethod
    private void setTestResult() {
        if(!failedTest){
            setResultSauceLabs(PASSED);}
        failedTest = false;
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @SuppressWarnings("Duplicates")
    @Test
    public void latam() {
        logTestTitle("ICBC Store - LATAM");
        checkOutPageV3 = openAlmundoCart(cartId);

        paymentSectionGridV3 = initPaymentSectionGridV3();

        selectPaymentV3("1",BANCO_ICBC, VISA);
        almundo_visa_1 = checkOutPageV3.getTotalPrice();
        paymentSectionGridV3.clickChangeCardLink();

/*        selectPaymentV3("1",BANCO_ICBC, MASTERCARD);
        almundo_master_1 = checkOutPageV3.getTotalPrice();
        paymentSectionGridV3.clickChangeCardLink();*/

        selectPaymentV3("6",BANCO_ICBC, VISA);
        almundo_visa_6 = checkOutPageV3.getTotalPrice();
        paymentSectionGridV3.clickChangeCardLink();

        selectPaymentV3("6",BANCO_ICBC, MASTERCARD);
        almundo_master_6 = checkOutPageV3.getTotalPrice();
        paymentSectionGridV3.clickChangeCardLink();

        selectPaymentV3("12",BANCO_ICBC, VISA);
        almundo_visa_12 = checkOutPageV3.getTotalPrice();
        paymentSectionGridV3.clickChangeCardLink();

        selectPaymentV3("12",BANCO_ICBC, MASTERCARD);
        almundo_master_12 = checkOutPageV3.getTotalPrice();

//        paymentSectionGridV3.clickChangeCardLink();
//
//        selectPaymentV3("18","Visa", "Visa");
//        almundo_visa_18 = checkOutPageV3.getTotalPrice();
//
//        paymentSectionGridV3.clickChangeCardLink();
//
//        selectPaymentV3("18","Mastercard", "Mastercard");
//        almundo_master_18 = checkOutPageV3.getTotalPrice();

        /****************** ICBC ******************/

        checkOutPage = openIcbcCart(cartIdICBC);
        paymentSection = checkOutPage.paymentSection();

        paymentSection.selectPaymentQty("1 cuota");
        selectCardAndBank(VISA, BANCO_ICBC);
        icbc_visa_1 = checkOutPage.getTotalPrice();
        paymentSection.clickChangeCardLink();

        paymentSection.selectPaymentQty("1 cuota");
        selectCardAndBank(MASTERCARD, BANCO_ICBC);
        icbc_master_1 = checkOutPage.getTotalPrice();
        paymentSection.clickChangeCardLink();

        paymentSection.selectPaymentQty("6 cuota");
        selectCardAndBank(VISA, BANCO_ICBC);
        icbc_visa_6 = checkOutPage.getTotalPrice();
        paymentSection.clickChangeCardLink();

        paymentSection.selectPaymentQty("6 cuota");
        selectCardAndBank(MASTERCARD, BANCO_ICBC);
        icbc_master_6 = checkOutPage.getTotalPrice();
        paymentSection.clickChangeCardLink();

        paymentSection.selectPaymentQty("12 cuota");
        selectCardAndBank(VISA, BANCO_ICBC);
        icbc_visa_12 = checkOutPage.getTotalPrice();
        paymentSection.clickChangeCardLink();

        paymentSection.selectPaymentQty("12 cuota");
        selectCardAndBank(MASTERCARD, BANCO_ICBC);
        icbc_master_12 = checkOutPage.getTotalPrice();
        paymentSection.clickChangeCardLink();

//        paymentSection.selectPaymentQty("18 cuotas");
//
//        selectCardAndBank("VI", "ICBC");
//        icbc_visa_18 = checkOutPage.getTotalPrice();
//
//        selectCardAndBank("CA", "ICBC");
//        icbc_master_18 = checkOutPage.getTotalPrice();

        logger.info("******************************************************** INICIO DE PRUEBAS ********************************************************");
        printItineraryData();
        printPriceData(VISA,"1", almundo_visa_1, icbc_visa_1);
        softAssert.assertTrue(checkComparison(difference >= -15), "VISA - 1 Cuota - Prices have big difference: [" + difference + "] - Almundo [" + almundo_visa_1 + "] - ICBC [" + icbc_visa_1 + "]");

        printPriceData(MASTERCARD,"1", almundo_master_1, icbc_master_1);
        softAssert.assertTrue(checkComparison(difference >= -15), "MASTERCARD - 1 Cuota - Prices have big difference: [" + difference + "] - Almundo [" + almundo_master_1 + "] - ICBC [" + icbc_master_1 + "]");

        printPriceData(VISA,"6", almundo_visa_6, icbc_visa_6);
        softAssert.assertTrue(checkComparison(difference >= -15), "VISA - 6 Cuotas - Prices have big difference: [" + difference + "] - Almundo [" + almundo_visa_6 + "] - ICBC [" + icbc_visa_6 + "]");

        printPriceData(MASTERCARD,"6", almundo_master_6, icbc_master_6);
        softAssert.assertTrue(checkComparison(difference >= -15), "MASTERCARD - 6 Cuotas - Prices have big difference: [" + difference + "] - Almundo [" + almundo_master_6 + "] - ICBC [" + icbc_master_6 + "]");

        printPriceData(VISA,"12", almundo_visa_12, icbc_visa_12);
        softAssert.assertTrue(checkComparison(difference >= -15), "VISA - 12 Cuotas - Prices have big difference: [" + difference + "] - Almundo [" + almundo_visa_12 + "] - ICBC [" + icbc_visa_12 + "]");

        printPriceData(MASTERCARD,"12", almundo_master_12, icbc_master_12);
        softAssert.assertTrue(checkComparison(difference >= -15), "MASTERCARD - 12 Cuotas - Prices have big difference: [" + difference + "] - Almundo [" + almundo_master_12 + "] - ICBC [" + icbc_master_12 + "]");

//        logger.info("VISA - 18 Cuotas - Almundo: [" + almundo_visa_18  + "] -----> No se está seleccionando un banco en este caso");
//        logger.info("VISA - 18 Cuotas - ICBC   : [" + icbc_visa_18  + "]" + "\n");
//        softAssert.assertTrue(almundo_visa_18 == icbc_visa_18, "VISA - 18 Cuotas - Prices are not equal: Almundo [" + almundo_visa_18 + "] - ICBC [" + icbc_visa_18 + "]");
//
//        logger.info("MASTERCARD - 18 Cuotas - Almundo: [" + almundo_master_18  + "] -----> No se está seleccionando un banco en este caso");
//        logger.info("MASTERCARD - 18 Cuotas - ICBC   : [" + icbc_master_18  + "]" + "\n");
//        softAssert.assertTrue(almundo_master_18 == icbc_master_18, "MASTERCARD - 18 Cuotas - Prices are not equal: Almundo [" + almundo_master_18 + "] - ICBC [" + icbc_master_18 + "]");
        logger.info("********************************************************** FIN DE PRUEBAS **********************************************************");

        if(failedTest){
            setResultSauceLabs(FAILED);}
        softAssert.assertAll();
    }

    @Test
    public void aerolineasArgentinas() {
        logTestTitle("ICBC Store - Aerolíneas Argentinas");
        checkOutPageV3 = openAlmundoCart(cartId);

        paymentSectionGridV3 = initPaymentSectionGridV3();

        selectPaymentV3("1",BANCO_ICBC, VISA);
        almundo_visa_1 = checkOutPageV3.getTotalPrice();
        paymentSectionGridV3.clickChangeCardLink();

/*        selectPaymentV3("1",BANCO_ICBC, MASTERCARD);
        almundo_master_1 = checkOutPageV3.getTotalPrice();
        paymentSectionGridV3.clickChangeCardLink();*/

        selectPaymentV3("6",BANCO_ICBC, VISA);
        almundo_visa_6 = checkOutPageV3.getTotalPrice();
        paymentSectionGridV3.clickChangeCardLink();

        selectPaymentV3("6",BANCO_ICBC, MASTERCARD);
        almundo_master_6 = checkOutPageV3.getTotalPrice();
        paymentSectionGridV3.clickChangeCardLink();

        selectPaymentV3("12",BANCO_ICBC, VISA);
        almundo_visa_12 = checkOutPageV3.getTotalPrice();
        paymentSectionGridV3.clickChangeCardLink();

        selectPaymentV3("12",BANCO_ICBC, MASTERCARD);
        almundo_master_12 = checkOutPageV3.getTotalPrice();

        /****************** ICBC ******************/

        checkOutPage = openIcbcCart(cartIdICBC);
        paymentSection = checkOutPage.paymentSection();

        paymentSection.selectPaymentQty("1 cuota");
        selectCardAndBank(VISA, BANCO_ICBC);
        icbc_visa_1 = checkOutPage.getTotalPrice();
        paymentSection.clickChangeCardLink();

        paymentSection.selectPaymentQty("1 cuota");
        selectCardAndBank(MASTERCARD, BANCO_ICBC);
        icbc_master_1 = checkOutPage.getTotalPrice();
        paymentSection.clickChangeCardLink();

        paymentSection.selectPaymentQty("6 cuota");
        selectCardAndBank(VISA, BANCO_ICBC);
        icbc_visa_6 = checkOutPage.getTotalPrice();
        paymentSection.clickChangeCardLink();

        paymentSection.selectPaymentQty("6 cuota");
        selectCardAndBank(MASTERCARD, BANCO_ICBC);
        icbc_master_6 = checkOutPage.getTotalPrice();
        paymentSection.clickChangeCardLink();

        paymentSection.selectPaymentQty("12 cuota");
        selectCardAndBank(VISA, BANCO_ICBC);
        icbc_visa_12 = checkOutPage.getTotalPrice();
        paymentSection.clickChangeCardLink();

        paymentSection.selectPaymentQty("12 cuota");
        selectCardAndBank(MASTERCARD, BANCO_ICBC);
        icbc_master_12 = checkOutPage.getTotalPrice();
        paymentSection.clickChangeCardLink();

        logger.info("******************************************************** INICIO DE PRUEBAS ********************************************************");
        printItineraryData();
        printPriceData(VISA,"1", almundo_visa_1, icbc_visa_1);
        softAssert.assertTrue(checkComparison(difference >= -15), "VISA - 1 Cuota - Prices have big difference: [" + difference + "] - Almundo [" + almundo_visa_1 + "] - ICBC [" + icbc_visa_1 + "]");

        printPriceData(MASTERCARD,"1", almundo_master_1, icbc_master_1);
        softAssert.assertTrue(checkComparison(difference >= -15), "MASTERCARD - 1 Cuota - Prices have big difference: [" + difference + "] - Almundo [" + almundo_master_1 + "] - ICBC [" + icbc_master_1 + "]");

        printPriceData(VISA,"6", almundo_visa_6, icbc_visa_6);
        softAssert.assertTrue(checkComparison(difference >= -15), "VISA - 6 Cuotas - Prices have big difference: [" + difference + "] - Almundo [" + almundo_visa_6 + "] - ICBC [" + icbc_visa_6 + "]");

        printPriceData(MASTERCARD,"6", almundo_master_6, icbc_master_6);
        softAssert.assertTrue(checkComparison(difference >= -15), "MASTERCARD - 6 Cuotas - Prices have big difference: [" + difference + "] - Almundo [" + almundo_master_6 + "] - ICBC [" + icbc_master_6 + "]");

        printPriceData(VISA,"12", almundo_visa_12, icbc_visa_12);
        softAssert.assertTrue(checkComparison(difference >= -15), "VISA - 12 Cuotas - Prices have big difference: [" + difference + "] - Almundo [" + almundo_visa_12 + "] - ICBC [" + icbc_visa_12 + "]");

        printPriceData(MASTERCARD,"12", almundo_master_12, icbc_master_12);
        softAssert.assertTrue(checkComparison(difference >= -15), "MASTERCARD - 12 Cuotas - Prices have big difference: [" + difference + "] - Almundo [" + almundo_master_12 + "] - ICBC [" + icbc_master_12 + "]");

        logger.info("********************************************************** FIN DE PRUEBAS **********************************************************");

        if(failedTest){
            setResultSauceLabs(FAILED);}
        softAssert.assertAll();
    }

    @Test
    public void airCanada() {
        logTestTitle("ICBC Store - Air Canada - ");
        checkOutPageV3 = openAlmundoCart(cartId);

        paymentSectionGridV3 = initPaymentSectionGridV3();

        selectPaymentV3("1",BANCO_ICBC, VISA);
        almundo_visa_1 = checkOutPageV3.getTotalPrice();
        paymentSectionGridV3.clickChangeCardLink();

/*        selectPaymentV3("1",BANCO_ICBC, MASTERCARD);
        almundo_master_1 = checkOutPageV3.getTotalPrice();
        paymentSectionGridV3.clickChangeCardLink();*/

        selectPaymentV3("6",BANCO_ICBC, VISA);
        almundo_visa_6 = checkOutPageV3.getTotalPrice();
        paymentSectionGridV3.clickChangeCardLink();

        selectPaymentV3("6",BANCO_ICBC, MASTERCARD);
        almundo_master_6 = checkOutPageV3.getTotalPrice();
        paymentSectionGridV3.clickChangeCardLink();

        selectPaymentV3("12",BANCO_ICBC, VISA);
        almundo_visa_12 = checkOutPageV3.getTotalPrice();
        paymentSectionGridV3.clickChangeCardLink();

        selectPaymentV3("12",BANCO_ICBC, MASTERCARD);
        almundo_master_12 = checkOutPageV3.getTotalPrice();

        /****************** ICBC ******************/

        checkOutPage = openIcbcCart(cartIdICBC);
        paymentSection = checkOutPage.paymentSection();

        paymentSection.selectPaymentQty("1 cuota");
        selectCardAndBank(VISA, BANCO_ICBC);
        icbc_visa_1 = checkOutPage.getTotalPrice();
        paymentSection.clickChangeCardLink();

        paymentSection.selectPaymentQty("1 cuota");
        selectCardAndBank(MASTERCARD, BANCO_ICBC);
        icbc_master_1 = checkOutPage.getTotalPrice();
        paymentSection.clickChangeCardLink();

        paymentSection.selectPaymentQty("6 cuota");
        selectCardAndBank(VISA, BANCO_ICBC);
        icbc_visa_6 = checkOutPage.getTotalPrice();
        paymentSection.clickChangeCardLink();

        paymentSection.selectPaymentQty("6 cuota");
        selectCardAndBank(MASTERCARD, BANCO_ICBC);
        icbc_master_6 = checkOutPage.getTotalPrice();
        paymentSection.clickChangeCardLink();

        paymentSection.selectPaymentQty("12 cuota");
        selectCardAndBank(VISA, BANCO_ICBC);
        icbc_visa_12 = checkOutPage.getTotalPrice();
        paymentSection.clickChangeCardLink();

        paymentSection.selectPaymentQty("12 cuota");
        selectCardAndBank(MASTERCARD, BANCO_ICBC);
        icbc_master_12 = checkOutPage.getTotalPrice();
        paymentSection.clickChangeCardLink();

        logger.info("******************************************************** INICIO DE PRUEBAS ********************************************************");
        printItineraryData();
        printPriceData(VISA,"1", almundo_visa_1, icbc_visa_1);
        softAssert.assertTrue(checkComparison(difference >= -15), "VISA - 1 Cuota - Prices have big difference: [" + difference + "] - Almundo [" + almundo_visa_1 + "] - ICBC [" + icbc_visa_1 + "]");

        printPriceData(MASTERCARD,"1", almundo_master_1, icbc_master_1);
        softAssert.assertTrue(checkComparison(difference >= -15), "MASTERCARD - 1 Cuota - Prices have big difference: [" + difference + "] - Almundo [" + almundo_master_1 + "] - ICBC [" + icbc_master_1 + "]");

        printPriceData(VISA,"6", almundo_visa_6, icbc_visa_6);
        softAssert.assertTrue(checkComparison(difference >= -15), "VISA - 6 Cuotas - Prices have big difference: [" + difference + "] - Almundo [" + almundo_visa_6 + "] - ICBC [" + icbc_visa_6 + "]");

        printPriceData(MASTERCARD,"6", almundo_master_6, icbc_master_6);
        softAssert.assertTrue(checkComparison(difference >= -15), "MASTERCARD - 6 Cuotas - Prices have big difference: [" + difference + "] - Almundo [" + almundo_master_6 + "] - ICBC [" + icbc_master_6 + "]");

        printPriceData(VISA,"12", almundo_visa_12, icbc_visa_12);
        softAssert.assertTrue(checkComparison(difference >= -15), "VISA - 12 Cuotas - Prices have big difference: [" + difference + "] - Almundo [" + almundo_visa_12 + "] - ICBC [" + icbc_visa_12 + "]");

        printPriceData(MASTERCARD,"12", almundo_master_12, icbc_master_12);
        softAssert.assertTrue(checkComparison(difference >= -15), "MASTERCARD - 12 Cuotas - Prices have big difference: [" + difference + "] - Almundo [" + almundo_master_12 + "] - ICBC [" + icbc_master_12 + "]");

        logger.info("********************************************************** FIN DE PRUEBAS **********************************************************");

        if(failedTest){
            setResultSauceLabs(FAILED);}
        softAssert.assertAll();
    }

    /////////////////////////////////// ADDITIONAL METHODS ///////////////////////////////////

    private CheckOutPageV3 openAlmundoCart(String cartId){
        driver.navigate().to("https://almundo.com.ar/" + "checkout/" + cartId + "?");

        try{
            PageUtils.waitUrlContains(driver, 10, "checkout", "Checkout V3");

            logger.info("Forcing Checkout to Matrix");
            String currentUrl = driver.getCurrentUrl();

            if(currentUrl.contains("sc=1")) {
                logger.info("Replacing sc=1 with sc=0");
                String newURL = currentUrl.replace("sc=1", "sc=0");
                logger.info("Navigating to: [" + newURL + "]");
                driver.navigate().to(newURL);
            } else if(currentUrl.contains("sc=0")) {
                logger.info("Nothing to replace, matrix is displayed");
                logger.info("Navigating to: [" + PROD_URL + "checkout/" + cartId + "]");
            } else {
                logger.info("Adding sc=0");
                String newURL = currentUrl.concat("&sc=0");
                logger.info("Navigating to: [" + newURL + "]");
                driver.navigate().to(newURL);
            }
        } catch(Exception time) {
            time.printStackTrace();
        }
        return initCheckOutPageV3();
    }

    private CheckOutPage openIcbcCart(String cartId){
        logger.info("Navigating to: [" + ICBC_URL + "cart/v2/" + cartId + "]");
        driver.navigate().to(ICBC_URL + "cart/v2/" + cartId);
        PageUtils.waitElementForVisibility(driver, By.cssSelector(".description-container"), 40, "Payments rows");
        return initCheckOutPage();
    }

    private void selectPaymentV3(String payment, String bankName, String cardName) {
        paymentSectionGridV3.setPayment(payment, ".card-container-1");
        paymentSectionGridV3.setBank(bankName, ".card-container-1");
        paymentSectionGridV3.setCreditCard(cardName, ".card-container-1");
    }

    private void selectCardAndBank(String creditCard, String bankName) {
        paymentSection.selectBank(bankName);
        paymentSection.selectCreditCard(creditCard);
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

    private void printPriceData(String cardName, String paymentQty, int almundoPrice, int icbcStorePrice) {
        logger.info(cardName + " - " + paymentQty + " Cuota/s - Almundo: [" + almundoPrice + "]");
        logger.info(cardName + " - " + paymentQty + " Cuota/s - ICBC   : [" + icbcStorePrice + "]" + "\n");
        difference = almundoPrice - icbcStorePrice;
    }

    private boolean checkComparison(boolean softAssertResult){
        if(!softAssertResult && !failedTest){
            failedTest = true;
        }
        return softAssertResult;
    }
}
