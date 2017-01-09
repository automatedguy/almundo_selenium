package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPage.*;
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

    public PassengerSection passengerSection() {
        return initPassengerInfoSection();
    }

    public PickUpLocationSection pickUpLocationSection() {
        return initPickUpLocationSection();
    }

    public CreditCardSection creditCardSection() {
        return initCreditCardSection();
    }

    public BillingSection billingSection() {
        return initBillingSection();
    }

    public ContactSection contactSection() {
        return initContactInfoSection();
    }

    private static boolean isElementRequiered(JSONObject JSONElementsRead, String element){
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

    public CheckOutPageV3 populateCheckOutPage(int numPassengers,
                                               JSONArray passengerList,
                                               JSONObject creditCardData,
                                               JSONObject billingData,
                                               JSONObject contactData,
                                               String productCheckOutPage ) {

        getCheckOutPageElements(productCheckOutPage);
        populatePassengerSection(numPassengers, passengerList);
        populatePickUpLocationSection();
        populateCreditCardSection(creditCardData, productCheckOutPage);
        populateBillingSection(billingData);
        populateContactSection(contactData);
        acceptConditions();

        return this;
    }

    private CheckOutPageV3 populatePassengerSection(int numPassengers, JSONArray passengerList){

        PassengerSectionV3 passengerSection = initPassengerInfoSectionV3();
        logger.info("------------- Filling Passenger Section -------------");

        JSONObject passengerInfo;

        for(int passengerIndex = 0; passengerIndex <= numPassengers-1; passengerIndex++ ){

            logger.info("************ Filling Passenger [" + passengerIndex + "] ************");

            passengerInfo = (JSONObject) passengerList.get(passengerIndex);

            passengerSection.setFirstName(passengerIndex, passengerInfo.get("first_name").toString());

            passengerSection.setlastName(passengerIndex, passengerInfo.get("last_name").toString());

            if(isElementRequiered(checkOutPageElements, "documentType0")) {
                passengerSection.setDocumentType(passengerIndex, passengerInfo.get("documentType").toString());
            }

            if(isElementRequiered(checkOutPageElements, "document_number")){
                passengerSection.setDocumentNumber(passengerIndex, passengerInfo.get("document_number").toString());
            }

            if(isElementRequiered(checkOutPageElements, "document_emisor")) {
                passengerSection.setDocumentEmisor(passengerIndex, passengerInfo.get("document_emisor").toString());
            }

            if(isElementRequiered(checkOutPageElements, "document_expiration")) {
                passengerSection.setDocumentExpiration(passengerIndex, passengerInfo.get("document_expiration").toString());
            }

            if(isElementRequiered(checkOutPageElements, "birthday")) {
                passengerSection.setBirthDay(passengerIndex, passengerInfo.get("birthday").toString());
            }

            if(isElementRequiered(checkOutPageElements, "gender")) {
                passengerSection.setGender(passengerIndex, passengerInfo.get("gender").toString());
            }

            if(isElementRequiered(checkOutPageElements, "nationality")) {
                    passengerSection.setNationality(passengerIndex, passengerInfo.get("nationality").toString());
            }
        }
        return this;
    }

    private CheckOutPageV3 populatePickUpLocationSection(){
        if(isElementRequiered(checkOutPageElements, "PickUpLocationSection")){
            pickUpLocationSection().selectAgency();
        }
        return this;
    }

    private CheckOutPageV3 populateCreditCardSection(JSONObject creditCardData, String product) {

        CreditCardSection creditCardSection = initCreditCardSection();
        logger.info("------------- Filling Credit Card Section -------------");

        creditCardSection.selectPaymentQtyOption(0);
        creditCardSection.selectBankOption(creditCardData.get("credit_card_name").toString());

        creditCardSection.setCardHolder(creditCardData.get("card_holder").toString());

        creditCardSection.setCardNumber(creditCardData.get("card_number").toString());

        if(product.contains("Hoteles") || product.contains("Autos") || product.contains("Vuelos")) {
            creditCardSection.selectMonthCardExpiration(creditCardData.get("month_card_expire").toString());
            creditCardSection.selectYearCardExpiration(creditCardData.get("year_card_expire").toString());
        }else {
            creditCardSection.setCardExpiration(creditCardData.get("card_expire").toString());

        }

        creditCardSection.setSecurityCode(creditCardData.get("security_code").toString());

        if(isElementRequiered(checkOutPageElements, "documentType")) {
            creditCardSection.selectDocumentType(creditCardData.get("documentType").toString());
        }

        if(isElementRequiered(checkOutPageElements, "document_number_card")) {
            creditCardSection.setDocumentNumber(creditCardData.get("document_number").toString());
        }
        return this;
    }

    private CheckOutPageV3 populateBillingSection(JSONObject billingData) {
        if (isElementRequiered(checkOutPageElements, "BillingInfoSection")) {

            BillingSection billingSection = initBillingSection();
            logger.info("------------- Filling Billing Section -------------");

            if (isElementRequiered(checkOutPageElements, "fiscal_name")) {
                billingSection.setBillingFiscalName(billingData.get("fiscal_name").toString());
            }

            if (isElementRequiered(checkOutPageElements, "billing_fiscal_type")){
                billingSection.selectBillingFiscalType(billingData.get("billing_fiscal_type").toString());
            }
            if (isElementRequiered(checkOutPageElements, "billing_document_type")){
                billingSection.selectBillingDocumentType(billingData.get("billing_document_type").toString());
            }

            billingSection.setBillingFiscalDocument(billingData.get("billing_fiscal_document").toString());
            billingSection.setBillingAddress(billingData.get("billing_address").toString());
            billingSection.setAddressNumber(billingData.get("address_number").toString());
            billingSection.setAddressFloor(billingData.get("address_floor").toString());
            billingSection.setAddressDepartment(billingData.get("address_department").toString());
            billingSection.setAddressPostalCode(billingData.get("address_postal_code").toString());
            billingSection.setAddressState(billingData.get("address_state").toString());
            billingSection.setAddressCity(billingData.get("address_city").toString());
        }
        return this;
    }

    private CheckOutPageV3 populateContactSection(JSONObject contactData) {
        ContactSection contactSection = initContactInfoSection();
        logger.info("------------- Filling Contact Section -------------");
        contactSection.setEmail(contactData.get("email").toString());
        contactSection.setRepEmail(contactData.get("rep_email").toString());
        contactSection.selectPhoneType(contactData.get("tel").toString());
        contactSection.setCountryCode(contactData.get("country_code").toString());
        contactSection.setAreaCode(contactData.get("area").toString());
        contactSection.setPhoneNumber(contactData.get("phone_number").toString());

        return this;
    }

    private CheckOutPageV3 acceptConditions(){
        FooterSection footerSection = initFooterSection();
        logger.info("---------- Checking options Footer Section ----------");

        footerSection.acceptTermsAndConditions();
        if(isElementRequiered(checkOutPageElements, "accepted")) {
            footerSection.acceptItinerary();
            footerSection.confirmarClick();
        }
        return this;
    }

}
