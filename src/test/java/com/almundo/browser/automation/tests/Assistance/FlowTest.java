package com.almundo.browser.automation.tests.Assistance;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.AssistanceDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.ResultsPage.AssistanceResultsPage;
import org.json.simple.JSONArray;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 17/08/17.
 */
public class FlowTest extends TestBaseSetup {

    private DataManagement dataManagement = new DataManagement();
    private AssistanceDataTrip assistanceDataTrip = null;
    private AssistanceResultsPage assistanceResultsPage = null;

    @BeforeClass
    private void initItineraryData() {
        dataManagement.getAssistanceItineraryData();
    }

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
        logTestTitle("Seguros Flow - Int - 2 Persons - Turista - " + countryPar );
        dataManagement.getAssistanceDataTripItinerary("miami_10days_2persons_unique");
        assistanceDataTrip.selectTipoDeViaje(dataManagement.tripType);
        assistanceDataTrip.enterDestino(dataManagement.destinationAuto, dataManagement.destinationFull);
        assistanceDataTrip.selectDateFromCalendar(assistanceDataTrip.departureAssistanceCalendar, dataManagement.startDate);
        assistanceDataTrip.selectDateFromCalendar(assistanceDataTrip.arrivalAssistanceCalendar, dataManagement.endDate);
        assistanceDataTrip.selectPersonas(dataManagement.persons);
        assistanceResultsPage = assistanceDataTrip.clickBuscarBtn();
    }
}
