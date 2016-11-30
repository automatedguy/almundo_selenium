package com.almundo.browser.automation.pages.PaymentPageSections;

import com.almundo.browser.automation.base.PageBaseSetup;
import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.locators.pages.PaymentPageMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class PaymentPage extends PageBaseSetup {

    public PaymentPage(WebDriver driver) { super.driver = driver; }

    WebElement billingInfo;

    public PaymentPage populateCreditCardOwnerData(WebDriver driver){

        moveToElement(driver, PaymentPageMap.TITULAR_DE_LA_TARJETA_TXT.getBy());

        enterText(driver, "Nombre", PaymentPageMap.TITULAR_DE_LA_TARJETA_TXT.getBy());
        enterText(driver, "999999999999", PaymentPageMap.NUMERO_DE_TARJETA_TXT.getBy());

        if(TestBaseSetup.baseURL == "http://www.almundo.com"){
            enterText(driver, "07/17", PaymentPageMap.FECHA_DE_VENCIMIENTO_TXT.getBy());
        }else
        {
         // select from drop down list.
        }

        enterText(driver, "777", PaymentPageMap.CODIGO_DE_SEGURIDAD_TXT.getBy());
        // TODO: agregar Cedula para Colombia
        return this;
    }

    public boolean isBillingInfoRequiered(WebDriver driver){

        billingInfo = driver.findElement(By.cssSelector("div:nth-child(4)>fieldset>div.container__title>h2"));

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
        clickOn(driver, PaymentPageMap.LEI_ACEPTO_CBX.getBy());
        // TODO: add Additional check box for Colombia
        return this;
    }

    public PaymentPage acceptTermsConditions(WebDriver driver){
        leiAceptoCbx(driver);
        return this;
    }

}
