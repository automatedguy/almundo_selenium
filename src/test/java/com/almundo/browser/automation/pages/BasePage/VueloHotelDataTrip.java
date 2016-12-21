package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.pages.ResultsPage.VueloHotelResultsPage;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

/**
 * Created by leandro.efron on 6/12/2016.
 */
public class VueloHotelDataTrip extends BasePage {

    public VueloHotelDataTrip(WebDriver driver) {
        super(driver);
    }

    public static JSONObject vueloHotelDataTripList = null;
    public static JSONObject vueloHotelDataTripItinerary = null;

    public static String originAuto;
    public static String originFull;
    public static String destinationAuto;
    public static String destinationFull;
    public static int startDate;
    public static int endDate;
    public static int adults;
    public static int childs;
    public static int rooms;

    //############################################### Locators ##############################################

    @FindBy(id = "origin-trips")
    public WebElement originTripsTxt;

    @FindBy(id = "destination-trips")
    public WebElement destinationTripsTxt;

    @FindBy(id = "departure-trips")
    public WebElement departureCalendar;

    @FindBy(id = "arrival-trips")
    public WebElement arrivalCalendar;

    @FindBy(css = ".row-rooms>.sub")
    public WebElement subRoomBtn;

    @FindBy(css = ".row-rooms>.add")
    public WebElement addRoomBtn;

    @FindBy(css = ".row-room-details>div:nth-of-type(1)>.sub")
    public WebElement subAdultBtn;

    @FindBy(css = ".row-room-details>div:nth-of-type(1)>.add")
    public WebElement addAdultBtn;

    @FindBy(css = ".row-room-details>div:nth-of-type(2)>.sub")
    public WebElement subChildBtn;

    @FindBy(css = ".row-room-details>div:nth-of-type(2)>.add")
    public WebElement addChildBtn;

    //############################################### Actions ###############################################

    public VueloHotelDataTrip setOrigin(String origin, String originFull) {
        PageUtils.waitElementForVisibility(driver, originTripsTxt, 10, "Origin text field");
        logger.info("Entering Origin: [" + originFull + "]");
        originTripsTxt.clear();
        originTripsTxt.sendKeys(origin);
        selectAutoCompleteOption(originFull);
        return this;
    }

    public VueloHotelDataTrip setDestination(String destination, String destinationFull) {
        PageUtils.waitElementForVisibility(driver, destinationTripsTxt, 10, "Destination text field");
        logger.info("Entering Destination: [" + destinationFull + "]");
        destinationTripsTxt.clear();
        destinationTripsTxt.sendKeys(destination);
        selectAutoCompleteOption(destinationFull);
        return this;
    }

    public int selectPassenger(int adults, int childs, int rooms) {
        personasTxt.click();

        if (adults>2){
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

            List<WebElement> dropDownList = driver.findElements(By.cssSelector(".row-yougers-details>.input--block"));
            for(int i=0; i<childs; i++) {
                Random rand = new Random();
                int randomNum = rand.nextInt((17 - 1) + 1) + 1;
                Select dropdown = new Select (dropDownList.get(i));
                dropdown.selectByVisibleText(String.valueOf(randomNum));
            }
        }
        listoBtn.click();

        return adults + childs;
    }

    public VueloHotelResultsPage clickBuscarBtn() {
        logger.info("Clicking on Buscar Button");
        buscarBtn.click();
        return initVueloHotelResultsPage();
    }

    public static void getVueloHotelDataTripList() {
        vueloHotelDataTripList = JsonRead.getJsonDataObject(jsonDataObject, "vueloHotel", countryPar.toLowerCase() + "_data.json");
    }

    public static void getVueloHotelDataTripItinerary(String dataSet) {
        vueloHotelDataTripItinerary = JsonRead.getJsonDataObject(vueloHotelDataTripList, dataSet, countryPar.toLowerCase() + "_data.json");

        originAuto = vueloHotelDataTripItinerary.get("originAuto").toString();
        originFull = vueloHotelDataTripItinerary.get("originFull").toString();

        destinationAuto = vueloHotelDataTripItinerary.get("destinationAuto").toString();
        destinationFull = vueloHotelDataTripItinerary.get("destinationFull").toString();

        startDate = Integer.parseInt(vueloHotelDataTripItinerary.get("startDate").toString());
        endDate = Integer.parseInt(vueloHotelDataTripItinerary.get("endDate").toString());

        adults = Integer.parseInt(vueloHotelDataTripItinerary.get("adults").toString());
        childs = Integer.parseInt(vueloHotelDataTripItinerary.get("childs").toString());

        rooms = Integer.parseInt(vueloHotelDataTripItinerary.get("rooms").toString());
    }

}
