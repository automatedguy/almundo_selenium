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
public class RetailHotelsTests extends TestBaseSetup {

    private DataManagement dataManagement = new DataManagement();
    private CheckOutPageV3 checkOutPageV3 = null;
    private ThanksPageV3 thanksPageV3 = null;
    private final String productURl = "?product=hotel";

    @BeforeClass
    private void initDataLists() {
        retriesCount = true;
        dataManagement.setPassengersList();
        dataManagement.setPaymentList();
        dataManagement.setBillingList();
        dataManagement.setContactList();
    }

    private void getAssertionInfo(){
        thanksPageAssertInfo.setFinalAmountPaid(checkOutPageV3.breakDownSectionV3().getFinalPriceString());
        thanksPageAssertInfo.setHotelsDetailInfo(checkOutPageV3.breakDownSectionV3().getHotelDetailContent());
        thanksPageAssertInfo.setContactEmailEntered(checkOutPageV3.contactSection().getContactEmail());
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.clearPassengerJsonList();
    }

    private void getPassengersData(){
        dataManagement.setPassengerData("adult_female_native");
        dataManagement.setPassengerData("adult_female_native");
    }

    /************************ Test Area ************************/

    @SuppressWarnings("Duplicates")
    @Test
    public void OneCard(){
        logTestTitle("One Credit Card");

        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(), VISA_1,
                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void TwoCards(){
        logTestTitle("Two Credit Cards");

        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1 + "$" + MASTER_1,
                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void CreditCash(){
        logTestTitle("Credit and Cash");

        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1 + "$" + EFECTIVO,
                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void Cash(){
        logTestTitle("Cash");

        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                EFECTIVO,
                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void Transfer(){
        logTestTitle("Transfer");

        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                TRANSFERENCIA,
                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void Deposit(){
        logTestTitle("Deposit");

        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                DEPOSITO,
                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void payWithLink(){
        logTestTitle("Pay With Link");

        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                LINK_VISA_1,
                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
    }
}