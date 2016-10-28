package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.SauceTestBaseSetup;
import com.almundo.browser.automation.locators.HomePageMap;
import com.almundo.browser.automation.locators.HotelesPageMap;
import com.almundo.browser.automation.locators.testsmaps.HotelesTestMap;
import com.almundo.browser.automation.locators.testsmaps.TestInputs;
import com.almundo.browser.automation.pages.HomePage;
import com.almundo.browser.automation.pages.HotelesPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public class SauceHotelesPageTest extends SauceTestBaseSetup {

    @org.testng.annotations.Test(dataProvider = "hardCodedBrowsers")
    public void hotelesPageHappyPathSearchTest(String browser, String version, String os, Method method)
            throws MalformedURLException, InvalidElementStateException, UnexpectedException, InterruptedException
    {
        this.createDriver(browser, version, os, method.getName());
        WebDriver driver = this.getWebDriver();

        PageUtils.waitForVisibilityOfElementLocated(driver, 30, HomePageMap.HOTELES_ICO.getBy());

        HomePage.hotelesTab(driver).click();

        PageUtils.waitForVisibilityOfElementLocated(driver, 30, HotelesPageMap.DESTINATION_TXT.getBy());

        HotelesPage.hotelDestinationTxtBox(driver).sendKeys(String.valueOf(TestInputs.FULL_DESTINATION1_INP));
        HotelesPage.hotelDestinationTxtBox(driver).sendKeys(Keys.RETURN);

        HotelesPage.buscarBtn(driver).click();

        PageUtils.waitForVisibilityOfElementLocated(driver, 30, HotelesPageMap.VER_HOTEL_BTN.getBy());

        PageUtils.assertElementIsPresent(driver, HotelesPageMap.VER_HOTEL_BTN.getBy(), String.valueOf(TestInputs.HOTEL_VER_HOTEL_BTN));

        PageUtils.waitForSaucePicture(10000);
    }

    @org.testng.annotations.Test(dataProvider = "hardCodedBrowsers")
    public void hotelesPageHappyPathAutoCompleteCitySearchTest(String browser, String version, String os, Method method)
            throws MalformedURLException, InvalidElementStateException, UnexpectedException, InterruptedException
    {
        this.createDriver(browser, version, os, method.getName());
        WebDriver driver = this.getWebDriver();

        PageUtils.waitForVisibilityOfElementLocated(driver, 30, HomePageMap.HOTELES_ICO.getBy());

        HomePage.hotelesTab(driver).click();

        PageUtils.waitForVisibilityOfElementLocated(driver, 30, HotelesPageMap.DESTINATION_TXT.getBy());

        HotelesPage.hotelDestinationTxtBox(driver).sendKeys(String.valueOf(TestInputs.AUTOCOMPLETE_INP));

        PageUtils.waitForSaucePicture(10000);

        HotelesPage.selectCityFromAutoCompleteSuggestions(driver, HotelesTestMap.DESTINATION_CITY_SUG.getBy());

        HotelesPage.buscarBtn(driver).click();

        PageUtils.waitForVisibilityOfElementLocated(driver, 30, HotelesPageMap.VER_HOTEL_BTN.getBy());

        PageUtils.assertElementIsPresent(driver, HotelesPageMap.VER_HOTEL_BTN.getBy(), String.valueOf(TestInputs.HOTEL_VER_HOTEL_BTN));

        PageUtils.waitForSaucePicture(10000);
    }
}