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
public class RetailTrips extends TestBaseSetup {

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

    @SuppressWarnings("Duplicates")
    @Test
    public void payWithLink() {
        logTestTitle("Trips Pay With Link");
        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                                    LINK_VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                                    dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_DOM_RET);
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
    public void payWithLinkTwoCards() {
        logTestTitle("Trips Pay With Link With Two Cards");
        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(), LINK_VISA_1, MASTER_1,
                                    dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                                    dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_DOM_RET);
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
}