package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.SauceTestBaseSetup;
import com.almundo.browser.automation.locators.HomePageMap;
import com.almundo.browser.automation.pages.HomePage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public class SauceHomePageTest extends SauceTestBaseSetup {

    @org.testng.annotations.Test(dataProvider = "hardCodedBrowsers")
    public void homePageHotelTabTest(String browser, String version, String os, Method method)
            throws MalformedURLException, InvalidElementStateException, UnexpectedException, InterruptedException {

        this.createDriver(browser, version, os, method.getName());
        WebDriver driver = this.getWebDriver();

        PageUtils.waitForVisibilityOfElementLocated(driver, 30, HomePageMap.HOTELES_ICO.getBy());

        HomePage.hotelesTab(driver).click();

        // Add assertions here

        PageUtils.waitForSaucePicture(10000);
    }

    @org.testng.annotations.Test(dataProvider = "hardCodedBrowsers")
    public void homePageVuelosTabTest(String browser, String version, String os, Method method)
            throws MalformedURLException, InvalidElementStateException, UnexpectedException, InterruptedException {

        this.createDriver(browser, version, os, method.getName());
        WebDriver driver = this.getWebDriver();

        PageUtils.waitForVisibilityOfElementLocated(driver, 30, HomePageMap.VUELOS_ICO.getBy());

        HomePage.vuelosTab(driver).click();

        // Add assertions here

        PageUtils.waitForSaucePicture(10000);
    }

    @org.testng.annotations.Test(dataProvider = "hardCodedBrowsers")
    public void homePageVueloHotelTabTest(String browser, String version, String os, Method method)
            throws MalformedURLException, InvalidElementStateException, UnexpectedException, InterruptedException {

        this.createDriver(browser, version, os, method.getName());
        WebDriver driver = this.getWebDriver();

        PageUtils.waitForVisibilityOfElementLocated(driver, 30, HomePageMap.VUELO_HOTEL_ICO.getBy());

        HomePage.vueloHotelTab(driver).click();

        // Add assertions here

        PageUtils.waitForSaucePicture(10000);
    }
}