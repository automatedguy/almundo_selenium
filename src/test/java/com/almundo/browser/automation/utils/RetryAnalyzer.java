package com.almundo.browser.automation.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;
public class RetryAnalyzer implements IRetryAnalyzer {
    private int count = 0;
    private int maxCount = 2;
    @Override
    public boolean retry(ITestResult result) {

        if (!result.isSuccess()) {

            if (count < maxCount) {

                count++;
                result.setStatus(ITestResult.SUCCESS_PERCENTAGE_FAILURE);
                String message = Thread.currentThread().getName() +
                        "Error in " + result.getName() +
                        " with status " + result.getStatus() +
                        " Retrying " + count + " times";
                System.out.println(message);
                Reporter.log(message);

                return true;
            }
        }
        return false;
    }
}