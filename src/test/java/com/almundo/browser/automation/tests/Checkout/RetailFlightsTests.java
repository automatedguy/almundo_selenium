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
public class RetailFlightsTests extends TestBaseSetup {

    private DataManagement dataManagement = new DataManagement();
    private CheckOutPageV3 checkOutPageV3 = null;
    private ThanksPageV3 thanksPageV3 = null;
    private final String productURl = "?product=flights&origin=flights";

    @SuppressWarnings("Duplicates")
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
    public void DebitCard(){
        logTestTitle("Debit Card");

        addInsurance =  true;

        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                DEBITO,
                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

        getFlightsAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void CreditCardModal(){
        logTestTitle("Credit Card Modal");

        addInsurance =  true;

        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(), VISA_COMAFI,
                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

        getFlightsAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void PayWithLink(){
        logTestTitle("PayWithLink With One Credit Card");

        addInsurance =  false;

        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(), LINK_VISA_1,
                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

        getFlightsAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void OneCard(){
        logTestTitle("One Credit Card");

        addInsurance =  false;

        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(), VISA_1,
                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

        getFlightsAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void TwoCards(){
        logTestTitle("Two Credit Cards");

        addInsurance =  true;

        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1 + "$" + MASTER_1,
                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

        getFlightsAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void ThreeCards(){
        logTestTitle("Three Credit Cards");

        addInsurance =  true;

        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1 + "$" + MASTER_1 + "$" + AMEX_1,
                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

        getFlightsAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void CreditCash(){
        logTestTitle("Two Credit Cards");

        addInsurance =  true;

        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1 + "$" + EFECTIVO,
                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

        getFlightsAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void Cash(){
        logTestTitle("Efectivo");

        addInsurance =  true;

        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                EFECTIVO,
                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

        getFlightsAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void Transfer(){
        logTestTitle("Transferencia");

        addInsurance =  true;

        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                TRANSFERENCIA,
                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

        getFlightsAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void Deposit(){
        logTestTitle("Transferencia");

        addInsurance =  true;

        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                DEPOSITO,
                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

        getFlightsAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void payWithLink(){
        logTestTitle("Pay with link");

        addInsurance =  true;

        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                LINK_VISA_1,
                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

        getFlightsAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
    }
}