package com.almundo.browser.automation.pages.Trippers;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.pages.ResultsPage.FlightsResultsPage;
import com.almundo.browser.automation.utils.Constants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

/**
 * Created by gabrielcespedes on 16/06/17.
 */
public class BuscarEnAlmundo extends BasePage {

    public BuscarEnAlmundo(WebDriver iDriver) {
        super(iDriver);
    }

    /***************************** Locators  **********************************/

    @FindBy(css = "flights-search-box > form > div:nth-child(1) > select")
    public WebElement tipoDeVueloDdl;

    @FindBy(css = "#origin")
    public WebElement originTxt;

    @FindBy(css = "#destination")
    public WebElement destinationTxt;

    @FindBy(id = "departure")
    public WebElement checkinCalendar;

    @FindBy(css = ".flights-search-form .btn.btn-primary.btn-submit")
    public WebElement buscarBtn;


    /***************************** Actions
     * @param flightType**********************************/

    public BuscarEnAlmundo selectFlightType(Constants.FlightType flightType){
        logger.info("Selecting Flight Type: " + "["+ flightType + "]");
        Select flightTypeDdl = new Select(tipoDeVueloDdl);
        flightTypeDdl.selectByVisibleText(flightType.toString());
        return this;
    }

    public BuscarEnAlmundo setOrigin(String originAuto, String originFull){
        logger.info("Entering Flight Origin: " + "[" + originFull + "]");
        originTxt.clear();
        originTxt.sendKeys(originAuto);
        waitImplicitly(3000);
        selectAutoCompleteOption(originFull);
        return this;
    }

    public BuscarEnAlmundo setDestination(String destinationAuto, String destinationFull) {
        logger.info("Entering Flight Destination: [" + destinationFull + "]");
        destinationTxt.clear();
        destinationTxt.sendKeys(destinationAuto);
        waitImplicitly(5000);
        selectAutoCompleteOption(destinationFull);
        return this;
    }

    public FlightsResultsPage clickBuscarBtn(){
        logger.info("Clicking on [Agregar] button.");
        buscarBtn.click();
        return initFlightsResultsPage();
    }

}
