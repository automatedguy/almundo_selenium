package com.almundo.browser.automation.pages.AlmundoTrips;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

/**
 * Created by gabrielcespedes on 16/06/17.
 */
public class AddAnotherEvent extends BasePage {

    public AddAnotherEvent(WebDriver iDriver) {
        super(iDriver);
    }

    /***************************** Locators  **********************************/

    @FindBy(css = "#name")
    public WebElement nombreDeEvento;

    @FindBy(css = "#input-")
    public WebElement originTxt;

    @FindBy(css = "#destination")
    public WebElement destinationTxt;

    @FindBy(css = "#description")
    public WebElement descripcion;

    @FindBy(id = "departure")
    public WebElement checkinCalendar;

    @FindBy(id = "arrival")
    public WebElement checkoutCalendar;

    @FindBy(css = "time-select > #startTime")
    public WebElement pickUpTimeSelect;

    @FindBy(css = "time-select > #endTime")
    public WebElement dropOffTimeSelect;

    @FindBy(css = ".modal-footer .btn.btn-primary")
    public WebElement agregarBtn;

    /***************************** Actions  **********************************/

    public AddAnotherEvent setNombreDeEvento(String eventName){
        logger.info("Entering Event Name: [" + eventName + "]");
        nombreDeEvento.sendKeys(eventName);
        return this;
    }

    public AddAnotherEvent setOrigin(String originAuto, String originFull) {
        logger.info("Entering Event Origin: [" + originFull + "]");
        originTxt.clear();
        originTxt.sendKeys(originAuto);
        waitImplicitly(5000);
        selectAutoCompleteOption(originFull);
        return this;
    }

    public AddAnotherEvent setDestination(String destinationAuto, String destinationFull) {
        logger.info("Entering Event Destination: [" + destinationFull + "]");
        destinationTxt.clear();
        destinationTxt.sendKeys(destinationAuto);
        waitImplicitly(5000);
        selectAutoCompleteOption(destinationFull);
        return this;
    }

    public AddAnotherEvent setDescription(String description){
        logger.info("Entering Event Description: [" + description + "]");
        descripcion.sendKeys(description);
        return this;
    }

    public AddAnotherEvent selectPickUpTime(String pickUpTime){
        Select pickUpTimeDdl = new Select(pickUpTimeSelect);
        logger.info("Selecting Pick Up Time: [" + pickUpTime + "]");
        pickUpTimeDdl.selectByVisibleText(pickUpTime);
        return this;
    }

    public AddAnotherEvent selectDropOffTime(String dropOffTime){
        Select pickUpTimeDdl = new Select(dropOffTimeSelect);
        logger.info("Selecting Drop Off Time: [" + dropOffTime + "]");
        pickUpTimeDdl.selectByVisibleText(dropOffTime);
        return this;
    }

    public AddAnotherEvent clickAgregarBtn(){
        logger.info("Clicking on [Agregar] button.");
        PageUtils.scrollToElement(driver,agregarBtn);
        agregarBtn.click();
        return this;
    }

}
