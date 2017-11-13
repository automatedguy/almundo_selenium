package com.almundo.browser.automation.tests.Trips;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.TripsDataTrip;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ThanksPageV3;
import com.almundo.browser.automation.pages.ResultsPage.TripsDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.TripsResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.*;
import static com.almundo.browser.automation.utils.Constants.Results.PASSED;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class RetailFlowTest extends TestBaseSetup {

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

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.clearPassengerJsonList();
    }

    private void getTripsAssertionInfo(){
        thanksPageAssertInfo.setFinalAmountPaid(checkOutPageV3.breakDownSectionV3().getFinalPriceString());
        thanksPageAssertInfo.setContactEmailEntered(checkOutPageV3.contactSection().getContactEmail());
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void suc_Dom_Booking_Flow() {
        logTestTitle("Domestic - 20 days - 2 Adults/1 Child - 1 Room");
        PageUtils.waitElementForVisibility(driver, basePage.tripsIcon, 10, TRIPS_ICO);

        dataManagement.setTripsDataTripItinerary(DOM02_20D_2A_1C_1R);

        tripsDataTrip = basePage.clicksTripsBtn();
        tripsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
        tripsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
        tripsDataTrip.setDate(tripsDataTrip.getDepartureCalendar(), dataManagement.getStartDate());
        tripsDataTrip.setDate(tripsDataTrip.getArrivalCalendar(), dataManagement.getEndDate());
        tripsDataTrip.setPassengers(dataManagement.getAdults(), dataManagement.getChilds(), dataManagement.getRooms());
        tripsResultsPage = tripsDataTrip.clickBuscarBtn();

        Assert.assertTrue(tripsResultsPage.vacancy());

        tripsResultsPage.clickElegirBtn(FIRST_OPTION);
        tripsDetailPage = tripsResultsPage.clickContinuarBtn();
        tripsDetailPage.clickVerHabitacionBtn();

        checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);

        dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);
        dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);
        dataManagement.setPassengerData(CHILD_FEM_NAT_TRIPS_DOM);

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(), VISA_1,
                                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                                dataManagement.getContactData(CONTACT_CELL_PHONE),TRIPS_CHECKOUT_DOM_RET);
        getTripsAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void suc_Int_Booking_Flow() {
        logTestTitle("International - 20 days - 2 Adults - 1 Room");
        PageUtils.waitElementForVisibility(driver, basePage.tripsIcon, 10, TRIPS_ICO);

        dataManagement.setTripsDataTripItinerary(INT02_20D_2A_1R);

        tripsDataTrip = basePage.clicksTripsBtn();
        tripsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
        tripsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
        tripsDataTrip.setDate(tripsDataTrip.getDepartureCalendar(), dataManagement.getStartDate());
        tripsDataTrip.setDate(tripsDataTrip.getArrivalCalendar(), dataManagement.getEndDate());
        tripsDataTrip.setPassengers(dataManagement.getAdults(), dataManagement.getChilds(), dataManagement.getRooms());
        tripsResultsPage = tripsDataTrip.clickBuscarBtn();

        Assert.assertTrue(tripsResultsPage.vacancy());

        tripsResultsPage.clickElegirBtn(FIRST_OPTION);
        tripsDetailPage = tripsResultsPage.clickContinuarBtn();
        tripsDetailPage.clickVerHabitacionBtn();

        checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);

        dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);
        dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(), MASTER_1,
                                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_DOM_RET);
        getTripsAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void IntBookingWithTransfer() {
        logTestTitle("International With Transfer - 20 days - 2 Adults - 1 Room");
        if(countryPar.equals(ARGENTINA)) {
            addTransfer = true;
            PageUtils.waitElementForVisibility(driver, basePage.tripsIcon, 10, TRIPS_ICO);

            dataManagement.setTripsDataTripItinerary(INT02_20D_2A_1R);

            tripsDataTrip = basePage.clicksTripsBtn();
            tripsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
            tripsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
            tripsDataTrip.setDate(tripsDataTrip.getDepartureCalendar(), dataManagement.getStartDate());
            tripsDataTrip.setDate(tripsDataTrip.getArrivalCalendar(), dataManagement.getEndDate());
            tripsDataTrip.setPassengers(dataManagement.getAdults(), dataManagement.getChilds(), dataManagement.getRooms());
            tripsResultsPage = tripsDataTrip.clickBuscarBtn();

            Assert.assertTrue(tripsResultsPage.vacancy());

            tripsResultsPage.clickElegirBtn(FIRST_OPTION);
            tripsDetailPage = tripsResultsPage.clickContinuarBtn();
            tripsDetailPage.clickVerHabitacionBtn();

            checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);

            dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);
            dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);

            checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(), MASTER_1,
                                    dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                                    dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_DOM_RET);
            getTripsAssertionInfo();
            thanksPageV3 = checkOutPageV3.clickComprarBtn();

            Assert.assertTrue(thanksPageV3.confirmationOk());
            Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
            Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
            Assert.assertTrue(thanksPageV3.isPassengersInfoOk());
        } else {
            logger.warn(NOT_RUNNING_MEXICO_COLOMBIA);
        }
        setResultSauceLabs(PASSED);
    }

/*    @SuppressWarnings("Duplicates")
    @Test
    public void suc_Int_Booking_Flow_Splitted_2cards() {
        logTestTitle("International - VISA_MASTER - 20 days - 2 Adults - 1 Room");
        if(!countryPar.equals(MEXICO)) {
            PageUtils.waitElementForVisibility(driver, basePage.tripsIcon, 10, TRIPS_ICO);

            dataManagement.setTripsDataTripItinerary(INT02_20D_2A_1R);

            tripsDataTrip = basePage.clicksTripsBtn();
            tripsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
            tripsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
            tripsDataTrip.setDate(tripsDataTrip.getDepartureCalendar(), dataManagement.getStartDate());
            tripsDataTrip.setDate(tripsDataTrip.getArrivalCalendar(), dataManagement.getEndDate());
            tripsDataTrip.setPassengers(dataManagement.getAdults(), dataManagement.getChilds(), dataManagement.getRooms());
            tripsResultsPage = tripsDataTrip.clickBuscarBtn();

            Assert.assertTrue(tripsResultsPage.vacancy());

            tripsResultsPage.clickElegirBtn(FIRST_OPTION);
            tripsDetailPage = tripsResultsPage.clickContinuarBtn();
            tripsDetailPage.clickVerHabitacionBtn();

            checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);

            dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);
            dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);

            checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                    VISA_MASTER, dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                    dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_DOM_RET);

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
        logTestTitle("International - DEPOSIT_MASTER - 20 days - 2 Adults - 1 Room");
        if(!countryPar.equals(MEXICO)) {
            PageUtils.waitElementForVisibility(driver, basePage.tripsIcon, 10, TRIPS_ICO);

            dataManagement.setTripsDataTripItinerary(INT02_20D_2A_1R);

            tripsDataTrip = basePage.clicksTripsBtn();
            tripsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
            tripsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
            tripsDataTrip.setDate(tripsDataTrip.getDepartureCalendar(), dataManagement.getStartDate());
            tripsDataTrip.setDate(tripsDataTrip.getArrivalCalendar(), dataManagement.getEndDate());
            tripsDataTrip.setPassengers(dataManagement.getAdults(), dataManagement.getChilds(), dataManagement.getRooms());
            tripsResultsPage = tripsDataTrip.clickBuscarBtn();

            Assert.assertTrue(tripsResultsPage.vacancy());

            tripsResultsPage.clickElegirBtn(FIRST_OPTION);
            tripsDetailPage = tripsResultsPage.clickContinuarBtn();
            tripsDetailPage.clickVerHabitacionBtn();

            checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);

            dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);
            dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);

            checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                    DEPOSIT_MASTER, dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                    dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_DOM_RET);

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
        logTestTitle("International - VISA_TRANSFER - 20 days - 2 Adults - 1 Room");
        if(!countryPar.equals(MEXICO)) {
            PageUtils.waitElementForVisibility(driver, basePage.tripsIcon, 10, TRIPS_ICO);

            dataManagement.setTripsDataTripItinerary(INT02_20D_2A_1R);

            tripsDataTrip = basePage.clicksTripsBtn();
            tripsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
            tripsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
            tripsDataTrip.setDate(tripsDataTrip.getDepartureCalendar(), dataManagement.getStartDate());
            tripsDataTrip.setDate(tripsDataTrip.getArrivalCalendar(), dataManagement.getEndDate());
            tripsDataTrip.setPassengers(dataManagement.getAdults(), dataManagement.getChilds(), dataManagement.getRooms());
            tripsResultsPage = tripsDataTrip.clickBuscarBtn();

            Assert.assertTrue(tripsResultsPage.vacancy());

            tripsResultsPage.clickElegirBtn(FIRST_OPTION);
            tripsDetailPage = tripsResultsPage.clickContinuarBtn();
            tripsDetailPage.clickVerHabitacionBtn();

            checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);

            dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);
            dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);

            checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                    VISA_TRANSFER, dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                    dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_DOM_RET);

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
        logTestTitle("International - AMEX_VISA_MASTER - 20 days - 2 Adults - 1 Room");
        if(!countryPar.equals(MEXICO)) {
            PageUtils.waitElementForVisibility(driver, basePage.tripsIcon, 10, TRIPS_ICO);

            dataManagement.setTripsDataTripItinerary(INT02_20D_2A_1R);

            tripsDataTrip = basePage.clicksTripsBtn();
            tripsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
            tripsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
            tripsDataTrip.setDate(tripsDataTrip.getDepartureCalendar(), dataManagement.getStartDate());
            tripsDataTrip.setDate(tripsDataTrip.getArrivalCalendar(), dataManagement.getEndDate());
            tripsDataTrip.setPassengers(dataManagement.getAdults(), dataManagement.getChilds(), dataManagement.getRooms());
            tripsResultsPage = tripsDataTrip.clickBuscarBtn();

            Assert.assertTrue(tripsResultsPage.vacancy());

            tripsResultsPage.clickElegirBtn(FIRST_OPTION);
            tripsDetailPage = tripsResultsPage.clickContinuarBtn();
            tripsDetailPage.clickVerHabitacionBtn();

            checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);

            dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);
            dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);

            checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                    AMEX_VISA_MASTER, dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                    dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_DOM_RET);

            thanksPageV3 = checkOutPageV3.clickComprarBtn();
            Assert.assertTrue(thanksPageV3.confirmationOk());
        }
        else{
            logger.warn(NOT_RUNNING_MEXICO);
        }
        setResultSauceLabs(PASSED);
    }*/
}