package com.almundo.browser.automation.tests.Flights;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.FlightsDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPage.ConfirmationPage;
import com.almundo.browser.automation.pages.ResultsPage.FlightsResultsPage;
import org.json.simple.JSONArray;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.FlightType.MULTIDESTINATION;
import static com.almundo.browser.automation.utils.Constants.FlightType.ONE_WAY;
import static com.almundo.browser.automation.utils.Constants.FlightType.ROUND_TRIP;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class FlowTest extends TestBaseSetup {

    private FlightsResultsPage flightsResultsPage = null;
    private CheckOutPage checkOutPage = null;
    private ConfirmationPage confirmationPage = null;

    private FlightsDataTrip flightsDataTrip = null;
    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initDataLists() {
        dataManagement.getFlightsDataTripList();
        dataManagement.getPassengersList();
        dataManagement.getPaymentList();
        dataManagement.getBillingList();
        dataManagement.getContactList();
    }

    @BeforeMethod
    private void closeLoginPopUp(){
        LoginPopUp loginPopUp = initLoginPopUp();
        loginPopUp.clickCloseLoginBtn();
        basePage.clickFlightsBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void oneWay_Int_Booking_Flow() {
        logTestTitle("Flight Flow - One Way - Int - 2 Adults/2 Childs - Turista - " + countryPar );

        dataManagement.getOneWayDataTripItinerary("miami_10days_2adults_2childs_turista");

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.selectFlightType(ONE_WAY.toString());
        flightsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull );
        flightsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.departureFlightsCalendar, dataManagement.startDate);
        flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
        flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
        flightsDataTrip.selectClass(dataManagement.flightClass);
        flightsResultsPage = flightsDataTrip.clickBuscarBtn();

        Assert.assertTrue(flightsResultsPage.vacancy());
        flightsResultsPage.clickTicketIdaRdb();
        checkOutPage = flightsResultsPage.clickComprarBtn(0);

        dataManagement.getPassengerData("adult_male_native");
        dataManagement.getPassengerData("adult_female_native");
        dataManagement.getPassengerData("child_male_native");
        dataManagement.getPassengerData("child_male_native");

        checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                                          dataManagement.getPaymentData("1_amex_amex"),
                                          dataManagement.getBillingData("local_Billing_v2"),
                                          dataManagement.getContactData("contact_cell_phone"),
                                          "FlightsCheckOutPageInternational");
    }

    @Test
    public void oneWay_Dom_Booking_Flow() {
        logTestTitle("Flight Flow - One Way - Dom - 2 Adults - Todas - " + countryPar );

        dataManagement.getOneWayDataTripItinerary("domestic_20days_2adults_todas");

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.selectFlightType(ONE_WAY.toString());
        flightsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        flightsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.departureFlightsCalendar, dataManagement.startDate);
        flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
        flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
        flightsDataTrip.selectClass(dataManagement.flightClass);
        flightsResultsPage = flightsDataTrip.clickBuscarBtn();

        Assert.assertTrue(flightsResultsPage.vacancy());
        flightsResultsPage.clickTicketIdaRdb();
        checkOutPage = flightsResultsPage.clickComprarBtn(0);

        dataManagement.getPassengerData("adult_female_foreign");
        dataManagement.getPassengerData("adult_female_foreign");

        checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                                          dataManagement.getPaymentData("1_amex_amex"),
                                          dataManagement.getBillingData("local_Billing_v2"),
                                          dataManagement.getContactData("contact_phone"),
                                          "FlightsCheckOutPageDomestic");
    }

    @Test
    public void roundTrip_Int_Booking_Flow() {
        logTestTitle("Flight Flow - Round Trip - Int - 10 days - 2 Adults/2 Childs - Turista - " + countryPar );

        dataManagement.getRoundTripDataTripItinerary("miami_10days_2adults_2childs_turista");

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.selectFlightType(ROUND_TRIP.toString());
        flightsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        flightsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.departureFlightsCalendar, dataManagement.startDate);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.arrivalFlightsCalendar, dataManagement.endDate);
        flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
        flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
        flightsDataTrip.selectClass(dataManagement.flightClass);
        flightsResultsPage = flightsDataTrip.clickBuscarBtn();

        Assert.assertTrue(flightsResultsPage.vacancy());
        flightsResultsPage.clickTicketIdaRdb();
        flightsResultsPage.clickTicketVuelta();
        checkOutPage = flightsResultsPage.clickComprarBtn(0);

        dataManagement.getPassengerData("adult_male_native");
        dataManagement.getPassengerData("adult_female_native");
        dataManagement.getPassengerData("child_male_native");
        dataManagement.getPassengerData("child_male_native");

        checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                                          dataManagement.getPaymentData("1_amex_amex"),
                                          dataManagement.getBillingData("local_Billing_v2"),
                                          dataManagement.getContactData("contact_cell_phone"),
                                          "FlightsCheckOutPageInternational");
    }

    @Test
    public void roundTrip_Dom_Booking_Flow() {
        logTestTitle("Flight Flow - Round Trip - Dom - 20 days - 2 Adults - Todas - " + countryPar );

        dataManagement.getRoundTripDataTripItinerary("domestic_20days_2adults_todas");

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.selectFlightType(ROUND_TRIP.toString());
        flightsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        flightsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.departureFlightsCalendar, dataManagement.startDate);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.arrivalFlightsCalendar, dataManagement.endDate);
        flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
        flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
        flightsDataTrip.selectClass(dataManagement.flightClass);
        flightsResultsPage = flightsDataTrip.clickBuscarBtn();

        Assert.assertTrue(flightsResultsPage.vacancy());
        flightsResultsPage.clickTicketIdaRdb();
        flightsResultsPage.clickTicketVuelta();
        checkOutPage = flightsResultsPage.clickComprarBtn(0);

        dataManagement.getPassengerData("adult_female_foreign");
        dataManagement.getPassengerData("adult_female_foreign");

        checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                                          dataManagement.getPaymentData("1_amex_amex"),
                                          dataManagement.getBillingData("local_Billing_v2"),
                                          dataManagement.getContactData("contact_phone"),
                                          "FlightsCheckOutPageDomestic");
    }

    @Test
    public void multiDest_Int_Booking_Flow() {
        logTestTitle("Flight Flow - Multi Destination - Int - 2 Adults - Turista - " + countryPar );

        dataManagement.getMultiDestDataTripItinerary("multiDest_3Flights_2adults_todas");

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.selectFlightType(MULTIDESTINATION.toString());
        flightsDataTrip.clickAddLegLnk();
        flightsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull );
        flightsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.departureFlightsCalendar, dataManagement.startDate);
        flightsDataTrip.setMultiDestFlight(dataManagement.originAuto2, dataManagement.originFull2, "origin-flights-0");
        flightsDataTrip.setMultiDestFlight(dataManagement.destinationAuto2, dataManagement.destinationFull2, "destination-flights-0");
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.departureFlights0Calendar, dataManagement.startDate2);
        flightsDataTrip.setMultiDestFlight(dataManagement.originAuto3, dataManagement.originFull3, "origin-flights-1");
        flightsDataTrip.setMultiDestFlight(dataManagement.destinationAuto3, dataManagement.destinationFull3, "destination-flights-1");
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.departureFlights1Calendar, dataManagement.startDate3);
        flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
        flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
        flightsDataTrip.selectClass(dataManagement.flightClass);
        flightsResultsPage = flightsDataTrip.clickBuscarBtn();

        Assert.assertTrue(flightsResultsPage.vacancy());
        checkOutPage = flightsResultsPage.clickComprarBtn(0);

        dataManagement.getPassengerData("adult_male_native");
        dataManagement.getPassengerData("adult_female_native");

        checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                                          dataManagement.getPaymentData("1_amex_amex"),
                                          dataManagement.getBillingData("local_Billing_v2"),
                                          dataManagement.getContactData("contact_cell_phone"),
                                          "FlightsCheckOutPageInternational");
    }

}