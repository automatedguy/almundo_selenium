package com.almundo.browser.automation.tests.Trips;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.BasePage.TripsDataTrip;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPage.ConfirmationPage;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ConfirmationPageV3;
import com.almundo.browser.automation.pages.ResultsPage.TripsDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.TripsResultsPage;
import org.json.simple.JSONArray;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.*;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class FlowTest extends TestBaseSetup {

    private TripsResultsPage tripsResultsPage = null;
    private TripsDetailPage tripsDetailPage = null;
    private TripsDataTrip tripsDataTrip = null;
    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initItineraryData() {
        dataManagement.getTripsItineraryData();
    }

    @BeforeMethod
    private void closeLoginPopUp(){
        LoginPopUp loginPopUp = initLoginPopUp();
        loginPopUp.clickCloseLoginBtn();
        basePage.clicksTripsBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void int_Booking_Flow() {
        logTestTitle("Trips Flow - Int - 10 days - 2 Adults/2 Childs - 1 Room - " + countryPar );

        dataManagement.getTripsDataTripItinerary("miami_10days_2adults_2childs_1room");

        tripsDataTrip = basePage.tripsDataTrip();
        tripsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        tripsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        tripsDataTrip.selectDateFromCalendar(tripsDataTrip.departureCalendar, dataManagement.startDate);
        tripsDataTrip.selectDateFromCalendar(tripsDataTrip.arrivalCalendar, dataManagement.endDate);
        tripsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
        tripsResultsPage = tripsDataTrip.clickBuscarBtn();

        Assert.assertTrue(tripsResultsPage.vacancy());
        tripsResultsPage.clickElegirBtn(FIRST_OPTION);
        tripsDetailPage = tripsResultsPage.clickContinuarBtn();
        tripsDetailPage.clickVerHabitacionBtn();

        dataManagement.getPassengerData("adult_male_native");
        dataManagement.getPassengerData("adult_male_native");
        dataManagement.getPassengerData("child_male_native");
        dataManagement.getPassengerData("child_male_native");

        if(countryPar.equals("ARGENTINA")) {
            CheckOutPageV3 checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);
            replaceUrl();
            checkOutPageV3.populateCheckOutPage(dataManagement.passengerJsonList,
                                                dataManagement.getPaymentData("1_visa_visa"),
                                                dataManagement.getBillingData("local_Billing"),
                                                dataManagement.getContactData("contact_cell_phone"),
                                                "TripsCheckOutPageInternationalV3");

            ConfirmationPageV3 confirmationPage = checkOutPageV3.clickComprarBtn();
            Assert.assertTrue(confirmationPage.confirmationOk());

        } else {
            CheckOutPage checkOutPage = tripsDetailPage.clickComprarBtn(FIRST_OPTION);
            checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                                              dataManagement.getPaymentData("1_visa_visa"),
                                              dataManagement.getBillingData("local_Billing"),
                                              dataManagement.getContactData("contact_cell_phone"),
                                              "TripsCheckOutPageInternational");

            ConfirmationPage confirmationPage = checkOutPage.clickComprarBtn();
            Assert.assertTrue(confirmationPage.confirmationOk());
        }
    }

    @Test
    public void dom_Booking_Flow() {
        logTestTitle("Trips Flow - Domestic - 15 days - 2 Adults/1 Child - 1 Room - " + countryPar );

        dataManagement.getTripsDataTripItinerary("domestic01_15days_2adults_1childs_1room");

        tripsDataTrip = basePage.tripsDataTrip();
        tripsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        tripsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        tripsDataTrip.selectDateFromCalendar(tripsDataTrip.departureCalendar, dataManagement.startDate);
        tripsDataTrip.selectDateFromCalendar(tripsDataTrip.arrivalCalendar, dataManagement.endDate);
        tripsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
        tripsResultsPage = tripsDataTrip.clickBuscarBtn();

        Assert.assertTrue(tripsResultsPage.vacancy());
        tripsResultsPage.clickElegirBtn(FIRST_OPTION);
        tripsDetailPage = tripsResultsPage.clickContinuarBtn();
        tripsDetailPage.clickVerHabitacionBtn();

        dataManagement.getPassengerData("adult_female_foreign");
        dataManagement.getPassengerData("adult_female_foreign");
        dataManagement.getPassengerData("child_female_native");

        if(countryPar.equals("ARGENTINA")) {
            CheckOutPageV3 checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);
            replaceUrl();
            checkOutPageV3.populateCheckOutPage(dataManagement.passengerJsonList,
                                                dataManagement.getPaymentData("1_amex_amex"),
                                                dataManagement.getBillingData("local_Billing"),
                                                dataManagement.getContactData("contact_cell_phone"),
                                                "TripsCheckOutPageDomesticlV3");
        } else {
            CheckOutPage checkOutPage = tripsDetailPage.clickComprarBtn(FIRST_OPTION);
            checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                                              dataManagement.getPaymentData("1_amex_amex"),
                                              dataManagement.getBillingData("local_Billing"),
                                              dataManagement.getContactData("contact_cell_phone"),
                                              "TripsCheckOutPageDomestic");
        }
    }
}