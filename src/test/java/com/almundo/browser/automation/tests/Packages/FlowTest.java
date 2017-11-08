package com.almundo.browser.automation.tests.Packages;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.BasePage.PackagesDataTrip;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ThanksPageV3;
import com.almundo.browser.automation.pages.ResultsPage.PackagesDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.PackagesResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.*;
import static com.almundo.browser.automation.utils.Constants.Results.PASSED;

public class FlowTest extends TestBaseSetup {

    private final int option = 5;
    private final int season = 1;

    private PackagesDataTrip packagesDataTrip = null;
    private PackagesResultsPage packagesResultsPage = null;
    private PackagesDetailPage packagesDetailPage = null;
    private CheckOutPageV3 checkOutPageV3 =  null;
    private ThanksPageV3 thanksPageV3 =  null;
    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initItineraryData(){ dataManagement.setPackagesInitneraryData();}

    @BeforeMethod
    private void closeLoginPopUp(){
        LoginPopUp loginPopUp = initLoginPopUp();
        loginPopUp.clickCloseLoginBtn();
        packagesDataTrip = basePage.clickPackagesBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.clearPassengerJsonList();
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void packagesBookingToSomePlace() {
        logTestTitle("Booking Packages To Some Place.");

        dataManagement.setPackagesDataTripItinerary(PKG_10D_2A);

        packagesDataTrip.selectOrigin(dataManagement.getOriginFull());
        packagesDataTrip.setDestination(option);
        packagesDataTrip.setSeason(season);
        packagesDataTrip.setPassenger(dataManagement.getAdults(), dataManagement.getChilds(), dataManagement.getRooms());
        packagesResultsPage = packagesDataTrip.clickBuscarBtn();
        packagesDetailPage = packagesResultsPage.verPaqueteClick(SECOND_OPTION_O2);

        PageUtils.switchToNewTab(driver);

        checkOutPageV3 = packagesDetailPage.comprarClick();

        dataManagement.setPassengerData(ADULT_MALE_NATIVE);
        dataManagement.setPassengerData(ADULT_FEMALE_NATIVE);

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(), VISA_1,
                                        dataManagement.getBillingData(LOCAL_BILLING),
                                        dataManagement.getContactData(CONTACT_CELL_PHONE), HOTELS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void packagesBookingToSomePlace2Cards() {
        logTestTitle("Booking Packages To Some Place.");

        dataManagement.setPackagesDataTripItinerary(PKG_10D_2A);

        packagesDataTrip.selectOrigin(dataManagement.getOriginFull());
        packagesDataTrip.setDestination(option);
        packagesDataTrip.setSeason(season);
        packagesDataTrip.setPassenger(dataManagement.getAdults(), dataManagement.getChilds(), dataManagement.getRooms());
        packagesResultsPage = packagesDataTrip.clickBuscarBtn();
        packagesDetailPage = packagesResultsPage.verPaqueteClick(FIRST_OPTION_O1);

        PageUtils.switchToNewTab(driver);

        checkOutPageV3 = packagesDetailPage.comprarClick();

        dataManagement.setPassengerData(ADULT_MALE_NATIVE);
        dataManagement.setPassengerData(ADULT_FEMALE_NATIVE);

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(), TWOCARDS_VISA_MASTER,
                dataManagement.getBillingData(LOCAL_BILLING),
                dataManagement.getContactData(CONTACT_PHONE), HOTELS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());

        setResultSauceLabs(PASSED);
    }
}
