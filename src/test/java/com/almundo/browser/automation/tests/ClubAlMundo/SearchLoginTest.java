package com.almundo.browser.automation.tests.ClubAlMundo;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.FlightsDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.ResultsPage.FlightsResultsPage;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.FIRST_OPTION;
import static com.almundo.browser.automation.utils.Constants.FlightType.ROUND_TRIP;

/**
 * Created by gabrielcespedes on 23/05/17.
 */
public class SearchLoginTest extends TestBaseSetup {

    private LoginPopUp loginPopUp = null;

    private DataManagement dataManagement = new DataManagement();
    private FlightsDataTrip flightsDataTrip = null;
    private FlightsResultsPage flightsResultsPage = null;
    private CheckOutPageV3 checkOutPageV3 = null;

    JSONObject userData = null;

    @BeforeClass
    private void initUserList() {
        dataManagement.getUsersDataList();
        dataManagement.getFlightsItineraryData();
        userData = dataManagement.getUserData("email");
    }

    @BeforeMethod
    private void closeLoginPopUp(){
        loginPopUp = initLoginPopUp();
        loginPopUp.clickCloseLoginBtn();
    }

    @Test
    public void searchFlightAndLogin(){
        logTestTitle("Club AlMundo - Search Flight And Login With Email - " + countryPar );

        dataManagement.getOneWayDataTripItinerary("miami_10days_2adults_2childs_turista");

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

        loginPopUp = basePage.headerSection().clickMyAccountMenuLnk();
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());

        flightsResultsPage = loginPopUp.clickIngresarOnFlightBtn();
        Assert.assertTrue(flightsResultsPage.vacancy());

        logger.info("Validating user name is displayed: [" + userData.get("name").toString() + "]");
        Assert.assertEquals(userData.get("name").toString(), basePage.headerSection().textLnk.getText());

        flightsResultsPage.clickTicketIdaRdb(FIRST_OPTION);
        flightsResultsPage.clickTicketVuelta(FIRST_OPTION+1);

        dataManagement.getPassengerData("adult_female_foreign");
        dataManagement.getPassengerData("adult_female_foreign");
        dataManagement.getPassengerData("child_male_native");
        dataManagement.getPassengerData("child_male_native");

        checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);

    }
}
