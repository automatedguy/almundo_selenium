package com.almundo.browser.automation.tests.Activities;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.ExcursionsDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ThanksPageV3;
import com.almundo.browser.automation.pages.ResultsPage.ExcursionsDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.ExcursionsResultsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.*;
import static com.almundo.browser.automation.utils.Constants.FLIGHTS_CHECKOUT_INT;
import static com.almundo.browser.automation.utils.Constants.Results.PASSED;

public class FlowTest extends TestBaseSetup{

    private ExcursionsDataTrip excursionsDataTrip = null;
    private ExcursionsResultsPage excursionsResultsPage = null;
    private ExcursionsDetailPage excursionsDetailPage = null;
    private CheckOutPageV3 checkOutPageV3 =  null;
    private ThanksPageV3 thanksPageV3 = null;

    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initItineraryData(){
        dataManagement.setExcursionItinerary();
    }

    @BeforeMethod
    private void closeLoginPopUp(){
        LoginPopUp loginPopUp = initLoginPopUp();
        loginPopUp.clickCloseLoginBtn();
        excursionsDataTrip = basePage.clickExcursionsBtn();
    }

    @Test
    public void activityBooking(){
        logTestTitle("Activity booking -  1 Adult");
        excursionsDataTrip.setDestinationExcursions("Mia", "Miami, Estados Unidos de América");
        excursionsResultsPage = excursionsDataTrip.clickBuscar();
        excursionsDetailPage = excursionsResultsPage.clickVerActividadBtn(FIRST_OPTION);
        excursionsDetailPage.clickElegirFechaBtn();
        checkOutPageV3 = excursionsDetailPage.clickComprarBtn();

        dataManagement.setPassengerData(ADULT_MALE_NATIVE);

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                                    VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                                    dataManagement.getContactData(CONTACT_CELL_PHONE), FLIGHTS_CHECKOUT_INT);

        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

}
