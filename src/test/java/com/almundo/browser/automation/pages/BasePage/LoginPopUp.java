package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.pages.ResultsPage.*;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

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

    @FindBy(css = "#section div.login-content .button--md.submit")
    public WebElement ingresarBtn;

    @FindBy(css = "#section div:nth-child(4) > div.footer-login > span.link-button")
    public WebElement firstIngresarBtn;

    @FindBy(css = ".login-background.modal-login")
    public WebElement background;

    @FindBy(css = ".header-modal>.closelogin")
    public WebElement closeLoginBtn;

    //############################################### Actions ###############################################

    public BasePage clickCloseLoginBtn() {
        logger.info("Closing Login Pop-Up");
        PageUtils.waitLoginPopup(driver, closeLoginBtn, 15, "Close Login button");
        closeLoginBtn.click();
        waitImplicitly(2000);
        return initBasePage();
    }

    public LoginPopUp loginUser(String loginEmail, String loginPassword) {
        if(driver.getCurrentUrl().contains("st.") || driver.getCurrentUrl().contains("staging.") ){
            loginEmail = loginEmail.replace("@","st@");
        }
        setLoginEmailTxt(loginEmail);
        setLoginPasswordTxt(loginPassword);
        return this;
    }

    public LoginPopUp setLoginEmailTxt(String loginEmail) {
        try {
            PageUtils.waitElementForVisibility(driver, loginEmailTxt, 10, "Login Email box");
        }catch(Exception ouch){
            logger.info("Clicking on [Ingresar] in order to display login text boxes.");
            firstIngresarBtn.click();
        }
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
        logger.info("Clicking on [Ingresar] button");
        ingresarBtn.click();
        PageUtils.waitForUserNameDisplayed(driver);
        waitImplicitly(4000);
        return initBasePage();
    }

    public FlightsResultsPage clickIngresarOnFlightBtn() {
        logger.info("Clicking on Ingresar button");
        ingresarBtn.click();
        PageUtils.waitForUserNameDisplayed(driver);
        return initFlightsResultsPage();
    }

    public HotelsResultsPage clickIngresarOnHotelstBtn() {
        logger.info("Clicking on Ingresar button");
        ingresarBtn.click();
        PageUtils.waitForUserNameDisplayed(driver);
        return initHotelsResultsPage();
    }

    public HotelsDetailPage clickIngresarOnHotelsDetailBtn() {
        logger.info("Clicking on Ingresar button");
        ingresarBtn.click();
        PageUtils.waitForUserNameDisplayed(driver);
        return initHotelsDetailPage();
    }

    public CarsResultsPage clickIngresarOnCarstBtn() {
        logger.info("Clicking on Ingresar button");
        ingresarBtn.click();
        PageUtils.waitForUserNameDisplayed(driver);
        return initCarsResultsPage();
    }

    public TripsResultsPage clickIngresarOnTripstBtn() {
        logger.info("Clicking on Ingresar button");
        ingresarBtn.click();
        PageUtils.waitForUserNameDisplayed(driver);
        return initTripsResultsPage();
    }

    public TripsDetailPage clickIngresarOnTripsDetailBtn() {
        logger.info("Clicking on Ingresar button");
        ingresarBtn.click();
        PageUtils.waitForUserNameDisplayed(driver);
        return initTripsDetailPage();
    }

    public FacebookLoginPopUp clickFacebookLoginBtn() {
        logger.info("Clicking on Facebook Login button");
        PageUtils.waitElementForClickable(driver, facebookLoginBtn, 10, "Facebook login button");
        facebookLoginBtn.click();
        PageUtils.waitForUserNameDisplayed(driver);
        return initFacebookLoginPopUp();
    }

    public BasePage clickGoogleLoginBtn() {
        logger.info("Clicking on Facebook Login button");
        googleLoginBtn.click();
        waitImplicitly(4000);
        return initGoogleLoginPopUpEmail();
    }
}
