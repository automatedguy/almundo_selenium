package com.almundo.browser.automation.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabrielcespedes on 31/08/17.
 */
public class ThanksPageAssertInfo {

    public String finalAmountPaid = null;
    public String contactEmailEntered = null;

    public String flightDetailInfo = null;
    public String hotelDetailInfo = null;
    public String carsDetailInfo = null;
    public String tripsDetailInfo = null;

    public static class Passenger{
        public String fullName = null;
        public String documentNumber = null;

        public Passenger(String passengerName, String passengerDocument) {
            fullName = passengerName;
            documentNumber =  passengerDocument;
        }
    }
    public static List<Passenger> passengersList = new ArrayList<Passenger>();

    public static class paymentForms{
        String paymentType = null;
        String paymentAmount = null;
    }
    public static List<paymentForms> paymentFormsList = new ArrayList<paymentForms>();
}
