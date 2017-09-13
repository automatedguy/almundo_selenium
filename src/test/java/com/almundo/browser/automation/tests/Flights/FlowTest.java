package com.almundo.browser.automation.tests.Flights;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.FlightsDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPageV3.AgreementPage;
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
import static com.almundo.browser.automation.utils.Constants.FlightType.*;
import static com.almundo.browser.automation.utils.Constants.Results.PASSED;


/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class FlowTest extends TestBaseSetup {

    private FlightsResultsPage flightsResultsPage = null;
    private CheckOutPageV3 checkOutPageV3 = null;
    private ThanksPageV3 thanksPageV3 = null;
    private AgreementPage agreementPage = null;

    private FlightsDataTrip flightsDataTrip = null;
    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initItineraryData() {
        dataManagement.getFlightsItineraryData();
    }

    @BeforeMethod
    private void closeLoginPopUp(){
        LoginPopUp loginPopUp = initLoginPopUp();
        loginPopUp.clickCloseLoginBtn();
        flightsDataTrip = basePage.clickFlightsBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    private void validateTermsAndConditions(){
        if(!baseURL.contains("st.almundo.com")) {
            agreementPage = checkOutPageV3.termAndConditionsClick();
            Assert.assertTrue(agreementPage.agreementUrlOk());
            Assert.assertTrue(agreementPage.agreementOk());
            agreementPage.closeAgreementPage();
        }
        else{
            logger.warn("We are not validating Terms and Conditions against STG due to the bug below :)");
            logger.info(" This is the bug: https://almundo.atlassian.net/browse/MA-1277");
        }
    }

    private void getAssertionInfo(){
        thanksPageAssertInfo.finalAmountPaid = checkOutPageV3.breakDownSectionV3().getFinalPriceString();
        thanksPageAssertInfo.flightDetailInfo = checkOutPageV3.breakDownSectionV3().getFlightDetailContent();
        thanksPageAssertInfo.contactEmailEntered = checkOutPageV3.contactSection().getContactEmail();
    }

    /***************************** Test Cases *****************************/

    @Test
    public void todoPago_OneWay_Int_Booking_Flow() {
        logTestTitle("International - 2 Adults/2 Childs - Turista - Todo Pago");
        if(countryPar.equals(ARGENTINA)) {
            dataManagement.getOneWayDataTripItinerary(MIAMI_10D_2A_2C_TOURIST);

            flightsDataTrip.selectFlightType(ONE_WAY);
            flightsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
            flightsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
            flightsDataTrip.selectDateFromCalendar(flightsDataTrip.departureFlightsCalendar, dataManagement.startDate);
            flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
            flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
            flightsDataTrip.selectClass(dataManagement.flightClass);
            flightsResultsPage = flightsDataTrip.clickBuscarBtn();

            Assert.assertTrue(flightsResultsPage.vacancy());
            flightsResultsPage.clickTicketIdaRdb(FIRST_OPTION);

            dataManagement.getPassengerData(ADULT_MALE_NATIVE);
            dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);
            dataManagement.getPassengerData(CHILD_MALE_NATIVE);
            dataManagement.getPassengerData(CHILD_MALE_NATIVE);

            checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                    VISA_3_TODOPAGO, dataManagement.getBillingData(LOCAL_BILLING),
                    dataManagement.getContactData(CONTACT_CELL_PHONE), FLIGHTS_CHECKOUT_INT);

            thanksPageV3 = checkOutPageV3.clickComprarBtn();
            Assert.assertTrue(thanksPageV3.confirmationOk());
        } else {
            logger.warn(NOT_RUNNING_MEXICO_COLOMBIA);
        }
        setResultSauceLabs(PASSED);
    }

    @Test
    public void oneWay_Int_Booking_Flow() {
        logTestTitle("International - 2 Adults/2 Childs - Tourist");

        dataManagement.getOneWayDataTripItinerary(MIAMI_10D_2A_2C_TOURIST);

        flightsDataTrip.selectFlightType(ONE_WAY);
        flightsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull );
        flightsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.departureFlightsCalendar, dataManagement.startDate);
        flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
        flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
        flightsDataTrip.selectClass(dataManagement.flightClass);
        flightsResultsPage = flightsDataTrip.clickBuscarBtn();

        Assert.assertTrue(flightsResultsPage.vacancy());
        flightsResultsPage.clickTicketIdaRdb(FIRST_OPTION);

        dataManagement.getPassengerData(ADULT_MALE_NATIVE);
        dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);
        dataManagement.getPassengerData(CHILD_MALE_NATIVE);
        dataManagement.getPassengerData(CHILD_MALE_NATIVE);

        checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, VISA_1,
                                              dataManagement.getBillingData(LOCAL_BILLING),
                                              dataManagement.getContactData(CONTACT_CELL_PHONE),FLIGHTS_CHECKOUT_INT);

        validateTermsAndConditions();

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void oneWay_Dom_Booking_Flow() {
        logTestTitle("Domestic - 2 Adults - All");

        dataManagement.getOneWayDataTripItinerary(DOMESTIC_20D_2A_ALL);

        flightsDataTrip.selectFlightType(ONE_WAY);
        flightsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        flightsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.departureFlightsCalendar, dataManagement.startDate);
        flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
        flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
        flightsDataTrip.selectClass(dataManagement.flightClass);
        flightsResultsPage = flightsDataTrip.clickBuscarBtn();

        Assert.assertTrue(flightsResultsPage.vacancy());
        flightsResultsPage.clickTicketIdaRdb(FIRST_OPTION);

        dataManagement.getPassengerData(ADULT_FEMALE_FOREIGN);
        dataManagement.getPassengerData(ADULT_FEMALE_FOREIGN);

        checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, MASTER_1,
                                               dataManagement.getBillingData(LOCAL_BILLING),
                                               dataManagement.getContactData(CONTACT_CELL_PHONE), FLIGHTS_CHECKOUT_DOM);

        validateTermsAndConditions();

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void roundTrip_Int_Booking_Flow() {
        logTestTitle("International - 10 days - 2 Adults/2 Childs - Tourist");

        dataManagement.getRoundTripDataTripItinerary(MIAMI_10D_2A_2C_TOURIST);

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

        dataManagement.getPassengerData(ADULT_MALE_NATIVE);
        dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);
        dataManagement.getPassengerData(CHILD_MALE_NATIVE);
        dataManagement.getPassengerData(CHILD_MALE_NATIVE);

        checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                                              VISA_1,
                                               dataManagement.getBillingData(LOCAL_BILLING),
                                               dataManagement.getContactData(CONTACT_CELL_PHONE), FLIGHTS_CHECKOUT_INT);
        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.finalAmountPaid));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.contactEmailEntered));
        Assert.assertTrue(thanksPageV3.isFlightDetailInfoOk(thanksPageAssertInfo.flightDetailInfo));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

/*
    @SuppressWarnings("Duplicates")
    @Test
    public void roundTrip_Dom_Booking_2Credit_Cards_Flow() {
        logTestTitle("Domestic - 20 days - 2 Adults - Todas");
        if(countryPar.equals(ARGENTINA)) {
            dataManagement.getRoundTripDataTripItinerary(DOMESTIC_20D_2A_ALL);

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

            dataManagement.getPassengerData(ADULT_FEMALE_FOREIGN);
            dataManagement.getPassengerData(ADULT_FEMALE_FOREIGN);

            checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);
            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, TWOCARDS_VISA_MASTER,
                    dataManagement.getBillingData(LOCAL_BILLING),
                    dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_DOM);

            thanksPageV3 = checkOutPageV3.clickComprarBtn();
            Assert.assertTrue(thanksPageV3.confirmationOk());
        }else{
            logger.warn(NOT_RUNNING_MEXICO_COLOMBIA);
        }
        setResultSauceLabs(PASSED);
    }
*/

    @Test
    public void roundTrip_Dom_Booking_Flow() {
        logTestTitle("Domestic - 20 days - 2 Adults - All");

        dataManagement.getRoundTripDataTripItinerary(DOMESTIC_20D_2A_ALL);

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

        dataManagement.getPassengerData(ADULT_FEMALE_FOREIGN);
        dataManagement.getPassengerData(ADULT_FEMALE_FOREIGN);

        checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, VISA_1,
                dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_DOM);

        validateTermsAndConditions();

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void multiDest_Int_Booking_Flow() {
        final int TRANSFERS = 3;
        logTestTitle("International - 2 Adults - All");

        dataManagement.getMultiDestDataTripItinerary(MULTI_3LEGS_2A_ALL);

        flightsDataTrip.selectFlightType(MULTIDESTINATION);
        flightsDataTrip.clickAddLegLnk();
        flightsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull );
        flightsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.departureFlightsCalendar, dataManagement.startDate);
        flightsDataTrip.setMultiDestFlight(dataManagement.originAuto2, dataManagement.originFull2, originCalendarLegOne);
        flightsDataTrip.setMultiDestFlight(dataManagement.destinationAuto2, dataManagement.destinationFull2, destinationCalendarLegOne);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.departureFlights0Calendar, dataManagement.startDate2);
        flightsDataTrip.setMultiDestFlight(dataManagement.originAuto3, dataManagement.originFull3, originCalendarLegTwo);
        flightsDataTrip.setMultiDestFlight(dataManagement.destinationAuto3, dataManagement.destinationFull3, destinationCalendarLegTwo);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.departureFlights1Calendar, dataManagement.startDate3);
        flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
        flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
        flightsDataTrip.selectClass(dataManagement.flightClass);
        flightsResultsPage = flightsDataTrip.clickBuscarBtn();

        Assert.assertTrue(flightsResultsPage.vacancy());
        flightsResultsPage.displayMultidestinationInfo(TRANSFERS);

        dataManagement.getPassengerData(ADULT_MALE_NATIVE);
        dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);

        checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, VISA_1,
                                               dataManagement.getBillingData(LOCAL_BILLING),
                                               dataManagement.getContactData(CONTACT_CELL_PHONE), FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }
}