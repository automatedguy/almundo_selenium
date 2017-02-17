package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 05/01/17.
 */
public class GoogleLoginPopUpPasswd extends BasePage {
    public GoogleLoginPopUpPasswd(WebDriver iDriver) {
        super(iDriver);
    }

    //############################################### Locators ##############################################

    @FindBy(id = "Passwd")
    public WebElement passwdTxt;

    @FindBy(id = "signIn")
    public WebElement signInBtn;

    //############################################### Actions ###############################################

    public GoogleLoginPopUpPasswd setPasswdTxt(String paswd) {
        PageUtils.waitElementForVisibility(driver, passwdTxt, 15, "Google Login Password Text Box...");
        logger.info("Entering Login Email: [" + paswd + "]");
        passwdTxt.clear();
        passwdTxt.sendKeys(paswd);
        return this;
    }

    public BasePage clickSignInBtn() {
        logger.info("Clicking on Google Sign In Button.");
        signInBtn.click();
        PageUtils.waitImplicitly(4000);
        return initBasePage();
    }
}
