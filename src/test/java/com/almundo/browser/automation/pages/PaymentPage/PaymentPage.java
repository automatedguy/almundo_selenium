package com.almundo.browser.automation.pages.PaymentPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.locators.pages.PaymentPageMap;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class PaymentPage extends TestBaseSetup {

    public PaymentPage(WebDriver iDriver) {
        this.driver = iDriver;
    }


    public PaymentPage populatePaymentPage(WebDriver driver, int numPassengers) throws InterruptedException {

        // AQ-41
        populatePassengerInfo(driver, numPassengers);

        // AQ-42
        populatePaymentInfo(driver);

        // AQ-43
        popupateBillingInfo(driver);

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

        PaymentInfoSection paymentInfoSection = initPaymentInfoSection();

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

        // TODO: agregar Cedula para Colombia
    }

    public void popupateBillingInfo(WebDriver driver) {
        if(isBillingInfoRequiered(driver)) {
            BillingInfoSection billingInfoSection = initBillingInfoSection();
            billingInfoSection.populateBillingInfo();
        }
    }

    public boolean isBillingInfoRequiered(WebDriver driver){

        WebElement billingInfo = driver.findElement(By.cssSelector("div:nth-child(4)>fieldset>div.container__title>h2"));

        List<WebElement> paymentPageSectionTittles = driver.findElements(By.cssSelector("h2.text--lg"));

        for(WebElement paymentPageSectionTittlesToPrint : paymentPageSectionTittles){
            System.out.println("Printing Paymentpage Sections Tittles... " + paymentPageSectionTittlesToPrint.getText());
        }

        System.out.println("This is the text....: " + billingInfo.getText());

        if(billingInfo.getText().equals("3.¿A nombre de quién emitimos la factura?")){
            logger.info("Billing information is requiered.");
            return true;
        }
        else{
            logger.info("Billing information is not requiered.");
            return false;
        }
    }

    public void populateContactInfo(WebDriver driver) {

        ContactInfoSection contactInfoSection = initContactInfoSection();

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


    //################################################ Inits ################################################


}
