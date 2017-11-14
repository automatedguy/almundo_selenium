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
public class Trips extends TestBaseSetup {

    private DataManagement dataManagement = new DataManagement();
    private CheckOutPageV3 checkOutPageV3 = null;
    private ThanksPageV3 thanksPageV3 = null;
    private final String productURl = "?product=vuelohotel";

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

    private void getPassengersData(){
        logger.info("Getting Passenger Data.");
        dataManagement.setPassengerData("adult_male_native");
        dataManagement.setPassengerData("adult_female_native");
        dataManagement.setPassengerData("child_male_native");
    }

    private void getTripsAssertionInfo(){
        thanksPageAssertInfo.setFinalAmountPaid(checkOutPageV3.breakDownSectionV3().getFinalPriceString());
        thanksPageAssertInfo.setContactEmailEntered(checkOutPageV3.contactSection().getContactEmail());
    }
    
    /************************ Grid Test Area ************************/

    @Test
    public void gridWithTodoPago() {
        logTestTitle("Grid with one credit card");
        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void twoCards() {
        logTestTitle("Grid with two credit cards.");
        checkOutPageV3 = openCart(cartId, "&stc=1",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void IntBookingWithTransfer(){
        logTestTitle("International - 10 days - 2 Adults/2 Childs - 1 Room");
        addTransfer = true;

        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(), VISA_1,
                                    dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                                    dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);
        getTripsAssertionInfo();

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void IntBookingWithTransferWithTwoCards(){
        logTestTitle("International - 10 days - 2 Adults/2 Childs - 1 Room");
        addTransfer = true;

        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(), VISA_1, MASTER_1,
                                    dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                                    dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);
        getTripsAssertionInfo();

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithTodoPago() {
        logTestTitle("Trips – Grid (explicit) With Todo Pago");
        checkOutPageV3 = openCart(cartId, "&sc=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithTodoPagoExplicit() {
        logTestTitle("Trips – Grid (explicit) With Todo Pago (explicit)");
        checkOutPageV3 = openCart(cartId, "&sc=0&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithoutTodoPagoExplicit() {
        logTestTitle("Trips – Grid Without Todo Pago (explicit)");
        checkOutPageV3 = openCart(cartId, "&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithDebitExplicitWithTodoPago() {
        logTestTitle("Trips – Grid With Debit (explicit)  With Todo Pago");
        checkOutPageV3 = openCart(cartId, "&svd=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithDebitExplicitWithTodoPago() {
        logTestTitle("Trips – Grid (explicit)  With Debit (explicit)  With Todo Pago");
        checkOutPageV3 = openCart(cartId, "&sc=0&svd=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithDebitExplicitWithTodoPagoExplicit() {
        logTestTitle("Trips – Grid (explicit)  With Debit (explicit)  With Todo Pago (explicit)");
        checkOutPageV3 = openCart(cartId, "&sc=0&svd=1&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithDebitExplicitWithoutTodoPago() {
        logTestTitle("Trips – Grid With Debit (explicit) Without Todo Pago");
        checkOutPageV3 = openCart(cartId, "&svd=1&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void  gridWithoutDebitExplicitWithTodoPago() {
        logTestTitle("Trips – Grid Without Debit (explicit) Without Todo Pago");
        checkOutPageV3 = openCart(cartId, "&svd=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithoutDebitExplicitWithoutTodoPagoExplicit() {
        logTestTitle("Trips – Grid Without Debit (explicit) Without Todo Pago (explicit)");
        checkOutPageV3 = openCart(cartId, "&svd=0&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    /************************ Combo Test Area ************************/

    @Test
    public void comboWithTodoPago() {
        logTestTitle("Trips – Combo With Todo Pago");
        checkOutPageV3 = openCart(cartId, "&sc=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithTodoPagoExplicit() {
        logTestTitle("Trips – Combo With Todo Pago (explicit)");
        checkOutPageV3 = openCart(cartId, "&sc=1&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutTodoPago() {
        logTestTitle("Trips – Combo Without Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithDebitExplicitWithTodoPago() {
        logTestTitle("Trips – Combo With Debit (explicit) With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithDebitExplicitWithTodoPagoExplicit() {
        logTestTitle("Trips – Combo With Debit (explicit) With Todo Pago  (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=1&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithDebitExplicitWithoutTodoPagoExplicit() {
        logTestTitle("Trips – Combo With Debit (explicit) Without Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=1&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutDebitExplicitWithTodoPago() {
        logTestTitle("Trips – Combo Without Debit (explicit) With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutDebitExplicitWithTodoPagoExplicit() {
        logTestTitle("Trips – Combo Without Debit (explicit) With Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=0&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutDebitExplicitWithoutTodoPagoExplicit() {
        logTestTitle("Trips – Combo Without Debit (explicit) Without Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=0&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }
}