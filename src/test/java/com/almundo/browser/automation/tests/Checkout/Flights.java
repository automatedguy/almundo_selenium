package com.almundo.browser.automation.tests.Checkout;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ThanksPageV3;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.*;
import static com.almundo.browser.automation.utils.Constants.Results.PASSED;

/**
 * Created by gabrielcespedes on 02/03/17.
 */
public class Flights extends TestBaseSetup {

    private DataManagement dataManagement = new DataManagement();
    private CheckOutPageV3 checkOutPageV3 = null;
    private ThanksPageV3 thanksPageV3 = null;
    private final String productURl = "?product=flights&origin=flights";

    @BeforeClass
    private void initDataLists() {
        retriesCount = true;
        dataManagement.setPassengersList();
        dataManagement.setPaymentList();
        dataManagement.setBillingList();
        dataManagement.setContactList();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.clearPassengerJsonList();
    }

    private void getFlightsAssertionInfo(){
        thanksPageAssertInfo.setFinalAmountPaid(checkOutPageV3.breakDownSectionV3().getFinalPriceString());
        thanksPageAssertInfo.setFlightsDetailInfo(checkOutPageV3.breakDownSectionV3().getFlightDetailContent());
        thanksPageAssertInfo.setContactEmailEntered(checkOutPageV3.contactSection().getContactEmail());
    }

    private void getPassengersData(){
        logger.info("Getting Passenger Data.");
        dataManagement.setPassengerData("adult_male_native");
        dataManagement.setPassengerData("adult_female_native");
        dataManagement.setPassengerData("child_male_native");
    }
    
    /************************ Grid Test Area ************************/

    @SuppressWarnings("Duplicates")
    @Test
    public void gridWithTodoPago() {
        logTestTitle("Grid With Todo Pago" + countryPar );
        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE),
                FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void twoCards() {
        logTestTitle("Payment with two cards");
        checkOutPageV3 = openCart(cartId, "&stc=1",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(), TWOCARDS_VISA_MASTER,
                                dataManagement.getBillingData(LOCAL_BILLING),
                                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void twoCardsAddingInsurance() {
        logTestTitle("Payment with two cards adding insurance");
        addInsurance = true;
        checkOutPageV3 = openCart(cartId, "&stc=1",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(), TWOCARDS_VISA_MASTER,
                dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_DOM);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithTodoPago() {
        logTestTitle("Grid (explicit) With Todo Pago");
        checkOutPageV3 = openCart(cartId, "&sc=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE),
                FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithTodoPagoExplicit() {
        logTestTitle("Grid (explicit) With Todo Pago (explicit)");
        checkOutPageV3 = openCart(cartId, "&sc=0&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE),
                FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithoutTodoPagoExplicit() {
        logTestTitle("Grid Without Todo Pago (explicit)");
        checkOutPageV3 = openCart(cartId, "&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE),
                FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithDebitExplicitWithTodoPago() {
        logTestTitle("Grid With Debit (explicit)  With Todo Pago");
        checkOutPageV3 = openCart(cartId, "&svd=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE),
                FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithDebitExplicitWithTodoPago() {
        logTestTitle("Grid (explicit)  With Debit (explicit)  With Todo Pago");
        checkOutPageV3 = openCart(cartId, "&sc=0&svd=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE),
                FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithDebitExplicitWithTodoPagoExplicit() {
        logTestTitle("Grid (explicit)  With Debit (explicit)  With Todo Pago (explicit)");
        checkOutPageV3 = openCart(cartId, "&sc=0&svd=1&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE),
                FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithDebitExplicitWithoutTodoPago() {
        logTestTitle("Grid With Debit (explicit) Without Todo Pago");
        checkOutPageV3 = openCart(cartId, "&svd=1&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE),
                FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void  gridWithoutDebitExplicitWithTodoPago() {
        logTestTitle("Grid Without Debit (explicit) Without Todo Pago");
        checkOutPageV3 = openCart(cartId, "&svd=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE),
                FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithoutDebitExplicitWithoutTodoPagoExplicit() {
        logTestTitle("Grid Without Debit (explicit) Without Todo Pago (explicit)");
        checkOutPageV3 = openCart(cartId, "&svd=0&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE),
                FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    /************************ Combo Test Area ************************/

    @Test
    public void comboWithTodoPago() {
        logTestTitle("Combo With Todo Pago");
        checkOutPageV3 = openCart(cartId, "&sc=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE),
                FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithTodoPagoExplicit() {
        logTestTitle("Combo With Todo Pago (explicit) ");
        checkOutPageV3 = openCart(cartId, "&sc=1&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE),
                FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutTodoPago() {
        logTestTitle("Combo Without Todo Pago");
        checkOutPageV3 = openCart(cartId, "&sc=1&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE),
                FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithDebitExplicitWithTodoPago() {
        logTestTitle("Combo With Debit (explicit) With Todo Pago");
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE),
                FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithDebitExplicitWithTodoPagoExplicit() {
        logTestTitle("Combo With Debit (explicit) With Todo Pago  (explicit)");
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=1&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE),
                FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithDebitExplicitWithoutTodoPagoExplicit() {
        logTestTitle("Combo With Debit (explicit) Without Todo Pago (explicit)");
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=1&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE),
                FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutDebitExplicitWithTodoPago() {
        logTestTitle("Combo Without Debit (explicit) With Todo Pago");
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE),
                FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutDebitExplicitWithTodoPagoExplicit() {
        logTestTitle("Combo Without Debit (explicit) With Todo Pago (explicit)");
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=0&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE),
                FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutDebitExplicitWithoutTodoPagoExplicit() {
        logTestTitle("Combo Without Debit (explicit) Without Todo Pago (explicit)");
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=0&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                                    VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                                    dataManagement.getContactData(CONTACT_CELL_PHONE),
                                    FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }
}