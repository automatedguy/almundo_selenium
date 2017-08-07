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
public class Flights extends TestBaseSetup {

    private DataManagement dataManagement = new DataManagement();
    private CheckOutPageV3 checkOutPageV3 = null;
    private ConfirmationPageV3 confirmationPageV3 = null;
    private final String productURl = "?product=flights&origin=flights";

    @BeforeClass
    private void initDataLists() {
        retriesCount = true;
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
        logTestTitle("Flights – Grid With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "FlightsCheckOutPageInternational");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithTodoPago() {
        logTestTitle("Flights – Grid (explicit) With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "FlightsCheckOutPageInternational");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithTodoPagoExplicit() {
        logTestTitle("Flights – Grid (explicit) With Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=0&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "FlightsCheckOutPageInternational");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithoutTodoPagoExplicit() {
        logTestTitle("Flights – Grid Without Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "FlightsCheckOutPageInternational");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithDebitExplicitWithTodoPago() {
        logTestTitle("Flights – Grid With Debit (explicit)  With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&svd=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "FlightsCheckOutPageInternational");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithDebitExplicitWithTodoPago() {
        logTestTitle("Flights – Grid (explicit)  With Debit (explicit)  With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=0&svd=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "FlightsCheckOutPageInternational");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithDebitExplicitWithTodoPagoExplicit() {
        logTestTitle("Flights – Grid (explicit)  With Debit (explicit)  With Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=0&svd=1&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "FlightsCheckOutPageInternational");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithDebitExplicitWithoutTodoPago() {
        logTestTitle("Flights – Grid With Debit (explicit) Without Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&svd=1&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "FlightsCheckOutPageInternational");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void  gridWithoutDebitExplicitWithTodoPago() {
        logTestTitle("Flights – Grid Without Debit (explicit) Without Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&svd=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "FlightsCheckOutPageInternational");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithoutDebitExplicitWithoutTodoPagoExplicit() {
        logTestTitle("Flights – Grid Without Debit (explicit) Without Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&svd=0&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "FlightsCheckOutPageInternational");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    /************************ Combo Test Area ************************/

    @Test
    public void comboWithTodoPago() {
        logTestTitle("Flights – Combo With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "FlightsCheckOutPageInternational");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithTodoPagoExplicit() {
        logTestTitle("Flights – Combo With Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "FlightsCheckOutPageInternational");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutTodoPago() {
        logTestTitle("Flights – Combo Without Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "FlightsCheckOutPageInternational");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithDebitExplicitWithTodoPago() {
        logTestTitle("Flights – Combo With Debit (explicit) With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "FlightsCheckOutPageInternational");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithDebitExplicitWithTodoPagoExplicit() {
        logTestTitle("Flights – Combo With Debit (explicit) With Todo Pago  (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=1&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "FlightsCheckOutPageInternational");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithDebitExplicitWithoutTodoPagoExplicit() {
        logTestTitle("Flights – Combo With Debit (explicit) Without Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=1&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "FlightsCheckOutPageInternational");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutDebitExplicitWithTodoPago() {
        logTestTitle("Flights – Combo Without Debit (explicit) With Todo Pago " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "FlightsCheckOutPageInternational");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutDebitExplicitWithTodoPagoExplicit() {
        logTestTitle("Flights – Combo Without Debit (explicit) With Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=0&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "FlightsCheckOutPageInternational");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutDebitExplicitWithoutTodoPagoExplicit() {
        logTestTitle("Flights – Combo Without Debit (explicit) Without Todo Pago (explicit) " + countryPar );
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=0&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "FlightsCheckOutPageInternational");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }
}