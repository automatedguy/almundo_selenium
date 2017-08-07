package com.almundo.browser.automation.pages.CheckOutPageV3;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 07/08/17.
 */
public class BreakDownSectionV3 extends CheckOutPageV3 {

    public BreakDownSectionV3(WebDriver driver) {
        super(driver);
    }

    /**************************** Locators **********************************/

    @FindBy(css = "#product-resume span.price__amount")
    public WebElement finalPrice;

    /**************************** Actions **********************************/

    public int getFinalPrice(){
        logger.info("Getting Total Final Price.");
        return Integer.parseInt(finalPrice.getText().replace(".","").replaceAll("\\s",""));
    }
}
