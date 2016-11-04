package com.almundo.browser.automation.flows;

import com.almundo.browser.automation.base.PageBaseSetup;
import org.openqa.selenium.WebDriver;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class VueloHotelFlow extends PageBaseSetup {

    public String TRIPS_FECHA_SALIDA_CAL = "departure-trips";
    public String TRIPS_FECHA_REGRESO_CAL = "arrival-trips";

    public VueloHotelFlow(WebDriver driver) {
        super.driver = driver;
    }

    public VueloHotelFlow doVueloHotelReservationFlow(WebDriver driver){

        return this;
    }

}
