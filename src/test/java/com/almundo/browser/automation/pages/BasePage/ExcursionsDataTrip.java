package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.pages.ResultsPage.ExcursionsResultsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ExcursionsDataTrip extends BasePage {

    public ExcursionsDataTrip(WebDriver driver) {
        super(driver);
    }

    /************************* Locators *************************/

    @FindBy(css = "#destination-excursions")
    private WebElement destinationExcursions;

    /************************* Actions *************************/

    public ExcursionsDataTrip setDestinationExcursions(String destAuto, String destFull) {
        logger.info("Entering Autocomplete search pattern: " + destAuto);
        destinationExcursions.sendKeys(destAuto);
        selectAutoCompleteOption(destFull);
        return this;
    }

    public ExcursionsResultsPage clickBuscar(){
        logger.info("Clicking on [Buscar] button:");
        buscarBtn.click();
        return initExcursionsResultsPage();
    }
}
