package com.almundo.browser.automation.pages.ResultsPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

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

    public CheckOutPage clickReservarAhoraBtn(int index) {
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

}