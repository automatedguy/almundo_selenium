package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.pages.ResultsPage.VuelosResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by gabrielcespedes on 05/12/16.
 */
public class VuelosDataTrip extends BasePage{

    public VuelosDataTrip(WebDriver driver) {
        super(driver);
    }

    //############################################### Locators ##############################################

    @FindBy(name = "type-flights")
    public WebElement flightTypeDdl;

    @FindBy(id = "origin-flights")
    public WebElement originFlightsTxt;

    @FindBy(id = "destination-flights")
    public WebElement destinationFlightsTxt;

    @FindBy(id = "departure-flights")
    public WebElement departureFlightsCalendar;

    @FindBy(id = "arrival-flights")
    public WebElement arrivalFlightsCalendar;

    @FindBy(id = "class-flights")
    public WebElement classFlightsDdl;

    @FindBy(css = ".row-adults>.sub")
    public WebElement subAdultsBtn;

    @FindBy(css = ".row-adults>.add")
    public WebElement addAdultBtn;

    @FindBy(css = ".row-youngers>.sub")
    public WebElement subChildBtn;

    @FindBy(css = ".row-youngers>.add")
    public WebElement addChildBtn;

    @FindBy(name = "class-flights")
    public WebElement classFlightDdl;

    //MULTIDESTINATION
    @FindBy(css = ".add-leg .txt")
    public WebElement addLegLnk;

    @FindBy(id = "departure-flights-0")
    public WebElement departureFlights0Calendar;

    @FindBy(id = "departure-flights-1")
    public WebElement departureFlights1Calendar;

    //############################################### Actions ###############################################

    public void clickAddLegLnk() {
        PageUtils.waitElementForClickable(driver, addLegLnk, 5, "Agregar vuelo link");
        logger.info("Adding flight leg");
        addLegLnk.click();
    }

    public VuelosDataTrip selectFlightType(String flightType) {
        Select flightTypeSelect = new Select(flightTypeDdl);
        logger.info("Selecting Flight Type: [" + flightType + "]");
        flightTypeSelect.selectByVisibleText(flightType);
        return this;
    }

    public VuelosDataTrip setOrigin(String origin, String originFull) {
        PageUtils.waitElementForVisibility(driver, originFlightsTxt, 10, "Origin text field");
        logger.info("Entering Flight Origin: [" + originFull + "]");
        originFlightsTxt.clear();
        originFlightsTxt.sendKeys(origin);
        selectAutoCompleteOption(originFull);
        return this;
    }

    public VuelosDataTrip setFlight(String nameAuto, String nameFull, String id) {
        WebElement originFld = driver.findElement(By.id(id));
        PageUtils.waitElementForVisibility(driver, originFld, 10, "Text field");
        logger.info("Entering Flight: [" + nameFull + "]");
        originFld.clear();
        originFld.sendKeys(nameAuto);
        selectAutoCompleteOption(nameFull);
        return this;
    }

    public VuelosDataTrip setDestination(String destination, String destinationFull) {
        PageUtils.waitElementForVisibility(driver, destinationFlightsTxt, 10, "Destination text field");
        logger.info("Entering Flight Destination: [" + destinationFull + "]");
        destinationFlightsTxt.clear();
        destinationFlightsTxt.sendKeys(destination);
        selectAutoCompleteOption(destinationFull);
        return this;
    }

    public VuelosDataTrip selectPassenger(int adults, int childs) {
        personasTxt.click();

        if (adults>1){
            for(int i=1; i<adults; i++) {
                logger.info("Adding: [1 adult]");
                addAdultBtn.click();
            }
        }

        if (childs>0){
            for(int i=0; i<childs; i++) {
                logger.info("Adding: [1 child]");
                addChildBtn.click();
            }
        }

        logger.info("Total Adults: [" + adults + "]");
        logger.info("Total Childs: [" + childs + "]");
        return this;
    }

    public VuelosDataTrip selectChildAgeRange(String ageRange, int childs) {
        if (childs>0){
            for(int i=0; i<childs; i++) {
                Select ageSelection = new Select(driver.findElement(By.id("age-" + i)));
                ageSelection.selectByVisibleText(ageRange);
            }
        }
        listoBtn.click();
        return this;
    }

    public VuelosDataTrip selectClass(String flightClass) {
        Select claseVueloDdl = new Select(classFlightDdl);
        logger.info("Selecting Flight Class: [" + flightClass + "]");
        claseVueloDdl.selectByVisibleText(flightClass);
        return this;
    }

    public VuelosResultsPage clickBuscarBtn() {
        PageUtils.scrollToElement(driver, buscarBtn);
        logger.info("Clicking on Buscar Button");
        buscarBtn.click();
        return initVuelosResultsPage();
    }

}
