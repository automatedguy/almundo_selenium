package com.almundo.browser.automation.tests.Hotels;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.HotelesDataTrip;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPage.ConfirmationPage;
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

public class SucursalesFlowTest extends TestBaseSetup {

    private HotelesResultsPage hotelesResultsPage = null;
    private HotelesDetailPage hotelesDetailPage = null;
    private CheckOutPage checkOutPage = null;
    private ConfirmationPage confirmationPage = null;

    private HotelesDataTrip hotelesDataTrip = null;
    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initDataLists() {
        dataManagement.getHotelesDataTripList();
        dataManagement.getPassengersList();
        dataManagement.getPaymentList();
        dataManagement.getBillingList();
        dataManagement.getContactList();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void suc_Dom_Booking_Flow() {
        logTestTitle("Sucursales Hotel Flow - Domestic - 15 days - 2 Adults - 1 Room - " + countryPar );

        PageUtils.waitElementForVisibility(driver, basePage.hotelesIcon, 10, "Hoteles icon");
        basePage.hotelesIcon.click();

        dataManagement.getHotelDataTripItinerary("domestic02_20days_2adults_1room");

        hotelesDataTrip = basePage.hotelesDataTrip();

        hotelesDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);

        hotelesDataTrip.selectDateFromCalendar(hotelesDataTrip.checkinCalendar, dataManagement.startDate);
        hotelesDataTrip.selectDateFromCalendar(hotelesDataTrip.checkoutCalendar, dataManagement.endDate);

        hotelesDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);

        hotelesResultsPage = hotelesDataTrip.clickBuscarBtn();

        Assert.assertTrue(hotelesResultsPage.vacancy());

        hotelesDetailPage = hotelesResultsPage.clickVerHotelBtn(0);
        hotelesDetailPage.clickVerHabitacionesBtn();

        checkOutPage = hotelesDetailPage.clickReservarAhoraBtn();

        dataManagement.getPassengerData("adult_female_native");
        dataManagement.getPassengerData("adult_female_native");

        checkOutPage.populateCheckOutPage(dataManagement.passengerJsonList,
                                          dataManagement.getPaymentData("deposit"),
                                          dataManagement.getBillingData("local_Billing_sucursales"),
                                          dataManagement.getContactData("contact_phone"),
                                          "HotelCheckOutPageDomesticSucursal");
    }
}
