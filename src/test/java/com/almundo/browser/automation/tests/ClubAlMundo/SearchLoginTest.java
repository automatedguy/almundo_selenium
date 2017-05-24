package com.almundo.browser.automation.tests.ClubAlMundo;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.CarsDataTrip;
import com.almundo.browser.automation.pages.BasePage.FlightsDataTrip;
import com.almundo.browser.automation.pages.BasePage.HotelsDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPageV3.AgreementPage;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ConfirmationPageV3;
import com.almundo.browser.automation.pages.ResultsPage.CarsResultsPage;
import com.almundo.browser.automation.pages.ResultsPage.FlightsResultsPage;
import com.almundo.browser.automation.pages.ResultsPage.HotelsDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.HotelsResultsPage;
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

import static com.almundo.browser.automation.utils.Constants.FIRST_OPTION;
import static com.almundo.browser.automation.utils.Constants.FlightType.ROUND_TRIP;

/**
 * Created by gabrielcespedes on 23/05/17.
 */
public class SearchLoginTest extends TestBaseSetup {

    private LoginPopUp loginPopUp = null;

    private DataManagement dataManagement = new DataManagement();

    /********** Flights Related Objects and Pages ***********/
    private FlightsDataTrip flightsDataTrip = null;
    private FlightsResultsPage flightsResultsPage = null;
    private List<WebElement> flightsChoicesListFirst;

    /********** Hotels Related Objects and Pages ***********/
    private HotelsDataTrip hotelsDataTrip = null;
    private HotelsResultsPage hotelsResultsPage = null;
    private HotelsDetailPage hotelsDetailPage = null;
    private List<WebElement> hotelsChoicesListFirst;

    /********** Cars Related Objects and Pages ***********/
    private CarsDataTrip carsDataTrip =  null;
    private CarsResultsPage carsResultsPage = null;
    private List<WebElement> carsChoicesListFirst;

    /********** Common Objects: Checkout, Agreement and Confirmation Pages ***********/
    private CheckOutPageV3 checkOutPageV3 = null;

    private AgreementPage agreementPage = null;
    private ConfirmationPageV3 confirmationPageV3 = null;

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
        logTestTitle("Club AlMundo - Search Flight And Login With Email - " + countryPar );
        dataManagement.getFlightsItineraryData();
        dataManagement.getRoundTripDataTripItinerary("miami_10days_2adults_2childs_turista");

        flightsDataTrip = basePage.clickFlightsBtn();
        flightsDataTrip.selectFlightType(ROUND_TRIP.toString());
        flightsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull );
        flightsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.departureFlightsCalendar, dataManagement.startDate);
        flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
        flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
        flightsDataTrip.selectClass(dataManagement.flightClass);

        flightsResultsPage = flightsDataTrip.clickBuscarBtn();
        Assert.assertTrue(flightsResultsPage.vacancy());
        flightsChoicesListFirst = flightsResultsPage.getFlightChoices();

        loginPopUp = basePage.headerSection().clickMyAccountMenuLnk();
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());

        flightsResultsPage = loginPopUp.clickIngresarOnFlightBtn();
        Assert.assertTrue(flightsResultsPage.vacancy());

        logger.info("Validating flight choices on results page are the same as before login.");
        Assert.assertTrue(flightsChoicesListFirst.equals(flightsResultsPage.getFlightChoices()));

        logger.info("Validating user name is displayed: [" + userData.get("name").toString() + "]");
        Assert.assertEquals(userData.get("name").toString(), basePage.headerSection().textLnk.getText());

        flightsResultsPage.clickTicketIdaRdb(FIRST_OPTION);
        flightsResultsPage.clickTicketVuelta(FIRST_OPTION+1);

        dataManagement.getPassengerData("adult_female_foreign");
        dataManagement.getPassengerData("adult_female_foreign");
        dataManagement.getPassengerData("child_male_native");
        dataManagement.getPassengerData("child_male_native");

        checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "FlightsCheckOutPageInternational");

        agreementPage = checkOutPageV3.termAndConditionsClick();
        Assert.assertTrue(agreementPage.agreementUrlOk());
        Assert.assertTrue(agreementPage.agreementOk());
        agreementPage.closeAgreementPage();

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
    }

    @Test
    public void hotelsAvailabilityLogin(){
        logTestTitle("Club AlMundo - Search Hotel And Login With Email - " + countryPar );

        dataManagement.getHotelsItineraryData();
        dataManagement.getHotelsDataTripItinerary("miami_10days_2adults_2childs_1room");

        hotelsDataTrip = basePage.hotelsDataTrip();
        hotelsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.startDate);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);
        hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
        hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

        Assert.assertTrue(hotelsResultsPage.vacancy());
        hotelsChoicesListFirst = hotelsResultsPage.getHotelChoices();

        loginPopUp = basePage.headerSection().clickMyAccountMenuLnk();
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());

        hotelsResultsPage = loginPopUp.clickIngresarOnHotelstBtn();
        Assert.assertTrue(hotelsResultsPage.vacancy());

        logger.info("Validating hotels choices on results page are the same as before login.");
        Assert.assertTrue(hotelsChoicesListFirst.size() == hotelsResultsPage.getHotelChoices().size());

        logger.info("Validating user name is displayed: [" + userData.get("name").toString() + "]");
        Assert.assertEquals(userData.get("name").toString(), basePage.headerSection().textLnk.getText());

        hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

        PageUtils.switchToNewTab(driver);
        hotelsDetailPage.clickVerHabitacionesBtn();

        dataManagement.getPassengerData("adult_female_native");
        dataManagement.getPassengerData("adult_female_native");
        dataManagement.getPassengerData("child_female_native");
        dataManagement.getPassengerData("child_female_native");

        checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "random",
                dataManagement.getBillingData("local_Billing"),
                dataManagement.getContactData("contact_cell_phone"),
                "HotelsCheckOutPageInternationalV3");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
    }
}
