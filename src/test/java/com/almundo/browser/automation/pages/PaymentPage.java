package com.almundo.browser.automation.pages;

import com.almundo.browser.automation.locators.PaymentPageMap;
import com.almundo.browser.automation.locators.dynamic.Passenger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

/**
 * Created by gabrielcespedes on 31/10/16.
 */
public class PaymentPage {
    private static WebElement element = null;

    public static void populatePassenger(WebDriver driver, int numPassengers){
        /* First create */
        ArrayList<Passenger> passengers = new ArrayList<Passenger>();
        for (int idNum = 0; idNum < numPassengers; idNum++) {
            passengers.add(new Passenger(idNum));
        }

        /* Then populate passengers */
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
    }

    public static WebElement ver15BancosMasLnk(WebDriver driver){
        element = driver.findElement(PaymentPageMap.VER_15_BANCOS_MAS_LNK.getBy());
        return element;
    }

    public static WebElement ver15BancosMasLnkCkl(WebDriver driver){
        element = driver.findElement(PaymentPageMap.VER_15_BANCOS_MAS_LNK_CLK.getBy());
        return element;
    }

    public static WebElement amexPagoUnaCuota(WebDriver driver){
        element = driver.findElement(PaymentPageMap.AMEX_01_CUOTAS_RDO.getBy());
        return element;
    }

    public static WebElement titularDeLaTarjetaTxt(WebDriver driver){
        element = driver.findElement(PaymentPageMap.TITULAR_DE_LA_TARJETA_TXT.getBy());
        return element;
    }

    public static WebElement numeroDeTarjetaTxt(WebDriver driver){
        element = driver.findElement(PaymentPageMap.NUMERO_DE_TARJETA_TXT.getBy());
        return element;
    }

    public static WebElement fechaDeVencimientoTxt(WebDriver driver) {
        element = driver.findElement(PaymentPageMap.FECHA_DE_VENCIMIENTO_TXT.getBy());
        return element;
    }

    public static WebElement codigoSeguridadTxt(WebDriver driver){
        element = driver.findElement(PaymentPageMap.CODIGO_DE_SEGURIDAD_TXT.getBy());
        return element;
    }

    public static WebElement cuilTxt(WebDriver driver){
        element = driver.findElement(PaymentPageMap.CUIL_TXT.getBy());
        return element;
    }

    public static WebElement domicilioTxt(WebDriver driver){
        element = driver.findElement(PaymentPageMap.DOMICILIO_TXT.getBy());
        return element;
    }

    public static WebElement numeroTxt(WebDriver driver){
        element = driver.findElement(PaymentPageMap.NUMERO_TXT.getBy());
        return element;
    }

    public static WebElement pisoTxt(WebDriver driver){
        element = driver.findElement(PaymentPageMap.PISO_TXT.getBy());
        return element;
    }

    public static WebElement departamentTxt(WebDriver driver){
        element = driver.findElement(PaymentPageMap.DEPARTAMENTO_TXT.getBy());
        return element;
    }

    public static WebElement codigoPostalTxt(WebDriver driver){
        element = driver.findElement(PaymentPageMap.CODIGO_POSTAL_TXT.getBy());
        return element;
    }

    public static WebElement provinciaTxt(WebDriver driver){
        element = driver.findElement(PaymentPageMap.PROVINCIA_TXT.getBy());
        return element;
    }

    public static WebElement ciudadTxt(WebDriver driver){
        element = driver.findElement(PaymentPageMap.CIUDAD_TXT.getBy());
        return element;
    }

    public static WebElement emailTxt(WebDriver driver){
        element = driver.findElement(PaymentPageMap.EMAIL_TXT.getBy());
        return element;
    }

    public static WebElement repEmailTxt(WebDriver driver){
        element = driver.findElement(PaymentPageMap.REPETI_TU_EMAIL_TXT.getBy());
        return element;
    }

    public static WebElement codAreaTelefTxt(WebDriver driver){
        element = driver.findElement(PaymentPageMap.CODIGO_DE_AREA_TELEF_TXT.getBy());
        return element;
    }

    public static WebElement numeroTelefTxt(WebDriver driver){
        element = driver.findElement(PaymentPageMap.NUMERO_TELEF_TXT.getBy());
        return element;
    }

    public static WebElement leiAceptoCbx(WebDriver driver){
        element = driver.findElement(PaymentPageMap.LEI_ACEPTO_CBX.getBy());
        return element;
    }

    public static WebElement seleccionaTarjetaDdl(WebDriver driver){
        element = driver.findElement(PaymentPageMap.TARJETA_DDL.getBy());
        return element;
    }

    public static WebElement seleccionarTarjeta(WebDriver driver, By tarjeta){
        WebElement selectedCard = driver.findElement(tarjeta);
        selectedCard.click();
        return element;
    }

    public static WebElement cantidadCuotasDdl(WebDriver driver){
        element = driver.findElement(PaymentPageMap.CANT_CUOTAS_DDL.getBy());
        return element;
    }

    public static WebElement SeleccionarCantidadCuotas(WebDriver driver, By cuotas){
        WebElement selectedCard = driver.findElement(cuotas);
        selectedCard.click();
        return element;
    }

    public static WebElement comprarBtn(WebDriver driver){
        element = driver.findElement(PaymentPageMap.COMPRAR_BTN.getBy());
        return element;
    }
}
