package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.locators.pages.HomePageMap;
import com.almundo.browser.automation.locators.pages.HotelesPageMap;
import com.almundo.browser.automation.locators.testsmaps.TestInputMap;
import com.almundo.browser.automation.pages.HomePage;
import com.almundo.browser.automation.pages.HotelesPage;
import com.almundo.browser.automation.pages.PaymentPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 31/10/16.
 */
public class HotelesPageTest extends TestBaseSetup {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver=getDriver();
    }

    @Test
    public void hotelesReservationHappyPathTest() throws InterruptedException {

        PageUtils.waitForVisibilityOfElementLocated(driver, 20, HomePageMap.HOTELES_ICO.getBy());
        HomePage.hotelesTab(driver).click();

        PageUtils.waitForVisibilityOfElementLocated(driver, 20, HotelesPageMap.DESTINATION_TXT.getBy());
        HotelesPage.hotelDestinationTxtBox(driver).sendKeys(TestBaseSetup.destinationAutoComplete);

        PageUtils.waitForVisibilityOfElementLocated(driver, 20, TestInputMap.DESTINATION_FULL_PAR.getBy());
        HotelesPage.selectCityFromAutoCompleteSuggestions(driver, TestInputMap.DESTINATION_FULL_PAR.getBy());

        PageUtils.selectDateFromCalendar(driver, HotelesPage.HOTELES_FECHA_SALIDA_CAL, departureDate);
        PageUtils.selectDateFromCalendar(driver, HotelesPage.HOTELES_FECHA_REGRESO_CAL, returnDate);

        HotelesPage.buscarBtn(driver).click();
        HotelesPage.doHotelReservationFlow(driver);
        PaymentPage.populatePassenger(driver, 2);

/*        PageUtils.moveToElement(driver, PaymentPageMap.VER_BANCOS_01CUOTAS_LNK.getBy());
        PaymentPage.verMasBancos01Lnk(driver).click();
        PaymentPage.pagoUnaCuota(driver).click();
        PaymentPage.populateCreditCardPayments(driver);*/

        PaymentPage.populateCreditCardOwnerData(driver);
        PaymentPage.populateBillingInformation(driver);
        PaymentPage.acceptTermsConditions(driver);

        //PaymentPage.comprarBtn(driver).click();

        PageUtils.waitForSaucePicture(10000);
    }
}
