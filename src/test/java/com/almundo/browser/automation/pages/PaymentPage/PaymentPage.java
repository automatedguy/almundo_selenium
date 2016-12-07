package com.almundo.browser.automation.pages.PaymentPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.locators.pages.PaymentPageMap;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

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
        populatePassengerInfo(driver, numPassengers);

        // AQ-42
        populatePaymentInfo(driver);

        // AQ-43
        populateBillingInfo(driver);

        populateContactInfo(driver);

        // AQ-44
        acceptTermsConditions(driver);
        return this;
    }

    public void populatePassengerInfo(WebDriver driver, int numPassengers) {

        PassengersSection passengersSection = new PassengersSection();
        passengersSection.populatePassenger(driver, numPassengers);

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

    public void populateContactInfo(WebDriver driver) {

        ContactInfoSection contactInfoSection = initContactInfoSection(driver);

        contactInfoSection.setEmail("testing@almundo.com");

        contactInfoSection.setRepEmail("testing@almundo.com");

        contactInfoSection.selectPhoneType("Teléfono");

        contactInfoSection.setCountryCode("0054");

        contactInfoSection.setAreaCode("11");

        contactInfoSection.setPhoneNumber("44448888");

    }
    


    public PaymentPage leiAceptoCbx(WebDriver driver){
        PageUtils.clickOn(driver, PaymentPageMap.LEI_ACEPTO_CBX.getBy());
        // TODO: add Additional check box for Colombia
        return this;
    }

    public PaymentPage acceptTermsConditions(WebDriver driver){
        leiAceptoCbx(driver);
        return this;
    }


    //Inits

    protected PaymentInfoSection initPaymentInfoSection(WebDriver driver) {
        return PageFactory.initElements(driver, PaymentInfoSection.class);
    }

    protected BillingInfoSection initBillingInfoSection(WebDriver driver) {
        return PageFactory.initElements(driver, BillingInfoSection.class);
    }

    protected ContactInfoSection initContactInfoSection(WebDriver driver) {
        return PageFactory.initElements(driver, ContactInfoSection.class);
    }
}
