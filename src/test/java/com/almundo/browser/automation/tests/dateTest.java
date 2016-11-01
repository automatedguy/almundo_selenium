package com.almundo.browser.automation.tests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by gabrielcespedes on 01/11/16.
 */
public class dateTest {

    public static void main(String[] Args){

        int startDate = 30;

        Calendar c = Calendar.getInstance();
        int maxDaysCurrentMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);

        DateFormat dateFormat = new SimpleDateFormat("dd");
        Calendar cal = Calendar.getInstance();
        int currentDateDay = Integer.parseInt(dateFormat.format(cal.getTime()));

        int actualDayDate = currentDateDay + startDate;

        System.out.println("Cantidad de dias en el mes actual... " + maxDaysCurrentMonth);
        System.out.println("Fecha del dia de hoy ................ " + currentDateDay);
        System.out.println("Fecha del dia a marcar............... " + actualDayDate);

        if(actualDayDate <= maxDaysCurrentMonth){
            System.out.println("La Fecha es aceptable");
        }
        else {
            System.out.println("La Fecha es NO aceptable");
            actualDayDate = actualDayDate - maxDaysCurrentMonth;
            System.out.println("La Fecha nueva es: " + actualDayDate);
        }
    }

}
