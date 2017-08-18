package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.pages.ResultsPage.AssistanceResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by gabrielcespedes on 17/08/17.
 */
public class AssistanceDataTrip extends BasePage  {

    public AssistanceDataTrip(WebDriver driver) { super(driver); }

    /****************** Locators ******************/

    @FindBy(css = "#main-content am-searchbox .search-inputs-assistance div:nth-child(1) select")
    private WebElement tipoDeViajeDdl;

    @FindBy(css = "#destination-options")
    private WebElement destinoTxt;

    @FindBy(css = "#main-content .search-inputs.search-inputs-assistance div:nth-child(5) select")
    private WebElement personasDdl;

    @FindBy(css = "#departure-assistance")
    public WebElement departureAssistanceCalendar;

    @FindBy(css = "#arrival-assistance")
    public WebElement arrivalAssistanceCalendar;

    /****************** Actions ******************/

    public AssistanceDataTrip selectTipoDeViaje(String tipoDeViaje){
        logger.info("Selecting [Tipo de viaje]: [" + tipoDeViaje + "]" );
        Select tipoDeViajeSelect = new Select(tipoDeViajeDdl);
        tipoDeViajeSelect.selectByVisibleText(tipoDeViaje);
        return this;
    }

    public AssistanceDataTrip enterDestino(String destinoAuto, String destinoFull){
        logger.info("Entering [Destino]: [" + destinoFull + "]" );
        PageUtils.waitElementForVisibility(driver, destinoTxt, 10, "Destination text field");
        logger.info("Entering Flight Destination: [" + destinoFull + "]");
        destinoTxt.clear();
        destinoTxt.sendKeys(destinoAuto);
        selectAutoCompleteOption(destinoFull);
        return this;
    }

    public AssistanceDataTrip selectPersonas(int personas){
        logger.info("Selecting [Personas]: [" + personas + "]" );
        Select personasSelect = new Select(personasDdl);
        personasSelect.selectByVisibleText(String.valueOf(personas));
        return this;
    }

    public AssistanceResultsPage clickBuscarBtn(){
        logger.info("Clicking on button: [Buscar]" );
        buscarBtn.click();
        return initAssistanceResultsPage();
    }
}
