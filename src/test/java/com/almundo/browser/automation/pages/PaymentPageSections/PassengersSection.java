package com.almundo.browser.automation.pages.PaymentPageSections;

import com.almundo.browser.automation.base.PageBaseSetup;
import com.almundo.browser.automation.locators.pages.PaymentPageMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

/**
 * Created by gabrielcespedes on 31/10/16.
 */
public class PassengersSection extends PageBaseSetup{

    public class Passenger {
        public String firstName;
        public String lastName;
        public String documentNumber;
        public String fechaNacimiento;
        public String numeroPasajero;

        Passenger(int idNum){
            this.firstName = "first_name" + String.valueOf(idNum);
            this.lastName = "last_name" + String.valueOf(idNum);
            this.documentNumber = "document_number" + String.valueOf(idNum);
            this.fechaNacimiento = "birthday" + String.valueOf(idNum);
            this.numeroPasajero = String.valueOf(idNum+1);
        }
    }

    public ArrayList<Passenger> createPassenger(int numPassengers){
        ArrayList<Passenger> passengers = new ArrayList<>();
        for (int idNum = 0; idNum < numPassengers; idNum++) {
            passengers.add(new Passenger(idNum));
        }
        return passengers;
    }

    public PassengersSection populatePassenger(WebDriver driver, int numPassengers){

        ArrayList<Passenger> passengers = createPassenger(numPassengers);

        waitForVisibilityOfElementLocated(driver, 60 , PaymentPageMap.FIRST_NAME_TXT.getBy());

        for(Passenger passengerToPopulate : passengers){
            WebElement elementToPopulate;
            String typePassenger;

            elementToPopulate = driver.findElement(By.id(passengerToPopulate.firstName));
            elementToPopulate.sendKeys("Nombre");

            elementToPopulate = driver.findElement(By.id(passengerToPopulate.lastName));
            elementToPopulate.sendKeys("Apellido");

            /*  This must be available for Argentina only apparently */
            if(!driver.findElements(By.id(passengerToPopulate.documentNumber)).isEmpty()){
                elementToPopulate = driver.findElement(By.id(passengerToPopulate.documentNumber));
                elementToPopulate.sendKeys("123456789");
            }else{
                logger.info("Document number is not requiered.");
            }

            if(!driver.findElements(By.id(passengerToPopulate.fechaNacimiento)).isEmpty()){
                elementToPopulate = driver.findElement(By.id(passengerToPopulate.fechaNacimiento));
                typePassenger = driver.findElement(By.cssSelector(".passenger-ctn:nth-of-type(" + passengerToPopulate.numeroPasajero + ")>.passenger__info__detail>div:nth-of-type(1)>h3>span:nth-of-type(2)")).getText();
                if (typePassenger.equals("Adulto")){
                    elementToPopulate.sendKeys("09/09/1979");
                } else if (typePassenger.equals("Niño")){
                    elementToPopulate.sendKeys("09/09/2010");
                } else {
                    elementToPopulate.sendKeys("09/09/2015");
                }
            }else{
                logger.info("Birthday field is not requiered.");
            }
        }
        return this;
    }

    public void main(String[] args) {

        int numPassengers = 10;

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