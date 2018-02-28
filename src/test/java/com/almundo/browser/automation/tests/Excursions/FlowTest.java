package com.almundo.browser.automation.tests.Excursions;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.ExcursionsDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.pages.CheckOutPageV3.ThanksPageV3;
import com.almundo.browser.automation.pages.ResultsPage.ExcursionsDetailPage;
import com.almundo.browser.automation.pages.ResultsPage.ExcursionsResultsPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.almundo.browser.automation.utils.Constants.*;
import static com.almundo.browser.automation.utils.Constants.FLIGHTS_CHECKOUT_INT;
import static com.almundo.browser.automation.utils.Constants.Results.PASSED;
import static com.almundo.browser.automation.utils.PageUtils.switchToNewTab;

public class FlowTest extends TestBaseSetup{

    private final String autoDestination = "Mia";
    private final String fullDestination = "Miami, Flórida, Estados Unidos da América";

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

    @AfterMethod
    private void cleanPassengerJsonList() {
        dataManagement.clearPassengerJsonList();
    }

    private void setExcursionAssertionInfo(){
        thanksPageAssertInfo.setFinalAmountPaid(checkOutPageV3.breakDownSectionV3().getFinalPriceString());
        thanksPageAssertInfo.setContactEmailEntered(checkOutPageV3.contactSection().getContactEmail());
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void activityBooking(){
        logTestTitle("Activity booking -  1 Adult");
        excursionsDataTrip.setDestinationExcursions(autoDestination, fullDestination);
        excursionsResultsPage = excursionsDataTrip.clickBuscarBtn();
        excursionsDetailPage = excursionsResultsPage.clickVerActividadBtn(FIRST_OPTION);

        switchToNewTab(driver);

        excursionsDetailPage.clickElegirFechaBtn();

        excursionsDetailPage.clickComprarBtn();

        excursionsDetailPage.setPuntoDePartida(OTRO);

        checkOutPageV3 = excursionsDetailPage.clickContinuarBtn();

        dataManagement.setPassengerData(ADULT_MALE_NATIVE);

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(),
                                    VISA_1, dataManagement.getBillingData(LOCAL_BILLING),
                                    dataManagement.getContactData(CONTACT_CELL_PHONE), FLIGHTS_CHECKOUT_INT);
        setExcursionAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }

/*    @SuppressWarnings("Duplicates")
    @Test
    public void activityBooking2CreditCards(){
        logTestTitle("Activity booking -  1 Adult - 2 Credit Cards");
        excursionsDataTrip.setDestinationExcursions(autoDestination, fullDestination);
        excursionsResultsPage = excursionsDataTrip.clickBuscar();
        excursionsDetailPage = excursionsResultsPage.clickVerActividadBtn(FIRST_OPTION);

        switchToNewTab(driver);

        excursionsDetailPage.clickElegirFechaBtn();
        checkOutPageV3 = excursionsDetailPage.clickComprarBtn();

        dataManagement.setPassengerData(ADULT_MALE_NATIVE);

        checkOutPageV3.setCheckOutInfo(dataManagement.getPassengerJsonList(), TWOCARDS_VISA_MASTER,
                                    dataManagement.getBillingData(LOCAL_BILLING),
                                    dataManagement.getContactData(CONTACT_PHONE), FLIGHTS_CHECKOUT_INT);
        setExcursionAssertionInfo();
        thanksPageV3 = checkOutPageV3.clickComprarBtn();

        Assert.assertTrue(thanksPageV3.confirmationOk());
        Assert.assertTrue(thanksPageV3.isPaymentInfoOk(thanksPageAssertInfo.getFinalAmountPaid()));
        Assert.assertTrue(thanksPageV3.isContactInfoOk(thanksPageAssertInfo.getContactEmailEntered()));
        Assert.assertTrue(thanksPageV3.isPassengersInfoOk());

        setResultSauceLabs(PASSED);
    }*/
}