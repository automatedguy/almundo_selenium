package com.almundo.browser.automation.locators.loginmaps;

import org.openqa.selenium.By;

/**
 * Created by gabrielcespedes on 18/10/16.
 */
public enum LoginPageMap {
    LOGIN_EMAIL_TXT(By.id("login_email")),
    LOGIN_PASSWORD_TXT(By.id("login_password")),
    INGRESAR_BTN(By.xpath("//input[@value='Ingresar']")),
    OLVIDE_LNK(By.linkText("Olvidé mi contraseña")),
    REGISTRATE_LNK(By.linkText("Registrate")),
    FACEBOOK_BTN(By.xpath("//span[contains(.,'Facebook')]")),
    GOOGLE_BTN(By.xpath("//span[contains(.,'Google')]"));
    private By name;
    LoginPageMap(By locator) {this.name = locator; }
    public By getBy() { return name; }
}
