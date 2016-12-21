package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPage.PassengerSection;
import com.almundo.browser.automation.pages.ResultsPage.AutosResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 20/12/16.
 */
public class AutosFlowTest extends TestBaseSetup {

    private AutosResultsPage autosResultsPage = null;
    private CheckOutPage checkOutPage = null;

    @BeforeClass
    private void initDataTripList() {
        basePage = new BasePage(driver);
        basePage.autosDataTrip().getAutosDataTripList();
//
//        checkOutPage = initCheckOutPage();
//        checkOutPage.passengerSection().getPassengersList();
//        checkOutPage.creditCardSection().getCreditCardList();
//        checkOutPage.billingSection().getBillingList();
//        checkOutPage.contactSection().getContactList();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        PassengerSection.passengerJsonList = new JSONArray();

    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void autosReservationFirstOptionFlow() throws InterruptedException {
        PageUtils.waitElementForVisibility(driver, basePage.autosIcon, 10, "Autos icon");
        basePage.autosIcon.click();

        basePage.autosDataTrip().getAutosDataTripItinerary("capital_10days_mayor_24");

        basePage.autosDataTrip().setOrigin(basePage.autosDataTrip().originAuto, basePage.autosDataTrip().originFull);
        basePage.autosDataTrip().setDestination(basePage.autosDataTrip().destinationAuto, basePage.autosDataTrip().destinationFull);

        basePage.autosDataTrip().selectDateFromCalendar(basePage.autosDataTrip().pickUpDateCalendar, basePage.autosDataTrip().startDate);
        basePage.autosDataTrip().selectDateFromCalendar(basePage.autosDataTrip().dropOffDateCalendar, basePage.autosDataTrip().endDate);



    }

}
