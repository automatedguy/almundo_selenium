package com.almundo.browser.automation.pages.CheckOutPageV3;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.almundo.browser.automation.utils.Constants.MEXICO;

/**
 * Created by gabrielcespedes on 07/08/17.
 */
public class BreakDownSectionV3 extends CheckOutPageV3 {

    public BreakDownSectionV3(WebDriver driver) {
        super(driver);
    }

    /**************************** Locators **********************************/

    @FindBy(css = "#product-resume span.price__amount")
    public WebElement finalPrice;

    @FindBy(css = ".flight-detail-content")
    public WebElement flightDetailContent;

    @FindBy(css = ".hotel-data .info-container")
    public WebElement hotelDetailContent;

    @FindBy(css = ".car-product-checkout")
    public WebElement carsDetailContent;

    @FindBy(css = "")
    public WebElement tripsDetailContent;

    @FindBy(css = "")
    public WebElement assuranceDetailContent;

    /**************************** Actions **********************************/

    public int getFinalPrice(){
        logger.info("Getting Total Final Price.");
        if(!countryPar.equals(MEXICO)) {
            return Integer.parseInt(finalPrice.getText().replace(".", "").replaceAll("\\s", ""));
        }
        else{
            return Integer.parseInt(finalPrice.getText().replace(",", "").replaceAll("\\s", ""));
        }
    }

    public String getFinalPriceString(){
        logger.info("Getting Total Final Price: [" + finalPrice.getText().replaceAll("\\s", "") + "]");
        return finalPrice.getText().replaceAll("\\s", "");
    }

    public String getFlightDetailContent(){
        logger.info("Retrieving Flight Detail.");
        logger.info("[" + flightDetailContent.getText() + "]");
        return flightDetailContent.getText();
    }

    public String getHotelDetailContent(){
        logger.info("Retrieving Hotel Details.");
        logger.info("[" + hotelDetailContent.getText() + "]");
        return hotelDetailContent.getText();
    }

    public String getCarsDetailContent(){
        logger.info("Retrieving Car Details.");
        logger.info("[" + carsDetailContent.getText() + "]");
        return carsDetailContent.getText();
    }

    public String getTripsDetailContent(){
        logger.info("Retrieving Trips Details.");
        logger.info("[" + tripsDetailContent.getText() + "]");
        return tripsDetailContent.getText();
    }
}