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
        dataManagement.setFlightsItineraryData();
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

    private void getFlightsAssertionInfo(){
        thanksPageAssertInfo.setFinalAmountPaid(checkOutPageV3.breakDownSectionV3().getFinalPriceString());
        thanksPageAssertInfo.setFlightsDetailInfo(checkOutPageV3.breakDownSectionV3().getFlightDetailContent());
        thanksPageAssertInfo.setContactEmailEntered(checkOutPageV3.contactSection().getContactEmail());
    }

    /***************************** Test Cases *****************************/

    @Test
    public void todoPago_OneWay_Int_Booking_Flow() {
        logTestTitle("International - 2 Adults/2 Childs - Turista - Todo Pago");
        if(countryPar.equals(ARGENTINA)) {
            dataManagement.setOneWayDataTripItinerary(MIAMI_10D_2A_2C_TOURIST);

            flightsDataTrip.selectFlightType(ONE_WAY);
            flightsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
            flightsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
            flightsDataTrip.selectDateFromCalendar(flightsDataTrip.getDepartureFlightsCalendar(), dataManagement.getStartDate());
            flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
            flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
            flightsDataTrip.selectClass(dataManagement.flightClass);
            flightsResultsPage = flightsDataTrip.clickBuscarBtn();

            Assert.assertTrue(flightsResultsPage.vacancy());
            flightsResultsPage.clickTicketIdaRdb(FIRST_OPTION);

            dataManagement.setPassengerData(ADULT_MALE_NATIVE);
            dataManagement.setPassengerData(ADULT_FEMALE_NATIVE);
            dataManagement.setPassengerData(CHILD_MALE_NATIVE);
            dataManagement.setPassengerData(CHILD_MALE_NATIVE);

            checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                    VISA_3, dataManagement.setBillingData(LOCAL_BILLING),
                    dataManagement.setContactData(CONTACT_CELL_PHONE), FLIGHTS_CHECKOUT_INT);

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

        dataManagement.setOneWayDataTripItinerary(MIAMI_10D_2A_2C_TOURIST);

        flightsDataTrip.selectFlightType(ONE_WAY);
        flightsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull() );
        flightsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.getDepartureFlightsCalendar(), dataManagement.getStartDate());
        flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
        flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
        flightsDataTrip.selectClass(dataManagement.flightClass);
        flightsResultsPage = flightsDataTrip.clickBuscarBtn();

        Assert.assertTrue(flightsResultsPage.vacancy());
        flightsResultsPage.clickTicketIdaRdb(FIRST_OPTION);

        dataManagement.setPassengerData(ADULT_MALE_NATIVE);
        dataManagement.setPassengerData(ADULT_FEMALE_NATIVE);
        dataManagement.setPassengerData(CHILD_MALE_NATIVE);
        dataManagement.setPassengerData(CHILD_MALE_NATIVE);

        checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, VISA_1,
                                              dataManagement.setBillingData(LOCAL_BILLING),
                                              dataManagement.setContactData(CONTACT_CELL_PHONE),FLIGHTS_CHECKOUT_INT);

        getFlightsAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isFlightDetailInfoOk(thanksPageAssertInfo.getFlightDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
        setResultSauceLabs(PASSED);
    }

    @Test
    public void oneWay_Dom_Booking_Flow() {
        logTestTitle("Domestic - 2 Adults - All");

        dataManagement.setOneWayDataTripItinerary(DOMESTIC_20D_2A_ALL);

        flightsDataTrip.selectFlightType(ONE_WAY);
        flightsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
        flightsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.getDepartureFlightsCalendar(), dataManagement.getStartDate());
        flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
        flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
        flightsDataTrip.selectClass(dataManagement.flightClass);
        flightsResultsPage = flightsDataTrip.clickBuscarBtn();

        Assert.assertTrue(flightsResultsPage.vacancy());
        flightsResultsPage.clickTicketIdaRdb(FIRST_OPTION);

        dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);
        dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);

        checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, MASTER_1,
                                               dataManagement.setBillingData(LOCAL_BILLING),
                                               dataManagement.setContactData(CONTACT_CELL_PHONE), FLIGHTS_CHECKOUT_DOM);

        validateTermsAndConditions();

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void roundTrip_Int_Booking_Flow() {
        logTestTitle("International - 10 days - 2 Adults/2 Childs - Tourist");

        dataManagement.setRoundTripDataTripItinerary(MIAMI_10D_2A_2C_TOURIST);

        flightsDataTrip.selectFlightType(ROUND_TRIP);
        flightsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
        flightsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.getDepartureFlightsCalendar(), dataManagement.getStartDate());
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.arrivalFlightsCalendar, dataManagement.endDate);
        flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
        flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
        flightsDataTrip.selectClass(dataManagement.flightClass);
        flightsResultsPage = flightsDataTrip.clickBuscarBtn();

        Assert.assertTrue(flightsResultsPage.vacancy());
        flightsResultsPage.clickTicketIdaRdb(FIRST_OPTION);
        flightsResultsPage.clickTicketVuelta(FIRST_OPTION+1);

        dataManagement.setPassengerData(ADULT_MALE_NATIVE);
        dataManagement.setPassengerData(ADULT_FEMALE_NATIVE);
        dataManagement.setPassengerData(CHILD_MALE_NATIVE);
        dataManagement.setPassengerData(CHILD_MALE_NATIVE);

        checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                                              VISA_1,
                                               dataManagement.setBillingData(LOCAL_BILLING),
                                               dataManagement.setContactData(CONTACT_CELL_PHONE), FLIGHTS_CHECKOUT_INT);
        getFlightsAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isFlightDetailInfoOk(thanksPageAssertInfo.getFlightDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void roundTrip_Int_Booking_2Credit_Cards_Flow() {
        logTestTitle("International - 10 days - 2 Adults - Tourist");
        if(countryPar.equals(ARGENTINA)) {
            dataManagement.setRoundTripDataTripItinerary(MIAMI_10D_2A_TOURIST);

            flightsDataTrip.selectFlightType(ROUND_TRIP);
            flightsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
            flightsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
            flightsDataTrip.selectDateFromCalendar(flightsDataTrip.getDepartureFlightsCalendar(), dataManagement.getStartDate());
            flightsDataTrip.selectDateFromCalendar(flightsDataTrip.arrivalFlightsCalendar, dataManagement.endDate);
            flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
            flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
            flightsDataTrip.selectClass(dataManagement.flightClass);
            flightsResultsPage = flightsDataTrip.clickBuscarBtn();

            Assert.assertTrue(flightsResultsPage.vacancy());
            flightsResultsPage.clickTicketIdaRdb(FIRST_OPTION);
            flightsResultsPage.clickTicketVuelta(FIRST_OPTION + 1);

            dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);
            dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);

            checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);
            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, TWOCARDS_VISA_MASTER,
                                                dataManagement.setBillingData(LOCAL_BILLING),
                                                dataManagement.setContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_DOM);
            getFlightsAssertionInfo();
            thanksPageV3 = checkOutPageV3.clickComprarBtn();

            Assert.assertTrue(thanksPageV3.confirmationOk());
            Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
            Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
            Assert.assertTrue(thanksPageV3.isFlightDetailInfoOk(thanksPageAssertInfo.getFlightDetailInfo()));
            Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        }else{
            logger.warn(NOT_RUNNING_MEXICO_COLOMBIA);
        }
        setResultSauceLabs(PASSED);
    }

    @Test
    public void roundTrip_Dom_Booking_Flow() {
        logTestTitle("Domestic - 20 days - 2 Adults - All");

        dataManagement.setRoundTripDataTripItinerary(DOMESTIC_20D_2A_ALL);

        flightsDataTrip.selectFlightType(ROUND_TRIP);
        flightsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
        flightsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.getDepartureFlightsCalendar(), dataManagement.getStartDate());
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.arrivalFlightsCalendar, dataManagement.endDate);
        flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
        flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
        flightsDataTrip.selectClass(dataManagement.flightClass);
        flightsResultsPage = flightsDataTrip.clickBuscarBtn();

        Assert.assertTrue(flightsResultsPage.vacancy());
        flightsResultsPage.clickTicketIdaRdb(FIRST_OPTION);
        flightsResultsPage.clickTicketVuelta(FIRST_OPTION+1);

        dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);
        dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);

        checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, VISA_1,
                dataManagement.setBillingData(LOCAL_BILLING),
                dataManagement.setContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_DOM);

        validateTermsAndConditions();

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void multiDest_Int_Booking_Flow() {
        final int TRANSFERS = 3;
        logTestTitle("International - 2 Adults - All");

        dataManagement.setMultiDestDataTripItinerary(MULTI_3LEGS_2A_ALL);

        flightsDataTrip.selectFlightType(MULTIDESTINATION);
        flightsDataTrip.clickAddLegLnk();
        flightsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
        flightsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.getDepartureFlightsCalendar(), dataManagement.getStartDate());
        flightsDataTrip.setMultiDestFlight(dataManagement.getOriginAuto2(), dataManagement.getOriginFull2(), originCalendarLegOne);
        flightsDataTrip.setMultiDestFlight(dataManagement.getDestinationAuto2(), dataManagement.getDestinationFull2(), destinationCalendarLegOne);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.getDepartureFlights2Calendar(), dataManagement.getStartDate2());
        flightsDataTrip.setMultiDestFlight(dataManagement.getOriginAuto3(), dataManagement.getOriginFull3(), originCalendarLegTwo);
        flightsDataTrip.setMultiDestFlight(dataManagement.getDestinationAuto3(), dataManagement.getDestinationFull3(), destinationCalendarLegTwo);
        flightsDataTrip.selectDateFromCalendar(flightsDataTrip.getDepartureFlights3Calendar(), dataManagement.getStartDate3());
        flightsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
        flightsDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);
        flightsDataTrip.selectClass(dataManagement.flightClass);
        flightsResultsPage = flightsDataTrip.clickBuscarBtn();

        Assert.assertTrue(flightsResultsPage.vacancy());
        flightsResultsPage.displayMultidestinationInfo(TRANSFERS);

        dataManagement.setPassengerData(ADULT_MALE_NATIVE);
        dataManagement.setPassengerData(ADULT_FEMALE_NATIVE);

        checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, VISA_1,
                                               dataManagement.setBillingData(LOCAL_BILLING),
                                               dataManagement.setContactData(CONTACT_CELL_PHONE), FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }
}