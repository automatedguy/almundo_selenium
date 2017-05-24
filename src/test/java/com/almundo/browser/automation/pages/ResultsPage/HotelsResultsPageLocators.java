package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import org.openqa.selenium.WebDriver;

import static com.almundo.browser.automation.utils.PageUtils.getHotelsResultsPageLayout;

/**
 * Created by gabi on 23/04/17.
 */
public class HotelsResultsPageLocators extends TestBaseSetup{

    public HotelsResultsPageLocators(WebDriver iDriver,int index){
        driver = iDriver;
        setLocators(index);
    }

    String nameLocator;
    String starsListLocator;
    String priceBoxLocator;
    String amenitiesListLocator;
    String verHotelButtonLocator;
    String hotelChoicesLocator;

    public String setHotelNameLocator(){ return nameLocator; }

    public String setHotelStarsListLocator(){ return starsListLocator; }

    public String setHotelPriceBoxLocator(){ return priceBoxLocator; }

    public String setHotelAmenitiesListLocator(){ return amenitiesListLocator; }

    public String setVerHotelButtonLocator(){ return verHotelButtonLocator;}

    public String sethotelChoicesLocator(){ return hotelChoicesLocator;}

    /*** Set the locators based on getHotelsResultsPageLayout results. ***/
    private void setLocators(int index){
        switch(getHotelsResultsPageLayout(driver)){
            case "card" :
                setCardLocators(index);
                break;
            case "normal" :
                setNormalLocators(index);
                break;
            default :
                logger.error("Current result page was not defined.");
                break;
        }
    }

    /*** When URL contains card=true. ***/
    private void setCardLocators(int index){
        logger.info("Setting results page layout with card=true parameter");
        nameLocator = "div:nth-child(" + (index + 1) + ") > hotel-card > article > div > div.hotel-description > a > h1";
        starsListLocator = "div:nth-child(" + (index + 1) + ") > hotel-card > article > div > div.hotel-description > p.star-ctn > span";
        priceBoxLocator = "div:nth-child(" + (index + 1) + ") > hotel-card > article > div > div.price-box-ctn";
        amenitiesListLocator = "div:nth-child(" + (index+1) + ") > hotel-card > article > div > div.hotel-description > div.amenities-ctn.ng-scope > ul > li";
        verHotelButtonLocator = "div:nth-child(" + (index + 1) + ") > hotel-card .price-box a";
        hotelChoicesLocator= "body section .content";
    }

    /*** When URL does not contain card=true is the old one. ***/
    private void setNormalLocators(int index){
        logger.info("Setting results page layout with no card=true parameter");
        nameLocator = "hotel:nth-child(" + (index + 1) + ") > article h1";
        starsListLocator = "hotel:nth-child(" + (index + 1) + ") > article p.star-ctn > span";
        priceBoxLocator = "hotel:nth-child(" + (index + 1) + ") > article .price-box-ctn.col-3.col-12--ph > div";
        amenitiesListLocator = "hotel:nth-child(" + (index+1) + ") > article .hint--bottom.hint--rounded";
        verHotelButtonLocator = "hotel:nth-child(" + (index + 1) + ") > article > div.price-box-ctn.col-3.col-12--ph div a";
        hotelChoicesLocator =  "body .hotel-ctn.am-ctn";
    }
}

