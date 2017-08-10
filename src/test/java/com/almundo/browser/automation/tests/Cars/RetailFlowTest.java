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

import static com.almundo.browser.automation.utils.Constants.FIRST_OPTION;
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
        logTestTitle("Sucursales Autos Flow - International - 10 days - " + countryPar );

        dataManagement.getCarsDataTripItinerary("miami_10days_entre_21_24");

        carsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        carsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        carsDataTrip.selectDateFromCalendar(carsDataTrip.pickUpDateCalendar, dataManagement.startDate);
        carsDataTrip.selectDateFromCalendar(carsDataTrip.dropOffDateCalendar, dataManagement.endDate);
        carsDataTrip.selectPickUpTime(dataManagement.pickUpTime);
        carsDataTrip.selectDropOffTime(dataManagement.dropOffTime);
        carsDataTrip.selectAgeRange(dataManagement.ageRange);
        carsResultsPage = carsDataTrip.clickBuscarBtn();

        Assert.assertTrue(carsResultsPage.vacancy());

        dataManagement.getPassengerData("adult_male_native");

        checkOutPageV3 = carsResultsPage.clickReservarAhoraBtn(FIRST_OPTION);

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing_sucursales"),
                dataManagement.getContactData("contact_cell_phone"),
                "CarsCheckOutPageSucursal");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void suc_Int_Booking_Flow_Splitted_2cards() {
        logTestTitle("Sucursales Autos Flow - International - Splitted -  10 days - " + countryPar );
        if(!countryPar.equals("MEXICO")) {
            dataManagement.getCarsDataTripItinerary("miami_10days_entre_21_24");

            carsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
            carsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
            carsDataTrip.selectDateFromCalendar(carsDataTrip.pickUpDateCalendar, dataManagement.startDate);
            carsDataTrip.selectDateFromCalendar(carsDataTrip.dropOffDateCalendar, dataManagement.endDate);
            carsDataTrip.selectPickUpTime(dataManagement.pickUpTime);
            carsDataTrip.selectDropOffTime(dataManagement.dropOffTime);
            carsDataTrip.selectAgeRange(dataManagement.ageRange);
            carsResultsPage = carsDataTrip.clickBuscarBtn();

            Assert.assertTrue(carsResultsPage.vacancy());

            dataManagement.getPassengerData("adult_male_native");

            checkOutPageV3 = carsResultsPage.clickReservarAhoraBtn(FIRST_OPTION);

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                    "pago_dividido$1_visa_visa$1_master_master$",
                    dataManagement.getBillingData("local_Billing_sucursales"),
                    dataManagement.getContactData("contact_cell_phone"),
                    "CarsCheckOutPageSucursal");

            confirmationPageV3 = checkOutPageV3.clickComprarBtn();
            Assert.assertTrue(confirmationPageV3.confirmationOk());
        }
        else{
            logger.warn("We are not running this for MEXICO!");
        }
        setResultSauceLabs(PASSED);
    }
}