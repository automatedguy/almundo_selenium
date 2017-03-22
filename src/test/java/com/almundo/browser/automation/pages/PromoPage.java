package com.almundo.browser.automation.pages;

import com.almundo.browser.automation.base.TestBaseSetup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by leandro.efron on 1/2/2017.
 */
public class PromoPage extends TestBaseSetup {

    public PromoPage(WebDriver iDriver) {
        this.driver = iDriver;
    }

    //############################################### Locators ##############################################

    @FindBy(css = ".brand-logo")
    public WebElement brandLogo;

    @FindBy(id = "mailing")
    public WebElement mailingSection;

    @FindBy(css = "meta[itemprop='url']")
    public WebElement urlItemProperty;

    @FindBy(css = "meta[itemprop='title']")
    public WebElement titleItemProperty;

    @FindBy(css = ".search")
    public WebElement searchBox;

    //############################################### Actions ###############################################
}
