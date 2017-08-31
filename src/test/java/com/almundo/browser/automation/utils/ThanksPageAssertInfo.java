package com.almundo.browser.automation.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabrielcespedes on 31/08/17.
 */
public class ThanksPageAssertInfo {

    public int finalAmountPaid = 0;
    public String contactEmailEntered = null;

    public String flightDetailInfo = null;

    public class paymentForms{
        String paymentType = null;
        String paymentAmount = null;
    }
    List<paymentForms> paymentFormsList = new ArrayList<paymentForms>();

    public class passengers{
        String fullName = null;
        String documentNumber = null;
    }
    List<passengers> passengersList = new ArrayList<passengers>();
}
