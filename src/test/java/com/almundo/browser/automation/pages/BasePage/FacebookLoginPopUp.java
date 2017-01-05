package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 05/01/17.
 */
public class FacebookLoginPopUp extends BasePage {

    public FacebookLoginPopUp(WebDriver iDriver) {
        super(iDriver);
    }

    //############################################### Locators ##############################################

    @FindBy(id = "email")
    public WebElement emailTxt;

    @FindBy(id = "pass")
    public WebElement passTxt;

    @FindBy(id = "u_0_2")
    public WebElement facebookLoginBtn;

    //############################################### Actions ###############################################


    public FacebookLoginPopUp setEmailTxt(String email) {
        PageUtils.waitElementForVisibility(driver, emailTxt, 15, "Facebook Login Email Text Box...");
        logger.info("Entering Login Email: [" + email + "]");
        emailTxt.clear();
        emailTxt.sendKeys(email);
        return this;
    }

    public FacebookLoginPopUp setPassTxt(String pass) {
        logger.info("Entering Login Pass: [" + pass + "]");
        passTxt.clear();
        passTxt.sendKeys(pass);
        return this;
    }

    public BasePage clickFacebookLoginBtn() {
        logger.info("Clicking on Facebook Log In Button.");
        facebookLoginBtn.click();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return initBasePage();
    }
}
