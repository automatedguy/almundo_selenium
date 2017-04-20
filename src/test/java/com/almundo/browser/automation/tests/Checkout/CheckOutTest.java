package com.almundo.browser.automation.tests.Checkout;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPage.ConfirmationPage;
import org.json.simple.JSONArray;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.STAGING_URL;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by gabrielcespedes on 02/03/17.
 */
public class CheckOutTest extends TestBaseSetup {

    private DataManagement dataManagement = new DataManagement();
    private CheckOutPage checkOutPage = null;
    private ConfirmationPage confirmationPage = null;

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
        driver.navigate().to(STAGING_URL + "cart/v2/" + cartId);
        return initCheckOutPage();
    }

    @Test
    public void oneWay_Int_1_ADULT() {
        logTestTitle("Flight - OneWay, MIA, NYC, 1 Adult " + countryPar );
        checkOutPage = openCart(cartId);

        dataManagement.getPassengerData("adult_male_native");

        checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                dataManagement.getPaymentData("1_visa_visa"),
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_phone"),
                "FlightsCheckOutPageInternational");
                confirmationPage = checkOutPage.clickComprarBtn();
                assertTrue(confirmationPage.confirmationOk());
    }
}