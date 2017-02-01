package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPage.ConfirmationPage;
import com.almundo.browser.automation.pages.CheckOutPage.PassengerSection;
import com.almundo.browser.automation.pages.ResultsPage.AutosResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 20/12/16.
 */
public class AutosFlowTest extends TestBaseSetup {

    private AutosResultsPage autosResultsPage = null;
    private CheckOutPage checkOutPage = null;
    private ConfirmationPage confirmationPage = null;

    //TODO: overload populateCheckOutPage
    private int carDrivers = 1;

    @BeforeClass
    private void initDataTripList() {
        basePage = new BasePage(driver);
        basePage.autosDataTrip().getAutosDataTripList();

        checkOutPage = initCheckOutPage();
        checkOutPage.passengerSection().getPassengersList();
        checkOutPage.paymentSection().getPaymentList();
        checkOutPage.billingSection().getBillingList();
        checkOutPage.contactSection().getContactList();
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
    public void autosDomReservationFlow() {
        logTestTitle("Autos Flow - Domestic - 10 days - " + countryPar );

        PageUtils.waitElementForVisibility(driver, basePage.autosIcon, 10, "Autos icon");
        basePage.autosIcon.click();

        basePage.autosDataTrip().getAutosDataTripItinerary("capital_10days_entre_21_24");

        basePage.autosDataTrip().setOrigin(basePage.autosDataTrip().originAuto, basePage.autosDataTrip().originFull);
        basePage.autosDataTrip().setDestination(basePage.autosDataTrip().destinationAuto, basePage.autosDataTrip().destinationFull);

        basePage.autosDataTrip().selectDateFromCalendar(basePage.autosDataTrip().pickUpDateCalendar, basePage.autosDataTrip().startDate);
        basePage.autosDataTrip().selectDateFromCalendar(basePage.autosDataTrip().dropOffDateCalendar, basePage.autosDataTrip().endDate);

        basePage.autosDataTrip().selectPickUpTime(basePage.autosDataTrip().pickUpTime);
        basePage.autosDataTrip().selectDropOffTime(basePage.autosDataTrip().dropOffTime);

        basePage.autosDataTrip().selectAgeRange(basePage.autosDataTrip().ageRange);

        autosResultsPage = basePage.autosDataTrip().clickBuscarBtn();

        checkOutPage = autosResultsPage.clickReservarAhoraBtn();

        checkOutPage.passengerSection().getPassengerData("adult_male_native");

        checkOutPage.paymentSection().getPaymentData("1_amex_amex");
        checkOutPage.billingSection().getBillingData("local_Billing_v2");
        checkOutPage.contactSection().getContactData("contact_cell_phone");

        checkOutPage.populateCheckOutPage(carDrivers,
                checkOutPage.passengerSection().passengerJsonList,
                checkOutPage.paymentSection().paymentData,
                checkOutPage.billingSection().billingData,
                checkOutPage.contactSection().contactData, "AutosCheckOutPage");

    }

    @Test
    public void autosIntReservationFlow() {
        logTestTitle("Autos Flow - International - 10 days - " + countryPar );

        PageUtils.waitElementForVisibility(driver, basePage.autosIcon, 10, "Autos icon");
        basePage.autosIcon.click();

        basePage.autosDataTrip().getAutosDataTripItinerary("miami_10days_entre_21_24");

        basePage.autosDataTrip().setOrigin(basePage.autosDataTrip().originAuto, basePage.autosDataTrip().originFull);
        basePage.autosDataTrip().setDestination(basePage.autosDataTrip().destinationAuto, basePage.autosDataTrip().destinationFull);

        basePage.autosDataTrip().selectDateFromCalendar(basePage.autosDataTrip().pickUpDateCalendar, basePage.autosDataTrip().startDate);
        basePage.autosDataTrip().selectDateFromCalendar(basePage.autosDataTrip().dropOffDateCalendar, basePage.autosDataTrip().endDate);

        basePage.autosDataTrip().selectPickUpTime(basePage.autosDataTrip().pickUpTime);
        basePage.autosDataTrip().selectDropOffTime(basePage.autosDataTrip().dropOffTime);

        basePage.autosDataTrip().selectAgeRange(basePage.autosDataTrip().ageRange);

        autosResultsPage = basePage.autosDataTrip().clickBuscarBtn();

        checkOutPage = autosResultsPage.clickReservarAhoraBtn();

        checkOutPage.passengerSection().getPassengerData("adult_male_native");

        checkOutPage.paymentSection().getPaymentData("1_amex_amex");
        checkOutPage.billingSection().getBillingData("local_Billing_v2");
        checkOutPage.contactSection().getContactData("contact_cell_phone");

        checkOutPage.populateCheckOutPage(carDrivers,
                checkOutPage.passengerSection().passengerJsonList,
                checkOutPage.paymentSection().paymentData,
                checkOutPage.billingSection().billingData,
                checkOutPage.contactSection().contactData, "AutosCheckOutPage");
    }

}
