package com.almundo.browser.automation.locators;
import org.openqa.selenium.By;

/**
 * Created by gabrielcespedes on 31/10/16.
 */
public enum PaymentPageMap {
    TARJETA_DDL(By.id("cardselect")),
    CANT_CUOTAS_DDL(By.id("cantselect")),
    VER_BANCOS_LNK(By.xpath("//*[@id=\"ng-app\"]/div/div[4]/form/div[3]/fieldset/div[2]/div/div/div/div[1]/div[1]/div[2]/div/div/p[1]/span/span[1]")),
    VER_BANCOS_LNK_CLK(By.xpath("//input[contains(@class,'epp-space-right-8')]")),
    AMERICAN_EXPRESS_INP(By.xpath("//option[contains(.,'American Express')]")),
    CUOTAS_24(By.xpath("//option[contains(.,'24')]")),
    TARJETA_RDO(By.xpath("//input[contains(@id,'p_')]")),
    TITULAR_DE_LA_TARJETA_TXT(By.id("card_holder")),
    NUMERO_DE_TARJETA_TXT(By.id("card_number")),
    FECHA_DE_VENCIMIENTO_TXT(By.id("card_expire")),
    CODIGO_DE_SEGURIDAD_TXT(By.id("security_code")),
    CUIL_TXT(By.id("billing_fiscal_document")),
    DOMICILIO_TXT(By.id("billing_address")),
    NUMERO_TXT(By.id("address_number")),
    PISO_TXT(By.id("address_floor")),
    DEPARTAMENTO_TXT(By.id("address_department")),
    CODIGO_POSTAL_TXT(By.id("address_postal_code")),
    PROVINCIA_TXT(By.id("address_state")),
    CIUDAD_TXT(By.id("address_city")),
    EMAIL_TXT(By.id("email")),
    REPETI_TU_EMAIL_TXT(By.id("rep_email")),
    CODIGO_DE_AREA_TELEF_TXT(By.id("area0")),
    NUMERO_TELEF_TXT(By.id("phone_number0")),
    LEI_ACEPTO_CBX(By.id("read")),
    COMPRAR_BTN(By.xpath("//input[@class='button button--lg button--secondary']"));

    private By name;
    PaymentPageMap(By locator) {this.name = locator; }
    public By getBy() { return name; }
}