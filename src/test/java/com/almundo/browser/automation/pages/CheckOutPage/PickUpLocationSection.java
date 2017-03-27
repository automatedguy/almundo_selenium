package com.almundo.browser.automation.pages.CheckOutPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by gabrielcespedes on 22/12/16.
 */

public class PickUpLocationSection extends CheckOutPage {

    public PickUpLocationSection(WebDriver driver) {
        super(driver);
    }

    //############################################### Locators ##############################################

    //############################################### Actions ##############################################

    public PickUpLocationSection populatePickUpLocationSection(){
        if(isElementRequiered(checkOutPageElements, "PickUpLocationSection")){
            logger.info("------------- Selecting Car Rental Agency -------------");
            pickUpLocationSection().selectAgency();
        }
        return this;
    }

    private void selectAgency() {
        String agencyLocator = "adress";
        String agencyNameLocator = "div.adress-box";

        List<WebElement> agenciesList = driver.findElements(By.id(agencyLocator));
        List<WebElement> agenciesNameList = driver.findElements(By.cssSelector(agencyNameLocator));

        logger.info("Selecting Agency: " + agenciesNameList.get(agenciesNameList.size()-1).getText());
        agenciesList.get(agenciesList.size()-1).click();
    }
}
