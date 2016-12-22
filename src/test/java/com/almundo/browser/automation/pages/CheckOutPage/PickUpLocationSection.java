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

    public void selectAgency() {
        String agencyLocator = "adress";
        List<WebElement> agenciesList = driver.findElements(By.id(agencyLocator));
        agenciesList.get(agenciesList.size()-1).click();
    }
}
