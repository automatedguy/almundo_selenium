package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.pages.ResultsPage.HotelesResultsPage;
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
 * Created by leandro.efron on 5/12/2016.
 */
public class HotelesDataTrip extends BasePage {

    public static JSONObject hotelesDataTripList = null;
    public static JSONObject hotelDataTripItinerary = null;

    public static String destinationAuto;
    public static String destinationFull;
    public static int startDate;
    public static int endDate;
    public static int adults;
    public static int childs;
    public static int rooms;

    public HotelesDataTrip(WebDriver driver) {
        super(driver);
    }

    //############################################### Locators ##############################################

    @FindBy(id = "destination-hotels")
    public WebElement destinationTxt;

    @FindBy(id = "checkin-hotels")
    public WebElement checkinCalendar;

    @FindBy(id = "checkout-hotels")
    public WebElement checkoutCalendar;

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

    public HotelesDataTrip setDestination(String destinationAuto, String destinationFull) {
        PageUtils.waitElementForVisibility(driver, destinationTxt, 10, "Destination text field");
        logger.info("Entering Destination: [" + destinationFull + "]");
        destinationTxt.clear();
        destinationTxt.sendKeys(destinationAuto);
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

    public HotelesResultsPage clickBuscarBtn() {
        logger.info("Clicking on Buscar Button");
        buscarBtn.click();
        return initHotelesResultsPage();
    }

    public static void getHotelesDataTripList() {
        hotelesDataTripList = JsonRead.getJsonDataObject(jsonDataObject, "hoteles", countryPar.toLowerCase() + "_data.json");
    }

    public static void getHotelDataTripItinerary(String dataSet) {
        hotelDataTripItinerary = JsonRead.getJsonDataObject(hotelesDataTripList, dataSet, countryPar.toLowerCase() + "_data.json");

        destinationAuto = hotelDataTripItinerary.get("destinationAuto").toString();
        destinationFull = hotelDataTripItinerary.get("destinationFull").toString();

        startDate = Integer.parseInt(hotelDataTripItinerary.get("startDate").toString());
        endDate = Integer.parseInt(hotelDataTripItinerary.get("endDate").toString());

        adults = Integer.parseInt(hotelDataTripItinerary.get("adults").toString());
        childs = Integer.parseInt(hotelDataTripItinerary.get("childs").toString());

        rooms = Integer.parseInt(hotelDataTripItinerary.get("rooms").toString());
    }

}
