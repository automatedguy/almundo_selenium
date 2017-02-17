package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by leandro.efron on 13/12/2016.
 */
public class HotelsDetailPage extends TestBaseSetup {

    public HotelsDetailPage(WebDriver iDriver) {
        this.driver = iDriver;
    }

    //############################################### Locators ##############################################

    @FindBy(id = "more-rooms")
    public WebElement verHabitacionesBtn;

    //############################################### Actions ###############################################

    public HotelsDetailPage clickVerHabitacionesBtn() {
        PageUtils.waitElementForVisibility(driver, verHabitacionesBtn, 30, "Ver Habitaciones button");
        logger.info("Clicking on Ver Habitaciones button");
        verHabitacionesBtn.click();
        return this;
    }

    public CheckOutPage clickReservarAhoraBtn() {
        List<WebElement> detailHotelButtonResults = driver.findElements(By.cssSelector(".button.button--lg.button--secondary"));
        PageUtils.waitElementForClickable(driver, detailHotelButtonResults.get(1), 20, "Reservar Ahora button");
        logger.info("Clicking on Reservar button");
        detailHotelButtonResults.get(1).click();
        return initCheckOutPage();
    }

    public CheckOutPageV3 clickReservarAhoraV3Btn() {
        List<WebElement> detailHotelButtonResults = driver.findElements(By.cssSelector(".button.button--lg.button--secondary"));
        PageUtils.waitElementForClickable(driver, detailHotelButtonResults.get(1), 20, "Reservar Ahora button");
        logger.info("Clicking on Reservar button");
        detailHotelButtonResults.get(1).click();
        return initCheckOutPageV3();
    }
}
