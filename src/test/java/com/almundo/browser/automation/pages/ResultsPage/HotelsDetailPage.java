package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.almundo.browser.automation.utils.Constants.MEXICO;
import static com.almundo.browser.automation.utils.PageUtils.closeExpertsPopUp;
import static com.almundo.browser.automation.utils.PageUtils.scrollToElement;
import static com.almundo.browser.automation.utils.PageUtils.waitElementForClickable;

/**
 * Created by leandro.efron on 13/12/2016.
 */
public class HotelsDetailPage extends TestBaseSetup {

    private static Boolean assertHotelInfo = true;

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
        waitElementForClickable(driver, verHabitacionesBtn, 30, "Ver Habitaciones button");
        logger.info("Clicking on Ver Habitaciones button");
        scrollToElement(driver, verHabitacionesBtn);
        verHabitacionesBtn.click();
        return this;
    }

    private boolean getCancelationPolicy(){
        List<WebElement> cancelationPolicyList = driver.findElements(By.cssSelector("cancel-policies-information > div > p"));
        String selectedCancelationPolicy = cancelationPolicyList.get(0).getText();
        logger.info("Cancelation Policy: [" + selectedCancelationPolicy + "]");
        if(selectedCancelationPolicy.contains("no tiene reembolso")){
            return false;
        }else{
            return true;
        }
    }

    public CheckOutPageV3 clickReservarAhoraV3Btn(int index) {
        if(!countryPar.equals(MEXICO)){
        closeExpertsPopUp(driver);}
        assertHotelInfo = getCancelationPolicy();
        List<WebElement> detailHotelButtonResults = driver.findElements(By.cssSelector("room-options .button.button--lg.button--secondary"));
        PageUtils.waitElementForClickable(driver, detailHotelButtonResults.get(index), 20, "Reservar Ahora button");
        logger.info("Clicking on Reservar button");
        detailHotelButtonResults.get(index).click();
        return initCheckOutPageV3();
    }

    public static Boolean getAssertHotelInfo() {
        return assertHotelInfo;
    }

    public HotelsDetailPage clickPayAtDestination(){
        closeExpertsPopUp(driver);
        waitElementForClickable(driver, payAtDestination, 10, "Pago En Destino Tab");
        logger.info("Clicking on [Pago en destino] Tab");
        scrollToElement(driver, payAtDestination);
        payAtDestination.click();
        return this;
    }
}
