package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.almundo.browser.automation.utils.Constants.Messages.*;
import static com.almundo.browser.automation.utils.PageUtils.formatInfo;

/**
 * Created by leandro.efron on 13/12/2016.
 */
public class HotelsResultsPage extends TestBaseSetup {

    public HotelsResultsPage(WebDriver iDriver) {
        this.driver = iDriver;
    }

    //############################################### Locators ##############################################

    //############################################### Actions ###############################################


    public void displayHotelName(int index){
        PageUtils.waitListContainResults(driver, ".button.button--lg.button--secondary.button--block.button-detail", 0);
        List<WebElement> hotelNameList = driver.findElements(By.cssSelector(".hotel-description"));
        logger.info("Hotel name: " + "[" + formatInfo(hotelNameList.get(index).getText()) + "]");
    }

    public void displayHotelAmenities(int index){
        List<WebElement> hotelAmenitiesList = driver.findElements(By.cssSelector("hotel:nth-child(" + (index+1) + ") article > div.col-5.col-12--ph > div > div.amenities-ctn.ng-scope > ul > li"));
        int amenityNumber = 0;
        logger.info("Checking Hotel Amenities...");
        for(WebElement amenity : hotelAmenitiesList){
            amenityNumber++;
            logger.info("Hotel amenity " + (amenityNumber) + ": [" +amenity.getAttribute("data-hint").toString() + "]");
        }
    }

    public void displayHotelStars(int index){
        List<WebElement> hotelStarsList = driver.findElements(By.cssSelector("hotel:nth-child(" + (index+1) + ") article > div.col-5.col-12--ph > div > p.star-ctn > span"));
        logger.info("Hotel category: " + "[" +(hotelStarsList.size()) + " stars]");
    }

    public void displayHotelRates(int index){
        List<WebElement> hotelPriceBoxList = driver.findElements(By.cssSelector(".price-box-ctn.col-3.col-12--ph"));
        logger.info("Hotel rates: " + "[" + formatInfo(hotelPriceBoxList.get(index).getText()) + "]");
    }

    public void displayHotelInfo(int index){
        displayHotelName(index);
        displayHotelStars(index);
        displayHotelAmenities(index);
        displayHotelRates(index);
    }

    public HotelsDetailPage clickVerHotelBtn(int index) {
        displayHotelInfo(index);
        List<WebElement> verHotelButtonResults = driver.findElements(By.cssSelector(".button.button--lg.button--secondary.button--block.button-detail"));
        logger.info("Clicking on button: [Ver Hotel]");
        verHotelButtonResults.get(index).click();
        return initHotelsDetailPage();
    }

    public boolean vacancy(){
        try {
            PageUtils.waitForNoVacancy(driver, By.cssSelector(".main-loader>am-alert>div>div:nth-of-type(2)"), 5, "[" + NO_DISPONIBILIDAD_MSG + "] message");
        } catch (Exception ex){
            return true;
        }
        return false;
    }
}
