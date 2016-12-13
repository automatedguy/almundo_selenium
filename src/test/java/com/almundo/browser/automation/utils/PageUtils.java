package com.almundo.browser.automation.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public class PageUtils {

    public static Logger logger = Logger.getLogger(PageUtils.class);

    // TODO: we can probably define generic methods here.

    public static void clickOn(WebDriver driver, By elementToClick){
        driver.findElement(elementToClick).click();
    }

    public boolean verifyElementPresent(WebElement element) throws InterruptedException {
        for (int second = 0; ; second++)
        {
            if (second >= 60) org.junit.Assert.fail();

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

    public static void waitElementClickable(WebDriver driver, WebElement element, int timeOutInSeconds, String message){
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.withMessage(message);
            wait.until(ExpectedConditions.elementToBeClickable(element));
        }catch (TimeoutException exception) {
            logger.error(message + " is not clickable");
            throw exception;
        }
    }

    public static void waitElementForClickable(WebDriver driver, By element, int timeOutInSeconds, String message){
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.withMessage(message);
            logger.info("Waiting for: [" + message + "]");
            wait.until(ExpectedConditions.elementToBeClickable(element));
        }catch (TimeoutException exception) {
            logger.error(message + " is not clickable");
            throw exception;
        }
    }

    public static void waitElementLocatedforVisibility(WebDriver driver, By elementToLocate, int timeOutInSeconds, String message){
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.withMessage(message);
            logger.info("Waiting for: [" + elementToLocate.toString() + "]");
            wait.until(ExpectedConditions.visibilityOfElementLocated(elementToLocate));
        }catch (TimeoutException exception) {
            logger.error(message + " is not displayed");
            throw exception;
        }
    }

    public static void waitForElementToBeClickcable(WebDriver driver, long timeout,By elementToClick){
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.elementToBeClickable(elementToClick));
    }

    public static void waitListContainResults(WebDriver driver, String element, int number){
        try {
            WebDriverWait wait = new WebDriverWait(driver,5);
            wait.withMessage("Incorrect number of results");
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(element), number));
        }catch (TimeoutException exception) {
            logger.error("Incorrect number of results");
            throw exception;
        }
    }

    public static void waitForElement(WebElement element, int timeToWaitInSeconds, int pollingIntervalInMilliSeconds) throws InterruptedException {
        logger.info("Waiting for WebElement: " + element);
        for (int i = 0; i < timeToWaitInSeconds; i++)
        {
            if (!element.isDisplayed())
            {
                continue;
            }
            Thread.sleep(pollingIntervalInMilliSeconds);
        }
    }

    public static void scrollToElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+element.getLocation().y+")");

    }


}