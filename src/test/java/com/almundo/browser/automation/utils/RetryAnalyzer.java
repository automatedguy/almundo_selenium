package com.almundo.browser.automation.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

import static com.almundo.browser.automation.base.TestBaseSetup.logger;
import static com.almundo.browser.automation.base.TestBaseSetup.retriesMaxCount;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int maxCount;

    public int getMaxCount() {
        String env_path = System.getenv("PATH");
        int maxCount = 0;

        logger.info("PATH: " + env_path);

        if(env_path.contains("jenkins")) {
            maxCount = 3;
        }
        return maxCount;
    }

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if (getMaxCount() < retriesMaxCount) {
                maxCount++;
                result.setStatus(ITestResult.SUCCESS_PERCENTAGE_FAILURE);
                String message = Thread.currentThread().getName() +
                        "Error in " + result.getName() +
                        " with status " + result.getStatus() +
                        " Retrying " + maxCount + " times";
                System.out.println(message);
                Reporter.log(message);

                return true;
            }
        }
        return false;
    }
}