package com.almundo.browser.automation.pages.CheckOutPageV3;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class FloridaOther extends CheckOutPageV3{

    public FloridaOther(WebDriver driver) {
        super(driver);
    }

    /************************ Locators ***********************/

    @FindBy(css = "#paymentOptionSection am-non-card-ts input")
    private WebElement montoInput;

    @FindBy(css = "#paymentOptionSection am-non-card-ts .combo-space select")
    private List<WebElement> medioDePago;

    /************************ Actions ***********************/

    private FloridaOther setMontoInput(String monto){
        logger.info("Setting Monto: [" + monto + "]");
        montoInput.clear();
        montoInput.sendKeys(monto);
        return this;
    }

    private FloridaOther setMedioDePago(String paymentForm, int index){
        logger.info("Selecting [Medio de pago]: [" + paymentForm + "]");
        Select medioDePagoDdl = new Select(medioDePago.get(index));
        medioDePagoDdl.selectByVisibleText(paymentForm);
        return this;
    }

    public FloridaOther setOtherInfo(String paymentForm, String priceToPay, int index, boolean isLastPayment){
        setMedioDePago(paymentForm, index);
        if(!isLastPayment){
            setMontoInput(priceToPay);
        }
        return this;
    }
}