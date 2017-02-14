package com.almundo.browser.automation.tests.Flights;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.BasePage.VuelosDataTrip;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPage.PassengerSection;
import com.almundo.browser.automation.pages.ResultsPage.VuelosResultsPage;
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

public class LoginFlowTest extends TestBaseSetup {

    private VuelosResultsPage vuelosResultsPage = null;
    private CheckOutPage checkOutPage = null;

    private VuelosDataTrip vuelosDataTrip = null;
    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initDataTripList() {
        dataManagement.getVuelosDataTripList();

        checkOutPage = initCheckOutPage();
        checkOutPage.passengerSection().getPassengersList();
        checkOutPage.paymentSection().getPaymentList();
        checkOutPage.billingSection().getBillingList();
        checkOutPage.contactSection().getContactList();
    }

    @BeforeMethod
    private void doLogin(){
        LoginPopUp loginPopUp = initLoginPopUp();
        loginPopUp.setLoginEmailTxt("automationthings@gmail.com");
        loginPopUp.setLoginPasswordTxt("gabi1981ce");
        basePage = loginPopUp.clickIngresarBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        PassengerSection.passengerJsonList = new JSONArray();

    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void vueloIntLoginReservationFlow() {
        logTestTitle("Vuelo Flow - International - 10 days - 2 Adults/2 Childs - Turista - " + countryPar );

        PageUtils.waitElementForVisibility(driver, basePage.vuelosIcon, 10, "Vuelos icon");
        basePage.vuelosIcon.click();

        dataManagement.getVuelosDataTripItinerary("miami_10days_2adults_2childs_turista");

        vuelosDataTrip = basePage.vuelosDataTrip();

        vuelosDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        vuelosDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);

        vuelosDataTrip.selectDateFromCalendar(vuelosDataTrip.departureFlightsCalendar, dataManagement.startDate);
        vuelosDataTrip.selectDateFromCalendar(vuelosDataTrip.arrivalFlightsCalendar, dataManagement.endDate);

        //numPassengers = vuelosDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
        vuelosDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);

        vuelosDataTrip.selectClass(dataManagement.flightClass);

        vuelosResultsPage = vuelosDataTrip.clickBuscarBtn();

        Assert.assertTrue(vuelosResultsPage.vacancy());

        vuelosResultsPage.clickTicketIdaRdb();
        vuelosResultsPage.clickTicketVuelta();
        checkOutPage = vuelosResultsPage.clickComprarBtn(0);

        checkOutPage.passengerSection().getPassengerData("adult_male_native");
        checkOutPage.passengerSection().getPassengerData("adult_female_native");
        checkOutPage.passengerSection().getPassengerData("child_male_native");
        checkOutPage.passengerSection().getPassengerData("child_male_native");

        checkOutPage.paymentSection().getPaymentData("1_amex_amex");
        checkOutPage.billingSection().getBillingData("local_Billing_v2");
        checkOutPage.contactSection().getContactData("contact_cell_phone");

        //checkOutPage.populateCheckOutPage(numPassengers,
//                checkOutPage.passengerSection().passengerJsonList,
//                checkOutPage.paymentSection().paymentData,
//                checkOutPage.billingSection().billingData,
//                checkOutPage.contactSection().contactData, "VuelosCheckOutPageInternational");

    }

    @Test
    public void vueloDomLoginReservationFlow() {
        logTestTitle("Vuelo Flow - Domestic - 20 days - 2 Adults - Todas - " + countryPar );

        PageUtils.waitElementForVisibility(driver, basePage.vuelosIcon, 10, "Vuelos icon");
        basePage.vuelosIcon.click();

        dataManagement.getVuelosDataTripItinerary("domestic_20days_2adults_todas");

        vuelosDataTrip = basePage.vuelosDataTrip();

        vuelosDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        vuelosDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);

        vuelosDataTrip.selectDateFromCalendar(vuelosDataTrip.departureFlightsCalendar, dataManagement.startDate);
        vuelosDataTrip.selectDateFromCalendar(vuelosDataTrip.arrivalFlightsCalendar, dataManagement.endDate);

        //numPassengers = vuelosDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs);
        vuelosDataTrip.selectChildAgeRange(dataManagement.childAgeRange, dataManagement.childs);

        vuelosDataTrip.selectClass(dataManagement.flightClass);

        vuelosResultsPage = vuelosDataTrip.clickBuscarBtn();

        Assert.assertTrue(vuelosResultsPage.vacancy());

        vuelosResultsPage.clickTicketIdaRdb();
        vuelosResultsPage.clickTicketVuelta();
        checkOutPage = vuelosResultsPage.clickComprarBtn(0);

        checkOutPage.passengerSection().getPassengerData("adult_female_foreign");
        checkOutPage.passengerSection().getPassengerData("adult_female_foreign");

        checkOutPage.paymentSection().getPaymentData("1_amex_amex");
        checkOutPage.billingSection().getBillingData("local_Billing_v2");
        checkOutPage.contactSection().getContactData("contact_phone");

        //checkOutPage.populateCheckOutPage(numPassengers,
//                checkOutPage.passengerSection().passengerJsonList,
//                checkOutPage.paymentSection().paymentData,
//                checkOutPage.billingSection().billingData,
//                checkOutPage.contactSection().contactData, "VuelosCheckOutPageDomestic");

    }
}