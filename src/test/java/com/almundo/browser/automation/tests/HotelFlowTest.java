package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPage.PassengerSection;
import com.almundo.browser.automation.pages.ResultsPage.HotelesDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.HotelesResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class HotelFlowTest extends TestBaseSetup {

    private HotelesResultsPage hotelesResultsPage = null;
    private HotelesDetailPage hotelesDetailPage = null;
    private CheckOutPage checkOutPage = null;

    @BeforeClass
    private void initDataTripList() {
        basePage = new BasePage(driver);
        basePage.hotelesDataTrip().getHotelesDataTripList();

        checkOutPage = initCheckOutPage();
        checkOutPage.passengerSection().getPassengersList();
        checkOutPage.creditCardSection().getCreditCardList();
        checkOutPage.billingSection().getBillingList();
        checkOutPage.contactSection().getContactList();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        PassengerSection.passengerJsonList = new JSONArray();

    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void hotelReservationFirstOptionFlow() throws InterruptedException {

        PageUtils.waitElementForVisibility(driver, basePage.hotelesIcon, 10, "Hoteles icon");
        basePage.hotelesIcon.click();

        basePage.hotelesDataTrip().getHotelDataTripItinerary("miami_10days_2adults_2childs_1room");

        basePage.hotelesDataTrip().setDestination(basePage.hotelesDataTrip().destinationAuto, basePage.hotelesDataTrip().destinationFull);

        basePage.hotelesDataTrip().selectDateFromCalendar(basePage.hotelesDataTrip().checkinCalendar, basePage.hotelesDataTrip().startDate);
        basePage.hotelesDataTrip().selectDateFromCalendar(basePage.hotelesDataTrip().checkoutCalendar, basePage.hotelesDataTrip().endDate);

        numPassengers = basePage.hotelesDataTrip().selectPassenger(basePage.hotelesDataTrip().adults, basePage.hotelesDataTrip().childs, basePage.hotelesDataTrip().rooms);

        hotelesResultsPage = basePage.hotelesDataTrip().clickBuscarBtn();

        Assert.assertTrue(hotelesResultsPage.vacancy());

        hotelesDetailPage = hotelesResultsPage.clickVerHotelBtn(0);
        hotelesDetailPage.clickVerHabitacionesBtn();

        checkOutPage = hotelesDetailPage.clickReservarAhoraBtn();

        checkOutPage.passengerSection().getPassengerData("adult_female_dni_native");
        checkOutPage.passengerSection().getPassengerData("adult_female_dni_native");
        checkOutPage.passengerSection().getPassengerData("child_female_dni_native");
        checkOutPage.passengerSection().getPassengerData("child_female_dni_native");

        checkOutPage.creditCardSection().getCreditCardData("amex");
        checkOutPage.billingSection().getBillingData("local_Billing");
        checkOutPage.contactSection().getContactData("contact_cell_phone");

        checkOutPage.populateCheckOutPage(numPassengers,
                                         checkOutPage.passengerSection().passengerJsonList,
                                         checkOutPage.creditCardSection().creditCardData,
                                         checkOutPage.billingSection().billingData,
                                         checkOutPage.contactSection().contactData);

    }
}
