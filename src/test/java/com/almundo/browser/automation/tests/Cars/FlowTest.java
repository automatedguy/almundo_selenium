package com.almundo.browser.automation.tests.Cars;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.CarsDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPage.ConfirmationPage;
import com.almundo.browser.automation.pages.ResultsPage.CarsResultsPage;
import org.json.simple.JSONArray;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 20/12/16.
 */
public class FlowTest extends TestBaseSetup {

    private CarsResultsPage carsResultsPage = null;
    private CheckOutPage checkOutPage = null;
    private ConfirmationPage confirmationPage = null;

    private CarsDataTrip carsDataTrip = null;
    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initDataLists() {
        dataManagement.getCarsDataTripList();
        dataManagement.getPassengersList();
        dataManagement.getPaymentList();
        dataManagement.getBillingList();
        dataManagement.getContactList();
    }

    @BeforeMethod
    private void closeLoginPopUp(){
        LoginPopUp loginPopUp = initLoginPopUp();
        loginPopUp.clickCloseLoginBtn();
        basePage.clickCarsBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void dom_Booking_Flow() {
        logTestTitle("Cars Flow - Domestic - 10 days - " + countryPar );

        dataManagement.getCarsDataTripItinerary("capital_10days_entre_21_24");

        carsDataTrip = basePage.carsDataTrip();
        carsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        carsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        carsDataTrip.selectDateFromCalendar(carsDataTrip.pickUpDateCalendar, dataManagement.startDate);
        carsDataTrip.selectDateFromCalendar(carsDataTrip.dropOffDateCalendar, dataManagement.endDate);
        carsDataTrip.selectPickUpTime(dataManagement.pickUpTime);
        carsDataTrip.selectDropOffTime(dataManagement.dropOffTime);
        carsDataTrip.selectAgeRange(dataManagement.ageRange);
        carsResultsPage = carsDataTrip.clickBuscarBtn();
        checkOutPage = carsResultsPage.clickReservarAhoraBtn();

        dataManagement.getPassengerData("adult_male_native");

        checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                                          dataManagement.getPaymentData("1_amex_amex"),
                                          dataManagement.getBillingData("local_Billing_v2"),
                                          dataManagement.getContactData("contact_cell_phone"),
                                          "CarsCheckOutPage");
    }

    @Test
    public void int_Booking_Flow() {
        logTestTitle("Cars Flow - International - 10 days - " + countryPar );

        dataManagement.getCarsDataTripItinerary("miami_10days_entre_21_24");

        carsDataTrip = basePage.carsDataTrip();
        carsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        carsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        carsDataTrip.selectDateFromCalendar(carsDataTrip.pickUpDateCalendar, dataManagement.startDate);
        carsDataTrip.selectDateFromCalendar(carsDataTrip.dropOffDateCalendar, dataManagement.endDate);
        carsDataTrip.selectPickUpTime(dataManagement.pickUpTime);
        carsDataTrip.selectDropOffTime(dataManagement.dropOffTime);
        carsDataTrip.selectAgeRange(dataManagement.ageRange);
        carsResultsPage = carsDataTrip.clickBuscarBtn();
        checkOutPage = carsResultsPage.clickReservarAhoraBtn();

        dataManagement.getPassengerData("adult_male_native");

        checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                                          dataManagement.getPaymentData("1_amex_amex"),
                                          dataManagement.getBillingData("local_Billing_v2"),
                                          dataManagement.getContactData("contact_cell_phone"),
                                          "CarsCheckOutPage");
    }
}