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
import static com.almundo.browser.automation.utils.PageUtils.formatInfo;

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

    @FindBy(id = "cluster0-choice1-0")
    private WebElement ticketVueltaRdb;


    @FindBy(xpath = "//label[@for='cluster0-choice0-0']/div/am-flight-choice/div")
    public WebElement tramoUnoInfo;

    @FindBy(xpath = "//label[@for='cluster0-choice1-0']/div/am-flight-choice/div")
    public WebElement tramoDosInfo;

    @FindBy(xpath = "//label[@for='cluster0-choice2-0']/div/am-flight-choice/div")
    public WebElement tramoTresInfo;

    @FindBy(css = ".date")
    public List<WebElement> dateList;


    //############################################### Actions ##############################################

    public FlightsResultsPage clickTicketIdaRdb() {
        PageUtils.waitElementForVisibility(driver,ticketIdaRdb,30, "Ticket Ida Radio Button");
        logger.info("Selecting Ticket de Ida");
        ticketIdaRdb.click();
        logger.info("Departure Flight Date: " + "[" + dateList.get(0).getText() + "]");
        logger.info("Departure Flight Info: " + "[" + formatInfo(tramoUnoInfo.getText())  + "]");
        return this;
    }

    public FlightsResultsPage clickTicketVuelta() {
        PageUtils.waitElementForVisibility(driver,ticketVueltaRdb,30, "Ticket Ida Radio Button");
        logger.info("Selecting Ticket de Vuelta");
        ticketVueltaRdb.click();
        logger.info("Return Flight Date: " + "[" + dateList.get(1).getText() + "]");
        logger.info("Return Flight Info: " + "[" + formatInfo(tramoDosInfo.getText()) + "]");
        return this;
    }

    public CheckOutPage clickComprarBtn(int index) {
        String cssSelectorName = ".flights-cluster-pricebox .button";
        PageUtils.waitListContainResults(driver, cssSelectorName, 0);
        List<WebElement> comprarBtn = driver.findElements(By.cssSelector(cssSelectorName));
        displayPriceBoxInfo(0);
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

    public void displayMultidestinationInfo(){
        PageUtils.waitElementForVisibility(driver, tramoUnoInfo, 30, "Tramo 1");
        logger.info("TRAMO 1 - Date: " + "[" + dateList.get(0).getText() + "]");
        logger.info("Flight Info: " + "[" + formatInfo(tramoUnoInfo.getText()) + "]");

        logger.info("TRAMO 2 - Date: " + "[" + dateList.get(1).getText() + "]");
        logger.info("Flight Info: " + "[" + formatInfo(tramoDosInfo.getText())  + "]");

        logger.info("TRAMO 3 - Date: " + "[" + dateList.get(3).getText() + "]");
        logger.info("Flight Info: " + "[" + formatInfo(tramoTresInfo.getText()) + "]");
    }

    void displayPriceBoxInfo(int index){
        List<WebElement> flightsClusterPricebox = driver.findElements(By.cssSelector(".flights-cluster-pricebox"));
        logger.info("Flight Rates Info: " + "[" + formatInfo(flightsClusterPricebox.get(index).getText()) + "]");
    }
}
