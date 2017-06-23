package com.almundo.browser.automation.pages.AlmundoTrips;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.almundo.browser.automation.utils.Constants.Results.FAILED;
import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

/**
 * Created by gabrielcespedes on 19/06/17.
 */
public class Home extends BasePage {

    public Home(WebDriver iDriver) {
        super(iDriver);
    }

    /***************************** Locators  **********************************/

    @FindBy(css = ".btn.btn-md")
    public WebElement createFirstTripBtn;

    @FindBy(css = ".btn.btn-sm")
    public WebElement createTripBtn;

    @FindBy(css = tripListLocator)
    public List<WebElement> tripsList;

    final String tripListLocator = ".trip-info > a";

    /***************************** Actions  **********************************/

    public CreateTrip clickCreateTrip(){
        try {
            PageUtils.waitElementForVisibility(driver, createFirstTripBtn, 10, "[Create Trip] (Empty Dashboard)");
            createFirstTripBtn.click();
        }catch (Exception ouch) {
            logger.info("There are trips created already, clicking on the other [Create Trip] button.");
            createTripBtn.click();
        }
        return initCreateTrip();
    }

    public Dashboard selectTripFromList(int index){
        PageUtils.waitListContainResults(driver, tripListLocator, 0);
        logger.info("Clicking on: " + "[" + tripsList.get(index).getText() +"]");
        tripsList.get(index).click();
        return initTripsDashboard();
    }

    public boolean tripWasCreatedOk(String finalTripName){
        boolean created = false;
        waitImplicitly(5000);
        PageUtils.waitListContainResults(driver, tripListLocator, 0);
        logger.info("Checking if the trip: " + "["+ finalTripName +"]" + " was created correctly.");
        for(WebElement tripName : tripsList ){
            if(tripName.getText().equals(finalTripName)){
                logger.info("The trip: [" + finalTripName + "] was added to the trip list!");
                created = true;
                break;
            }
        }
        if(!created){setResultSauceLabs(FAILED);}
        return created;
    }
}