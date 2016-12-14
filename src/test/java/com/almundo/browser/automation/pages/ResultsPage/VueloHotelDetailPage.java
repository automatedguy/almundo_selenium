package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.PaymentPage.PaymentPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by gabrielcespedes on 14/12/16.
 */
public class VueloHotelDetailPage extends TestBaseSetup {

    //############################################### Locators ##############################################

    @FindBy(css = ".button.button--lg.button--secondary")
    private WebElement verHabitacionBtn;

    //############################################### Actions ##############################################

    public VueloHotelDetailPage verHabitacionBtnClick() {
        PageUtils.waitElementForVisibility(driver,verHabitacionBtn,30, "Ver Habitacion Button");
        verHabitacionBtn.click();
        return this;
    }

    public PaymentPage comprarBtnClick(int index) {
        String cssSelectorName = ".select-room-button.button.button--md.button--secondary";
        PageUtils.waitListContainResults(driver, cssSelectorName, 0);
        List<WebElement> comprarBtn = driver.findElements(By.cssSelector(cssSelectorName));
        comprarBtn.get(index).click();
        return initPaymentPage();
    }

}
