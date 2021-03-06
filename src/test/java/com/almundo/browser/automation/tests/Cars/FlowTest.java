package com.almundo.browser.automation.tests.Cars;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.CarsDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ThanksPageV3;
import com.almundo.browser.automation.pages.ResultsPage.CarsResultsPage;
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
        dataManagement.setCarsItineraryData();
    }

    @BeforeMethod
    private void closeLoginPopUp(){
        LoginPopUp loginPopUp = initLoginPopUp();
        loginPopUp.clickCloseLoginBtn();
        carsDataTrip = basePage.clickCarsBtn();
    }

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.clearPassengerJsonList();
    }

    private void getAssertionInfo(){
        thanksPageAssertInfo.setFinalAmountPaid(checkOutPageV3.breakDownSectionV3().getFinalPriceString());
        thanksPageAssertInfo.setCarsDetailInfo(checkOutPageV3.breakDownSectionV3().getCarsDetailContent());
        thanksPageAssertInfo.setContactEmailEntered(checkOutPageV3.contactSection().getContactEmail());
    }

    /***************************** Test Cases *****************************/

   @Test
    public void DomBooking() {
        logTestTitle("Domestic - 10 days");
        if(countryPar.equals(COLOMBIA)){
            logger.warn(NO_DOMESTIC_CARS_COLOMBIA);
            logger.warn(NO_DOMESTIC_CARS_COLOMBIA_TICKET);}
        else {
            dataManagement.setCarsDataTripItinerary(CAP_10D_21_24);

            carsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
            carsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
            carsDataTrip.setDate(carsDataTrip.getPickUpDateCalendar(), dataManagement.getStartDate());
            carsDataTrip.setDate(carsDataTrip.getDropOffDateCalendar(), dataManagement.getEndDate());
            carsDataTrip.selectPickUpTime(dataManagement.getPickUpTime());
            carsDataTrip.selectDropOffTime(dataManagement.getDropOffTime());
            carsDataTrip.selectAgeRange(dataManagement.getAgeRange());

            carsResultsPage = carsDataTrip.clickBuscarBtn();

            Assert.assertTrue(carsResultsPage.vacancy());
            Assert.assertTrue(carsResultsPage.processed());

            dataManagement.setPassengerData(ADULT_MALE_NATIVE);

            checkOutPageV3 = carsResultsPage.clickReservarAhoraBtn(FIRST_OPTION);

            checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(), VISA_1,
                    dataManagement.getBillingData(LOCAL_BILLING),
                    dataManagement.getContactData(CONTACT_CELL_PHONE), CARS_CHECKOUT);

            getAssertionInfo();
            thanksPageV3 = checkOutPageV3.clickComprarBtn();

            Assert.assertTrue(thanksPageV3.confirmationOk());
            Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
            Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
            Assert.assertTrue(thanksPageV3.isCarsDetailInfoOk(thanksPageAssertInfo.getCarsDetailInfo()));
            Assert.assertTrue(thanksPageV3.isPassengersInfoOk());
        }
        setResultSauceLabs(PASSED);
    }

    @Test
    public void IntBooking() {
        logTestTitle("International - 10 days");
        dataManagement.setCarsDataTripItinerary(MIA_10D_21_24);

        carsDataTrip.setOrigin(dataManagement.getOriginAuto(), dataManagement.getOriginFull());
        carsDataTrip.setDestination(dataManagement.getDestinationAuto(), dataManagement.getDestinationFull());
        carsDataTrip.setDate(carsDataTrip.getPickUpDateCalendar(), dataManagement.getStartDate());
        carsDataTrip.setDate(carsDataTrip.getDropOffDateCalendar(), dataManagement.getEndDate());
        carsDataTrip.selectPickUpTime(dataManagement.getPickUpTime());
        carsDataTrip.selectDropOffTime(dataManagement.getDropOffTime());
        carsDataTrip.selectAgeRange(dataManagement.getAgeRange());

        carsResultsPage = carsDataTrip.clickBuscarBtn();

        Assert.assertTrue(carsResultsPage.vacancy());
        Assert.assertTrue(carsResultsPage.processed());

        dataManagement.setPassengerData(ADULT_FEMALE_NATIVE);

        checkOutPageV3 = carsResultsPage.clickReservarAhoraBtn(FIRST_OPTION);

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(), MASTER_1,
                                    dataManagement.getBillingData(LOCAL_BILLING),
                                    dataManagement.getContactData(CONTACT_CELL_PHONE), CARS_CHECKOUT);
        getAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isCarsDetailInfoOk(thanksPageAssertInfo.getCarsDetailInfo()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }
}