package com.almundo.browser.automation.locators.flows;

import com.almundo.browser.automation.base.PageBaseSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by gabrielcespedes on 13/10/16.
 */
public class BaseFlowMap extends PageBaseSetup {


//    HOTELES_ICO(By.cssSelector("span.icon.hotels")),
//    VUELOS_ICO(By.cssSelector("span.icon.flights")),
//    VUELO_HOTEL_ICO(By.cssSelector("span.icon.trips")),
//    INGRESAR(By.cssSelector("span.ng-binding.ng-scope")),
//    CALENDAR_MONTH(By.cssSelector(".ui-datepicker-month")),
//    CALENDAR_YEAR(By.cssSelector(".ui-datepicker-year")),
//    CALENDAR_NEXT_CAL(By.xpath("//span[@class='ui-icon ui-icon-circle-triangle-e']")),
//    AVAILABLE_DATES_CAL(By.cssSelector(".ui-datepicker-calendar>tbody>tr>td>a")),
//    PERSONAS_TXT(By.cssSelector(".search__input")),
//    LISTO_BTN(By.cssSelector(".button.button--sm")),
//    BUSCAR_BTN(By.xpath("//button[contains(.,'Buscar')]"));
//    private By name;
//    BaseFlowMap(By locator) {this.name = locator; }
//    public By getBy() { return name; }


    //############################################### Locators ##############################################



    @FindBy(css = ".ui-datepicker-month")
    public WebElement monthLbl;

    @FindBy(css = ".ui-datepicker-year")
    public WebElement yearLbl;

    @FindBy(css = ".ui-icon.ui-icon-circle-triangle-e")
    public WebElement nextCalBtn;

    @FindBy(css = ".search__input")
    public WebElement personasTxt;

    @FindBy(css = ".button.button--sm")
    public WebElement listoBtn;

    @FindBy(css = ".button")
    public WebElement buscarBtn;


    //############################################### Actions ###############################################

    public List<WebElement> getAvailableDatesList () {
        List<WebElement> results = driver.findElements(By.cssSelector(".ui-datepicker-calendar>tbody>tr>td>a"));
    return  results;
    }


}
