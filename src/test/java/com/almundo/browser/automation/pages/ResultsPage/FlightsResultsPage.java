package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.almundo.browser.automation.utils.Constants.Messages.*;

/**
 * Created by gabrielcespedes on 13/12/16.
 */
public class FlightsResultsPage extends TestBaseSetup {

    public FlightsResultsPage(WebDriver driver) {
        super.driver = driver;
    }

    //############################################### Locators ##############################################

    @FindBy(id = "cluster0-choice0-0")
    private WebElement ticketIdaRdb;

    @FindBy(xpath = "//label[@for='cluster0-choice0-0']/div/am-flight-choice/div")
    public WebElement ticketIdaInfo;

    @FindBy(id = "cluster0-choice1-0")
    private WebElement ticketVueltaRdb;

    @FindBy(xpath = "//label[@for='cluster0-choice0-0']/div/am-flight-choice/div")
    public WebElement ticketVueltaInfo;


    //############################################### Actions ##############################################

    public FlightsResultsPage clickTicketIdaRdb() {
        PageUtils.waitElementForVisibility(driver,ticketIdaRdb,30, "Ticket Ida Radio Button");
        logger.info("Selecting Ticket de Ida");
        ticketIdaRdb.click();
        logger.info("Departure Flight Info: " + "[" + ticketIdaInfo.getText().replaceAll("[\\n\\r]+", " - ")  + "]");
        return this;
    }

    public FlightsResultsPage clickTicketVuelta() {
        PageUtils.waitElementForVisibility(driver,ticketVueltaRdb,30, "Ticket Ida Radio Button");
        logger.info("Selecting Ticket de Vuelta");
        ticketVueltaRdb.click();
        logger.info("Return Flight Info: " + "[" + ticketVueltaInfo.getText().replaceAll("[\\n\\r]+", "\\\\n") + "]");
        return this;
    }

    public CheckOutPage clickComprarBtn(int index) {
        String cssSelectorName = ".flights-cluster-pricebox .button";
        PageUtils.waitListContainResults(driver, cssSelectorName, 0);
        List<WebElement> comprarBtn = driver.findElements(By.cssSelector(cssSelectorName));
        logger.info("Clicking on Comprar button");
        comprarBtn.get(index).click();
        return initCheckOutPage();
    }

    public boolean vacancy(){
        try {
            PageUtils.waitForNoVacancy(driver, By.cssSelector("div:nth-child(4) > div > div > div.alert__text > p:nth-child(5) > a"), 5, "[" + LISTADO_DE_SUCURSALES_LNK + "] link");
        } catch (Exception ex){
            return true;
        }
        return false;
    }
}
