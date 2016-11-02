package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.locators.pages.HomePageMap;
import com.almundo.browser.automation.locators.testsmaps.TestInputMap;
import com.almundo.browser.automation.pages.HomePage;
import com.almundo.browser.automation.pages.PaymentPage;
import com.almundo.browser.automation.pages.VuelosPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 31/10/16.
 */
public class VuelosPageTest extends TestBaseSetup {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver=getDriver();
    }

    @Test
    public void vuelosReservationHappyPathTest() throws InterruptedException {
        PageUtils.waitForVisibilityOfElementLocated(driver, 10, HomePageMap.HOTELES_ICO.getBy());

        PageUtils.waitForVisibilityOfElementLocated(driver, 30, HomePageMap.VUELOS_ICO.getBy());
        HomePage.vuelosTab(driver).click();

        VuelosPage.vuelosPageOriginFlightsTxtBox(driver).sendKeys(TestBaseSetup.originAutoComplete);
        PageUtils.waitForSaucePicture(2000);
        PageUtils.selectFromAutoCompleteSuggestions(driver, TestInputMap.ORIGIN_FULL_PAR.getBy());

        VuelosPage.vuelosPageDestinationFlightsTxtBox(driver).sendKeys(TestBaseSetup.destinationAutoComplete);
        PageUtils.waitForSaucePicture(2000);
        PageUtils.selectFromAutoCompleteSuggestions(driver, TestInputMap.DESTINATION_FULL_PAR.getBy());

        PageUtils.selectDateFromCalendar(driver, VuelosPage.VUELOS_FECHA_SALIDA_CAL, departureDate);
        PageUtils.selectDateFromCalendar(driver, VuelosPage.VUELOS_FECHA_REGRESO_CAL, returnDate);

        VuelosPage.vuelosPageBuscarBtn(driver).click();

        PageUtils.waitForSaucePicture(20000);

        VuelosPage.comprarTickets(driver);

        PageUtils.waitForSaucePicture(20000);

        PaymentPage.populatePassenger(driver, Integer.valueOf(TestBaseSetup.numPassengers));

        PageUtils.waitForSaucePicture(20000);

        // Enable once we have credit card
        // PageUtils.moveToElement(driver, PaymentPageMap.VER_BANCOS_02CUOTAS_LNK.getBy());
        // PaymentPage.populateCreditCardPayments(driver);

        PaymentPage.populateCreditCardOwnerData(driver);

        PaymentPage.populateBillingInformation(driver);

        PaymentPage.acceptTermsConditions(driver);

        // Enable once we have credit card
        // PaymentPage.comprarBtn(driver).click();

        PageUtils.waitForSaucePicture(1000);
    }
}
