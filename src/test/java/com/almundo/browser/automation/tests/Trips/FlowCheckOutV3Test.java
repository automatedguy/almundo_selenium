package com.almundo.browser.automation.tests.Trips;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.BasePage.VueloHotelDataTrip;
import com.almundo.browser.automation.pages.CheckOutPage.ConfirmationPage;
import com.almundo.browser.automation.pages.CheckOutPage.PassengerSection;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.ResultsPage.VueloHotelDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.VueloHotelResultsPage;
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

public class FlowCheckOutV3Test extends TestBaseSetup {

    private VueloHotelResultsPage vueloHotelResultsPage = null;
    private VueloHotelDetailPage vueloHotelDetailPage = null;
    private CheckOutPageV3 checkOutPage = null;
    private ConfirmationPage confirmationPage = null;

    private VueloHotelDataTrip vueloHotelDataTrip = null;
    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initDataTripList() {
        dataManagement.getVueloHotelDataTripList();

        checkOutPage = initCheckOutPageV3();
        checkOutPage.passengerSectionV3().getPassengersList();
        checkOutPage.paymentSectionV3().getPaymentList();
        checkOutPage.billingSectionV3().getBillingList();
        checkOutPage.contactSectionV3().getContactList();
    }

    @BeforeMethod
    private void closeLoginPopUp(){
        LoginPopUp loginPopUp = initLoginPopUp();
        loginPopUp.clickCloseLoginBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        PassengerSection.passengerJsonList = new JSONArray();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void vueloHotelIntReservationFlow() {
        logTestTitle("Vuelo+Hotel - Int - 20 days - 2 Adults/2 Childs - 1 Room - " + countryPar );

        PageUtils.waitElementForVisibility(driver, basePage.vueloHotelIcon, 10, "Vuelo+Hotel icon");
        basePage.vueloHotelIcon.click();

        dataManagement.getVueloHotelDataTripItinerary("int02_20days_2adults_1childs_1room");

        vueloHotelDataTrip = basePage.vueloHotelDataTrip();

        vueloHotelDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        vueloHotelDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);

        vueloHotelDataTrip.selectDateFromCalendar(vueloHotelDataTrip.departureCalendar, dataManagement.startDate);
        vueloHotelDataTrip.selectDateFromCalendar(vueloHotelDataTrip.arrivalCalendar, dataManagement.endDate);

        //numPassengers = vueloHotelDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);

        vueloHotelResultsPage = vueloHotelDataTrip.clickBuscarBtn();

        Assert.assertTrue(vueloHotelResultsPage.vacancy());

        vueloHotelResultsPage.clickElegirBtn(0);
        vueloHotelDetailPage = vueloHotelResultsPage.clickContinuarBtn();
        vueloHotelDetailPage.clickVerHabitacionBtn();

        checkOutPage = vueloHotelDetailPage.clickComprarV3Btn(0);

        checkOutPage.passengerSectionV3().getPassengerData("adult_female_foreign");
        checkOutPage.passengerSectionV3().getPassengerData("adult_female_foreign");
        checkOutPage.passengerSectionV3().getPassengerData("child_female_native");

        checkOutPage.paymentSectionV3().getPaymentData("6_visa_visa");
        checkOutPage.billingSectionV3().getBillingData("local_Billing_v2");
        checkOutPage.contactSectionV3().getContactData("contact_cell_phone");

        replaceUrl();

//        checkOutPage.populateCheckOutPage(numPassengers,
//                checkOutPage.passengerSectionV3().passengerJsonList,
//                checkOutPage.paymentSectionV3().paymentData,
//                checkOutPage.billingSectionV3().billingData,
//                checkOutPage.contactSectionV3().contactData, "VueloHotelCheckOutPageInternationalV3");

/*      confirmationPage = checkOutPage.clickComprarBtn();
        Assert.assertTrue(confirmationPage.confirmationOk());*/
    }

    @Test
    public void vueloHotel_2CreditCard() {
        logTestTitle("Vuelo+Hotel - 2 Credit Card payment - " + countryPar );

        PageUtils.waitElementForVisibility(driver, basePage.vueloHotelIcon, 10, "Vuelo+Hotel icon");
        basePage.vueloHotelIcon.click();

        dataManagement.getVueloHotelDataTripItinerary("int02_20days_2adults_1childs_1room");

        vueloHotelDataTrip = basePage.vueloHotelDataTrip();

        vueloHotelDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        vueloHotelDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);

        vueloHotelDataTrip.selectDateFromCalendar(vueloHotelDataTrip.departureCalendar, dataManagement.startDate);
        vueloHotelDataTrip.selectDateFromCalendar(vueloHotelDataTrip.arrivalCalendar, dataManagement.endDate);

       // numPassengers = vueloHotelDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);

        vueloHotelResultsPage = vueloHotelDataTrip.clickBuscarBtn();

        Assert.assertTrue(vueloHotelResultsPage.vacancy());

        vueloHotelResultsPage.clickElegirBtn(0);
        vueloHotelDetailPage = vueloHotelResultsPage.clickContinuarBtn();
        vueloHotelDetailPage.clickVerHabitacionBtn();

        checkOutPage = vueloHotelDetailPage.clickComprarV3Btn(0);

        checkOutPage.passengerSectionV3().getPassengerData("adult_female_foreign");
        checkOutPage.passengerSectionV3().getPassengerData("adult_female_foreign");
        checkOutPage.passengerSectionV3().getPassengerData("child_female_native");

        checkOutPage.paymentSectionV3().getPaymentData("6_visa_visa");
        checkOutPage.billingSectionV3().getBillingData("local_Billing_v2");
        checkOutPage.contactSectionV3().getContactData("contact_cell_phone");

        replaceUrl();

        checkOutPage.paymentSectionV3().clickSeveralCardsCbx();

//        checkOutPage.populateCheckOutPage(numPassengers,
//                checkOutPage.passengerSectionV3().passengerJsonList,
//                checkOutPage.paymentSectionV3().paymentData,
//                checkOutPage.paymentSectionV3().paymentData,
//                checkOutPage.billingSectionV3().billingData,
//                checkOutPage.contactSectionV3().contactData, "VueloHotelCheckOutPageInternationalV3");

/*      confirmationPage = checkOutPage.clickComprarBtn();
        Assert.assertTrue(confirmationPage.confirmationOk());*/
    }
}
