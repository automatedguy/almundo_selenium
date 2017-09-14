package com.almundo.browser.automation.tests.Hotels;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.HotelsDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
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

public class FlowTest extends TestBaseSetup {

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
    private void closeLoginPopUp(){
        LoginPopUp loginPopUp = initLoginPopUp();
        loginPopUp.clickCloseLoginBtn();
        hotelsDataTrip = basePage.clickHotelsBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    private void getAssertionInfo(){
        thanksPageAssertInfo.finalAmountPaid = checkOutPageV3.breakDownSectionV3().getFinalPriceString();
        thanksPageAssertInfo.hotelDetailInfo = checkOutPageV3.breakDownSectionV3().getHotelDetailContent();
        thanksPageAssertInfo.contactEmailEntered = checkOutPageV3.contactSection().getContactEmail();
    }

    /***************************** Test Cases *****************************/

    @Test
    public void int_Booking_Flow() {
        logTestTitle("International - 10 days - 2 Adults/2 Childs - 1 Room");

        dataManagement.getHotelsDataTripItinerary(MIA_10D_2A_2C_1R);

        hotelsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.startDate);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);
        hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
        hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

        Assert.assertTrue(hotelsResultsPage.vacancy());

        hotelsResultsPage.clickPrePaid();

        hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

        PageUtils.switchToNewTab(driver);
        hotelsDetailPage.clickVerHabitacionesBtn();

        dataManagement.getPassengerData(ADULT_MALE_NATIVE);
        dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);
        dataManagement.getPassengerData(CHILD_FEMALE_NATIVE);
        dataManagement.getPassengerData(CHILD_FEMALE_NATIVE);

        checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, VISA_1,
                                               dataManagement.getBillingData(LOCAL_BILLING),
                                               dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);
        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.finalAmountPaid));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.contactEmailEntered));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.hotelDetailInfo));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void dom_Booking_Flow() {
        logTestTitle("Domestic - 15 days - 2 Adults - 1 Room");

        dataManagement.getHotelsDataTripItinerary(DOM01_15D_2A_1R);

        hotelsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.startDate);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);
        hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
        hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

        Assert.assertTrue(hotelsResultsPage.vacancy());

        hotelsResultsPage.clickPrePaid();

        hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

        PageUtils.switchToNewTab(driver);
        hotelsDetailPage.clickVerHabitacionesBtn();

        dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);
        dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);

        checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, MASTER_1,
                                               dataManagement.getBillingData(LOCAL_BILLING),
                                               dataManagement.getContactData(CONTACT_PHONE), HOTELS_CHECKOUT_DOM);
        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void dom_Booking_Flow_PayAtDestination() {
        logTestTitle("Domestic - Pay At Destination - 15 days - 2 Adults - 1 Room");

        dataManagement.getHotelsDataTripItinerary(DOM01_15D_2A_1R);

        hotelsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.startDate);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);
        hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
        hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

        Assert.assertTrue(hotelsResultsPage.vacancy());

        hotelsResultsPage.clickPayAtDestination();

        hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

        PageUtils.switchToNewTab(driver);

        hotelsDetailPage.clickPayAtDestination();
        hotelsDetailPage.clickVerHabitacionesBtn();

        dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);
        dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);

        checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, DESTINATION_MASTER_1,
                                        dataManagement.getBillingData(LOCAL_BILLING),
                                        dataManagement.getContactData(CONTACT_PHONE), HOTELS_CHECKOUT_DOM);

        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.finalAmountPaid));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.contactEmailEntered));
        Assert.assertTrue(thanksPageV3.isHotelDetailInfoOk(thanksPageAssertInfo.hotelDetailInfo));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }
}