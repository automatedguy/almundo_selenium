package com.almundo.browser.automation.tests.Checkout;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ConfirmationPageV3;
import org.json.simple.JSONArray;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.Results.PASSED;

/**
 * Created by gabrielcespedes on 02/03/17.
 */
public class Hotels extends TestBaseSetup {

    private DataManagement dataManagement = new DataManagement();
    private CheckOutPageV3 checkOutPageV3 = null;
    private ConfirmationPageV3 confirmationPageV3 = null;
    private final String productURl = "?product=hotel";

    @BeforeClass
    private void initDataLists() {
        dataManagement.getPassengersList();
        dataManagement.getPaymentList();
        dataManagement.getBillingList();
        dataManagement.getContactList();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    private void getPassengersData(){
        dataManagement.getPassengerData("adult_female_native");
        dataManagement.getPassengerData("adult_female_native");
    }

    /************************ Grid Test Area ************************/

    @Test
    public void gridWithTodoPago() {
        logTestTitle("Hotels – Grid With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "HotelsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithTodoPago() {
        logTestTitle("Hotels – Grid (explicit) With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "HotelsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithTodoPagoExplicit() {
        logTestTitle("Hotels – Grid (explicit) With Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=0&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "HotelsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithoutTodoPagoExplicit() {
        logTestTitle("Hotels – Grid Without Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "HotelsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithDebitExplicitWithTodoPago() {
        logTestTitle("Hotels – Grid With Debit (explicit)  With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&svd=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "HotelsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithDebitExplicitWithTodoPago() {
        logTestTitle("Hotels – Grid (explicit)  With Debit (explicit)  With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=0&svd=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "HotelsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithDebitExplicitWithTodoPagoExplicit() {
        logTestTitle("Hotels – Grid (explicit)  With Debit (explicit)  With Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=0&svd=1&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "HotelsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithDebitExplicitWithoutTodoPago() {
        logTestTitle("Hotels – Grid With Debit (explicit) Without Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&svd=1&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "HotelsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void  gridWithoutDebitExplicitWithTodoPago() {
        logTestTitle("Hotels – Grid Without Debit (explicit) Without Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&svd=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "HotelsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithoutDebitExplicitWithoutTodoPagoExplicit() {
        logTestTitle("Hotels – Grid Without Debit (explicit) Without Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&svd=0&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "HotelsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    /************************ Combo Test Area ************************/

    @Test
    public void comboWithTodoPago() {
        logTestTitle("Hotels – Combo With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "HotelsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithTodoPagoExplicit() {
        logTestTitle("Hotels – Combo With Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "HotelsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutTodoPago() {
        logTestTitle("Hotels – Combo Without Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "HotelsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithDebitExplicitWithTodoPago() {
        logTestTitle("Hotels – Combo With Debit (explicit) With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "HotelsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithDebitExplicitWithTodoPagoExplicit() {
        logTestTitle("Hotels – Combo With Debit (explicit) With Todo Pago  (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=1&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "HotelsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithDebitExplicitWithoutTodoPagoExplicit() {
        logTestTitle("Hotels – Combo With Debit (explicit) Without Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=1&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "HotelsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutDebitExplicitWithTodoPago() {
        logTestTitle("Hotels – Combo Without Debit (explicit) With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "HotelsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutDebitExplicitWithTodoPagoExplicit() {
        logTestTitle("Hotels – Combo Without Debit (explicit) With Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=0&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "HotelsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutDebitExplicitWithoutTodoPagoExplicit() {
        logTestTitle("Hotels – Combo Without Debit (explicit) Without Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=0&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "HotelsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }
}