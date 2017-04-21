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


    public String getHotelName(int index){
        PageUtils.waitListContainResults(driver, ".button.button--lg.button--secondary.button--block.button-detail", 0);
        List<WebElement> hotelNameList = driver.findElements(By.cssSelector(".hotel-description"));
        return formatInfo(hotelNameList.get(index).getText());
    }

    public List<WebElement> getHotelAmenities(int index){
        List<WebElement> hotelAmenitiesList = driver.findElements(By.cssSelector("hotel:nth-child(" + (index+1) + ") article > div.col-5.col-12--ph > div > div.amenities-ctn.ng-scope > ul > li"));
        logger.info("Checking Hotel Amenities...");
        return hotelAmenitiesList;
    }

    public String getHotelStars(int index){
        List<WebElement> hotelStarsList = driver.findElements(By.cssSelector("hotel:nth-child(" + (index+1) + ") article > div.col-5.col-12--ph > div > p.star-ctn > span"));
        return String.valueOf(hotelStarsList.size());
    }

    public String getHotelRates(int index){
        List<WebElement> hotelPriceBoxList = driver.findElements(By.cssSelector(".price-box-ctn.col-3.col-12--ph"));
        return formatInfo(hotelPriceBoxList.get(index).getText());
    }

    public void displayHotelInfo(int index){
        int hotelAmenityIndex = 1;
        logger.info("Hotel name: " + getHotelName(index));
        logger.info("Hotel category: " + "[" + getHotelStars(index) + " stars]");
        logger.info("Hotel rates: " + "[" + getHotelRates(index) + "]");
        for(WebElement amenity : getHotelAmenities(index)){
            logger.info("Hotel amenity " + hotelAmenityIndex++ + ": [" +amenity.getAttribute("data-hint").toString() + "]");
        }

    }

    public HotelsDetailPage clickVerHotelBtn(int index) {
        //displayHotelInfo(index);
        List<WebElement> verHotelButtonResults = driver.findElements(By.cssSelector(".button.button--lg.button--secondary.button--block.button-detail"));
        logger.info("Clicking on button: [Ver Hotel]");
        verHotelButtonResults.get(index).click();
        return initHotelsDetailPage();
    }

    public boolean vacancy(){
        logger.info("Results URL: " + "[" + driver.getCurrentUrl() + "]");
        try {
            PageUtils.waitForNoVacancy(driver, By.cssSelector(".main-loader>am-alert>div>div:nth-of-type(2)"), 5, "[" + NO_DISPONIBILIDAD_MSG + "] message");
        } catch (Exception ex){
            return true;
        }
        return false;
    }
}
