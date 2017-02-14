package com.almundo.browser.automation.pages.CheckOutPage;

import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;

/**
 * Created by gabrielcespedes on 12/12/16.
 */
public class PassengerSection extends CheckOutPage {

    public PassengerSection(WebDriver driver) {
        super(driver);
    }

    private static JSONObject passengersList = null;
    public static JSONObject passengerData = null;
    public static JSONArray passengerJsonList = new JSONArray();

    //############################################### Actions ###############################################

    private ArrayList<Passenger> createPassenger(int numPassengers){
        ArrayList<Passenger> passengers = new ArrayList<>();
        for (int idNum = 0; idNum < numPassengers; idNum++) {
            passengers.add(new Passenger(idNum));
        }
        return passengers;
    }

    public PassengerSection populatePassengerSection(JSONArray passengerList){

        ArrayList<Passenger> passengers = createPassenger(passengerList.size());
        logger.info("------------- Filling Passenger Section -------------");

        int passengerIndex = 0;
        JSONObject passengerInfo;

        for(Passenger passengerId : passengers){

            logger.info("************ Filling Passenger [" + (passengerIndex + 1) +  "] ************");

            passengerInfo = (JSONObject) passengerList.get(passengerIndex);

            setFirstName(passengerId.firstName, passengerInfo.get("first_name").toString());

            setlastName(passengerId.lastName, passengerInfo.get("last_name").toString());

            if(isElementRequiered(checkOutPageElements, "documentType0")) {
                setDocumentType(passengerId.documentType, passengerInfo.get("documentType").toString());
            }

            if(isElementRequiered(checkOutPageElements, "document_number")){
                setDocumentNumber(passengerId.documentNumber, passengerInfo.get("document_number").toString());
            }

            if(isElementRequiered(checkOutPageElements, "document_emisor")) {
                setDocumentEmisor(passengerId.document_emisor, passengerInfo.get("document_emisor").toString());
            }

            if(isElementRequiered(checkOutPageElements, "document_expiration")) {
                setDocumentExpiration(passengerId.document_expiration, passengerInfo.get("document_expiration").toString());
            }

            if(isElementRequiered(checkOutPageElements, "birthday")) {
                setBirthDay(passengerId.birthday, passengerInfo.get("birthday").toString());
            }

            if(isElementRequiered(checkOutPageElements, "gender")) {
                setGender(passengerId.gender, passengerInfo.get("gender").toString());
            }

            if(isElementRequiered(checkOutPageElements, "nationality")) {
                setNationality(passengerId.nationality, passengerInfo.get("nationality").toString());
            }
            passengerIndex = passengerIndex + 1;
        }
        return this;
    }


    public PassengerSection setFirstName(String firstName, String firstNamePassenger){
        PageUtils.waitElementForVisibility(driver, By.id(firstName), 45, "First Name text box");
        WebElement elementToPopulate = driver.findElement(By.id(firstName));

        logger.info("Entering Nombre/s: [" + firstNamePassenger + "]");
        elementToPopulate.clear();
        elementToPopulate.sendKeys(firstNamePassenger);
        return this;
    }

    public PassengerSection setlastName(String lastName, String lastNamePassenger){
        WebElement elementToPopulate = driver.findElement(By.id(lastName));

        logger.info("Entering Apellido/s: [" + lastNamePassenger + "]");
        elementToPopulate.clear();
        elementToPopulate.sendKeys(lastNamePassenger);
        return this;
    }

    public PassengerSection setDocumentType(String documentType, String documentTypePassenger){
        WebElement elementToPopulate = driver.findElement(By.id(documentType));

        logger.info("Selecting Tipo de documento: [" + documentTypePassenger + "]");
        Select tipoDeDocumento = new Select(elementToPopulate);
        tipoDeDocumento.selectByVisibleText(documentTypePassenger);
        return this;
    }

    public PassengerSection setDocumentNumber(String documentNumber, String documentNumberPassenger){
        WebElement elementToPopulate = driver.findElement(By.id(documentNumber));

        logger.info("Entering Número: [" + documentNumberPassenger + "]");
        elementToPopulate.clear();
        elementToPopulate.sendKeys(documentNumberPassenger);
        return this;
    }

    public PassengerSection setDocumentEmisor(String documentEmisor, String documentEmisorPassenger){
        WebElement elementToPopulate = driver.findElement(By.id(documentEmisor));

        logger.info("Selecting País emisor del pasaporte: [" + documentEmisorPassenger + "]");
        Select paisEmisorDelPasaporte = new Select(elementToPopulate);
        paisEmisorDelPasaporte.selectByVisibleText(documentEmisorPassenger);
        return this;
    }

    public PassengerSection setDocumentExpiration(String documentExpiration, String documentExpirationPassenger){
        WebElement elementToPopulate = driver.findElement(By.id(documentExpiration));

        logger.info("Entering Fecha de venc. del documento: [" + documentExpirationPassenger + "]");
        elementToPopulate.clear();
        elementToPopulate.sendKeys(documentExpirationPassenger);
        return this;
    }

    public PassengerSection setBirthDay(String birthday, String birthdayPassenger){
        WebElement elementToPopulate = driver.findElement(By.id(birthday));

        logger.info("Entering Fecha de Nacimiento: [" + birthdayPassenger + "]");
        elementToPopulate.clear();
        elementToPopulate.sendKeys(birthdayPassenger);
        return this;
    }

    public PassengerSection setGender(String gender, String genderPassenger){
        WebElement elementToPopulate = driver.findElement(By.id(gender));

        logger.info("Selecting Sexo: [" + genderPassenger + "]");
        Select sexo = new Select(elementToPopulate);
        sexo.selectByVisibleText(genderPassenger);
        return this;
    }

    public PassengerSection setNationality(String nationality, String nationalityPassenger){
        WebElement elementToPopulate = driver.findElement(By.id(nationality));

        logger.info("Selecting Nacionalidad: [" + nationalityPassenger + "]");
        Select nacionalidad = new Select(elementToPopulate);
        nacionalidad.selectByVisibleText(nationalityPassenger);
        return this;
    }


    public static void getPassengersList()  {
        passengersList = JsonRead.getJsonDataObject(jsonDataObject, "passengers", countryPar.toLowerCase() + "_data.json");
    }

    public static void getPassengerData(String dataSet)  {
        passengerData = JsonRead.getJsonDataObject(passengersList, dataSet, countryPar.toLowerCase() + "_data.json");
        passengerJsonList.add(passengerData);
    }
}
