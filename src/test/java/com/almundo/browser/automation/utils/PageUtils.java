package com.almundo.browser.automation.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.almundo.browser.automation.base.TestBaseSetup.countryPar;
import static com.almundo.browser.automation.base.TestBaseSetup.runningRemote;

/**
 * Created by gabrielcespedes on 20/10/16.
 */
public class PageUtils {

    public static Logger logger = Logger.getLogger(PageUtils.class);

    //WAIT
    public static void waitElementForVisibility(WebDriver driver, WebElement element, int timeOutInSeconds, String message){
        try {
            logger.info("Waiting for: [" + message + "]");
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
            logger.info("Waiting for: [" + message + "]");
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.withMessage(message);
            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        }catch (TimeoutException exception) {
            logger.error("[" + message + "] is not displayed");
            setFailureSauceLabs(driver);
            throw exception;
        }
    }

    @SuppressWarnings("Duplicates")
    public static void waitElementForClickable(WebDriver driver, WebElement element, int timeOutInSeconds, String message){
        try {
            logger.info("Waiting for: [" + message + "]");
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.withMessage(message);
            wait.until(ExpectedConditions.elementToBeClickable(element));
        }catch (TimeoutException exception) {
            logger.error("[" + message + "] is not clickable");
            throw exception;
        }
    }

    @SuppressWarnings("Duplicates")
    public static void waitElementForClickable(WebDriver driver, By element, int timeOutInSeconds, String message){
        try {
            logger.info("Waiting for: [" + message + "]");
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.withMessage(message);
            wait.until(ExpectedConditions.elementToBeClickable(element));
        }catch (TimeoutException exception) {
            logger.error("[" + message + "] is not clickable");
            throw exception;
        }
    }

    @SuppressWarnings("Duplicates")
    public static void waitElementForClickableCatch(WebDriver driver, By element, int timeOutInSeconds, String message){
        try {
            logger.info("Waiting for: [" + message + "]");
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.withMessage(message);
            wait.until(ExpectedConditions.elementToBeClickable(element));
        }catch (Exception exception) {
            logger.info("[" + message + "] is not clickable");
        }
    }

    public static void waitUrlContains(WebDriver driver, int timeOutInSeconds, String text, String message){
        try {
            logger.info("Waiting for: [" + message + "]");
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

    public static void printSeparator(){
        System.out.println("=======================================================================");
    }

    public static void printStarsSeparator(){
        logger.info("**********************************************");
    }

    public static void scrollToTop(WebDriver driver){
        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,0)");
    }

    public static void scrollToBottom(WebDriver driver){
        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,0,500)");
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

        try {
            waitWithTryCatch(driver, "#account-header am-account-logged > div", "User Logged", 10);
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

    public static String getCountryCurrency(){
        String currency = "";
        switch(countryPar){
            case "ARGENTINA" : currency = "ARS";
                break;
            case "COLOMBIA" : currency = "COP";
                break;
            case "MEXICO" : currency = "MXN";
                break;
            case "BRASIL" : currency = "BRL";
                break;
            default:
                logger.error("No currency found for: " + countryPar);
                break;
        }

        return currency;
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

    public static void closeExpertsPopUp(WebDriver driver){
        String closeButtonLocator = "body > am-experts-contact > div > div.header > span";
        try {
            waitElementForClickableCatch(driver, By.cssSelector(closeButtonLocator), 10 ,"Experts Pop-Up");
            driver.findElement(By.cssSelector(closeButtonLocator)).click();
            logger.info("Closing [Contacta un Experto de Almundo] Pop-Up");
            waitImplicitly(1500);
        }catch(NoSuchElementException ouch){
            logger.info("The Experts Pop Up didn't showed up.");
        }
    }

    public static void setUrlParameter(WebDriver driver, String parameter){
        logger.info("Adding Parameter: " + parameter);
        String currentUrl = driver.getCurrentUrl();
        if(currentUrl.contains(parameter)){
            logger.info("Parameter: " + parameter + " is already set.");
        }
        else {
            if (currentUrl.contains(parameter.substring(0, parameter.length()-1))) {
                //TODO: come up with something.
                driver.navigate().to(currentUrl.replaceAll(parameter + "(0)", "1 $1"));
            }else {
                logger.info("Refreshing page URL: " +  currentUrl+parameter);
                driver.navigate().to(currentUrl+parameter);
            }
        }
    }

    @SuppressWarnings("Duplicates")
    public static WebElement waitWithTryCatch(WebDriver driver, String elementLocator, String elementDescription, int attempts){
        int tryNumber = 1;
        boolean elementShowedUp = false;
        WebElement webElement = null;
        logger.info("Waiting for [" + elementDescription + "]");
        do {
            logger.info(elementDescription + " not loaded completely yet, try N°: [" + tryNumber + "]");
            try {
                webElement =
                        driver.findElement(By.cssSelector(elementLocator));
                logger.info(elementDescription + " WebElement finally appeared.");
                elementShowedUp=true;
            } catch (NoSuchElementException ouch) {
                waitImplicitly(2000);
                tryNumber = tryNumber +1;
                if(tryNumber == attempts){logger.error("Waited so long for: [" + elementDescription + "]");}
            }
        }while((tryNumber <= attempts) && !elementShowedUp);
        return webElement;
    }

    @SuppressWarnings("Duplicates")
    public static List<WebElement> waitWithTryCatchList(WebDriver driver, String elementLocator, String elementDescription, int attempts){
        int tryNumber = 1;
        boolean elementShowedUp = false;
        List<WebElement> webElementList = null;
        logger.info("Waiting for " + elementDescription + " list");
        do {
            logger.info(elementDescription + " not loaded completely yet, try N°: [" + tryNumber + "]");
            try {
                webElementList =
                        driver.findElements(By.cssSelector(elementLocator));
                logger.info(elementDescription + " WebElement finally appeared.");
                elementShowedUp=true;
            } catch (NoSuchElementException ouch) {
                waitImplicitly(2000);
                tryNumber = tryNumber +1;
                if(tryNumber == attempts){logger.error("Waited so long for: [" + elementDescription + "]");}
            }
        }while((tryNumber <= attempts) && !elementShowedUp);
        return webElementList;
    }

    public static Select waitSelectContainsResults(Select select, String elementDescription ,int attempts, int expectedSize) {
        int tryNumber = 1;
        boolean elementShowedUp = false;
        do {
            logger.info("Waiting for select contains results.");
            waitImplicitly(1000);
            try {
                if (select.getOptions().size() >= expectedSize) {
                    elementShowedUp = true;
                }
            } catch (NoSuchElementException ouch) {
                tryNumber = tryNumber + 1;
                if (tryNumber == attempts) {
                    logger.error("Waited so long for: [" + elementDescription + "]");
                }
            }
        }while((tryNumber <= attempts) && !elementShowedUp);
        return select;
    }

    public static void logCookie(WebDriver driver){
        List cookiesList = new ArrayList(driver.manage().getCookies());

        boolean cookieFound =  false;
        int i = 0;
        int cookiesListSize = cookiesList.size();
        while(!cookieFound && i <= cookiesListSize){
            if(cookiesList.get(i).toString().contains("_ga=")){
                logger.info("Cookie Found: [" + cookiesList.get(i).toString() +"]");
                cookieFound =  true;
            }else {
                ++i;
            }
        }
    }

    public static String generateDate(int additionalDays){

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date currentDate =  new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        calendar.add(Calendar.DATE, additionalDays);

        Date selectedDate = calendar.getTime();

        logger.info("Selected date: [" + dateFormat.format(selectedDate).toString() + "]");
        return dateFormat.format(selectedDate).toString();
    }

}