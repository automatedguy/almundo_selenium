package com.almundo.browser.automation.pages.AlmundoTrips;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by gabrielcespedes on 19/06/17.
 */
public class Home extends BasePage {

    public Home(WebDriver iDriver) {
        super(iDriver);
    }

    /***************************** Locators  **********************************/

    @FindBy(css = ".trip-info > a")
    public List<WebElement> tripsList;

    /***************************** Actions  **********************************/

    public Dashboard selectTripFromList(int index){
        PageUtils.waitListContainResults(driver, ".trip-info > a", 0);
        logger.info("Clicking on: " + "[" + tripsList.get(index).getText() +"]");
        tripsList.get(index).click();
        return initTrippersDashboard();
    }

}
