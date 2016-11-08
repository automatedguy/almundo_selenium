package com.almundo.browser.automation.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Created by mehmetgerceker on 12/9/15.
 * This will apply to tests decorated with @Test(retryAnalyzer=RetryAnalyzer.class)
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private int maxRetryCount = 3;
    public boolean retry(ITestResult result) {
        if(retryCount < maxRetryCount)
        {
            retryCount++;
            return true;
        }
        return false;
    }

    // set your count to rerun test Maven can set a sys property which can be read here
 /*   private AtomicInteger count = new AtomicInteger(3);

    @Override
    public boolean retry(ITestResult result) {
        if (0 < count.getAndDecrement()) {
            return true;
        }
        return false;
    }*/

}