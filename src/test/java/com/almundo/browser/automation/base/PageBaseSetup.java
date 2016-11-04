package com.almundo.browser.automation.base;

import com.almundo.browser.automation.locators.flows.BaseFlowMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    public PageBaseSetup selectFromAutoCompleteSuggestions(WebDriver driver, By autoComplete){
        driver.findElement(autoComplete).click();
        return this;
    }

    public PageBaseSetup selectDateFromCalendar(WebDriver driver, String idCalendar, int daysAhead) throws InterruptedException {
        Calendar c = Calendar.getInstance();
        int maxDaysCurrentMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);

        DateFormat dateFormat = new SimpleDateFormat("dd");
        Calendar cal = Calendar.getInstance();
        int currentDateDay = Integer.parseInt(dateFormat.format(cal.getTime()));

        int actualDayDate = currentDateDay + daysAhead;

        System.out.println("Cantidad de dias en el mes actual... " + maxDaysCurrentMonth);
        System.out.println("Fecha del dia de hoy ................ " + currentDateDay);
        System.out.println("Fecha del dia a marcar............... " + actualDayDate);

        if(actualDayDate <= maxDaysCurrentMonth){
            driver.findElement(By.id(idCalendar)).click();
            waitForVisibilityOfElementLocated(driver, 10, BaseFlowMap.CALENDAR_CAL.getBy());
            String string = String.format("//a[text()='%s']", actualDayDate );
            driver.findElement(By.xpath(string)).click();
        }
        else {
            actualDayDate = actualDayDate - maxDaysCurrentMonth;
            driver.findElement(By.id(idCalendar)).click();
            driver.findElement(BaseFlowMap.CALENDAR_NEXT_CAL.getBy()).click();
            String string = String.format("//a[text()='%s']", actualDayDate );
            driver.findElement(By.xpath(string)).click();
        }
        return this;
    }

    public PageBaseSetup moveToElement(WebDriver driver, By elementToLocate){
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(elementToLocate));
        return this;
    }
}
