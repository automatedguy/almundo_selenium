package com.almundo.browser.automation.pages.AlmundoTrips;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.pages.ResultsPage.CarsResultsPage;
import com.almundo.browser.automation.pages.ResultsPage.FlightsResultsPage;
import com.almundo.browser.automation.utils.Constants;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

/**
 * Created by gabrielcespedes on 16/06/17.
 */
public class BuscarEnAlmundo extends BasePage {

    public BuscarEnAlmundo(WebDriver iDriver) {
        super(iDriver);
    }

    /***************************** Locators  **********************************/

    @FindBy(css = "flights-search-box > form > div:nth-child(1) > select")
    public WebElement tipoDeVueloDdl;

    @FindBy(css = "#origin")
    public WebElement originTxt;

/*    @FindBy(id = "destination")
    public WebElement destinationTxt;*/

    /****** Flights Calendars ******/
    @FindBy(id = "departure")
    public WebElement checkinCalendarFlights;

    @FindBy(id = "return")
    public WebElement checkoutCalendarFlights;

    @FindBy(css = ".select-product")
    public WebElement selectProductDdl;


    @FindBy(css = ".flights-search-form .btn.btn-primary.btn-submit")
    public WebElement buscarVuelosBtn;



    @FindBy(css = ".cars-search-form .btn.btn-primary.btn-submit")
    public WebElement buscarAutosBtn;

    /****** Cars Calendars ******/

    @FindBy(id = "pickUpDate")
    public WebElement checkinCalendarCars;

    @FindBy(id = "dropOffDate")
    public WebElement checkoutCalendarCars;


    /***************************** Actions **********************************/

    public BuscarEnAlmundo selectProduct(Constants.Products product){
        PageUtils.waitElementForClickable(driver, selectProductDdl, 10, "Products drop down list");
        Select productSelect = new Select(selectProductDdl);
        logger.info("Selecting: " + product);
        productSelect.selectByVisibleText(product.toString());
        return this;
    }

    public BuscarEnAlmundo selectFlightType(Constants.FlightType flightType){
        logger.info("Selecting Flight Type: " + "["+ flightType + "]");
        Select flightTypeDdl = new Select(tipoDeVueloDdl);
        flightTypeDdl.selectByVisibleText(flightType.toString());
        return this;
    }

    public BuscarEnAlmundo setOrigin(String originAuto, String originFull){
        logger.info("Entering Flight Origin: " + "[" + originFull + "]");
        originTxt.clear();
        originTxt.sendKeys(originAuto);
        waitImplicitly(3000);
        selectAutoCompleteOption(originFull);
        return this;
    }

    public BuscarEnAlmundo setDestination(String destinationAuto, String destinationFull) {
        WebElement destinationTxt = driver.findElement(By.cssSelector("#destination"));
        logger.info("Entering Destination: [" + destinationFull + "]");
        destinationTxt.clear();
        destinationTxt.sendKeys(destinationAuto);
        waitImplicitly(5000);
        selectAutoCompleteOption(destinationFull);
        return this;
    }

    public FlightsResultsPage clickBuscarVuelosBtn(){
        logger.info("Clicking on [Buscar] button (For Flights)");
        waitImplicitly(1000);
        buscarVuelosBtn.click();
        return initFlightsResultsPage();
    }


    public CarsResultsPage clickBuscarAutosBtn(){
        logger.info("Clicking on [Buscar] button (For Cars)");
        waitImplicitly(1000);
        buscarAutosBtn.click();
        return initCarsResultsPage();
    }

}
