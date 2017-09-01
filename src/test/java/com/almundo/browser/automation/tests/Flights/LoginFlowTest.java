package com.almundo.browser.automation.tests.Flights;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.FlightsDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.ResultsPage.FlightsResultsPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.*;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class LoginFlowTest extends TestBaseSetup {

    private FlightsResultsPage flightsResultsPage = null;
    private CheckOutPageV3 checkOutPageV3 = null;

    private FlightsDataTrip flightsDataTrip = null;
    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initItineraryData() {
        dataManagement.getFlightsItineraryData();
    }

    @BeforeMethod
    private void doLogin(){
        JSONObject userData = dataManagement.getUserData("email");
        LoginPopUp loginPopUp = initLoginPopUp();
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());
        basePage = loginPopUp.clickIngresarBtn();
        flightsDataTrip = basePage.clickFlightsBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    /***************************** Test Cases *****************************/

    @Test
    public void login_Int_Booking_Flow() {
        logTestTitle("International - 10 days - 2 Adults/2 Childs - Tourist");

        dataManagement.getRoundTripDataTripItinerary(MIAMI_10D_2A_2C_TOURIST);

        flightsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        flightsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.departureFlightsCalendar, dataManagement.startDate);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.arrivalFlightsCalendar, dataManagement.endDate);
        flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
        flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
        flightsDataTrip.selectClass(dataManagement.flightClass);
        flightsResultsPage = flightsDataTrip.clickBuscarBtn();

        Assert.assertTrue(flightsResultsPage.vacancy());
        flightsResultsPage.clickTicketIdaRdb(FIRST_OPTION);
        flightsResultsPage.clickTicketVuelta(FIRST_OPTION+1);

        dataManagement.getPassengerData(ADULT_MALE_NATIVE);
        dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);
        dataManagement.getPassengerData(CHILD_MALE_NATIVE);
        dataManagement.getPassengerData(CHILD_MALE_NATIVE);

        checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                                              RANDOM, dataManagement.getBillingData(LOCAL_BILLING),
                                               dataManagement.getContactData(CONTACT_CELL_PHONE), FLIGHTS_CHECKOUT_INT);
    }

    @Test
    public void login_Dom_Booking_Flow() {
        logTestTitle("Domestic - 20 days - 2 Adults - All");

        dataManagement.getRoundTripDataTripItinerary(DOMESTIC_20D_2A_ALL);

        flightsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        flightsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.departureFlightsCalendar, dataManagement.startDate);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.arrivalFlightsCalendar, dataManagement.endDate);
        flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
        flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
        flightsDataTrip.selectClass(dataManagement.flightClass);
        flightsResultsPage = flightsDataTrip.clickBuscarBtn();

        Assert.assertTrue(flightsResultsPage.vacancy());
        flightsResultsPage.clickTicketIdaRdb(FIRST_OPTION);
        flightsResultsPage.clickTicketVuelta(FIRST_OPTION+1);

        dataManagement.getPassengerData(ADULT_FEMALE_FOREIGN);
        dataManagement.getPassengerData(ADULT_FEMALE_FOREIGN);

        checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                                              RANDOM, dataManagement.getBillingData(LOCAL_BILLING),
                                               dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_DOM);
    }
}