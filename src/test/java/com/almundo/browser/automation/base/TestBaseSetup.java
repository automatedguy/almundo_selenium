package com.almundo.browser.automation.base;

import com.almundo.browser.automation.pages.AlmundoTrips.*;
import com.almundo.browser.automation.pages.AlmundoTrips.DataTrips.TripsCarsData;
import com.almundo.browser.automation.pages.AlmundoTrips.DataTrips.TripsFlightsData;
import com.almundo.browser.automation.pages.AlmundoTrips.DataTrips.TripsHotelsData;
import com.almundo.browser.automation.pages.BasePage.*;
import com.almundo.browser.automation.pages.CheckOutPage.*;
import com.almundo.browser.automation.pages.CheckOutPageV3.*;
import com.almundo.browser.automation.pages.CheckOutPageV3.Retail.*;
import com.almundo.browser.automation.pages.LandingPage;
import com.almundo.browser.automation.pages.PromoPage;
import com.almundo.browser.automation.pages.ResultsPage.*;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.RetryAnalyzer;
import com.almundo.browser.automation.utils.SauceHelpers;
import com.almundo.browser.automation.utils.SeleniumProxy;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
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

    /************* Selenium Proxy *************/
    private static SeleniumProxy seleniumProxy = new SeleniumProxy();
    private static Boolean initProxy = false;

    public static JSONObject jsonDataObject = null;
    public static JSONObject jsonPropertiesObject = null;
    public static JSONObject jsonCountryPropertyObject = null;

    // Selenium URI -- static same for everyone.
    public static String seleniumURI = null;

    public static boolean runningRemote;

    // Multidestination Calendars
    public String originCalendarLegOne = "origin-flights-0";
    public String originCalendarLegTwo = "origin-flights-1";

    public String destinationCalendarLegOne = "destination-flights-0";
    public String destinationCalendarLegTwo = "destination-flights-1";

    @Parameters({"env", "osType", "browserType", "browserTypeVersion", "country", "landing", "cart_id", "cart_id_icbc", "submit_Reservation", "retries_Max_Count"})
    @BeforeSuite

    public void initializeTestBaseSetup(@Optional(STG_URL) String env_url,
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

        if(baseURL.equals(STG_URL) || baseURL.equals(RET_URL) || baseURL.equals(RET_STG_URL)
                || baseURL.equals(CCR_URL) || baseURL.equals(CCR_STG_URL)) {
            switch (countryPar) {
                case "ARGENTINA":
                    baseURL = baseURL.concat(".ar/");
                    break;
                case "COLOMBIA":
                    baseURL = baseURL.concat(".co/");
                    break;
                case "MEXICO":
                    baseURL = baseURL.concat(".mx/");
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

        if(baseURL.contains("st.almundo")) {
            method = method + " - STG";
        } else {
            method = method + " - PROD";
        }

        try {
            if (os == null || browserVersion == null) {
                switch (browser) {
                    case "chrome":
                        driver = new ChromeDriver(capabilities);
                        if(initProxy){
                            logger.info("Initizalizing Selenium Proxy.");
                            seleniumProxy.setBrowserMobProxy();
                        }
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
            }

            if(cartId.isEmpty()) {
                logger.info("Navigating to baseURL: [" + baseURL + "]");
                driver.navigate().to(baseURL);

                if (landingEnabled) {
                    LandingPage landingPage = initLandingPage();
                    logger.info("Selecting country page: [" + countryPar + "]");
                    basePage = landingPage.selectCountryPage(countryPar);
                } else {
                    basePage = initBasePage();
                }
            } else {
                logger.info("cartId was provided, hence avoiding baseUrl navigation.");
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
                if(initProxy){
                    chromeCapabilities.setCapability(CapabilityType.PROXY, seleniumProxy.initSeleniumProxy());
                }
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
        runningRemote =  true;
        String USERNAME = "viajesyplacerautomation";
        String ACCESS_KEY = "1deb3616-b504-4e7d-ad44-da6108d4f7c9";
        String url = "https://" + USERNAME + ":" + ACCESS_KEY + seleniumURI +"/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();

        // set desired capabilities to launch appropriate browser on Sauce
        capabilities.setCapability("build", System.getenv("JOB_NAME") + "__" + System.getenv("BUILD_NUMBER"));
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
        driver = this.getWebDriver();
        String id = String.format("SauceOnDemandSessionID=%1$s job-name=%2$s",
                (((RemoteWebDriver) driver).getSessionId()).toString(), method);
        System.out.println("\n");
        logger.info("Current Saucelabs Session Id: " + id);
        sessionId.set(id);
    }

    @AfterMethod
    public void tearDown() {
        if(initProxy){
            seleniumProxy.displayHarInfo();}
        try {
            if(runningRemote){webDriver.get().quit();}
            else {
                driver.manage().deleteAllCookies();
                driver.quit();
            }
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
        System.lineSeparator();
        logger.info("-------------------------------------------------------------------------------------");
        logger.info("Method: [" + method + "]");
        logger.info("-------------------------------------------------------------------------------------");
        logger.info("Tittle: [" + testTitle + "]");
        logger.info("-------------------------------------------------------------------------------------");
    }

    public CheckOutPageV3 openCart(String cartId, String parameters, String productURl){
        logger.info("Opening checkout page with URL: " + baseURL + "checkout/" + cartId +  productURl + parameters);
        driver.navigate().to( baseURL + "checkout/" + cartId + productURl + parameters);
        return initCheckOutPageV3();
    }

    //################################################ Tests Results ########################################

    public void setResultSauceLabs(Results result) {
        if (runningRemote) {
            logger.info("Reporting results to Saucelabs...");
            try {
                ((JavascriptExecutor) driver).executeScript("sauce:job-result=" + result);
                logger.info("Test " + result + "!");
            }
            catch(Exception ex){
                logger.error("Communication with Saucelabs went wrong :( ");
            }
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

    protected AssistanceDataTrip initAssistanceDataTrip(){
        return PageFactory.initElements(driver, AssistanceDataTrip.class);
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

    protected ThanksPageV3 initConfirmationPageV3() {
        return PageFactory.initElements(driver, ThanksPageV3.class);
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

    protected PaymentSectionComboV3 initPaymentSectionComboV3() {
        return PageFactory.initElements(driver, PaymentSectionComboV3.class);
    }

    protected PaymentSectionComboRetailV3 initPaymentSectionComboRetailV3() {
        return PageFactory.initElements(driver, PaymentSectionComboRetailV3.class);
    }

    protected PaymentTwoCreditCardsV3 initPaymentTwoCreditCardsV3(){
        return PageFactory.initElements(driver, PaymentTwoCreditCardsV3.class);
    }

    protected PaymentSectionGridV3 initPaymentSectionGridV3(){
        return PageFactory.initElements(driver, PaymentSectionGridV3.class);
    }

    protected CreditCardDataV3 initCreditCardDataV3(){
        return PageFactory.initElements(driver, CreditCardDataV3.class);
    }

    protected CreditCardDataRetailV3 initCreditCardDataRetailV3(){
        return PageFactory.initElements(driver, CreditCardDataRetailV3.class);
    }

    protected DebitCardDataV3 initDebitCardDataV3(){
        return PageFactory.initElements(driver, DebitCardDataV3.class);
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

    protected AssistanceResultsPage initAssistanceResultsPage(){
        return PageFactory.initElements(driver, AssistanceResultsPage.class);
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

    protected CarsOfficeModal initCarsOfficeModal(){
        return PageFactory.initElements(driver, CarsOfficeModal.class);
    }

    /*************************** AlmundoTrips Inits ***********************/

    protected Home initHome(){
        return PageFactory.initElements(driver, Home.class);
    }

    protected ActivityFeed initActivityFeed(){
        return PageFactory.initElements(driver, ActivityFeed.class);
    }

    protected AddEvent initTrippersAgregarEvento(){
        return PageFactory.initElements(driver, AddEvent.class);
    }

    protected Dashboard initTripsDashboard(){
        return PageFactory.initElements(driver, Dashboard.class);
    }

    protected AddCustomEvent initTrippersAgregarOtroEvento(){
        return PageFactory.initElements(driver, AddCustomEvent.class);
    }

    protected SearchInAlmundo initBuscarEnAlmundo(){
        return PageFactory.initElements(driver, SearchInAlmundo.class);
    }

    protected TripsHotelsData initTripsHotelsData(){
        return PageFactory.initElements(driver, TripsHotelsData.class);
    }

    protected TripsFlightsData initTripsFlightsData(){
        return PageFactory.initElements(driver, TripsFlightsData.class);
    }

    protected TripsCarsData initTripsCarsData(){
        return PageFactory.initElements(driver, TripsCarsData.class);
    }

    protected CreateTrip initCreateTrip(){
        return PageFactory.initElements(driver, CreateTrip.class);
    }

    protected TripConfirmation initTripConfirmation(){
        return PageFactory.initElements(driver, TripConfirmation.class);
    }

    protected SaveFavourite initSaveFavourite(){
        return PageFactory.initElements(driver, SaveFavourite.class);
    }

    protected PaymentSelectorV3 initPaymentSelectorV3(){
        return PageFactory.initElements(driver, PaymentSelectorV3.class);
    }

    protected BreakDownSectionV3 initBreakDownSectionV3(){
        return PageFactory.initElements(driver, BreakDownSectionV3.class);
    }

    protected PaymentSelectorRetailV3 initPaymentSelectorRetailV3(){
        return PageFactory.initElements(driver, PaymentSelectorRetailV3.class);
    }

    protected PaymentSelectorRetailSplitV3 initPaymentSelectorRetailSplitV3(){
        return PageFactory.initElements(driver, PaymentSelectorRetailSplitV3.class);
    }

    protected AgentSectionV3 initAgentSectionV3(){
        return PageFactory.initElements(driver, AgentSectionV3.class);
    }
}