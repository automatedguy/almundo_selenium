package com.almundo.browser.automation.base;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.pages.BasePage.HotelesDataTrip;
import com.almundo.browser.automation.pages.BasePage.VueloHotelDataTrip;
import com.almundo.browser.automation.pages.BasePage.VuelosDataTrip;
import com.almundo.browser.automation.pages.LandingPage;
import com.almundo.browser.automation.pages.PaymentPage.*;
import com.almundo.browser.automation.pages.ResultsPage.*;
import com.almundo.browser.automation.utils.Constants;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.RetryAnalyzer;
import com.almundo.browser.automation.utils.SauceHelpers;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.UnexpectedException;


public class TestBaseSetup {

    public static Logger logger = Logger.getLogger( TestBaseSetup.class );

    public static WebDriver driver;

    public BasePage basePage = null;

    public static String baseURL = null;
    private static String os = null;
    private static String browser = null;
    private static String browserVersion = null;
    private static String osName = null;

    public int numPassengers;
    public static String countryPar;

    public static JSONObject jsonDataObject = null;
    public static JSONObject jsonPropertiesObject = null;
    public static JSONObject jsonCountryPropertyObject = null;

    public String osProperty = System.getProperty("os.name").toLowerCase();

    // Selenium URI -- static same for everyone.
    public static String seleniumURI = null;
    public static String buildTag = null;

    @Parameters({"env", "osType", "browserType", "browserTypeVersion", "country"})
    @BeforeSuite
    public void initializeTestBaseSetup(@Optional(Constants.PROD_URL) String env_url,
                                        @Optional() String osType,
                                        //@Optional("OS X 10.11") String osType,
                                        @Optional("firefox") String browserType,
                                        @Optional("latest") String browserTypeVersion,
                                        @Optional("ARGENTINA") String country) throws Exception {

        this.baseURL = env_url;
        this.os = osType;
        this.browser = browserType;
        this.browserVersion = browserTypeVersion;
        osName = System.getProperty("os.name");
        this.countryPar = country;

        try {
            if (os == null || browserVersion == null) {
                logger.info("OS: [" + osName + "]");
                logger.info("Browser: [" + browser + "]");
                logger.info("Environment: [" + baseURL + "]");

            } else {
                logger.info("OS: [" + os + "]");
                logger.info("Browser: [" + browser + " " + browserVersion + "]");
                logger.info("Environment: [" + baseURL + "]");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        jsonDataObject = JsonRead.getJsonFile(countryPar.toLowerCase() + "_data.json");
        jsonPropertiesObject = JsonRead.getJsonFile("countries_properties.json");
        jsonCountryPropertyObject = JsonRead.getJsonDataObject(jsonPropertiesObject, countryPar, "countries_properties.json");
    }


    @BeforeMethod
    public void setDriver() throws InterruptedException {
        System.out.println();
        logger.info("Starting @BeforeMethod...");
        try {
            if (os == null || browserVersion == null) {
                switch (browser) {
                    case "chrome":
                        if (osProperty.contains("windows")){
                            System.setProperty("webdriver.chrome.driver", Constants.RESOURCES_PATH + "chromedriver.exe");
                        } else {
                            System.setProperty("webdriver.chrome.driver", Constants.RESOURCES_PATH + "chromedriver");
                        }
                        driver = new ChromeDriver();
                        break;

                    case "firefox":
                        if (osProperty.contains("windows")){
                            System.setProperty("webdriver.gecko.driver", Constants.RESOURCES_PATH + "geckodriver.exe");
                        } else {
                            System.setProperty("webdriver.gecko.driver", Constants.RESOURCES_PATH + "geckodriver");
                        }

                        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                        capabilities.setCapability("marionette", true);
                        driver = new FirefoxDriver(capabilities);
                        break;

                    default:
                        throw new Exception("Browser [" + browser + "] not well defined. Allowed values are: 'firefox', 'chrome' or  'internet explorer'. WebDriver cannot be initialized!");
                }
            } else {

                String method = this.getClass().getName().substring(37) + " - " + countryPar;

                if(baseURL.contains("staging")){method = method + " - STG";}
                else{method = method + " - PROD";}

                logger.info("============ Method: " + method + " ============");
                this.initSauceLabsDriver(method);
            }


            logger.info("Maximizing Window...");
            driver.manage().window().maximize();

            logger.info("Navigating to baseURL: [" + baseURL + "]");
            driver.navigate().to(baseURL);

            LandingPage landingPage = initLandingPage();
            logger.info("Selecting country page: [" + countryPar + "]");
            basePage = landingPage.selectCountryPage(countryPar);

            logger.info("Finishing @BeforeMethod...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initSauceLabsDriver (String methodName) throws MalformedURLException, UnexpectedException {

        String USERNAME = "dafrubihit";
        String ACCESS_KEY = "b8a12f4f-f7de-4ed1-8f1d-f5a80bb1ee7a";
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
    }

    @AfterMethod
    public void tearDown() {
        try {
            driver.manage().deleteAllCookies();
            driver.quit();
        } catch (WebDriverException exception){
            System.out.println(exception.getMessage());
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }
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

    //################################################ Inits ################################################

    protected LandingPage initLandingPage () {
        return PageFactory.initElements(driver, LandingPage.class);
    }

    protected BasePage initBasePage () {
        return PageFactory.initElements(driver, BasePage.class);
    }

    protected HotelesDataTrip initHotelesDataTrip() {
        return PageFactory.initElements(driver, HotelesDataTrip.class);
    }

    protected VuelosDataTrip initVuelosDataTrip () {
        return PageFactory.initElements(driver, VuelosDataTrip.class);
    }

    protected VueloHotelDataTrip initVueloHotelDataTrip() {
        return PageFactory.initElements(driver, VueloHotelDataTrip.class);
    }

    protected PaymentPage initPaymentPage() {
        return PageFactory.initElements(driver, PaymentPage.class);
    }

    protected PassengerInfoSection initPassengerInfoSection() {
        return PageFactory.initElements(driver, PassengerInfoSection.class);
    }

    protected PaymentInfoSection initPaymentInfoSection() {
        return PageFactory.initElements(driver, PaymentInfoSection.class);
    }

    protected BillingInfoSection initBillingInfoSection() {
        return PageFactory.initElements(driver, BillingInfoSection.class);
    }

    protected ContactInfoSection initContactInfoSection() {
        return PageFactory.initElements(driver, ContactInfoSection.class);
    }

    protected FooterSection initFooterSection() {
        return PageFactory.initElements(driver, FooterSection.class);
    }

    protected HotelesResultsPage initHotelesResultsPage() {
        return PageFactory.initElements(driver, HotelesResultsPage.class);
    }

    protected HotelesDetailPage initHotelesDetailPage() {
        return PageFactory.initElements(driver, HotelesDetailPage.class);
    }

    protected VuelosResultsPage initVuelosResultsPage() {
        return PageFactory.initElements(driver, VuelosResultsPage.class);
    }

    protected VueloHotelResultsPage initVueloHotelResultsPage() {
        return PageFactory.initElements(driver, VueloHotelResultsPage.class);
    }

    protected VueloHotelDetailPage initVueloHotelDetailPage() {
        return PageFactory.initElements(driver, VueloHotelDetailPage.class);
    }

}
