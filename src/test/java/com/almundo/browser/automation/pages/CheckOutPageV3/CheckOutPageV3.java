package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPage.PickUpLocationSection;
import com.almundo.browser.automation.utils.JsonRead;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;

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

    public PaymentSectionV3 paymentSectionV3() {
        return initPaymentSectionV3();
    }

    public BillingSectionV3 billingSection() {
        return initBillingSectionV3();
    }

    public ContactSectionV3 contactSection() {
        return initContactInfoSectionV3();
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

    public CheckOutPageV3 populateCheckOutPage(JSONArray passengerList, JSONObject paymentData, JSONObject billingData, JSONObject contactData, String productCheckOutPage  ) {
        getCheckOutPageElements(productCheckOutPage);
        populatePaymentSection(paymentData, ".card-container-1", productCheckOutPage);
        passengerSection().populatePassengerSection(passengerList);
        pickUpLocationSection().populatePickUpLocationSection();
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
                                               String productCheckOutPage ) {

        getCheckOutPageElements(productCheckOutPage);
        populatePaymentSection(paymentData1, ".card-container-1", productCheckOutPage);
        populatePaymentSection(paymentData2, ".card-container-2", productCheckOutPage);

        passengerSection().populatePassengerSection(passengerList);
        pickUpLocationSection().populatePickUpLocationSection();
        billingSection().populateBillingSection(billingData);
        contactSection().populateContactSection(contactData);
        acceptConditions();
        return this;
    }

    private CheckOutPageV3 populatePaymentSection(JSONObject paymentData, String container, String product) {

        PaymentSectionV3 paymentSection = initPaymentSectionV3();
        logger.info("------------- Selecting type of Payment "+ container + "-------------");
        paymentSection.selectPayment(paymentData.get("payment_qty").toString(), container);
        paymentSection.selectBank(paymentData.get("bank_name").toString(), container);
        paymentSection.selectCreditCard(paymentData.get("credit_card_name").toString(), container);
        logger.info("------------- Filling Payment Section -------------");
        paymentSection.setCardNumber(paymentData.get("card_number").toString(), container);
        paymentSection.setCardHolder(paymentData.get("card_holder").toString(), container);
        if(product.contains("Hoteles") || product.contains("Autos") || product.contains("Vuelos") || product.contains("VueloHotel") ) {
            paymentSection.selectMonthCardExpiration(paymentData.get("month_card_expire").toString(), container);
            paymentSection.selectYearCardExpiration(paymentData.get("year_card_expire").toString(), container);
        }else {
            paymentSection.setCardExpiration(paymentData.get("card_expire").toString());

        }
        paymentSection.setSecurityCode(paymentData.get("security_code").toString(), container);
        if(isElementRequiered(checkOutPageElements, "documentType")) {
            paymentSection.selectDocumentType(paymentData.get("documentType").toString(), container);
        }
        if(isElementRequiered(checkOutPageElements, "document_number_card")) {
            paymentSection.setDocumentNumber(paymentData.get("document_number").toString(), container);
        }
        return this;
    }

    private CheckOutPageV3 acceptConditions(){
        FooterSectionV3 footerSection = initFooterSectionV3();
        logger.info("---------- Checking options Footer Section ----------");
        footerSection.acceptTermsAndConditions();
        if(isElementRequiered(checkOutPageElements, "accepted")) {
            footerSection.acceptItinerary();
            footerSection.confirmarClick();
        }
        return this;
    }
}