package com.almundo.browser.automation.base;

import com.almundo.browser.automation.pages.BasePage.*;
import com.almundo.browser.automation.pages.CheckOutPage.*;
import com.almundo.browser.automation.pages.CheckOutPageV3.*;
import com.almundo.browser.automation.pages.LandingPage;
import com.almundo.browser.automation.pages.PromoPage;
import com.almundo.browser.automation.pages.ResultsPage.*;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.PageUtils;
import com.almundo.browser.automation.utils.RetryAnalyzer;
import com.almundo.browser.automation.utils.SauceHelpers;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

import static com.almundo.browser.automation.utils.Constants.*;

public class TestBaseSetup {

    public static Logger logger = Logger.getLogger(TestBaseSetup.class);
    public WebDriver driver;

    public BasePage basePage = null;

    public static String baseURL = null;
    private static String os = null;
    private static String browser = null;
    private static String browserVersion = null;
    public static String osName = System.getProperty("os.name");
    public static Boolean landingEnabled = true;
    public static String cartId = null;
    public static String cartIdICBC = null;
    public static Boolean submitReservation = false;
    public static Boolean retriesCount = false;

    public static String className;
    public static String method;
    public static String countryPar;
    private static DesiredCapabilities capabilities = null;

    public static JSONObject jsonDataObject = null;
    public static JSONObject jsonPropertiesObject = null;
    public static JSONObject jsonCountryPropertyObject = null;

    // Selenium URI -- static same for everyone.
    public static String seleniumURI = null;

    @Parameters({"env", "osType", "browserType", "browserTypeVersion", "country", "landing", "cart_id", "cart_id_icbc", "submit_Reservation", "retries_Max_Count"})
    @BeforeSuite
    public void initializeTestBaseSetup(@Optional(PROD_URL) String env_url,
                                        @Optional() String osType,
//                                        @Optional("OS X 10.11") String osType,
//                                        @Optional("Windows 10") String osType,
                                        @Optional("chrome") String browserType,
                                        @Optional("latest") String browserTypeVersion,
                                        @Optional("ARGENTINA") String country,
                                        @Optional("true") Boolean landing,
                                        @Optional("") String cart_id,
                                        @Optional("") String cart_id_icbc,
                                        @Optional("false") Boolean submit_Reservation,
                                        @Optional("false") Boolean retries_Max_Count) {

        this.baseURL = env_url;
        this.os = osType;
        this.browser = browserType;
        this.browserVersion = browserTypeVersion;
        this.countryPar = country;
        this.landingEnabled = landing;
        this.cartId = cart_id;
        this.cartIdICBC = cart_id_icbc;
        this.submitReservation = submit_Reservation;
        this.retriesCount = retries_Max_Count;

        try {
            if (os == null || browserVersion == null) {
                capabilities = getCapabilities();
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

        if(baseURL.equals(STAGING_URL)) {
            switch (countryPar) {
                case "ARGENTINA":
                    baseURL = STAGING_URL.concat(".ar/");
                    break;
                case "COLOMBIA":
                    baseURL = STAGING_URL.concat(".co/");
                    break;
                case "MEXICO":
                    baseURL = STAGING_URL.concat(".mx/");
                    break;
            }
            landingEnabled = false;
        }

        if(baseURL.equals(RET_URL)) {
            switch (countryPar) {
                case "ARGENTINA":
                    baseURL = RET_URL.concat(".ar/");
                    break;
                case "COLOMBIA":
                    baseURL = RET_URL.concat(".co/");
                    break;
                case "MEXICO":
                    baseURL = RET_URL.concat(".mx/");
                    break;
            }
            landingEnabled = false;
        }

        jsonDataObject = JsonRead.getJsonFile(countryPar.toLowerCase() + "_data.json");
        jsonPropertiesObject = JsonRead.getJsonFile("countries_properties.json");
        jsonCountryPropertyObject = JsonRead.getJsonDataObject(jsonPropertiesObject, countryPar, "countries_properties.json");
    }


    @BeforeMethod
    public void setDriver(Method methodName) {

        className = this.getClass().getName().substring(37);
        method = className + " - " + methodName.getName() + " - " + countryPar;

        if(baseURL.contains("st.almundo")){
            method = method + " - STG";
        } else {
            method = method + " - PROD";
        }

        try {
            if (os == null || browserVersion == null) {
                switch (browser) {
                    case "chrome":
                        driver = new ChromeDriver(capabilities);
                        break;

                    case "firefox":
                        driver = new FirefoxDriver(capabilities);
                        break;

                    case "phantomjs":
                        driver = new PhantomJSDriver(capabilities);
                        driver.manage().window().maximize();
                        break;

                    default:
                        throw new Exception("Browser [" + browser + "] not well defined. Allowed values are: 'firefox', 'chrome' or  'phantomjs'. WebDriver cannot be initialized!");
                }
            } else {
                this.initSauceLabsDriver(method);
                //this.initBrowserStackDriver(method);
            }

            logger.info("Navigating to baseURL: [" + baseURL + "]");
            driver.navigate().to(baseURL);

            if(landingEnabled) {
                LandingPage landingPage = initLandingPage();
                logger.info("Selecting country page: [" + countryPar + "]");
                basePage = landingPage.selectCountryPage(countryPar);
            } else {
                basePage = initBasePage();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DesiredCapabilities getCapabilities() throws Exception {
        switch (browser) {
            case "chrome":
                if (osName.toLowerCase().contains("windows")){
                    System.setProperty("webdriver.chrome.driver", RESOURCES_PATH + "chromedriver.exe");
                } else {
                    System.setProperty("webdriver.chrome.driver", RESOURCES_PATH + "chromedriver");
                }
                DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
                chromeCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                ChromeOptions options = new ChromeOptions();
                options.addArguments("test-type", "start-maximized", "no-default-browser-check");
                options.addArguments("--disable-extensions");
                chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, options);

                return chromeCapabilities;

            case "firefox":
                if (osName.toLowerCase().contains("windows")){
                    System.setProperty("webdriver.gecko.driver", RESOURCES_PATH + "geckodriver.exe");
                } else {
                    System.setProperty("webdriver.gecko.driver", RESOURCES_PATH + "geckodriver");
                }
                DesiredCapabilities firefoxCapabilities = DesiredCapabilities.firefox();
                firefoxCapabilities.setCapability("marionette", true);
                return firefoxCapabilities;

            case "phantomjs":
                if (osName.toLowerCase().contains("windows")){
                    System.setProperty("phantomjs.binary.path", RESOURCES_PATH + "phantomjs.exe");
                } else {
                    System.setProperty("phantomjs.binary.path", RESOURCES_PATH + "phantomjs");
                }
                DesiredCapabilities phantomCapabilities = new DesiredCapabilities();
                phantomCapabilities.setJavascriptEnabled(true);
                phantomCapabilities.setCapability("takesScreenshot", false);
                phantomCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] {
                        "--web-security=false",
                        "--ssl-protocol=any",
                        "--ignore-ssl-errors=true",
                        "--webdriver-loglevel=NONE"
                });
                return phantomCapabilities;

            default:
                throw new Exception("Browser [" + browser + "] not well defined. Allowed values are: 'firefox', 'chrome' or  'phantomjs'. WebDriver cannot be initialized!");
        }
    }

    private void initSauceLabsDriver(String methodName)  {
        String USERNAME = "automationalmundouser";
        String ACCESS_KEY = "36088d80-d52f-4c3c-b354-adbad1518d5a";
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

    private void initBrowserStackDriver(String methodName) throws Exception {
        String USERNAME = "almundoautomatio1";
        String ACCESS_KEY = "js5ijJ8iVtgeru8s1ULs";
        String url = "https://" + USERNAME + ":" + ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("browser", "Firefox");
        caps.setCapability("browser_version", "52.0");
        if (os.contains("OSX")) caps.setCapability("os", "OS X");
        else caps.setCapability("os", "Windows");
        caps.setCapability("os_version", "10");
        caps.setCapability("resolution", "1280x1024");

        String className = getClass().getName().replace("com.almundo.browser.automation.tests.Flights.", "");
        caps.setCapability("name", className + " - " + methodName);

        driver = new RemoteWebDriver(new URL(url), caps);
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
    private WebDriver getWebDriver() {
        return webDriver.get();
    }

    public void logTestTitle(String testTitle) {
        logger.info("-------------------------------------------------------------------------------------");
        logger.info(testTitle + " - Method [" + method + "]");
        logger.info("-------------------------------------------------------------------------------------");
    }

    public void forceCheckoutV3(){
        try{
            PageUtils.waitUrlContains(driver, 10, "checkout", "Checkout V3");
        } catch(Exception time) {
            logger.info("Forcing Checkout to V3");
            String newURL = driver.getCurrentUrl().replace("cart/v2", "checkout");
            driver.navigate().to(newURL);
        }
    }

    public void forceCombosV3(){
        try{
            PageUtils.waitUrlContains(driver, 10, "checkout", "Checkout V3");

            logger.info("Forcing Checkout to Combos V3");
            String currentUrl = driver.getCurrentUrl();

            if(currentUrl.contains("sc=0")) {
                logger.info("Replacing sc=0 with sc=1");
                String newURL = currentUrl.replace("sc=0", "sc=1");
                driver.navigate().to(newURL);
            } else if(currentUrl.contains("sc=1")) {
                logger.info("Nothing to replace, combos are displayed");
            } else {
                logger.info("Adding sc=1");
                String newURL = currentUrl.concat("&sc=1");
                driver.navigate().to(newURL);
            }
        } catch(Exception time) {
            time.printStackTrace();
        }
    }

    public void forceTodoPagoOff(){
        try{
            PageUtils.waitUrlContains(driver, 10, "checkout", "Checkout V3");

            logger.info("Forcing Checkout to disable TodoPago");
            String currentUrl = driver.getCurrentUrl();

            if(currentUrl.contains("stp=1")) {
                logger.info("Replacing stp=1 with stp=0");
                String newURL = currentUrl.replace("stp=1", "stp=0");
                PageUtils.waitImplicitly(5000);
                driver.navigate().to(newURL);
            } else {
                logger.info("Nothing to replace, Todo Pago is not displayed");
            }
        } catch(Exception time) {
            time.printStackTrace();
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

    protected ConfirmationPageV3 initConfirmationPageV3() {
        return PageFactory.initElements(driver, ConfirmationPageV3.class);
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

    protected HotelsResultsPage initHotelsResultsPage() {
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

    protected AgreementPage initTermsAndConditonsPage(){
        return PageFactory.initElements(driver, AgreementPage.class);
    }

}