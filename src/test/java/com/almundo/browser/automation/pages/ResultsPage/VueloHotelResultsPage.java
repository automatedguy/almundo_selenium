package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
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

    public VueloHotelDetailPage continuarBtnClick() {
        PageUtils.waitElementForVisibility(driver,continuarBtn,30, "Continuar Button");
        continuarBtn.click();
        return initVueloHotelDetailPage();
    }

    public VueloHotelResultsPage elegirBtnClick(int index) {
        String cssSelectorNameElegir = ".button.button--secondary.button--block.button--md";
        PageUtils.waitListContainResults(driver, cssSelectorNameElegir, 0);
        List<WebElement> elegirBtn = driver.findElements(By.cssSelector(cssSelectorNameElegir));
        elegirBtn.get(index).click();
        return this;
    }
}
