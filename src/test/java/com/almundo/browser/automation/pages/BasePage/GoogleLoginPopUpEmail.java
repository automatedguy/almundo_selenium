package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 05/01/17.
 */
public class GoogleLoginPopUpEmail extends BasePage {

    public GoogleLoginPopUpEmail(WebDriver iDriver) {
        super(iDriver);
    }

    //############################################### Locators ##############################################

    @FindBy(id = "Email")
    public WebElement emailTxt;

    @FindBy(id = "next")
    public WebElement nextBtn;

    @FindBy(id = "Passwd")
    public WebElement passwdTxt;

    @FindBy(id = "signIn")
    public WebElement signInBtn;

    //############################################### Actions ###############################################

    public GoogleLoginPopUpEmail setEmailTxt(String email) {
        PageUtils.waitElementForVisibility(driver, emailTxt, 15, "Facebook Login Email Text Box...");
        logger.info("Entering Login Email: [" + email + "]");
        emailTxt.clear();
        emailTxt.sendKeys(email);
        return this;
    }

    public GoogleLoginPopUpEmail clickNextBtn() {
        logger.info("Clicking on Facebook Log In Button.");
        nextBtn.click();
        PageUtils.waitImplicitly(4000);
        return this;
    }

}
