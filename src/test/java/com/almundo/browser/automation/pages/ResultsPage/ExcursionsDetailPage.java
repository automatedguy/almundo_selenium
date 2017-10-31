package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;
import static com.almundo.browser.automation.utils.PageUtils.waitWithTryCatch;

public class ExcursionsDetailPage extends TestBaseSetup {

    public ExcursionsDetailPage(WebDriver driver) {
        super.driver = driver;
    }

    /********************** Locators **********************/

    private final String elegirFechaBtnLct = "#activityDetails .card.collapsed-card-top .price a";
    @FindBy(css = elegirFechaBtnLct)
    private WebElement elegirFechaBtn;

    private final String puntoDePartidaLct = "#inputs-container .selectize-input input";
    @FindBy(css = puntoDePartidaLct)
    private WebElement puntoDePartida;

    private final String comprarBtnLct = "#activityDetails .second .pricebox .price a";
    @FindBy(css = comprarBtnLct)
    private WebElement comprarBtn;

    /********************** Actions **********************/

    public ExcursionsDetailPage clickElegirFechaBtn(){
        waitWithTryCatch(driver, elegirFechaBtnLct, "Elegir fecha", 10);
        logger.info("Clicking on [Elegir fecha] button");
        elegirFechaBtn.click();
        waitImplicitly(1000);
        return this;
    }

    public ExcursionsDetailPage setPuntoDePartida(String puntoDePartida){
        logger.info("Locating [Punto de partida] Select");
        waitWithTryCatch(driver, puntoDePartidaLct, "Punto De Partida", 10).click();
        logger.info("Retrieving [Puntos de partida] List");
        List<WebElement> puntosDePartidaList =  driver.findElements(By.cssSelector(".selectize-dropdown div"));
        logger.info("Selecting [Punto de partida]:" + puntoDePartida);
        for(WebElement puntoDePartidaWebElement : puntosDePartidaList){
            if(puntoDePartidaWebElement.getText().equals(puntoDePartida)){
                JavascriptExecutor je = (JavascriptExecutor) driver;
                je.executeScript("arguments[0].scrollIntoView(true);",puntoDePartidaWebElement);
                puntoDePartidaWebElement.click();
                break;
            }
        }
        return this;
    }

    public CheckOutPageV3 clickComprarBtn(){
        waitWithTryCatch(driver, comprarBtnLct, "Comprar", 10);
        logger.info("Clicking on [Comprar] button");
        comprarBtn.click();
        return initCheckOutPageV3();
    }
}
