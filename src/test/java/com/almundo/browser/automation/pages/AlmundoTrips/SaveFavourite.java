package com.almundo.browser.automation.pages.AlmundoTrips;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by leandro.efron on 29/6/2017.
 */
public class SaveFavourite extends BasePage {

    public SaveFavourite(WebDriver iDriver) {
        super(iDriver);
    }

    //############################################### Locators ##############################################

    @FindBy(css = ".trip-name")
    private List<WebElement> tripsList;

    @FindBy(css = ".btn-lg")
    public WebElement guardarBtn;

    //############################################### Actions ##############################################

    public void selectTripFromList(String tripName){
        PageUtils.waitListContainResults(driver, ".trip-name", 0);

        for(WebElement tripItem : tripsList ){
            if(tripItem.getText().equals(tripName)){
                logger.info("Selecting trip: " + "["+ tripName +"]" + " to add favourite.");
                tripItem.click();
                break;
            }
        }
    }

}
