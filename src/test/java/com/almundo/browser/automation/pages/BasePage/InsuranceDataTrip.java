package com.almundo.browser.automation.pages.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 17/08/17.
 */
public class InsuranceDataTrip extends BasePage  {

    public InsuranceDataTrip(WebDriver driver) { super(driver); }

    /****************** Locators ******************/

    @FindBy(css = "#main-content am-searchbox .search-inputs-assistance div:nth-child(1) select")
    private WebElement tipoDeViajeSelect;

    @FindBy(css = "#destination-options")
    private WebElement destinoTxt;

    @FindBy(css = "#main-content .search-inputs.search-inputs-assistance div:nth-child(5) select")
    private WebElement personasSelect;

    /****************** Actions ******************/

    public InsuranceDataTrip selectTipoDeViaje(String tipoDeViaje){
        logger.info("Selecting [Tipo de viaje]: [" + tipoDeViaje + "]" );
        return this;
    }

    public InsuranceDataTrip enterDestino(String destinoAuto, String destinoFull){
        logger.info("Entering [Destino]: [" + destinoFull + "]" );

        return this;
    }

    public InsuranceDataTrip selectPersonas(String personas){
        logger.info("Selecting [Personas]: [" + personas + "]" );
        return this;
    }
}
