package com.almundo.browser.automation.utils;

import com.almundo.browser.automation.base.TestBaseSetup;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabrielcespedes on 31/08/17.
 */
public class ThanksPageAssertInfo {

    private static Logger logger = Logger.getLogger(ThanksPageAssertInfo.class);

    private String finalAmountPaid = null;
    private String contactEmailEntered = null;

    private String flightDetailInfo = null;
    private String hotelsDetailInfo = null;
    private String carsDetailInfo = null;
    private String tripsDetailInfo = null;

    public static class Passenger{
        public String fullName = null;
        public String documentNumber = null;

        public Passenger(String passengerName, String passengerDocument) {
            fullName = passengerName;
            documentNumber =  passengerDocument;
        }
    }

    public static List<Passenger> passengersList = new ArrayList<Passenger>();

    public void initPassengerList(){
        passengersList = new ArrayList<Passenger>();
    }

    public class paymentForms{
        String paymentType = null;
        String paymentAmount = null;
    }
    public static List<paymentForms> paymentFormsList = new ArrayList<paymentForms>();

    public void setFinalAmountPaid(String finalAmountPaidCheckout){
        logger.info("Final amount paid: [" + finalAmountPaidCheckout + "]");
        finalAmountPaid = finalAmountPaidCheckout;
    }

    public String getFinalAmountPaid(){
        return finalAmountPaid;
    }

    public void setFlightsDetailInfo(String flightsDetailInfoCheckout){
        logger.info("Setting flights detail assertion info: [" + flightsDetailInfoCheckout + "]");
        flightDetailInfo = flightsDetailInfoCheckout;
    }

    public String getFlightDetailInfo(){
        return flightDetailInfo;
    }

    public void setHotelsDetailInfo(String hotelsDetailInfoCheckout){
        logger.info("Setting hotels detail assertion info: [" + hotelsDetailInfoCheckout + "]");
        hotelsDetailInfo = hotelsDetailInfoCheckout; }

    public String getHotelsDetailInfo(){
        return hotelsDetailInfo;
    }

    public void setCarsDetailInfo(String carsDetailInfoCheckout){
        logger.info("Setting cars detail assertion info: " + carsDetailInfoCheckout + "]");
        carsDetailInfo = carsDetailInfoCheckout; }

    public String getCarsDetailInfo(){
        return carsDetailInfo;
    }

    public void setTripsDetailInfo(String tripsDetailInfoCheckout){
        logger.info("Setting trips detail assertion info: [" + tripsDetailInfoCheckout + "]");
        tripsDetailInfo = tripsDetailInfoCheckout; }

    public String getTripsDetailInfo(){
        return tripsDetailInfo;
    }

    public void setContactEmailEntered(String contactEmailEnteredCheckout){
        logger.info("Setting contact email assertion info: [" + contactEmailEnteredCheckout + "]");
        contactEmailEntered = contactEmailEnteredCheckout; }

    public String getContactEmailEntered(){
        return contactEmailEntered;
    }
}