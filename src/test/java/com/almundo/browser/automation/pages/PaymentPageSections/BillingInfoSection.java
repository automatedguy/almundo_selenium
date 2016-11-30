package com.almundo.browser.automation.pages.PaymentPageSections;

import com.almundo.browser.automation.base.PageBaseSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by gabrielcespedes on 29/11/16.
 */
public class BillingInfoSection extends PageBaseSetup {

    WebElement CUIL,
            SITUACION_FISCAL,
            TIPO_DE_DOCUMENTO,
            NOMBRE_O_RAZON_SOCIAL,
            DOMICILIO,
            NUMERO,
            PISO,
            DEPARTAMENTO,
            CODIGO_POSTAL,
            PROVINCIA,
            CIUDAD;

    BillingInfoSection(WebDriver driver){
        if(!driver.findElements(By.id("billing_fiscal_document")).isEmpty()){
            CUIL = driver.findElement(By.id("billing_fiscal_document"));
            logger.info("Billing Fiscal information is requiered.");

            logger.info("Trying to find whether Razon Social is requiered or not...");
            if(!driver.findElements(By.id("fiscal_name")).isEmpty()){
                NOMBRE_O_RAZON_SOCIAL = driver.findElement(By.id("fiscal_name"));
                logger.info("Nombre o Razon Social is requiered.");
            }
            else{
                logger.info("Nombre o Razon Social is not requiered.");
                NOMBRE_O_RAZON_SOCIAL = null;
            }

            SITUACION_FISCAL = driver.findElement(By.id("billing_fiscal_type"));
            TIPO_DE_DOCUMENTO = driver.findElement(By.id("billing_document_type"));
            DOMICILIO = driver.findElement(By.id("billing_address"));
            NUMERO = driver.findElement(By.id("address_number"));
            PISO = driver.findElement(By.id("address_floor"));
            DEPARTAMENTO = driver.findElement(By.id("address_department"));
            CODIGO_POSTAL = driver.findElement(By.id("address_postal_code"));
            PROVINCIA = driver.findElement(By.id("address_state"));
            CIUDAD = driver.findElement(By.id("address_city"));
        }
        else{
            logger.info("Billing Fiscal information not requiered at all.");
            CUIL = null;
        }

    }

    public BillingInfoSection selectSituacionFiscal(){
        logger.info("Situación fiscal drop down list is enabled, selecting...");
        Select SITUACION_FISCAL_SELECT = new Select(SITUACION_FISCAL);
        SITUACION_FISCAL_SELECT.selectByVisibleText("Persona juridica");
        return this;
    }

    public BillingInfoSection selectTipoDeDocumento(){
        logger.info("Tipo de Documento drop down list is enabled, selecting...");
        Select TIPO_DE_DOCUMENTO_SELECT = new Select(TIPO_DE_DOCUMENTO);
        TIPO_DE_DOCUMENTO_SELECT.selectByVisibleText("Tarjeta de Identidad");
        return this;
    }

    public BillingInfoSection populateBillingInfo(){
        if(CUIL != null) {
            logger.info("Populating billing information fields requiered...");

            if (NOMBRE_O_RAZON_SOCIAL != null) {
                NOMBRE_O_RAZON_SOCIAL.sendKeys("Nombre o Razon Social");
            }

            if(SITUACION_FISCAL.isEnabled()) {
                selectSituacionFiscal();
            }

            if(TIPO_DE_DOCUMENTO.isEnabled()) {
                selectTipoDeDocumento();
            }

            CUIL.sendKeys("20285494568");
            DOMICILIO.sendKeys("Domicilo");
            NUMERO.sendKeys("7550");
            PISO.sendKeys("10");
            DEPARTAMENTO.sendKeys("A");
            CODIGO_POSTAL.sendKeys("1009");
            PROVINCIA.sendKeys("Buenos Aires");
            CIUDAD.sendKeys("CABA");
        }
        return this;
    }
}