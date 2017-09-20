package com.almundo.browser.automation.tests.ClubAlMundo;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.HotelsDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.BasePage.TripsDataTrip;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ThanksPageV3;
import com.almundo.browser.automation.pages.ResultsPage.HotelsDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.HotelsResultsPage;
import com.almundo.browser.automation.pages.ResultsPage.TripsDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.TripsResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.*;
import static com.almundo.browser.automation.utils.Constants.Results.PASSED;
import static com.almundo.browser.automation.utils.PageUtils.userNameOk;

/**
 * Created by gabrielcespedes on 26/05/17.
 */
public class DetailsLoginTest extends TestBaseSetup {

    private LoginPopUp loginPopUp = null;

    private DataManagement dataManagement = new DataManagement();

    /********** Hotels Related Objects and Pages ***********/
    private HotelsDataTrip hotelsDataTrip = null;
    private HotelsResultsPage hotelsResultsPage = null;
    private HotelsDetailPage hotelsDetailPage = null;

    /********** Trips Related Objects and Pages ***********/

    private TripsDataTrip tripsDataTrip = null;
    private TripsResultsPage tripsResultsPage = null;
    private TripsDetailPage tripsDetailPage = null;

    /********** Common Objects for (V3): Checkout, Agreement and Confirmation Pages ***********/
    private CheckOutPageV3 checkOutPageV3 = null;
    private ThanksPageV3 thanksPageV3 = null;

    JSONObject userData = null;

    private void getHotelsAssertionInfo(){
        thanksPageAssertInfo.setHotelsDetailInfo(checkOutPageV3.breakDownSectionV3().getHotelDetailContent());
        thanksPageAssertInfo.setContactEmailEntered(checkOutPageV3.contactSection().getContactEmail());
    }

    @BeforeClass
    private void initUserList() {
        dataManagement.getUsersDataList();
        userData = dataManagement.getUserData("email");
    }

    @BeforeMethod
    private void closeLoginPopUp(){
        loginPopUp = initLoginPopUp();
        loginPopUp.clickCloseLoginBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    @Test
    public void hotelsDetailsLogin(){
        logTestTitle("Search Hotel And Login With Email on Detail Page");

        dataManagement.getHotelsItineraryData();
        dataManagement.getHotelsDataTripItinerary(MIA_10D_2A_2C_1R);

        basePage.clickHotelsBtn();
        hotelsDataTrip = basePage.hotelsDataTrip();
        hotelsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.startDate);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);
        hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);

        hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

        Assert.assertTrue(hotelsResultsPage.vacancy());

        hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

        PageUtils.switchToNewTab(driver);

        loginPopUp = basePage.headerSection().clickMyAccountMenuLnk();
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());

        hotelsDetailPage = loginPopUp.clickIngresarOnHotelsDetailBtn();

        Assert.assertTrue(userNameOk(userData.get("name").toString(), basePage.headerSection().textLoggedIntLnk.getText()));

        hotelsDetailPage.clickVerHabitacionesBtn();

        dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);
        dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);
        dataManagement.getPassengerData(CHILD_FEMALE_NATIVE);
        dataManagement.getPassengerData(CHILD_FEMALE_NATIVE);

        checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void hotelsDetailsBookingLogin(){
        logTestTitle("Search Hotel And Login With Email on Detail Page");

        dataManagement.getHotelsItineraryData();
        dataManagement.getHotelsDataTripItinerary(MIA_10D_2A_2C_1R);

        basePage.clickHotelsBtn();
        hotelsDataTrip = basePage.hotelsDataTrip();
        hotelsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkinCalendar, dataManagement.startDate);
        hotelsDataTrip.selectDateFromCalendar(hotelsDataTrip.checkoutCalendar, dataManagement.endDate);
        hotelsDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);

        hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

        Assert.assertTrue(hotelsResultsPage.vacancy());

        hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

        PageUtils.switchToNewTab(driver);

        loginPopUp = basePage.headerSection().clickMyAccountMenuLnk();
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());

        hotelsDetailPage = loginPopUp.clickIngresarOnHotelsDetailBtn();

        Assert.assertTrue(userNameOk(userData.get("name").toString(), basePage.headerSection().textLoggedIntLnk.getText()));

        hotelsDetailPage.clickVerHabitacionesBtn();

        dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);
        dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);
        dataManagement.getPassengerData(CHILD_FEMALE_NATIVE);
        dataManagement.getPassengerData(CHILD_FEMALE_NATIVE);

        checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);

        thanksPageAssertInfo.setFinalAmountPaid(checkOutPageV3.breakDownSectionV3().getFinalPriceString());

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                REWARDS_VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        getHotelsAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.finalAmountPaid));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.contactEmailEntered));
        Assert.assertTrue(thanksPageV3.isFlightDetailInfoOk(thanksPageAssertInfo.flightDetailInfo));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

    @Test
    public void tripsDetailsLogin(){
        logTestTitle("Search Trip And Login With Email on Detail Page");

        dataManagement.getTripsItineraryData();
        dataManagement.getTripsDataTripItinerary(MIA_10D_2A_2C_1R);

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

        loginPopUp = basePage.headerSection().clickMyAccountMenuLnk();
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());

        tripsDetailPage = loginPopUp.clickIngresarOnTripsDetailBtn();

        Assert.assertTrue(userNameOk(userData.get("name").toString(), basePage.headerSection().textLoggedIntLnk.getText()));

        checkOutPageV3 = tripsDetailPage.clickComprarBtnV3(FIRST_OPTION);
        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList,
                MASTER_1, dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_CELL_PHONE), TRIPS_CHECKOUT_INTV3);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }
}
