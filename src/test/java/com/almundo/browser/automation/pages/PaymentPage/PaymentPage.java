package com.almundo.browser.automation.pages.PaymentPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.utils.JsonRead;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class PaymentPage extends TestBaseSetup {

    public PaymentPage(WebDriver driver) { super.driver = driver; }

    public JSONObject paymentPageElements = null;
    public static JSONObject billingsList = null;
    public static JSONObject contactsList = null;
    public static JSONObject creditCardList = null;

    private void getPaymentPageElements()  {
        paymentPageElements = JsonRead.getJsonDataObject(jsonCountryPropertyObject, "PaymentPage", "countries_properties.json");
    }

    public static JSONObject getBillingListObject()  {
        billingsList = JsonRead.getJsonDataObject(jsonDataObject, "billings", countryPar.toLowerCase() + "_data.json");
        return billingsList;
    }

    public static JSONObject getContactsListObject()  {
        contactsList = JsonRead.getJsonDataObject(jsonDataObject, "contacts", countryPar.toLowerCase() + "_data.json");
        return contactsList;
    }

    public static JSONObject getCreditCardListObject()  {
        creditCardList = JsonRead.getJsonDataObject(jsonDataObject, "creditcard", countryPar.toLowerCase() + "_data.json");
        return creditCardList;
    }

    public PaymentPage populatePaymentPage(JSONObject billingData, JSONObject contactData, JSONObject creditCardData,int numPassengers) throws InterruptedException {

        getPaymentPageElements();
        // AQ-41
        populatePassengers(numPassengers);

        // AQ-42
        populateCreditCardSection(creditCardData);

        // AQ-43
        populateBillingSection(billingData);

        populateContactSection(contactData);

        // AQ-44
        checkConditions();

        return this;
    }


    private ArrayList<Passenger> createPassenger(int numPassengers){
        ArrayList<Passenger> passengers = new ArrayList<>();
        for (int idNum = 0; idNum < numPassengers; idNum++) {
            passengers.add(new Passenger(idNum));
        }
        return passengers;
    }

    private boolean isElementRequiered(JSONObject JSONElementsRead, String element){

        boolean isRequiered = false;
        //logger.info("Checking whether Billing Information will be requiered or not...");

        try {
            isRequiered = Boolean.parseBoolean(JSONElementsRead.get(element).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(isRequiered){
            logger.info(element + " information is requiered.");
        }
        else{
            logger.info(element + " information is not requiered.");

        }
        return isRequiered;
    }

    private PaymentPage populatePassengers(int numPassengers){

        ArrayList<Passenger> passengers = createPassenger(numPassengers);

        PassengerInfoSection passengerInfoSection = initPassengerInfoSection();
        logger.info("------------- Filling Passenger Section -------------");

        for(Passenger passengerToPopulate : passengers){

            passengerInfoSection.setFirstName(passengerToPopulate.firstName);

            passengerInfoSection.setlastName(passengerToPopulate.lastName);

            //passengerInfoSection.setDocumentType(passengerToPopulate.documentType, "Pasaporte");

            if(isElementRequiered(paymentPageElements, "document_number")){
                passengerInfoSection.setDocumentNumber(passengerToPopulate.documentNumber);
            }

            if(isElementRequiered(paymentPageElements, "document_emisor")) {
                passengerInfoSection.setDocumentEmisor(passengerToPopulate.document_emisor);
            }

            if(isElementRequiered(paymentPageElements, "document_expiration")) {
                passengerInfoSection.setDocumentExpiration(passengerToPopulate.document_expiration);
            }

            //passengerInfoSection.setBirthDay(passengerToPopulate.birthday, String.valueOf(numPassengers));

            //passengerInfoSection.setGender(passengerToPopulate.gender);

            passengerInfoSection.setNationality(passengerToPopulate.nationality);
        }
        return this;
    }

    private PaymentPage populateCreditCardSection(JSONObject creditCardData){

        CreditCardSection creditCardSection = initCreditCardSection();
        logger.info("------------- Filling Credit Card Section -------------");

        creditCardSection.selectPaymentQtyOption(0);
        creditCardSection.selectBankOption(creditCardData.get("credit_card_name").toString());

        creditCardSection.setCardHolder(creditCardData.get("card_holder").toString());

        creditCardSection.setCardNumber(creditCardData.get("card_number").toString());

        creditCardSection.setCardExpiration(creditCardData.get("card_expire").toString());

        creditCardSection.setSecurityCode(creditCardData.get("security_code").toString());

        if(isElementRequiered(paymentPageElements, "documentType")) {
            creditCardSection.selectDocumentType(creditCardData.get("documentType").toString());
        }

        if(isElementRequiered(paymentPageElements, "document_number_card")) {
            creditCardSection.setDocumentNumber(creditCardData.get("document_number").toString());
        }
        return this;
    }

    private PaymentPage populateBillingSection(JSONObject billingData) {
        if (isElementRequiered(paymentPageElements, "BillingInfoSection")) {

            BillingSection billingSection = initBillingSection();
            logger.info("------------- Filling Billing Section -------------");

            if (isElementRequiered(paymentPageElements, "fiscal_name")) {
                billingSection.setBillingFiscalName("Nombre o Razon Social");
            }

            if (isElementRequiered(paymentPageElements, "billing_fiscal_type")){
                billingSection.selectBillingFiscalType("Persona juridica");
            }
            if (isElementRequiered(paymentPageElements, "billing_document_type")){
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

    private PaymentPage populateContactSection(JSONObject contactData) {
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

    private PaymentPage checkConditions(){
        FooterSection footerSection = initFooterSection();
        logger.info("---------- Checking options Footer Section ----------");

        footerSection.acceptTermsAndConditions();
        if(isElementRequiered(paymentPageElements, "accepted")) {
            footerSection.acceptItinerary();
            footerSection.confirmarClick();
        }
        return this;
    }

}
