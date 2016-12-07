package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.utils.PageUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by leandro.efron on 5/12/2016.
 */
public class BasePage extends TestBaseSetup {

    public static Logger logger = Logger.getLogger(PageUtils.class);

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

    @FindBy(css = ".button.button--secondary.button--lg.button-search.button--block.ellipsis.ng-binding")
    public WebElement buscarBtn;

    //############################################### Actions ###############################################

    public BasePage selectDateFromCalendar(WebElement calendar, int daysAhead) {

        calendar.click();
        List<WebElement> availableDates = getAvailableDatesList();
        int totalAvailableDates = availableDates.size();

        if(totalAvailableDates >= daysAhead){
            logger.info("Selecting date: [" + availableDates.get(daysAhead-1).getText() + " " + hotelesDataTrip().monthLbl.getText() + " " + hotelesDataTrip().yearLbl.getText() + "]");

            availableDates.get(daysAhead-1).click();
        }
        else{
            daysAhead = daysAhead - totalAvailableDates;
            hotelesDataTrip().nextCalBtn.click();
            List<WebElement> availableDatesNextCal = getAvailableDatesList();
            logger.info("Selecting date: [" + availableDates.get(daysAhead-1).getText() + " " + hotelesDataTrip().monthLbl.getText() + " " + hotelesDataTrip().yearLbl.getText() + "]");
            availableDatesNextCal.get(daysAhead-1).click();
        }
        return this;
    }

    public List<WebElement> getAvailableDatesList () {
        List<WebElement> results = driver.findElements(By.cssSelector(".ui-datepicker-calendar>tbody>tr>td>a"));
        return  results;
    }

    public BasePage selectFromAutoCompleteSuggestions(By autoComplete) {
        PageUtils.waitForVisibilityOfElementLocated(driver, 10, autoComplete);
        driver.findElement(autoComplete).click();
        return this;
    }


    //TODO: Move to results page object class
    public boolean nothingFound(){
        if(!driver.findElements(By.linkText("Ver listado de sucursales")).isEmpty()){
            logger.warn("No Results found - acercate a nuestras sucursales");
            return true;
        }
        else{
            return false;
        }
    }

    //TODO: Move to results page object class
    public boolean noVacancy(){
        try {
            By noVacancyMsg = By.xpath("//span[contains(.,'Lo sentimos. No encontramos disponibilidad para tu búsqueda')]");
            PageUtils.waitForVisibilityOfElementLocated(driver, 5, noVacancyMsg);

        } catch (TimeoutException timeOut){
            logger.info("There is vacancy.");
            return false;
        }
        return true;
    }


}
