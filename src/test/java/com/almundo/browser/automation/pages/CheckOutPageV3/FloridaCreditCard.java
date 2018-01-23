package com.almundo.browser.automation.pages.CheckOutPageV3;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FloridaCreditCard extends CheckOutPageV3 {

    public FloridaCreditCard(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#paymentOptionSection div:nth-child(1) > label > input")
    private WebElement importeTarjeta;

    @FindBy(css = "#paymentOptionSection div:nth-child(2) > input")
    private WebElement numeroTarjeta;

    @FindBy(css = "#paymentOptionSection div:nth-child(2) > div:nth-child(1) > select")
    private WebElement tarjetaSelect;

    @FindBy(css = "#paymentOptionSection div:nth-child(2) > div:nth-child(2) > select")
    private WebElement bancoSelect;

    @FindBy(css = "#paymentOptionSection div:nth-child(3) > div > select")
    private WebElement cuotasSelect;

    @FindBy(css = "#paymentOptionSection div:nth-child(4) input")
    private WebElement titularTarjeta;

    @FindBy(css = "#paymentOptionSection div:nth-child(4) > div:nth-child(2) > select")
    private WebElement fechVencMes;

    @FindBy(css = "#paymentOptionSection div:nth-child(4) > div:nth-child(3) > select")
    private WebElement getFechVencAno;

    @FindBy(css = "#paymentOptionSection div:nth-child(5) input")
    private WebElement codigoSeguridad;

}
