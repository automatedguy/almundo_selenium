package com.almundo.browser.automation.tests.Cars;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.CarsDataTrip;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ConfirmationPageV3;
import com.almundo.browser.automation.pages.ResultsPage.CarsResultsPage;
import org.json.simple.JSONArray;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.*;
import static com.almundo.browser.automation.utils.Constants.Results.PASSED;

/**
 * Created by gabrielcespedes on 20/12/16.
 */
public class RetailFlowTest extends TestBaseSetup {

    private CarsResultsPage carsResultsPage = null;
    private CheckOutPageV3 checkOutPageV3 = null;
    private ConfirmationPageV3 confirmationPageV3 = null;

    private CarsDataTrip carsDataTrip = null;
    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initItineraryData() {
        dataManagement.getCarsItineraryData();
    }

    @BeforeMethod
    private void clickCarsBtn() {
        carsDataTrip = basePage.clickCarsBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void suc_Int_Booking_Flow() {
        logTestTitle("International - 10 days");
        dataManagement.getCarsDataTripItinerary(MIA_10D_21_24);

        carsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        carsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        carsDataTrip.selectDateFromCalendar(carsDataTrip.pickUpDateCalendar, dataManagement.startDate);
        carsDataTrip.selectDateFromCalendar(carsDataTrip.dropOffDateCalendar, dataManagement.endDate);
        carsDataTrip.selectPickUpTime(dataManagement.pickUpTime);
        carsDataTrip.selectDropOffTime(dataManagement.dropOffTime);
        carsDataTrip.selectAgeRange(dataManagement.ageRange);
        carsResultsPage = carsDataTrip.clickBuscarBtn();

        Assert.assertTrue(carsResultsPage.vacancy());

        dataManagement.getPassengerData(ADULT_MALE_NATIVE);

        checkOutPageV3 = carsResultsPage.clickReservarAhoraBtn(FIRST_OPTION);

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, VISA_1,
                dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.getContactData(CONTACT_CELL_PHONE), CARS_CHECKOUT_PAGE_SUCURSAL);

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void suc_Int_Booking_Flow_Splitted_2cards() {
        logTestTitle("International - 10 days - VISA_MASTER");
        if(!countryPar.equals(MEXICO)) {
            dataManagement.getCarsDataTripItinerary(MIA_10D_21_24);

            carsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
            carsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
            carsDataTrip.selectDateFromCalendar(carsDataTrip.pickUpDateCalendar, dataManagement.startDate);
            carsDataTrip.selectDateFromCalendar(carsDataTrip.dropOffDateCalendar, dataManagement.endDate);
            carsDataTrip.selectPickUpTime(dataManagement.pickUpTime);
            carsDataTrip.selectDropOffTime(dataManagement.dropOffTime);
            carsDataTrip.selectAgeRange(dataManagement.ageRange);
            carsResultsPage = carsDataTrip.clickBuscarBtn();

            Assert.assertTrue(carsResultsPage.vacancy());

            dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);

            checkOutPageV3 = carsResultsPage.clickReservarAhoraBtn(FIRST_OPTION);

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, VISA_MASTER,
                    dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                    dataManagement.getContactData(CONTACT_CELL_PHONE), CARS_CHECKOUT_PAGE_SUCURSAL);

            confirmationPageV3 = checkOutPageV3.clickComprarBtn();
            Assert.assertTrue(confirmationPageV3.confirmationOk());
        }
        else{
            logger.warn(NOT_RUNNING_MEXICO);
        }
        setResultSauceLabs(PASSED);
    }

    @Test
    public void suc_Int_Booking_Flow_Splitted_DepositCredit() {
        logTestTitle("International - 10 days - DEPOSIT_MASTER");
        if(!countryPar.equals(MEXICO)) {
            dataManagement.getCarsDataTripItinerary(MIA_10D_21_24);

            carsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
            carsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
            carsDataTrip.selectDateFromCalendar(carsDataTrip.pickUpDateCalendar, dataManagement.startDate);
            carsDataTrip.selectDateFromCalendar(carsDataTrip.dropOffDateCalendar, dataManagement.endDate);
            carsDataTrip.selectPickUpTime(dataManagement.pickUpTime);
            carsDataTrip.selectDropOffTime(dataManagement.dropOffTime);
            carsDataTrip.selectAgeRange(dataManagement.ageRange);
            carsResultsPage = carsDataTrip.clickBuscarBtn();

            Assert.assertTrue(carsResultsPage.vacancy());

            dataManagement.getPassengerData(ADULT_MALE_NATIVE);

            checkOutPageV3 = carsResultsPage.clickReservarAhoraBtn(FIRST_OPTION);

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, DEPOSIT_MASTER,
                    dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                    dataManagement.getContactData(CONTACT_CELL_PHONE), CARS_CHECKOUT_PAGE_SUCURSAL);

            confirmationPageV3 = checkOutPageV3.clickComprarBtn();
            Assert.assertTrue(confirmationPageV3.confirmationOk());
        }
        else{
            logger.warn(NOT_RUNNING_MEXICO);
        }
        setResultSauceLabs(PASSED);
    }

    @Test
    public void suc_Int_Booking_Flow_Splitted_TransaferCredit() {
        logTestTitle("International - 10 days - TRANSFER_MASTER");
        if(!countryPar.equals(MEXICO)) {
            dataManagement.getCarsDataTripItinerary(MIA_10D_21_24);

            carsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
            carsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
            carsDataTrip.selectDateFromCalendar(carsDataTrip.pickUpDateCalendar, dataManagement.startDate);
            carsDataTrip.selectDateFromCalendar(carsDataTrip.dropOffDateCalendar, dataManagement.endDate);
            carsDataTrip.selectPickUpTime(dataManagement.pickUpTime);
            carsDataTrip.selectDropOffTime(dataManagement.dropOffTime);
            carsDataTrip.selectAgeRange(dataManagement.ageRange);
            carsResultsPage = carsDataTrip.clickBuscarBtn();

            Assert.assertTrue(carsResultsPage.vacancy());

            dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);

            checkOutPageV3 = carsResultsPage.clickReservarAhoraBtn(FIRST_OPTION);

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, TRANSFER_MASTER,
                    dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                    dataManagement.getContactData(CONTACT_CELL_PHONE), CARS_CHECKOUT_PAGE_SUCURSAL);

            confirmationPageV3 = checkOutPageV3.clickComprarBtn();
            Assert.assertTrue(confirmationPageV3.confirmationOk());
        }
        else{
            logger.warn(NOT_RUNNING_MEXICO);
        }
        setResultSauceLabs(PASSED);
    }

    @Test
    public void suc_Int_Booking_Flow_Splitted_3cards() {
        logTestTitle("International - 10 days - VISA_MASTER_AMEX");
        if(!countryPar.equals(MEXICO)) {
            dataManagement.getCarsDataTripItinerary(MIA_10D_21_24);

            carsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
            carsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
            carsDataTrip.selectDateFromCalendar(carsDataTrip.pickUpDateCalendar, dataManagement.startDate);
            carsDataTrip.selectDateFromCalendar(carsDataTrip.dropOffDateCalendar, dataManagement.endDate);
            carsDataTrip.selectPickUpTime(dataManagement.pickUpTime);
            carsDataTrip.selectDropOffTime(dataManagement.dropOffTime);
            carsDataTrip.selectAgeRange(dataManagement.ageRange);
            carsResultsPage = carsDataTrip.clickBuscarBtn();

            Assert.assertTrue(carsResultsPage.vacancy());

            dataManagement.getPassengerData(ADULT_MALE_NATIVE);

            checkOutPageV3 = carsResultsPage.clickReservarAhoraBtn(FIRST_OPTION);

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, VISA_MASTER_AMEX,
                    dataManagement.getBillingData(LOCAL_BILLING_SUCURSALES),
                    dataManagement.getContactData(CONTACT_CELL_PHONE), CARS_CHECKOUT_PAGE_SUCURSAL);

            confirmationPageV3 = checkOutPageV3.clickComprarBtn();
            Assert.assertTrue(confirmationPageV3.confirmationOk());
        }
        else{
            logger.warn(NOT_RUNNING_MEXICO);
        }
        setResultSauceLabs(PASSED);
    }
}