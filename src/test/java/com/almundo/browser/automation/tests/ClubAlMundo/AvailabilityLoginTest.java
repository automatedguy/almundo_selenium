package com.almundo.browser.automation.tests.ClubAlMundo;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.*;
import com.almundo.browser.automation.pages.CheckOutPageV3.AgreementPage;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ThanksPageV3;
import com.almundo.browser.automation.pages.ResultsPage.*;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.almundo.browser.automation.utils.Constants.*;
import static com.almundo.browser.automation.utils.Constants.FlightType.ROUND_TRIP;
import static com.almundo.browser.automation.utils.Constants.Results.PASSED;
import static com.almundo.browser.automation.utils.PageUtils.userNameOk;

/**
 * Created by gabrielcespedes on 23/05/17.
 */
public class AvailabilityLoginTest extends TestBaseSetup {

    private LoginPopUp loginPopUp = null;

    private DataManagement dataManagement = new DataManagement();

    /********** Flights Related Objects and Pages ***********/
    private FlightsDataTrip flightsDataTrip = null;
    private FlightsResultsPage flightsResultsPage = null;

    /********** Hotels Related Objects and Pages ***********/
    private HotelsDataTrip hotelsDataTrip = null;
    private HotelsResultsPage hotelsResultsPage = null;
    private HotelsDetailPage hotelsDetailPage = null;

    /********** Cars Related Objects and Pages ***********/
    private CarsDataTrip carsDataTrip =  null;
    private CarsResultsPage carsResultsPage = null;

    /********** Trips Related Objects and Pages ***********/
    private TripsDataTrip tripsDataTrip = null;
    private TripsResultsPage tripsResultsPage = null;
    private TripsDetailPage tripsDetailPage = null;

    /********** Common Objects for (V3): Checkout, Agreement and Confirmation Pages ***********/
    private CheckOutPageV3 checkOutPageV3 = null;
    private AgreementPage agreementPage = null;
    private ThanksPageV3 thanksPageV3 = null;

    JSONObject userData = null;

    @BeforeClass
    private void initUserList() {
        dataManagement.getUsersDataList();
        userData = dataManagement.getUserData("email");
    }

    @BeforeMethod
    private void closeLoginPopUp(){
        loginPopUp = initLoginPopUp();
        loginPopUp.clickCloseLoginBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    @Test
    public void flightsAvailabilityLogin(){
        logTestTitle("Search Flight And Login With Email");

        dataManagement.getFlightsItineraryData();
        dataManagement.getRoundTripDataTripItinerary(MIAMI_10D_2A_2C_TOURIST);

        flightsDataTrip = basePage.clickFlightsBtn();
        flightsDataTrip.selectFlightType(ROUND_TRIP);
        flightsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull );
        flightsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.departureFlightsCalendar, dataManagement.startDate);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.arrivalFlightsCalendar, dataManagement.endDate);
        flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
        flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
        flightsDataTrip.selectClass(dataManagement.flightClass);

        flightsResultsPage = flightsDataTrip.clickBuscarBtn();
        Assert.assertTrue(flightsResultsPage.vacancy());
        List<WebElement> flightsChoicesListFirst = flightsResultsPage.getFlightsChoices();

        loginPopUp = basePage.headerSection().clickMyAccountMenuLnk();
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());

        flightsResultsPage = loginPopUp.clickIngresarOnFlightBtn();
        Assert.assertTrue(flightsResultsPage.vacancy());

        logger.info("Validating flight choices on results page are the same as before login.");
        Assert.assertTrue(flightsChoicesListFirst.equals(flightsResultsPage.getFlightsChoices()));

        Assert.assertTrue(userNameOk(userData.get("name").toString(), basePage.headerSection().textLoggedIntLnk.getText()));

        flightsResultsPage.clickTicketIdaRdb(FIRST_OPTION);
        flightsResultsPage.clickTicketVuelta(FIRST_OPTION+1);

        dataManagement.getPassengerData(ADULT_FEMALE_FOREIGN);
        dataManagement.getPassengerData(ADULT_FEMALE_FOREIGN);
        dataManagement.getPassengerData(CHILD_MALE_NATIVE);
        dataManagement.getPassengerData(CHILD_MALE_NATIVE);

        checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), FLIGHTS_CHECKOUT_INT);

        agreementPage = checkOutPageV3.termAndConditionsClick();
        Assert.assertTrue(agreementPage.agreementUrlOk());
        Assert.assertTrue(agreementPage.agreementOk());
        agreementPage.closeAgreementPage();

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void hotelsAvailabilityLogin(){
        logTestTitle("Search Hotel And Login With Email");

        dataManagement.getHotelsItineraryData();
        dataManagement.getHotelsDataTripItinerary(MIA_10D_2A_2C_1R);

        basePage.clickHotelsBtn();

        hotelsDataTrip = basePage.hotelsDataTrip();
        hotelsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.startDate);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);
        hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);

        hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

        Assert.assertTrue(hotelsResultsPage.vacancy());
        List<WebElement> hotelsChoicesListFirst = hotelsResultsPage.getHotelsChoices();

        loginPopUp = basePage.headerSection().clickMyAccountMenuLnk();
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());

        hotelsResultsPage = loginPopUp.clickIngresarOnHotelstBtn();
        Assert.assertTrue(hotelsResultsPage.vacancy());

        logger.info("Validating hotels choices on results page are the same as before login.");
        Assert.assertTrue(hotelsChoicesListFirst.size() == hotelsResultsPage.getHotelsChoices().size());

        Assert.assertTrue(userNameOk(userData.get("name").toString(), basePage.headerSection().textLoggedIntLnk.getText()));

        hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

        PageUtils.switchToNewTab(driver);
        hotelsDetailPage.clickVerHabitacionesBtn();

        dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);
        dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);
        dataManagement.getPassengerData(CHILD_FEMALE_NATIVE);
        dataManagement.getPassengerData(CHILD_FEMALE_NATIVE);

        checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                MASTER_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void carsAvailabilityLogin(){
        logTestTitle("Search Car And Login With Email");

        dataManagement.getCarsItineraryData();
        dataManagement.getCarsDataTripItinerary(MIA_10D_21_24);

        basePage.clickCarsBtn();
        carsDataTrip = basePage.carsDataTrip();
        carsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        carsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        carsDataTrip.selectDateFromCalendar(carsDataTrip.pickUpDateCalendar, dataManagement.startDate);
        carsDataTrip.selectDateFromCalendar(carsDataTrip.dropOffDateCalendar, dataManagement.endDate);
        carsDataTrip.selectPickUpTime(dataManagement.pickUpTime);
        carsDataTrip.selectDropOffTime(dataManagement.dropOffTime);
        carsDataTrip.selectAgeRange(dataManagement.ageRange);
        carsResultsPage = carsDataTrip.clickBuscarBtn();

        Assert.assertTrue(carsResultsPage.vacancy());
        Assert.assertTrue(carsResultsPage.processed());

        List<WebElement> carsChoicesListFirst = carsResultsPage.getCarsChoices();

        loginPopUp = basePage.headerSection().clickMyAccountMenuLnk();
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());

        carsResultsPage = loginPopUp.clickIngresarOnCarstBtn();

        Assert.assertTrue(carsResultsPage.vacancy());
        Assert.assertTrue(carsResultsPage.processed());

        logger.info("Validating cars choices on results page are the same as before login.");
        Assert.assertTrue(carsChoicesListFirst.size() == carsResultsPage.getCarsChoices().size());

        Assert.assertTrue(userNameOk(userData.get("name").toString(), basePage.headerSection().textLoggedIntLnk.getText()));

        checkOutPageV3 = carsResultsPage.clickReservarAhoraBtn(FIRST_OPTION);

        dataManagement.getPassengerData(ADULT_MALE_NATIVE);

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), CARS_CHECKOUT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void tripsAvailabilityLogin(){
        logTestTitle("Search Trips And Login With Email");

        dataManagement.getTripsItineraryData();
        dataManagement.getTripsDataTripItinerary(MIA_10D_2A_2C_1R);

        tripsDataTrip = basePage.clicksTripsBtn();
        tripsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        tripsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        tripsDataTrip.selectDateFromCalendar(tripsDataTrip.departureCalendar, dataManagement.startDate);
        tripsDataTrip.selectDateFromCalendar(tripsDataTrip.arrivalCalendar, dataManagement.endDate);
        tripsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);

        tripsResultsPage = tripsDataTrip.clickBuscarBtn();
        Assert.assertTrue(tripsResultsPage.vacancy());
        List<WebElement> tripsChoicesListFirst = tripsResultsPage.getTripsChoices();

        loginPopUp = basePage.headerSection().clickMyAccountMenuLnk();
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());

        tripsResultsPage = loginPopUp.clickIngresarOnTripstBtn();
        Assert.assertTrue(tripsResultsPage.vacancy());

        logger.info("Validating trips (hotel) choices on results page are the same as before login.");
        Assert.assertTrue(tripsChoicesListFirst.size() == tripsResultsPage.getTripsChoices().size());

        Assert.assertTrue(userNameOk(userData.get("name").toString(), basePage.headerSection().textLoggedIntLnk.getText()));

        tripsResultsPage.clickElegirBtn(FIRST_OPTION);
        tripsDetailPage = tripsResultsPage.clickContinuarBtn();
        tripsDetailPage.clickVerHabitacionBtn();

        dataManagement.getPassengerData(ADULT_FEMALE_FOREIGN);
        dataManagement.getPassengerData(ADULT_FEMALE_FOREIGN);
        dataManagement.getPassengerData(CHILD_MALE_NATIVE);

        checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                MASTER_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }
}