package com.almundo.browser.automation.pages.AlmundoTrips;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.utils.PageUtils;
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

    @FindBy(css = ".btn.btn-md.ng-scope")
    WebElement addFirstEventBtn;

    @FindBy(css = "trip-itinerary-tab .btn.btn-sm")
    WebElement addEventBtn;

    /***************************** Actions  **********************************/

    public AddEvent clickAddEvent(){
        try{
            PageUtils.waitElementForClickable(driver, addFirstEventBtn, 20, "[Agregar Evento] (First event button)");
            logger.info("Clicking on [Agregar Evento] (First Event.)");
            addFirstEventBtn.click();
        }catch(Exception ouch){
            PageUtils.waitElementForClickable(driver, addEventBtn, 10, "Agregar Evento Button");
            logger.info("Clicking on [Agregar evento] (there are events already for this trip)");
            addEventBtn.click();
        }
        return initTrippersAgregarEvento();
    }
}
