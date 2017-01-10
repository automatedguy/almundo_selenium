package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.utils.Constants;
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

    @FindBy(id = "cluster0-choice0-0")
    private WebElement ticketIdaRdb;

    @FindBy(id = "cluster0-choice1-0")
    private WebElement ticketVueltaRdb;

    //############################################### Actions ##############################################

    public VuelosResultsPage clickTicketIdaRdb() {
        PageUtils.waitElementForVisibility(driver,ticketIdaRdb,30, "Ticket Ida Radio Button");
        logger.info("Selecting Ticket de Ida");
        ticketIdaRdb.click();
        return this;
    }

    public VuelosResultsPage clickTicketVuelta() {
        PageUtils.waitElementForVisibility(driver,ticketVueltaRdb,30, "Ticket Ida Radio Button");
        logger.info("Selecting Ticket de Vuelta");
        ticketVueltaRdb.click();
        return this;
    }

    public CheckOutPage clickComprarBtn(int index) {
        String cssSelectorName = ".button";
        PageUtils.waitListContainResults(driver, cssSelectorName, 0);
        List<WebElement> comprarBtn = driver.findElements(By.cssSelector(cssSelectorName));
        logger.info("Clicking on Comprar button");
        comprarBtn.get(index).click();
        return initCheckOutPage();
    }

    public boolean vacancy(){
        try {
            PageUtils.waitForNoVacancy(driver, By.cssSelector("div:nth-child(4) > div > div > div.alert__text > p:nth-child(5) > a"), 5, "[" + Constants.LISTADO_DE_SUCURSALES_LNK + "] link");
        } catch (Exception ex){
            return true;
        }
        return false;
    }
}
