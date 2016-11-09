package com.almundo.browser.automation.base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

/**
 * Created by gabrielcespedes on 07/11/16.
 */
public class TestBaseSetupHeader extends TestBaseSetup {

    /* Overloading Chrome method in order to setup Http Header */
    public WebDriver initChromeDriver(String appURL, String country) throws InterruptedException {
        System.out.println("Launching google chrome with new profile..");
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File("/home/gabrielcespedes/Downloads/extension_2_1_1.crx"));


        WebDriver driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        driver.get("chrome-extension://idgpnmonknjnojddfkpgkljpfnnfcklj/settings.tmpl.html");

        ((JavascriptExecutor)driver).executeScript(
                "localStorage.setItem('profiles', JSON.stringify([{                " +
                        "  title: 'Selenium', hideComment: true, appendMode: '',           " +
                        "  headers: [                                                      " +
                        "    {enabled: true, name: 'X-Debug', value: 'True', comment: ''}  " +
                        "  ],                                                              " +
                        "  respHeaders: [],                                                " +
                        "  filters: []                                                     " +
                        "}]));                                                             " );
        return driver;
    }
}
