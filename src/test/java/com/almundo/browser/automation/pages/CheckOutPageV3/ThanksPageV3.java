package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.utils.PageUtils;
import com.almundo.browser.automation.utils.ThanksPageAssertInfo;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.almundo.browser.automation.pages.ResultsPage.HotelsDetailPage.getAssertHotelInfo;
import static com.almundo.browser.automation.utils.Constants.Messages.FELICITACIONES_V3_MSG;
import static com.almundo.browser.automation.utils.Constants.Results.FAILED;
import static com.almundo.browser.automation.utils.PageUtils.getCountryCurrency;
import static com.almundo.browser.automation.utils.PageUtils.printStarsSeparator;
import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;


/**
 * Created by leandro.efron on 29/3/2017.
 */
public class ThanksPageV3 extends BasePage {

    public ThanksPageV3(WebDriver iDriver) {
        super(iDriver);
    }

    /*************************** Locators ***************************/

    private boolean assertThanksPageElements = false;

    @FindBy(css = ".content-ctn .title")
    public WebElement felicitacionesLbl;

    @FindBy(css = ".code")
    public WebElement reservationCode;

    @FindBy(css =".amount")
    public WebElement finalPricePaid;

    @FindBy(css = ".email")
    public WebElement contactInfo;

    @FindBy(css = ".person.ng-binding")
    public List<WebElement> passengersNameList;

    @FindBy(css = ".item.ng-binding.ng-scope.ng-binding > strong")
    public List<WebElement> passengerDocumentList;

    @FindBy(css =".icon-arrow-right.ng-scope")
    private WebElement nextPassenger;

    @FindBy(css = ".flight-detail-content")
    public WebElement flightDetailContent;

    @FindBy(css = ".hotel-cluster div.info-container")
    public WebElement hotelDetailContent;

    @FindBy(css = ".car-product-checkout")
    public WebElement carsDetailContent;

    @FindBy(css = "")
    public WebElement tripsDetailContent;

    @FindBy(css = "")
    public WebElement assuranceDetailContent;

    /*************************** Actions ***************************/

    private void printReservationCode(String reservationCode){
        logger.info("Reservation Confirmed!");
        printStarsSeparator();
        logger.info("Reservation Code:" + reservationCode);
        printStarsSeparator();
    }

    private String normalizeItineraryInfo(String itineraryInfo){
        return itineraryInfo.replaceAll("\\n"," ").replaceAll("  ", " ");
    }

    public boolean confirmationOk(){
        if((baseURL.contains("st.almundo") || baseURL.contains("staging.almundo") || baseURL.contains("dv.almundo")) && submitReservation) {
            assertThanksPageElements = true;
            try {
                PageUtils.waitElementForVisibility(driver, felicitacionesLbl, 70, "Reservation Confirmation");
            }catch (TimeoutException ouch){
                logger.info("Waited so long for the [Reservation Confirmation]");
                setResultSauceLabs(FAILED);
            }
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

    public boolean isPaymentInfoOk(String finalPrice){

        String checkoutFinalPrice = getCountryCurrency() + " " + finalPrice;
        if (countryPar.equals("COLOMBIA")) {
            checkoutFinalPrice = getCountryCurrency() + "  " + finalPrice;
        }

        String thanksFinalPrice =  finalPricePaid.getText().toString();

        logger.info("Asserting Final Amount Paid: On Breakdown was:[" + checkoutFinalPrice + "]");
        logger.info("Asserting Final Amount Paid: On Thanks is:[" + thanksFinalPrice + "]");

        if(assertThanksPageElements) {
            if (thanksFinalPrice.equals(checkoutFinalPrice)) {
                logger.info("Amount Paid is Ok.");
                printStarsSeparator();
                return true;
            } else {
                logger.error("Amount paid assertion failure.");
                setResultSauceLabs(FAILED);
                return false;
            }
        } else{
            logger.info("Condition is not approved for Payment validation");
            printStarsSeparator();
            return true;
        }
    }

    public boolean isContactInfoOk(String contactEmail){
        if(assertThanksPageElements) {
            logger.info("Asserting Contact Info [" + contactEmail + "]");
            if (contactInfo.getText().toString().equals(contactEmail)) {
                logger.info("Contact Info is Ok.");
                printStarsSeparator();
                return true;
            } else {
                logger.error("Contact Info assertion failure.");
                setResultSauceLabs(FAILED);
                printStarsSeparator();
                return false;
            }
        }else {
            logger.info("Condition is not approved for Contact validation");
            return true;
        }
    }

    public boolean isFlightDetailInfoOk(String flightDetailInfo){
        if(assertThanksPageElements) {
            logger.info("Asserting Flights Info: [" + flightDetailInfo + "]");
            if (normalizeItineraryInfo(flightDetailContent.getText()).equals(normalizeItineraryInfo(flightDetailInfo))) {
                logger.info("Flight Detail Info is Ok.");
                printStarsSeparator();
                return true;
            } else {
                logger.error("Flight Detail assertion failure.");
                printStarsSeparator();
                return false;
            }
        } else {
            logger.info("Condition is not approved for Flights validation");
            return true;
        }
    }

    public boolean isHotelDetailInfoOk(String hotelDetailInfo){
        if(assertThanksPageElements && getAssertHotelInfo()) {
            logger.info("Asserting Hotel Info: [" + hotelDetailInfo + "]");
            if (normalizeItineraryInfo(hotelDetailContent.getText()).equals(normalizeItineraryInfo(hotelDetailInfo))) {
                logger.info("Hotel Detail Info is Ok.");
                printStarsSeparator();
                return true;
            } else {
                logger.error("Hotel Detail assertion failure.");
                printStarsSeparator();
                return false;
            }
        }else {
            logger.info("Condition is not approved for Hotel validation");
            return true;
        }
    }

    public boolean isCarsDetailInfoOk(String carsDetailInfo){
        if(assertThanksPageElements) {
            logger.info("Asserting Cars Info: [" + carsDetailInfo + "]");
            if (normalizeItineraryInfo(carsDetailContent.getText()).equals(normalizeItineraryInfo(carsDetailInfo))) {
                logger.info("Cars Info is Ok.");
                printStarsSeparator();
                return true;
            } else {
                logger.error("Cars assertion failure.");
                printStarsSeparator();
                return false;
            }
        }else{
            logger.info("Condition is not approved for Cars validation");
            return true;
        }
    }

    public boolean isTripsDetailInfoOk(String tripsDetailInfo){
        if(assertThanksPageElements) {
            logger.info("Asserting Trips Info: [" + tripsDetailInfo + "]");
            if (normalizeItineraryInfo(tripsDetailContent.getText()).equals(normalizeItineraryInfo(tripsDetailInfo))) {
                logger.info("Trips Cars Info is Ok.");
                printStarsSeparator();
                return true;
            } else {
                logger.error("Trips Cars assertion failure.");
                printStarsSeparator();
                return false;
            }
        }else{
            logger.info("Condition is not approved for validation");
            return true;
        }
    }

    public boolean isAssuranceDetailInfoOk(String assuranceDetailInfo){
        if(assertThanksPageElements) {
            logger.info("Asserting Assurance Info: [" + assuranceDetailInfo + "]");
            if (normalizeItineraryInfo(assuranceDetailContent.getText()).equals(normalizeItineraryInfo(assuranceDetailInfo))) {
                logger.info("Hotel Assurance Info is Ok.");
                return true;
            } else {
                logger.error("Hotel Assurance assertion failure.");
                printStarsSeparator();
                return false;
            }
        }else{
            logger.info("Condition is not approved for validation");
            printStarsSeparator();
            return true;
        }
    }

    private ThanksPageV3 clickNextPassenger(){
        logger.info("Clicking on [next passenger arrow (>) button]");
        nextPassenger.click();
        waitImplicitly(2000);
        return this;
    }

    public boolean isPassengersInfoOk(){
        if(assertThanksPageElements) {
            logger.info("Asserting Passengers Info...");
            boolean passengersOk = true;
            int passengerIndex = 0;
            for (ThanksPageAssertInfo.Passenger passenger : thanksPageAssertInfo.passengersList) {
                logger.info("Asserting Passenger N°: " + (passengerIndex + 1));
                logger.info("Full Name: [" + passenger.fullName + "]");
                if (!passenger.fullName.equals(passengersNameList.get(passengerIndex).getText())) {
                    logger.error("Passenger Full Name Assertion failure.");
                    logger.error("The Full Name found was: [" + passengersNameList.get(passengerIndex).getText() + "]");
                    setResultSauceLabs(FAILED);
                    passengersOk = false;
                    break;
                }
                if (method.contains("Flights.")||method.contains("flights")) {
                    logger.info("Document Number: [" + passenger.documentNumber + "]");
                    if (!passenger.documentNumber.equals(passengerDocumentList.get(passengerIndex).getText())) {
                        logger.error("Passenger Document Number Assertion failure.");
                        logger.error("The Document found was: [" + passengerDocumentList.get(passengerIndex).getText() + "]");
                        setResultSauceLabs(FAILED);
                        passengersOk = false;
                        break;
                    }
                }
                if (thanksPageAssertInfo.passengersList.size() > 1) {
                    passengerIndex = passengerIndex + 1;
                    clickNextPassenger();
                }
            }
            if (passengersOk) {
                logger.info("Passengers Info is Ok.");
                printStarsSeparator();
            }
            return passengersOk;
        }else{
            logger.info("Condition is not approved for Passengers validation");
            return true;
        }
    }
}