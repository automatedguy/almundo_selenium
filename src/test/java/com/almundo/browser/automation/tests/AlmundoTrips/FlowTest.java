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
import com.almundo.browser.automation.utils.sevices.Trips;
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
import static com.almundo.browser.automation.utils.Constants.FlightType.ONE_WAY;
import static com.almundo.browser.automation.utils.Constants.Products.*;
import static com.almundo.browser.automation.utils.Constants.Results.PASSED;
import static com.almundo.browser.automation.utils.Constants.TRIPS_URL;
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
    private SearchInAlmundo searchInAlmundo = null;
    private ActivityFeed activityFeed = null;
    private Dashboard dashboard = null;
    private AddEvent addEvent = null;
    private JSONObject userData = null;

    @BeforeClass
    private void initItineraryData() {
        dataManagement.getUsersDataList();
        dataManagement.getTrippersDataTripList();
        userData = dataManagement.getUserData("email");
    }

    @BeforeMethod
    private void initLoginPopUpElement(){
        loginPopUp = initLoginPopUp();
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());
        basePage = loginPopUp.clickIngresarBtn();
        activityFeed =  basePage.headerSection().clickMyTripsLnk();
        home = activityFeed.clickMyTripsTittle();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void createAlmundoTrip(){
        CreateTrip createTrip = null;
        String finalTripName = null;
        TripConfirmation tripConfirmation;
        cleanUserTrips();

        logTestTitle("Trips Flow - createAlmundoTrip " + countryPar );

        dataManagement.getAlmundoTripInfo("trip_111Days_NewYork");

        createTrip = home.clickCreateTrip();
        createTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        createTrip.setTripName(finalTripName = dataManagement.tripName + " " + randomString(7));
        createTrip.selectDateFromCalendar(createTrip.tripStartDateCalendar, dataManagement.startDate);
        createTrip.selectDateFromCalendar(createTrip.tripEndDateCalendar, dataManagement.endDate);

        tripConfirmation = createTrip.clickCreateTrip();

        dashboard = tripConfirmation.clickOmitirLnk();

        Assert.assertTrue(dashboard.checkTripTitle(finalTripName));

        activityFeed =  basePage.headerSection().clickMyTripsLnk();
        home = activityFeed.clickMyTripsTittle();

        Assert.assertTrue(home.tripWasCreatedOk(finalTripName));
        setResultSauceLabs(PASSED);
    }

    @Test
    public void addCustomEvent(){
        AddCustomEvent addCustomEvent;

        logTestTitle("Trips Flow - Add Custom Event " + countryPar );

        dataManagement.getAlmundoDataTripsItinerary("miami_10days_2adults_2childs_1room");

        dashboard = home.selectTripFromList(FIRST_OPTION);
        addEvent = dashboard.clickAddEvent();

        addCustomEvent = addEvent.clickEventoPersonalizado();
        addCustomEvent.setNombreDeEvento(dataManagement.eventName);
        addCustomEvent.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        addCustomEvent.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        addCustomEvent.setDescription(dataManagement.eventDescription);
        addCustomEvent.selectDateFromCalendar(addCustomEvent.checkinCalendar, dataManagement.startDate);
        addCustomEvent.selectPickUpTime(dataManagement.pickUpTime);
        addCustomEvent.selectDateFromCalendar(addCustomEvent.checkoutCalendar, dataManagement.endDate);
        addCustomEvent.selectDropOffTime(dataManagement.dropOffTime);
        addCustomEvent.clickAgregarBtn();

        Assert.assertTrue(dashboard.eventCreated(dataManagement.eventName));
        setResultSauceLabs(PASSED);
    }

    @Test
    public void addFlightAlmundoEvent(){
        TripsFlightsData tripsFlightsData;
        FlightsResultsPage flightsResultsPage;
        SaveFavourite saveFavourite;

        logTestTitle("Trips Flow - Add Almundo Event Flight" + countryPar );

        dataManagement.getAlmundoDataTripsItinerary("miami_10days_2adults_2childs_1room");

        dashboard = home.selectTripFromList(FIRST_OPTION);
        String tripName = dashboard.getTripTitle();
        addEvent = dashboard.clickAddEvent();

        searchInAlmundo = addEvent.clickBuscarEnAlmundo();
        searchInAlmundo.selectProduct(VUELOS);

        tripsFlightsData = initTripsFlightsData();

        tripsFlightsData.selectFlightType(ONE_WAY);
        tripsFlightsData.setOrigin(dataManagement.originAuto,dataManagement.originFull);
        tripsFlightsData.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        tripsFlightsData.selectDateFromCalendar(tripsFlightsData.checkinCalendarFlights, dataManagement.startDate);
        flightsResultsPage = tripsFlightsData.clickBuscarVuelosBtn();

        Assert.assertTrue(flightsResultsPage.vacancy());
        flightsResultsPage.clickTicketIdaRdb(FIRST_OPTION);
        saveFavourite = flightsResultsPage.clickFavouriteIcon(FIRST_OPTION);

        saveFavourite.selectTripFromList(tripName);
        saveFavourite.guardarBtn.click();




        setResultSauceLabs(PASSED);

    }

    @Test
    public void addAlmundoEventHotel(){
        TripsHotelsData tripsHotelsData;
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

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    private void cleanUserTrips() {
        String externalUserId = userData.get("externalUserId").toString();
        String id_token = userData.get("id_token").toString();
        Trips trips;
        try {
            trips = new Trips(TRIPS_URL, externalUserId, id_token);
            trips.cleanUserTrips();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}