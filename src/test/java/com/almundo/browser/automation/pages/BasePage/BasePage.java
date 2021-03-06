package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.PromoPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;
import static com.almundo.browser.automation.utils.PageUtils.waitWithTryCatch;

/**
 * Created by leandro.efron on 5/12/2016.
 */
public class BasePage extends TestBaseSetup {

    public BasePage(WebDriver iDriver) {
        this.driver = iDriver;
    }

    public HotelsDataTrip hotelsDataTrip() {
        return initHotelsDataTrip();
    }

    public FlightsDataTrip flightsDataTrip() {
        return initFlightsDataTrip();
    }

    public TripsDataTrip tripsDataTrip() {
        return initTripsDataTrip();
    }

    public CarsDataTrip carsDataTrip() {
        return initCarsDataTrip();
    }

    public PackagesDataTrip packagesDataTrip(){
        return initPackagesDataTrip();
    }

    public ExcursionsDataTrip excursionsDataTrip(){
        return initExcursionsDataTrip();
    }

    public AssistanceDataTrip assistanceDataTrip() {
        return initAssistanceDataTrip();
    }

    public HeaderSection headerSection() { return initHeaderSection(); }

    //############################################### Locators ##############################################

    //Products elements
    @FindBy(css = ".icon.hotels")
    public WebElement hotelsIcon;

    @FindBy(css = ".icon.flights")
    private WebElement flightsIcon;

    @FindBy(css = ".icon.trips")
    public WebElement tripsIcon;

    @FindBy(css = ".icon.packages")
    private WebElement packagesIcon;

    @FindBy(css = ".icon.disney")
    private WebElement disneyIcon;

    @FindBy(css = ".icon.assistance")
    private WebElement assistanceIcon;

    @FindBy(css = ".icon.cars")
    private WebElement carsIcon;

    @FindBy(css = ".icon.excursions")
    private WebElement excursionsIcon;

    @FindBy(css = ".icon.trains")
    public WebElement trainsIcon;

    //Calendar elements
    @FindBy(css = ".ui-datepicker-group-first")
    public WebElement firstCalendar;

    @FindBy(css = ".ui-datepicker-group-last")
    public WebElement lastCalendar;

    @FindBy(css = ".ui-datepicker")
    public WebElement uniqueCalendar;

    @FindBy(css = ".ui-icon.ui-icon-circle-triangle-e")
    public WebElement nextMonthCalBtn;

    @FindBy(css = ".button.button--sm")
    public WebElement listoBtn;

    @FindBy(css = ".search__input")
    public WebElement personasTxt;

    @FindBy(css = ".button.button--secondary.button--lg.button-search.button--block.ellipsis.ng-binding")
    public WebElement buscarBtn;

    @FindBy(css = "#main-content am-searchbox .form-container div > button")
    public WebElement buscarV3Btn;

    //Banners
    @FindBy(css = ".banners-container .banner-first-container")
    public WebElement mainLeftBannerLnk;

    @FindBy(css = ".banners-container .banner-second-container")
    public WebElement mainRightBannerLnk;

    //@FindBy(name = "AR Home Medio Izquierda")
    @FindBy(css = ".bannersads-container .banner-first-container")
    public WebElement homeMedioLeftBannerLnk;

    @FindBy(css = ".bannersads-container .banner-second-container")
    public WebElement homeMedioRightBannerLnk;

    //############################################### Actions ###############################################

    public FlightsDataTrip clickFlightsBtn() {
        waitImplicitly(2000);
        PageUtils.waitElementForClickable(driver, flightsIcon, 10, "Vuelos button");
        logger.info("Clicking on [Vuelos button]");
        flightsIcon.click();
        return flightsDataTrip();
    }

    public HotelsDataTrip clickHotelsBtn() {
        PageUtils.waitElementForClickable(driver, hotelsIcon, 10, "Hoteles button");
        logger.info("Clicking on [Hoteles button]");
        hotelsIcon.click();
        return initHotelsDataTrip();
    }

    public CarsDataTrip clickCarsBtn() {
        waitImplicitly(2000);
        PageUtils.waitElementForClickable(driver, carsIcon, 10, "Autos button");
        PageUtils.scrollToElement(driver,carsIcon);
        logger.info("Clicking on [Autos button]");
        carsIcon.click();
        return initCarsDataTrip();
    }

    public TripsDataTrip clicksTripsBtn() {
        PageUtils.waitElementForClickable(driver, tripsIcon, 10, "Vuelos+Hotel button");
        logger.info("Clicking on [Vuelos+Hotel button]");
        tripsIcon.click();
        return tripsDataTrip();
    }

    public PackagesDataTrip clickPackagesBtn(){
        PageUtils.waitElementForClickable(driver, packagesIcon, 10, "Paquetes button");
        logger.info("Clicking on [Paquetes button]");
        packagesIcon.click();
        return packagesDataTrip();
    }

    public ExcursionsDataTrip clickExcursionsBtn(){
        PageUtils.waitElementForClickable(driver, excursionsIcon, 10, "Actividades button");
        logger.info("Clicking on [Actividades button]");
        excursionsIcon.click();
        return excursionsDataTrip();
    }

    public AssistanceDataTrip clickAssistanceBtn() {
        PageUtils.waitElementForClickable(driver, assistanceIcon, 10, "Seguros button");
        logger.info("Clicking on [Seguros button]");
        assistanceIcon.click();
        return assistanceDataTrip();
    }

    public PromoPage clickMainLeftBannerLnk() {
        PageUtils.waitElementForClickable(driver, mainLeftBannerLnk, 15, "Home Main Left Banner");
        logger.info("Clicking on [Home Main left Banner]");
        mainLeftBannerLnk.click();
        return initPromoPage();
    }

    public PromoPage clickMainRightBannerLnk() {
        PageUtils.waitElementForClickable(driver, mainRightBannerLnk, 15, "Home Main Right Banner");
        logger.info("Clicking on [Home Main right Banner]");
        mainRightBannerLnk.click();
        return initPromoPage();
    }

    public BasePage clickBuscar(){
        try {
            buscarBtn.click();
        }catch(NoSuchElementException ouch){
            logger.info("Apparently new home here, trying to click buscarV3Btn");
            buscarV3Btn.click();
        }
        return this;
    }

    public PromoPage clickHomeMedioLeftBannerLnk() {
        PageUtils.waitElementForClickable(driver, homeMedioLeftBannerLnk, 15, "Home Medio Left Banner");
        PageUtils.scrollToElement(driver, homeMedioLeftBannerLnk);
        logger.info("Clicking on [Home Medio left Banner]");
        homeMedioLeftBannerLnk.click();
        return initPromoPage();
    }

    public PromoPage clickHomeMedioRightBannerLnk() {
        PageUtils.waitElementForClickable(driver, homeMedioRightBannerLnk, 15, "Home Medio Right Banner");
        PageUtils.scrollToElement(driver, homeMedioRightBannerLnk);
        logger.info("Clicking on [Home Medio right Banner]");
        homeMedioRightBannerLnk.click();
        return initPromoPage();
    }

    public BasePage setDate(WebElement calendar, int daysAhead) {
        PageUtils.waitImplicitly(2000);
        calendar.click();
        PageUtils.waitListContainResults(driver, ".ui-datepicker-calendar a", 0);

        if(PageUtils.isElementPresent(firstCalendar)) {
            int availableDates = getAvailableDatesSize(firstCalendar);

            if (PageUtils.isElementPresent(nextMonthCalBtn)) {
                while(availableDates < daysAhead) {
                    daysAhead = daysAhead - availableDates;
                    nextMonthCalBtn.click();
                    availableDates = getAvailableDatesSize(firstCalendar);
                }
                PageUtils.waitImplicitly(1000);
                List<WebElement> availableDatesList = firstCalendar.findElements(By.cssSelector(".ui-datepicker-calendar a"));
                logger.info("Selecting date: [" + availableDatesList.get(daysAhead-1).getText() + " " + getMonthSelected(firstCalendar) + "]");
                availableDatesList.get(daysAhead-1).click();
            } else {
                if(daysAhead <= availableDates) {
                    List<WebElement> availableDatesList = firstCalendar.findElements(By.cssSelector(".ui-datepicker-calendar a"));
                    logger.info("Selecting date: [" + availableDatesList.get(daysAhead-1).getText() + " " + getMonthSelected(firstCalendar) + "]");
                    availableDatesList.get(daysAhead-1).click();
                } else {
                    daysAhead = daysAhead - availableDates;
                    List<WebElement> availableDatesList = lastCalendar.findElements(By.cssSelector(".ui-datepicker-calendar a"));
                    logger.info("Selecting date: [" + availableDatesList.get(daysAhead-1).getText() + " " + getMonthSelected(lastCalendar) + "]");
                    availableDatesList.get(daysAhead-1).click();
                }
            }
        } else {
            int availableDates = getAvailableDatesSize(uniqueCalendar);

            while(availableDates < daysAhead) {
                daysAhead = daysAhead - availableDates;
                nextMonthCalBtn.click();
                availableDates = getAvailableDatesSize(uniqueCalendar);
            }
            List<WebElement> availableDatesList = uniqueCalendar.findElements(By.cssSelector(".ui-datepicker-calendar a"));
            logger.info("Selecting date: [" + availableDatesList.get(daysAhead-1).getText() + " " + getMonthSelected(uniqueCalendar) + "]");
            availableDatesList.get(daysAhead-1).click();
        }
        PageUtils.waitAttributeContains(driver, "#ui-datepicker-div", "style", "display: none");
        return this;
    }

    public int getAvailableDatesSize(WebElement calendar) {
        List<WebElement> results = calendar.findElements(By.cssSelector(".ui-datepicker-calendar a"));
        return results.size();
    }

    public BasePage selectAutoCompleteOption(String value) {
        String suggestionListLocator = ".ellipsis.ng-binding";
        PageUtils.waitListContainResults(driver, suggestionListLocator, 1);
        List <WebElement> autoCompleteList = driver.findElements(By.cssSelector(suggestionListLocator));

        for (WebElement autoCompleteOption : autoCompleteList) {
            if (autoCompleteOption.getText().contains(value)) {
                autoCompleteOption.click();
                break;
            }
        }
        return this;
    }

    private String getMonthSelected(WebElement calendar) {
        return calendar.findElement(By.cssSelector(".ui-datepicker-title")).getText();
    }
}