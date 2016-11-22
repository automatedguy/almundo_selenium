package com.almundo.browser.automation.base;

import com.almundo.browser.automation.locators.flows.BaseFlowMap;
import com.almundo.browser.automation.locators.flows.HotelFlowMap;
import com.almundo.browser.automation.locators.flows.VueloFlowMap;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class PageBaseSetup {

    protected WebDriver driver;

    public PageBaseSetup clickOn(WebDriver driver, By elementToClick){
        driver.findElement(elementToClick).click();
        return this;
    }

    public PageBaseSetup enterText(WebDriver driver, String text, By textBox){
        driver.findElement(textBox).sendKeys(text);
        return this;
    }

    public PageBaseSetup waitForVisibilityOfElementLocated(WebDriver driver, long timeout, By elementToLocate){
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(elementToLocate));
        return this;
    }

    public PageBaseSetup waitForElementToBeClickcable(WebDriver driver, long timeout,By elementToClick){
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.elementToBeClickable(elementToClick));
        return this;
    }

    public PageBaseSetup waitForElement(WebElement element,
                               int timeToWaitInSeconds, int pollingIntervalInMilliSeconds) throws InterruptedException {
        System.out.println("NOW TRYING TO FIND WEB ELEMENT....LOOP");
        for (int i = 0; i < timeToWaitInSeconds; i++)
        {
            if (!element.isDisplayed())
            {
                continue;
            }
            Thread.sleep(pollingIntervalInMilliSeconds);
        }
        return this;
    }

    public boolean verifyElementPresent(WebElement element) throws InterruptedException {
        for (int second = 0; ; second++)
        {
            if (second >= 60) Assert.fail();

            try
            {
                if (element != null)
                {
                    break;
                }
            }
            catch (Exception someException)
            {
                return false;
            }
            Thread.sleep(100);
        }
        return true;
    }

    public PageBaseSetup selectFromAutoCompleteSuggestions(WebDriver driver, By autoComplete){
        driver.findElement(autoComplete).click();
        return this;
    }

    public PageBaseSetup selectDateFromCalendar(WebDriver driver, By idCalendar, int daysAhead){

        driver.findElement(idCalendar).click();
        List<WebElement> availableDates = driver.findElements(BaseFlowMap.AVAILABLE_DATES_CAL.getBy());
        int totalAvailableDates = availableDates.size();

        if(totalAvailableDates >= daysAhead){
            System.out.println("Fecha Seleccionada: " + availableDates.get(daysAhead-1).getText());
            availableDates.get(daysAhead-1).click();
        }
        else{
            daysAhead = daysAhead - totalAvailableDates;
            driver.findElement(BaseFlowMap.CALENDAR_NEXT_CAL.getBy()).click();
            List<WebElement> availableDatesNextCal = driver.findElements(BaseFlowMap.AVAILABLE_DATES_CAL.getBy());
            System.out.println("Fecha Seleccionada: " + availableDatesNextCal.get(daysAhead-1).getText());
            availableDatesNextCal.get(daysAhead-1).click();
        }
        return this;
    }

    public int selectPassenger(WebDriver driver, int adults, int childs) {
        driver.findElement(BaseFlowMap.PERSONAS_TXT.getBy()).click();

        if (adults>1){
            for(int i=1; i<adults; i++) {
                System.out.println("Adding 1 adult");
                driver.findElement(VueloFlowMap.ADD_ADULT_BTN.getBy()).click();
            }
        }

        if (childs>0){
            for(int i=0; i<childs; i++) {
                System.out.println("Adding 1 child");
                driver.findElement(VueloFlowMap.ADD_CHILD_BTN.getBy()).click();
            }
        }

        driver.findElement(BaseFlowMap.LISTO_BTN.getBy()).click();

        return adults + childs;
    }

    public int selectPassenger(WebDriver driver, int adults, int childs, int rooms) {
        driver.findElement(BaseFlowMap.PERSONAS_TXT.getBy()).click();

        if (adults>2){
            for(int i=1; i<adults; i++) {
                System.out.println("Adding 1 adult");
                driver.findElement(HotelFlowMap.ADD_ADULT_BTN.getBy()).click();
            }
        }

        if (childs>0){
            for(int i=0; i<childs; i++) {
                System.out.println("Adding 1 child");
                driver.findElement(HotelFlowMap.ADD_CHILD_BTN.getBy()).click();
            }
        }

        driver.findElement(BaseFlowMap.LISTO_BTN.getBy()).click();

        return adults + childs;
    }

    public PageBaseSetup moveToElement(WebDriver driver, By elementToLocate){
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(elementToLocate));
        return this;
    }

    public boolean nothingFound(WebDriver driver){
        if(!driver.findElements(By.linkText("Ver listado de sucursales")).isEmpty()){
            System.out.println("No Results found - acercate a nuestras sucursales");
            return true;
        }
        else{
            return false;
        }
    }

    public boolean noVacancy(WebDriver driver){
        try {
            By noVacancyMsg = By.xpath("//span[contains(.,'Lo sentimos. No encontramos disponibilidad para tu búsqueda')]");
            waitForVisibilityOfElementLocated(driver, 5, noVacancyMsg);

        } catch (TimeoutException timeOut){
                return false;
        }
        return true;
    }
}
