package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.utils.PageUtils;
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

    @FindBy(id = "origin-flights")
    public WebElement originFlightsTxt;

    @FindBy(id = "destination-flights")
    public WebElement destinationFlightsTxt;

    @FindBy(id = "departure-flights")
    public WebElement departureFlightsCalendar;

    @FindBy(id = "arrival-flights")
    public WebElement arrivalFlightsCalendar;

    @FindBy(css = ".search__input")
    public WebElement personasTxt;

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

    @FindBy(css = ".button.button--sm")
    public WebElement listoBtn;

    @FindBy(name = "class-flights")
    public WebElement classFlightDdl;



    //############################################### Actions ###############################################


    public VuelosDataTrip setOrigin(String origin) {
        PageUtils.waitElementForVisibility(driver, originFlightsTxt, 10, "Origin text field");
        logger.info("Entering Flight Origin: [" + origin + "]");
        originFlightsTxt.clear();
        originFlightsTxt.sendKeys(origin);
        return this;
    }

    public VuelosDataTrip setDestination(String destination) {
        PageUtils.waitElementForVisibility(driver, destinationFlightsTxt, 10, "Destination text field");
        logger.info("Entering Destination: [" + destination + "]");
        destinationFlightsTxt.clear();
        destinationFlightsTxt.sendKeys(destination);
        return this;
    }

    public int selectPassenger(int adults, int childs) {
        personasTxt.click();

        if (adults>1){
            for(int i=1; i<adults; i++) {
                logger.info("Adding 1 adult");
                addAdultBtn.click();
            }
        }

        if (childs>0){
            for(int i=0; i<childs; i++) {
                logger.info("Adding 1 child");
                addChildBtn.click();
            }
        }
        listoBtn.click();

        return adults + childs;
    }


    public VuelosDataTrip selectClass(String flightClass) {
        Select claseVueloDdl = new Select(classFlightDdl);
        logger.info("Selecting Flight Class: [" + flightClass + "]");
        claseVueloDdl.selectByVisibleText(flightClass);
        return this;
    }
}
