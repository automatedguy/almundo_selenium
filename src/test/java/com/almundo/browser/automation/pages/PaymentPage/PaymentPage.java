package com.almundo.browser.automation.pages.PaymentPage;

import com.almundo.browser.automation.base.PageBaseSetup;
import com.almundo.browser.automation.locators.pages.PaymentPageMap;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static com.almundo.browser.automation.base.TestBaseSetup.baseURL;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class PaymentPage extends PageBaseSetup {

    public PaymentPage(WebDriver driver) { super.driver = driver; }

    TypeOfPaymentSection typeOfPaymentSection = null;

    WebElement billingInfo;

    public PaymentPage populateCreditCardOwnerData(WebDriver driver){

        PageUtils.moveToElement(driver, PaymentPageMap.TITULAR_DE_LA_TARJETA_TXT.getBy());

        typeOfPaymentSection = initTypeOfPaymentSection(driver);

        typeOfPaymentSection.selectPaymentQtyOption(0);
        typeOfPaymentSection.selectBankOption("American Express");

        typeOfPaymentSection.setCardHolder("Nombre Apellido");

        typeOfPaymentSection.setCardNumber("4242424242424242");

        if(baseURL.equals("http://almundo.com/")){
            typeOfPaymentSection.setCardExpiration("07/17");
        }else {
         // select from drop down list.
        }

        typeOfPaymentSection.setSecurityCode("777");

        // TODO: agregar Cedula para Colombia
        return this;
    }

    public boolean isBillingInfoRequiered(WebDriver driver){

        billingInfo = driver.findElement(By.cssSelector("div:nth-child(4)>fieldset>div.container__title>h2"));


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
    
    public PaymentPage populatePaymentInfo(WebDriver driver, int numPassengers) throws InterruptedException {

        // AQ-41
        PassengersSection passengersSection = new PassengersSection();
        passengersSection.populatePassenger(driver, numPassengers);

        // AQ-42
        populateCreditCardOwnerData(driver);

        // AQ-43
        if(isBillingInfoRequiered(driver)) {
            BillingInfoSection billingInfoSection = new BillingInfoSection(driver);
            billingInfoSection.populateBillingInfo();
        }

        // AQ-44
        acceptTermsConditions(driver);
        return this;
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

    protected TypeOfPaymentSection initTypeOfPaymentSection(WebDriver driver) {
        return PageFactory.initElements(driver, TypeOfPaymentSection.class);
    }

}
