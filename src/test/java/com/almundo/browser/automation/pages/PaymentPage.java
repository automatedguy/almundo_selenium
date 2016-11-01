package com.almundo.browser.automation.pages;

import com.almundo.browser.automation.locators.pages.PaymentPageMap;
import com.almundo.browser.automation.locators.dynamic.Passenger;
import com.almundo.browser.automation.utils.PageUtils;
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

        // This is nasty but could work for now.... :)
        PageUtils.waitForVisibilityOfElementLocated(driver, 30 ,PaymentPageMap.FIRST_NAME_TXT.getBy());

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
        element = driver.findElement(PaymentPageMap.VER_BANCOS_01CUOTAS_LNK.getBy());
        return element;
    }

    public static WebElement ver15BancosMasLnkCkl(WebDriver driver){
        element = driver.findElement(PaymentPageMap.VER_BANCOS_LNK_CLK.getBy());
        return element;
    }

    public static WebElement pagoUnaCuota(WebDriver driver){
        element = driver.findElement(PaymentPageMap.TARJETA_RDO.getBy());
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

    public static void populateCreditCardPayments(WebDriver driver){

        PageUtils.moveToElement(driver, PaymentPageMap.VER_BANCOS_01CUOTAS_LNK.getBy());
        ver15BancosMasLnk(driver).click();

        PageUtils.moveToElement(driver, PaymentPageMap.TARJETA_RDO.getBy());
        pagoUnaCuota(driver).click();

        PageUtils.moveToElement(driver, PaymentPageMap.TARJETA_RDO.getBy());
        pagoUnaCuota(driver).click();
    }

    public static void populatedCreditCardPayment24(WebDriver driver){

        PageUtils.moveToElement(driver, PaymentPageMap.TARJETA_DDL.getBy());
        seleccionaTarjetaDdl(driver).click();

        seleccionarTarjeta(driver, PaymentPageMap.AMERICAN_EXPRESS_INP.getBy()).click();
        cantidadCuotasDdl(driver).click();

        SeleccionarCantidadCuotas(driver, PaymentPageMap.CUOTAS_24.getBy());
    }

    public static void populateCreditCardOwnerData(WebDriver driver){

        PageUtils.moveToElement(driver, PaymentPageMap.TITULAR_DE_LA_TARJETA_TXT.getBy());

        titularDeLaTarjetaTxt(driver).sendKeys("Nombre");
        numeroDeTarjetaTxt(driver).sendKeys("999999999999");
        fechaDeVencimientoTxt(driver).sendKeys("07/17");
        codigoSeguridadTxt(driver).sendKeys("777");
    }

    public static void populateBillingInformation(WebDriver driver){
        cuilTxt(driver).sendKeys("20285494568");
        domicilioTxt(driver).sendKeys("Domicilio");
        numeroTxt(driver).sendKeys("7570");
        pisoTxt(driver).sendKeys("75");
        departamentTxt(driver).sendKeys("A");
        codigoPostalTxt(driver).sendKeys("7525");
        provinciaTxt(driver).sendKeys("Provincia");
        ciudadTxt(driver).sendKeys("Ciudad");
        emailTxt(driver).sendKeys("email@sarasa.cuak");
        repEmailTxt(driver).sendKeys("email@sarasa.cuak");
        codAreaTelefTxt(driver).sendKeys("098");
        numeroTelefTxt(driver).sendKeys("20279456548");
    }

    public static void acceptTermsConditions(WebDriver driver){
        leiAceptoCbx(driver).click();
    }

}
