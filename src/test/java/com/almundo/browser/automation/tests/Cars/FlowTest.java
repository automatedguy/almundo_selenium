package com.almundo.browser.automation.tests.Cars;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.CarsDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ThanksPageV3;
import com.almundo.browser.automation.pages.ResultsPage.CarsResultsPage;
import org.json.simple.JSONArray;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.*;
import static com.almundo.browser.automation.utils.Constants.Results.PASSED;


/**
 * Created by gabrielcespedes on 20/12/16.
 */
public class FlowTest extends TestBaseSetup {

    private CarsResultsPage carsResultsPage = null;
    private CheckOutPageV3 checkOutPageV3 = null;
    private ThanksPageV3 thanksPageV3 = null;

    private CarsDataTrip carsDataTrip = null;
    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initItineraryData() {
        dataManagement.getCarsItineraryData();
    }

    @BeforeMethod
    private void closeLoginPopUp(){
        LoginPopUp loginPopUp = initLoginPopUp();
        loginPopUp.clickCloseLoginBtn();
        carsDataTrip = basePage.clickCarsBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.passengerJsonList = new JSONArray();
    }

    private void getAssertionInfo(){
        thanksPageAssertInfo.finalAmountPaid = checkOutPageV3.breakDownSectionV3().getFinalPriceString();
        thanksPageAssertInfo.carsDetailInfo = checkOutPageV3.breakDownSectionV3().getCarsDetailContent();
        thanksPageAssertInfo.contactEmailEntered = checkOutPageV3.contactSection().getContactEmail();
    }

    /***************************** Test Cases *****************************/

   @Test
    public void dom_Booking_Flow() {
        logTestTitle("Domestic - 10 days");
        if(countryPar.equals(COLOMBIA)){
            logger.warn(NO_DOMESTIC_CARS_COLOMBIA);
            logger.warn(NO_DOMESTIC_CARS_COLOMBIA_TICKET);}
        else {
            dataManagement.getCarsDataTripItinerary(CAP_10D_21_24);

            carsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
            carsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
            carsDataTrip.selectDateFromCalendar(carsDataTrip.pickUpDateCalendar, dataManagement.startDate);
            carsDataTrip.selectDateFromCalendar(carsDataTrip.dropOffDateCalendar, dataManagement.endDate);
            carsDataTrip.selectPickUpTime(dataManagement.pickUpTime);
            carsDataTrip.selectDropOffTime(dataManagement.dropOffTime);
            carsDataTrip.selectAgeRange(dataManagement.ageRange);

            carsResultsPage = carsDataTrip.clickBuscarBtn();

            Assert.assertTrue(carsResultsPage.vacancy());
            Assert.assertTrue(carsResultsPage.processed());

            dataManagement.getPassengerData(ADULT_MALE_NATIVE);

            checkOutPageV3 = carsResultsPage.clickReservarAhoraBtn(FIRST_OPTION);

            checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, VISA_1,
                    dataManagement.getBillingData(LOCAL_BILLING),
                    dataManagement.getContactData(CONTACT_CELL_PHONE), CARS_CHECKOUT);

            thanksPageV3 = checkOutPageV3.clickComprarBtn();
            Assert.assertTrue(thanksPageV3.confirmationOk());
        }
        setResultSauceLabs(PASSED);
    }

    @Test
    public void int_Booking_Flow() {
        logTestTitle("International - 10 days");
        dataManagement.getCarsDataTripItinerary(MIA_10D_21_24);

        carsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        carsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        carsDataTrip.selectDateFromCalendar(carsDataTrip.pickUpDateCalendar, dataManagement.startDate);
        carsDataTrip.selectDateFromCalendar(carsDataTrip.dropOffDateCalendar, dataManagement.endDate);
        carsDataTrip.selectPickUpTime(dataManagement.pickUpTime);
        carsDataTrip.selectDropOffTime(dataManagement.dropOffTime);
        carsDataTrip.selectAgeRange(dataManagement.ageRange);

        carsResultsPage = carsDataTrip.clickBuscarBtn();

        Assert.assertTrue(carsResultsPage.vacancy());
        Assert.assertTrue(carsResultsPage.processed());

        dataManagement.getPassengerData(ADULT_FEMALE_NATIVE);

        checkOutPageV3 = carsResultsPage.clickReservarAhoraBtn(FIRST_OPTION);

        checkOutPageV3.populateCheckOutPageV3(dataManagement.passengerJsonList, MASTER_1,
                                    dataManagement.getBillingData(LOCAL_BILLING),
                                    dataManagement.getContactData(CONTACT_CELL_PHONE), CARS_CHECKOUT);
        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();
        Assert.assertTrue(thanksPageV3.confirmationOk());

        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.finalAmountPaid));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.contactEmailEntered));
        Assert.assertTrue(thanksPageV3.isCarsDetailInfoOk(thanksPageAssertInfo.carsDetailInfo));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }
}