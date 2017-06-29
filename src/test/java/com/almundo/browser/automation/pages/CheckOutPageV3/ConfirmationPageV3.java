package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.almundo.browser.automation.utils.Constants.Messages.FELICITACIONES_V3_MSG;
import static com.almundo.browser.automation.utils.Constants.Results.FAILED;


/**
 * Created by leandro.efron on 29/3/2017.
 */
public class ConfirmationPageV3 extends BasePage {

    public ConfirmationPageV3(WebDriver iDriver) {
        super(iDriver);
    }

    //############################################### Locators ##############################################

    @FindBy(css = ".content-ctn .title")
    public WebElement felicitacionesLbl;

    @FindBy(css = ".code")
    public WebElement reservationCode;

    //############################################### Actions ###############################################

    public boolean confirmationOk(){
        if((baseURL.contains("st.almundo") || baseURL.contains("staging.almundo")) && submitReservation) {
            PageUtils.waitElementForVisibility(driver, By.cssSelector(".content-ctn .title"), 70, "Reservation Confirmation");
            if (felicitacionesLbl.getText().equals(FELICITACIONES_V3_MSG.toString())) {
                logger.info("Reservation Confirmed! :) ");
                logger.info("***************************************************");
                logger.info("Your Reservation Code:" + reservationCode.getText());
                logger.info("***************************************************");
                return true;
            } else {
                logger.info("Reservation Failed! :( ");
                setResultSauceLabs(FAILED);
                return false;
            }
        } else {
            logger.info("Condition is not approved to validate confirmation");
            return true;
        }
    }
}