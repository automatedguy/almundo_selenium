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
public class Trips extends TestBaseSetup {

    private DataManagement dataManagement = new DataManagement();
    private CheckOutPageV3 checkOutPageV3 = null;
    private ConfirmationPageV3 confirmationPageV3 = null;
    private final String productURl = "?product=vuelohotel";

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
        logger.info("Getting Passenger Data.");
        dataManagement.getPassengerData("adult_male_native");
        dataManagement.getPassengerData("adult_female_native");
        dataManagement.getPassengerData("child_male_native");
    }
    
    /************************ Grid Test Area ************************/

    @Test
    public void gridWithTodoPago() {
        logTestTitle("Trips – Grid With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "TripsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithTodoPago() {
        logTestTitle("Trips – Grid (explicit) With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "TripsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithTodoPagoExplicit() {
        logTestTitle("Trips – Grid (explicit) With Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=0&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "TripsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithoutTodoPagoExplicit() {
        logTestTitle("Trips – Grid Without Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "TripsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithDebitExplicitWithTodoPago() {
        logTestTitle("Trips – Grid With Debit (explicit)  With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&svd=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "TripsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithDebitExplicitWithTodoPago() {
        logTestTitle("Trips – Grid (explicit)  With Debit (explicit)  With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=0&svd=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "TripsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithDebitExplicitWithTodoPagoExplicit() {
        logTestTitle("Trips – Grid (explicit)  With Debit (explicit)  With Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=0&svd=1&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "TripsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithDebitExplicitWithoutTodoPago() {
        logTestTitle("Trips – Grid With Debit (explicit) Without Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&svd=1&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "TripsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void  gridWithoutDebitExplicitWithTodoPago() {
        logTestTitle("Trips – Grid Without Debit (explicit) Without Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&svd=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "TripsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithoutDebitExplicitWithoutTodoPagoExplicit() {
        logTestTitle("Trips – Grid Without Debit (explicit) Without Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&svd=0&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "TripsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    /************************ Combo Test Area ************************/

    @Test
    public void comboWithTodoPago() {
        logTestTitle("Trips – Combo With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "TripsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithTodoPagoExplicit() {
        logTestTitle("Trips – Combo With Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "TripsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutTodoPago() {
        logTestTitle("Trips – Combo Without Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "TripsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithDebitExplicitWithTodoPago() {
        logTestTitle("Trips – Combo With Debit (explicit) With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "TripsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithDebitExplicitWithTodoPagoExplicit() {
        logTestTitle("Trips – Combo With Debit (explicit) With Todo Pago  (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=1&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "TripsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithDebitExplicitWithoutTodoPagoExplicit() {
        logTestTitle("Trips – Combo With Debit (explicit) Without Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=1&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "TripsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutDebitExplicitWithTodoPago() {
        logTestTitle("Trips – Combo Without Debit (explicit) With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "TripsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutDebitExplicitWithTodoPagoExplicit() {
        logTestTitle("Trips – Combo Without Debit (explicit) With Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=0&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "TripsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutDebitExplicitWithoutTodoPagoExplicit() {
        logTestTitle("Trips – Combo Without Debit (explicit) Without Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=0&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "TripsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }
}