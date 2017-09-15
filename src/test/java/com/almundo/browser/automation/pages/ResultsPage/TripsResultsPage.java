package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.almundo.browser.automation.utils.Constants.Messages.*;
import static com.almundo.browser.automation.utils.Constants.Results.FAILED;
import static com.almundo.browser.automation.utils.PageUtils.formatInfo;
import static com.almundo.browser.automation.utils.PageUtils.scrollToElement;
import static com.almundo.browser.automation.utils.PageUtils.waitElementForClickable;

/**
 * Created by gabrielcespedes on 14/12/16.
 */
public class TripsResultsPage extends TestBaseSetup {

    public TripsResultsPage(WebDriver driver) {
        super.driver = driver;
    }

    //############################################### Locators ##############################################

    @FindBy(id = "continue-v2")
    private WebElement continuarBtn;

    @FindBy(css = ".details>hotel-summary")
    private WebElement detailsHotelSummary;

    @FindBy(css = "pricebox")
    private WebElement tripPricebox;

    @FindBy(css = "#trip-options hotel-option-list-item")
    public List<WebElement> tripsChoices;


    //############################################### Actions ##############################################

    public String getHotelName(){
        return formatInfo(detailsHotelSummary.getText());
    }

    public List<WebElement> getHotelAmenities(){
        List<WebElement> hotelAmenitiesList = driver.findElements(By.cssSelector(".hotel.active > div > div.details > hotel-summary > div.amenities > span > div"));
        logger.info("Checking Hotel Amenities...");
        return hotelAmenitiesList;
    }

    public String getHotelStars(){
        List<WebElement> hotelStarsList = driver.findElements(By.cssSelector(".hotel.active > div > div.details > hotel-summary > star-rating .icon-star.star--lg"));
        return String.valueOf(hotelStarsList.size());
    }

    public void displayHotelInfo() {
        int hotelAmenityIndex = 1;
        logger.info("Hotel name: " + getHotelName());
        logger.info("Hotel category: " + "[" + getHotelStars() + " stars]");
        for (WebElement amenity : getHotelAmenities()) {
            logger.info("Hotel amenity " + hotelAmenityIndex++ + ": [" + amenity.getAttribute("aria-label").toString() + "]");
        }
    }

    public void displayDepartureFlightInfo(WebElement departureFlightInfo) {
        logger.info("Departure Flight Info: " + "[" +  formatInfo(departureFlightInfo.getText())+ "]");
    }

    public void displayReturnFlightInfo(WebElement returnFlightInfo) {
        logger.info("Return Flight Info: " + "[" + formatInfo(returnFlightInfo.getText()) + "]");
    }

    public void displayFlightInfo(){
        List<WebElement> flightSegmentsList = driver.findElements(By.cssSelector(".content>flight-segment"));
        displayDepartureFlightInfo(flightSegmentsList.get(0));
        displayReturnFlightInfo(flightSegmentsList.get(1));
    }

    public void displayTripsRatesInfo(){
        logger.info("Trip rates info: " + "[" + formatInfo(tripPricebox.getText())+ "]");
    }

    public void displayTripInfo(){
        displayHotelInfo();
        displayFlightInfo();
        displayTripsRatesInfo();
    }

    public TripsDetailPage clickContinuarBtn() {
        PageUtils.waitElementForVisibility(driver,continuarBtn,30, "Continuar Button");
        displayTripInfo();
        logger.info("Clicking on Continuar button");
        continuarBtn.click();
        PageUtils.waitImplicitly(2000);
        return initTripsDetailPage();
    }

    public TripsResultsPage clickElegirBtn(int index) {
        String cssSelectorNameElegir = ".button.button--secondary.button--block.button--md";
        PageUtils.waitListContainResults(driver, cssSelectorNameElegir, 0);
        List<WebElement> elegirBtn = driver.findElements(By.cssSelector(cssSelectorNameElegir));
        waitElementForClickable(driver, elegirBtn.get(index), 5, "[Elegir] button clickable.");
        logger.info("Clicking on [Elegir] button index: [" + index + "]");
        scrollToElement(driver, elegirBtn.get(index));
        elegirBtn.get(index).click();
        PageUtils.waitImplicitly(2000);
        return this;
    }

    public boolean vacancy(){
        PageUtils.waitUrlContains(driver, 10, "results", "Results url");
        logger.info("Results URL: " + "[" + driver.getCurrentUrl() + "]");
        try {
            PageUtils.waitForNoVacancy(driver, By.cssSelector("div.alert__text > p:nth-child(4)"), 5, "[" + VOLVE_A_INTENTARLO_MSG + "] message");
        } catch (Exception ex){
            return true;
        }
        setResultSauceLabs(FAILED);
        return false;
    }

    public List<WebElement> getTripsChoices(){
        return tripsChoices;
    }
}
