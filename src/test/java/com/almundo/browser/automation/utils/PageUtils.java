package com.almundo.browser.automation.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public class PageUtils {
    private static WebElement element = null;

    public static WebElement waitForVisibilityOfElementLocated(WebDriver driver, long timeout, By elementToLocate){
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(elementToLocate));
        return element;
    }

    public static WebElement waitForElementToBeClickcable(WebDriver driver, long timeout,By elementToClick){
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.elementToBeClickable(elementToClick));
        return element;
    }

    public static WebElement moveToElement(WebDriver driver,  By elementToLocate){
        element = driver.findElement(elementToLocate);
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        return element;
    }

    public static WebElement setFocusOnChildWindow(WebDriver driver){
        Set<String> set1=driver.getWindowHandles();
        Iterator<String> win1=set1.iterator();
        String parent=win1.next();
        String child=win1.next();
        driver.switchTo().window(child);
        return element;
    }

    public static void waitForSaucePicture(long waiting) throws InterruptedException {
        Thread.sleep(waiting);
    }

    public static void assertElementIsPresent(WebDriver driver, By assertedElement, String textToCompare){
        WebElement elementToAssert = driver.findElement(assertedElement);

        System.out.println("Text to assert: " + elementToAssert.getText());

        try {
            Assert.assertTrue(elementToAssert.getText() != null);
        }
        catch (AssertionError uhOh) {
            System.out.println("Object: " + elementToAssert + " - is not there");
            System.out.println("StackTrace: "+ uhOh.getStackTrace());
            Assert.fail();
        }

        try {
            Assert.assertEquals(elementToAssert.getText(), textToCompare);
        }
        catch (AssertionError uhOh) {
            System.out.println("Object text: " + elementToAssert.getText() + " - label is wrong or changed.");
            System.out.println("StackTrace: "+ uhOh.getStackTrace());
            Assert.fail();
        }
    }

    public static WebElement selectFromAutoCompleteSuggestions(WebDriver driver, By city){
        WebElement selectedCity = driver.findElement(city);
        selectedCity.click();
        return element;
    }

    public static void selectDateFromCalendar(WebDriver driver, String idCalendar, int daysAhead) throws InterruptedException {


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
            System.out.println("La Fecha es aceptable");

            driver.findElement(By.id(idCalendar)).click();
            PageUtils.waitForSaucePicture(3000);

            String string = String.format("//a[text()='%s']", actualDayDate );
            driver.findElement(By.xpath(string)).click();

            PageUtils.waitForSaucePicture(3000);

        }
        else {
            System.out.println("La Fecha es NO aceptable");
            actualDayDate = actualDayDate - maxDaysCurrentMonth;
            System.out.println("La Fecha nueva es del proximo mes es: " + actualDayDate);

            driver.findElement(By.id(idCalendar)).click();
            driver.findElement(By.xpath("//span[@class='ui-icon ui-icon-circle-triangle-e']")).click();
            String string = String.format("//a[text()='%s']", actualDayDate );
            driver.findElement(By.xpath(string)).click();
        }
    }

    public static boolean nothingFound(WebDriver driver){
        if(!driver.findElements(By.linkText("Ver listado de sucursales")).isEmpty()){
            System.out.println("No Results found - acercate a nuestras sucursales");
            return true;
        }
        else{
            return false;
        }
    }

    public static void waitForLoad(WebDriver driver) {
        try {
            new WebDriverWait(driver, 60).until((ExpectedCondition<Boolean>) wd ->
                    ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));

        }
        catch (TimeoutException Ouch){
            driver.navigate().refresh();
        }
    }

}