package com.almundo.browser.automation.pages.PaymentPage;

/**
 * Created by gabrielcespedes on 07/12/16.
 */
public class Passenger {

    public String firstName;
    public String lastName;
    public String documentType;
    public String documentNumber;
    public String document_emisor;
    public String document_expiration;
    public String birthday;
    public String gender;
    public String nationality;
    public String numeroPasajero;

    Passenger(int idNum){
        this.firstName = "first_name" + String.valueOf(idNum);
        this.lastName = "last_name" + String.valueOf(idNum);
        this.documentType = "documentType" + String.valueOf(idNum);
        this.document_emisor = "document_emisor" + String.valueOf(idNum);
        this.document_expiration = "document_expiration" + String.valueOf(idNum);
        this.documentNumber = "document_number" + String.valueOf(idNum);
        this.birthday = "birthday" + String.valueOf(idNum);
        this.gender = "gender" + String.valueOf(idNum);
        this.nationality = "nationality" + String.valueOf(idNum);
        this.numeroPasajero = String.valueOf(idNum+1);
    }
}