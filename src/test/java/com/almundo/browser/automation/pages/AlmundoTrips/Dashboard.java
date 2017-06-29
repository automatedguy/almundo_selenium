package com.almundo.browser.automation.pages.AlmundoTrips;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.almundo.browser.automation.utils.PageUtils.isElementPresent;

/**
 * Created by gabrielcespedes on 16/06/17.
 */
public class Dashboard extends BasePage {

    public Dashboard(WebDriver iDriver) {
        super(iDriver);
    }

    /***************************** Locators  **********************************/

    @FindBy(css = ".btn.btn-md")
    WebElement addFirstEventBtn;

    @FindBy(css = "trip-itinerary-tab .btn.btn-sm")
    WebElement addEventBtn;

    /***************************** Actions  **********************************/

    public AddEvent clickAddEvent(){
        if(isElementPresent(addFirstEventBtn)) {
            logger.info("Clicking on: [Agregar Evento] button (First Event)");
            addFirstEventBtn.click();
        } else {
            logger.info("Clicking on: [Agregar evento] button (there are events already for this trip)");
            PageUtils.waitElementForClickable(driver, addEventBtn, 8, "Agregar evento button");
            addEventBtn.click();
        }
        return initTrippersAgregarEvento();
    }

}
