package com.almundo.browser.automation.pages.CheckOutPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.utils.JsonRead;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class CheckOutPage extends TestBaseSetup {

    public CheckOutPage(WebDriver driver) { super.driver = driver; }

    public static JSONObject checkOutPageElements = null;

    public PassengerSection passengerSection() {
        return initPassengerInfoSection();
    }

    public PickUpLocationSection pickUpLocationSection() {
        return initPickUpLocationSection();
    }

    public PaymentSection paymentSection() {
        return initPaymentSection();
    }

    public BillingSection billingSection() {
        return initBillingSection();
    }

    public ContactSection contactSection() {
        return initContactInfoSection();
    }

    //############################################### Locators ##############################################

    @FindBy(css = ".button.button--lg.button--secondary")
    public WebElement comprarBtn;

//    @FindBy(id = "assistance_yes")
//    public WebElement assistanceRdb;

    //############################################### Actions ##############################################

    public ConfirmationPage clickComprarBtn(){
        logger.info("Clicking on Comprar Button.");
        comprarBtn.click();
        return initConfirmationPage();
    }

/*    public CheckOutPage selectAssistanceRdb(){
        PageUtils.waitElementForVisibility(driver, By.id("assistance_yes"), 45, "Include Insurance Radio Button.");
        logger.info("Clicking on Insurance.");
        assistanceRdb.click();
        return this;
    }*/

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

    public CheckOutPage populateCheckOutPage(JSONArray passengerList,
                                             JSONObject paymentData,
                                             JSONObject billingData,
                                             JSONObject contactData,
                                             String productCheckOutPage)
    {
        getCheckOutPageElements(productCheckOutPage);
        passengerSection().populatePassengerSection(passengerList);
        pickUpLocationSection().populatePickUpLocationSection();
        paymentSection().selectPaymentOption(paymentData, productCheckOutPage);
        billingSection().populateBillingSection(billingData);
        contactSection().populateContactSection(contactData);
        acceptConditions();
        return this;
    }

    public CheckOutPage populateCheckOutPage(JSONArray passengerList,
                                             JSONObject paymentData,
                                             JSONObject billingData,
                                             JSONObject contactData,
                                             String productCheckOutPage,
                                             boolean includeAssistance)
    {
        getCheckOutPageElements(productCheckOutPage);
//        if(includeAssistance){selectAssistanceRdb();}
        passengerSection().populatePassengerSection(passengerList);
        pickUpLocationSection().populatePickUpLocationSection();
        paymentSection().selectPaymentOption(paymentData, productCheckOutPage);
        billingSection().populateBillingSection(billingData);
        contactSection().populateContactSection(contactData);
        acceptConditions();
        return this;
    }

    private CheckOutPage acceptConditions(){
        FooterSection footerSection = initFooterSection();
        logger.info("---------- Checking Footer Section options ----------");
        footerSection.acceptTermsAndConditions();
        if(isElementRequiered(checkOutPageElements, "accepted")) {
            footerSection.acceptItinerary();
            footerSection.confirmarClick();
        }
        if(isElementRequiered(checkOutPageElements, "agentEmail")) {
            footerSection.setEmailTxt("gabriel.cespedes@almundo.com");
        }
        return this;
    }
}
