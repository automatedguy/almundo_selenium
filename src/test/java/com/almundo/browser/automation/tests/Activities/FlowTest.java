package com.almundo.browser.automation.tests.Activities;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.ExcursionsDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.Results.PASSED;

public class FlowTest extends TestBaseSetup{

    private ExcursionsDataTrip excursionsDataTrip = null;

    private DataManagement dataManagement = new DataManagement();

    @BeforeMethod
    private void closeLoginPopUp(){
        LoginPopUp loginPopUp = initLoginPopUp();
        loginPopUp.clickCloseLoginBtn();
        excursionsDataTrip = basePage.clickExcursionsBtn();
    }

    @Test
    public void activityBooking(){
        logTestTitle("Activity booking");
        excursionsDataTrip.setDestinationExcursions("Mia", "Miami, Estados Unidos de América");
        excursionsDataTrip.clickBuscar();

        setResultSauceLabs(PASSED);
    }

}
