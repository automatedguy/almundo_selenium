package com.almundo.browser.automation.pages.CheckOutPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.utils.JsonRead;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class CheckOutPage extends TestBaseSetup {

    public CheckOutPage(WebDriver driver) { super.driver = driver; }

    public static JSONObject checkOutPageElements = null;

    public PassengerSection passengerSection() {
        return initPassengerInfoSection();
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

    private static void getCheckOutPageElements()  {
        checkOutPageElements = JsonRead.getJsonDataObject(jsonCountryPropertyObject, "CheckOutPage", "countries_properties.json");
    }

    public CheckOutPage populateCheckOutPage(int numPassengers, JSONArray passengerList, JSONObject creditCardData, JSONObject billingData, JSONObject contactData) throws InterruptedException {

        getCheckOutPageElements();
        populatePassengerSection(numPassengers, passengerList);
        populateCreditCardSection(creditCardData);
        populateBillingSection(billingData);
        populateContactSection(contactData);
        acceptConditions();

        return this;
    }

    private ArrayList<Passenger> createPassenger(int numPassengers){
        ArrayList<Passenger> passengers = new ArrayList<>();
        for (int idNum = 0; idNum < numPassengers; idNum++) {
            passengers.add(new Passenger(idNum));
        }
        return passengers;
    }

    private CheckOutPage populatePassengerSection(int numPassengers, JSONArray passengerList){

        ArrayList<Passenger> passengers = createPassenger(numPassengers);

        PassengerSection passengerSection = initPassengerInfoSection();
        logger.info("------------- Filling Passenger Section -------------");

        int passengerIndex = 0;
        JSONObject passengerInfo;

        for(Passenger passengerId : passengers){

            logger.info("************ Filling Passenger [" + passengerIndex + "] ************");

            passengerInfo = (JSONObject) passengerList.get(passengerIndex);

            passengerSection.setFirstName(passengerId.firstName, passengerInfo.get("first_name").toString());

            passengerSection.setlastName(passengerId.lastName, passengerInfo.get("last_name").toString());

            passengerSection.setDocumentType(passengerId.documentType, passengerInfo.get("documentType").toString());

            if(isElementRequiered(checkOutPageElements, "document_number")){
                passengerSection.setDocumentNumber(passengerId.documentNumber, passengerInfo.get("document_number").toString());
            }

            if(isElementRequiered(checkOutPageElements, "document_emisor")) {
                passengerSection.setDocumentEmisor(passengerId.document_emisor, passengerInfo.get("document_emisor").toString());
            }

            if(isElementRequiered(checkOutPageElements, "document_expiration")) {
                passengerSection.setDocumentExpiration(passengerId.document_expiration, passengerInfo.get("document_expiration").toString());
            }

            //passengerSection.setBirthDay(passengerToPopulate.birthday, passengerInfo.get("birthday").toString());

            //passengerSection.setGender(passengerToPopulate.gender, passengerInfo.get("gender").toString());

            passengerSection.setNationality(passengerId.nationality, passengerInfo.get("nationality").toString());

            passengerIndex = passengerIndex + 1;
        }
        return this;
    }

    private CheckOutPage populateCreditCardSection(JSONObject creditCardData){

        CreditCardSection creditCardSection = initCreditCardSection();
        logger.info("------------- Filling Credit Card Section -------------");

        creditCardSection.selectPaymentQtyOption(0);
        creditCardSection.selectBankOption(creditCardData.get("credit_card_name").toString());

        creditCardSection.setCardHolder(creditCardData.get("card_holder").toString());

        creditCardSection.setCardNumber(creditCardData.get("card_number").toString());

        creditCardSection.setCardExpiration(creditCardData.get("card_expire").toString());

        creditCardSection.setSecurityCode(creditCardData.get("security_code").toString());

        if(isElementRequiered(checkOutPageElements, "documentType")) {
            creditCardSection.selectDocumentType(creditCardData.get("documentType").toString());
        }

        if(isElementRequiered(checkOutPageElements, "document_number_card")) {
            creditCardSection.setDocumentNumber(creditCardData.get("document_number").toString());
        }
        return this;
    }

    private CheckOutPage populateBillingSection(JSONObject billingData) {
        if (isElementRequiered(checkOutPageElements, "BillingInfoSection")) {

            BillingSection billingSection = initBillingSection();
            logger.info("------------- Filling Billing Section -------------");

            if (isElementRequiered(checkOutPageElements, "fiscal_name")) {
                billingSection.setBillingFiscalName("Nombre o Razon Social");
            }

            if (isElementRequiered(checkOutPageElements, "billing_fiscal_type")){
                billingSection.selectBillingFiscalType("Persona juridica");
            }
            if (isElementRequiered(checkOutPageElements, "billing_document_type")){
                billingSection.selectBillingDocumentType("Tarjeta de Identidad");
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

    private CheckOutPage populateContactSection(JSONObject contactData) {
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

    private CheckOutPage acceptConditions(){
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
