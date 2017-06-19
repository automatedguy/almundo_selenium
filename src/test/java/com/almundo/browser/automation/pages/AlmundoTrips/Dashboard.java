package com.almundo.browser.automation.pages.AlmundoTrips;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 16/06/17.
 */
public class Dashboard extends BasePage {

    public Dashboard(WebDriver iDriver) {
        super(iDriver);
    }

    /***************************** Locators  **********************************/

    @FindBy(css = "trip-itinerary-tab > div.tab-ctn.ng-scope > div.side-bar-ctn.ng-scope > div > button")
    WebElement agregarEventoBtn;

    /***************************** Actions  **********************************/

    public AgregarEvento clickAgregarEventoBtn(){
        logger.info("Clicking on [Agregar evento]");
        agregarEventoBtn.click();
        return initTrippersAgregarEvento();
    }
}
