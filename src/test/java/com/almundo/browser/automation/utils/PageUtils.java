package com.almundo.browser.automation.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public class PageUtils {

    public static Logger logger = Logger.getLogger(PageUtils.class);

    //WAIT
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

    public static void waitLoginPopup(WebDriver driver, WebElement element, int timeOutInSeconds, String message){
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.withMessage(message);
            wait.until(ExpectedConditions.visibilityOf(element));
        }catch (TimeoutException exception) {
            logger.warn("Login modal is not displayed");
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

    public static void waitImplicitly(int miliSeconds){
        try {
            Thread.sleep(miliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //SCROLL
    public static void scrollToElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+element.getLocation().y+")");

    }

    public static void scrollToCoordinate(WebDriver driver, int coordinate) {
        ((JavascriptExecutor)driver).executeScript("window.scrollBy(0," + coordinate + ")");
    }

    //BROWSER TAB
    public static void switchToNewTab(WebDriver driver){
        logger.info("Switching to new tab");
        PageUtils.waitImplicitly(2000);
        ArrayList<String> newTab = new ArrayList(driver.getWindowHandles());
        driver.switchTo().window(newTab.get(1));
        logger.info("Detail URL: " + "[" +  driver.getCurrentUrl() + "]");
    }

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

    public static boolean equalLists(List<String> actualList, List<String> expectedList) {
        boolean equalLists = true;

        for (int i = 0; i < expectedList.size(); i++) {
            if (actualList.contains(expectedList.get(i))) {
                logger.info("Option [" + expectedList.get(i) + "] is displayed");
            }else {
                logger.error("Option [" + expectedList.get(i) + "] is not displayed");
                equalLists = false;
            }
        }
        if(actualList.size()!=expectedList.size()) {
            logger.error("Lists have different size");
            equalLists = false;
        }
        return equalLists;
    }

    public static boolean isElementPresent(WebElement webElement) {
        try {
            webElement.isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
        catch (Exception e)
        {
            return false;
        }

    }

    static final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public static String randomString(int len){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    /*** Analyze current results page URL***/
    public static String getHotelsResultsPageLayout(WebDriver driver) {
        String layout = null;
        try{
            PageUtils.waitUrlContains(driver, 30, "results", "results page");
            logger.info("results page loaded, analyzing current locators mapping...");
            String currentUrl = driver.getCurrentUrl();
            if(currentUrl.contains("card=true")){
                layout = "card";
            }else{
                layout = "normal";}
        } catch(Exception time) {
            logger.error("results page was not displayed.");
        } return layout;
    }

    public static String formatInfo(String info){
        return info.replaceAll("[\\n\\r]+", " - ");
    }
}