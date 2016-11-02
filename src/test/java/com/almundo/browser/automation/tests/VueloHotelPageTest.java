package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.locators.pages.HomePageMap;
import com.almundo.browser.automation.locators.pages.VueloHotelPageMap;
import com.almundo.browser.automation.locators.testsmaps.TestInputMap;
import com.almundo.browser.automation.pages.HomePage;
import com.almundo.browser.automation.pages.PaymentPage;
import com.almundo.browser.automation.pages.VueloHotelPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 01/11/16.
 */
public class VueloHotelPageTest extends TestBaseSetup {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver=getDriver();
    }

    @Test
    public void vueloHotelReservationHappyPathTest() throws InterruptedException {

        PageUtils.waitForVisibilityOfElementLocated(driver, 20, HomePageMap.VUELO_HOTEL_ICO.getBy());
        HomePage.vueloHotelTab(driver).click();

        VueloHotelPage.originFlightTxt(driver).sendKeys(TestBaseSetup.originAutoComplete);
        PageUtils.waitForSaucePicture(1000);
        PageUtils.selectFromAutoCompleteSuggestions(driver, TestInputMap.ORIGIN_FULL_PAR.getBy());

        VueloHotelPage.destinationFlightTxt(driver).sendKeys(TestBaseSetup.destinationAutoComplete);
        PageUtils.waitForSaucePicture(1000);
        PageUtils.selectFromAutoCompleteSuggestions(driver, TestInputMap.DESTINATION_FULL_PAR.getBy());

        PageUtils.selectDateFromCalendar(driver, VueloHotelPage.TRIPS_FECHA_SALIDA_CAL, departureDate);
        PageUtils.waitForSaucePicture(1000);

        PageUtils.selectDateFromCalendar(driver, VueloHotelPage.TRIPS_FECHA_REGRESO_CAL, returnDate);

        VueloHotelPage.buscarBtn(driver).click();

        PageUtils.waitForVisibilityOfElementLocated(driver, 30, VueloHotelPageMap.CONTINUAR_BTN.getBy());
        VueloHotelPage.continuarBtn(driver).click();

        PageUtils.waitForVisibilityOfElementLocated(driver, 30, VueloHotelPageMap.VER_HABITACION_BTN.getBy());
        PageUtils.waitForElementToBeClickcable(driver, 10, VueloHotelPageMap.VER_HABITACION_BTN.getBy());
        VueloHotelPage.verHabitacionBtn(driver).click();

        // PageUtils.waitForVisibilityOfElementLocated(driver, 30, VueloHotelPageMap.COMPRAR_BTN.getBy());
        PageUtils.waitForElementToBeClickcable(driver, 10, VueloHotelPageMap.COMPRAR_BTN.getBy());
        VueloHotelPage.comprarBtn(driver).click();

        PaymentPage.populatePassenger(driver, 2);

        PaymentPage.populateCreditCardOwnerData(driver);

        PaymentPage.populateBillingInformation(driver);

        PaymentPage.acceptTermsConditions(driver);

        // PaymentPage.comprarBtn(driver).click();

        PageUtils.waitForSaucePicture(1000);

    }
}
