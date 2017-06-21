package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.pages.AlmundoTrips.ActivityFeed;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leandro.efron on 5/12/2016.
 */
public class HeaderSection extends BasePage {

    public HeaderSection(WebDriver driver) {
        super(driver);
    }

    //############################################### Locators ##############################################



    @FindBy(css = "#account-header > am-account-logged")
    public WebElement myAccountMenuLnk;

    @FindBy(css = ".my-trips__toggle>span")
    public WebElement myTripsLnk;

    @FindBy(css = "#account-header .header-link")
    public WebElement textLoggedIntLnk;

    @FindBy(css = "#account-header .am-account-logged-login-desk")
    public WebElement textLoggedOutLnk;

    String menuLocatorStr = "#account-header > am-account-logged > div > ul > li";

    //############################################### Actions ###############################################

    public LoginPopUp clickMyAccountMenuLnk() {
        PageUtils.waitElementForVisibility(driver, myAccountMenuLnk, 20, "My account menu link");
        logger.info("Clicking on My Account menu link");
        myAccountMenuLnk.click();
        return initLoginPopUp();
    }

    public List<String> getMyAccountMenuList() {
        List<String> stringList = new ArrayList<>();
        List<WebElement> ElementList = driver.findElements(By.cssSelector(menuLocatorStr));

        for (WebElement result : ElementList) {
            String newResult = result.getText();
            stringList.add(newResult);
        }
        return stringList;
    }

    public void clickMyAccountMenuOption(String option) {
        List<WebElement> results = driver.findElements(By.cssSelector(menuLocatorStr));

        for (WebElement result : results) {
            if (result.getText().equals(option)) {
                logger.info("Clicking on [" + option + "] option");
                result.click();
                break;
            }
        }
    }

    public ActivityFeed clickMyTripsLnk(){
        PageUtils.waitElementForVisibility(driver, myTripsLnk, 10, "[Mis Viajes] Header Link");
        logger.info("Clicking on [Mis Viajes] from header");
        myTripsLnk.click();
        return initActivityFeed();
    }
}
