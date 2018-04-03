package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.pages.ResultsPage.CarsResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by gabrielcespedes on 20/12/16.
 */
public class CarsDataTrip extends BasePage {

    public CarsDataTrip(WebDriver iDriver) {
        super(iDriver);
    }

    //############################################### Locators ##############################################

    @FindBy(css = "#origin")
    public WebElement originTxt;

    @FindBy(css = "#destination")
    public WebElement destinationTxt;

    @FindBy(css = "#pickUpDate")
    private WebElement pickUpDateCalendar;

    @FindBy(css = "#dropOffDate")
    private WebElement dropOffDateCalendar;

    @FindBy(css = ".pickUpTime select")
    public WebElement pickUpTimeSelect;

    @FindBy(css = ".dropOffTime select")
    public WebElement dropOffTimeSelect;

    @FindBy(css = ".ageRange select")
    public WebElement ageRangeSelect;

    @FindBy(css = ".destinationCheckbox input")
    private WebElement droppOffInDestiny;

    /*****************************************  ***********************************************/

    public WebElement getPickUpDateCalendar(){return pickUpDateCalendar;}

    public WebElement getDropOffDateCalendar(){return dropOffDateCalendar;}

    //############################################### Actions ###############################################

    public CarsDataTrip setOrigin(String origin, String originFull) {
        PageUtils.waitElementForVisibility(driver, originTxt, 10, "Pick Up Origin text field");
        logger.info("Entering Pick Up Origin: [" + originFull + "]");
        originTxt.clear();
        originTxt.sendKeys(origin);
        selectAutoCompleteOption(originFull);
        return this;
    }

    public CarsDataTrip setDestination(String destination, String destinationFull) {
        clickDropOffInDestiny();
        PageUtils.waitElementForVisibility(driver, destinationTxt, 10, "Drop Off Destination text field");
        logger.info("Entering Drop Off Destination: [" + destinationFull + "]");
        destinationTxt.clear();
        destinationTxt.sendKeys(destination);
        selectAutoCompleteOption(destinationFull);
        return this;
    }

    public CarsDataTrip selectPickUpTime(String pickUpTime) {
        Select pickUpTimeDdl = new Select(pickUpTimeSelect);
        logger.info("Selecting Pick Up Time: [" + pickUpTime + "]");
        pickUpTimeDdl.selectByVisibleText(pickUpTime);
        return this;
    }

    public CarsDataTrip selectDropOffTime(String dropOffTime) {
        Select dropOffTimeDdl = new Select(dropOffTimeSelect);
        logger.info("Selecting Drop Off Time: [" + dropOffTime + "]");
        dropOffTimeDdl.selectByVisibleText(dropOffTime);
        return this;
    }

    public CarsDataTrip selectAgeRange(String ageRange) {
        Select ageRangeDdl = new Select(ageRangeSelect);
        logger.info("Selecting Age Range: [" + ageRange + "]");
        ageRangeDdl.selectByVisibleText(ageRange);
        return this;
    }

    public CarsDataTrip clickDropOffInDestiny(){
        logger.info("Clicking on [Devolverlo en otro destino]");
        droppOffInDestiny.click();
        return this;
    }

    public CarsResultsPage clickBuscarBtn() {
        logger.info("Clicking on Buscar Button");
        clickBuscar();
        return initCarsResultsPage();
    }
}
