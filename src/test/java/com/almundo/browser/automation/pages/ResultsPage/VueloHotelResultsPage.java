package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.utils.Constants;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by gabrielcespedes on 14/12/16.
 */
public class VueloHotelResultsPage extends TestBaseSetup {

    public VueloHotelResultsPage(WebDriver driver) {
        super.driver = driver;
    }

    //############################################### Locators ##############################################

    @FindBy(id = "continue-v2")
    private WebElement continuarBtn;


    //############################################### Actions ##############################################

    public VueloHotelDetailPage clickContinuarBtn() {
        PageUtils.waitElementForVisibility(driver,continuarBtn,30, "Continuar Button");
        logger.info("Clicking on Continuar button");
        continuarBtn.click();
        PageUtils.waitImplicitly(2000);
        return initVueloHotelDetailPage();
    }

    public VueloHotelResultsPage clickElegirBtn(int index) {
        String cssSelectorNameElegir = ".button.button--secondary.button--block.button--md";
        PageUtils.waitListContainResults(driver, cssSelectorNameElegir, 0);
        List<WebElement> elegirBtn = driver.findElements(By.cssSelector(cssSelectorNameElegir));
        logger.info("Clicking on Elegir button index: " + index);
        elegirBtn.get(index).click();
        PageUtils.waitImplicitly(2000);
        return this;
    }

    public boolean vacancy(){
        try {
            PageUtils.waitForNoVacancy(driver, By.cssSelector("div.alert__text > p:nth-child(4)"), 5, "[" + Constants.VOLVE_A_INTENTARLO_MSG + "] message");
        } catch (Exception ex){
            return true;
        }
        return false;
    }
}
