package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Created by gabrielcespedes on 12/12/16.
 */
public class PassengerSectionV3 extends CheckOutPage {

    public PassengerSectionV3(WebDriver driver) {
        super(driver);
    }

    private static JSONObject passengersList = null;
    public static JSONObject passengerData = null;
    public static JSONArray passengerJsonList = new JSONArray();

    private static List<WebElement> firstNameList = null;
    private static List<WebElement> lastNameList = null;
    private static List<WebElement> docTypeList = null;
    private static List<WebElement> docNumberList = null;
    private static List<WebElement> docEmisorList = null;
    private static List<WebElement> docExpirationList = null;
    private static List<WebElement> birthdayList = null;
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
        birthdayList = driver.findElements(By.id("birthday"));
    }

    public void setGenderList() {
        genderList = driver.findElements(By.id("gender"));
    }

    public void setNationalityList() {
        nationalityList = driver.findElements(By.id("nationality"));
    }


    public PassengerSectionV3 setFirstName(int index, String firstName){
        PageUtils.waitElementForVisibility(driver, first_name, 45, "First Name text box");
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
        logger.info("Entering Fecha de Nacimiento: [" + birthday + "]");
        birthdayList.get(index).clear();
        birthdayList.get(index).sendKeys(birthday);
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


    public static void getPassengersList()  {
        passengersList = JsonRead.getJsonDataObject(jsonDataObject, "passengers", countryPar.toLowerCase() + "_data.json");
    }

    public static void getPassengerData(String dataSet)  {
        passengerData = JsonRead.getJsonDataObject(passengersList, dataSet, countryPar.toLowerCase() + "_data.json");
        passengerJsonList.add(passengerData);
    }
}
