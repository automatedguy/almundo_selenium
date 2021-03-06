package com.almundo.browser.automation.tests.Hotels;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.HotelsDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.ResultsPage.HotelsDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.HotelsResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.*;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class LoginFlowTest extends TestBaseSetup {

    private HotelsResultsPage hotelsResultsPage = null;
    private HotelsDetailPage hotelsDetailPage = null;
    private CheckOutPageV3 checkOutPageV3 = null;

    private HotelsDataTrip hotelsDataTrip = null;
    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initItineraryData() {
        dataManagement.setHotelsItineraryData();
    }

    @BeforeMethod
    private void doLogin(){
        JSONObject userData = dataManagement.setUserData("email");
        LoginPopUp loginPopUp = initLoginPopUp();
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());
        basePage = loginPopUp.clickIngresarBtn();
        hotelsDataTrip = basePage.clickHotelsBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.clearPassengerJsonList();
    }

    /***************************** Test Cases *****************************/

    @Test
    public void login_Int_Booking_Flow() {
        logTestTitle("International - 10 days - 2 Adults/2 Childs - 1 Room");

        dataManagement.setHotelsDataTripItinerary(MIA_10D_2A_2C_1R);

        hotelsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
        hotelsDataTrip.setDate(hotelsDataTrip.getCheckinCalendar(), dataManagement.getStartDate());
        hotelsDataTrip.setDate(hotelsDataTrip.getCheckoutCalendar(), dataManagement.getEndDate());
        hotelsDataTrip.selectPassenger(dataManagement.getAdults(), dataManagement.getChilds(), dataManagement.getRooms());
        hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

        Assert.assertTrue(hotelsResultsPage.vacancy());
        hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

        PageUtils.switchToNewTab(driver);
        hotelsDetailPage.clickVerHabitacionesBtn();
        checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);

        dataManagement.setPassengerData(ADULT_FEMALE_NATIVE);
        dataManagement.setPassengerData(ADULT_FEMALE_NATIVE);
        dataManagement.setPassengerData(CHILD_FEMALE_NATIVE);
        dataManagement.setPassengerData(CHILD_FEMALE_NATIVE);

        checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);
        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                                             RANDOM, dataManagement.getBillingData(LOCAL_BILLING),
                                              dataManagement.getContactData(CONTACT_PHONE), HOTELS_CHECKOUT_DOM);
    }

    @Test
    public void login_Dom_Booking_Flow() {
        logTestTitle("Domestic - 15 days - 2 Adults - 1 Room");

        dataManagement.setHotelsDataTripItinerary(DOM01_15D_2A_1R);

        hotelsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
        hotelsDataTrip.setDate(hotelsDataTrip.getCheckinCalendar(), dataManagement.getStartDate());
        hotelsDataTrip.setDate(hotelsDataTrip.getCheckoutCalendar(), dataManagement.getEndDate());
        hotelsDataTrip.selectPassenger(dataManagement.getAdults(), dataManagement.getChilds(), dataManagement.getRooms());
        hotelsResultsPage = hotelsDataTrip.clickBuscarBtn();

        Assert.assertTrue(hotelsResultsPage.vacancy());
        hotelsDetailPage = hotelsResultsPage.clickVerHotelBtn(FIRST_OPTION);

        PageUtils.switchToNewTab(driver);
        hotelsDetailPage.clickVerHabitacionesBtn();
        checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);

        dataManagement.setPassengerData(ADULT_FEMALE_NATIVE);
        dataManagement.setPassengerData(ADULT_FEMALE_NATIVE);

        checkOutPageV3 = hotelsDetailPage.clickReservarAhoraV3Btn(FIRST_OPTION);
        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                                             RANDOM, dataManagement.getBillingData(LOCAL_BILLING),
                                              dataManagement.getContactData(CONTACT_PHONE), HOTELS_CHECKOUT_DOM);
    }
}