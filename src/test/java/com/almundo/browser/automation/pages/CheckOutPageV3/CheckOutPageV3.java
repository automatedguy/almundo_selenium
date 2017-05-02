package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPage.PickUpLocationSection;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class CheckOutPageV3 extends TestBaseSetup {

    public CheckOutPageV3(WebDriver driver) { super.driver = driver; }

    public static JSONObject checkOutPageElements = null;

    public PassengerSectionV3 passengerSection() {
        return initPassengerInfoSectionV3();
    }

    public PickUpLocationSection pickUpLocationSection() {
        return initPickUpLocationSection();
    }

    public PaymentSectionV3 paymentSection() {
        return initPaymentSectionV3();
    }

    public BillingSectionV3 billingSection() {
        return initBillingSectionV3();
    }

    public ContactSectionV3 contactSection() {
        return initContactInfoSectionV3();
    }

    //############################################### Locators ##############################################

    @FindBy(css = ".button-buy")
    public WebElement comprarBtn;

    @FindBy(css = ".main-title")
    public WebElement mainTitleLbl;

    //############################################### Actions ##############################################

    public ConfirmationPageV3 clickComprarBtn(){
        if((baseURL.contains("st.almundo") || baseURL.contains("staging.almundo")) && submitReservation) {
            logger.info("Clicking on Comprar Button");
            comprarBtn.click();
        } else {
            logger.info("Condition is not approved to submit reservation");
        }
        return initConfirmationPageV3();
    }

    public static boolean isElementRequiered(JSONObject JSONElementsRead, String element){
        boolean isRequiered = false;
        try {
            isRequiered = Boolean.parseBoolean(JSONElementsRead.get(element).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isRequiered;
    }

    private static void getCheckOutPageElements(String productCheckOutPage)  {
        checkOutPageElements = JsonRead.getJsonDataObject(jsonCountryPropertyObject, productCheckOutPage, "countries_properties.json");
    }

    public CheckOutPageV3 populateCheckOutPage(JSONArray passengerList,
                                               JSONObject paymentData,
                                               JSONObject billingData,
                                               JSONObject contactData,
                                               String productCheckOutPage)
    {
        getCheckOutPageElements(productCheckOutPage);
        waitForCheckoutLoad();
        paymentSection().populatePaymentSection(paymentData, ".card-container-1");
        passengerSection().populatePassengerSection(passengerList);
        //TODO: Refactor for Cars (when migrated to checkout V3)
        //pickUpLocationSection().populatePickUpLocationSection();
        billingSection().populateBillingSection(billingData);
        contactSection().populateContactSection(contactData);
        acceptConditions();
        return this;
    }

    public CheckOutPageV3 populateCheckOutPageNew(JSONArray passengerList,
                                               String paymentData,
                                               JSONObject billingData,
                                               JSONObject contactData,
                                               String productCheckOutPage)
    {
        getCheckOutPageElements(productCheckOutPage);
        waitForCheckoutLoad();
        logger.warn("Checkout is not populated, due to current changes.");
        /* paymentSection().populatePaymentSectionNew(paymentData, ".card-container-1");
        passengerSection().populatePassengerSection(passengerList);
        //TODO: Refactor for Cars (when migrated to checkout V3)
        //pickUpLocationSection().populatePickUpLocationSection();
        billingSection().populateBillingSection(billingData);
        contactSection().populateContactSection(contactData);
        acceptConditions(); */
        return this;
    }

    public CheckOutPageV3 populateCheckOutPage(JSONArray passengerList,
                                               JSONObject paymentData1,
                                               JSONObject paymentData2,
                                               JSONObject billingData,
                                               JSONObject contactData,
                                               String productCheckOutPage)
    {
        getCheckOutPageElements(productCheckOutPage);
        paymentSection().populatePaymentSection(paymentData1, ".card-container-1");
        paymentSection().populatePaymentSection(paymentData2, ".card-container-2");
        passengerSection().populatePassengerSection(passengerList);
        //TODO: Refactor for Cars (when migrated to checkout V3)
        //pickUpLocationSection().populatePickUpLocationSection();
        billingSection().populateBillingSection(billingData);
        contactSection().populateContactSection(contactData);
        acceptConditions();
        return this;
    }

    public void waitForCheckoutLoad(){
        PageUtils.waitElementForVisibility(driver, By.id("first_name"), 45, "Checkout V3");
        logger.info("Checkout URL: " + "[" + driver.getCurrentUrl() + "]");
    }

    private CheckOutPageV3 acceptConditions(){
        FooterSectionV3 footerSection = initFooterSectionV3();
        logger.info("---------- Checking options Footer Section ----------");
        footerSection.acceptTermsAndConditions();
        if(isElementRequiered(checkOutPageElements, "accepted")) {
            footerSection.acceptItinerary();
            footerSection.clickConfirmarBtn();
        }
        return this;
    }
}