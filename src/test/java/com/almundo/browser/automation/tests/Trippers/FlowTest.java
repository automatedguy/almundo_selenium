package com.almundo.browser.automation.tests.Trippers;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.BasePage.TripsDataTrip;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ConfirmationPageV3;
import com.almundo.browser.automation.pages.ResultsPage.TripsDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.TripsResultsPage;
import com.almundo.browser.automation.pages.Trippers.TrippersAgregarEvento;
import com.almundo.browser.automation.pages.Trippers.TrippersAgregarOtroEvento;
import com.almundo.browser.automation.pages.Trippers.TrippersDashboard;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

/**
 * Created by gabrielcespedes on 16/06/17.
 */
public class FlowTest extends TestBaseSetup {

    private LoginPopUp loginPopUp = null;

    private DataManagement dataManagement = new DataManagement();

    /******** Trippers Sh*t ****/
    private TrippersDashboard trippersDashboard = null;
    private TrippersAgregarEvento trippersAgregarEvento = null;
    private TrippersAgregarOtroEvento trippersAgregarOtroEvento = null;

    private TripsDataTrip tripsDataTrip = null;
    private TripsResultsPage tripsResultsPage = null;
    private TripsDetailPage tripsDetailPage = null;
    private CheckOutPageV3 checkOutPageV3 = null;
    private ConfirmationPageV3 confirmationPageV3 = null;

    @BeforeClass
    private void initItineraryData() {
        dataManagement.getUsersDataList();
        dataManagement.getTrippersDataTripList();
    }

    @BeforeMethod
    private void initLoginPopUpElement(){
        loginPopUp = initLoginPopUp();
        JSONObject userData = dataManagement.getUserData("email");
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());
        basePage = loginPopUp.clickIngresarBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    @Test
    public void addPersonalizedEvent(){
        logTestTitle("Trips Flow - Add Personalized Event " + countryPar );

        dataManagement.getTrippersDataTripsItinerary("miami_10days_2adults_2childs_1room");

        trippersDashboard = goToTrippersDashboard();
        trippersAgregarEvento = trippersDashboard.clickAgregarEventoBtn();
        trippersAgregarOtroEvento = trippersAgregarEvento.clickEventoPersonalizado();
        trippersAgregarOtroEvento.setNombreDeEvento(dataManagement.eventName);
        trippersAgregarOtroEvento.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        trippersAgregarOtroEvento.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        trippersAgregarOtroEvento.setDescription(dataManagement.eventDescription);
        trippersAgregarOtroEvento.selectDateFromCalendar(trippersAgregarOtroEvento.checkinCalendar, dataManagement.startDate);
        trippersAgregarOtroEvento.selectPickUpTime(dataManagement.pickUpTime);
        trippersAgregarOtroEvento.selectDateFromCalendar(trippersAgregarOtroEvento.checkoutCalendar, dataManagement.endDate);
        trippersAgregarOtroEvento.selectDropOffTime(dataManagement.dropOffTime);
        trippersAgregarOtroEvento.clickAgregarBtn();
        waitImplicitly(7000);
    }
}
