package com.almundo.browser.automation.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public class PageUtils {

    public static Logger logger = Logger.getLogger(PageUtils.class);

    private static WebElement element = null;

    // TODO: we can probably define generic methods here.

    public static void clickOn(WebDriver driver, By elementToClick){
        driver.findElement(elementToClick).click();
    }

    public static void enterText(WebDriver driver, String text, By textBox){
        driver.findElement(textBox).sendKeys(text);
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

    public static void waitForLoad(WebDriver driver) {
        try {
            new WebDriverWait(driver, 60).until((ExpectedCondition<Boolean>) wd ->
                    ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));

        }
        catch (TimeoutException Ouch){
            driver.navigate().refresh();
        }
    }

    public static void waitElementForVisibility(WebDriver driver, WebElement element, int timeOutInSeconds, String message){
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.withMessage(message);
            wait.until(ExpectedConditions.visibilityOf(element));
        }catch (TimeoutException exception) {
            logger.error(message + " is not displayed");
            throw exception;
        }
    }

    public static void waitForVisibilityOfElementLocated(WebDriver driver, long timeout, By elementToLocate){
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(elementToLocate));
    }

}