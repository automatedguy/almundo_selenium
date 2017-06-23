package com.almundo.browser.automation.tests.AlmundoTrips;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.AlmundoTrips.*;
import com.almundo.browser.automation.pages.AlmundoTrips.DataTrips.TripsCarsData;
import com.almundo.browser.automation.pages.AlmundoTrips.DataTrips.TripsFlightsData;
import com.almundo.browser.automation.pages.AlmundoTrips.DataTrips.TripsHotelsData;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.ResultsPage.CarsResultsPage;
import com.almundo.browser.automation.pages.ResultsPage.FlightsResultsPage;
import com.almundo.browser.automation.pages.ResultsPage.HotelsResultsPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.FIRST_OPTION;
import static com.almundo.browser.automation.utils.Constants.FlightType.ONE_WAY;
import static com.almundo.browser.automation.utils.Constants.Products.*;
import static com.almundo.browser.automation.utils.Constants.Results.PASSED;
import static com.almundo.browser.automation.utils.PageUtils.randomString;
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

    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    @Test
    public void createAlmundoTrip(){
        CreateTrip createTrip = null;
        String finalTripName = null;
        TripConfirmation tripConfirmation = null;

        logTestTitle("Trips Flow - createAlmundoTrip " + countryPar );

        dataManagement.getAlmundoTripInfo("trip_111Days_NewYork");

        createTrip = home.clickCreateTrip();
        createTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        createTrip.setTripName(finalTripName = dataManagement.tripName + " " + randomString(7));
        createTrip.selectDateFromCalendar(createTrip.tripStartDateCalendar, dataManagement.startDate);
        createTrip.selectDateFromCalendar(createTrip.tripEndDateCalendar, dataManagement.endDate);

        tripConfirmation = createTrip.clickCreateTrip();

        dashboard = tripConfirmation.clickOmitirLnk();

        activityFeed =  basePage.headerSection().clickMyTripsLnk();
        home = activityFeed.clickMyTripsTittle();

        Assert.assertTrue(home.tripWasCreatedOk(finalTripName));
        setResultSauceLabs(PASSED);
    }

    @Test
    public void addEventPersonalized(){
        AddAnotherEvent addAnotherEvent = null;

        logTestTitle("Trips Flow - Add Personalized Event " + countryPar );

        dataManagement.getAlmundoDataTripsItinerary("miami_10days_2adults_2childs_1room");

        dashboard = home.selectTripFromList(FIRST_OPTION);
        addEvent = dashboard.clickAddEvent();

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
        setResultSauceLabs(PASSED);
        waitImplicitly(7000);
    }

    @Test
    public void addFlightAlmundoEvent(){
        SearchInAlmundo searchInAlmundo = null;
        TripsFlightsData tripsFlightsData = null;
        FlightsResultsPage flightsResultsPage = null;

        logTestTitle("Trips Flow - Add Almundo Event Flight" + countryPar );

        dataManagement.getAlmundoDataTripsItinerary("miami_10days_2adults_2childs_1room");

        dashboard = home.selectTripFromList(FIRST_OPTION);
        addEvent = dashboard.clickAddEvent();

        searchInAlmundo = addEvent.clickBuscarEnAlmundo();
        searchInAlmundo.selectProduct(VUELOS);

        tripsFlightsData = initTripsFlightsData();

        tripsFlightsData.selectFlightType(ONE_WAY);
        tripsFlightsData.setOrigin(dataManagement.originAuto,dataManagement.originFull);
        tripsFlightsData.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        tripsFlightsData.selectDateFromCalendar(tripsFlightsData.checkinCalendarFlights, dataManagement.startDate);
        flightsResultsPage = tripsFlightsData.clickBuscarVuelosBtn();
        setResultSauceLabs(PASSED);
        waitImplicitly(7000);

    }

    @Test
    public void addAlmundoEventHotel(){
        SearchInAlmundo searchInAlmundo = null;
        TripsHotelsData tripsHotelsData = null;
        HotelsResultsPage hotelsResultsPage = null;

        logTestTitle("Trips Flow - Add Almundo Event Hotel" + countryPar );

        dataManagement.getAlmundoDataTripsItinerary("miami_10days_2adults_2childs_1room");

        dashboard = home.selectTripFromList(FIRST_OPTION);
        addEvent = dashboard.clickAddEvent();

        searchInAlmundo = addEvent.clickBuscarEnAlmundo();
        searchInAlmundo.selectProduct(HOTELES);

        tripsHotelsData = initTripsHotelsData();

        tripsHotelsData.setDestination(dataManagement.destinationAuto,dataManagement.destinationFull);
        tripsHotelsData.selectDateFromCalendar(tripsHotelsData.checkinCalendarHotels, dataManagement.startDate);
        tripsHotelsData.selectDateFromCalendar(tripsHotelsData.checkinCalendarHotels, dataManagement.startDate);

        hotelsResultsPage = tripsHotelsData.clickBuscarHotelesBtn();
        waitImplicitly(7000);

    }

    @Test
    public void addAlmundoEventCars(){
        SearchInAlmundo searchInAlmundo = null;
        CarsResultsPage carsResultsPage = null;
        TripsCarsData tripsCarsData = null;

        logTestTitle("Trips Flow - Add Almundo Event Car" + countryPar );

        dataManagement.getAlmundoDataTripsItinerary("miami_10days_2adults_2childs_1room");

        dashboard = home.selectTripFromList(FIRST_OPTION);
        addEvent = dashboard.clickAddEvent();

        searchInAlmundo = addEvent.clickBuscarEnAlmundo();
        searchInAlmundo.selectProduct(AUTOS);

        tripsCarsData = initTripsCarsData();

        //searchInAlmundo.setOrigin(dataManagement.originAuto,dataManagement.originFull);
        tripsCarsData.setDestination(dataManagement.destinationAuto,dataManagement.destinationFull);
        tripsCarsData.selectDateFromCalendar(searchInAlmundo.checkinCalendarCars, dataManagement.startDate);
        tripsCarsData.selectDateFromCalendar(searchInAlmundo.checkinCalendarCars, dataManagement.startDate);

        carsResultsPage = tripsCarsData.clickBuscarAutosBtn();
        waitImplicitly(7000);
    }
}
