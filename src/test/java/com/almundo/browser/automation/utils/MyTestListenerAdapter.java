package com.almundo.browser.automation.utils;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;



/**
 * Created by gabrielcespedes on 08/11/16.
 */


public class MyTestListenerAdapter extends TestListenerAdapter {
    @Override
    public void onTestFailure(ITestResult result) {
        if (result.getMethod().getRetryAnalyzer() != null) {
            MyRetryAnalyzer retryAnalyzer = (MyRetryAnalyzer)result.getMethod().getRetryAnalyzer();

            if(retryAnalyzer.isRetryAvailable()) {
                result.setStatus(ITestResult.SKIP);
            } else {
                result.setStatus(ITestResult.FAILURE);
            }
            Reporter.setCurrentTestResult(result);
        }
    }
}