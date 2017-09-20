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

    public String finalAmountPaid = null;
    public String contactEmailEntered = null;

    public String flightDetailInfo = null;
    public String hotelsDetailInfo = null;
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

    public List<Passenger> passengersList = new ArrayList<Passenger>();

    public void initPassengerList(){
        passengersList = new ArrayList<Passenger>();
    }

    public class paymentForms{
        String paymentType = null;
        String paymentAmount = null;
    }
    public static List<paymentForms> paymentFormsList = new ArrayList<paymentForms>();

    public void setFinalAmountPaid(String finalAmountPaidCheckout){
        finalAmountPaid = finalAmountPaidCheckout;
    }

    public void setFlightsDetailInfo(String flightsDetailInfoCheckout){
        logger.info("Setting flights detail assertion info.");
        flightDetailInfo = flightsDetailInfoCheckout;
    }

    public void setHotelsDetailInfo(String hotelsDetailInfoCheckout){
        logger.info("Setting hotels detail assertion info.");
        hotelsDetailInfo = hotelsDetailInfoCheckout; }

    public void setCarsDetailInfo(String carsDetailInfoCheckout){
        logger.info("Setting cars detail assertion info.");
        carsDetailInfo = carsDetailInfoCheckout; }

    public void setTripsDetailInfo(String tripsDetailInfoCheckout){
        logger.info("Setting trips detail assertion info.");
        tripsDetailInfo = tripsDetailInfoCheckout; }

    public void setContactEmailEntered(String contactEmailEnteredCheckout){ contactEmailEntered = contactEmailEnteredCheckout; }
}