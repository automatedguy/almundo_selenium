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
import static com.almundo.browser.automation.utils.PageUtils.setUrlParameter;

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
        dataManagement.setHotelsItineraryData();
    }

    @BeforeMethod
    private void clickHotelsBtn() {
        hotelsDataTrip = basePage.clickHotelsBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    /***************************** Test Cases *****************************/

    @Test
    public void suc_Dom_Booking_Flow() {
        logTestTitle("Domestic - 15 days - 2 Adults - 1 Room");

        dataManagement.setHotelsDataTripItinerary(DOM02_20D_2A_1R);

        hotelsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.getStartDate());
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);
        hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
        hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

        setUrlParameter(driver, "&PAYMENT_METHOD=PREPAID");

        Assert.assertTrue(hotelsResultsPage.vacancy());
        hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

        PageUtils.switchToNewTab(driver);
        hotelsDetailPage.clickVerHabitacionesBtn();

        checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);

        dataManagement.setPassengerData(ADULT_MALE_NATIVE);
        dataManagement.setPassengerData(ADULT_FEMALE_NATIVE);

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, VISA_1,
                dataManagement.setBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.setContactData(CONTACT_PHONE), HOTELS_CHECKOUT_DOM_RET);
        setResultSauceLabs(PASSED);
    }

    @Test
    public void suc_Int_Booking_Flow() {
        logTestTitle("International -  10 days - 2 Adults - 1 Room");

        dataManagement.setHotelsDataTripItinerary("miami_10days_2adults_1room");

        hotelsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.getStartDate());
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);
        hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
        hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

        Assert.assertTrue(hotelsResultsPage.vacancy());
        hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

        PageUtils.switchToNewTab(driver);
        hotelsDetailPage.clickVerHabitacionesBtn();

        checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);

        dataManagement.setPassengerData(ADULT_FEMALE_NATIVE);
        dataManagement.setPassengerData(ADULT_MALE_NATIVE);

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                AMEX_1,
                dataManagement.setBillingData(LOCAL_BILLING_SUCURSALES),
                dataManagement.setContactData(CONTACT_PHONE), HOTELS_CHECKOUT_INT_RET);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());
        setResultSauceLabs(PASSED);
    }

/*    @SuppressWarnings("Duplicates")
    @Test
    public void suc_Int_Booking_Flow_Splitted_2cards() {
        logTestTitle("International - 2 Credit Cards - 10 days - 2 Adults - 1 Room");
        if(countryPar.equals(ARGENTINA)) {
            dataManagement.setHotelsDataTripItinerary(MIA_10D_2A_1R);

            hotelsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
            hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.getStartDate());
            hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);
            hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
            hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

            Assert.assertTrue(hotelsResultsPage.vacancy());
            hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

            PageUtils.switchToNewTab(driver);
            hotelsDetailPage.clickVerHabitacionesBtn();

            checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);

            dataManagement.setPassengerData(ADULT_FEMALE_NATIVE);
            dataManagement.setPassengerData(ADULT_MALE_NATIVE);

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                    VISA_MASTER,
                    dataManagement.setBillingData(LOCAL_BILLING_SUCURSALES),
                    dataManagement.setContactData(CONTACT_PHONE), HOTELS_CHECKOUT_INT_RET);

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
    public void suc_Int_Booking_Flow_Splitted_DepositCredit() {
        logTestTitle("International - Deposit Credit - 10 days - 2 Adults - 1 Room");
        if(countryPar.equals(ARGENTINA)) {
            dataManagement.setHotelsDataTripItinerary(MIA_10D_2A_1R);

            hotelsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
            hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.getStartDate());
            hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);
            hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
            hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

            Assert.assertTrue(hotelsResultsPage.vacancy());
            hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

            PageUtils.switchToNewTab(driver);
            hotelsDetailPage.clickVerHabitacionesBtn();

            checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);

            dataManagement.setPassengerData(ADULT_MALE_NATIVE);
            dataManagement.setPassengerData(ADULT_FEMALE_NATIVE);

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                    DEPOSIT_VISA,
                    dataManagement.setBillingData(LOCAL_BILLING_SUCURSALES),
                    dataManagement.setContactData(CONTACT_PHONE), HOTELS_CHECKOUT_INT_RET);

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
    public void suc_Int_Booking_Flow_Splitted_TransferCredit() {
        logTestTitle("International - Transfer Credit - 10 days - 2 Adults - 1 Room");
        if(countryPar.equals(ARGENTINA)) {
            dataManagement.setHotelsDataTripItinerary(MIA_10D_2A_1R);

            hotelsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
            hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.getStartDate());
            hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);
            hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
            hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

            Assert.assertTrue(hotelsResultsPage.vacancy());
            hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

            PageUtils.switchToNewTab(driver);
            hotelsDetailPage.clickVerHabitacionesBtn();

            checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);

            dataManagement.setPassengerData(ADULT_MALE_NATIVE);
            dataManagement.setPassengerData(ADULT_FEMALE_NATIVE);

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                    TRANSFER_VISA,
                    dataManagement.setBillingData(LOCAL_BILLING_SUCURSALES),
                    dataManagement.setContactData(CONTACT_PHONE), HOTELS_CHECKOUT_INT_RET);

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
        logTestTitle("International - 3 Cards - 10 days - 2 Adults - 1 Room");
        if(countryPar.equals(ARGENTINA)) {
            dataManagement.setHotelsDataTripItinerary(MIA_10D_2A_1R);

            hotelsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
            hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.getStartDate());
            hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);
            hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
            hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

            Assert.assertTrue(hotelsResultsPage.vacancy());
            hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

            PageUtils.switchToNewTab(driver);
            hotelsDetailPage.clickVerHabitacionesBtn();

            checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);

            dataManagement.setPassengerData(ADULT_MALE_NATIVE);
            dataManagement.setPassengerData(ADULT_FEMALE_NATIVE);

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                    AMEX_VISA_MASTER,
                    dataManagement.setBillingData(LOCAL_BILLING_SUCURSALES),
                    dataManagement.setContactData(CONTACT_PHONE), HOTELS_CHECKOUT_INT_RET);

            thanksPageV3 = checkOutPageV3.clickComprarBtn();
            Assert.assertTrue(thanksPageV3.confirmationOk());
        }
        else{
            logger.warn(NOT_RUNNING_MEXICO_COLOMBIA);
        }
        setResultSauceLabs(PASSED);
    }*/
}
