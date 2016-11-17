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
    final String driverPath = "src/test/resources/";

    //final String chromeDriverPath = "/home/gabrielcespedes/idea-IC-162.2032.8/chrome/chromedriver";
    //final String firefoxDriverPath = "/home/gabrielcespedes/idea-IC-162.2032.8/gecko/geckodriver";

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

    public static String appUrl;
    public String countryPar;

    public String os = System.getProperty("os.name").toLowerCase();

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

    public WebDriver initChromeDriver() throws InterruptedException {
        System.out.println("Launching google chrome with new profile..");

        if (os.contains("windows")){
            System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver");
        }

        WebDriver driver = new ChromeDriver();
        return driver;
    }

    public WebDriver initFirefoxDriver() throws InterruptedException {
        System.out.println("Launching Firefox browser..");

        if (os.contains("windows")){
            System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver");
        }

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

        /* Note: Parameters are initialized inside Before Class probably best option for now. */
        /* as @BeforeClass methods are invoked after test class instantiation and parameters for each test may differ */

            this.appUrl = appURL;
            this.countryPar = country;

            this.numPassengers = passengers;

            this.originAutoComplete = originAuto;
            this.originFullText = originFull;
            this.originFullTextStr = String.format("//span[contains(.,'%s')]", originFullText);
            this.ORIGIN_FULL_PAR = By.xpath(originFullTextStr);

            this.destinationAutoComplete = destinationAuto;
            this.destinationFullText = destinationFull;
            this.destinationFullTextStr = String.format("//span[contains(.,'%s')]", destinationFullText);
            this.DESTINATION_FULL_PAR = By.xpath(destinationFullTextStr);

            this.departureDate = startDate;
            this.returnDate = endDate;
    }

    public WebDriver selectCountry(WebDriver driver, String selectedCountry){
        landingPage.selectCountryPage(driver, selectedCountry);
        return driver;
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @BeforeMethod
    public void beforeMethod(){
        driver.manage().window().maximize();
        System.out.println("Running Before Method");
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
