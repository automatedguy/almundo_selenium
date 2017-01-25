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

    public PassengerSectionV3 passengerSectionV3() {
        return initPassengerInfoSectionV3();
    }

    public PickUpLocationSection pickUpLocationSection() {
        return initPickUpLocationSection();
    }

    public PaymentSectionV3 paymentSectionV3() {
        return initPaymentSectionV3();
    }

    public BillingSectionV3 billingSectionV3() {
        return initBillingSectionV3();
    }

    public ContactSectionV3 contactSectionV3() {
        return initContactInfoSectionV3();
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
                                               JSONObject paymentData,
                                               JSONObject billingData,
                                               JSONObject contactData,
                                               String productCheckOutPage ) {

        getCheckOutPageElements(productCheckOutPage);
        populatePaymentSection(paymentData, productCheckOutPage);
        populatePassengerSection(numPassengers, passengerList);
        populatePickUpLocationSection();
        populateBillingSection(billingData);
        populateContactSection(contactData);
        acceptConditions();

        return this;
    }

    private CheckOutPageV3 populatePassengerSection(int numPassengers, JSONArray passengerList){

        PassengerSectionV3 passengerSection = initPassengerInfoSectionV3();
        logger.info("------------- Filling Passenger Section -------------");

        JSONObject passengerInfo;

        passengerSectionV3().setFirstNameList();
        passengerSectionV3().setLastNameList();
        passengerSectionV3().setDocTypeList();
        passengerSectionV3().setDocNumberList();
        passengerSectionV3().setBirthdayList();
        passengerSectionV3().setGenderList();
        passengerSectionV3().setNationalityList();

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

    private CheckOutPageV3 populatePaymentSection(JSONObject paymentData, String product) {

        PaymentSectionV3 paymentSection = initPaymentSectionV3();
        logger.info("------------- Filling Payment Section -------------");

        //paymentSection.selectPaymentQtyOption(0);
        paymentSection.selectPayment(paymentData.get("payment_qty").toString());

        //paymentSection.selectBankOption(paymentData.get("credit_card_name").toString());
        paymentSection.selectBank(paymentData.get("bank_name").toString());

        paymentSection.selectCreditCard(paymentData.get("credit_card_name").toString());

        paymentSection.setCardNumber(paymentData.get("card_number").toString());

        paymentSection.setCardHolder(paymentData.get("card_holder").toString());

        if(product.contains("Hoteles") || product.contains("Autos") || product.contains("Vuelos") || product.contains("VueloHotel") ) {
            paymentSection.selectMonthCardExpiration(paymentData.get("month_card_expire").toString());
            paymentSection.selectYearCardExpiration(paymentData.get("year_card_expire").toString());
        }else {
            paymentSection.setCardExpiration(paymentData.get("card_expire").toString());

        }

        paymentSection.setSecurityCode(paymentData.get("security_code").toString());

        if(isElementRequiered(checkOutPageElements, "documentType")) {
            paymentSection.selectDocumentType(paymentData.get("documentType").toString());
        }

        if(isElementRequiered(checkOutPageElements, "document_number_card")) {
            paymentSection.setDocumentNumber(paymentData.get("document_number").toString());
        }
        return this;
    }

    private CheckOutPageV3 populateBillingSection(JSONObject billingData) {
        if (isElementRequiered(checkOutPageElements, "BillingInfoSection")) {

            BillingSectionV3 billingSection = initBillingSectionV3();
            logger.info("------------- Filling Billing Section -------------");

            if (isElementRequiered(checkOutPageElements, "fiscal_name")) {
                billingSection.setBillingFiscalNameTxt(billingData.get("fiscal_name").toString());
            }

            if (isElementRequiered(checkOutPageElements, "billing_fiscal_type")){
                billingSection.selectBillingFiscalType(billingData.get("billing_fiscal_type").toString());
            }
            if (isElementRequiered(checkOutPageElements, "billing_document_type")){
                billingSection.selectBillingDocumentType(billingData.get("billing_document_type").toString());
            }

            billingSection.setBillingFiscalDocumentTxt(billingData.get("billing_fiscal_document").toString());
            billingSection.setBillingAddressTxt(billingData.get("billing_address").toString());
            billingSection.setAddressNumberTxt(billingData.get("address_number").toString());
            billingSection.setAddressFloorTxt(billingData.get("address_floor").toString());
            billingSection.setAddressDepartmentTxt(billingData.get("address_department").toString());
            billingSection.setAddressPostalCodeTxt(billingData.get("address_postal_code").toString());
            billingSection.setAddressStateDdl(billingData.get("address_state").toString());
            billingSection.setAddressCityTxt(billingData.get("address_city").toString());
        }
        return this;
    }

    private CheckOutPageV3 populateContactSection(JSONObject contactData) {
        ContactSectionV3 contactSection = initContactInfoSectionV3();
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
