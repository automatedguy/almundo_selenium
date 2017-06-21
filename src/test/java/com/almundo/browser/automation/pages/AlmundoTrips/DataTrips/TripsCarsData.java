package com.almundo.browser.automation.pages.AlmundoTrips.DataTrips;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.pages.ResultsPage.CarsResultsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

/**
 * Created by gabrielcespedes on 19/06/17.
 */
public class TripsCarsData extends BasePage {

    public TripsCarsData(WebDriver iDriver) {
        super(iDriver);
    }

    /***************************** Locators *****************************/

    @FindBy(css = "#origin")
    public WebElement originTxt;

    @FindBy(css = "#destination")
    public WebElement destinationTxt;

    @FindBy(css = ".cars-search-form .btn.btn-primary.btn-submit")
    public WebElement buscarAutosBtn;

    /***************************** Actions *****************************/

    public TripsCarsData setOrigin(String originAuto, String originFull){
        logger.info("Entering Flight Origin: " + "[" + originFull + "]");
        originTxt.clear();
        originTxt.sendKeys(originAuto);
        selectAutoCompleteOption(originFull);
        return this;
    }

    public TripsCarsData setDestination(String destinationAuto, String destinationFull) {
        logger.info("Entering Destination: [" + destinationFull + "]");
        destinationTxt.clear();
        destinationTxt.sendKeys(destinationAuto);
        waitImplicitly(5000);
        return this;
    }

    public CarsResultsPage clickBuscarAutosBtn(){
        logger.info("Clicking on [Buscar] button (For Cars)");
        waitImplicitly(1000);
        buscarAutosBtn.click();
        return initCarsResultsPage();
    }
}
