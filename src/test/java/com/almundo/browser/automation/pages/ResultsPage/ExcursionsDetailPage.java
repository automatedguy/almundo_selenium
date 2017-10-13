package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;
import static com.almundo.browser.automation.utils.PageUtils.waitWithTryCatch;

public class ExcursionsDetailPage extends TestBaseSetup {

    public ExcursionsDetailPage(WebDriver driver) {
        super.driver = driver;
    }

    /********************** Locators **********************/

    private final String elegirFechaBtnLct = "#activityDetails .card.collapsed-card-top .price a";
    @FindBy(css = elegirFechaBtnLct)
    private WebElement elegirFechaBtn;

    private final String comprarBtnLct = "#activityDetails .second .pricebox .price a";
    @FindBy(css = comprarBtnLct)
    private WebElement comprarBtn;

    /********************** Actions **********************/

    public void clickElegirFechaBtn(){
        waitWithTryCatch(driver, elegirFechaBtnLct, "Elegir fecha", 10);
        logger.info("Clicking on [Elegir fecha] button");
        elegirFechaBtn.click();
        waitImplicitly(1000);
    }

    public CheckOutPageV3 clickComprarBtn(){
        waitWithTryCatch(driver, comprarBtnLct, "Comprar", 10);
        logger.info("Clicking on [Comprar] button");
        comprarBtn.click();
        return initCheckOutPageV3();
    }
}
