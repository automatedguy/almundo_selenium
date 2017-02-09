package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.PromoPage;
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

    public AutosDataTrip autosDataTrip() {
        return initAutosDataTrip();
    }

    public HeaderSection headerSection() { return initHeaderSection(); }

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

    //BANNERS
    @FindBy(css = "ul[name$='Home Principal Buscador Izquierda (Fijo)']")
    public WebElement mainLeftBannerLnk;

    @FindBy(css = "ul[name$='Home Principal Buscador Derecha']")
    public WebElement mainRightBannerLnk;

    @FindBy(css = "ul[name='AR Home Medio Izquierda']")
    public WebElement homeMedioLeftBannerLnk;

    @FindBy(css = "ul[name='AR Home Medio Derecha']")
    public WebElement homeMedioRightBannerLnk;

    //############################################### Actions ###############################################

    public PromoPage clickMainLeftBannerLnk() {
        PageUtils.waitElementForClickable(driver, mainLeftBannerLnk, 15, "Home Main Left Banner");
        logger.info("Clicking on Home Main left Banner");
        mainLeftBannerLnk.click();
        return initPromoPage();
    }

    public PromoPage clickMainRightBannerLnk() {
        PageUtils.waitElementForClickable(driver, mainRightBannerLnk, 15, "Home Main Right Banner");
        logger.info("Clicking on Home Main right Banner");
        mainRightBannerLnk.click();
        return initPromoPage();
    }

    public PromoPage clickHomeMedioLeftBannerLnk() {
        PageUtils.waitElementForClickable(driver, homeMedioLeftBannerLnk, 15, "Home Medio Left Banner");
        PageUtils.scrollToElement(driver, homeMedioLeftBannerLnk);
        logger.info("Clicking on Home Medio left Banner");
        homeMedioLeftBannerLnk.click();
        return initPromoPage();
    }

    public PromoPage clickHomeMedioRightBannerLnk() {
        PageUtils.waitElementForClickable(driver, homeMedioRightBannerLnk, 15, "Home Medio Right Banner");
        PageUtils.scrollToElement(driver, homeMedioRightBannerLnk);
        logger.info("Clicking on Home Medio right Banner");
        homeMedioRightBannerLnk.click();
        return initPromoPage();
    }

    public BasePage selectDateFromCalendar(WebElement calendar, int daysAhead) {
        calendar.click();
        PageUtils.waitImplicitly(2000);
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
