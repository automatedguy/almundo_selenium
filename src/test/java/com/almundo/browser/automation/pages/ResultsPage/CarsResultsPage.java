package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.almundo.browser.automation.utils.Constants.Messages.NO_PUDIMOS_PROCESAR;
import static com.almundo.browser.automation.utils.Constants.Messages.PRUEBA_CON_OTRAS_FECHAS;
import static com.almundo.browser.automation.utils.Constants.Results.FAILED;
import static com.almundo.browser.automation.utils.PageUtils.formatInfo;

/**
 * Created by gabrielcespedes on 20/12/16.
 */
public class CarsResultsPage extends TestBaseSetup {

    public CarsResultsPage(WebDriver iDriver) {
        this.driver = iDriver;
    }

    //############################################### Locators ##############################################

    @FindBy(css = "body .epp-cl-1.ng-scope")
    List<WebElement> carsChoices;

    //############################################### Actions ###############################################

    public void displayCarType(int index){
        logger.info("Car Type: " + "[" + formatInfo(driver.findElement(By.cssSelector("article:nth-child(" + index + ") .epp-cl-1.epp-cl-1-2--ph.epp-cl-1-4--tb")).getText() + "]"));
    }

    public void displayAgency(int index){
        logger.info("Agency: " + "[" + formatInfo(driver.findElement(By.cssSelector("article:nth-child(" + index + ") .agency.ng-scope.selected")).getText() + "]"));
    }

    public void displayCarInfo(int index){
        logger.info("Car Info: " + "[" + formatInfo(driver.findElement(By.cssSelector("article:nth-child(" + index + ") .epp-cl-7-12--tb.ng-scope")).getText() + "]"));
    }

    public void displayCarRatesInfo(int index){
        logger.info("Car Rates Info :" + "[" +  formatInfo(driver.findElement(By.cssSelector("article:nth-child(" + index + ") .price-box-ctn.epp-cl-1-4.epp-cl-1--ph.epp-cl-1--tb")).getText() + "]"));
    }

    public void displaySelectedCarInfo(int index){
        displayCarType(index);
        displayAgency(index);
        displayCarInfo(index);
        displayCarRatesInfo(index);
    }

    public CarsOfficeModal clickSeleccionarOficinas(int index) {
        PageUtils.waitListContainResults(driver, ".see-offices", 0);
        List<WebElement> selectOfficeButtonResults = driver.findElements(By.cssSelector(".see-offices"));
        PageUtils.waitElementForClickable(driver, selectOfficeButtonResults.get(index), 20, "Seleccionar Oficinas button");
        logger.info("Clicking on Seleccionar Oficinas button");
        selectOfficeButtonResults.get(index).click();
        return initCarsOfficeModal();
    }

    public CheckOutPageV3 clickReservarAhoraBtn(int index) {
        PageUtils.waitUrlContains(driver, 10, "results", "Results url");
        logger.info("Results URL: " + "[" + driver.getCurrentUrl() + "]");
        String cssSelectorName  = "article .button";
        PageUtils.waitListContainResults(driver, cssSelectorName, 0);
        List<WebElement> detailCarsButtonResults = driver.findElements(By.cssSelector(cssSelectorName));
        PageUtils.waitElementForClickable(driver, detailCarsButtonResults.get(index), 20, "Reservar Ahora button");
        logger.info("Clicking on Reservar button");
        displaySelectedCarInfo(index + 3);
        detailCarsButtonResults.get(index).click();
        return initCheckOutPageV3();
    }

    public CheckOutPage clickReservarAhoraBtnV2(int index) {
        PageUtils.waitUrlContains(driver, 10, "results", "Results url");
        logger.info("Results URL: " + "[" + driver.getCurrentUrl() + "]");
        String cssSelectorName  = "article .button";
        PageUtils.waitListContainResults(driver, cssSelectorName, 0);
        List<WebElement> detailCarsButtonResults = driver.findElements(By.cssSelector(cssSelectorName));
        PageUtils.waitElementForClickable(driver, detailCarsButtonResults.get(index), 20, "Reservar Ahora button");
        logger.info("Clicking on Reservar button");
        displaySelectedCarInfo(index + 3);
        detailCarsButtonResults.get(index).click();
        return initCheckOutPage();
    }

    public List<WebElement> getCarsChoices(){
        return carsChoices;
    }

    public boolean vacancy(){
        PageUtils.waitUrlContains(driver, 10, "results", "Results url");
        logger.info("Results URL: " + "[" + driver.getCurrentUrl() + "]");
        try {
            PageUtils.waitForNoVacancy(driver, By.cssSelector("#main-content  div.alert__text > p:nth-child(4)"), 5, "[" + PRUEBA_CON_OTRAS_FECHAS + "] link");
        } catch (Exception ex){
            return true;
        }
        setResultSauceLabs(FAILED);
        return false;
    }

    public boolean processed(){
        try {
            PageUtils.waitForNoVacancy(driver, By.cssSelector("#main-content div.alert__text > p:nth-child(1)"), 5, "[" + NO_PUDIMOS_PROCESAR + "] link");
        } catch (Exception ex){
            return true;
        }
        setResultSauceLabs(FAILED);
        return false;
    }

}