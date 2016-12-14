package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.pages.ResultsPage.VueloHotelResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by leandro.efron on 6/12/2016.
 */
public class VueloHotelDataTrip extends BasePage {

    public VueloHotelDataTrip(WebDriver driver) {
        super(driver);
    }

    //############################################### Locators ##############################################

    @FindBy(id = "origin-trips")
    public WebElement originTripsTxt;

    @FindBy(id = "destination-trips")
    public WebElement destinationTripsTxt;

    @FindBy(id = "departure-trips")
    public WebElement departureCalendar;

    @FindBy(id = "arrival-trips")
    public WebElement arrivalCalendar;

    @FindBy(css = ".ui-datepicker-month")
    public WebElement monthLbl;

    @FindBy(css = ".ui-datepicker-year")
    public WebElement yearLbl;

    @FindBy(css = ".ui-icon.ui-icon-circle-triangle-e")
    public WebElement nextCalBtn;

    @FindBy(css = ".search__input")
    public WebElement personasTxt;

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

    @FindBy(css = ".button.button--sm")
    public WebElement listoBtn;

    //############################################### Actions ###############################################

    public VueloHotelDataTrip setOrigin(String origin, String originFull) {
        PageUtils.waitElementForVisibility(driver, originTripsTxt, 10, "Origin text field");
        logger.info("Entering Origin: [" + origin + "]");
        originTripsTxt.clear();
        originTripsTxt.sendKeys(origin);
        selectAutoCompleteOption(originFull);
        return this;
    }

    public VueloHotelDataTrip setDestination(String destination, String destinationFull) {
        PageUtils.waitElementForVisibility(driver, destinationTripsTxt, 10, "Destination text field");
        logger.info("Entering Destination: [" + destination + "]");
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
        }
        listoBtn.click();

        return adults + childs;
    }

    public VueloHotelResultsPage clickBuscarBtn() {
        buscarBtn.click();
        return initVueloHotelResultsPage();
    }

}
