package com.almundo.browser.automation.tests.ClubAlMundo;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.*;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ThanksPageV3;
import com.almundo.browser.automation.pages.ResultsPage.*;
import com.almundo.browser.automation.utils.PageUtils;
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
    private ThanksPageV3 thanksPageV3 = null;

    JSONObject userData = null;

    private void getFlightsAssertionInfo(){
        thanksPageAssertInfo.setFlightsDetailInfo(checkOutPageV3.breakDownSectionV3().getFlightDetailContent());
        thanksPageAssertInfo.setContactEmailEntered(checkOutPageV3.contactSection().getContactEmail());
    }

    @BeforeClass
    private void initUserList() {
        dataManagement.setUsersDataList();
        userData = dataManagement.setUserData("email");
    }

    @BeforeMethod
    private void closeLoginPopUp(){
        loginPopUp = initLoginPopUp();
        loginPopUp.clickCloseLoginBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.clearPassengerJsonList();
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void flightsAvailabilityLogin(){
        logTestTitle("Search Flight And Login With Email");

        dataManagement.setFlightsItineraryData();
        dataManagement.setRoundTripDataTripItinerary(MIAMI_10D_2A_2C_TOURIST);

        flightsDataTrip = basePage.clickFlightsBtn();
        flightsDataTrip.selectFlightType(ROUND_TRIP);
        flightsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull() );
        flightsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
        flightsDataTrip.setDate(flightsDataTrip.getDepartureFlightsCalendar(), dataManagement.getStartDate());
        flightsDataTrip.setDate(flightsDataTrip.getArrivalFlightsCalendar(), dataManagement.getEndDate());
        flightsDataTrip.selectPassenger(dataManagement.getAdults(), dataManagement.getChilds());
        flightsDataTrip.selectChildAgeRange(dataManagement.getChildAgeRange(), dataManagement.getChilds());
        flightsDataTrip.selectClass(dataManagement.getFlightClass());

        flightsResultsPage = flightsDataTrip.clickBuscarBtn();
        Assert.assertTrue(flightsResultsPage.vacancy());
        List<WebElement> flightsChoicesListFirst = flightsResultsPage.getFlightsChoices();

        loginPopUp = basePage.headerSection().clickMyAccountMenuLnk();
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());

        flightsResultsPage = loginPopUp.clickIngresarOnFlightBtn();
        Assert.assertTrue(flightsResultsPage.vacancy());

        logger.info("Validating flight choices on results page are the same as before login.");
        Assert.assertTrue(flightsChoicesListFirst.equals(flightsResultsPage.getFlightsChoices()));

        // Assert.assertTrue(userNameOk(userData.get("name").toString(), basePage.headerSection().textLoggedIntLnk.getText()));

        flightsResultsPage.clickTicketIdaRdb(FIRST_OPTION);
        flightsResultsPage.clickTicketVuelta(FIRST_OPTION+1);

        dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);
        dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);
        dataManagement.setPassengerData(CHILD_MALE_NATIVE);
        dataManagement.setPassengerData(CHILD_MALE_NATIVE);

        checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void flightsAvailabilityLoginBooking(){
        logTestTitle("Search Flight - Login With Email On Result Page - And book using Rewards Points");
        if(countryPar.equals(ARGENTINA)) {
            dataManagement.setFlightsItineraryData();
            dataManagement.setRoundTripDataTripItinerary(MIAMI_10D_2A_2C_TOURIST);

            flightsDataTrip = basePage.clickFlightsBtn();
            flightsDataTrip.selectFlightType(ROUND_TRIP);
            flightsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
            flightsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
            flightsDataTrip.setDate(flightsDataTrip.getDepartureFlightsCalendar(), dataManagement.getStartDate());
            flightsDataTrip.setDate(flightsDataTrip.getArrivalFlightsCalendar(), dataManagement.getEndDate());
            flightsDataTrip.selectPassenger(dataManagement.getAdults(), dataManagement.getChilds());
            flightsDataTrip.selectChildAgeRange(dataManagement.getChildAgeRange(), dataManagement.getChilds());
            flightsDataTrip.selectClass(dataManagement.getFlightClass());

            flightsResultsPage = flightsDataTrip.clickBuscarBtn();
            Assert.assertTrue(flightsResultsPage.vacancy());
            List<WebElement> flightsChoicesListFirst = flightsResultsPage.getFlightsChoices();

            loginPopUp = basePage.headerSection().clickMyAccountMenuLnk();
            loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());

            flightsResultsPage = loginPopUp.clickIngresarOnFlightBtn();
            Assert.assertTrue(flightsResultsPage.vacancy());

            logger.info("Validating flight choices on results page are the same as before login.");
            Assert.assertTrue(flightsChoicesListFirst.equals(flightsResultsPage.getFlightsChoices()));

            // Assert.assertTrue(userNameOk(userData.get("name").toString(), basePage.headerSection().textLoggedIntLnk.getText()));

            flightsResultsPage.clickTicketIdaRdb(FIRST_OPTION);
            flightsResultsPage.clickTicketVuelta(FIRST_OPTION + 1);

            dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);
            dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);
            dataManagement.setPassengerData(CHILD_MALE_NATIVE);
            dataManagement.setPassengerData(CHILD_MALE_NATIVE);

            checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);

            thanksPageAssertInfo.setFinalAmountPaid(checkOutPageV3.breakDownSectionV3().getFinalPriceString());

            checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                    MASTER_1, dataManagement.getBillingData(LOCAL_BILLING),
                    dataManagement.getContactData(CONTACT_CELL_PHONE), FLIGHTS_CHECKOUT_INT);

            getFlightsAssertionInfo();
            thanksPageV3 = checkOutPageV3.clickComprarBtn();

            Assert.assertTrue(thanksPageV3.confirmationOk());
            Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
            Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
            Assert.assertTrue(thanksPageV3.isFlightDetailInfoOk(thanksPageAssertInfo.getFlightDetailInfo()));
            Assert.assertTrue(thanksPageV3.isPassengersInfoOk());
        }else {
            logger.info(NOT_RUNNING_MEXICO_COLOMBIA);
        }
        setResultSauceLabs(PASSED);
    }

    @Test
    public void hotelsAvailabilityLogin(){
        logTestTitle("Search Hotel And Login With Email");

        dataManagement.setHotelsItineraryData();
        dataManagement.setHotelsDataTripItinerary(MIA_10D_2A_2C_1R);

        basePage.clickHotelsBtn();

        hotelsDataTrip = basePage.hotelsDataTrip();
        hotelsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
        hotelsDataTrip.setDate(hotelsDataTrip.getCheckinCalendar(), dataManagement.getStartDate());
        hotelsDataTrip.setDate(hotelsDataTrip.getCheckoutCalendar(), dataManagement.getEndDate());
        hotelsDataTrip.selectPassenger(dataManagement.getAdults(), dataManagement.getChilds(), dataManagement.getRooms());

        hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

        Assert.assertTrue(hotelsResultsPage.vacancy());
        List<WebElement> hotelsChoicesListFirst = hotelsResultsPage.getHotelsChoices();

        loginPopUp = basePage.headerSection().clickMyAccountMenuLnk();
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());

        hotelsResultsPage = loginPopUp.clickIngresarOnHotelstBtn();
        Assert.assertTrue(hotelsResultsPage.vacancy());

        logger.info("Validating hotels choices on results page are the same as before login.");
        Assert.assertTrue(hotelsChoicesListFirst.size() == hotelsResultsPage.getHotelsChoices().size());

        // Assert.assertTrue(userNameOk(userData.get("name").toString(), basePage.headerSection().textLoggedIntLnk.getText()));

        hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

        PageUtils.switchToNewTab(driver);
        hotelsDetailPage.clickVerHabitacionesBtn();

        dataManagement.setPassengerData(ADULT_FEMALE_NATIVE);
        dataManagement.setPassengerData(ADULT_FEMALE_NATIVE);
        dataManagement.setPassengerData(CHILD_FEMALE_NATIVE);
        dataManagement.setPassengerData(CHILD_FEMALE_NATIVE);

        checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);
        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                            MASTER_1, dataManagement.getBillingData(LOCAL_BILLING),
                            dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void carsAvailabilityLogin(){
        logTestTitle("Search Car And Login With Email");

        dataManagement.setCarsItineraryData();
        dataManagement.setCarsDataTripItinerary(MIA_10D_21_24);

        basePage.clickCarsBtn();
        carsDataTrip = basePage.carsDataTrip();
        carsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
        carsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
        carsDataTrip.setDate(carsDataTrip.getPickUpDateCalendar(), dataManagement.getStartDate());
        carsDataTrip.setDate(carsDataTrip.getDropOffDateCalendar(), dataManagement.getEndDate());
        carsDataTrip.selectPickUpTime(dataManagement.getPickUpTime());
        carsDataTrip.selectDropOffTime(dataManagement.getDropOffTime());
        carsDataTrip.selectAgeRange(dataManagement.getAgeRange());
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

        // Assert.assertTrue(userNameOk(userData.get("name").toString(), basePage.headerSection().textLoggedIntLnk.getText()));

        checkOutPageV3 = carsResultsPage.clickReservarAhoraBtn(FIRST_OPTION);

        dataManagement.setPassengerData(ADULT_MALE_NATIVE);

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), CARS_CHECKOUT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void tripsAvailabilityLogin(){
        logTestTitle("Search Trips And Login With Email");

        dataManagement.setTripsItineraryData();
        dataManagement.setTripsDataTripItinerary(MIA_10D_2A_2C_1R);

        tripsDataTrip = basePage.clicksTripsBtn();
        tripsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
        tripsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
        tripsDataTrip.setDate(tripsDataTrip.getDepartureCalendar(), dataManagement.getStartDate());
        tripsDataTrip.setDate(tripsDataTrip.getArrivalCalendar(), dataManagement.getEndDate());
        tripsDataTrip.setPassengers(dataManagement.getAdults(), dataManagement.getChilds(), dataManagement.getRooms());

        tripsResultsPage = tripsDataTrip.clickBuscarBtn();
        Assert.assertTrue(tripsResultsPage.vacancy());
        List<WebElement> tripsChoicesListFirst = tripsResultsPage.getTripsChoices();

        loginPopUp = basePage.headerSection().clickMyAccountMenuLnk();
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());

        tripsResultsPage = loginPopUp.clickIngresarOnTripstBtn();
        Assert.assertTrue(tripsResultsPage.vacancy());

        logger.info("Validating trips (hotel) choices on results page are the same as before login.");
        Assert.assertTrue(tripsChoicesListFirst.size() == tripsResultsPage.getTripsChoices().size());

        // Assert.assertTrue(userNameOk(userData.get("name").toString(), basePage.headerSection().textLoggedIntLnk.getText()));

        tripsResultsPage.clickElegirBtn(FIRST_OPTION);
        tripsDetailPage = tripsResultsPage.clickContinuarBtn();
        tripsDetailPage.clickVerHabitacionBtn();

        dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);
        dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);
        dataManagement.setPassengerData(CHILD_MALE_NATIVE);

        checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);
        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                MASTER_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }
}