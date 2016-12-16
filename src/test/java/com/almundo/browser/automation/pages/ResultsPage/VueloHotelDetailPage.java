package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
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

    public VueloHotelDetailPage clickVerHabitacionBtn() throws InterruptedException {
        PageUtils.waitElementForVisibility(driver,verHabitacionBtn,30, "Ver Habitacion Button");
        PageUtils.waitElementForClickable(driver,By.cssSelector(".button.button--lg.button--secondary"), 30, "Ver Habitacion Button");
        logger.info("Clicking on Ver Habitación button");
        verHabitacionBtn.click();
        //TODO: we can try to make this explicit.
        Thread.sleep(1000);
        return this;
    }

    public CheckOutPage clickComprarBtn(int index) {
        String cssSelectorName = ".select-room-button.button.button--md.button--secondary";
        PageUtils.waitListContainResults(driver, cssSelectorName, 0);

        List<WebElement> comprarBtn = driver.findElements(By.cssSelector(cssSelectorName));
        logger.info("Clicking on Comprar button index: " + index);
        comprarBtn.get(index).click();
        return initCheckOutPage();
    }

}
