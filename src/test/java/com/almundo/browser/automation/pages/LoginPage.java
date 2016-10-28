package com.almundo.browser.automation.pages;

import com.almundo.browser.automation.locators.loginmaps.FacebookLoginPageMap;
import com.almundo.browser.automation.locators.loginmaps.GoogleLoginPageMap;
import com.almundo.browser.automation.locators.loginmaps.LoginPageMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public class LoginPage {

    private static WebElement element = null;

    public static WebElement loginPageEmailTxtBox(WebDriver driver){
        element = driver.findElement(LoginPageMap.LOGIN_EMAIL_TXT.getBy());
        return element;
    }

    public static WebElement loginPagePasswordTxtBox(WebDriver driver){
        element = driver.findElement(LoginPageMap.LOGIN_PASSWORD_TXT.getBy());
        return element;
    }

    public static WebElement loginPageSubmitBtn(WebDriver driver){
        element = driver.findElement(LoginPageMap.INGRESAR_BTN.getBy());
        return element;
    }

    public static WebElement loginPageFacebookBtn(WebDriver driver){
        element = driver.findElement(LoginPageMap.FACEBOOK_BTN.getBy());
        return element;
    }

    public static WebElement facebookLoginPageEmailTxtBox(WebDriver driver){
        element = driver.findElement(FacebookLoginPageMap.EMAIL_TXT.getBy());
        return element;
    }

    public static WebElement facebookLoginPagePasswordTxtBox(WebDriver driver){
        element = driver.findElement(FacebookLoginPageMap.PASSWORD_TXT.getBy());
        return element;
    }

    public static WebElement facebookLoginPageSubmitBtn(WebDriver driver){
        element = driver.findElement(FacebookLoginPageMap.LOGIN_BTN.getBy());
        return element;
    }

    public static WebElement loginPageGoogleBtn(WebDriver driver){
        element = driver.findElement(LoginPageMap.GOOGLE_BTN.getBy());
        return element;
    }

    public static WebElement googleLoginPageEmailTxtBox(WebDriver driver){
        element = driver.findElement(GoogleLoginPageMap.EMAIL_TXT.getBy());
        return element;
    }

    public static WebElement googleLoginPagePasswordTxtBox(WebDriver driver){
        element = driver.findElement(GoogleLoginPageMap.PASSWORD_TXT.getBy());
        return element;
    }

    public static WebElement googleLoginPageNextBtn(WebDriver driver){
        element = driver.findElement(GoogleLoginPageMap.NEXT_BTN.getBy());
        return element;
    }

    public static WebElement googleLoginPageSubmitBtn(WebDriver driver){
        element = driver.findElement(GoogleLoginPageMap.SIGNIN_BTN.getBy());
        return element;
    }
}