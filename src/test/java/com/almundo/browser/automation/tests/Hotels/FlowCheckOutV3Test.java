package com.almundo.browser.automation.tests.Hotels;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.HotelesDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPage.PassengerSection;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
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

public class FlowCheckOutV3Test extends TestBaseSetup {

    private HotelesResultsPage hotelesResultsPage = null;
    private HotelesDetailPage hotelesDetailPage = null;
    private CheckOutPageV3 checkOutPage = null;

    private HotelesDataTrip hotelesDataTrip = null;
    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initDataTripList() {
        dataManagement.getHotelesDataTripList();

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
    public void hotelDomReservationFlow() {
        logTestTitle("Hotel Flow - Domestic - 15 days - 2 Adults - 1 Room - " + countryPar );

        PageUtils.waitElementForVisibility(driver, basePage.hotelesIcon, 10, "Hoteles icon");
        basePage.hotelesIcon.click();

        dataManagement.getHotelDataTripItinerary("domestic01_15days_2adults_1room");

        hotelesDataTrip = basePage.hotelesDataTrip();

        hotelesDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);

        hotelesDataTrip.selectDateFromCalendar(basePage.hotelesDataTrip().checkinCalendar, dataManagement.startDate);
        hotelesDataTrip.selectDateFromCalendar(basePage.hotelesDataTrip().checkoutCalendar, dataManagement.endDate);

        numPassengers = basePage.hotelesDataTrip().selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);

        hotelesResultsPage = basePage.hotelesDataTrip().clickBuscarBtn();

        Assert.assertTrue(hotelesResultsPage.vacancy());

        hotelesDetailPage = hotelesResultsPage.clickVerHotelBtn(0);
        hotelesDetailPage.clickVerHabitacionesBtn();

        checkOutPage = hotelesDetailPage.clickReservarAhoraV3Btn();

        checkOutPage.passengerSectionV3().getPassengerData("adult_female_native");
        checkOutPage.passengerSectionV3().getPassengerData("adult_female_native");

        checkOutPage.paymentSectionV3().getPaymentData("1_amex_amex");
        checkOutPage.billingSectionV3().getBillingData("local_Billing_v2");
        checkOutPage.contactSectionV3().getContactData("contact_phone");

        replaceUrl();

        checkOutPage.populateCheckOutPage(numPassengers,
                checkOutPage.passengerSectionV3().passengerJsonList,
                checkOutPage.paymentSectionV3().paymentData,
                checkOutPage.billingSectionV3().billingData,
                checkOutPage.contactSectionV3().contactData, "HotelesCheckOutPageDomesticV3");

    }

    public void replaceUrl(){
        PageUtils.waitUrlContains(driver, 30, "cart", "URL does not contain cart");
        String actualURL = driver.getCurrentUrl();
        String newURL = actualURL.replace("cart/v2", "checkout");
        logger.info("new URL: " + newURL);
        driver.navigate().to(newURL);
        PageUtils.waitImplicitly(8000);
    }
}
