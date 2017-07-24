package com.almundo.browser.automation.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.security.SecureRandom;
import java.util.*;

import static com.almundo.browser.automation.base.TestBaseSetup.runningRemote;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public class PageUtils {

    public static Logger logger = Logger.getLogger(PageUtils.class);

    //WAIT
    public static void waitElementForVisibility(WebDriver driver, WebElement element, int timeOutInSeconds, String message){
        try {
            logger.info("Waiting for" + message);
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.withMessage(message);
            wait.until(ExpectedConditions.visibilityOf(element));
            logger.info(message + " is displayed");
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
            logger.error("Login modal is not displayed");
            setFailureSauceLabs(driver);
        }
    }

    public static void waitElementForVisibility(WebDriver driver, By element, int timeOutInSeconds, String message){
        try {
            logger.info("Waiting for: " + message);
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.withMessage(message);
            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        }catch (TimeoutException exception) {
            logger.error(message + " is not displayed");
            setFailureSauceLabs(driver);
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
            setFailureSauceLabs(driver);
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
        setFailureSauceLabs(driver);
    }

    public static void waitListContainResults(WebDriver driver, String cssSelectorName, int number){
        try {
            WebDriverWait wait = new WebDriverWait(driver,30);
            wait.withMessage("Incorrect number of results");
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(cssSelectorName), number));
        }catch (TimeoutException exception) {
            logger.error("Incorrect number of results");
            throw exception;
        }
    }

    public static void waitAttributeContains(WebDriver driver, String element, String attribute, String value){
        try {
            WebDriverWait wait = new WebDriverWait(driver,5);
            wait.withMessage("Attribute [" + attribute +  "] does not contain [" + value + "]");
            wait.until(ExpectedConditions.attributeContains(By.cssSelector(element), attribute, value));
        }catch (TimeoutException exception) {
            logger.error("Attribute [" + attribute +  "] does not contain [" + value + "]");
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
        logger.info("Switching to child tab.");
        PageUtils.waitImplicitly(2000);
        ArrayList<String> newTab = new ArrayList(driver.getWindowHandles());
        driver.switchTo().window(newTab.get(1));
        logger.info("Child Tab URL: " + "[" +  driver.getCurrentUrl() + "]");
    }

    public static void switchToParentTab(WebDriver driver){
        logger.info("Switching back to parent tab.");
        PageUtils.waitImplicitly(2000);
        ArrayList<String> newTab = new ArrayList(driver.getWindowHandles());
        driver.switchTo().window(newTab.get(0));
        logger.info("Parent Tab URL: " + "[" +  driver.getCurrentUrl() + "]");
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

    public static boolean equalLists(List<String> actualList, List<String> expectedList, WebDriver driver) {
        boolean equalLists = true;

        for (int i = 0; i < expectedList.size(); i++) {
            if (actualList.contains(expectedList.get(i))) {
                logger.info("Option [" + expectedList.get(i) + "] is displayed");
            }else {
                logger.error("Option [" + expectedList.get(i) + "] is not displayed");
                setFailureSauceLabs(driver);
                equalLists = false;
            }
        }
        if(actualList.size()!=expectedList.size()) {
            logger.error("Lists have different size");
            setFailureSauceLabs(driver);
            equalLists = false;
        }
        return equalLists;
    }

    public static boolean isElementPresent(WebElement webElement) {
        try {
            if(webElement.isDisplayed()){
                return true;
            } else {return false;}
        } catch (NoSuchElementException e) {
            return false;
        } catch (Exception e) {
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
            PageUtils.waitUrlContains(driver, 15, "results", "results page");
            Thread.sleep(9000);
            logger.info("results page loaded, analyzing current locators mapping...");
            String currentUrl = driver.getCurrentUrl();
            if(currentUrl.contains("card=true")){
                layout = "card";
            }else{
                layout = "normal";}
        } catch(Exception time) {
            logger.error("results page was not displayed.");
            setFailureSauceLabs(driver);
        } return layout;
    }

    public static String formatInfo(String info){
        return info.replaceAll("[\\n\\r]+", " - ");
    }

    public static void waitForUserNameDisplayed(WebDriver driver){
        PageUtils.waitImplicitly(10000);
        try {
            PageUtils.waitElementForVisibility(driver, By.cssSelector("#account-header > am-account-logged div:nth-child(1) > span"), 5, "User Name");
        }
        catch(Exception ex){
            logger.error("The Login failed :( ");
        }
    }

    private static void setFailureSauceLabs(WebDriver driver){
        if (runningRemote) {
            logger.info("Reporting Failure to Saucelabs :( ");
            try {
                ((JavascriptExecutor) driver).executeScript("sauce:job-result=failed");
                logger.error("Test Failed!");
            }
            catch(Exception ex){
                logger.error("Communication with Saucelabs went wrong :( ");
            }
        }
    }

    public static boolean userNameOk(String expectedUserName , String currentUserName){
        logger.info("Validating user name is displayed: [" + expectedUserName + "]");
        if(currentUserName.contains(expectedUserName)){
            return true;
        }else {
            return false;
        }
    }

    public static boolean isElementClickable(WebDriver driver, WebElement webElement, int timeOutInSeconds, String message){
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.withMessage(message);
            wait.until(ExpectedConditions.elementToBeClickable(webElement));
            return true;
        }catch (TimeoutException exception) {
            logger.error(message + " is not clickable");
            setFailureSauceLabs(driver);
            return false;
        }
    }

    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}