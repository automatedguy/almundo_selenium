package com.almundo.browser.automation.tests.Trips;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.BasePage.TripsDataTrip;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.ResultsPage.TripsDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.TripsResultsPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.almundo.browser.automation.utils.Constants.FIRST_OPTION;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class LoginFlowTest extends TestBaseSetup {

    private DataManagement dataManagement = new DataManagement();
    private TripsDataTrip tripsDataTrip = null;
    private TripsResultsPage tripsResultsPage = null;
    private TripsDetailPage tripsDetailPage = null;
    private CheckOutPageV3 checkOutPageV3 = null;

    @BeforeClass
    private void initItineraryData() {
        dataManagement.getTripsItineraryData();
    }

    @BeforeMethod
    private void doLogin(){
        LoginPopUp loginPopUp = initLoginPopUp();
        JSONObject userData = dataManagement.getUserData("email");
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());
        basePage = loginPopUp.clickIngresarBtn();
        tripsDataTrip = basePage.clicksTripsBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void login_Int_Booking_Flow() throws IOException, ParseException {
        logTestTitle("Login Trips Flow - International - 10 days - 2 Adults/2 Childs - 1 Room - " + countryPar );

        dataManagement.getTripsDataTripItinerary("miami_10days_2adults_2childs_1room");

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

        checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                                             "random",
                                              dataManagement.getBillingData("local_Billing"),
                                              dataManagement.getContactData("contact_cell_phone"),
                                             "TripsCheckOutPageInternationalV3");
    }

    @Test
    public void login_Dom_Booking_Flow() throws IOException, ParseException {
        logTestTitle("Login Trips Flow - Domestic - 15 days - 2 Adults/1 Child - 1 Room - " + countryPar );

        dataManagement.getTripsDataTripItinerary("domestic01_15days_2adults_1childs_1room");

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

        checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                                             "random",
                                              dataManagement.getBillingData("local_Billing"),
                                              dataManagement.getContactData("contact_cell_phone"),
                                             "VueloHotelCheckOutPageDomesticV3");
    }
}