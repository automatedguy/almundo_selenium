package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.almundo.browser.automation.utils.PageUtils.closeExpertsPopUp;
import static com.almundo.browser.automation.utils.PageUtils.waitElementForClickable;

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

    @FindBy(css = ".choice.agreggate>span")
    public WebElement payAtDestination;

    //############################################### Actions ###############################################

    public HotelsDetailPage clickVerHabitacionesBtn() {
        PageUtils.waitElementForVisibility(driver, verHabitacionesBtn, 30, "Ver Habitaciones button");
        logger.info("Clicking on Ver Habitaciones button");
        verHabitacionesBtn.click();
        return this;
    }

    public CheckOutPageV3 clickReservarAhoraV3Btn(int index) {
        List<WebElement> detailHotelButtonResults = driver.findElements(By.cssSelector("room-options .button.button--lg.button--secondary"));
        PageUtils.waitElementForClickable(driver, detailHotelButtonResults.get(index), 20, "Reservar Ahora button");
        logger.info("Clicking on Reservar button");
        detailHotelButtonResults.get(index).click();
        return initCheckOutPageV3();
    }

    public HotelsDetailPage clickPayAtDestination(){
        closeExpertsPopUp(driver);
        waitElementForClickable(driver, payAtDestination, 10, "Pago En Destino Tab");
        logger.info("Clicking on [Pago en destino] Tab");
        payAtDestination.click();
        return this;
    }
}
