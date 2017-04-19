package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.utils.JsonRestReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.util.List;

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
    private static List<WebElement> docExpirationList = null;
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

    @FindBy(id = "birthday")
    private WebElement birthday;

    @FindBy(id = "gender")
    private WebElement gender;

    @FindBy(id = "nationality")
    private WebElement nationality;

    //############################################### Actions ###############################################

    public PassengerSectionV3 populatePassengerSection(JSONArray passengerList) throws IOException, ParseException {
        logger.info("------------- Filling Passenger Section -------------");
        JsonRestReader inputDef = new JsonRestReader("http://apipr.almundo.it:8080/api/v3/cart/58f7cb03e4b0481e291dd3b6/input-definitions?site=ARG&language=es");

        JSONObject passengerInfo;
        setFirstNameList();
        setLastNameList();
        setDocTypeList();
        setDocNumberList();
        setBirthdayList();
        setGenderList();
        setNationalityList();

        for(int passengerIndex = 0; passengerIndex <= passengerList.size()-1; passengerIndex++ ){
            logger.info("************ Filling Passenger [" + passengerIndex + "] ************");
            passengerInfo = (JSONObject) passengerList.get(passengerIndex);

            if(inputDef.isRequired("passengers","first_name",passengerIndex)){
            setFirstName(passengerIndex, passengerInfo.get("first_name").toString());}

            if(inputDef.isRequired("passengers","last_name",passengerIndex)){
            setlastName(passengerIndex, passengerInfo.get("last_name").toString());}

            if(inputDef.isRequired("passengers","type",passengerIndex)) {
                setDocumentType(passengerIndex, passengerInfo.get("documentType").toString());}

            if(inputDef.isRequired("passengers","document",passengerIndex)){
                setDocumentNumber(passengerIndex, passengerInfo.get("document_number").toString());}

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
        }
        return this;
    }

    public void setFirstNameList() {
        firstNameList = driver.findElements(By.id("first_name"));
    }

    public void setLastNameList() {
        lastNameList = driver.findElements(By.id("last_name"));
    }

    public void setDocTypeList() {
        docTypeList = driver.findElements(By.cssSelector("passengers-form #document_type"));
    }

    public void setDocNumberList() {
        docNumberList = driver.findElements(By.cssSelector("passengers-form #number"));
    }

    public void setBirthdayList() {
        dayBirthdayList = driver.findElements(By.cssSelector(".section.persons .day"));
        monthBirthdayList = driver.findElements(By.cssSelector(".section.persons .month"));
        yearBirthdayList = driver.findElements(By.cssSelector(".section.persons .year"));
    }

    public void setGenderList() {
        genderList = driver.findElements(By.id("gender"));
    }

    public void setNationalityList() {
        nationalityList = driver.findElements(By.id("nationality"));
    }

    public PassengerSectionV3 setFirstName(int index, String firstName){
        logger.info("Entering Nombre/s: [" + firstName + "]");
        firstNameList.get(index).clear();
        firstNameList.get(index).sendKeys(firstName);
        return this;
    }

    public PassengerSectionV3 setlastName(int index, String lastName){
        logger.info("Entering Apellido/s: [" + lastName + "]");
        lastNameList.get(index).clear();
        lastNameList.get(index).sendKeys(lastName);
        return this;
    }

    public PassengerSectionV3 setDocumentType(int index, String documentType){
        logger.info("Selecting Tipo de documento: [" + documentType + "]");
        Select tipoDeDocumento = new Select(docTypeList.get(index));
        tipoDeDocumento.selectByVisibleText(documentType);
        return this;
    }

    public PassengerSectionV3 setDocumentNumber(int index, String docNumber){
        logger.info("Entering Número: [" + docNumber + "]");
        docNumberList.get(index).clear();
        docNumberList.get(index).sendKeys(docNumber);
        return this;
    }

    public PassengerSectionV3 setDocumentEmisor(int index, String documentEmisorPassenger){
        List<WebElement> documentEmisorList = driver.findElements(By.id("documentEmisor"));
        logger.info("Selecting País emisor del pasaporte: [" + documentEmisorPassenger + "]");
        Select paisEmisorDelPasaporte = new Select(documentEmisorList.get(index));
        paisEmisorDelPasaporte.selectByVisibleText(documentEmisorPassenger);
        return this;
    }

    public PassengerSectionV3 setDocumentExpiration(int index, String documentExpirationPassenger){
        List<WebElement> documentExpirationList = driver.findElements(By.id("documentExpiration"));
        logger.info("Entering Fecha de venc. del documento: [" + documentExpirationPassenger + "]");
        documentExpirationList.get(index).clear();
        documentExpirationList.get(index).sendKeys(documentExpirationPassenger);
        return this;
    }

    public PassengerSectionV3 setBirthDay(int index, String birthday){

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

    public PassengerSectionV3 setGender(int index, String gender){
        logger.info("Selecting Sexo: [" + gender + "]");
        Select sexo = new Select(genderList.get(index));
        sexo.selectByVisibleText(gender);
        return this;
    }

    public PassengerSectionV3 setNationality(int index, String nationality){
        logger.info("Selecting Nacionalidad: [" + nationality + "]");
        Select nacionalidad = new Select(nationalityList.get(index));
        nacionalidad.selectByVisibleText(nationality);
        return this;
    }
}
