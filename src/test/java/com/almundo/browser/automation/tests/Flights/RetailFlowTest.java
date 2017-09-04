package com.almundo.browser.automation.tests.Flights;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.FlightsDataTrip;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ThanksPageV3;
import com.almundo.browser.automation.pages.ResultsPage.FlightsResultsPage;
import org.json.simple.JSONArray;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.*;
import static com.almundo.browser.automation.utils.Constants.FlightType.ROUND_TRIP;
import static com.almundo.browser.automation.utils.Constants.Results.PASSED;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class RetailFlowTest extends TestBaseSetup {

    private FlightsResultsPage flightsResultsPage = null;
    private CheckOutPageV3 checkOutPageV3 = null;
    private ThanksPageV3 thanksPageV3 = null;

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

    /***************************** Test Cases *****************************/

    @Test
    public void suc_Dom_Booking_Flow() {
        logTestTitle("Domestic - 20 days - 2 Adults - All");

        dataManagement.getRoundTripDataTripItinerary(DOMESTIC_30D_2A_ALL);

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.selectFlightType(ROUND_TRIP);
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

        dataManagement.getPassengerData(ADULT_FEMALE_FOREIGN);
        dataManagement.getPassengerData(ADULT_FEMALE_FOREIGN);

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, VISA_1,
                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_DOM_RET);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void suc_Int_Booking_Flow() {
        logTestTitle("International - 20 days - 2 Adults - Tourist");

        dataManagement.getRoundTripDataTripItinerary(MIAMI_10D_2A_TOURIST);

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.selectFlightType(ROUND_TRIP);
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

        dataManagement.getPassengerData(ADULT_MALE_NATIVE);
        dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, VISA_1,
                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void suc_Int_Booking_Flow_Splitted_2cards() {
        logTestTitle("International - Splitted VISA_MASTER - 20 days - 2 Adults - Tourist");

        if(!countryPar.equals(MEXICO)) {
            dataManagement.getRoundTripDataTripItinerary(MIAMI_10D_2A_TOURIST);

            flightsDataTrip = basePage.flightsDataTrip();
            flightsDataTrip.selectFlightType(ROUND_TRIP);
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
            flightsResultsPage.clickTicketVuelta(FIRST_OPTION + 1);

            checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);

            dataManagement.getPassengerData(ADULT_MALE_NATIVE);
            dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, VISA_MASTER,
                    dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                    dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

            thanksPageV3 = checkOutPageV3.clickComprarBtn();
            Assert.assertTrue(thanksPageV3.confirmationOk());
        }
        else{
            logger.warn(NOT_RUNNING_MEXICO);
        }
        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void suc_Int_Booking_Flow_Splitted_DepositCredit() {
        logTestTitle("International - DEPOSIT_MASTER - 20 days - 2 Adults - Tourist");

        if(!countryPar.equals(MEXICO)) {
            dataManagement.getRoundTripDataTripItinerary(MIAMI_10D_2A_TOURIST);

            flightsDataTrip = basePage.flightsDataTrip();
            flightsDataTrip.selectFlightType(ROUND_TRIP);
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
            flightsResultsPage.clickTicketVuelta(FIRST_OPTION + 1);

            checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);

            dataManagement.getPassengerData(ADULT_MALE_NATIVE);
            dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, DEPOSIT_MASTER,
                    dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                    dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

            thanksPageV3 = checkOutPageV3.clickComprarBtn();
            Assert.assertTrue(thanksPageV3.confirmationOk());
        }
        else{
            logger.warn(NOT_RUNNING_MEXICO);
        }
        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void suc_Int_Booking_Flow_Splitted_TransferCredit() {
        logTestTitle("International - TRANSFER_VISA - 2 Adults - Tourist");

        if(!countryPar.equals(MEXICO)) {
            dataManagement.getRoundTripDataTripItinerary(MIAMI_10D_2A_TOURIST);

            flightsDataTrip = basePage.flightsDataTrip();
            flightsDataTrip.selectFlightType(ROUND_TRIP);
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
            flightsResultsPage.clickTicketVuelta(FIRST_OPTION + 1);

            checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);

            dataManagement.getPassengerData(ADULT_MALE_NATIVE);
            dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, TRANSFER_VISA,
                    dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                    dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

            thanksPageV3 = checkOutPageV3.clickComprarBtn();
            Assert.assertTrue(thanksPageV3.confirmationOk());
        }
        else{
            logger.warn(NOT_RUNNING_MEXICO);
        }
        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void suc_Int_Booking_Flow_Splitted_3cards() {
        logTestTitle("International - 3 Cards - 20 days - 2 Adults - Tourist");

        if(!countryPar.equals(MEXICO)) {
            dataManagement.getRoundTripDataTripItinerary(MIAMI_10D_2A_TOURIST);

            flightsDataTrip = basePage.flightsDataTrip();
            flightsDataTrip.selectFlightType(ROUND_TRIP);
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
            flightsResultsPage.clickTicketVuelta(FIRST_OPTION + 1);

            checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);

            dataManagement.getPassengerData(ADULT_MALE_NATIVE);
            dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, AMEX_VISA_MASTER,
                    dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                    dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);

            thanksPageV3 = checkOutPageV3.clickComprarBtn();
            Assert.assertTrue(thanksPageV3.confirmationOk());
        }
        else{
            logger.warn(NOT_RUNNING_MEXICO);
        }
        setResultSauceLabs(PASSED);
    }
}