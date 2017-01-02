package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

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
    public WebElement loginBtn;

    @FindBy(css = ".login-background.modal-login")
    public WebElement background;

    //############################################### Actions ###############################################

    public void clickCloseLoginBtn() {
        logger.info("Closing Login Pop-Up");
        PageUtils.waitListContainResults(driver, ".closelogin", 0);
        List<WebElement> closeLoginBtn = driver.findElements(By.cssSelector(".closelogin"));
        closeLoginBtn.get(1).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
