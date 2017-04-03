package com.almundo.browser.automation.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

import static com.almundo.browser.automation.base.TestBaseSetup.retriesMaxCount;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int count = 0;

    public int getMaxount() {
        String env_path = System.getenv("PATH");
        int maxCount = 0;

        if(env_path.contains("jenkins")) {
            maxCount = 3;
        }
        return maxCount;
    }

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if (count < retriesMaxCount) {
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