package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import org.openqa.selenium.WebDriver;

import static com.almundo.browser.automation.utils.PageUtils.waitWithTryCatch;

public class PackagesResultsPage extends TestBaseSetup {

    public PackagesResultsPage(WebDriver driver) {
        super.driver = driver;
    }

    /********************** Locators **********************/


    /********************** Actions **********************/

    public PackagesDetailPage verPaqueteClick(int option){
        String verPaqueteLocator = "results-page results-list-item:nth-child(" + option +") > div > button > span";
        logger.info("Results URL: " + "[" + driver.getCurrentUrl() + "]");
        logger.info("Clicking on: [Ver paquete] Selecting option N°: [" + option + "]");
        waitWithTryCatch(driver, verPaqueteLocator,"Ver paquete", 5).click();
        return initPackagesDetailPage();
    }

}
