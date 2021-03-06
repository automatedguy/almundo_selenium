package com.almundo.browser.automation.tests.Trips;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.BasePage.TripsDataTrip;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.ResultsPage.TripsDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.TripsResultsPage;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.almundo.browser.automation.utils.Constants.*;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class LoginFlowTest extends TestBaseSetup {

    private DataManagement itineraryData = new DataManagement();
    private TripsDataTrip tripsDataTrip = null;
    private TripsResultsPage tripsResultsPage = null;
    private TripsDetailPage tripsDetailPage = null;
    private CheckOutPageV3 checkOutPageV3 = null;

    @BeforeClass
    private void initItineraryData() {
        itineraryData.setTripsItineraryData();
    }

    @BeforeMethod
    private void doLogin(){
        LoginPopUp loginPopUp = initLoginPopUp();
        JSONObject userData = itineraryData.setUserData("email");
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());
        basePage = loginPopUp.clickIngresarBtn();
        tripsDataTrip = basePage.clicksTripsBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        itineraryData.clearPassengerJsonList();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void login_Int_Booking_Flow() {
        logTestTitle("International - 10 days - 2 Adults/2 Childs - 1 Room");

        itineraryData.setTripsDataTripItinerary(MIA_10D_2A_2C_1R);

        tripsDataTrip.setOrigin(itineraryData.getOriginAuto(), itineraryData.getOriginFull());
        tripsDataTrip.setDestination(itineraryData.getDestinationAuto(), itineraryData.getDestinationFull());
        tripsDataTrip.setDate(tripsDataTrip.getDepartureCalendar(), itineraryData.getStartDate());
        tripsDataTrip.setDate(tripsDataTrip.getArrivalCalendar(), itineraryData.getEndDate());
        tripsDataTrip.setPassengers(itineraryData.getAdults(), itineraryData.getChilds(), itineraryData.getRooms());
        tripsResultsPage = tripsDataTrip.clickBuscarBtn();

        Assert.assertTrue(tripsResultsPage.vacancy());
        tripsResultsPage.clickElegirBtn(FIRST_OPTION);
        tripsDetailPage = tripsResultsPage.clickContinuarBtn();
        tripsDetailPage.clickVerHabitacionBtn();

        itineraryData.setPassengerData(ADULT_MALE_NATIVE);
        itineraryData.setPassengerData(ADULT_MALE_NATIVE);
        itineraryData.setPassengerData(CHILD_MALE_NATIVE);
        itineraryData.setPassengerData(CHILD_MALE_NATIVE);

        checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);
        checkOutPageV3.setCheckOutInfo(itineraryData.getPassengerJsonList(),
                                             RANDOM, itineraryData.getBillingData(LOCAL_BILLING),
                                              itineraryData.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);
    }

    @Test
    public void login_Dom_Booking_Flow() throws IOException, ParseException {
        logTestTitle("Domestic - 15 days - 2 Adults/1 Child - 1 Room");

        itineraryData.setTripsDataTripItinerary(DOM01_15D_2A_1C_1R);

        tripsDataTrip.setOrigin(itineraryData.getOriginAuto(), itineraryData.getOriginFull());
        tripsDataTrip.setDestination(itineraryData.getDestinationAuto(), itineraryData.getDestinationFull());
        tripsDataTrip.setDate(tripsDataTrip.getDepartureCalendar(), itineraryData.getStartDate());
        tripsDataTrip.setDate(tripsDataTrip.getArrivalCalendar(), itineraryData.getEndDate());
        tripsDataTrip.setPassengers(itineraryData.getAdults(), itineraryData.getChilds(), itineraryData.getRooms());
        tripsResultsPage = tripsDataTrip.clickBuscarBtn();

        Assert.assertTrue(tripsResultsPage.vacancy());
        tripsResultsPage.clickElegirBtn(FIRST_OPTION);
        tripsDetailPage = tripsResultsPage.clickContinuarBtn();
        tripsDetailPage.clickVerHabitacionBtn();

        itineraryData.setPassengerData(ADULT_FEMALE_FOREIGN);
        itineraryData.setPassengerData(ADULT_FEMALE_FOREIGN);
        itineraryData.setPassengerData(CHILD_FEMALE_NATIVE);

        checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);
        checkOutPageV3.setCheckOutInfo(itineraryData.getPassengerJsonList(),
                                             RANDOM, itineraryData.getBillingData(LOCAL_BILLING),
                                              itineraryData.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_DOMV3);
    }
}