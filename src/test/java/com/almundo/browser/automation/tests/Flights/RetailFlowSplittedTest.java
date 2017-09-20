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

public class RetailFlowSplittedTest extends TestBaseSetup {

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

    private void getAssertionInfo(){
        thanksPageAssertInfo.setFinalAmountPaid(checkOutPageV3.breakDownSectionV3().getFinalPriceString());
        thanksPageAssertInfo.setFlightsDetailInfo(checkOutPageV3.breakDownSectionV3().getFlightDetailContent());
        thanksPageAssertInfo.setContactEmailEntered(checkOutPageV3.contactSection().getContactEmail());
    }

    /***************************** Test Cases *****************************/

    @SuppressWarnings("Duplicates")
    @Test
    public void suc_Int_Booking_Flow_Splitted_2cards() {
        logTestTitle("International - Splitted VISA_MASTER - 20 days - 2 Adults - Tourist");

        if(countryPar.equals(ARGENTINA)) {
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

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, VISA_AMEX,
                    dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                    dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);
            getAssertionInfo();
            thanksPageV3 = checkOutPageV3.clickComprarBtn();

            Assert.assertTrue(thanksPageV3.confirmationOk());
            Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.finalAmountPaid));
            Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.contactEmailEntered));
            Assert.assertTrue(thanksPageV3.isFlightDetailInfoOk(thanksPageAssertInfo.flightDetailInfo));
            Assert.assertTrue(thanksPageV3.isPassengersInfoOk());
        }
        else{
            logger.warn(NOT_RUNNING_MEXICO_COLOMBIA);
        }
        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void suc_Int_Booking_Flow_Splitted_DepositCredit() {
        logTestTitle("International - DEPOSIT_VISA - 20 days - 2 Adults - Tourist");

        if(countryPar.equals(ARGENTINA)) {
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

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, DEPOSIT_VISA,
                    dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                    dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);
            getAssertionInfo();
            thanksPageV3 = checkOutPageV3.clickComprarBtn();

            Assert.assertTrue(thanksPageV3.confirmationOk());
            Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.finalAmountPaid));
            Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.contactEmailEntered));
            Assert.assertTrue(thanksPageV3.isFlightDetailInfoOk(thanksPageAssertInfo.flightDetailInfo));
            Assert.assertTrue(thanksPageV3.isPassengersInfoOk());
        }
        else{
            logger.warn(NOT_RUNNING_MEXICO_COLOMBIA);
        }
        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void suc_Int_Booking_Flow_Splitted_CashDeposit() {
        logTestTitle("International - Splitted CASH_DEPOSIT - 20 days - 2 Adults - Tourist");

        if(countryPar.equals(ARGENTINA)) {
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

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, CASH_DEPOSIT,
                                    dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                                    dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);
            getAssertionInfo();
            thanksPageV3 = checkOutPageV3.clickComprarBtn();

            Assert.assertTrue(thanksPageV3.confirmationOk());
            Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.finalAmountPaid));
            Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.contactEmailEntered));
            Assert.assertTrue(thanksPageV3.isFlightDetailInfoOk(thanksPageAssertInfo.flightDetailInfo));
            Assert.assertTrue(thanksPageV3.isPassengersInfoOk());
        }
        else{
            logger.warn(NOT_RUNNING_MEXICO_COLOMBIA);
        }
        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void suc_Int_Booking_Flow_Splitted_CashTransfer() {
        logTestTitle("International - Splitted CASH_TRANSFER - 20 days - 2 Adults - Tourist");

        if(countryPar.equals(ARGENTINA)) {
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

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, CASH_TRANSFER,
                    dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                    dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT_RET);
            getAssertionInfo();
            thanksPageV3 = checkOutPageV3.clickComprarBtn();

            Assert.assertTrue(thanksPageV3.confirmationOk());
            Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.finalAmountPaid));
            Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.contactEmailEntered));
            Assert.assertTrue(thanksPageV3.isFlightDetailInfoOk(thanksPageAssertInfo.flightDetailInfo));
            Assert.assertTrue(thanksPageV3.isPassengersInfoOk());
        }
        else{
            logger.warn(NOT_RUNNING_MEXICO_COLOMBIA);
        }
        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void suc_Int_Booking_Flow_Splitted_TransferCredit() {
        logTestTitle("International - TRANSFER_VISA - 2 Adults - Tourist");

        if(countryPar.equals(ARGENTINA)) {
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
            logger.warn(NOT_RUNNING_MEXICO_COLOMBIA);
        }
        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void suc_Int_Booking_Flow_Splitted_3cards() {
        logTestTitle("International - 3 Cards - 20 days - 2 Adults - Tourist");

        if(countryPar.equals(ARGENTINA)) {
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
            getAssertionInfo();
            thanksPageV3 = checkOutPageV3.clickComprarBtn();

            Assert.assertTrue(thanksPageV3.confirmationOk());
            Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.finalAmountPaid));
            Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.contactEmailEntered));
            Assert.assertTrue(thanksPageV3.isFlightDetailInfoOk(thanksPageAssertInfo.flightDetailInfo));
            Assert.assertTrue(thanksPageV3.isPassengersInfoOk());
        }
        else{
            logger.warn(NOT_RUNNING_MEXICO_COLOMBIA);
        }
        setResultSauceLabs(PASSED);
    }
}