package com.almundo.browser.automation.tests.Hotels;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.FacebookLoginPopUp;
import com.almundo.browser.automation.pages.BasePage.HotelesDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPage.PassengerSection;
import com.almundo.browser.automation.pages.ResultsPage.HotelesDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.HotelesResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class FacebookLoginFlowTest extends TestBaseSetup {

    private HotelesResultsPage hotelesResultsPage = null;
    private HotelesDetailPage hotelesDetailPage = null;
    private CheckOutPage checkOutPage = null;

    private HotelesDataTrip hotelesDataTrip = null;
    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initDataTripList() {
        dataManagement.getHotelesDataTripList();

        checkOutPage = initCheckOutPage();
        checkOutPage.passengerSection().getPassengersList();
        checkOutPage.paymentSection().getPaymentList();
        checkOutPage.billingSection().getBillingList();
        checkOutPage.contactSection().getContactList();
    }

    @BeforeMethod
    private void doLogin(){
        LoginPopUp loginPopUp = initLoginPopUp();
        FacebookLoginPopUp facebookLoginPopUp = loginPopUp.clickFacebookLoginBtn();
        PageUtils.setFocusOnChildWindow(driver);
        facebookLoginPopUp.setEmailTxt("almundoqastuff@gmail.com");
        facebookLoginPopUp.setPassTxt("almundo#01");
        basePage = facebookLoginPopUp.clickFacebookLoginBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        PassengerSection.passengerJsonList = new JSONArray();

    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void hotelIntLoginReservationFlow() {
        logTestTitle("Hotel Flow - International - 10 days - 2 Adults/2 Childs - 1 Room - " + countryPar );



        PageUtils.waitElementForClickable(driver, basePage.hotelesIcon, 10, "Hoteles icon");
        basePage.hotelesIcon.click();

        dataManagement.getHotelDataTripItinerary("miami_10days_2adults_2childs_1room");

        hotelesDataTrip = basePage.hotelesDataTrip();

        hotelesDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);

        hotelesDataTrip.selectDateFromCalendar(basePage.hotelesDataTrip().checkinCalendar, dataManagement.startDate);
        hotelesDataTrip.selectDateFromCalendar(basePage.hotelesDataTrip().checkoutCalendar, dataManagement.endDate);

        numPassengers = hotelesDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);

        hotelesResultsPage = hotelesDataTrip.clickBuscarBtn();

        Assert.assertTrue(hotelesResultsPage.vacancy());

        hotelesDetailPage = hotelesResultsPage.clickVerHotelBtn(0);
        hotelesDetailPage.clickVerHabitacionesBtn();

        checkOutPage = hotelesDetailPage.clickReservarAhoraBtn();

        checkOutPage.passengerSection().getPassengerData("adult_female_native");
        checkOutPage.passengerSection().getPassengerData("adult_female_native");
        checkOutPage.passengerSection().getPassengerData("child_female_native");
        checkOutPage.passengerSection().getPassengerData("child_female_native");

        checkOutPage.paymentSection().getPaymentData("1_amex_amex");
        checkOutPage.billingSection().getBillingData("local_Billing_v2");
        checkOutPage.contactSection().getContactData("contact_cell_phone");

        checkOutPage.populateCheckOutPage(numPassengers,
                                         checkOutPage.passengerSection().passengerJsonList,
                                         checkOutPage.paymentSection().paymentData,
                                         checkOutPage.billingSection().billingData,
                                         checkOutPage.contactSection().contactData, "HotelesCheckOutPageInternational");


    }

    @Test
    public void hotelDomLoginReservationFlow() {
        logTestTitle("Hotel Flow - Domestic - 15 days - 2 Adults - 1 Room - " + countryPar );

        PageUtils.waitElementForVisibility(driver, basePage.hotelesIcon, 10, "Hoteles icon");
        basePage.hotelesIcon.click();

        dataManagement.getHotelDataTripItinerary("domestic01_15days_2adults_1room");

        hotelesDataTrip = basePage.hotelesDataTrip();

        hotelesDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);

        hotelesDataTrip.selectDateFromCalendar(hotelesDataTrip.checkinCalendar, dataManagement.startDate);
        hotelesDataTrip.selectDateFromCalendar(hotelesDataTrip.checkoutCalendar, dataManagement.endDate);

        numPassengers = hotelesDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);

        hotelesResultsPage = hotelesDataTrip.clickBuscarBtn();

        Assert.assertTrue(hotelesResultsPage.vacancy());

        hotelesDetailPage = hotelesResultsPage.clickVerHotelBtn(0);
        hotelesDetailPage.clickVerHabitacionesBtn();

        checkOutPage = hotelesDetailPage.clickReservarAhoraBtn();

        checkOutPage.passengerSection().getPassengerData("adult_female_native");
        checkOutPage.passengerSection().getPassengerData("adult_female_native");

        checkOutPage.paymentSection().getPaymentData("1_amex_amex");
        checkOutPage.billingSection().getBillingData("local_Billing_v2");
        checkOutPage.contactSection().getContactData("contact_phone");

        checkOutPage.populateCheckOutPage(numPassengers,
                checkOutPage.passengerSection().passengerJsonList,
                checkOutPage.paymentSection().paymentData,
                checkOutPage.billingSection().billingData,
                checkOutPage.contactSection().contactData, "HotelesCheckOutPageDomestic");


    }
}
