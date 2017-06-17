package com.almundo.browser.automation.pages.Trippers;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 16/06/17.
 */
public class AgregarEvento extends BasePage {

    public AgregarEvento(WebDriver iDriver) {
        super(iDriver);
    }

    /***************************** Locators  **********************************/

    @FindBy(css = "event-type-selector div.event-type li:nth-child(3) > div.title-ctn > p")
    public WebElement eventoPersonalizado;

    @FindBy(css = "div.event-type > ul > li:nth-child(1) > div.title-ctn > p")
    public WebElement buscarEnAlmundo;

    /***************************** Actions  **********************************/

    public AgregarOtroEvento clickEventoPersonalizado(){
        logger.info("Click on [Evento Personalizado]");
        eventoPersonalizado.click();
        return initTrippersAgregarOtroEvento();
    }

    public BuscarEnAlmundo clickBuscarEnAlmundo(){
        logger.info("Click on [Buscar en Almundo]");
        buscarEnAlmundo.click();
        return initBuscarEnAlmundo();
    }

}
