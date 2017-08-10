package com.almundo.browser.automation.tests.Trips;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.TripsDataTrip;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ConfirmationPageV3;
import com.almundo.browser.automation.pages.ResultsPage.TripsDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.TripsResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.FIRST_OPTION;
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
    private ConfirmationPageV3 confirmationPageV3 = null;

    @BeforeClass
    private void initItineraryData() {
        dataManagement.getTripsItineraryData();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void suc_Dom_Booking_Flow() {
        logTestTitle("Sucursales Trips Flow - Domestic - 20 days - 2 Adults/1 Child - 1 Room - " + countryPar );
        PageUtils.waitElementForVisibility(driver, basePage.tripsIcon, 10, "Vuelo+Hotel icon");

        dataManagement.getTripsDataTripItinerary("domestic02_20days_2adults_1childs_1room");

        tripsDataTrip = basePage.clicksTripsBtn();
        tripsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        tripsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        tripsDataTrip.selectDateFromCalendar(tripsDataTrip.departureCalendar, dataManagement.startDate);
        tripsDataTrip.selectDateFromCalendar(tripsDataTrip.arrivalCalendar, dataManagement.endDate);
        tripsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
        tripsResultsPage = tripsDataTrip.clickBuscarBtn();

        Assert.assertTrue(tripsResultsPage.vacancy());

        tripsResultsPage.clickElegirBtn(FIRST_OPTION);
        tripsDetailPage = tripsResultsPage.clickContinuarBtn();
        tripsDetailPage.clickVerHabitacionBtn();

        checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);

        dataManagement.getPassengerData("adult_female_foreign");
        dataManagement.getPassengerData("adult_female_foreign");
        dataManagement.getPassengerData("child_female_native_trips_domestic");

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing_sucursales"),
                dataManagement.getContactData("contact_cell_phone"),
                "TripsCheckOutPageDomesticSucursal");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void suc_Int_Booking_Flow() {
        logTestTitle("Sucursales Trips Flow - International - 20 days - 2 Adults - 1 Room - " + countryPar );
        PageUtils.waitElementForVisibility(driver, basePage.tripsIcon, 10, "Vuelo+Hotel icon");

        dataManagement.getTripsDataTripItinerary("int02_20days_2adults_1room");

        tripsDataTrip = basePage.clicksTripsBtn();
        tripsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        tripsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        tripsDataTrip.selectDateFromCalendar(tripsDataTrip.departureCalendar, dataManagement.startDate);
        tripsDataTrip.selectDateFromCalendar(tripsDataTrip.arrivalCalendar, dataManagement.endDate);
        tripsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
        tripsResultsPage = tripsDataTrip.clickBuscarBtn();

        Assert.assertTrue(tripsResultsPage.vacancy());

        tripsResultsPage.clickElegirBtn(FIRST_OPTION);
        tripsDetailPage = tripsResultsPage.clickContinuarBtn();
        tripsDetailPage.clickVerHabitacionBtn();

        checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);

        dataManagement.getPassengerData("adult_female_foreign");
        dataManagement.getPassengerData("adult_female_foreign");

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                "1_visa_visa",
                dataManagement.getBillingData("local_Billing_sucursales"),
                dataManagement.getContactData("contact_cell_phone"),
                "TripsCheckOutPageDomesticSucursal");

        confirmationPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(confirmationPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void suc_Int_Booking_Flow_Splitted_2cards() {
        logTestTitle("Sucursales Trips Flow - International - Splitted - 20 days - 2 Adults - 1 Room - " + countryPar );
        if(!countryPar.equals("MEXICO")) {
            PageUtils.waitElementForVisibility(driver, basePage.tripsIcon, 10, "Vuelo+Hotel icon");

            dataManagement.getTripsDataTripItinerary("int02_20days_2adults_1room");

            tripsDataTrip = basePage.clicksTripsBtn();
            tripsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
            tripsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
            tripsDataTrip.selectDateFromCalendar(tripsDataTrip.departureCalendar, dataManagement.startDate);
            tripsDataTrip.selectDateFromCalendar(tripsDataTrip.arrivalCalendar, dataManagement.endDate);
            tripsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
            tripsResultsPage = tripsDataTrip.clickBuscarBtn();

            Assert.assertTrue(tripsResultsPage.vacancy());

            tripsResultsPage.clickElegirBtn(FIRST_OPTION);
            tripsDetailPage = tripsResultsPage.clickContinuarBtn();
            tripsDetailPage.clickVerHabitacionBtn();

            checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);

            dataManagement.getPassengerData("adult_female_foreign");
            dataManagement.getPassengerData("adult_female_foreign");

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                    "pago_dividido$1_visa_visa$1_master_master$",
                    dataManagement.getBillingData("local_Billing_sucursales"),
                    dataManagement.getContactData("contact_cell_phone"),
                    "TripsCheckOutPageDomesticSucursal");

            confirmationPageV3 = checkOutPageV3.clickComprarBtn();
            Assert.assertTrue(confirmationPageV3.confirmationOk());
        }
        else{
            logger.warn("We are not running this for MEXICO!");
        }
        setResultSauceLabs(PASSED);
    }
}