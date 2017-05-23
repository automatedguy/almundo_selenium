package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.pages.ResultsPage.FlightsResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 02/01/17.
 */
public class LoginPopUp extends BasePage {

    public LoginPopUp(WebDriver iDriver) {
        super(iDriver);
    }

    //############################################### Locators ##############################################

    @FindBy(css = ".button-logo.full.button-facebook")
    public WebElement facebookLoginBtn;

    @FindBy(css = ".button-logo.full.button-googleplus")
    public WebElement googleLoginBtn;

    @FindBy(id = "login_email")
    public WebElement loginEmailTxt;

    @FindBy(id = "login_password")
    public WebElement loginPasswordTxt;

    @FindBy(css = ".button.button--secondary.button--block.button--md.submit")
    public WebElement ingresarBtn;

    @FindBy(css = ".login-background.modal-login")
    public WebElement background;

    @FindBy(css = ".header-modal>.closelogin")
    public WebElement closeLoginBtn;

    //############################################### Actions ###############################################

    public BasePage clickCloseLoginBtn() {
        logger.info("Closing Login Pop-Up");
        PageUtils.waitLoginPopup(driver, closeLoginBtn, 15, "Close Login button");
        closeLoginBtn.click();
        PageUtils.waitImplicitly(2000);
        return initBasePage();
    }

    public LoginPopUp loginUser(String loginEmail, String loginPassword) {
        setLoginEmailTxt(loginEmail);
        setLoginPasswordTxt(loginPassword);
        return this;
    }

    public LoginPopUp setLoginEmailTxt(String loginEmail) {
        PageUtils.waitElementForVisibility(driver, loginEmailTxt, 15, "Login Pop-Up...");
        logger.info("Entering Login Email: [" + loginEmail + "]");
        loginEmailTxt.clear();
        loginEmailTxt.sendKeys(loginEmail);
        return this;
    }

    public LoginPopUp setLoginPasswordTxt(String loginPassword) {
        logger.info("Entering Login Password: [" + loginPassword + "]");
        loginPasswordTxt.clear();
        loginPasswordTxt.sendKeys(loginPassword);
        return this;
    }

    public BasePage clickIngresarBtn() {
        logger.info("Clicking on Ingresar button");
        ingresarBtn.click();
        PageUtils.waitImplicitly(4000);
        return initBasePage();
    }

    public FlightsResultsPage clickIngresarOnFlightBtn() {
        logger.info("Clicking on Ingresar button");
        ingresarBtn.click();
        PageUtils.waitImplicitly(4000);
        return initFlightsResultsPage();
    }

    public FacebookLoginPopUp clickFacebookLoginBtn() {
        logger.info("Clicking on Facebook Login button");
        PageUtils.waitElementForClickable(driver, facebookLoginBtn, 10, "Facebook login button");
        facebookLoginBtn.click();
        PageUtils.waitImplicitly(4000);
        return initFacebookLoginPopUp();
    }

    public BasePage clickGoogleLoginBtn() {
        logger.info("Clicking on Facebook Login button");
        googleLoginBtn.click();
        PageUtils.waitImplicitly(4000);
        return initGoogleLoginPopUpEmail();
    }
}
