package com.almundo.browser.automation.pages.SummaryPage;

import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.almundo.browser.automation.utils.PageUtils.scrollToElement;

public class SummaryPage extends CheckOutPageV3 {

    public SummaryPage(WebDriver driver) {
        super(driver);
    }

    /******************* Locators *******************/

    private final String beforeButtonLct = "checkout-page .button-before.ng-binding.ng-scope";
    @FindBy(css = beforeButtonLct)
    private WebElement beforeBtn;

    /******************* Actions *******************/

    public void clickBeforeBtn(){
        scrollToElement(driver, beforeBtn);
        logger.info("Clicking on [Before] button (On Summary Page)");
        beforeBtn.click();
    }
}
