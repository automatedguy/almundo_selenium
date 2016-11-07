package com.almundo.browser.automation.pages;

import com.almundo.browser.automation.base.PageBaseSetup;
import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.locators.dynamic.Passenger;
import com.almundo.browser.automation.locators.pages.PaymentPageMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class PaymentPage extends PageBaseSetup {

    public PaymentPage(WebDriver driver) { super.driver = driver; }

    public PaymentPage populatePassenger(WebDriver driver, int numPassengers){
        /* create passengers */
        ArrayList<Passenger> passengers = new ArrayList<Passenger>();
        for (int idNum = 0; idNum < numPassengers; idNum++) {
            passengers.add(new Passenger(idNum));
        }

        // TODO: come up with a solution for the case when the payment page is not loaded.
        // driver.navigate().refresh();
        waitForVisibilityOfElementLocated(driver, 60 , PaymentPageMap.FIRST_NAME_TXT.getBy());

        /*  Populate passengers */
        for(final Passenger passengerToPopulate : passengers){
            WebElement firstName, lastName, documentNumber;

            firstName = driver.findElement(By.id(passengerToPopulate.firstName));
            firstName.sendKeys("Nombre");

            lastName = driver.findElement(By.id(passengerToPopulate.lastName));
            lastName.sendKeys("Apellido");

            /*  This must be available for Argentina only ????? */
            if(!driver.findElements(By.id(passengerToPopulate.documentNumber)).isEmpty()){
                documentNumber = driver.findElement(By.id(passengerToPopulate.documentNumber));
                documentNumber.sendKeys("123456789");
            }else{
                System.out.println("Document number");
            }

            if(!driver.findElements(By.id(passengerToPopulate.fechaNacimiento)).isEmpty()){
                documentNumber = driver.findElement(By.id(passengerToPopulate.fechaNacimiento));
                documentNumber.sendKeys("09/09/1979");
            }else{
                System.out.println("Birthday");
            }
        }
        return this;
    }

    public PaymentPage populateCreditCardOwnerData(WebDriver driver){

        moveToElement(driver, PaymentPageMap.TITULAR_DE_LA_TARJETA_TXT.getBy());

        enterText(driver, "Nombre", PaymentPageMap.TITULAR_DE_LA_TARJETA_TXT.getBy());
        enterText(driver, "999999999999", PaymentPageMap.NUMERO_DE_TARJETA_TXT.getBy());
        enterText(driver, "07/17", PaymentPageMap.FECHA_DE_VENCIMIENTO_TXT.getBy());
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
            System.out.println("No CUIL info");
        }
        enterText(driver, "email@sarasa.cuak", PaymentPageMap.EMAIL_TXT.getBy());
        enterText(driver, "email@sarasa.cuak", PaymentPageMap.REPETI_TU_EMAIL_TXT.getBy());
        enterText(driver, "011", PaymentPageMap.CODIGO_DE_AREA_TELEF_TXT.getBy());
        enterText(driver, "20279456548", PaymentPageMap.NUMERO_TELEF_TXT.getBy());
        return this;
    }

    public PaymentPage populatePaymentInfo(WebDriver driver) throws InterruptedException {

//        WebElement firstPassengerTxt = driver.findElement(PaymentPageMap.FIRST_NAME_TXT.getBy());
//        waitForElement(firstPassengerTxt, 10, 1000);

        populatePassenger(driver, TestBaseSetup.numPassengers);
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
