package com.almundo.browser.automation.locators.loginmaps;

import org.openqa.selenium.By;

/**
 * Created by gabrielcespedes on 18/10/16.
 */
public enum FacebookLoginPageMap {
    EMAIL_TXT(By.id("email")),
    PASSWORD_TXT(By.id("pass")),
    LOGIN_BTN(By.id("loginbutton"));
    private By name;
    FacebookLoginPageMap(By locator) {this.name = locator; }
    public By getBy() { return name; }
}
