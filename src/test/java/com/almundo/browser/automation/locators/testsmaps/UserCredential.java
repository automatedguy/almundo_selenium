package com.almundo.browser.automation.locators.testsmaps;

/**
 * Created by gabrielcespedes on 19/10/16.
 */
public enum UserCredential {
    REGISTERED_USER("automationthings@gmail.com"),
    REGISTERED_PASS("gabi1981ce");

    private UserCredential(String credential){
        this.userCredential = credential;
    }

    private String userCredential;

    public String getUserCredential(){
        return this.userCredential;
    }

    public String toString(){
        return this.userCredential;
    }
}
