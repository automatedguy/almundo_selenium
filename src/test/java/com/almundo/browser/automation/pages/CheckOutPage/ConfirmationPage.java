package com.almundo.browser.automation.pages.CheckOutPage;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.utils.Constants;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 10/01/17.
 */
public class ConfirmationPage extends BasePage {

    public ConfirmationPage(WebDriver iDriver) {
        super(iDriver);
    }

    //############################################### Locators ##############################################

    @FindBy(css = ".alert__text-primary")
    public WebElement felicitacionesLbl;

    @FindBy(css = ".alert__text-secondary")
    public WebElement datosReservaLbl;

    //############################################### Actions ###############################################

    public boolean confirmationOk(){
        PageUtils.waitElementForVisibility(driver, By.cssSelector(".alert__text-primary"), 45, "Reservation Confirmation.");
        if(felicitacionesLbl.getText().equals(Constants.FELICITACIONES_MSG)){
            logger.info("Reservation Confirmed! :) ");
            return true;
        }
        else{
            logger.info("Reservation Failed! :( ");
            return false;
        }
    }
}
