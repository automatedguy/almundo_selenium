package com.almundo.browser.automation.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

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

}