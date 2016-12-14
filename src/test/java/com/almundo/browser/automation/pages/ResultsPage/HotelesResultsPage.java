package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.utils.Constants;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by leandro.efron on 13/12/2016.
 */
public class HotelesResultsPage extends TestBaseSetup {

    public HotelesResultsPage(WebDriver iDriver) {
        this.driver = iDriver;
    }

    //############################################### Locators ##############################################


    //############################################### Actions ###############################################

    public HotelesDetailPage clickVerHotelButton (int index) {
        PageUtils.waitListContainResults(driver, ".button.button--lg.button--secondary.button--block.button-detail", 0);

        List<WebElement> verHotelButtonResults = driver.findElements(By.cssSelector(".button.button--lg.button--secondary.button--block.button-detail"));
        verHotelButtonResults.get(index).click();

        return initHotelesDetailPage();
    }

    public boolean vacancy(){
        try {
            PageUtils.waitForNoVacancy(driver, By.cssSelector(".main-loader>am-alert>div>div:nth-of-type(2)"), 5, "[" + Constants.NO_DISPONIBILIDAD_MSG + "] message");
        } catch (Exception ex){
            return true;
        }
        return false;
    }

}
