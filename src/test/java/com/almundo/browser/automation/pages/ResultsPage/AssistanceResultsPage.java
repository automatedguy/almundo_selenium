package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by gabrielcespedes on 18/08/17.
 */
public class AssistanceResultsPage extends TestBaseSetup {

    public AssistanceResultsPage(WebDriver driver) {
        super.driver = driver;
    }

    /****************** Locators ******************/

    @FindBy(css ="")
    List<WebElement> comprarButtonList;


    /****************** Actions ******************/

    public CheckOutPageV3 clickComprarBtn(){
        return initCheckOutPageV3();
    }

}
