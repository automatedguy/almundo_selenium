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

    private void getPaymentPageElements()  {
        paymentPageElements = JsonRead.getJsonDataObject(jsonCountryPropertyObject, "PaymentPage", "countries_properties.json");
    }

    public PaymentPage populatePaymentPage(int numPassengers) throws InterruptedException {

        getPaymentPageElements();
        // AQ-41
        populatePassengers(numPassengers);

        // AQ-42
        populatePaymentInfo();

        // AQ-43
        populateBillingInfo();

        populateContactInfo();

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
        logger.info("---------- Filling Passenger Info Section ----------");

        for(Passenger passengerToPopulate : passengers){

            passengerInfoSection.setFirstName(passengerToPopulate.firstName);

            passengerInfoSection.setlastName(passengerToPopulate.lastName);

            passengerInfoSection.setDocumentType(passengerToPopulate.documentType);

            if(isElementRequiered(paymentPageElements, "document_number")){
                passengerInfoSection.setDocumentNumber(passengerToPopulate.documentNumber);
            }

            if(isElementRequiered(paymentPageElements, "document_emisor")) {
                passengerInfoSection.setDocumentEmisor(passengerToPopulate.document_emisor);
            }

            if(isElementRequiered(paymentPageElements, "document_expiration")) {
                passengerInfoSection.setDocumentExpiration(passengerToPopulate.document_expiration);
            }

            passengerInfoSection.setBirthDay(passengerToPopulate.birthday, String.valueOf(numPassengers));

            passengerInfoSection.setGender(passengerToPopulate.gender);

            passengerInfoSection.setNationality(passengerToPopulate.nationality);
        }
        return this;
    }

    private PaymentPage populatePaymentInfo(){

        PaymentInfoSection paymentInfoSection = initPaymentInfoSection();
        logger.info("---------- Filling Payment Info Section ----------");

        paymentInfoSection.selectPaymentQtyOption(0);
        paymentInfoSection.selectBankOption("American Express");

        paymentInfoSection.setCardHolder("Nombre Apellido");

        paymentInfoSection.setCardNumber("4242424242424242");

        paymentInfoSection.setCardExpiration("07/17");

        paymentInfoSection.setSecurityCode("777");

        if(isElementRequiered(paymentPageElements, "documentType")) {
            paymentInfoSection.selectDocumentType("Pasaporte");
        }

        if(isElementRequiered(paymentPageElements, "document_number_card")) {
            paymentInfoSection.setDocumentNumber("2078709888");
        }
        return this;
    }

    private PaymentPage populateBillingInfo() {
        if (isElementRequiered(paymentPageElements, "BillingInfoSection")) {

            BillingInfoSection billingInfoSection = initBillingInfoSection();
            logger.info("---------- Filling Billing Info Section ----------");

            if (isElementRequiered(paymentPageElements, "fiscal_name")) {
                billingInfoSection.setBillingFiscalName("Nombre o Razon Social");
            }

            if (isElementRequiered(paymentPageElements, "billing_fiscal_type")){
                billingInfoSection.selectBillingFiscalType("Persona juridica");
            }
            if (isElementRequiered(paymentPageElements, "billing_document_type")){
                billingInfoSection.selectBillingDocumentType("Tarjeta de Identidad");
            }

            billingInfoSection.setBillingFiscalDocument("20285494568");
            billingInfoSection.setBillingAddress("Domicilo");
            billingInfoSection.setAddressNumber("7550");
            billingInfoSection.setAddressFloor("10");
            billingInfoSection.setAddressDepartment("A");
            billingInfoSection.setAddressPostalCode("1009");
            billingInfoSection.setAddressState("Buenos Aires");
            billingInfoSection.setAddressCity("CABA");
        }
        return this;
    }

    private PaymentPage populateContactInfo() {

        ContactInfoSection contactInfoSection = initContactInfoSection();
        logger.info("---------- Filling Contact Info Section ----------");

        contactInfoSection.setEmail("testing@almundo.com");

        contactInfoSection.setRepEmail("testing@almundo.com");

        contactInfoSection.selectPhoneType("Teléfono");

        contactInfoSection.setCountryCode("54");

        contactInfoSection.setAreaCode("11");

        contactInfoSection.setPhoneNumber("44448888");

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
