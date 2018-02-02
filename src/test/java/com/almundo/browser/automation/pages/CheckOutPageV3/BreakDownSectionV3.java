package com.almundo.browser.automation.pages.CheckOutPageV3;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.almundo.browser.automation.utils.Constants.MEXICO;
import static com.almundo.browser.automation.utils.PageUtils.*;

/**
 * Created by gabrielcespedes on 07/08/17.
 */
public class BreakDownSectionV3 extends CheckOutPageV3 {

    public BreakDownSectionV3(WebDriver driver) {
        super(driver);
    }

    /**************************** Locators **********************************/

    final String finalPriceLct = "#product-resume span.price__amount";
    @FindBy(css = finalPriceLct)
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

    @FindBy(css ="#healthcross-input")
    public List<WebElement> insuranceRdb;

    @FindBy(css = "#radio-yes")
    public WebElement addTransferRdb;

    /**************************** Actions **********************************/

    public int getFinalPrice(){
        waitWithTryCatch(driver, finalPriceLct, "Final Price to Pay", 10);
        waitImplicitly(4000);
        logger.info("Final price to pay now is :[" + finalPrice.getText() +"]");
        return Integer.parseInt(finalPrice.getText().replace(",", "").
                                                    replace(".","").
                                                    replaceAll("\\s", ""));
    }

    public String getFinalPriceString(){
        waitElementForVisibility(driver, finalPrice, 15, "[Breakdown Final Price]");
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

    public BreakDownSectionV3 dealWithInsurance(Boolean selectInsurance){
        if(selectInsurance) {
            getFinalPrice();
            clickAddInsurance();
            scrollToElement(driver, finalPrice);
            setInputDef();
            addInsurance = false;
        } else {
          logger.info("Not adding insurance.");
        }
        return this;
    }

    public BreakDownSectionV3 clickAddInsurance(){
        logger.info("Adding Insurance, looking for the radio button.");
        scrollToTop(driver);
        scrollToCoordinate(driver, 400);
        logger.info("Clicking on [Add Insurance] radio button.");
        insuranceRdb.get(0).click();
        getFinalPrice();
        return this;
    }

    public BreakDownSectionV3 dealWithTransfer(Boolean selectTransfer){
        if(selectTransfer) {
            getFinalPrice();
            clickAddTransfer();
        } else {
            logger.info("Not adding transfer.");
        }
        return this;
    }
    public BreakDownSectionV3 clickAddTransfer(){
        logger.info("Adding Transfer, looking for the radio button.");
        try{
            scrollToElement(driver, addTransferRdb);
            scrollToCoordinate(driver, -500);
            addTransferRdb.click();
        }catch(WebDriverException ouch){
            scrollToBottom(driver);
            scrollToElement(driver, addTransferRdb);
            logger.info("Clicking on [Add Transfer] radio button.");
            addTransferRdb.click();
        }
        getFinalPrice();
        return this;
    }

    public BreakDownSectionV3 clickRemoveInsurance(){
        logger.info("Removing Insurance, looking for the radio button.");
        scrollToTop(driver);
        scrollToCoordinate(driver, 400);
        logger.info("Clicking on [Remove Insurance] radio button.");
        insuranceRdb.get(1).click();
        return this;
    }
}