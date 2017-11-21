package com.almundo.browser.automation.tests.Checkout;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ThanksPageV3;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.*;
import static com.almundo.browser.automation.utils.Constants.Results.PASSED;

/**
 * Created by gabrielcespedes on 02/03/17.
 */
public class Hotels extends TestBaseSetup {

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

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.clearPassengerJsonList();
    }

    private void getPassengersData(){
        dataManagement.setPassengerData("adult_female_native");
        dataManagement.setPassengerData("adult_female_native");
    }

    private void getAssertionInfo(){
        thanksPageAssertInfo.setFinalAmountPaid(checkOutPageV3.breakDownSectionV3().getFinalPriceString());
        thanksPageAssertInfo.setHotelsDetailInfo(checkOutPageV3.breakDownSectionV3().getHotelDetailContent());
        thanksPageAssertInfo.setContactEmailEntered(checkOutPageV3.contactSection().getContactEmail());
    }

    /************************ Grid Test Area ************************/

    @Test
    public void clubAlmundo() {
        logTestTitle("Hotels – Club Almundo");

        checkOutPageV3 = openCart(cartId, "",productURl);

        dataManagement.setUsersDataList();
        JSONObject userData = dataManagement.setUserData("email");
        getPassengersData();

        LoginPopUp loginPopUp  = checkOutPageV3.clickIngresarBtn();
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());
        loginPopUp.ingresarBtn.click();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        // Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithTodoPago() {
        logTestTitle("Hotels – Grid With Todo Pago ");
        checkOutPageV3 = openCart(cartId, "",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void twoCards() {
        logTestTitle("Hotels – Grid With Todo Pago ");
        checkOutPageV3 = openCart(cartId, "&stc=1",productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, MASTER_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void CheckoutWizard() {
        logTestTitle("Checkout Wizard");
        checkOutPageV3 = openCart(cartId, "&stn=two_steps",productURl);

        getPassengersData();

        checkOutPageV3.setCheckoutWizardInfo(dataManagement.getPassengerJsonList(),
                MASTER_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarStepsBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void CheckoutWizardTwoCards() {
        logTestTitle("Checkout Wizard");
        checkOutPageV3 = openCart(cartId, "&stn=two_steps",productURl);

        getPassengersData();

        checkOutPageV3.setCheckoutWizardInfo(dataManagement.getPassengerJsonList(),
                VISA_1, MASTER_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarStepsBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithTodoPago() {
        logTestTitle("Hotels – Grid (explicit) With Todo Pago");
        checkOutPageV3 = openCart(cartId, "&sc=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithTodoPagoExplicit() {
        logTestTitle("Hotels – Grid (explicit) With Todo Pago (explicit)");
        checkOutPageV3 = openCart(cartId, "&sc=0&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithoutTodoPagoExplicit() {
        logTestTitle("Hotels – Grid Without Todo Pago (explicit) ");
        checkOutPageV3 = openCart(cartId, "&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithDebitExplicitWithTodoPago() {
        logTestTitle("Hotels – Grid With Debit (explicit)  With Todo Pago ");
        checkOutPageV3 = openCart(cartId, "&svd=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithDebitExplicitWithTodoPago() {
        logTestTitle("Hotels – Grid (explicit)  With Debit (explicit)  With Todo Pago ");
        checkOutPageV3 = openCart(cartId, "&sc=0&svd=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridExplicitWithDebitExplicitWithTodoPagoExplicit() {
        logTestTitle("Hotels – Grid (explicit)  With Debit (explicit)  With Todo Pago (explicit) ");
        checkOutPageV3 = openCart(cartId, "&sc=0&svd=1&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithDebitExplicitWithoutTodoPago() {
        logTestTitle("Hotels – Grid With Debit (explicit) Without Todo Pago ");
        checkOutPageV3 = openCart(cartId, "&svd=1&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void  gridWithoutDebitExplicitWithTodoPago() {
        logTestTitle("Hotels – Grid Without Debit (explicit) Without Todo Pago");
        checkOutPageV3 = openCart(cartId, "&svd=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void gridWithoutDebitExplicitWithoutTodoPagoExplicit() {
        logTestTitle("Hotels – Grid Without Debit (explicit) Without Todo Pago (explicit)");
        checkOutPageV3 = openCart(cartId, "&svd=0&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    /************************ Combo Test Area ************************/

    @Test
    public void comboWithTodoPago() {
        logTestTitle("Hotels – Combo With Todo Pago");
        checkOutPageV3 = openCart(cartId, "&sc=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithTwoCards() {
        logTestTitle("Hotels – Combo With Todo Pago TwoCards");
        checkOutPageV3 = openCart(cartId, "&sc=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithTodoPagoExplicit() {
        logTestTitle("Hotels – Combo With Todo Pago (explicit)");
        checkOutPageV3 = openCart(cartId, "&sc=1&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutTodoPago() {
        logTestTitle("Hotels – Combo Without Todo Pago");
        checkOutPageV3 = openCart(cartId, "&sc=1&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithDebitExplicitWithTodoPago() {
        logTestTitle("Hotels – Combo With Debit (explicit) With Todo Pago");
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithDebitExplicitWithTodoPagoExplicit() {
        logTestTitle("Hotels – Combo With Debit (explicit) With Todo Pago  (explicit)");
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=1&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithDebitExplicitWithoutTodoPagoExplicit() {
        logTestTitle("Hotels – Combo With Debit (explicit) Without Todo Pago (explicit)");
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=1&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutDebitExplicitWithTodoPago() {
        logTestTitle("Hotels – Combo Without Debit (explicit) With Todo Pago");
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutDebitExplicitWithTodoPagoExplicit() {
        logTestTitle("Hotels – Combo Without Debit (explicit) With Todo Pago (explicit)");
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=0&stp=1", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void comboWithoutDebitExplicitWithoutTodoPagoExplicit() {
        logTestTitle("Hotels – Combo Without Debit (explicit) Without Todo Pago (explicit)");
        checkOutPageV3 = openCart(cartId, "&sc=1&svd=0&stp=0", productURl);

        getPassengersData();

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.getHotelsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }
}