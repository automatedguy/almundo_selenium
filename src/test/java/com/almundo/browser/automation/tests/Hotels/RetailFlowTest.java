package com.almundo.browser.automation.tests.Hotels;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.HotelsDataTrip;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ThanksPageV3;
import com.almundo.browser.automation.pages.ResultsPage.HotelsDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.HotelsResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.*;
import static com.almundo.browser.automation.utils.Constants.Results.PASSED;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class RetailFlowTest extends TestBaseSetup {

    private HotelsResultsPage hotelsResultsPage = null;
    private HotelsDetailPage hotelsDetailPage = null;
    private CheckOutPageV3 checkOutPageV3 = null;
    private ThanksPageV3 thanksPageV3 = null;

    private HotelsDataTrip hotelsDataTrip = null;
    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initItineraryData() {
        dataManagement.getHotelsItineraryData();
    }

    @BeforeMethod
    private void clickHotelsBtn() {
        hotelsDataTrip = basePage.clickHotelsBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void suc_Dom_Booking_Flow() {
        logTestTitle("Domestic - 15 days - 2 Adults - 1 Room");

        dataManagement.getHotelsDataTripItinerary("domestic02_20days_2adults_1room");

        hotelsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.startDate);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);
        hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
        hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

        Assert.assertTrue(hotelsResultsPage.vacancy());
        hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

        PageUtils.switchToNewTab(driver);
        hotelsDetailPage.clickVerHabitacionesBtn();

        checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);

        dataManagement.getPassengerData("adult_female_native");
        dataManagement.getPassengerData("adult_female_native");

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, VISA_1,
                dataManagement.getBillingData("local_Billing_sucursales"),
                dataManagement.getContactData("contact_phone"),
                "HotelsCheckOutPageDomesticSucursal");
        setResultSauceLabs(PASSED);
    }

    @Test
    public void suc_Int_Booking_Flow() {
        logTestTitle("International -  10 days - 2 Adults - 1 Room");

        dataManagement.getHotelsDataTripItinerary("miami_10days_2adults_1room");

        hotelsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.startDate);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);
        hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
        hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

        Assert.assertTrue(hotelsResultsPage.vacancy());
        hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

        PageUtils.switchToNewTab(driver);
        hotelsDetailPage.clickVerHabitacionesBtn();

        checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);

        dataManagement.getPassengerData("adult_female_native");
        dataManagement.getPassengerData("adult_female_native");

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                AMEX_1,
                dataManagement.getBillingData("local_Billing_sucursales"),
                dataManagement.getContactData("contact_phone"),
                "HotelsCheckOutPageInternationalSucursal");

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

    @Test
    public void suc_Int_Booking_Flow_Splitted_2cards() {
        logTestTitle("International - 2 Credit Cards - 10 days - 2 Adults - 1 Room");
        if(!countryPar.equals("MEXICO")) {
            dataManagement.getHotelsDataTripItinerary("miami_10days_2adults_1room");

            hotelsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
            hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.startDate);
            hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);
            hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
            hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

            Assert.assertTrue(hotelsResultsPage.vacancy());
            hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

            PageUtils.switchToNewTab(driver);
            hotelsDetailPage.clickVerHabitacionesBtn();

            checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);

            dataManagement.getPassengerData("adult_female_native");
            dataManagement.getPassengerData("adult_female_native");

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                    "pago_dividido$1_visa_visa$1_master_master$",
                    dataManagement.getBillingData("local_Billing_sucursales"),
                    dataManagement.getContactData("contact_phone"),
                    "HotelsCheckOutPageInternationalSucursal");

            thanksPageV3 = checkOutPageV3.clickComprarBtn();
            Assert.assertTrue(thanksPageV3.confirmationOk());
        }
        else{
            logger.warn("We are not running this for MEXICO!");
        }
        setResultSauceLabs(PASSED);
    }

    @Test
    public void suc_Int_Booking_Flow_Splitted_DepositCredit() {
        logTestTitle("International - Deposit Credit - 10 days - 2 Adults - 1 Room");
        if(!countryPar.equals("MEXICO")) {
            dataManagement.getHotelsDataTripItinerary("miami_10days_2adults_1room");

            hotelsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
            hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.startDate);
            hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);
            hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
            hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

            Assert.assertTrue(hotelsResultsPage.vacancy());
            hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

            PageUtils.switchToNewTab(driver);
            hotelsDetailPage.clickVerHabitacionesBtn();

            checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);

            dataManagement.getPassengerData("adult_female_native");
            dataManagement.getPassengerData("adult_female_native");

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                    "pago_dividido$deposit$1_visa_visa$",
                    dataManagement.getBillingData("local_Billing_sucursales"),
                    dataManagement.getContactData("contact_phone"),
                    "HotelsCheckOutPageInternationalSucursal");

            thanksPageV3 = checkOutPageV3.clickComprarBtn();
            Assert.assertTrue(thanksPageV3.confirmationOk());
        }
        else{
            logger.warn("We are not running this for MEXICO!");
        }
        setResultSauceLabs(PASSED);
    }

    @Test
    public void suc_Int_Booking_Flow_Splitted_TransferCredit() {
        logTestTitle("International - Transfer Credit - 10 days - 2 Adults - 1 Room");
        if(!countryPar.equals(MEXICO) && !countryPar.equals(ARGENTINA)) {
            dataManagement.getHotelsDataTripItinerary("miami_10days_2adults_1room");

            hotelsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
            hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.startDate);
            hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);
            hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
            hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

            Assert.assertTrue(hotelsResultsPage.vacancy());
            hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

            PageUtils.switchToNewTab(driver);
            hotelsDetailPage.clickVerHabitacionesBtn();

            checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);

            dataManagement.getPassengerData("adult_female_native");
            dataManagement.getPassengerData("adult_female_native");

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                    "pago_dividido$transfer$1_visa_visa$",
                    dataManagement.getBillingData("local_Billing_sucursales"),
                    dataManagement.getContactData("contact_phone"),
                    "HotelsCheckOutPageInternationalSucursal");

            thanksPageV3 = checkOutPageV3.clickComprarBtn();
            Assert.assertTrue(thanksPageV3.confirmationOk());
        }
        else{
            logger.warn("We are not running this for MEXICO and ARGENTINA!");
        }
        setResultSauceLabs(PASSED);
    }

    @Test
    public void suc_Int_Booking_Flow_Splitted_3cards() {
        logTestTitle("International - 3 Cards - 10 days - 2 Adults - 1 Room");
        if(!countryPar.equals("MEXICO")) {
            dataManagement.getHotelsDataTripItinerary("miami_10days_2adults_1room");

            hotelsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
            hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.startDate);
            hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);
            hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
            hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

            Assert.assertTrue(hotelsResultsPage.vacancy());
            hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

            PageUtils.switchToNewTab(driver);
            hotelsDetailPage.clickVerHabitacionesBtn();

            checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);

            dataManagement.getPassengerData("adult_female_native");
            dataManagement.getPassengerData("adult_female_native");

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                    "pago_dividido$1_amex_amex$1_visa_visa$1_master_master$",
                    dataManagement.getBillingData("local_Billing_sucursales"),
                    dataManagement.getContactData("contact_phone"),
                    "HotelsCheckOutPageInternationalSucursal");

            thanksPageV3 = checkOutPageV3.clickComprarBtn();
            Assert.assertTrue(thanksPageV3.confirmationOk());
        }
        else{
            logger.warn("We are not running this for MEXICO!");
        }
        setResultSauceLabs(PASSED);
    }
}
