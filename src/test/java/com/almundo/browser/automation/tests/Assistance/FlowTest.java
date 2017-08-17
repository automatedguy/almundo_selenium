package com.almundo.browser.automation.tests.Assistance;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.AssistanceDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import org.json.simple.JSONArray;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 17/08/17.
 */
public class FlowTest extends TestBaseSetup {

    private AssistanceDataTrip assistanceDataTrip = null;
    private DataManagement dataManagement = new DataManagement();


    @BeforeMethod
    private void closeLoginPopUp(){
        LoginPopUp loginPopUp = initLoginPopUp();
        loginPopUp.clickCloseLoginBtn();
        assistanceDataTrip = basePage.clickAssistanceBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    @Test
    public void Int_Booking_Flow() {
        logTestTitle("Seguros Flow - Int - 2 Adults - Turista - " + countryPar );

        // dataManagement.getOneWayDataTripItinerary("miami_10days_2adults_2childs_turista");
    }

}
