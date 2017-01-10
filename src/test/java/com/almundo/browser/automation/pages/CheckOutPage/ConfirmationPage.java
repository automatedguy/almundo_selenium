package com.almundo.browser.automation.pages.CheckOutPage;

import com.almundo.browser.automation.pages.BasePage.BasePage;
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


}
