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
        activityFeed =  basePage.headerSection().clickMyTripsLnk();
        home = activityFeed.clickMyTripsTittle();
        dashboard = home.selectTripFromList(FIRST_OPTION);
        agregarEvento = dashboard.clickAgregarEventoBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    @Test
    public void addEventPersonalized(){
        AgregarOtroEvento agregarOtroEvento = null;

        logTestTitle("Trips Flow - Add Personalized Event " + countryPar );

        dataManagement.getTrippersDataTripsItinerary("miami_10days_2adults_2childs_1room");

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
    public void addFlightAlmundoEvent(){
        BuscarEnAlmundo buscarEnAlmundo = null;
        FlightsResultsPage flightsResultsPage = null;

        logTestTitle("Trips Flow - Add Almundo Event Flight" + countryPar );

        dataManagement.getTrippersDataTripsItinerary("miami_10days_2adults_2childs_1room");

        buscarEnAlmundo = agregarEvento.clickBuscarEnAlmundo();
        buscarEnAlmundo.selectProduct(VUELOS);
        buscarEnAlmundo.selectFlightType(ONE_WAY);
        buscarEnAlmundo.setOrigin(dataManagement.originAuto,dataManagement.originFull);
        buscarEnAlmundo.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        buscarEnAlmundo.selectDateFromCalendar(buscarEnAlmundo.checkinCalendarFlights, dataManagement.startDate);
        flightsResultsPage = buscarEnAlmundo.clickBuscarVuelosBtn();
        waitImplicitly(7000);

    }

    @Test
    public void addAlmundoEventHotel(){
        BuscarEnAlmundo buscarEnAlmundo = null;
        AlmundoTripsHotelsData almundoTripsHotelsData = null;
        HotelsResultsPage hotelsResultsPage = null;

        logTestTitle("Trips Flow - Add Almundo Event Hotel" + countryPar );

        dataManagement.getTrippersDataTripsItinerary("miami_10days_2adults_2childs_1room");

        buscarEnAlmundo = agregarEvento.clickBuscarEnAlmundo();
        buscarEnAlmundo.selectProduct(HOTELES);

        almundoTripsHotelsData =  initAlmundoTripsHotelsData();

        almundoTripsHotelsData.setDestination(dataManagement.destinationAuto,dataManagement.destinationFull);
        almundoTripsHotelsData.selectDateFromCalendar(almundoTripsHotelsData.checkinCalendarHotels, dataManagement.startDate);
        almundoTripsHotelsData.selectDateFromCalendar(almundoTripsHotelsData.checkinCalendarHotels, dataManagement.startDate);

        hotelsResultsPage = almundoTripsHotelsData.clickBuscarHotelesBtn();
        waitImplicitly(7000);

    }

    @Test
    public void addAlmundoEventCars(){
        BuscarEnAlmundo buscarEnAlmundo = null;
        CarsResultsPage carsResultsPage = null;

        logTestTitle("Trips Flow - Add Almundo Event Car" + countryPar );

        dataManagement.getTrippersDataTripsItinerary("miami_10days_2adults_2childs_1room");

        buscarEnAlmundo = agregarEvento.clickBuscarEnAlmundo();
        buscarEnAlmundo.selectProduct(AUTOS);

        buscarEnAlmundo.setOrigin(dataManagement.originAuto,dataManagement.originFull);
        buscarEnAlmundo.setDestination(dataManagement.destinationAuto,dataManagement.destinationFull);
        buscarEnAlmundo.selectDateFromCalendar(buscarEnAlmundo.checkinCalendarCars, dataManagement.startDate);
        buscarEnAlmundo.selectDateFromCalendar(buscarEnAlmundo.checkinCalendarCars, dataManagement.startDate);

        carsResultsPage = buscarEnAlmundo.clickBuscarAutosBtn();
        waitImplicitly(7000);
    }
}
