package com.almundo.browser.automation.pages.PaymentPageSections;

import com.almundo.browser.automation.base.PageBaseSetup;
import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.locators.pages.PaymentPageMap;
import org.openqa.selenium.WebDriver;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class PaymentPage extends PageBaseSetup {

    public PaymentPage(WebDriver driver) { super.driver = driver; }

    public PassengersSection passengersSection = new PassengersSection();


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

    public PaymentPage populateBillingInformation(WebDriver driver){

        if(!driver.findElements(PaymentPageMap.CUIL_TXT.getBy()).isEmpty()){
            enterText(driver, "20285494568", PaymentPageMap.CUIL_TXT.getBy());
            enterText(driver, "Domicilio", PaymentPageMap.DOMICILIO_TXT.getBy());
            enterText(driver, "7570", PaymentPageMap.NUMERO_TXT.getBy());
            enterText(driver, "75", PaymentPageMap.PISO_TXT.getBy());
            enterText(driver, "A", PaymentPageMap.DEPARTAMENTO_TXT.getBy());
            enterText(driver, "7525", PaymentPageMap.CODIGO_POSTAL_TXT.getBy());
            enterText(driver, "Provincia", PaymentPageMap.PROVINCIA_TXT.getBy());
            enterText(driver, "Ciudad", PaymentPageMap.CIUDAD_TXT.getBy());
        }
        else{
            logger.info("CUIL info is not required.");
        }
        enterText(driver, "email@sarasa.cuak", PaymentPageMap.EMAIL_TXT.getBy());
        enterText(driver, "email@sarasa.cuak", PaymentPageMap.REPETI_TU_EMAIL_TXT.getBy());
        enterText(driver, "011", PaymentPageMap.CODIGO_DE_AREA_TELEF_TXT.getBy());
        enterText(driver, "20279456548", PaymentPageMap.NUMERO_TELEF_TXT.getBy());
        return this;
    }

    public PaymentPage populatePaymentInfo(WebDriver driver, int numPassengers) throws InterruptedException {
        populateCreditCardOwnerData(driver);
        populateBillingInformation(driver);
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
