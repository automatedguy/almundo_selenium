package com.almundo.browser.automation.tests.Checkout;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import org.json.simple.JSONArray;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.STAGING_URL;

/**
 * Created by gabrielcespedes on 02/03/17.
 */
public class CheckOutTest extends TestBaseSetup {

    private DataManagement dataManagement = new DataManagement();
    private CheckOutPage checkOutPage = null;

    @BeforeClass
    private void initDataLists() {
        dataManagement.getFlightsDataTripList();
        dataManagement.getPassengersList();
        dataManagement.getPaymentList();
        dataManagement.getBillingList();
        dataManagement.getContactList();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }


    private CheckOutPage openCart(String cartId){
        driver.get(STAGING_URL + "/cart/v2/" + cartId);
        return initCheckOutPage();
    }

    @Test
    public void oneWay_Dom_Booking_Flow() {
        logTestTitle("Flight - One Way - Dom - 1 Adults " + countryPar );

        checkOutPage = openCart("58b8424ae4b051bff7810a65");

        dataManagement.getPassengerData("adult_male_native");

        checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                dataManagement.getPaymentData("1_amex_amex"),
                dataManagement.getBillingData("local_Billing_v2"),
                dataManagement.getContactData("contact_phone"),
                "FlightsCheckOutPageDomestic");
    }

}
