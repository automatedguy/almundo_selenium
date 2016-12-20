package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by leandro.efron on 5/12/2016.
 */
public class BasePage extends TestBaseSetup {

    public BasePage(WebDriver iDriver) {
        this.driver = iDriver;
    }

    public HotelesDataTrip hotelesDataTrip() {
        return initHotelesDataTrip();
    }

    public VuelosDataTrip vuelosDataTrip() {
        return initVuelosDataTrip();
    }

    public VueloHotelDataTrip vueloHotelDataTrip() {
        return initVueloHotelDataTrip();
    }

    //############################################### Locators ##############################################

    @FindBy(css = ".icon.hotels")
    public WebElement hotelesIcon;

    @FindBy(css = ".icon.flights")
    public WebElement vuelosIcon;

    @FindBy(css = ".icon.trips")
    public WebElement vueloHotelIcon;

    @FindBy(css = ".icon.packages")
    public WebElement paqueteslIcon;

    @FindBy(css = ".icon.disney")
    public WebElement disneyIcon;

    @FindBy(css = ".icon.assistance")
    public WebElement segurosIcon;

    @FindBy(css = ".icon.cars")
    public WebElement autosIcon;

    @FindBy(css = ".icon.excursions")
    public WebElement excursionesIcon;

    @FindBy(css = ".icon.trains")
    public WebElement trenesIcon;

    @FindBy(css = ".ui-datepicker-month")
    public WebElement monthLbl;

    @FindBy(css = ".ui-datepicker-year")
    public WebElement yearLbl;

    @FindBy(css = ".ui-icon.ui-icon-circle-triangle-e")
    public WebElement nextCalBtn;

    @FindBy(css = ".button.button--sm")
    public WebElement listoBtn;

    @FindBy(css = ".search__input")
    public WebElement personasTxt;

    @FindBy(css = ".button.button--secondary.button--lg.button-search.button--block.ellipsis.ng-binding")
    public WebElement buscarBtn;

    //############################################### Actions ###############################################

    public BasePage selectDateFromCalendar(WebElement calendar, int daysAhead) throws InterruptedException {
        calendar.click();
        Thread.sleep(500);
        PageUtils.waitListContainResults(driver, ".ui-datepicker-calendar>tbody>tr>td>a", 0);

        List<WebElement> availableDates = getAvailableDatesList();
        int totalAvailableDates = availableDates.size();

        if(totalAvailableDates >= daysAhead){
            logger.info("Selecting date: [" + availableDates.get(daysAhead-1).getText() + " " + monthLbl.getText() + " " + yearLbl.getText() + "]");
            availableDates.get(daysAhead-1).click();
        }
        else{
            daysAhead = daysAhead - totalAvailableDates;
            hotelesDataTrip().nextCalBtn.click();
            List<WebElement> availableDatesNextCal = getAvailableDatesList();
            logger.info("Selecting date: [" + availableDatesNextCal.get(daysAhead-1).getText() + " " + monthLbl.getText() + " " + yearLbl.getText() + "]");
            availableDatesNextCal.get(daysAhead-1).click();
        }
        return this;
    }

    public List<WebElement> getAvailableDatesList() {
        List<WebElement> results = driver.findElements(By.cssSelector(".ui-datepicker-calendar>tbody>tr>td>a"));
        return  results;
    }

    public BasePage selectAutoCompleteOption(String value) {
        PageUtils.waitListContainResults(driver, ".ellipsis.ng-binding", 1);
        List <WebElement> autoCompleteList = driver.findElements(By.cssSelector(".ellipsis.ng-binding"));

        for (WebElement autoCompleteOption : autoCompleteList) {
            if (autoCompleteOption.getText().equals(value)) {
                autoCompleteOption.click();
                break;
            }
        }
        return this;
    }

}
