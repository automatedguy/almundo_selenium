package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.PaymentPage.PaymentPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by leandro.efron on 13/12/2016.
 */
public class HotelesDetailPage extends TestBaseSetup {

    public HotelesDetailPage(WebDriver iDriver) {
        this.driver = iDriver;
    }

    //############################################### Locators ##############################################





    //############################################### Actions ###############################################

    public HotelesDetailPage clickVerHabitacionesBtn() throws InterruptedException {
        PageUtils.waitElementForVisibility(driver, By.cssSelector(".price-total--ctn"), 30, "Price Total Section");

        List<WebElement> verHabitacionesButtonResults = driver.findElements(By.cssSelector(".button.button--lg.button--secondary"));
        logger.info("Clicking on Ver Habitaciones button");
        verHabitacionesButtonResults.get(0).click();
        return this;
    }

    public PaymentPage clickReservarAhoraBtn() {
        List<WebElement> detailHotelButtonResults = driver.findElements(By.cssSelector(".button.button--lg.button--secondary"));
        PageUtils.waitElementForClickable(driver, detailHotelButtonResults.get(1), 20, "Reservar Ahora button");
        logger.info("Clicking on Reservar button");
        detailHotelButtonResults.get(1).click();

        return initPaymentPage();
    }

}
