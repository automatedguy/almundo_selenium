package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.almundo.browser.automation.utils.PageUtils.waitWithTryCatch;

public class ExcursionsResultsPage extends TestBaseSetup {

    public ExcursionsResultsPage(WebDriver driver) {
        super.driver = driver;
    }

    /********************** Locators **********************/

    @FindBy(css = "#activityCluster .pricebox a")
    private List<WebElement> verActividadBtnList;

    /********************** Actions **********************/

    public ExcursionsDetailPage clickVerActividadBtn(int index){
        waitWithTryCatch(driver, "#activityCluster .pricebox a", "Ver Actividad", 10);
        logger.info("Clicking on [Ver actividad] N°: [" + index + "]");
        verActividadBtnList.get(index).click();
        return initExcursionsDetailPage();
    }


}
