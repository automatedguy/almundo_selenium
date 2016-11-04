package com.almundo.browser.automation.base;

import com.almundo.browser.automation.pages.LandingPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


public class TestBaseSetup {

    public WebDriver driver;
    static String chromeDriverPath = "/home/gabrielcespedes/idea-IC-162.2032.8/chrome/chromedriver";
    static String firefoxDriverPath = "/home/gabrielcespedes/idea-IC-162.2032.8/gecko/geckodriver";

    public LandingPage landingPage = new LandingPage(driver);

    public String numPassengers;

    public String originAutoComplete;
    public String originFullText;
    public static String originFullTextStr;

    public String destinationAutoComplete;
    public String destinationFullText;
    public static String destinationFullTextStr;

    public int departureDate;
    public int returnDate;

    public static String countryPar;

    public WebDriver getDriver() {
        return driver;
    }

    private void setDriver(String browserType, String appURL, String country) throws InterruptedException {
        switch (browserType) {
            case "chrome":
                driver = initChromeDriver(appURL, country);
                break;
            case "firefox":
                driver = initFirefoxDriver(appURL, country);
                break;
            default:
                System.out.println("browser : " + browserType
                        + " is invalid, Launching Firefox as browser of choice..");
                driver = initFirefoxDriver(appURL, country);
        }
    }

    public WebDriver selectCountry(WebDriver driver, String selectedCountry){
        landingPage.selectCountryPage(driver, selectedCountry);
        return driver;
    }

    private WebDriver initChromeDriver(String appURL, String country) throws InterruptedException {
        System.out.println("Launching google chrome with new profile..");
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        selectCountry(driver, country);
        return driver;
    }

    private WebDriver initFirefoxDriver(String appURL, String country) throws InterruptedException {
        System.out.println("Launching Firefox browser..");
        System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);
        WebDriver driver = new FirefoxDriver(capabilities);
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        selectCountry(driver, country);
        return driver;
    }

    @Parameters({ "browserType", "appURL" , "country" , "passengers" ,
    "originAuto" , "originFull" , "destinationAuto" , "destinationFull",
    "startDate", "endDate" })
    @BeforeClass
    public void initializeTestBaseSetup(String browserType, String appURL, String country, String passengers,
                                        String originAuto, String originFull,
                                        String destinationAuto , String destinationFull,
                                        int startDate, int endDate) {
        try {
            setDriver(browserType, appURL, country);

        } catch (Exception e) {
            System.out.println("Error....." + e.getStackTrace());
        }

        /* Initialize Global Test Parameters */
        numPassengers = passengers;

        originAutoComplete = originAuto;
        originFullText = originFull;
        originFullTextStr = String.format("//span[contains(.,'%s')]", originFullText );

        destinationAutoComplete = destinationAuto;
        destinationFullText = destinationFull ;
        destinationFullTextStr = String.format("//span[contains(.,'%s')]", destinationFullText );

        departureDate = startDate;
        returnDate = endDate;

        countryPar = country;
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @AfterMethod
    public void quit(){
        driver.quit();
    }
}
