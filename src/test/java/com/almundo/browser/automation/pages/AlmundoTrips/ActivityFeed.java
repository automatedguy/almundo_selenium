package com.almundo.browser.automation.pages.AlmundoTrips;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 19/06/17.
 */
public class ActivityFeed extends BasePage {

    public ActivityFeed(WebDriver iDriver) {
        super(iDriver);
    }

    /***************************** Locators  **********************************/

    @FindBy(css = ".my-trips__drop-title>a")
    public WebElement myTripsTittle;

    /***************************** Actions  **********************************/

    public Home clickMyTripsTittle(){
        PageUtils.waitElementForVisibility(driver, myTripsTittle, 10, "[Mis Viajes] Feed Link");
        logger.info("Clicking on [Mis Viajes] link from Activity Feed");
        myTripsTittle.click();
        return initHome();
    }

}
