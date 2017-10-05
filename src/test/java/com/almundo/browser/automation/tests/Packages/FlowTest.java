package com.almundo.browser.automation.tests.Packages;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.BasePage.PackagesDataTrip;
import com.almundo.browser.automation.pages.ResultsPage.PackagesResultsPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.FIRST_OPTION;
import static com.almundo.browser.automation.utils.Constants.FIRST_OPTION_O1;
import static com.almundo.browser.automation.utils.Constants.PKG_10D_2A;
import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

public class FlowTest extends TestBaseSetup {

    private final int option = 7;
    private final int season = 2;

    private PackagesDataTrip packagesDataTrip = null;
    private PackagesResultsPage packagesResultsPage = null;
    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initItineraryData(){ dataManagement.getPackagesInitneraryData();}

    @BeforeMethod
    private void closeLoginPopUp(){
        LoginPopUp loginPopUp = initLoginPopUp();
        loginPopUp.clickCloseLoginBtn();
        packagesDataTrip = basePage.clickPackagesBtn();
    }

    @Test
    public void packagesBookingToSomePlace() {
        logTestTitle("Booking Packages To Some Place.");

        dataManagement.getPackagesDataTripItinerary(PKG_10D_2A);

        packagesDataTrip.selectOrigin(dataManagement.originFull);
        packagesDataTrip.setDestination(option);
        packagesDataTrip.selectSeason(season);
        packagesDataTrip.selectPassenger(dataManagement.adults, dataManagement.childs, dataManagement.rooms);
        packagesResultsPage = packagesDataTrip.clickBuscarBtn();
        packagesResultsPage.verPaqueteClick(FIRST_OPTION_O1);


        waitImplicitly(5000);

    }
}
