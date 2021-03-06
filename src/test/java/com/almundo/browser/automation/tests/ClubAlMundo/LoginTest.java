package com.almundo.browser.automation.tests.ClubAlMundo;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.CarsDataTrip;
import com.almundo.browser.automation.pages.BasePage.FlightsDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ThanksPageV3;
import com.almundo.browser.automation.pages.ResultsPage.CarsResultsPage;
import com.almundo.browser.automation.pages.ResultsPage.FlightsResultsPage;
import com.almundo.browser.automation.utils.Constants;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.almundo.browser.automation.utils.Constants.*;
import static com.almundo.browser.automation.utils.Constants.FLIGHTS_CHECKOUT_INT;
import static com.almundo.browser.automation.utils.Constants.FlightType.ROUND_TRIP;
import static com.almundo.browser.automation.utils.Constants.NOT_RUNNING_MEXICO_COLOMBIA;
import static com.almundo.browser.automation.utils.Constants.Results.PASSED;
import static com.almundo.browser.automation.utils.PageUtils.isElementClickable;
import static com.almundo.browser.automation.utils.PageUtils.userNameOk;

/**
 * Created by leandro.efron on 8/2/2017.
 */
public class LoginTest extends TestBaseSetup {

    private LoginPopUp loginPopUp = null;
    JSONObject userData = null;
    private DataManagement dataManagement = new DataManagement();

    /********** Flights Related Objects and Pages ***********/
    private FlightsDataTrip flightsDataTrip = null;
    private FlightsResultsPage flightsResultsPage = null;

    /********** Cars Related Objects and Pages ***********/
    private CarsDataTrip carsDataTrip =  null;
    private CarsResultsPage carsResultsPage = null;


    /********** Common Objects for (V3): Checkout, Agreement and Confirmation Pages ***********/
    private CheckOutPageV3 checkOutPageV3 = null;
    private ThanksPageV3 thanksPageV3 = null;

    @BeforeClass
    private void initUserList() {
        dataManagement.setUsersDataList();
    }

    @BeforeMethod
    private void initLoginPopUpElement(){
        loginPopUp = initLoginPopUp();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.clearPassengerJsonList();
    }

    private void getFlightsAssertionInfo(){
        thanksPageAssertInfo.setFlightsDetailInfo(checkOutPageV3.breakDownSectionV3().getFlightDetailContent());
        thanksPageAssertInfo.setContactEmailEntered(checkOutPageV3.contactSection().getContactEmail());
    }

    private void getCarsAssertionInfo(){
        thanksPageAssertInfo.setCarsDetailInfo(checkOutPageV3.breakDownSectionV3().getCarsDetailContent());
        thanksPageAssertInfo.setContactEmailEntered(checkOutPageV3.contactSection().getContactEmail());
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void login_email () {
        logTestTitle("Login with email and validate menu elements");

        JSONObject userData = dataManagement.setUserData("email");
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());
        basePage = loginPopUp.clickIngresarBtn();

        // Currently is not shown.
        // Assert.assertTrue(userNameOk(userData.get("name").toString(), basePage.headerSection().textLoggedIntLnk.getText()));

        basePage.headerSection().clickMyAccountMenuLnk();

        List<String> actualList  = basePage.headerSection().getMyAccountMenuList();
        List<String> expectedList;

        if(countryPar.equals("ARGENTINA")) {
            expectedList = Constants.USER_MENU_LIST_AR;
        } else if(countryPar.equals("COLOMBIA")) {
            expectedList = Constants.USER_MENU_LIST_CO;
        } else {
                expectedList = Constants.USER_MENU_LIST_MX;
        }

        logger.info("Validating My Account menu options are displayed:");
        Assert.assertTrue((PageUtils.equalLists(actualList, expectedList, driver)), "Displayed options are not correct");

        logger.info("Logging out user");
        basePage.headerSection().clickMyAccountMenuOption("Cerrar sesión");
        PageUtils.waitImplicitly(4000);

        logger.info("Validating user is logged out");
        Assert.assertTrue(isElementClickable(driver, basePage.headerSection().myAccountMenuLnk, 10, "Login Link (from header)"));

        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void flightsLoginBooking(){
        logTestTitle("Login With Email and Book Flight using Rewards Points.");
        if(countryPar.equals(ARGENTINA)) {
            loginPopUp.clickCloseLoginBtn();
            loginPopUp = basePage.headerSection().clickMyAccountMenuLnk();
            dataManagement.setUsersDataList();
            userData = dataManagement.setUserData("email");
            loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());
            basePage = loginPopUp.clickIngresarBtn();

            // Assert.assertTrue(userNameOk(userData.get("name").toString(), basePage.headerSection().textLoggedIntLnk.getText()));

            dataManagement.setFlightsItineraryData();
            dataManagement.setRoundTripDataTripItinerary(MIAMI_10D_2A_2C_TOURIST);

            flightsDataTrip = basePage.clickFlightsBtn();
            flightsDataTrip.selectFlightType(ROUND_TRIP);
            flightsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
            flightsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
            flightsDataTrip.setDate(flightsDataTrip.getDepartureFlightsCalendar(), dataManagement.getStartDate());
            flightsDataTrip.setDate(flightsDataTrip.getArrivalFlightsCalendar(), dataManagement.getEndDate());
            flightsDataTrip.selectPassenger(dataManagement.getAdults(), dataManagement.getChilds());
            flightsDataTrip.selectChildAgeRange(dataManagement.getChildAgeRange(), dataManagement.getChilds());
            flightsDataTrip.selectClass(dataManagement.getFlightClass());

            flightsResultsPage = flightsDataTrip.clickBuscarBtn();

            flightsResultsPage.clickTicketIdaRdb(FIRST_OPTION);
            flightsResultsPage.clickTicketVuelta(FIRST_OPTION + 1);

            dataManagement.setPassengerData(ADULT_FEMALE_FOREIGN);
            dataManagement.setPassengerData(CHILD_MALE_NATIVE);
            dataManagement.setPassengerData(CHILD_MALE_NATIVE);

            checkOutPageV3 = flightsResultsPage.clickComprarV3Btn(FIRST_OPTION);

            thanksPageAssertInfo.setFinalAmountPaid(checkOutPageV3.breakDownSectionV3().getFinalPriceString());

            checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                                    REWARDS_VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                                    dataManagement.getContactData(CONTACT_CELL_PHONE), FLIGHTS_CHECKOUT_INT);
            getFlightsAssertionInfo();
            thanksPageV3 = checkOutPageV3.clickComprarBtn();

            Assert.assertTrue(thanksPageV3.confirmationOk());
            //Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.finalAmountPaid));
            Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
            Assert.assertTrue(thanksPageV3.isFlightDetailInfoOk(thanksPageAssertInfo.getFlightDetailInfo()));
            // Assert.assertTrue(thanksPageV3.isPassengersInfoOk());
        }else {
            logger.info(NOT_RUNNING_MEXICO_COLOMBIA);
        }
        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void carsLoginBooking(){
        logTestTitle("Search Car And Login With Email");
        if(countryPar.equals(ARGENTINA)) {
            loginPopUp.clickCloseLoginBtn();
            loginPopUp = basePage.headerSection().clickMyAccountMenuLnk();
            dataManagement.setUsersDataList();
            userData = dataManagement.setUserData("email");
            loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());
            basePage = loginPopUp.clickIngresarBtn();

            // Assert.assertTrue(userNameOk(userData.get("name").toString(), basePage.headerSection().textLoggedIntLnk.getText()));

            dataManagement.setCarsItineraryData();
            dataManagement.setCarsDataTripItinerary(MIA_10D_21_24);

            basePage.clickCarsBtn();
            carsDataTrip = basePage.carsDataTrip();
            carsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
            carsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
            carsDataTrip.setDate(carsDataTrip.getPickUpDateCalendar(), dataManagement.getStartDate());
            carsDataTrip.setDate(carsDataTrip.getDropOffDateCalendar(), dataManagement.getEndDate());
            carsDataTrip.selectPickUpTime(dataManagement.getPickUpTime());
            carsDataTrip.selectDropOffTime(dataManagement.getDropOffTime());
            carsDataTrip.selectAgeRange(dataManagement.getAgeRange());
            carsResultsPage = carsDataTrip.clickBuscarBtn();

            Assert.assertTrue(carsResultsPage.vacancy());
            Assert.assertTrue(carsResultsPage.processed());


            checkOutPageV3 = carsResultsPage.clickReservarAhoraBtn(FIRST_OPTION);

            // dataManagement.setPassengerData(ADULT_MALE_NATIVE);

            thanksPageAssertInfo.setFinalAmountPaid(checkOutPageV3.breakDownSectionV3().getFinalPriceString());

            checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                                    REWARDS_VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                                    dataManagement.getContactData(CONTACT_CELL_PHONE), CARS_CHECKOUT);
            getCarsAssertionInfo();
            thanksPageV3 = checkOutPageV3.clickComprarBtn();

            Assert.assertTrue(thanksPageV3.confirmationOk());
            //Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.finalAmountPaid));
            Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
            Assert.assertTrue(thanksPageV3.isCarsDetailInfoOk(thanksPageAssertInfo.getCarsDetailInfo()));
            Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        }
        else {
            logger.info(NOT_RUNNING_MEXICO_COLOMBIA);
        }
        setResultSauceLabs(PASSED);
    }
}