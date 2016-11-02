package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.SauceTestBaseSetup;
import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.locators.pages.HomePageMap;
import com.almundo.browser.automation.pages.HomePage;
import com.almundo.browser.automation.pages.VuelosPage;
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
public class SauceVuelosPageTest extends SauceTestBaseSetup {

    @org.testng.annotations.Test(dataProvider = "hardCodedBrowsers")
    public void vuelosPageHappyPathSearchTest(String browser, String version, String os, Method method)
            throws MalformedURLException, InvalidElementStateException, UnexpectedException, InterruptedException {

        this.createDriver(browser, version, os, method.getName());
        WebDriver driver = this.getWebDriver();

        PageUtils.waitForVisibilityOfElementLocated(driver, 30, HomePageMap.VUELOS_ICO.getBy());
        HomePage.vuelosTab(driver).click();

        VuelosPage.vuelosPageOriginFlightsTxtBox(driver).sendKeys(TestBaseSetup.originFullText);
        VuelosPage.vuelosPageOriginFlightsTxtBox(driver).sendKeys(Keys.RETURN);

        VuelosPage.vuelosPageDestinationFlightsTxtBox(driver).sendKeys(TestBaseSetup.destinationFullText);
        VuelosPage.vuelosPageDestinationFlightsTxtBox(driver).sendKeys(Keys.RETURN);

        VuelosPage.vuelosPageBuscarBtn(driver).click();

        PageUtils.waitForSaucePicture(10000);
    }
}
