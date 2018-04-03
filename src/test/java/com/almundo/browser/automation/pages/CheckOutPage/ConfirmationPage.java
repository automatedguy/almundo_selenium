package com.almundo.browser.automation.pages.CheckOutPage;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.almundo.browser.automation.utils.Constants.Messages.*;

/**
 * Created by gabrielcespedes on 10/01/17.
 */
public class ConfirmationPage extends BasePage {

    public ConfirmationPage(WebDriver iDriver) {
        super(iDriver);
    }

    //############################################### Locators ##############################################

    @FindBy(css = ".alert__text-primary")
    public WebElement felicitacionesLbl;

    @FindBy(css = ".alert__text-secondary")
    public WebElement datosReservaLbl;

    @FindBy(css = ".data__container>div:nth-of-type(1) .text--xxs.color--secondary-dark.text--bold")
    public WebElement reservationCode;

    //############################################### Actions ###############################################

    public boolean confirmationOk(){
        if((baseURL.contains("st.almundo") || baseURL.contains("staging.almundo")) && submitReservation) {
            PageUtils.waitElementForVisibility(driver, By.cssSelector(".alert__text-primary"), 70, "Reservation Confirmation");
            if (felicitacionesLbl.getText().equals(FELICITACIONES_MSG.toString())) {
                logger.info("Reservation Confirmed! :) ");
                logger.info("***************************************************");
                logger.info("Your Reservation Code:" + reservationCode.getText());
                logger.info("***************************************************");
                return true;
            } else {
                logger.info("Reservation Failed! :( ");
                return false;
            }
        } else {
            logger.info("Condition is not approved to validate confirmation");
            return true;
        }
    }
}
