package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.PaymentPage.PaymentPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

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

    //############################################### Actions ##############################################

    public VuelosResultsPage ticketIdaRdbClick() {
        PageUtils.waitElementForVisibility(driver,ticketIdaRdb,30, "Ticket Ida Radio Button");
        logger.info("Selecting Ticket de Ida");
        ticketIdaRdb.click();
        return this;
    }

    public VuelosResultsPage ticketVueltaClick() {
        PageUtils.waitElementForVisibility(driver,ticketVueltaRdb,30, "Ticket Ida Radio Button");
        logger.info("Selecting Ticket de Vuelta");
        ticketVueltaRdb.click();
        return this;
    }

    public PaymentPage comprarBtnClick(int index) {
        String cssSelectorName = ".button.button--lg.button--secondary.button--block.epp-space-bottom-16";
        PageUtils.waitListContainResults(driver, cssSelectorName, 0);
        List<WebElement> comprarBtn = driver.findElements(By.cssSelector(cssSelectorName));
        logger.info("Clicking on Comprar button");
        comprarBtn.get(index).click();
        return initPaymentPage();
    }
}