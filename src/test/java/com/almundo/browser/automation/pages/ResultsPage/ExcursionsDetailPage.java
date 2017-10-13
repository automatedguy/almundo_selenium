package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ExcursionsDetailPage extends TestBaseSetup {

    public ExcursionsDetailPage(WebDriver driver) {
        super.driver = driver;
    }

    /********************** Locators **********************/

    @FindBy(css = "#activityDetails .card.collapsed-card-top .price a")
    private WebElement elegirFechaBtn;

    @FindBy(css = "#activityDetails .second .pricebox .price a")
    private WebElement comprarBtn;

    /********************** Actions **********************/

    public void clickElegirFechaBtn(){
        logger.info("Clicking on [Elegir fecha] button");
        elegirFechaBtn.click();
    }

    public CheckOutPageV3 clickComprarBtn(){
        logger.info("Clicking on [Comprar] button");
        comprarBtn.click();
        return initCheckOutPageV3();
    }
}
