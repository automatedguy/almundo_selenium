package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by gabrielcespedes on 20/12/16.
 */
public class AutosResultsPage extends TestBaseSetup {

    public AutosResultsPage(WebDriver iDriver) {
        this.driver = iDriver;
    }

    //############################################### Locators ##############################################



    //############################################### Actions ###############################################

    public CheckOutPage clickReservarAhoraBtn() {
        String cssSelectorName  = ".button--block.button--lg";
        PageUtils.waitListContainResults(driver, cssSelectorName, 0);

        List<WebElement> detailCarsButtonResults = driver.findElements(By.cssSelector(cssSelectorName));
        PageUtils.waitElementForClickable(driver, detailCarsButtonResults.get(0), 20, "Reservar Ahora button");

        logger.info("Clicking on Reservar button");
        detailCarsButtonResults.get(0).click();
        return initCheckOutPage();
    }

}