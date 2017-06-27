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

    @FindBy(css = ".trips-modal .icon-close")
    public WebElement closeEventModal;

    @FindBy(css = ".event-type li:nth-child(1)")
    public WebElement buscarEnAlmundo;

    @FindBy(css = ".event-type li:nth-child(2)")
    public WebElement tengoUnaReserva;

    @FindBy(css = ".event-type li:nth-child(3)")
    public WebElement eventoPersonalizado;

    /***************************** Actions  **********************************/

    public SearchInAlmundo clickBuscarEnAlmundo(){
        logger.info("Clicking on [Buscar en Almundo]");
        buscarEnAlmundo.click();
        return initBuscarEnAlmundo();
    }

    public SearchInAlmundo clickTengoUnaReserva(){
        logger.info("Clicking on [Tengo una reserva]");
        buscarEnAlmundo.click();
        return initBuscarEnAlmundo();
    }

    public AddAnotherEvent clickEventoPersonalizado(){
        logger.info("Clicking on [Evento Personalizado]");
        eventoPersonalizado.click();
        return initTrippersAgregarOtroEvento();
    }

}
