package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.SauceTestBaseSetup;
import com.almundo.browser.automation.components.UserCredential;
import com.almundo.browser.automation.locators.loginmaps.FacebookLoginPageMap;
import com.almundo.browser.automation.locators.loginmaps.GoogleLoginPageMap;
import com.almundo.browser.automation.pages.LoginPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;

/**
 * Created by gabrielcespedes on 19/10/16.
 */
public class SauceLoginPageTest extends SauceTestBaseSetup {

    @org.testng.annotations.Test(dataProvider = "hardCodedBrowsers")
    public void loginPageSimpleLoginTest(String browser, String version, String os, Method method)
            throws MalformedURLException, InvalidElementStateException, UnexpectedException, InterruptedException {

        this.createDriver(browser, version, os, method.getName());
        WebDriver driver = this.getWebDriver();

        driver.get("https://almundo.com.ar/account/login");

        // HomePage.ingresarLnk(driver).click();

        LoginPage.loginPageEmailTxtBox(driver).sendKeys(String.valueOf(UserCredential.REGISTERED_USER));
        LoginPage.loginPagePasswordTxtBox(driver).sendKeys(String.valueOf(UserCredential.REGISTERED_PASS));
        LoginPage.loginPageSubmitBtn(driver).click();

        // Add assertions here
        PageUtils.waitForSaucePicture(10000);
    }

    @org.testng.annotations.Test(dataProvider = "hardCodedBrowsers")
    public void loginPageFacebookLoginTest(String browser, String version, String os, Method method)
            throws MalformedURLException, InvalidElementStateException, UnexpectedException, InterruptedException {

        this.createDriver(browser, version, os, method.getName());
        WebDriver driver = this.getWebDriver();

        driver.get("https://almundo.com.ar/account/login");

        LoginPage.loginPageFacebookBtn(driver).click();

        PageUtils.setFocusOnChildWindow(driver);

        PageUtils.waitForVisibilityOfElementLocated(driver, 30, FacebookLoginPageMap.EMAIL_TXT.getBy());
        LoginPage.facebookLoginPageEmailTxtBox(driver).sendKeys(String.valueOf(UserCredential.REGISTERED_USER));
        LoginPage.facebookLoginPagePasswordTxtBox(driver).sendKeys(String.valueOf(UserCredential.REGISTERED_PASS));
        LoginPage.facebookLoginPageSubmitBtn(driver).click();

        // Add assertions here
        PageUtils.waitForSaucePicture(10000);
    }

    @org.testng.annotations.Test(dataProvider = "hardCodedBrowsers")
    public void loginPageChromeLoginTest(String browser, String version, String os, Method method)
            throws MalformedURLException, InvalidElementStateException, UnexpectedException, InterruptedException {

        this.createDriver(browser, version, os, method.getName());
        WebDriver driver = this.getWebDriver();

        driver.get("https://almundo.com.ar/account/login");
        // HomePage.ingresarLnk(driver).click();

        LoginPage.loginPageGoogleBtn(driver).click();

        PageUtils.setFocusOnChildWindow(driver);

        PageUtils.waitForVisibilityOfElementLocated(driver, 50, GoogleLoginPageMap.EMAIL_TXT.getBy());
        LoginPage.googleLoginPageEmailTxtBox(driver).sendKeys(String.valueOf(UserCredential.REGISTERED_USER));
        LoginPage.googleLoginPageNextBtn(driver).click();

        PageUtils.waitForVisibilityOfElementLocated(driver, 50, GoogleLoginPageMap.PASSWORD_TXT.getBy());
        LoginPage.googleLoginPagePasswordTxtBox(driver).sendKeys(String.valueOf(UserCredential.REGISTERED_PASS));

        LoginPage.googleLoginPageSubmitBtn(driver).click();

        // Add assertions here
        PageUtils.waitForSaucePicture(10000);
    }
}
