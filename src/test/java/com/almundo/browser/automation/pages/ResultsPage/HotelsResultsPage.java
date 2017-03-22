package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.almundo.browser.automation.utils.Constants.Messages.*;

/**
 * Created by leandro.efron on 13/12/2016.
 */
public class HotelsResultsPage extends TestBaseSetup {

    public HotelsResultsPage(WebDriver iDriver) {
        this.driver = iDriver;
    }

    //############################################### Locators ##############################################

    //############################################### Actions ###############################################

    public HotelsDetailPage clickVerHotelBtn(int index) {
        PageUtils.waitListContainResults(driver, ".button.button--lg.button--secondary.button--block.button-detail", 0);
        List<WebElement> verHotelButtonResults = driver.findElements(By.cssSelector(".button.button--lg.button--secondary.button--block.button-detail"));
        logger.info("Clicking on Ver Hotel button");
        verHotelButtonResults.get(index).click();
        return initHotelsDetailPage();
    }

    public boolean vacancy(){
        try {
            PageUtils.waitForNoVacancy(driver, By.cssSelector(".main-loader>am-alert>div>div:nth-of-type(2)"), 5, "[" + NO_DISPONIBILIDAD_MSG + "] message");
        } catch (Exception ex){
            return true;
        }
        return false;
    }
}
