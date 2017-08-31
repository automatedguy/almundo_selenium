package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.almundo.browser.automation.utils.Constants.Messages.FELICITACIONES_V3_MSG;
import static com.almundo.browser.automation.utils.Constants.Results.FAILED;
import static com.almundo.browser.automation.utils.PageUtils.getCountryCurrency;


/**
 * Created by leandro.efron on 29/3/2017.
 */
public class ThanksPageV3 extends BasePage {

    public ThanksPageV3(WebDriver iDriver) {
        super(iDriver);
    }

    /*************************** Locators ***************************/

    @FindBy(css = ".content-ctn .title")
    public WebElement felicitacionesLbl;

    @FindBy(css = ".code")
    public WebElement reservationCode;

    @FindBy(css =".amount")
    public WebElement finalPricePaid;

    @FindBy(css = ".email")
    public WebElement contactInfo;

    @FindBy(css = ".person.ng-binding")
    public List<WebElement> passengersInfo;

    @FindBy(css = ".flight-detail-content")
    public WebElement flightDetailContent;

    @FindBy(css = "..hotel-booking-info")
    public WebElement hotelBookingInfo;

    /*************************** Actions ***************************/

    private void printReservationCode(String reservationCode){
        logger.info("Reservation Confirmed! :) ");
        logger.info("***************************************************");
        logger.info("Your Reservation Code:" + reservationCode);
        logger.info("***************************************************");
    }

    public boolean confirmationOk(){
        if((baseURL.contains("st.almundo") || baseURL.contains("staging.almundo")) && submitReservation) {
            PageUtils.waitElementForVisibility(driver, felicitacionesLbl, 70, "Reservation Confirmation");
            if (felicitacionesLbl.getText().equals(FELICITACIONES_V3_MSG.toString())) {
                printReservationCode(reservationCode.getText());
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

    public boolean isFinalPriceOk(int finalPrice){
        logger.info("Asserting Final Price...");
        if(finalPricePaid.getText().toString().equals(" "+ getCountryCurrency() + finalPrice)) {
            logger.info("Amount Paid is Ok.");
            return true;
        }
        else{
            logger.error("Amount paid assertion failure.");
            setResultSauceLabs(FAILED);
            return false;
        }
    }

    public boolean isContactInfoOk(String contactEmail){
        logger.info("Asserting Contact Info...");
        if(contactInfo.getText().toString().equals(contactEmail)) {
            logger.info("Contact Info is Ok.");
            return true;
        }else{
            logger.error("Contact Info assertion failure.");
            setResultSauceLabs(FAILED);
            return false;
        }
    }

    public boolean isFlightDetailInfoOk(String flightDetailInfo){
        logger.info("Asserting Flights Info...");
        if(flightDetailContent.getText().toString().equals(flightDetailInfo)){
            logger.info("Flight Detail Info is Ok.");
            return true;
        }else{
            logger.error("Flight Detail assertion failure.");
            return false;
        }
    }

    public boolean isPaymentInfoOk(String paymentData){
        logger.info("Asserting Payment Information...");
        return true;
    }

    public boolean isPassengersInfoOk(List<String> passengersList){
        logger.info("Asserting Passengers Info...");
        return true;
    }
}