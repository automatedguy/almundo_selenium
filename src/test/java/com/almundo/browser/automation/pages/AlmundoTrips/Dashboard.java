package com.almundo.browser.automation.pages.AlmundoTrips;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.almundo.browser.automation.utils.Constants.Results.FAILED;
import static com.almundo.browser.automation.utils.PageUtils.*;

/**
 * Created by gabrielcespedes on 16/06/17.
 */
public class Dashboard extends BasePage {

    public Dashboard(WebDriver iDriver) {
        super(iDriver);
    }

    /***************************** Locators  **********************************/

    @FindBy(css = ".btn.btn-md")
    WebElement addFirstEventBtn;

    @FindBy(css = "trip-itinerary-tab .btn.btn-sm")
    WebElement addEventBtn;

    @FindBy(css = ".trip-title")
    public WebElement tripTitleLbl;

    @FindBy(css = ".trip-itinerary-item .title")
    public List<WebElement> eventsTitleList;

    /***************************** Actions  **********************************/

    public String getTripTitle() {
        PageUtils.waitElementForVisibility(driver, tripTitleLbl, 5, "Trip title");
        return tripTitleLbl.getText();
    }

    public AddEvent clickAddEvent(){
        waitElementForVisibility(driver, By.cssSelector(".nav-tabs"), 8, "Trip dashboard is not displayed");
        waitImplicitly(2000);
        if(isElementPresent(addFirstEventBtn)) {
            logger.info("Clicking on: [Agregar Evento] button (First Event)");
            addFirstEventBtn.click();
        } else {
            logger.info("Clicking on: [Agregar evento] button (there are events already for this trip)");
            PageUtils.waitElementForClickable(driver, addEventBtn, 8, "Agregar evento button");
            addEventBtn.click();
        }
        return initTrippersAgregarEvento();
    }

    public boolean checkTripTitle(String expected) {
        boolean areEquals = false;
        logger.info("Validating trip name is correct: " + "["+ expected +"]");
        if(tripTitleLbl.getText().equals(expected)) {
            areEquals = true;
        } else {
            setResultSauceLabs(FAILED);
        }
        return areEquals;
    }

    public boolean eventCreated(String finalEventName){
        boolean created = false;
        waitListContainResults(driver, ".trip-title", 0);
        //PageUtils.waitListContainResults(driver, tripListLocator, 0);
        logger.info("Checking if the event: " + "["+ finalEventName +"]" + " was created correctly.");
        for(WebElement eventName : eventsTitleList ){
            if(eventName.getText().equals(finalEventName)){
                //logger.info("The trip: [" + finalTripName + "] was added to the trip list!");
                created = true;
                break;
            }
        }
        if(!created){setResultSauceLabs(FAILED);}
        return created;
    }
}
