package com.almundo.browser.automation.pages.AlmundoTrips;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 16/06/17.
 */
public class AddEvent extends BasePage {

    public AddEvent(WebDriver iDriver) {
        super(iDriver);
    }

    /***************************** Locators  **********************************/

    @FindBy(css = "event-type-selector div.event-type li:nth-child(3) > div.title-ctn > p")
    public WebElement eventoPersonalizado;

    @FindBy(css = "div.event-type > ul > li:nth-child(1) > div.title-ctn > p")
    public WebElement buscarEnAlmundo;

    /***************************** Actions  **********************************/

    public AddAnotherEvent clickEventoPersonalizado(){
        logger.info("Click on [Evento Personalizado]");
        eventoPersonalizado.click();
        return initTrippersAgregarOtroEvento();
    }

    public SearchInAlmundo clickBuscarEnAlmundo(){
        logger.info("Click on [Buscar en Almundo]");
        buscarEnAlmundo.click();
        return initBuscarEnAlmundo();
    }

}
