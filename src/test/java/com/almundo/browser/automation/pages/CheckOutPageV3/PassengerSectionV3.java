package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.utils.ThanksPageAssertInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

import static com.almundo.browser.automation.utils.PageUtils.randomString;

/**
 * Created by gabrielcespedes on 12/12/16.
 */
public class PassengerSectionV3 extends CheckOutPageV3 {

    public PassengerSectionV3(WebDriver driver) {
        super(driver);
    }

    public static JSONArray passengerJsonList = new JSONArray();

    private static List<WebElement> firstNameList = null;
    private static List<WebElement> lastNameList = null;
    private static List<WebElement> docTypeList = null;
    private static List<WebElement> docNumberList = null;
    private static List<WebElement> docEmisorList = null;
    private static List<WebElement> dayDocExpirationList = null;
    private static List<WebElement> monthDocExpirationList = null;
    private static List<WebElement> yearDocExpirationList = null;
    private static List<WebElement> dayBirthdayList = null;
    private static List<WebElement> monthBirthdayList = null;
    private static List<WebElement> yearBirthdayList = null;
    private static List<WebElement> genderList = null;
    private static List<WebElement> nationalityList = null;

    //############################################### Locators ##############################################

    @FindBy(id = "first_name")
    private WebElement first_name;

    @FindBy(id = "last_name")
    private WebElement last_name;

    @FindBy(css = "passengers-form #document_type")
    private WebElement doc_type;

    @FindBy(css = "passengers-form #number")
    private WebElement doc_number;

    @FindBy(id = "document_emisor")
    private WebElement doc_emisor;

    @FindBy(css = "#passengers-section passengers-form .container-day select")
    private WebElement birthday;

    @FindBy(id = "gender")
    private WebElement gender;

    @FindBy(id = "nationality")
    private WebElement nationality;

    //############################################### Actions ###############################################

    public PassengerSectionV3 populatePassengerSection(JSONArray passengerList) {
        logger.info("------------- Filling Passenger Section -------------");

        JSONObject passengerInfo;
        setFirstNameList();
        setLastNameList();
        setDocTypeList();
        setDocNumberList();
        setDocEmisorList();
        setDocExpirationList();
        setBirthdayList();
        setGenderList();
        setNationalityList();

        String randomFirstName = "";
        String randomLastName = "";
        String documentNumber = "";

        logger.info("Initializing Passenger List Info for Assertions.");
        thanksPageAssertInfo.initPassengerList();

        for(int passengerIndex = 0; passengerIndex <= passengerList.size()-1; passengerIndex++ ){

            logger.info("************ Filling Passenger [" + passengerIndex + "] ************");
            passengerInfo = (JSONObject) passengerList.get(passengerIndex);

            if(inputDef.isRequired("passengers","first_name",passengerIndex)) {
                randomFirstName = randomString(15);
                setFirstName(passengerIndex, randomFirstName);}

            if(inputDef.isRequired("passengers","last_name",passengerIndex)){
                randomLastName = randomString(15);
                setlastName(passengerIndex, randomLastName);}

            if(inputDef.isRequired("passengers","document", passengerIndex)) {
                setDocumentType(passengerIndex, passengerInfo.get("documentType").toString());}

            if(inputDef.isRequired("passengers","document", passengerIndex)){
                documentNumber = passengerInfo.get("document_number").toString();
                setDocumentNumber(passengerIndex, documentNumber);}

            if(inputDef.isRequired("passengers","document_emisor",passengerIndex)) {
                setDocumentEmisor(passengerIndex, passengerInfo.get("document_emisor").toString());}

            if(inputDef.isRequired("passengers","document_expiration",passengerIndex)) {
                setDocumentExpiration(passengerIndex, passengerInfo.get("document_expiration").toString());}

            if(inputDef.isRequired("passengers","birthday",passengerIndex)) {
                setBirthDay(passengerIndex, passengerInfo.get("birthday").toString());}

            if(inputDef.isRequired("passengers","gender",passengerIndex)) {
                setGender(passengerIndex, passengerInfo.get("gender").toString());}

            if(inputDef.isRequired("passengers","nationality",passengerIndex)) {
                setNationality(passengerIndex, passengerInfo.get("nationality").toString());}

            thanksPageAssertInfo.passengersList.add(new ThanksPageAssertInfo.Passenger(randomFirstName + " " + randomLastName , documentNumber));
        }
        return this;
    }

    private void setFirstNameList() {
        firstNameList = driver.findElements(By.id("first_name"));
    }

    private void setLastNameList() {
        lastNameList = driver.findElements(By.id("last_name"));
    }

    private void setDocTypeList() {
        docTypeList = driver.findElements(By.cssSelector("passengers-form #document_type"));
    }

    private void setDocNumberList() {
        docNumberList = driver.findElements(By.cssSelector("passengers-form #number"));
    }

    private void setDocEmisorList() {
        docEmisorList = driver.findElements(By.id("document_emisor"));
    }

    private void setDocExpirationList() {
        dayDocExpirationList = driver.findElements(By.cssSelector("#passengers-section passengers-form div:nth-child(2) > div:nth-child(4) .container-day select"));
        monthDocExpirationList = driver.findElements(By.cssSelector("#passengers-section passengers-form div:nth-child(2) > div:nth-child(4) .container-month select"));
        yearDocExpirationList = driver.findElements(By.cssSelector("#passengers-section passengers-form div:nth-child(2) > div:nth-child(4) .container-year select"));
    }

    private void setBirthdayList() {
        dayBirthdayList = driver.findElements(By.cssSelector("#passengers-section passengers-form div:nth-child(3) > div:nth-child(1) .container-day select"));
        monthBirthdayList = driver.findElements(By.cssSelector("#passengers-section passengers-form div:nth-child(3) > div:nth-child(1) .container-month select"));
        yearBirthdayList = driver.findElements(By.cssSelector("#passengers-section passengers-form div:nth-child(3) > div:nth-child(1) .container-year select"));
    }

    private void setGenderList() {
        genderList = driver.findElements(By.id("gender"));
    }

    private void setNationalityList() {
        nationalityList = driver.findElements(By.id("nationality"));
    }

    private PassengerSectionV3 setFirstName(int index, String firstName){
        logger.info("Entering Nombre/s: [" + firstName + "]");
        firstNameList.get(index).clear();
        firstNameList.get(index).sendKeys(firstName);
        return this;
    }

    private PassengerSectionV3 setlastName(int index, String lastName){
        logger.info("Entering Apellido/s: [" + lastName + "]");
        lastNameList.get(index).clear();
        lastNameList.get(index).sendKeys(lastName);
        return this;
    }

    private PassengerSectionV3 setDocumentType(int index, String documentType){
        logger.info("Selecting Tipo de documento: [" + documentType + "]");
        Select tipoDeDocumento = new Select(docTypeList.get(index));
        tipoDeDocumento.selectByVisibleText(documentType);
        return this;
    }

    private PassengerSectionV3 setDocumentNumber(int index, String docNumber){
        logger.info("Entering Número: [" + docNumber + "]");
        docNumberList.get(index).clear();
        docNumberList.get(index).sendKeys(docNumber);
        return this;
    }

    private PassengerSectionV3 setDocumentEmisor(int index, String country){
        List<WebElement> documentEmisorList = driver.findElements(By.id("document_emisor"));
        logger.info("Selecting País emisor del pasaporte: [" + country + "]");
        Select countryIssuingPassport = new Select(documentEmisorList.get(index));
        countryIssuingPassport.selectByVisibleText(country);
        return this;
    }

    private PassengerSectionV3 setDocumentExpiration(int index, String docExpirationDay){

        String day;
        String month;

        if(String.valueOf(docExpirationDay.charAt(0)).equals("0")) {
            day = String.valueOf(docExpirationDay.charAt(1));
        } else {
            day = String.valueOf(docExpirationDay.charAt(0)) + String.valueOf(docExpirationDay.charAt(1));
        }

        if(String.valueOf(docExpirationDay.charAt(3)).equals("0")) {
            month = String.valueOf(docExpirationDay.charAt(4));
        } else {
            month = String.valueOf(docExpirationDay.charAt(3)) + String.valueOf(docExpirationDay.charAt(4));
        }

        String year = String.valueOf(docExpirationDay.charAt(6)) + String.valueOf(docExpirationDay.charAt(7)) + String.valueOf(docExpirationDay.charAt(8)) + String.valueOf(docExpirationDay.charAt(9));

        logger.info("Entering Fecha de venc. del documento: [" + docExpirationDay + "]");
        Select dayExpiration = new Select (dayDocExpirationList.get(index));
        dayExpiration.selectByVisibleText(day);

        Select monthBirthday = new Select (monthDocExpirationList.get(index));
        monthBirthday.selectByValue(month);

        Select yearBirthday = new Select (yearDocExpirationList.get(index));
        yearBirthday.selectByVisibleText(year);

        return this;
    }

    private PassengerSectionV3 setBirthDay(int index, String birthday){

        String day;
        String month;

        if(String.valueOf(birthday.charAt(0)).equals("0")) {
            day = String.valueOf(birthday.charAt(1));
        } else {
                day = String.valueOf(birthday.charAt(0)) + String.valueOf(birthday.charAt(1));
        }

        if(String.valueOf(birthday.charAt(3)).equals("0")) {
            month = String.valueOf(birthday.charAt(4));
        } else {
            month = String.valueOf(birthday.charAt(3)) + String.valueOf(birthday.charAt(4));
        }

        String year = String.valueOf(birthday.charAt(6)) + String.valueOf(birthday.charAt(7)) + String.valueOf(birthday.charAt(8)) + String.valueOf(birthday.charAt(9));

        logger.info("Entering Fecha de Nacimiento: [" + birthday + "]");
        Select dayBirthday = new Select (dayBirthdayList.get(index));
        dayBirthday.selectByVisibleText(day);

        Select monthBirthday = new Select (monthBirthdayList.get(index));
        monthBirthday.selectByValue(month);

        Select yearBirthday = new Select (yearBirthdayList.get(index));
        yearBirthday.selectByVisibleText(year);

        return this;
    }

    private PassengerSectionV3 setGender(int index, String gender){
        logger.info("Selecting Sexo: [" + gender + "]");
        Select sexo = new Select(genderList.get(index));
        sexo.selectByVisibleText(gender);
        return this;
    }

    private PassengerSectionV3 setNationality(int index, String nationality){
        logger.info("Selecting Nacionalidad: [" + nationality + "]");
        Select nacionalidad = new Select(nationalityList.get(index));
        nacionalidad.selectByVisibleText(nationality);
        return this;
    }
}
