package com.almundo.browser.automation.tests.Rewards;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.almundo.browser.automation.utils.Constants.ICBC_URL;
import static com.almundo.browser.automation.utils.Constants.PROD_URL;

/**
 * Created by leandro.efron on 10/3/2017.
 */
public class ICBCPriceComparison extends TestBaseSetup {

    private CheckOutPage checkOutPage = null;

    private WebElement paymentQtySelected = null;
    private WebElement cardSelected = null;

    SoftAssert softAssert = new SoftAssert();

    String almundo_visa_1 = null;
    String almundo_visa_12 = null;
    String almundo_visa_18 = null;

    String icbc_visa_1 = null;
    String icbc_visa_12 = null;
    String icbc_visa_18 = null;

    String almundo_master_1 = null;
    String almundo_master_12 = null;
    String almundo_master_18 = null;

    String icbc_master_1 = null;
    String icbc_master_12 = null;
    String icbc_master_18 = null;

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void bue_mia_latam() {

        checkOutPage = openAlmundoCart("58c6a3f5e4b0397369fe504c");

        paymentQtySelected = checkOutPage.paymentSection().selectPaymentQty("1 cuota");
        cardSelected = checkOutPage.paymentSection().selectCreditCard(paymentQtySelected, "VI");
        checkOutPage.paymentSection().selectBank(cardSelected, "Visa");
        almundo_visa_1 = checkOutPage.getTotalPrice();

        cardSelected = checkOutPage.paymentSection().selectCreditCard(paymentQtySelected, "CA");
        checkOutPage.paymentSection().selectBank(cardSelected, "Mastercard");
        almundo_master_1 = checkOutPage.getTotalPrice();

        paymentQtySelected = checkOutPage.paymentSection().selectPaymentQty("12 cuotas");
        cardSelected = checkOutPage.paymentSection().selectCreditCard(paymentQtySelected, "VI");
        checkOutPage.paymentSection().selectBank(cardSelected, "ICBC");
        almundo_visa_12 = checkOutPage.getTotalPrice();

        cardSelected = checkOutPage.paymentSection().selectCreditCard(paymentQtySelected, "CA");
        checkOutPage.paymentSection().selectBank(cardSelected, "ICBC");
        almundo_master_12 = checkOutPage.getTotalPrice();

        checkOutPage = openIcbcCart("58c6a42de4b0fc66a49484ec");

        paymentQtySelected = checkOutPage.paymentSection().selectPaymentQty("1 cuota");
        cardSelected = checkOutPage.paymentSection().selectCreditCard(paymentQtySelected, "VI");
        checkOutPage.paymentSection().selectBank(cardSelected, "ICBC");
        icbc_visa_1 = checkOutPage.getTotalPrice();

        cardSelected = checkOutPage.paymentSection().selectCreditCard(paymentQtySelected, "CA");
        checkOutPage.paymentSection().selectBank(cardSelected, "ICBC");
        icbc_master_1 = checkOutPage.getTotalPrice();

        paymentQtySelected = checkOutPage.paymentSection().selectPaymentQty("12 cuotas");
        cardSelected = checkOutPage.paymentSection().selectCreditCard(paymentQtySelected, "VI");
        checkOutPage.paymentSection().selectBank(cardSelected, "ICBC");
        icbc_visa_12 = checkOutPage.getTotalPrice();

        cardSelected = checkOutPage.paymentSection().selectCreditCard(paymentQtySelected, "CA");
        checkOutPage.paymentSection().selectBank(cardSelected, "ICBC");
        icbc_master_12 = checkOutPage.getTotalPrice();

        logger.info("------------------------------------------------");
        logger.info("LATAM - BUE-MIA");
        logger.info("------------------------------------------------");
        logger.info("VISA - 1 Cuota - Almundo: " + almundo_visa_1);
        logger.info("VISA - 1 Cuota - ICBC   : " + icbc_visa_1 + "\n");
        softAssert.assertTrue(almundo_visa_1.equals(icbc_visa_1), "VISA - 1 Cuota - Prices are not equal: [" + almundo_visa_1 + "] - [" + icbc_visa_1 + "]");

        logger.info("MASTERCARD - 1 Cuota - Almundo: " + almundo_master_1);
        logger.info("MASTERCARD - 1 Cuota - ICBC   : " + icbc_master_1 + "\n");
        softAssert.assertTrue(almundo_master_1.equals(icbc_master_1), "MASTERCARD - 1 Cuota - Prices are not equal: [" + almundo_master_1 + "] - [" + icbc_master_1 + "]");

        logger.info("VISA - 12 Cuotas - Almundo: " + almundo_visa_12);
        logger.info("VISA - 12 Cuotas - ICBC   : " + icbc_visa_12 + "\n");
        softAssert.assertTrue(almundo_visa_12.equals(icbc_visa_12), "VISA - 12 Cuotas - Prices are not equal: [" + almundo_visa_12 + "] - [" + icbc_visa_12 + "]");

        logger.info("MASTERCARD - 12 Cuotas - Almundo: " + almundo_master_12);
        logger.info("MASTERCARD - 12 Cuotas - ICBC   : " + icbc_master_12 + "\n");
        softAssert.assertTrue(almundo_master_12.equals(icbc_master_12), "MASTERCARD - 12 Cuotas - Prices are not equal: [" + almundo_master_12 + "] - [" + icbc_master_12 + "]");

        softAssert.assertAll();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    private CheckOutPage openAlmundoCart(String cartId){
        driver.navigate().to(PROD_URL + "cart/v2/" + cartId);
        return initCheckOutPage();
    }

    private CheckOutPage openIcbcCart(String cartId){
        driver.navigate().to(ICBC_URL + "cart/v2/" + cartId);
        return initCheckOutPage();
    }
}
