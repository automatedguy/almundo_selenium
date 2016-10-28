package com.almundo.browser.automation.base;

// import Sauce TestNG helper libraries

import com.almundo.browser.automation.pages.LandingPage;
import com.almundo.browser.automation.utils.SauceHelpers;
import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;
import com.saucelabs.testng.SauceOnDemandTestListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.UnexpectedException;


/**
 * TestNG being instantiated via a DataProvider in order to supply multiple browser combinations.
 *
 */

@Listeners({SauceOnDemandTestListener.class})
public class SauceTestBaseSetup implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider  {

    public String sauceCountryParam;
    public String sauceAppURLParam;

    // Selenium URI -- static same for everyone.
    public static String seleniumURI = null;

    // Selenium URI -- static same for everyone
    public static String buildTag = null;


    // Sauce username
    public String username = System.getenv("SAUCE_USERNAME");

    // Sauce access key
    public String accesskey = System.getenv("SAUCE_ACCESS_KEY");

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(username, accesskey);

    /**
     * ThreadLocal variable which contains the  {@link WebDriver} instance which is used to perform browser interactions with.
     */
    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

    /**
     * ThreadLocal variable which contains the Sauce Job Id.
     */
    private ThreadLocal<String> sessionId = new ThreadLocal<String>();

    /**
     * DataProvider that explicitly sets the browser combinations to be used.
     *
     * @param testMethod
     * @return Two dimensional array of objects with browser, version, and platform information
     */
    @DataProvider(name = "hardCodedBrowsers", parallel = true)
    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
        return new Object[][]{
                /* Windows 10 */
                  // new Object[]{"MicrosoftEdge", "14", "Windows 10"},
                  new Object[]{"internet explorer", "11", "Windows 10"},
                  new Object[]{"firefox", "latest", "Windows 10"},
                  new Object[]{"chrome", "latest", "Windows 10"},

                /* Windows 8.1 */
//                new Object[]{"internet explorer", "11", "Windows 8.1"},

                /* Windows 8 */
//                new Object[]{"internet explorer", "10", "Windows 8"},

                /* Windows 7 */
//                new Object[]{"internet explorer", "11", "Windows 7"},
//                new Object[]{"internet explorer", "11", "Windows 7"},

                /* Windows XP */
//                new Object[]{"chrome", "latest", "Windows XP"},

                /* Windows 8 */
//                new Object[]{"safari", "9", "OS X 10.11"},

                /* Linux */
//                new Object[]{"firefox", "latest", "Linux"},
//                new Object[]{"chrome", "latest", "Linux"},
        };
    }

    /**
     * @return the {@link WebDriver} for the current thread
     */
    public WebDriver getWebDriver() {
        return webDriver.get();
    }

    /**
     *
     * @return the Sauce Job id for the current thread
     */
    public String getSessionId() {
        return sessionId.get();
    }

    /**
     *
     * @return the {@link SauceOnDemandAuthentication} instance containing the Sauce username/access key
     */
    @Override
    public SauceOnDemandAuthentication getAuthentication() {
        return authentication;
    }

    /**
     * Constructs a new {@link RemoteWebDriver} instance which is configured to use the capabilities defined by the browser,
     * version and os parameters, and which is configured to run against ondemand.saucelabs.com, using
     * the username and access key populated by the {@link #authentication} instance.
     *
     * @param browser Represents the browser to be used as part of the test run.
     * @param version Represents the version of the browser to be used as part of the test run.
     * @param os Represents the operating system to be used as part of the test run.
     * @param methodName Represents the name of the test case that will be used to identify the test on Sauce.
     * @return
     * @throws MalformedURLException if an error occurs parsing the url
     */

    protected void createDriver(String browser, String version, String os, String methodName)
            throws MalformedURLException, UnexpectedException {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        // set desired capabilities to launch appropriate browser on Sauce
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        capabilities.setCapability(CapabilityType.VERSION, version);
        capabilities.setCapability(CapabilityType.PLATFORM, os);
        capabilities.setCapability("name", methodName);

        if (buildTag != null) {
            capabilities.setCapability("build", buildTag);
        }

        SauceHelpers.addSauceConnectTunnelId(capabilities);

        // Launch remote browser and set it as the current thread
        webDriver.set(new RemoteWebDriver(
                new URL("https://" + authentication.getUsername() + ":" + authentication.getAccessKey() + seleniumURI +"/wd/hub"),
                capabilities));

        // set current sessionId
        String id = ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
        sessionId.set(id);

        WebDriver driver = this.getWebDriver();

        // driver.manage().window().setSize(new Dimension(2000, 2000));
        // Maximize the browser
        // LandingPage.landingPageMaximize(driver);

        // Navigate to the Landing Page "almundo.com"
        LandingPage.goToBaseUrl(driver, sauceAppURLParam);
        // Navigate to the corresponding country page base on the selected Country

        LandingPage.selectCountryPage(driver, sauceCountryParam);

    }


    @Parameters({ "countrySauce", "appURLSauce"  })
    @BeforeClass
    public void setCountryURL(String countryParam, String urlParam) {
        sauceCountryParam = countryParam;
        sauceAppURLParam = urlParam;
    }
    /**
     * Method that gets invoked after test.
     * Dumps browser log and
     * Closes the browser
     */

    @AfterMethod
    public void tearDown() throws Exception {

        //Gets browser logs if available.
        webDriver.get().quit();
        WebDriver driver = this.getWebDriver();
        driver.quit();
    }


    @BeforeSuite
    public void setupSuite(){
        //get the uri to send the commands to.
        seleniumURI = SauceHelpers.buildSauceUri();
        //If available add build tag. When running under Jenkins BUILD_TAG is automatically set.
        //You can set this manually on manual runs.
        buildTag = System.getenv("BUILD_TAG");
    }
}
