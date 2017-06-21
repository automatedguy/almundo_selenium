package com.almundo.browser.automation.tests.AlmundoTrips;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.AlmundoTrips.*;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.ResultsPage.CarsResultsPage;
import com.almundo.browser.automation.pages.ResultsPage.FlightsResultsPage;
import com.almundo.browser.automation.pages.ResultsPage.HotelsResultsPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.FIRST_OPTION;
import static com.almundo.browser.automation.utils.Constants.FlightType.ONE_WAY;
import static com.almundo.browser.automation.utils.Constants.Products.AUTOS;
import static com.almundo.browser.automation.utils.Constants.Products.HOTELES;
import static com.almundo.browser.automation.utils.Constants.Products.VUELOS;
import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

/**
 * Created by gabrielcespedes on 16/06/17.
 */
public class FlowTest extends TestBaseSetup {

    private LoginPopUp loginPopUp = null;

    private DataManagement dataManagement = new DataManagement();

    /******** AlmundoTrips Stuff ****/
    private Home home = null;
    private ActivityFeed activityFeed = null;
    private Dashboard dashboard = null;
    private AddEvent addEvent = null;

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
        activityFeed =  basePage.headerSection().clickMyTripsLnk();
        home = activityFeed.clickMyTripsTittle();
        dashboard = home.selectTripFromList(FIRST_OPTION);
        addEvent = dashboard.clickAgregarEventoBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    @Test
    public void addEventPersonalized(){
        AddAnotherEvent addAnotherEvent = null;

        logTestTitle("Trips Flow - Add Personalized Event " + countryPar );

        dataManagement.getTrippersDataTripsItinerary("miami_10days_2adults_2childs_1room");

        addAnotherEvent = addEvent.clickEventoPersonalizado();
        addAnotherEvent.setNombreDeEvento(dataManagement.eventName);
        addAnotherEvent.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        addAnotherEvent.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        addAnotherEvent.setDescription(dataManagement.eventDescription);
        addAnotherEvent.selectDateFromCalendar(addAnotherEvent.checkinCalendar, dataManagement.startDate);
        addAnotherEvent.selectPickUpTime(dataManagement.pickUpTime);
        addAnotherEvent.selectDateFromCalendar(addAnotherEvent.checkoutCalendar, dataManagement.endDate);
        addAnotherEvent.selectDropOffTime(dataManagement.dropOffTime);
        addAnotherEvent.clickAgregarBtn();
        waitImplicitly(7000);
    }

    @Test
    public void addFlightAlmundoEvent(){
        SearchInAlmundo searchInAlmundo = null;
        FlightsResultsPage flightsResultsPage = null;

        logTestTitle("Trips Flow - Add Almundo Event Flight" + countryPar );

        dataManagement.getTrippersDataTripsItinerary("miami_10days_2adults_2childs_1room");

        searchInAlmundo = addEvent.clickBuscarEnAlmundo();
        searchInAlmundo.selectProduct(VUELOS);
        searchInAlmundo.selectFlightType(ONE_WAY);
        searchInAlmundo.setOrigin(dataManagement.originAuto,dataManagement.originFull);
        searchInAlmundo.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        searchInAlmundo.selectDateFromCalendar(searchInAlmundo.checkinCalendarFlights, dataManagement.startDate);
        flightsResultsPage = searchInAlmundo.clickBuscarVuelosBtn();
        waitImplicitly(7000);

    }

    @Test
    public void addAlmundoEventHotel(){
        SearchInAlmundo searchInAlmundo = null;
        AlmundoTripsHotelsData almundoTripsHotelsData = null;
        HotelsResultsPage hotelsResultsPage = null;

        logTestTitle("Trips Flow - Add Almundo Event Hotel" + countryPar );

        dataManagement.getTrippersDataTripsItinerary("miami_10days_2adults_2childs_1room");

        searchInAlmundo = addEvent.clickBuscarEnAlmundo();
        searchInAlmundo.selectProduct(HOTELES);

        almundoTripsHotelsData =  initAlmundoTripsHotelsData();

        almundoTripsHotelsData.setDestination(dataManagement.destinationAuto,dataManagement.destinationFull);
        almundoTripsHotelsData.selectDateFromCalendar(almundoTripsHotelsData.checkinCalendarHotels, dataManagement.startDate);
        almundoTripsHotelsData.selectDateFromCalendar(almundoTripsHotelsData.checkinCalendarHotels, dataManagement.startDate);

        hotelsResultsPage = almundoTripsHotelsData.clickBuscarHotelesBtn();
        waitImplicitly(7000);

    }

    @Test
    public void addAlmundoEventCars(){
        SearchInAlmundo searchInAlmundo = null;
        CarsResultsPage carsResultsPage = null;

        logTestTitle("Trips Flow - Add Almundo Event Car" + countryPar );

        dataManagement.getTrippersDataTripsItinerary("miami_10days_2adults_2childs_1room");

        searchInAlmundo = addEvent.clickBuscarEnAlmundo();
        searchInAlmundo.selectProduct(AUTOS);

        searchInAlmundo.setOrigin(dataManagement.originAuto,dataManagement.originFull);
        searchInAlmundo.setDestination(dataManagement.destinationAuto,dataManagement.destinationFull);
        searchInAlmundo.selectDateFromCalendar(searchInAlmundo.checkinCalendarCars, dataManagement.startDate);
        searchInAlmundo.selectDateFromCalendar(searchInAlmundo.checkinCalendarCars, dataManagement.startDate);

        carsResultsPage = searchInAlmundo.clickBuscarAutosBtn();
        waitImplicitly(7000);
    }
}
