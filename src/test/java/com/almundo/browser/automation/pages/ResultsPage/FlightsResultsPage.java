package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.almundo.browser.automation.utils.Constants.Messages.LISTADO_DE_SUCURSALES_LNK;
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

    @FindBy(css = ".date")
    public List<WebElement> dateList;

    @FindBy(css = ".choice-direction")
    public List<WebElement> directionList;

    @FindBy(css ="#main-content am-flights-cluster-container")
    public List<WebElement> flightChoices;


    //############################################### Actions ##############################################

    public String getDirectionLabel(int index){
        return directionList.get(index).getText();
    }

    public FlightsResultsPage clickTicketIdaRdb(int index) {
        PageUtils.waitElementForVisibility(driver,ticketIdaRdb,30, "Ticket Ida Radio Button");
        logger.info("Selecting Ticket de: " + "[" + getDirectionLabel(index) + "]");
        ticketIdaRdb.click();
        logger.info("Departure Flight Date: " + "[" + dateList.get(index).getText() + "]");
        logger.info("Departure Flight Info: " + "[" + formatInfo(tramoUnoInfo.getText())  + "]");
        return this;
    }

    public FlightsResultsPage clickTicketVuelta(int index) {
        PageUtils.waitElementForVisibility(driver,ticketVueltaRdb,30, "Ticket Ida Radio Button");
        logger.info("Selecting Ticket de: " + "[" + getDirectionLabel(index) + "]");
        ticketVueltaRdb.click();
        logger.info("Return Flight Date: " + "[" + dateList.get(index).getText() + "]");
        logger.info("Return Flight Info: " + "[" + formatInfo(tramoDosInfo.getText()) + "]");
        return this;
    }

    public CheckOutPage clickComprarBtn(int index) {
        String cssSelectorName = ".flights-cluster-pricebox .button";
        PageUtils.waitListContainResults(driver, cssSelectorName, 0);
        List<WebElement> comprarBtn = driver.findElements(By.cssSelector(cssSelectorName));
        logger.info("Flight Rates Info: " + "[" + getPriceBoxInfo(index) + "]");
        logger.info("Clicking on button: [Comprar]");
        comprarBtn.get(index).click();
        return initCheckOutPage();
    }

    public CheckOutPageV3 clickComprarV3Btn(int index) {
        String cssSelectorName = ".flights-cluster-pricebox .button";
        PageUtils.waitListContainResults(driver, cssSelectorName, 0);
        List<WebElement> comprarBtn = driver.findElements(By.cssSelector(cssSelectorName));
        logger.info("Flight Rates Info: " + "[" + getPriceBoxInfo(index) + "]");
        logger.info("Clicking on button: [Comprar]");
        comprarBtn.get(index).click();
        return initCheckOutPageV3();
    }

    public boolean vacancy(){
        PageUtils.waitUrlContains(driver, 10, "results", "Results url");
        logger.info("Results URL: " + "[" + driver.getCurrentUrl() + "]");

        try {
            PageUtils.waitForNoVacancy(driver, By.cssSelector("div:nth-child(4) > div > div > div.alert__text > p:nth-child(5) > a"), 5, "[" + LISTADO_DE_SUCURSALES_LNK + "] link");
        } catch (Exception ex){
            return true;
        }
        return false;
    }

    public void displayMultidestinationInfo(int tramos){
        PageUtils.waitElementForVisibility(driver, tramoUnoInfo, 30, "TRAMO 1");
        for(int tramoIndex=0; tramoIndex<tramos;tramoIndex++) {
            logger.info(getDirectionLabel(tramoIndex) + " - Date: " + "[" + dateList.get(tramoIndex).getText() + "]");
            logger.info("Flight Info: " + "[" +
                            formatInfo((driver.findElement(By.xpath("//label[@for='cluster0-choice" +
                            tramoIndex + "-0']/div/am-flight-choice/div"))).getText() + "]"));
        }
    }

    public String getPriceBoxInfo(int index){
        List<WebElement> flightsClusterPricebox = driver.findElements(By.cssSelector(".flights-cluster-pricebox"));
        return formatInfo(flightsClusterPricebox.get(index).getText());
    }

    public List<WebElement> getFlightsChoices(){
        return flightChoices;
    }
}
