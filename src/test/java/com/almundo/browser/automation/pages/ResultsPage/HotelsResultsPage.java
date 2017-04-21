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
        PageUtils.waitListContainResults(driver, ".button.button--md.button--secondary.button-detail", 0);
        WebElement hotelName = driver.findElement(By.cssSelector("div:nth-child(" + (index+1) + ") > hotel-card > article > div > div.hotel-description > a > h1"));
        return formatInfo(hotelName.getText());
    }

    public String getHotelStars(int index){
        List<WebElement> hotelStarsList = driver.findElements(By.cssSelector("div:nth-child(" + (index+1) + ") > hotel-card > article > div > div.hotel-description > p.star-ctn > span"));
        return String.valueOf(hotelStarsList.size());
    }

    public String getHotelRates(int index){
        WebElement hotelPriceBox = driver.findElement(By.cssSelector("div:nth-child(" + (index+1) +") > hotel-card > article > div > div.price-box-ctn"));
        return formatInfo(hotelPriceBox.getText());
    }

    public List<WebElement> getHotelAmenities(int index){
        List<WebElement> hotelAmenitiesList = driver.findElements(By.cssSelector("div:nth-child(" + (index+1) + ") > hotel-card > article > div > div.hotel-description > div.amenities-ctn.ng-scope > ul > li"));
        logger.info("Checking Hotel Amenities...");
        return hotelAmenitiesList;
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
        displayHotelInfo(index);
        WebElement verHotelButton = driver.findElement(By.cssSelector("div:nth-child(" + (index+1) + ") > hotel-card > article > div > div.price-box-ctn > div > a"));
        logger.info("Clicking on button: [Ver Hotel]");
        verHotelButton.click();
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
