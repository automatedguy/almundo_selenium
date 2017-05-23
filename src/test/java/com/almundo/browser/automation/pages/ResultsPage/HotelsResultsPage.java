package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.almundo.browser.automation.utils.Constants.FIRST_OPTION;
import static com.almundo.browser.automation.utils.Constants.Messages.NO_DISPONIBILIDAD_MSG;
import static com.almundo.browser.automation.utils.PageUtils.formatInfo;

/**
 * Created by leandro.efron on 13/12/2016.
 */
public class HotelsResultsPage extends TestBaseSetup {

    public HotelsResultsPage(WebDriver iDriver) {
        this.driver = iDriver;
        HotelsResultsPageLocators locators = new HotelsResultsPageLocators(driver, FIRST_OPTION);
        hotelNameCssSelector = locators.setHotelNameLocator();
        hotelStarsListCssSelector = locators.setHotelStarsListLocator();
        hotelPriceBoxCssSelector = locators.setHotelPriceBoxLocator();
        hotelAmenitiesListCssSelector = locators.setHotelAmenitiesListLocator();
        verHotelButtonCssSelector = locators.setVerHotelButtonLocator();
        hotelChoicesLocator = locators.sethotelChoicesLocator();
    }

    //############################################### Locators ##############################################

    int index = 0;

    private String hotelNameCssSelector;
    private WebElement hotelName;

    private String hotelStarsListCssSelector;
    private List<WebElement> hotelStarsList;

    private String hotelPriceBoxCssSelector;
    private WebElement hotelPriceBox;

    private String hotelAmenitiesListCssSelector;
    private List<WebElement> hotelAmenitiesList;

    private String verHotelButtonCssSelector;
    private WebElement verHotelButton;

    private String hotelChoicesLocator;
    public List<WebElement> hotelChoices;

    //############################################### Actions ###############################################

    public String getHotelName(){
        PageUtils.waitListContainResults(driver, ".button-detail", 0);
        hotelName = driver.findElement(By.cssSelector(hotelNameCssSelector));
        return formatInfo(hotelName.getText());
    }

    public String getHotelStars(){
        hotelStarsList =  driver.findElements(By.cssSelector(hotelStarsListCssSelector));
        return String.valueOf(hotelStarsList.size());
    }

    public String getHotelRates(){
        hotelPriceBox = driver.findElement(By.cssSelector(hotelPriceBoxCssSelector));
        return formatInfo(hotelPriceBox.getText());
    }

    public List<WebElement> getHotelAmenities(){
        logger.info("Checking Hotel Amenities...");
        hotelAmenitiesList = driver.findElements(By.cssSelector(hotelAmenitiesListCssSelector));
        return hotelAmenitiesList;
    }

    public void displayHotelInfo(){
        int hotelAmenityIndex = 1;
        logger.info("Hotel name: " + "[" + getHotelName() + "]");
        logger.info("Hotel category: " + "[" + getHotelStars() + " stars]");
        logger.info("Hotel rates: " + "[" + getHotelRates() + "]");
        for(WebElement amenity : getHotelAmenities()){
            logger.info("Hotel amenity " + hotelAmenityIndex++ + ": [" +amenity.getAttribute("data-hint").toString() + "]");
        }
    }

    public HotelsDetailPage clickVerHotelBtn(int option) {
        index = option;
        displayHotelInfo();
        logger.info("Clicking on button: [Ver Hotel]");
        verHotelButton = driver.findElement(By.cssSelector(verHotelButtonCssSelector));
        verHotelButton.click();
        return initHotelsDetailPage();
    }

    public boolean vacancy(){
        PageUtils.waitUrlContains(driver, 5, "results", "Results url");
        logger.info("Results URL: " + "[" + driver.getCurrentUrl() + "]");

        try {
            PageUtils.waitForNoVacancy(driver, By.cssSelector(".main-loader>am-alert>div>div:nth-of-type(2)"), 5, "[" + NO_DISPONIBILIDAD_MSG + "] message");
        } catch (Exception ex){
            return true;
        }
        return false;
    }

    public List<WebElement> getHotelChoices(){
        hotelChoices = driver.findElements(By.cssSelector(hotelChoicesLocator));
        return hotelChoices;
    }
}