package com.almundo.browser.automation.base;

import com.almundo.browser.automation.utils.Constants;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

/**
 * Created by gabrielcespedes on 07/11/16.
 */
public class TestBaseSetupHeader extends TestBaseSetup {

    /* Overloading initChromeDriver method in order to setup Http Header */
    public WebDriver initChromeDriver() {
        System.out.println("Launching google chrome with new profile..");

        if (osName.toLowerCase().contains("windows")){
            System.setProperty("webdriver.chrome.driver", Constants.RESOURCES_PATH + "chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", Constants.RESOURCES_PATH + "chromedriver");
        }

        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File(Constants.RESOURCES_PATH + "extension_2_1_1.crx"));

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get("chrome-extension://idgpnmonknjnojddfkpgkljpfnnfcklj/settings.tmpl.html");

        ((JavascriptExecutor)driver).executeScript(
                "localStorage.setItem('profiles', JSON.stringify([{                " +
                        "  title: 'Selenium', hideComment: true, appendMode: '',           " +
                        "  headers: [                                                      " +
                        "    {enabled: true, name: 'X-AM-Provider', value: 'AMA', comment: ''}  " +
                        "  ],                                                              " +
                        "  respHeaders: [],                                                " +
                        "  filters: []                                                     " +
                        "}]));                                                             " );
        return driver;
    }
}
