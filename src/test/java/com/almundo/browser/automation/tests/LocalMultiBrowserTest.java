package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.locators.HomePageMap;
import com.almundo.browser.automation.locators.HotelesPageMap;
import com.almundo.browser.automation.locators.testsmaps.HotelesTestMap;
import com.almundo.browser.automation.locators.testsmaps.TestInputs;
import com.almundo.browser.automation.pages.HomePage;
import com.almundo.browser.automation.pages.HotelesPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 26/10/16.
 */
public class LocalMultiBrowserTest extends TestBaseSetup {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver=getDriver();
    }

    @Test
    public void localLocatorTest() throws InterruptedException {
        PageUtils.waitForVisibilityOfElementLocated(driver, 30, HomePageMap.HOTELES_ICO.getBy());

        HomePage.hotelesTab(driver).click();

        PageUtils.waitForVisibilityOfElementLocated(driver, 30, HotelesPageMap.DESTINATION_TXT.getBy());

        HotelesPage.hotelDestinationTxtBox(driver).sendKeys(String.valueOf(TestInputs.AUTOCOMPLETE_INP));

        PageUtils.waitForSaucePicture(1000);

        HotelesPage.selectCityFromAutoCompleteSuggestions(driver, HotelesTestMap.DESTINATION_CITY_SUG.getBy());

        HotelesPage.buscarBtn(driver).click();

        PageUtils.waitForVisibilityOfElementLocated(driver, 30, HotelesPageMap.VER_HOTEL_BTN.getBy());
        PageUtils.assertElementIsPresent(driver, HotelesPageMap.VER_HOTEL_BTN.getBy(), String.valueOf(TestInputs.HOTEL_VER_HOTEL_BTN));
        HotelesPage.verHotelBtn(driver).click();

        PageUtils.waitForSaucePicture(2000);

    }
}
