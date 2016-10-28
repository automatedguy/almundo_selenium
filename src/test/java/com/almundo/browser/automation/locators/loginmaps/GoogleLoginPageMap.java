package com.almundo.browser.automation.locators.loginmaps;

import org.openqa.selenium.By;

/**
 * Created by gabrielcespedes on 18/10/16.
 */
public enum GoogleLoginPageMap {
    EMAIL_TXT(By.id("Email")),
    PASSWORD_TXT(By.id("Passwd")),
    NEXT_BTN(By.id("next")),
    SUBMIT_APPROVE_ACCESS_BTN(By.id("submit_approve_access")),
    SIGNIN_BTN(By.id("signIn"));
    private By name;
    GoogleLoginPageMap(By locator) {this.name = locator; }
    public By getBy() { return name; }
}
