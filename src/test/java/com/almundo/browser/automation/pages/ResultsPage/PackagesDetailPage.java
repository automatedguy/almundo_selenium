package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.almundo.browser.automation.utils.PageUtils.waitElementForClickable;
import static com.almundo.browser.automation.utils.PageUtils.waitWithTryCatch;

public class PackagesDetailPage extends TestBaseSetup {

    public PackagesDetailPage(WebDriver driver) {
        super.driver = driver;
    }

    /********************** Locators **********************/

    private String comprarBtnLocator = "div.pricebox > a";

    /********************** Actions **********************/

    public CheckOutPageV3 comprarClick(){
        logger.info("Details URL: " + "[" + driver.getCurrentUrl() + "]");
        logger.info("Clicking on: [Comprar] button");
        WebElement comprarBtn = waitWithTryCatch(driver, comprarBtnLocator ,"Comprar", 5);
        waitElementForClickable(driver, comprarBtn, 10, "Comprar Button");
        comprarBtn.click();
        return initCheckOutPageV3();
    }
}
