package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by leandro.efron on 30/6/2017.
 */
public class CarsOfficeModal extends TestBaseSetup {

    public CarsOfficeModal(WebDriver iDriver) {
        this.driver = iDriver;
    }

    //############################################### Locators ##############################################

    @FindBy(css = "car-offices-modal-footer .button")
    public WebElement reservarBtn;

    //############################################### Actions ###############################################

}
