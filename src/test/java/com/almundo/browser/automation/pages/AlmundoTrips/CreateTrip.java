package com.almundo.browser.automation.pages.AlmundoTrips;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

/**
 * Created by gabrielcespedes on 21/06/17.
 */
public class CreateTrip extends BasePage {

    public CreateTrip(WebDriver iDriver) {
        super(iDriver);
    }

    /***************************** Locators  **********************************/

    @FindBy(css = "#destination")
    public WebElement destinationTxt;

    @FindBy(css = "#name")
    public WebElement tripNameTxt;

    @FindBy(css = "#departure")
    public WebElement tripStartDateCalendar;

    @FindBy(css = "#arrival")
    public WebElement tripEndDateCalendar;

    @FindBy(css = ".btn.btn-md")
    public WebElement createTripBtn;

    /***************************** Actions  **********************************/

    public CreateTrip setDestination(String destinationAuto, String destinationFull){
        logger.info("Entering Event Destination: [" + destinationFull + "]");
        destinationTxt.clear();
        destinationTxt.sendKeys(destinationAuto);
        waitImplicitly(4000);
        selectAutoCompleteOption(destinationFull);
        return this;
    }

    public CreateTrip setTripName(String tripName){
        logger.info("Entering Trip Name: " + "[" + tripName + "]");
        tripNameTxt.clear();
        tripNameTxt.sendKeys(tripName);
        return this;
    }

    public TripConfirmation clickCreateTrip(){
        logger.info("Clicking on: [Create Trip] button");
        createTripBtn.click();
        return initTripConfirmation();
    }
}
