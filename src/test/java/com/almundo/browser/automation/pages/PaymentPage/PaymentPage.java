package com.almundo.browser.automation.pages.PaymentPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.locators.pages.PaymentPageMap;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class PaymentPage extends TestBaseSetup {

    public PaymentPage(WebDriver driver) { super.driver = driver; }

    public JSONObject paymentPageElements = null;

    private void getPaymentPageElements()  {
        logger.info("Elements from properties file...");
        logger.info(countryPropertyObject);

        logger.info("Reading PaymentPage enabled elements from properties file...");
        paymentPageElements = (JSONObject) countryPropertyObject.get("PaymentPage");
        logger.info(paymentPageElements);
    }

    public PaymentPage populatePaymentPage(WebDriver driver, int numPassengers) throws InterruptedException {

        getPaymentPageElements();
        // AQ-41
        populatePassengers(driver, numPassengers);

        // AQ-42
        populatePaymentInfo(driver);

        // AQ-43
        populateBillingInfo(driver);

        populateContactInfo(driver);

        // AQ-44
        checkConditions(driver);
        return this;
    }

    public ArrayList<Passenger> createPassenger(int numPassengers){
        ArrayList<Passenger> passengers = new ArrayList<>();
        for (int idNum = 0; idNum < numPassengers; idNum++) {
            passengers.add(new Passenger(idNum));
        }
        return passengers;
    }

    public boolean isElementRequiered(JSONObject JSONElementsRead, String element){

        boolean isRequiered = false;
        logger.info("Checking whether Billing Information will be requiered or not...");

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

    public PaymentPage populatePassengers(WebDriver driver, int numPassengers){

        ArrayList<Passenger> passengers = createPassenger(numPassengers);

        PassengerInfoSection passengerInfoSection = initPassengeInforSection(driver);

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

    public void populatePaymentInfo(WebDriver driver){

        PageUtils.moveToElement(driver, PaymentPageMap.TITULAR_DE_LA_TARJETA_TXT.getBy());

        PaymentInfoSection paymentInfoSection = initPaymentInfoSection(driver);

        paymentInfoSection.selectPaymentQtyOption(0);
        paymentInfoSection.selectBankOption("American Express");

        paymentInfoSection.setCardHolder("Nombre Apellido");

        paymentInfoSection.setCardNumber("4242424242424242");

        if(baseURL.equals("http://almundo.com/")){
            paymentInfoSection.setCardExpiration("07/17");
        }else {
         // select from drop down list.
        }

        paymentInfoSection.setSecurityCode("777");

        if(isElementRequiered(paymentPageElements, "documentType")) {
            paymentInfoSection.selectDocumentType("Pasaporte");
        }

        if(isElementRequiered(paymentPageElements, "document_number")) {
            paymentInfoSection.setDocumentNumber("2078709888");
        }
    }

    public void populateBillingInfo(WebDriver driver) {
        if (isElementRequiered(paymentPageElements, "BillingInfoSection")) {

            logger.info("Populating billing information fields requiered...");

            BillingInfoSection billingInfoSection = initBillingInfoSection(driver);

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
    }

    public void populateContactInfo(WebDriver driver) {

        ContactInfoSection contactInfoSection = initContactInfoSection(driver);

        contactInfoSection.setEmail("testing@almundo.com");

        contactInfoSection.setRepEmail("testing@almundo.com");

        contactInfoSection.selectPhoneType("Teléfono");

        contactInfoSection.setCountryCode("0054");

        contactInfoSection.setAreaCode("11");

        contactInfoSection.setPhoneNumber("44448888");

    }

    public PaymentPage checkConditions(WebDriver driver){
        FooterSection footerSection = initFooterSection(driver);
        footerSection.acceptTermsAndConditions();
        if(isElementRequiered(paymentPageElements, "accepted")) {
            footerSection.acceptItinerary();
            footerSection.confirmarClick();
        }
        return this;
    }

    // inits

    protected PaymentInfoSection initPaymentInfoSection(WebDriver driver) {
        return PageFactory.initElements(driver, PaymentInfoSection.class);
    }

    protected BillingInfoSection initBillingInfoSection(WebDriver driver) {
        return PageFactory.initElements(driver, BillingInfoSection.class);
    }

    protected ContactInfoSection initContactInfoSection(WebDriver driver) {
        return PageFactory.initElements(driver, ContactInfoSection.class);
    }

    protected FooterSection initFooterSection(WebDriver driver) {
        return PageFactory.initElements(driver, FooterSection.class);
    }

    protected PassengerInfoSection initPassengeInforSection(WebDriver driver) {
        return PageFactory.initElements(driver, PassengerInfoSection.class);
    }

}
