package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPage.PickUpLocationSection;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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

    @FindBy(css = ".main-title")
    public WebElement mainTitleLbl;

    //############################################### Actions ##############################################

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
        PageUtils.waitElementForVisibility(driver, mainTitleLbl, 10, "Main text label");
        getCheckOutPageElements(productCheckOutPage);
        paymentSection().populatePaymentSection(paymentData, ".card-container-1", productCheckOutPage);
        passengerSection().populatePassengerSection(passengerList);
        //TODO: Refactor for Cars (when migrated to checkout V3)
        //pickUpLocationSection().populatePickUpLocationSection();
        billingSection().populateBillingSection(billingData);
        contactSection().populateContactSection(contactData);
        acceptConditions();
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
        paymentSection().populatePaymentSection(paymentData1, ".card-container-1", productCheckOutPage);
        paymentSection().populatePaymentSection(paymentData2, ".card-container-2", productCheckOutPage);
        passengerSection().populatePassengerSection(passengerList);
        //TODO: Refactor for Cars (when migrated to checkout V3)
        //pickUpLocationSection().populatePickUpLocationSection();
        billingSection().populateBillingSection(billingData);
        contactSection().populateContactSection(contactData);
        acceptConditions();
        return this;
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