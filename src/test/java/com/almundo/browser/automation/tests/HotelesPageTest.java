package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.locators.HomePageMap;
import com.almundo.browser.automation.locators.HotelesPageMap;
import com.almundo.browser.automation.locators.testsmaps.HotelesTestMap;
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
        PageUtils.waitForVisibilityOfElementLocated(driver, 10, HomePageMap.HOTELES_ICO.getBy());

        HomePage.hotelesTab(driver).click();

        PageUtils.waitForVisibilityOfElementLocated(driver, 10, HotelesPageMap.DESTINATION_TXT.getBy());

        HotelesPage.hotelDestinationTxtBox(driver).sendKeys("Rio");

        PageUtils.waitForVisibilityOfElementLocated(driver, 10, HotelesTestMap.DESTINATION_CITY_SUG.getBy());

        HotelesPage.selectCityFromAutoCompleteSuggestions(driver, HotelesTestMap.DESTINATION_CITY_SUG.getBy());

        HotelesPage.buscarBtn(driver).click();

        HotelesPage.doHotelReservationFlow(driver);

        PaymentPage.populateCreditCardPayments(driver);

        PaymentPage.populateCreditCardOwnerData(driver);

        PaymentPage.populateBillingInformation(driver);

        PaymentPage.acceptTermsConditions(driver);

        PaymentPage.comprarBtn(driver).click();

        PageUtils.waitForSaucePicture(20000);
    }
}
