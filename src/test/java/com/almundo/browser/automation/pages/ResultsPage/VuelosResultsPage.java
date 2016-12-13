package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 13/12/16.
 */
public class VuelosResultsPage extends TestBaseSetup {

    public VuelosResultsPage(WebDriver driver) {
        super.driver = driver;
    }

    //############################################### Locators ##############################################

    @FindBy(id = "option-inbound-00")
    private WebElement ticketIdaRdb;

    @FindBy(id = "option-inbound-01")
    private WebElement ticketVueltaRdb;

    @FindBy(xpath = "//span[contains(.,'Comprar')]")
    private WebElement comprarBtn;

    //############################################### Actions ##############################################

    public VuelosResultsPage ticketIdaRdbClick() {
        ticketIdaRdb.click();
        return this;
    }

    public VuelosResultsPage ticketVueltaClick() {
        ticketVueltaRdb.click();
        return this;
    }

    public VuelosResultsPage comprarBtnClick() {
        comprarBtn.click();
        return this;
    }




}
