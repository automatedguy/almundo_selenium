package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.pages.ResultsPage.FlightsResultsPage;
import com.almundo.browser.automation.utils.Constants.FlightType;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

/**
 * Created by gabrielcespedes on 05/12/16.
 */
public class FlightsDataTrip extends BasePage{

    public FlightsDataTrip(WebDriver driver) {
        super(driver);
    }

    //############################################### Locators ##############################################

    @FindBy(name = "class-flights")
    public WebElement flightTypeDdl;

    @FindBy(css = "#main-content div:nth-child(1) > label > input")
    private WebElement roundTripRbn;

    @FindBy(css = "#main-content div:nth-child(2) > label > input")
    private WebElement oneWayRbn;

    @FindBy(css = "#main-content div:nth-child(3) > label > input")
    private WebElement multiDestinationRbn;

    @FindBy(id = "origin-flights")
    public WebElement originFlightsTxt;

    @FindBy(id = "destination-flights")
    public WebElement destinationFlightsTxt;

    @FindBy(id = "departure-flights")
    private WebElement departureFlightsCalendar;

    @FindBy(id = "arrival-flights")
    private WebElement arrivalFlightsCalendar;

    @FindBy(name = "class-flights")
    public WebElement classFlightDdl;

    @FindBy(css = ".row-adults>.sub")
    public WebElement subAdultsBtn;

    @FindBy(css = ".row-adults>.add")
    public WebElement addAdultBtn;

    @FindBy(css = ".row-adults>.count")
    public WebElement adultsCount;

    @FindBy(css = ".row-youngers>.sub")
    public WebElement subChildBtn;

    @FindBy(css = ".row-youngers>.add")
    public WebElement addChildBtn;

    @FindBy(css = ".row-youngers>.count")
    public WebElement youngersCount;

    @FindBy(css = "am-autocomplete[data-input-id='origin-flights'] .legend")
    public WebElement originMessage;

    @FindBy(css = "am-autocomplete[data-input-id='destination-flights'] .legend")
    public WebElement destinationMessage;

    @FindBy(name = "add-hotel")
    public WebElement addHotelCbx;

    //MULTIDESTINATION
    @FindBy(css = ".add-leg .txt")
    public WebElement addLegLnk;

    @FindBy(css = ".remove-leg .txt")
    public WebElement removeLegLnk;

    @FindBy(css = ".leg-row")
    public WebElement flightLegRow;

    @FindBy(id = "departure-flights-0")
    private WebElement departureFlights0Calendar;

    @FindBy(id = "departure-flights-1")
    private WebElement departureFlights1Calendar;


    /********************************* Getters ********************************/

    public WebElement getDepartureFlightsCalendar() {
        return departureFlightsCalendar;
    }

    public WebElement getArrivalFlightsCalendar(){
        return arrivalFlightsCalendar;
    }

    public WebElement getDepartureFlights2Calendar(){
        return departureFlights0Calendar;
    }

    public WebElement getDepartureFlights3Calendar(){
        return departureFlights1Calendar;
    }



    //############################################### Actions ###############################################

    public void clickAddLegLnk() {
        PageUtils.waitElementForClickable(driver, addLegLnk, 5, "Agregar vuelo link");
        logger.info("Adding flight leg");
        addLegLnk.click();
    }

    public void clickRemoveLegLnk() {
        PageUtils.waitElementForClickable(driver, removeLegLnk, 5, "Eliminar vuelo link");
        logger.info("Removing flight leg");
        removeLegLnk.click();
    }

    public void clickAddHotelCbk() {
        PageUtils.waitElementForClickable(driver, addHotelCbx, 5, "Agregar hotel checkbox");
        logger.info("Clicking on: [Agregar Hotel Checkbox]");
        addHotelCbx.click();
    }

    public FlightsResultsPage clickBuscarBtn() {
        PageUtils.scrollToElement(driver, buscarBtn);
        logger.info("Clicking on button: [Buscar]" );
        buscarBtn.click();
        return initFlightsResultsPage();
    }

    public List<String> getFlightTypeList() {
        List<String> stringList = new ArrayList<>();
        List<WebElement> elementList = flightTypeDdl.findElements(By.cssSelector("option"));

        for (WebElement result : elementList) {
            String newResult = result.getText();
            stringList.add(newResult);
        }
        return stringList;
    }

    public List<String> getFlightClassList() {
        List<String> stringList = new ArrayList<>();
        List<WebElement> elementList = classFlightDdl.findElements(By.cssSelector("option"));

        for (WebElement result : elementList) {
            String newResult = result.getText();
            stringList.add(newResult);
        }
        return stringList;
    }

    public List<String> getChildRangeList(int index) {

        WebElement childRangeDdl = driver.findElement(By.id("age-" + index));

        List<String> stringList = new ArrayList<>();
        List<WebElement> elementList = childRangeDdl.findElements(By.cssSelector("option"));

        for (WebElement result : elementList) {
            String newResult = result.getText();
            stringList.add(newResult);
        }
        return stringList;
    }

    public List<WebElement> getLegList() {
        List<WebElement> elementList = driver.findElements(By.cssSelector(".leg-row"));
        return elementList;
    }

    public FlightsDataTrip selectFlightType(FlightType flightType) {
        //PageUtils.waitElementForVisibility(driver, flightTypeDdl, 10, "Flight type drop down");
        waitImplicitly(1500);
        logger.info("Selecting Flight Type: [" + flightType + "]");
        try {
            Select flightTypeSelect = new Select(flightTypeDdl);
            flightTypeSelect.selectByVisibleText(flightType.toString());
        }
        catch(NoSuchElementException ouch){
            logger.info("We Have Radio Buttons Here! :) ");
            switch (flightType.toString()){
                case "Solo ida": oneWayRbn.click();
                    break;
                case "Ida y vuelta": roundTripRbn.click();
                    break;
                case "Varias ciudades": multiDestinationRbn.click();
                    break;
            }
        }
        return this;
    }

    public FlightsDataTrip setOrigin(String origin, String originFull) {
        PageUtils.waitElementForVisibility(driver, originFlightsTxt, 10, "Origin text field");
        logger.info("Entering Flight Origin: [" + originFull + "]");
        originFlightsTxt.clear();
        originFlightsTxt.sendKeys(origin);
        selectAutoCompleteOption(originFull);
        return this;
    }

    public FlightsDataTrip setOrigin(String origin) {
        PageUtils.waitElementForVisibility(driver, originFlightsTxt, 10, "Origin text field");
        logger.info("Entering Flight Origin: [" + origin + "]");
        originFlightsTxt.clear();
        originFlightsTxt.sendKeys(origin);
        waitImplicitly(3000);
        return this;
    }

    public FlightsDataTrip setMultiDestFlight(String nameAuto, String nameFull, String id) {
        WebElement originFld = driver.findElement(By.id(id));
        PageUtils.waitElementForVisibility(driver, originFld, 10, "Text field");
        logger.info("Entering Flight: [" + nameFull + "]");
        originFld.clear();
        originFld.sendKeys(nameAuto);
        selectAutoCompleteOption(nameFull);
        return this;
    }

    public FlightsDataTrip setDestination(String destination, String destinationFull) {
        PageUtils.waitElementForVisibility(driver, destinationFlightsTxt, 10, "Destination text field");
        logger.info("Entering Flight Destination: [" + destinationFull + "]");
        destinationFlightsTxt.clear();
        destinationFlightsTxt.sendKeys(destination);
        selectAutoCompleteOption(destinationFull);
        return this;
    }

    public FlightsDataTrip selectPassenger(int adults, int childs) {
        waitImplicitly(1000);
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

        logger.info("Total Adults: [" + adults + "]");
        logger.info("Total Childs: [" + childs + "]");
        return this;
    }

    public FlightsDataTrip selectChildAgeRange(String ageRange, int childs) {
        if (childs>0){
            for(int i=0; i<childs; i++) {
                Select ageSelection = new Select(driver.findElement(By.id("age-" + i)));
                ageSelection.selectByVisibleText(ageRange);
            }
        }
        listoBtn.click();
        return this;
    }

    public FlightsDataTrip selectClass(String flightClass) {
        Select claseVueloDdl = new Select(classFlightDdl);
        logger.info("Selecting Flight Class: [" + flightClass + "]");
        claseVueloDdl.selectByVisibleText(flightClass);
        return this;
    }


}
