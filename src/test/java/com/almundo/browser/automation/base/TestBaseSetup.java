package com.almundo.browser.automation.base;

import com.almundo.browser.automation.pages.LandingPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class TestBaseSetup {

    private WebDriver driver;
    static String chromeDriverPath = "/home/gabrielcespedes/idea-IC-162.2032.8/chrome/chromedriver";
    static String firefoxDriverPath = "/home/gabrielcespedes/idea-IC-162.2032.8/gecko/geckodriver";

    public static String numPassengers;

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

    private static WebDriver initChromeDriver(String appURL, String country) throws InterruptedException {
        System.out.println("Launching google chrome with new profile..");
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        LandingPage.selectCountryPage(driver, country);
        PageUtils.waitForSaucePicture(10000);
        return driver;
    }

    private static WebDriver initFirefoxDriver(String appURL, String country) throws InterruptedException {
        System.out.println("Launching Firefox browser..");
        System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);
        WebDriver driver = new FirefoxDriver(capabilities);
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        LandingPage.selectCountryPage(driver, country);
        PageUtils.waitForSaucePicture(10000);
        return driver;
    }

    @Parameters({ "browserType", "appURL" , "country" , "passengers" })
    @BeforeClass
    public void initializeTestBaseSetup(String browserType, String appURL, String country, String passengers) {
        try {
            setDriver(browserType, appURL, country);

        } catch (Exception e) {
            System.out.println("Error....." + e.getStackTrace());
        }

        /* Initialize Number of passengers */
        numPassengers = passengers;

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
