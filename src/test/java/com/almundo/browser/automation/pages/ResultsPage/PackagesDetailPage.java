package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.almundo.browser.automation.utils.PageUtils.*;

public class PackagesDetailPage extends TestBaseSetup {

    public PackagesDetailPage(WebDriver driver) {
        super.driver = driver;
    }

    /********************** Locators **********************/

    private String comprarBtnLocator = "div.pricebox > a";

    private final String comprarModalLct = ".detail-header-modal-price > div > div button";
    @FindBy(css = comprarModalLct)
    private WebElement comprarModalButton;

    /********************** Actions **********************/

    public CheckOutPageV3 comprarClick(){
        logger.info("Details URL: " + "[" + driver.getCurrentUrl() + "]");
        logger.info("Clicking on: [Comprar] button");
        WebElement comprarBtn = waitWithTryCatch(driver, comprarBtnLocator ,"Comprar", 30);
        scrollToElement(driver, comprarBtn);
        waitElementForClickable(driver, comprarBtn, 10, "Comprar Button");
        comprarBtn.click();
/*        try{
            waitWithTryCatch(driver, comprarModalLct, "Comprar Modal" , 7);
            logger.info("The price changed.");
            waitImplicitly(2000);
            comprarModalButton.click();
        }catch(ElementNotFoundException ouch){
            logger.info("Price didn't change.");
        }*/
        return initCheckOutPageV3();
    }

}