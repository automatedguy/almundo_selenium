package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.SauceTestBaseSetup;
import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.locators.pages.HomePageMap;
import com.almundo.browser.automation.locators.pages.HotelesPageMap;
import com.almundo.browser.automation.locators.testsmaps.TestInputMap;
import com.almundo.browser.automation.pages.HomePage;
import com.almundo.browser.automation.pages.HotelesPage;
import com.almundo.browser.automation.pages.PaymentPage;
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

        HotelesPage.hotelDestinationTxtBox(driver).sendKeys(String.valueOf(TestBaseSetup.destinationFullTextStr));
        HotelesPage.hotelDestinationTxtBox(driver).sendKeys(Keys.RETURN);

        HotelesPage.buscarBtn(driver).click();

        PageUtils.waitForVisibilityOfElementLocated(driver, 30, HotelesPageMap.VER_HOTEL_BTN.getBy());
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

        HotelesPage.hotelDestinationTxtBox(driver).sendKeys(String.valueOf(TestBaseSetup.destinationAutoComplete));

        PageUtils.waitForSaucePicture(10000);

        HotelesPage.selectCityFromAutoCompleteSuggestions(driver, TestInputMap.DESTINATION_FULL_PAR.getBy());

        HotelesPage.buscarBtn(driver).click();

        PageUtils.waitForVisibilityOfElementLocated(driver, 30, HotelesPageMap.VER_HOTEL_BTN.getBy());

        PageUtils.waitForSaucePicture(10000);
    }

    @org.testng.annotations.Test(dataProvider = "hardCodedBrowsers")
    public void hotelesPageHappyReservationTest(String browser, String version, String os, Method method)
            throws MalformedURLException, InvalidElementStateException, UnexpectedException, InterruptedException
    {
        this.createDriver(browser, version, os, method.getName());
        WebDriver driver = this.getWebDriver();

        PageUtils.waitForVisibilityOfElementLocated(driver, 30, HomePageMap.HOTELES_ICO.getBy());

        HomePage.hotelesTab(driver).click();

        PageUtils.waitForVisibilityOfElementLocated(driver, 10, HotelesPageMap.DESTINATION_TXT.getBy());

        HotelesPage.hotelDestinationTxtBox(driver).sendKeys("Rio");

        PageUtils.waitForVisibilityOfElementLocated(driver, 10, TestInputMap.DESTINATION_FULL_PAR.getBy());

        HotelesPage.selectCityFromAutoCompleteSuggestions(driver, TestInputMap.DESTINATION_FULL_PAR.getBy());

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