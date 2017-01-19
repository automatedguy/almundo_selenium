package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.pages.ResultsPage.VuelosResultsPage;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by gabrielcespedes on 05/12/16.
 */
public class VuelosDataTrip extends BasePage{

    public static JSONObject vueloDataTripList = null;
    public static JSONObject vueloDataTripItinerary = null;

    public static String originAuto;
    public static String originFull;
    public static String destinationAuto;
    public static String destinationFull;
    public static int startDate;
    public static int endDate;
    public static int adults;
    public static int childs;
    public static String childAgeRange;
    public static String flightClass;

    public VuelosDataTrip(WebDriver driver) {
        super(driver);
    }

    //############################################### Locators ##############################################

    @FindBy(id = "origin-flights")
    public WebElement originFlightsTxt;

    @FindBy(id = "destination-flights")
    public WebElement destinationFlightsTxt;

    @FindBy(id = "departure-flights")
    public WebElement departureFlightsCalendar;

    @FindBy(id = "arrival-flights")
    public WebElement arrivalFlightsCalendar;

    @FindBy(id = "class-flights")
    public WebElement classFlightsDdl;

    @FindBy(css = ".row-adults>.sub")
    public WebElement subAdultsBtn;

    @FindBy(css = ".row-adults>.add")
    public WebElement addAdultBtn;

    @FindBy(css = ".row-youngers>.sub")
    public WebElement subChildBtn;

    @FindBy(css = ".row-youngers>.add")
    public WebElement addChildBtn;

    @FindBy(name = "class-flights")
    public WebElement classFlightDdl;

    //############################################### Actions ###############################################

    public VuelosDataTrip setOrigin(String origin, String originFull) {
        PageUtils.waitElementForVisibility(driver, originFlightsTxt, 10, "Origin text field");
        logger.info("Entering Flight Origin: [" + originFull + "]");
        originFlightsTxt.clear();
        originFlightsTxt.sendKeys(origin);
        selectAutoCompleteOption(originFull);
        return this;
    }

    public VuelosDataTrip setDestination(String destination, String destinationFull) {
        PageUtils.waitElementForVisibility(driver, destinationFlightsTxt, 10, "Destination text field");
        logger.info("Entering Flight Destination: [" + destinationFull + "]");
        destinationFlightsTxt.clear();
        destinationFlightsTxt.sendKeys(destination);
        selectAutoCompleteOption(destinationFull);
        return this;
    }

    public int selectPassenger(int adults, int childs) {
        personasTxt.click();

        if (adults>1){
            for(int i=1; i<adults; i++) {
                logger.info("Adding: [1 adult]");
                addAdultBtn.click();
            }
        }

        if (childs>0){
            for(int i=0; i<childs; i++) {
                logger.info("Adding: [1 child]");
                addChildBtn.click();
            }
        }
        return adults + childs;
    }

    public VuelosDataTrip selectChildAgeRange(String ageRange) {
        if (childs>0){
            for(int i=0; i<childs; i++) {
                Select ageSelection = new Select(driver.findElement(By.id("age-" + i)));
                ageSelection.selectByVisibleText(ageRange);
            }
        }
        listoBtn.click();
        return this;
    }

    public VuelosDataTrip selectClass(String flightClass) {
        Select claseVueloDdl = new Select(classFlightDdl);
        logger.info("Selecting Flight Class: [" + flightClass + "]");
        claseVueloDdl.selectByVisibleText(flightClass);
        return this;
    }

    public VuelosResultsPage clickBuscarBtn() {
        logger.info("Clicking on Buscar Button");
        buscarBtn.click();
        return initVuelosResultsPage();
    }

    public static void getVuelosDataTripList() {
        vueloDataTripList = JsonRead.getJsonDataObject(jsonDataObject, "vuelos", countryPar.toLowerCase() + "_data.json");
    }

    public static void getVuelosDataTripItinerary(String dataSet) {
        vueloDataTripItinerary = JsonRead.getJsonDataObject(vueloDataTripList, dataSet, countryPar.toLowerCase() + "_data.json");

        originAuto = vueloDataTripItinerary.get("originAuto").toString();
        originFull = vueloDataTripItinerary.get("originFull").toString();

        destinationAuto = vueloDataTripItinerary.get("destinationAuto").toString();
        destinationFull = vueloDataTripItinerary.get("destinationFull").toString();

        startDate = Integer.parseInt(vueloDataTripItinerary.get("startDate").toString());
        endDate = Integer.parseInt(vueloDataTripItinerary.get("endDate").toString());

        adults = Integer.parseInt(vueloDataTripItinerary.get("adults").toString());
        childs = Integer.parseInt(vueloDataTripItinerary.get("childs").toString());
        childAgeRange = vueloDataTripItinerary.get("childAgeRange").toString();

        flightClass = vueloDataTripItinerary.get("flightClass").toString();
    }

}
