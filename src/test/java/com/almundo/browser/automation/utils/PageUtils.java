package com.almundo.browser.automation.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public class PageUtils {

    public static Logger logger = Logger.getLogger(PageUtils.class);

    // TODO: we can probably define generic methods here.

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

    public static void waitElementForVisibility(WebDriver driver, By element, int timeOutInSeconds, String message){
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.withMessage(message);
            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        }catch (TimeoutException exception) {
            logger.error(message + " is not displayed");
            throw exception;
        }
    }

    public static void waitElementForClickable(WebDriver driver, WebElement element, int timeOutInSeconds, String message){
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
            wait.until(ExpectedConditions.elementToBeClickable(element));
        }catch (TimeoutException exception) {
            logger.error(message + " is not clickable");
            throw exception;
        }
    }

    public static void waitUrlContains(WebDriver driver, int timeOutInSeconds, String text, String message){
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.withMessage(message);
            wait.until(ExpectedConditions.urlContains(text));
        }catch (TimeoutException exception) {
            logger.error(message + " is not displayed");
            throw exception;
        }
    }

    public static void waitForNoVacancy(WebDriver driver, By elementToLocate, int timeOutInSeconds, String message){
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.withMessage(message);
            wait.until(ExpectedConditions.visibilityOfElementLocated(elementToLocate));
        }catch (TimeoutException exception) {
            throw exception;
        }
        logger.error(message + " is displayed");
    }

    public static void waitListContainResults(WebDriver driver, String element, int number){
        try {
            WebDriverWait wait = new WebDriverWait(driver,30);
            wait.withMessage("Incorrect number of results");
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(element), number));
        }catch (TimeoutException exception) {
            logger.error("Incorrect number of results");
            throw exception;
        }
    }

    public static void scrollToElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+element.getLocation().y+")");

    }

    public static void scrollToCoordinate(WebDriver driver, int coordinate) {
        ((JavascriptExecutor)driver).executeScript("window.scrollBy(0," + coordinate + ")");
    }

//    public static void setFocusOnChildWindow(WebDriver driver){
//        Set<String> set1=driver.getWindowHandles();
//        Iterator<String> win1=set1.iterator();
//        String parent=win1.next();
//        String child=win1.next();
//        driver.switchTo().window(child);
//    }

    public static void setFocusOnWindow(WebDriver driver, String window){
        if (window.equals("child")) {
            Set<String> set = driver.getWindowHandles();
            Iterator<String> win1 = set.iterator();
            String parent = win1.next();
            String child = win1.next();
            driver.switchTo().window(child);
        } else {
            Set<String> set= driver.getWindowHandles();
            Iterator<String> win1 = set.iterator();
            String parent = win1.next();
            driver.switchTo().window(parent);
        }
    }

    public static void waitImplicitly(int miliSeconds){
        try {
            Thread.sleep(miliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean equalLists(List<String> actualList, List<String> expectedList) {
        boolean equalList = true;

        for (int i = 0; i < expectedList.size(); i++) {
            if (actualList.contains(expectedList.get(i))) {
                logger.info("Option [" + expectedList.get(i) + "] is displayed");
            }else {
                logger.error("Option [" + expectedList.get(i) + "] is not displayed");
                equalList = false;
            }
        }
       return equalList;
    }

}