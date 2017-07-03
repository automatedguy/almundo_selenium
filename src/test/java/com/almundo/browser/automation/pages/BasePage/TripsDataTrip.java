package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.pages.ResultsPage.TripsResultsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static com.almundo.browser.automation.utils.PageUtils.*;

/**
 * Created by leandro.efron on 6/12/2016.
 */
public class TripsDataTrip extends BasePage {

    public TripsDataTrip(WebDriver driver) {
        super(driver);
    }

    //############################################### Locators ##############################################

    @FindBy(id = "origin-trips")
    public WebElement originTripsTxt;

    @FindBy(id = "destination-trips")
    public WebElement destinationTripsTxt;

    @FindBy(id = "departure-trips")
    public WebElement departureCalendar;

    @FindBy(id = "arrival-trips")
    public WebElement arrivalCalendar;

    @FindBy(css = ".row-rooms>.sub")
    public WebElement subRoomBtn;

    @FindBy(css = ".row-rooms>.add")
    public WebElement addRoomBtn;

    @FindBy(css = ".row-room-details>div:nth-of-type(1)>.sub")
    public WebElement subAdultBtn;

    @FindBy(css = ".row-room-details>div:nth-of-type(1)>.add")
    public WebElement addAdultBtn;

    @FindBy(css = ".row-room-details>div:nth-of-type(2)>.sub")
    public WebElement subChildBtn;

    @FindBy(css = ".row-room-details>div:nth-of-type(2)>.add")
    public WebElement addChildBtn;

    //############################################### Actions ###############################################

    public TripsDataTrip setOrigin(String origin, String originFull) {
        waitElementForVisibility(driver, originTripsTxt, 10, "Origin text field");
        logger.info("Entering Trips Origin: [" + originFull + "]");
        originTripsTxt.clear();
        originTripsTxt.sendKeys(origin);
        selectAutoCompleteOption(originFull);
        return this;
    }

    public TripsDataTrip setDestination(String destination, String destinationFull) {
        waitElementForVisibility(driver, destinationTripsTxt, 10, "Destination text field");
        logger.info("Entering Trips Destination: [" + destinationFull + "]");
        destinationTripsTxt.clear();
        destinationTripsTxt.sendKeys(destination);
        selectAutoCompleteOption(destinationFull);
        return this;
    }

    public TripsDataTrip selectPassenger(int adults, int childs, int rooms) {
        personasTxt.click();

        if (adults>2){
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

            List<WebElement> dropDownList = driver.findElements(By.cssSelector(".row-yougers-details>.input--block"));
            for(int i=0; i<childs; i++) {
                Select dropdown = new Select (dropDownList.get(i));
                int randomNum = getRandomNumberInRange(2, 11); //change child age < 12 (issue between flight and hotel)
                dropdown.selectByVisibleText(String.valueOf(randomNum));
                logger.info("Adding: [1 child - Age: " + randomNum + "]" );
            }
        }
        listoBtn.click();

        logger.info("Total Adults: [" + adults + "]");
        logger.info("Total Childs: [" + childs + "]");
        return this;
    }

    public TripsResultsPage clickBuscarBtn() {
        logger.info("Clicking on Buscar Button");
        buscarBtn.click();
        return initTripsResultsPage();
    }
}
