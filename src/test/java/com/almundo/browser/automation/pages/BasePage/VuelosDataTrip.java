package com.almundo.browser.automation.pages.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by gabrielcespedes on 05/12/16.
 */
public class VuelosDataTrip extends BasePage{

    public VuelosDataTrip(WebDriver driver) {
        super.driver = driver;
    }

//    TICKET_IDA_RDB(By.id("option-inbound-00")),
//    TICKET_VUELTA_RDB(By.id("option-inbound-01")),

    //############################################### Locators ##############################################

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

    //############################################### Actions ###############################################

    public void selectFlightOption(int index, String idFlightOption) {
        List<WebElement> flight = driver.findElements(By.id(idFlightOption));
        flight.get(index).click();
    }

}
