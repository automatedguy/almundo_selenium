package com.almundo.browser.automation.base;

import com.almundo.browser.automation.pages.BasePage.*;
import com.almundo.browser.automation.pages.CheckOutPage.*;
import com.almundo.browser.automation.pages.CheckOutPageV3.*;
import com.almundo.browser.automation.pages.LandingPage;
import com.almundo.browser.automation.pages.PromoPage;
import com.almundo.browser.automation.pages.ResultsPage.*;
import com.almundo.browser.automation.utils.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;


public class TestBaseSetup {

    public static Logger logger = Logger.getLogger( TestBaseSetup.class );
    public static WebDriver driver;

    public BasePage basePage = null;

    public static String baseURL = null;
    private static String os = null;
    private static String browser = null;
    private static String browserVersion = null;
    public static String osName = System.getProperty("os.name");
    public static Boolean landingEnabled = Boolean.TRUE;
    public static String cartId = null;
    public static String cartIdICBC = null;

    public static String countryPar;

    public static JSONObject jsonDataObject = null;
    public static JSONObject jsonPropertiesObject = null;
    public static JSONObject jsonCountryPropertyObject = null;

    // Selenium URI -- static same for everyone.
    public static String seleniumURI = null;

    @Parameters({"env", "osType", "browserType", "browserTypeVersion", "country", "landing", "cart_id", "cart_id_icbc"})
    @BeforeSuite
    public void initializeTestBaseSetup(@Optional(Constants.PROD_URL) String env_url,
                                        @Optional() String osType,
//                                        @Optional("OS X 10.11") String osType,
//                                        @Optional("Windows 10") String osType,
                                        @Optional("chrome") String browserType,
                                        @Optional("latest") String browserTypeVersion,
                                        @Optional("ARGENTINA") String country,
                                        @Optional("False") Boolean landing,
                                        @Optional("58c7053ee4b0397369fe6150") String cart_id,
                                        @Optional("58c70563e4b0fc66a4949661") String cart_id_icbc) {

        this.baseURL = env_url;
        this.os = osType;
        this.browser = browserType;
        this.browserVersion = browserTypeVersion;
        this.countryPar = country;
        this.landingEnabled = landing;
        this.cartId = cart_id;
        this.cartIdICBC = cart_id_icbc;

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
    public void setDriver(Method methodName) {
        //System.out.println();
        //logger.info("Starting @BeforeMethod...");
        try {
            if (os == null || browserVersion == null) {
                switch (browser) {
                    case "chrome":
                        if (osName.toLowerCase().contains("windows")){
                            System.setProperty("webdriver.chrome.driver", Constants.RESOURCES_PATH + "chromedriver.exe");
                        } else {
                            System.setProperty("webdriver.chrome.driver", Constants.RESOURCES_PATH + "chromedriver");
                        }
                        DesiredCapabilities capability = DesiredCapabilities.chrome();
                        capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                        driver = new ChromeDriver(capability);
                        break;

                    case "firefox":
                        if (osName.toLowerCase().contains("windows")){
                            System.setProperty("webdriver.gecko.driver", Constants.RESOURCES_PATH + "geckodriver.exe");
                        } else {
                            System.setProperty("webdriver.gecko.driver", Constants.RESOURCES_PATH + "geckodriver");
                        }
                        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                        capabilities.setCapability("marionette", true);
                        driver = new FirefoxDriver(capabilities);
                        break;

                    case "phantomjs":
                        if (osName.toLowerCase().contains("windows")){
                            System.setProperty("phantomjs.binary.path", Constants.RESOURCES_PATH + "phantomjs.exe");
                        } else {
                            System.setProperty("phantomjs.binary.path", Constants.RESOURCES_PATH + "phantomjs");
                        }
                        DesiredCapabilities sCaps = new DesiredCapabilities();
                        sCaps.setJavascriptEnabled(true);
                        sCaps.setCapability("takesScreenshot", false);
                        sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] {
                                "--web-security=false",
                                "--ssl-protocol=any",
                                "--ignore-ssl-errors=true",
                                "--webdriver-loglevel=NONE"
                        });
                        driver = new PhantomJSDriver(sCaps);
                        break;

                    default:
                        throw new Exception("Browser [" + browser + "] not well defined. Allowed values are: 'firefox', 'chrome' or  'internet explorer'. WebDriver cannot be initialized!");
                }
            } else {

                String method = this.getClass().getName().substring(37) + " - " + methodName.getName() + " - " + countryPar;

                if(baseURL.contains("staging")){method = method + " - STG";}
                else{method = method + " - PROD";}

                logger.info("============ Method: " + method + " ============");
                this.initSauceLabsDriver(method);
            }


            logger.info("Maximizing Window...");
            driver.manage().window().maximize();

            if(landingEnabled) {
                logger.info("Navigating to baseURL: [" + baseURL + "]");
                driver.navigate().to(baseURL);
                LandingPage landingPage = initLandingPage();
                logger.info("Selecting country page: [" + countryPar + "]");
                basePage = landingPage.selectCountryPage(countryPar);
            }
            //logger.info("Finishing @BeforeMethod...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initSauceLabsDriver(String methodName)  {

        String USERNAME = "automationteambsas";
        String ACCESS_KEY = "69172f7e-ebab-465d-bd46-b158e45c137e";
        String url = "https://" + USERNAME + ":" + ACCESS_KEY + seleniumURI +"/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();

        // set desired capabilities to launch appropriate browser on Sauce
        capabilities.setCapability(CapabilityType.PLATFORM, os);
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        capabilities.setCapability(CapabilityType.VERSION, browserVersion);
        capabilities.setCapability("screenResolution", "1280x960");

        capabilities.setCapability("name", methodName);

        SauceHelpers.addSauceConnectTunnelId(capabilities);

        // Launch remote browser and set it as the current thread
        try {
            webDriver.set(new RemoteWebDriver(new URL(url), capabilities));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

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
            logger.info(exception.getMessage());
        } catch (Exception exception){
            logger.info(exception.getMessage());
        }
    }

    /* This is to run retry analyzer for all the suites / tests  */
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context) {

        //get the uri to send the commands to.
        seleniumURI = SauceHelpers.buildSauceUri();

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

    public void logTestTitle(String testTitle) {
        logger.info("--------------------------------------------------------------------------------");
        logger.info(testTitle);
        logger.info("--------------------------------------------------------------------------------");
    }

    public void replaceUrl(){
        try{
            PageUtils.waitUrlContains(driver, 4, "checkout", "URL does not contain checkout");
        } catch(Exception time) {
            String actualURL = driver.getCurrentUrl();
            String newURL = actualURL.replace("cart", "checkout");
            logger.info("new URL: " + newURL);
            driver.navigate().to(newURL);
        }
    }

    //################################################ Inits ################################################

    protected LandingPage initLandingPage() {
        return PageFactory.initElements(driver, LandingPage.class);
    }

    protected BasePage initBasePage() {
        return PageFactory.initElements(driver, BasePage.class);
    }

    protected LoginPopUp initLoginPopUp() {
        return PageFactory.initElements(driver, LoginPopUp.class);
    }

    protected FacebookLoginPopUp initFacebookLoginPopUp() {
        return PageFactory.initElements(driver, FacebookLoginPopUp.class);
    }

    protected BasePage initGoogleLoginPopUpEmail() {
        return PageFactory.initElements(driver, GoogleLoginPopUpEmail.class);
    }

    protected BasePage initGoogleLoginPopUpPasswd() {
        return PageFactory.initElements(driver, GoogleLoginPopUpPasswd.class);
    }

    protected HotelsDataTrip initHotelsDataTrip() {
        return PageFactory.initElements(driver, HotelsDataTrip.class);
    }

    protected FlightsDataTrip initFlightsDataTrip() {
        return PageFactory.initElements(driver, FlightsDataTrip.class);
    }

    protected TripsDataTrip initTripsDataTrip() {
        return PageFactory.initElements(driver, TripsDataTrip.class);
    }

    protected CarsDataTrip initCarsDataTrip() {
        return PageFactory.initElements(driver, CarsDataTrip.class);
    }

    protected CheckOutPage initCheckOutPage() {
        return PageFactory.initElements(driver, CheckOutPage.class);
    }

    protected CheckOutPageV3 initCheckOutPageV3() {
        return PageFactory.initElements(driver, CheckOutPageV3.class);
    }

    protected ConfirmationPage initConfirmationPage() {
        return PageFactory.initElements(driver, ConfirmationPage.class);
    }

    protected PassengerSection initPassengerInfoSection() {
        return PageFactory.initElements(driver, PassengerSection.class);
    }

    protected PassengerSectionV3 initPassengerInfoSectionV3() {
        return PageFactory.initElements(driver, PassengerSectionV3.class);
    }

    protected PaymentSection initPaymentSection() {
        return PageFactory.initElements(driver, PaymentSection.class);
    }

    protected PaymentSectionV3 initPaymentSectionV3() {
        return PageFactory.initElements(driver, PaymentSectionV3.class);
    }

    protected PickUpLocationSection initPickUpLocationSection() {
        return PageFactory.initElements(driver, PickUpLocationSection.class);
    }

    protected BillingSection initBillingSection() {
        return PageFactory.initElements(driver, BillingSection.class);
    }

    protected BillingSectionV3 initBillingSectionV3() {
        return PageFactory.initElements(driver, BillingSectionV3.class);
    }

    protected ContactSection initContactInfoSection() {
        return PageFactory.initElements(driver, ContactSection.class);
    }

    protected ContactSectionV3 initContactInfoSectionV3() {
        return PageFactory.initElements(driver, ContactSectionV3.class);
    }

    protected FooterSection initFooterSection() {
        return PageFactory.initElements(driver, FooterSection.class);
    }

    protected FooterSectionV3 initFooterSectionV3() {
        return PageFactory.initElements(driver, FooterSectionV3.class);
    }

    protected HotelsResultsPage initHotelesResultsPage() {
        return PageFactory.initElements(driver, HotelsResultsPage.class);
    }

    protected HotelsDetailPage initHotelsDetailPage() {
        return PageFactory.initElements(driver, HotelsDetailPage.class);
    }

    protected FlightsResultsPage initFlightsResultsPage() {
        return PageFactory.initElements(driver, FlightsResultsPage.class);
    }

    protected TripsResultsPage initTripsResultsPage() {
        return PageFactory.initElements(driver, TripsResultsPage.class);
    }

    protected TripsDetailPage initTripsDetailPage() {
        return PageFactory.initElements(driver, TripsDetailPage.class);
    }

    protected CarsResultsPage initCarsResultsPage() {
        return PageFactory.initElements(driver, CarsResultsPage.class);
    }

    protected PromoPage initPromoPage() {
        return PageFactory.initElements(driver, PromoPage.class);
    }

    protected HeaderSection initHeaderSection() {
        return PageFactory.initElements(driver, HeaderSection.class);
    }

}