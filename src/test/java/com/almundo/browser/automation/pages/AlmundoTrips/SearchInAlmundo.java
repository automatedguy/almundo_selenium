package com.almundo.browser.automation.pages.AlmundoTrips;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.utils.Constants;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by gabrielcespedes on 16/06/17.
 */
public class SearchInAlmundo extends BasePage {

    public SearchInAlmundo(WebDriver iDriver) {
        super(iDriver);
    }

    /***************************** Locators  **********************************/



    @FindBy(css = ".select-product")
    public WebElement selectProductDdl;

    /****** Cars Calendars ******/

    @FindBy(id = "pickUpDate")
    public WebElement checkinCalendarCars;

    @FindBy(id = "dropOffDate")
    public WebElement checkoutCalendarCars;


    /***************************** Actions **********************************/

    public SearchInAlmundo selectProduct(Constants.Products product){
        PageUtils.waitElementForClickable(driver, selectProductDdl, 10, "Products drop down list");
        Select productSelect = new Select(selectProductDdl);
        logger.info("Selecting: " + product);
        productSelect.selectByVisibleText(product.toString());
        return this;
    }


}
