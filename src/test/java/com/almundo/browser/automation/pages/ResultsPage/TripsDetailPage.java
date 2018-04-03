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

import static com.almundo.browser.automation.utils.PageUtils.*;

/**
 * Created by gabrielcespedes on 14/12/16.
 */
public class TripsDetailPage extends TestBaseSetup {

    public TripsDetailPage(WebDriver iDriver) {
        this.driver = iDriver;
    }

    //############################################### Locators ##############################################

    private final String verHabitacionLct = ".button.button--lg.button--secondary";
    @FindBy(css = verHabitacionLct)
    private WebElement verHabitacionBtn;

    //############################################### Actions ##############################################

    public TripsDetailPage clickVerHabitacionBtn() {
        logger.info("Detail URL: " + "[" + driver.getCurrentUrl() + "]");
        waitWithTryCatch(driver, verHabitacionLct, "Ver Habitacion", 10);
        waitImplicitly(3000);
        waitElementForClickable(driver,By.cssSelector(".button.button--lg.button--secondary"), 30, "Ver Habitacion Button");
        logger.info("Clicking on Ver Habitación button");
        verHabitacionBtn.click();
        //TODO: we can try to make this explicit.
        PageUtils.waitImplicitly(1000);
        return this;
    }

    public CheckOutPage clickComprarBtn(int index) {
        String cssSelectorName = ".select-room-button.button.button--md.button--secondary";
        PageUtils.waitListContainResults(driver, cssSelectorName, 0);
        List<WebElement> comprarBtn = driver.findElements(By.cssSelector(cssSelectorName));
        logger.info("Clicking on Comprar button index: [" + index + "]");
        comprarBtn.get(index).click();
        return initCheckOutPage();
    }

    public CheckOutPageV3 clickComprarBtnV3(int index) {
        String cssSelectorName = ".select-room-button.button.button--md.button--secondary";
        PageUtils.waitListContainResults(driver, cssSelectorName, 0);
        List<WebElement> comprarBtn = driver.findElements(By.cssSelector(cssSelectorName));
        scrollToElement(driver,comprarBtn.get(index));
        waitElementForClickable(driver, comprarBtn.get(index), 10, "[Comprar] button clickable");
        logger.info("Clicking on Comprar button index: [" + index + "]");
        comprarBtn.get(index).click();
        return initCheckOutPageV3();
    }

}
