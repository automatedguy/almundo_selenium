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

    private boolean newPageLayout = false;
    int index = 0;

    public HotelsResultsPage(WebDriver iDriver) {
        this.driver = iDriver;
    }

    //############################################### Locators ##############################################

    //############################################### Actions ###############################################


    public String getHotelName(){
        String hotelNameCssSelector = null;
        PageUtils.waitListContainResults(driver, ".button-detail", 0);
        if(newPageLayout) {
            hotelNameCssSelector = "div:nth-child(" + (index + 1) + ") > hotel-card > article > div > div.hotel-description > a > h1" ;
        }else {
            hotelNameCssSelector = "hotel:nth-child(" + (index + 1) + ") > article > div.col-5.col-12--ph > div > a > h1";
        }
        WebElement hotelName = driver.findElement(By.cssSelector(hotelNameCssSelector));
        return formatInfo(hotelName.getText());
    }

    public String getHotelStars(){
        String hotelStarsListCssSelector = null;
        if(newPageLayout) {
            hotelStarsListCssSelector = "div:nth-child(" + (index + 1) + ") > hotel-card > article > div > div.hotel-description > p.star-ctn > span";
        }else {
            hotelStarsListCssSelector = "hotel:nth-child(" + (index + 1) + ") > article > div.col-5.col-12--ph > div > p.star-ctn > span";
        }
        List<WebElement> hotelStarsList =  driver.findElements(By.cssSelector(hotelStarsListCssSelector));
        return String.valueOf(hotelStarsList.size());
    }

    public String getHotelRates(){
        String hotelPriceBoxCssSelector = null;
        if(newPageLayout) {
            hotelPriceBoxCssSelector = "div:nth-child(" + (index + 1) + ") > hotel-card > article > div > div.price-box-ctn" ;
        }else {
            hotelPriceBoxCssSelector = "hotel:nth-child(" + (index + 1) + ") > article > div.price-box-ctn.col-3.col-12--ph > div";

        }
        WebElement hotelPriceBox = driver.findElement(By.cssSelector(hotelPriceBoxCssSelector));
        return formatInfo(hotelPriceBox.getText());
    }

    public List<WebElement> getHotelAmenities(){
        String hotelAmenitiesListCssSelector = null;
        logger.info("Checking Hotel Amenities...");
        if(newPageLayout){
            hotelAmenitiesListCssSelector = "div:nth-child(" + (index+1) + ") > hotel-card > article > div > div.hotel-description > div.amenities-ctn.ng-scope > ul > li";
        }
        else{
            hotelAmenitiesListCssSelector = "hotel:nth-child(" + (index+1) + ") > article > div.col-5.col-12--ph > div > div.amenities-ctn.ng-scope > ul > li";

        }
        List<WebElement> hotelAmenitiesList = driver.findElements(By.cssSelector(hotelAmenitiesListCssSelector));
        return hotelAmenitiesList;
    }

    public void displayHotelInfo(){
        int hotelAmenityIndex = 1;
        logger.info("Hotel name: " + getHotelName());
        logger.info("Hotel category: " + "[" + getHotelStars() + " stars]");
        logger.info("Hotel rates: " + "[" + getHotelRates() + "]");
        for(WebElement amenity : getHotelAmenities()){
            logger.info("Hotel amenity " + hotelAmenityIndex++ + ": [" +amenity.getAttribute("data-hint").toString() + "]");
        }
    }

    public HotelsDetailPage clickVerHotelBtn(int option) {
        newPageLayout = cardTrue();
        index = option;
        displayHotelInfo();
        String verHotelButtonCssSelector = null;
        if(newPageLayout) {
            verHotelButtonCssSelector = "div:nth-child(" + (index + 1) + ") > hotel-card > article > div > div.price-box-ctn > div > a";
        }else{
            verHotelButtonCssSelector = "hotel:nth-child(" + (index + 1) + ") > article > div.price-box-ctn.col-3.col-12--ph > div > a";
        }
        logger.info("Clicking on button: [Ver Hotel]");
        WebElement verHotelButton = driver.findElement(By.cssSelector(verHotelButtonCssSelector));
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
