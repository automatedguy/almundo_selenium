package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.pages.ResultsPage.AutosResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by gabrielcespedes on 20/12/16.
 */
public class AutosDataTrip extends BasePage {

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

    public AutosDataTrip selectPickUpTime(String pickUpTime) {
        Select pickUpTimeDdl = new Select(pickUpTimeSelect);
        logger.info("Selecting Pick Up Time: [" + pickUpTime + "]");
        pickUpTimeDdl.selectByVisibleText(pickUpTime);
        return this;
    }

    public AutosDataTrip selectDropOffTime(String dropOffTime) {
        Select dropOffTimeDdl = new Select(dropOffTimeSelect);
        logger.info("Selecting Drop Off Time: [" + dropOffTime + "]");
        dropOffTimeDdl.selectByVisibleText(dropOffTime);
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

}
