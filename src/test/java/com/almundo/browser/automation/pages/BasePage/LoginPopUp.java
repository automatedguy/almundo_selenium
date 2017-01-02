package com.almundo.browser.automation.pages.BasePage;

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

    @FindBy(css = ".closelogin")
    public WebElement closeLoginBtn;

    @FindBy(css = ".button-logo.full.button-facebook")
    public WebElement facebookLoginBtn;

    @FindBy(css = ".button-logo.full.button-googleplus")
    public WebElement googleLoginBtn;

    @FindBy(id = "login_email")
    public WebElement loginEmailTxt;

    @FindBy(id = "login_password")
    public WebElement loginPasswordTxt;

    @FindBy(css = ".button.button--secondary.button--block.button--md.submit")
    public WebElement loginBtn;

    @FindBy(css = ".login-background.modal-login")
    public WebElement background;

    //############################################### Actions ###############################################

    public LoginPopUp clickCloseLoginBtn() {
        logger.info("Closing Login Pop-Up");
        closeLoginBtn.click();
        return this;
    }

    public LoginPopUp clickBackground() {
        logger.info("Closing Login Pop-Up");
        background.click();
        return this;
    }

}
