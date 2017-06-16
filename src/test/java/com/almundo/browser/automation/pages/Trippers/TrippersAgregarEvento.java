package com.almundo.browser.automation.pages.Trippers;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 16/06/17.
 */
public class TrippersAgregarEvento extends BasePage {

    public TrippersAgregarEvento(WebDriver iDriver) {
        super(iDriver);
    }

    /***************************** Locators  **********************************/

    @FindBy(css = "event-type-selector div.event-type li:nth-child(3) > div.title-ctn > p")
    public WebElement eventoPersonalizado;

    /***************************** Actions  **********************************/

    public TrippersAgregarOtroEvento clickEventoPersonalizado(){
        logger.info("Click on [Evento Personalizado]");
        eventoPersonalizado.click();
        return initTrippersAgregarOtroEvento();
    }

}
