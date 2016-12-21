package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.pages.ResultsPage.AutosResultsPage;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by gabrielcespedes on 20/12/16.
 */
public class AutosDataTrip extends BasePage {

    public static JSONObject autosDataTripList = null;
    public static JSONObject autosDataTripItinerary = null;


    public static String originAuto;
    public static String originFull;
    public static String destinationAuto;
    public static String destinationFull;
    public static int startDate;
    public static int endDate;
    public static String pickUpTime;
    public static String dropOffTime;
    public static String ageRange;

    public AutosDataTrip(WebDriver iDriver) {
        super(iDriver);
    }

    //############################################### Locators ##############################################

    @FindBy(id = "origin")
    public WebElement originTxt;

    @FindBy(id = "destination")
    public WebElement destinationTxt;

    @FindBy(id = "pickUpDate")
    public WebElement pickUpDateCalendar;

    @FindBy(id = "dropOffDate")
    public WebElement dropOffDateCalendar;

    @FindBy(css = "div:nth-child(2)>div:nth-child(3)>div>select")
    public WebElement pickUpTimeSelect;

    @FindBy(css = "div:nth-child(2)>div:nth-child(6)>div>select")
    public WebElement dropOffTimeSelect;

    @FindBy(css = "div:nth-child(2)>div:nth-child(7)>div>select")
    public WebElement ageRangeSelect;

    //############################################### Actions ###############################################

    public AutosDataTrip setOrigin(String origin, String originFull) {
        PageUtils.waitElementForVisibility(driver, originTxt, 10, "Pick Up Origin text field");
        logger.info("Entering Pick Up Origin: [" + originFull + "]");
        originTxt.clear();
        originTxt.sendKeys(origin);
        selectAutoCompleteOption(originFull);
        return this;
    }

    public AutosDataTrip setDestination(String destination, String destinationFull) {
        PageUtils.waitElementForVisibility(driver, destinationTxt, 10, "Drop Off Destination text field");
        logger.info("Entering Drop Off Destination: [" + destinationFull + "]");
        destinationTxt.clear();
        destinationTxt.sendKeys(destination);
        selectAutoCompleteOption(destinationFull);
        return this;
    }

    public AutosDataTrip selectAgeRange(String ageRange) {
        Select ageRangeDdl = new Select(ageRangeSelect);
        logger.info("Selecting Age Range: [" + ageRange + "]");
        ageRangeDdl.selectByVisibleText(ageRange);
        return this;
    }

    public AutosResultsPage clickBuscarBtn() {
        logger.info("Clicking on Buscar Button");
        buscarBtn.click();
        return initAutosResultsPage();
    }

    public static void getAutosDataTripList() {
        autosDataTripList = JsonRead.getJsonDataObject(jsonDataObject, "autos", countryPar.toLowerCase() + "_data.json");
    }

    public static void getAutosDataTripItinerary(String dataSet) {
        autosDataTripItinerary = JsonRead.getJsonDataObject(autosDataTripList, dataSet, countryPar.toLowerCase() + "_data.json");

        originAuto = autosDataTripItinerary.get("originAuto").toString();
        originFull = autosDataTripItinerary.get("originFull").toString();

        destinationAuto = autosDataTripItinerary.get("destinationAuto").toString();
        destinationFull = autosDataTripItinerary.get("destinationFull").toString();

        startDate = Integer.parseInt(autosDataTripItinerary.get("startDate").toString());
        endDate = Integer.parseInt(autosDataTripItinerary.get("endDate").toString());

        pickUpTime = autosDataTripItinerary.get("pickUpTime").toString();
        dropOffTime = autosDataTripItinerary.get("dropOffTime").toString();

        ageRange = autosDataTripItinerary.get("ageRange").toString();
    }
}
