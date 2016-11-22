package com.almundo.browser.automation.base;

import com.almundo.browser.automation.pages.LandingPage;
import com.almundo.browser.automation.utils.Constants;
import com.almundo.browser.automation.utils.RetryAnalyzer;
import com.almundo.browser.automation.utils.SauceHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.UnexpectedException;


public class TestBaseSetup {

    public WebDriver driver;

    public static String baseURL = null;
    private static String os = null;
    private static String browser = null;
    private static String browserVersion = null;

    public int numAdults;
    public int numChilds;
    public int numPassengers;
    public int numRooms;

    public String claseVuelo;


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

    public String countryPar;

    public LandingPage landingPage = new LandingPage(driver);

    public String osProperty = System.getProperty("os.name").toLowerCase();

    // Selenium URI -- static same for everyone.
    public static String seleniumURI = null;
    public static String buildTag = null;

    @Parameters({ "env" , "osType", "browserType", "browserTypeVersion", "country" , "adults", "childs", "rooms", "originAuto" , "originFull" ,
            "destinationAuto" , "destinationFull", "startDate", "endDate", "clase" })
    @BeforeClass
    public void initializeTestBaseSetup(@Optional(Constants.PROD_URL) String env_url,
                                        //@Optional() String osType,
                                        @Optional("Windows 10") String osType,
                                        @Optional("chrome") String browserType,
                                        @Optional("latest") String browserTypeVersion,
                                        @Optional("ARGENTINA") String country,
                                        int adults,
                                        int childs,
                                        int rooms,
                                        String originAuto,
                                        String originFull,
                                        String destinationAuto,
                                        String destinationFull,
                                        int startDate,
                                        int endDate, String clase) {

         /* Note: Parameters are initialized inside Before Class probably best option for now. */
        /* as @BeforeClass methods are invoked after test class instantiation and parameters for each test may differ */

        this.baseURL = env_url;
        this.os = osType;
        this.browser = browserType;
        this.browserVersion = browserTypeVersion;

        this.countryPar = country;

        this.numAdults = adults;
        this.numChilds= childs;
        this.numRooms = rooms;
        this.claseVuelo = clase;

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

        try {
            setDriver(browser);

        } catch (Exception e) {
            System.out.println("Error....." + e.getStackTrace());
        }

    }

    public void setDriver(String browser) throws InterruptedException {
        try {
            if (os == null || browserVersion == null) {
                switch (browser) {
                    case "chrome":
                        driver = initChromeDriver();
                        break;
                    case "firefox":
                        driver = initFirefoxDriver();
                        break;
                    default:
                        System.out.println("browser : " + browser
                                + " is invalid, Launching Firefox as browser of choice..");
                        driver = initFirefoxDriver();
                }
            } else {

                String method = this.getClass().getName().substring(37) + " - " + countryPar;

                System.out.println("Metodo: " + method);
                this.initSauceLabsDriver(method);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private WebDriver initChromeDriver() throws InterruptedException {
        System.out.println("Launching google chrome with new profile..");

        if (osProperty.contains("windows")){
            System.setProperty("webdriver.chrome.driver", Constants.DRIVERS_PATH + "chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", Constants.DRIVERS_PATH + "chromedriver");
        }

        return new ChromeDriver();
    }

    private WebDriver initFirefoxDriver() throws InterruptedException {
        System.out.println("Launching Firefox browser..");

        if (osProperty.contains("windows")){
            System.setProperty("webdriver.gecko.driver", Constants.DRIVERS_PATH + "geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", Constants.DRIVERS_PATH + "geckodriver");
        }

        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);

        return new FirefoxDriver(capabilities);
    }

    private WebDriver initSauceLabsDriver (String methodName) throws MalformedURLException, UnexpectedException {

        String USERNAME = "phidodrecr";
        String ACCESS_KEY = "ac3c7da6-5b2b-482e-b685-7e1a64e4374a";
        String url = "https://" + USERNAME + ":" + ACCESS_KEY + seleniumURI +"/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();

        // set desired capabilities to launch appropriate browser on Sauce
        capabilities.setCapability(CapabilityType.PLATFORM, os);
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        capabilities.setCapability(CapabilityType.VERSION, browserVersion);
        capabilities.setCapability("screenResolution", "1280x960");

        capabilities.setCapability("name", methodName);

        if (buildTag != null) {
            capabilities.setCapability("build", buildTag);
        }

        SauceHelpers.addSauceConnectTunnelId(capabilities);

        // Launch remote browser and set it as the current thread
        //driver = new RemoteWebDriver(new URL(url), capabilities);
        webDriver.set(new RemoteWebDriver(new URL(url), capabilities));

        // set current sessionId
        String id = ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
        sessionId.set(id);

        driver = this.getWebDriver();
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
        driver.navigate().to(baseURL);
        landingPage.selectCountryPage(driver, countryPar);
    }

    /* This is to run retry analyzer for all the suites / tests  */
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context) {

        //get the uri to send the commands to.
        seleniumURI = SauceHelpers.buildSauceUri();
        //If available add build tag. When running under Jenkins BUILD_TAG is automatically set.
        //You can set this manually on manual runs.
        buildTag = System.getenv("BUILD_TAG");

        for (ITestNGMethod method : context.getAllTestMethods()) {
            method.setRetryAnalyzer(new RetryAnalyzer());
        }
    }

    /**
     * ThreadLocal variable which contains the  {@link WebDriver} instance which is used to perform browser interactions with.
     */
    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

    /**
     * ThreadLocal variable which contains the Sauce Job Id.
     */
    private ThreadLocal<String> sessionId = new ThreadLocal<String>();

    /**
     * @return the {@link WebDriver} for the current thread
     */
    public WebDriver getWebDriver() {
        return webDriver.get();
    }

    public WebDriver getDriver() {
        return driver;
    }


}
