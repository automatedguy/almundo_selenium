package com.almundo.browser.automation.pages.PaymentPage;

import com.almundo.browser.automation.base.PageBaseSetup;
import com.almundo.browser.automation.locators.pages.PaymentPageMap;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;

/**
 * Created by gabrielcespedes on 31/10/16.
 */
public class PassengersSection extends PageBaseSetup{

    @FindBy(id = "first_name0")
    private WebElement readCbx;

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

    public ArrayList<Passenger> createPassenger(int numPassengers){
        ArrayList<Passenger> passengers = new ArrayList<>();
        for (int idNum = 0; idNum < numPassengers; idNum++) {
            passengers.add(new Passenger(idNum));
        }
        return passengers;
    }

    public PassengersSection populatePassenger(WebDriver driver, int numPassengers){

        ArrayList<Passenger> passengers = createPassenger(numPassengers);

        PageUtils.waitForVisibilityOfElementLocated(driver, 60 , PaymentPageMap.FIRST_NAME_TXT.getBy());

        for(Passenger passengerToPopulate : passengers){
            WebElement elementToPopulate;
            String typePassenger;

            elementToPopulate = driver.findElement(By.id(passengerToPopulate.firstName));
            elementToPopulate.sendKeys("Nombre");

            elementToPopulate = driver.findElement(By.id(passengerToPopulate.lastName));
            elementToPopulate.sendKeys("Apellido");

            elementToPopulate = driver.findElement(By.id(passengerToPopulate.documentType));
            Select tipoDeDocumento = new Select(elementToPopulate);
            tipoDeDocumento.selectByVisibleText("Pasaporte");

            /*  This must be available for Argentina only apparently */
            if(!driver.findElements(By.id(passengerToPopulate.documentNumber)).isEmpty()){
                elementToPopulate = driver.findElement(By.id(passengerToPopulate.documentNumber));
                elementToPopulate.sendKeys("123456789");
            }else{
                logger.info("Document number is not requiered.");
            }

            elementToPopulate = driver.findElement(By.id(passengerToPopulate.document_emisor));
            Select paisEmisorDelPasaporte = new Select(elementToPopulate);
            paisEmisorDelPasaporte.selectByVisibleText("Argentina");

            elementToPopulate = driver.findElement(By.id(passengerToPopulate.document_expiration));
            elementToPopulate.sendKeys("25/12/2017");


            if(!driver.findElements(By.id(passengerToPopulate.birthday)).isEmpty()){
                elementToPopulate = driver.findElement(By.id(passengerToPopulate.birthday));
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

            elementToPopulate = driver.findElement(By.id(passengerToPopulate.gender));
            Select sexo = new Select(elementToPopulate);
            sexo.selectByVisibleText("Femenino");

            elementToPopulate = driver.findElement(By.id(passengerToPopulate.nationality));
            Select nacionalidad = new Select(elementToPopulate);
            nacionalidad.selectByVisibleText("Argentina");
        }
        return this;
    }
}