package com.almundo.browser.automation.tests.Flights;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.FlightsDataTrip;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPage.ConfirmationPage;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.ResultsPage.FlightsResultsPage;
import org.json.simple.JSONArray;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.FIRST_OPTION;
import static com.almundo.browser.automation.utils.Constants.Results.PASSED;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class SucursalesFlowTest extends TestBaseSetup {

    private FlightsResultsPage flightsResultsPage = null;
    private CheckOutPage checkOutPage = null;
    private CheckOutPageV3 checkOutPageV3 = null;
    private ConfirmationPage confirmationPage = null;

    private FlightsDataTrip flightsDataTrip = null;
    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initItineraryData() {
        dataManagement.getFlightsItineraryData();
    }

    @BeforeMethod
    private void clickFlightsBtn() {
        basePage.clickFlightsBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void suc_Dom_Booking_Flow() {
        logTestTitle("Sucursales Flight Flow - Domestic - 20 days - 2 Adults - Todas - " + countryPar );

        dataManagement.getRoundTripDataTripItinerary("domestic_30days_2adults_todas");

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        flightsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.departureFlightsCalendar, dataManagement.startDate);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.arrivalFlightsCalendar, dataManagement.endDate);
        flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
        flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
        flightsDataTrip.selectClass(dataManagement.flightClass);
        flightsResultsPage = flightsDataTrip.clickBuscarBtn();

        Assert.assertTrue(flightsResultsPage.vacancy());
        flightsResultsPage.clickTicketIdaRdb(FIRST_OPTION);
        flightsResultsPage.clickTicketVuelta(FIRST_OPTION+1);
        checkOutPage = flightsResultsPage.clickComprarBtn(FIRST_OPTION);

        dataManagement.getPassengerData("adult_female_foreign");
        dataManagement.getPassengerData("adult_female_foreign");

        checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                                          dataManagement.getPaymentData("deposit"),
                                          dataManagement.getBillingData("local_Billing_sucursales"),
                                          dataManagement.getContactData("contact_phone"),
                                          "FlightsCheckOutPageDomesticSucursal", true);
        setResultSauceLabs(PASSED);
    }

    @Test
    public void suc_Int_Booking_Flow() {
        logTestTitle("Sucursales Flight Flow - International - 20 days - 2 Adults - Todas - " + countryPar );

        dataManagement.getRoundTripDataTripItinerary("miami_10days_2adults_turista");

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        flightsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.departureFlightsCalendar, dataManagement.startDate);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.arrivalFlightsCalendar, dataManagement.endDate);
        flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
        flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
        flightsDataTrip.selectClass(dataManagement.flightClass);
        flightsResultsPage = flightsDataTrip.clickBuscarBtn();

        Assert.assertTrue(flightsResultsPage.vacancy());
        flightsResultsPage.clickTicketIdaRdb(FIRST_OPTION);
        flightsResultsPage.clickTicketVuelta(FIRST_OPTION+1);
        checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);

        dataManagement.getPassengerData("adult_male_native");
        dataManagement.getPassengerData("adult_female_native");

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing_sucursales"),
                dataManagement.getContactData("contact_phone"),
                "FlightsCheckOutPageInternationalSucursal");

        setResultSauceLabs(PASSED);
    }
}