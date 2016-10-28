package com.almundo.browser.automation.locators.testsmaps;

/**
 * Created by gabrielcespedes on 27/10/16.
 */
public enum TestInputs {
    FULL_DESTINATION1_INP("Las Vegas, Nevada, Estados Unidos de América"),
    AUTOCOMPLETE_INP("Rio"),
    HOTEL_VER_HOTEL_BTN("Ver hotel");

    private final String name;

    private TestInputs(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
