package com.almundo.browser.automation.pages.CheckOutPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class CheckOutPage extends TestBaseSetup {


    @FindBy(css = ".button.button--lg.button--secondary")
    public WebElement comprarBtn;

    @FindBy(id = "assistance_yes")
    public WebElement assistanceRdb;

    public ConfirmationPage clickComprarBtn(){
        logger.info("Clicking on Comprar Button.");
        comprarBtn.click();
        return initConfirmationPage();
    }

    public CheckOutPage selectAssistanceRdb(){
        PageUtils.waitElementForVisibility(driver, By.id("assistance_yes"), 45, "Include Insurance Radio Button.");
        logger.info("Clicking on Insurance.");
        assistanceRdb.click();
        return this;
    }

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

    public CheckOutPage populateCheckOutPage(int numPassengers,
                                             JSONArray passengerList,
                                             JSONObject paymentData,
                                             JSONObject billingData,
                                             JSONObject contactData,
                                             String productCheckOutPage ) {

        getCheckOutPageElements(productCheckOutPage);
        populatePassengerSection(numPassengers, passengerList);
        populatePickUpLocationSection();
        selectPaymentOption(paymentData, productCheckOutPage);
        populateBillingSection(billingData);
        populateContactSection(contactData);
        acceptConditions();

        return this;
    }

    public CheckOutPage populateCheckOutPage(int numPassengers,
                                             JSONArray passengerList,
                                             JSONObject paymentData,
                                             JSONObject billingData,
                                             JSONObject contactData,
                                             String productCheckOutPage, boolean includeAssistance ) {

        getCheckOutPageElements(productCheckOutPage);
        if(includeAssistance){
            selectAssistanceRdb();
        }
        populatePassengerSection(numPassengers, passengerList);
        populatePickUpLocationSection();
        selectPaymentOption(paymentData, productCheckOutPage);
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

            if(isElementRequiered(checkOutPageElements, "documentType0")) {
                passengerSection.setDocumentType(passengerId.documentType, passengerInfo.get("documentType").toString());
            }

            if(isElementRequiered(checkOutPageElements, "document_number")){
                passengerSection.setDocumentNumber(passengerId.documentNumber, passengerInfo.get("document_number").toString());
            }

            if(isElementRequiered(checkOutPageElements, "document_emisor")) {
                passengerSection.setDocumentEmisor(passengerId.document_emisor, passengerInfo.get("document_emisor").toString());
            }

            if(isElementRequiered(checkOutPageElements, "document_expiration")) {
                passengerSection.setDocumentExpiration(passengerId.document_expiration, passengerInfo.get("document_expiration").toString());
            }

            if(isElementRequiered(checkOutPageElements, "birthday")) {
                passengerSection.setBirthDay(passengerId.birthday, passengerInfo.get("birthday").toString());
            }

            if(isElementRequiered(checkOutPageElements, "gender")) {
                passengerSection.setGender(passengerId.gender, passengerInfo.get("gender").toString());
            }

            if(isElementRequiered(checkOutPageElements, "nationality")) {
                    passengerSection.setNationality(passengerId.nationality, passengerInfo.get("nationality").toString());
            }

            passengerIndex = passengerIndex + 1;
        }
        return this;
    }

    private CheckOutPage populatePickUpLocationSection(){
        if(isElementRequiered(checkOutPageElements, "PickUpLocationSection")){
            pickUpLocationSection().selectAgency();
        }
        return this;
    }

    private CheckOutPage populatePaymentSection(JSONObject paymentData, String product, PaymentSection paymentSection){
        paymentSection.selectPaymentQtyOption(0);
        paymentSection.selectBankOption(paymentData.get("credit_card_name").toString());

        paymentSection.setCardHolder(paymentData.get("card_holder").toString());

        paymentSection.setCardNumber(paymentData.get("card_number").toString());

        if(product.contains("Hoteles") || product.contains("Autos") || product.contains("Vuelos")) {
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

    private CheckOutPage selectPaymentOption(JSONObject paymentData, String product) {

        PaymentSection paymentSection = initPaymentSection();
        logger.info("------------- Filling Payment Section -------------");

        switch(paymentData.get("credit_card_name").toString()){
            case "cash":
                paymentSection.selectPaymentOption("Pago en efectivo");
                break;
            case "deposit":
                paymentSection.selectPaymentOption("Depósito");
                break;
            case "transfer":
                paymentSection.selectPaymentOption("Transferencia");
                break;
            case "booking24":
                paymentSection.selectPaymentOption("Reserva por 24 hs.");
                break;
            default:
                populatePaymentSection(paymentData, product, paymentSection);
        }
        return this;
    }

    private CheckOutPage populateBillingSection(JSONObject billingData) {
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
        if(isElementRequiered(checkOutPageElements, "agentEmail")) {
            footerSection.setEmailTxt("gabriel.cespedes@almundo.com");
        }
        return this;
    }
}
