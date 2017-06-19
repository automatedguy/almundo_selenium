package com.almundo.browser.automation.tests.AlmundoTrips;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.ResultsPage.FlightsResultsPage;
import com.almundo.browser.automation.pages.AlmundoTrips.AgregarEvento;
import com.almundo.browser.automation.pages.AlmundoTrips.AgregarOtroEvento;
import com.almundo.browser.automation.pages.AlmundoTrips.BuscarEnAlmundo;
import com.almundo.browser.automation.pages.AlmundoTrips.Dashboard;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.FlightType.ONE_WAY;
import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

/**
 * Created by gabrielcespedes on 16/06/17.
 */
public class FlowTest extends TestBaseSetup {

    private LoginPopUp loginPopUp = null;

    private DataManagement dataManagement = new DataManagement();

    /******** AlmundoTrips Stuff ****/
    private Dashboard dashboard = null;
    private AgregarEvento agregarEvento = null;

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
        AgregarOtroEvento agregarOtroEvento = null;

        logTestTitle("Trips Flow - Add Personalized Event " + countryPar );

        dataManagement.getTrippersDataTripsItinerary("miami_10days_2adults_2childs_1room");

        dashboard = goToTrippersDashboard();
        agregarEvento = dashboard.clickAgregarEventoBtn();
        agregarOtroEvento = agregarEvento.clickEventoPersonalizado();
        agregarOtroEvento.setNombreDeEvento(dataManagement.eventName);
        agregarOtroEvento.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        agregarOtroEvento.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        agregarOtroEvento.setDescription(dataManagement.eventDescription);
        agregarOtroEvento.selectDateFromCalendar(agregarOtroEvento.checkinCalendar, dataManagement.startDate);
        agregarOtroEvento.selectPickUpTime(dataManagement.pickUpTime);
        agregarOtroEvento.selectDateFromCalendar(agregarOtroEvento.checkoutCalendar, dataManagement.endDate);
        agregarOtroEvento.selectDropOffTime(dataManagement.dropOffTime);
        agregarOtroEvento.clickAgregarBtn();
        waitImplicitly(7000);
    }

    @Test
    public void addAlmundoEventFlight(){
        BuscarEnAlmundo buscarEnAlmundo = null;
        FlightsResultsPage flightsResultsPage = null;

        logTestTitle("Trips Flow - Add Almundo Event Flight" + countryPar );

        dataManagement.getTrippersDataTripsItinerary("miami_10days_2adults_2childs_1room");

        dashboard = goToTrippersDashboard();
        agregarEvento = dashboard.clickAgregarEventoBtn();
        buscarEnAlmundo = agregarEvento.clickBuscarEnAlmundo();
        buscarEnAlmundo.selectFlightType(ONE_WAY);
        buscarEnAlmundo.setOrigin(dataManagement.originAuto,dataManagement.originFull);
        buscarEnAlmundo.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        buscarEnAlmundo.selectDateFromCalendar(buscarEnAlmundo.checkinCalendar, dataManagement.startDate);
        flightsResultsPage = buscarEnAlmundo.clickBuscarBtn();
        waitImplicitly(7000);

    }

    @Test
    public void addAlmundoEventHotel(){

        logTestTitle("Trips Flow - Add Almundo Event Hotel" + countryPar );

    }

    @Test
    public void addAlmundoEventCars(){

        logTestTitle("Trips Flow - Add Almundo Event Car" + countryPar );

    }
}
