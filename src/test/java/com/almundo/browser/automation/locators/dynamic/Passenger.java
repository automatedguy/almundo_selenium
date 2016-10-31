package com.almundo.browser.automation.locators.dynamic;


import java.util.ArrayList;

/**
 * Created by gabrielcespedes on 31/10/16.
 */
public class Passenger {
    public String firstName;
    public String lastName;
    public String documentNumber;
    public String fechaNacimiento;

    public Passenger(int idNum) {
        this.firstName = "first_name" + String.valueOf(idNum);
        this.lastName = "last_name" + String.valueOf(idNum);
        this.documentNumber = "document_number" + String.valueOf(idNum);
        this.fechaNacimiento = "birthday" + String.valueOf(idNum);
    }

    public static void main(String[] args) {

        int numPassengers = 7;

        ArrayList<Passenger> passengers = new ArrayList<Passenger>();
        for (int idNum = 0; idNum < numPassengers; idNum++) {
            passengers.add(new Passenger(idNum));
        }

        for(Passenger passengerToPrint : passengers){
            System.out.println("ID=======: ");
            System.out.println(passengerToPrint.firstName.toString());
            System.out.println(passengerToPrint.lastName.toString());
            System.out.println(passengerToPrint.documentNumber.toString());
            System.out.println(passengerToPrint.fechaNacimiento.toString());
        }
    }
}