package com.almundo.browser.automation.tests.Trips;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.BasePage.TripsDataTrip;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ThanksPageV3;
import com.almundo.browser.automation.pages.ResultsPage.TripsDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.TripsResultsPage;
import org.json.simple.JSONArray;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.*;
import static com.almundo.browser.automation.utils.Constants.Results.PASSED;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class FlowTest extends TestBaseSetup {

    private DataManagement dataManagement = new DataManagement();
    private TripsDataTrip tripsDataTrip = null;
    private TripsResultsPage tripsResultsPage = null;
    private TripsDetailPage tripsDetailPage = null;
    private CheckOutPageV3 checkOutPageV3 = null;
    private ThanksPageV3 thanksPageV3 = null;

    @BeforeClass
    private void initItineraryData() {
        dataManagement.setTripsItineraryData();
    }

    @BeforeMethod
    private void closeLoginPopUp(){
        LoginPopUp loginPopUp = initLoginPopUp();
        loginPopUp.clickCloseLoginBtn();
        tripsDataTrip = basePage.clicksTripsBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void int_Booking_Flow(){
        logTestTitle("International - 10 days - 2 Adults/2 Childs - 1 Room");

        dataManagement.setTripsDataTripItinerary(MIA_10D_2A_2C_1R);

        tripsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
        tripsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
        tripsDataTrip.selectDateFromCalendar(tripsDataTrip.departureCalendar, dataManagement.getStartDate());
        tripsDataTrip.selectDateFromCalendar(tripsDataTrip.arrivalCalendar, dataManagement.endDate);
        tripsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
        tripsResultsPage = tripsDataTrip.clickBuscarBtn();

        Assert.assertTrue(tripsResultsPage.vacancy());
        tripsResultsPage.clickElegirBtn(FIRST_OPTION);
        tripsDetailPage = tripsResultsPage.clickContinuarBtn();
        tripsDetailPage.clickVerHabitacionBtn();

        dataManagement.setPassengerData(ADULT_MALE_NATIVE);
        dataManagement.setPassengerData(ADULT_FEMALE_NATIVE);
        dataManagement.setPassengerData(CHILD_MALE_NATIVE);
        dataManagement.setPassengerData(CHILD_FEMALE_NAT_TRIPS);

        checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, VISA_1,
                                              dataManagement.setBillingData(LOCAL_BILLING_SUCURSALES),
                                              dataManagement.setContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void int_Booking_Flow_with2Cards(){
        logTestTitle("International - 10 days - 2 Adults/2 Childs - 1 Room - VISA_1 and MASTER_1");

        dataManagement.setTripsDataTripItinerary(MIA_10D_2A_2C_1R);

        tripsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
        tripsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
        tripsDataTrip.selectDateFromCalendar(tripsDataTrip.departureCalendar, dataManagement.getStartDate());
        tripsDataTrip.selectDateFromCalendar(tripsDataTrip.arrivalCalendar, dataManagement.endDate);
        tripsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
        tripsResultsPage = tripsDataTrip.clickBuscarBtn();

        Assert.assertTrue(tripsResultsPage.vacancy());
        tripsResultsPage.clickElegirBtn(FIRST_OPTION);
        tripsDetailPage = tripsResultsPage.clickContinuarBtn();
        tripsDetailPage.clickVerHabitacionBtn();

        dataManagement.setPassengerData(ADULT_MALE_NATIVE);
        dataManagement.setPassengerData(ADULT_MALE_NATIVE);
        dataManagement.setPassengerData(CHILD_MALE_NATIVE);
        dataManagement.setPassengerData(CHILD_MALE_NATIVE);

        checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, VISA_1, MASTER_1,
                dataManagement.setBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.setContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void dom_Booking_Flow(){
        logTestTitle("Domestic - 15 days - 2 Adults/1 Child - 1 Room");

        dataManagement.setTripsDataTripItinerary(DOM01_15D_2A_1C_1R);

        tripsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
        tripsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
        tripsDataTrip.selectDateFromCalendar(tripsDataTrip.departureCalendar, dataManagement.getStartDate());
        tripsDataTrip.selectDateFromCalendar(tripsDataTrip.arrivalCalendar, dataManagement.endDate);
        tripsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
        tripsResultsPage = tripsDataTrip.clickBuscarBtn();

        Assert.assertTrue(tripsResultsPage.vacancy());

        tripsResultsPage.clickElegirBtn(FIRST_OPTION);
        tripsDetailPage = tripsResultsPage.clickContinuarBtn();
        tripsDetailPage.clickVerHabitacionBtn();

        dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);
        dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);
        dataManagement.setPassengerData(CHILD_FEM_NAT_TRIPS_DOM);

        checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, MASTER_1,
                                              dataManagement.setBillingData(LOCAL_BILLING_SUCURSALES),
                                              dataManagement.setContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_DOMV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }
}