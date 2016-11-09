package com.almundo.browser.automation.base;

import com.almundo.browser.automation.pages.LandingPage;
import com.almundo.browser.automation.utils.RetryAnalyzer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.*;


public class TestBaseSetup {

    public WebDriver driver;
    final String chromeDriverPath = "/home/gabrielcespedes/idea-IC-162.2032.8/chrome/chromedriver";
    final String firefoxDriverPath = "/home/gabrielcespedes/idea-IC-162.2032.8/gecko/geckodriver";

    public LandingPage landingPage = new LandingPage(driver);

    public int numPassengers;

    public String originAutoComplete;
    public String originFullText;
    public String originFullTextStr;
    public By ORIGIN_FULL_PAR;

    public String destinationAutoComplete;
    public String destinationFullText;
    public String destinationFullTextStr;
    public By DESTINATION_FULL_PAR;

    public int departureDate;
    public int returnDate;

    public String appUrl;
    public String countryPar;

    /* TODO: We need to move parameters initizalization into the contructor below */
 /*   public TestBaseSetup(String appURL, String country, int numPassengers, String originAutoComplete, By ORIGIN_FULL_PAR,
                         String destinationAutoComplete, By DESTINATION_FULL_PAR, int departureDate, int returnDate){

        this.appUrl = appURL;
        this.countryPar = country;
        this.numPassengers = ;
        this.originAutoComplete = ;
        this.ORIGIN_FULL_PAR = ;
        this.destinationAutoComplete = ;
        this.DESTINATION_FULL_PAR = ;
        this.departureDate = ;
        this.returnDate = ;

    }*/

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(String browserType) throws InterruptedException {
        switch (browserType) {
            case "chrome":
                driver = initChromeDriver();
                break;
            case "firefox":
                driver = initFirefoxDriver();
                break;
            default:
                System.out.println("browser : " + browserType
                        + " is invalid, Launching Firefox as browser of choice..");
                driver = initFirefoxDriver();
        }
    }

    public WebDriver selectCountry(WebDriver driver, String selectedCountry){
        landingPage.selectCountryPage(driver, selectedCountry);
        return driver;
    }

    public WebDriver initChromeDriver() throws InterruptedException {
        System.out.println("Launching google chrome with new profile..");
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        WebDriver driver = new ChromeDriver();
        return driver;
    }

    public WebDriver initFirefoxDriver() throws InterruptedException {
        System.out.println("Launching Firefox browser..");
        System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);
        WebDriver driver = new FirefoxDriver(capabilities);
        return driver;
    }

    @Parameters({ "browserType", "appURL" , "country" , "passengers" ,
    "originAuto" , "originFull" , "destinationAuto" , "destinationFull",
    "startDate", "endDate" })
    @BeforeClass
    public void initializeTestBaseSetup(String browserType, String appURL, String country, int passengers,
                                        String originAuto, String originFull,
                                        String destinationAuto , String destinationFull,
                                        int startDate, int endDate) {
            try {
                setDriver(browserType);

            } catch (Exception e) {
                System.out.println("Error....." + e.getStackTrace());
            }

        /* TODO Initialize Global Test Parameters */
        /* TODO We need to move parameters initizalization into the TestBaseSetup constructor */

            numPassengers = passengers;

            originAutoComplete = originAuto;
            originFullText = originFull;
            originFullTextStr = String.format("//span[contains(.,'%s')]", originFullText);
            ORIGIN_FULL_PAR = By.xpath(originFullTextStr);

            destinationAutoComplete = destinationAuto;
            destinationFullText = destinationFull;
            destinationFullTextStr = String.format("//span[contains(.,'%s')]", destinationFullText);
            DESTINATION_FULL_PAR = By.xpath(destinationFullTextStr);

            departureDate = startDate;
            returnDate = endDate;

            appUrl = appURL;
            countryPar = country;

    }

    /* This is not generating null session */
    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @BeforeMethod
    public void beforeMethod(){
        driver.manage().window().maximize();
        System.out.println("Before Methodd NOWWWWWWW");
        driver.navigate().to(appUrl);
        selectCountry(driver, countryPar);
    }

    /* This is to run retry analyzer for all the suites / tests  */
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context) {
        for (ITestNGMethod method : context.getAllTestMethods()) {
            method.setRetryAnalyzer(new RetryAnalyzer());
        }
    }
}
