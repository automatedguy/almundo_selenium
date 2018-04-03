package com.almundo.browser.automation.pages.AlmundoTrips.DataTrips;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.pages.ResultsPage.FlightsResultsPage;
import com.almundo.browser.automation.utils.Constants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

/**
 * Created by gabrielcespedes on 19/06/17.
 */
public class TripsFlightsData extends BasePage {

    public TripsFlightsData(WebDriver iDriver) {
        super(iDriver);
    }

    /***************************** Locators *****************************/

    @FindBy(css = "flights-search-box > form > div:nth-child(1) > select")
    public WebElement tipoDeVueloDdl;

    @FindBy(css = "#origin")
    public WebElement originTxt;

    @FindBy(css = "#destination")
    public WebElement destinationTxt;
    
    @FindBy(id = "departure")
    public WebElement checkinCalendarFlights;

    @FindBy(id = "return")
    public WebElement checkoutCalendarFlights;

    @FindBy(css = ".flights-search-form .btn.btn-primary.btn-submit")
    public WebElement buscarVuelosBtn;

    /***************************** Actions *****************************/

    public TripsFlightsData selectFlightType(Constants.FlightType flightType){
        logger.info("Selecting Flight Type: " + "["+ flightType + "]");
        Select flightTypeDdl = new Select(tipoDeVueloDdl);
        flightTypeDdl.selectByVisibleText(flightType.toString());
        return this;
    }

    public TripsFlightsData setOrigin(String originAuto, String originFull){
        logger.info("Entering Flight Origin: " + "[" + originFull + "]");
        originTxt.clear();
        originTxt.sendKeys(originAuto);
        waitImplicitly(3000);
        selectAutoCompleteOption(originFull);
        return this;
    }

    public TripsFlightsData setDestination(String destinationAuto, String destinationFull) {
        logger.info("Entering Flight Destination: [" + destinationFull + "]");
        destinationTxt.clear();
        destinationTxt.sendKeys(destinationAuto);
        waitImplicitly(3000);
        selectAutoCompleteOption(destinationFull);
        return this;
    }

    public FlightsResultsPage clickBuscarVuelosBtn(){
        logger.info("Clicking on [Buscar] button (For Flights)");
        waitImplicitly(1000);
        buscarVuelosBtn.click();
        return initFlightsResultsPage();
    }
}
