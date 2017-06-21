package com.almundo.browser.automation.pages.AlmundoTrips;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 21/06/17.
 */
public class TripConfirmation extends BasePage {

    public TripConfirmation(WebDriver iDriver) {
        super(iDriver);
    }

    /***************************** Locators  **********************************/

    @FindBy(css = ".form-container .modal-footer .btn.btn-link")
    public WebElement omitirLnk;

    @FindBy(css = ".btn.btn-small")
    public WebElement finalizarBtn;

    /***************************** Actions  **********************************/

    public Dashboard clickOmitirLnk(){
        logger.info("Clicking on: [Omitir] link");
        PageUtils.waitElementForClickable(driver, omitirLnk, 10, "[Omitir] link");
        omitirLnk.click();
        return initTripsDashboard();
    }

}
