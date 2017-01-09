package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    //############################################### Actions ###############################################

    public PassengerSectionV3 setFirstName(int index, String firstName){
        List<WebElement> firstNameList = driver.findElements(By.id("first_name"));
        PageUtils.waitElementForVisibility(driver, By.id("first_name") , 45, "First Name text box");
        logger.info("Entering Nombre/s: [" + firstName + "]");
        firstNameList.get(index).clear();
        firstNameList.get(index).sendKeys(firstName);
        return this;
    }

    public PassengerSectionV3 setlastName(int index, String lastNamePassenger){
        List<WebElement> lastNameList = driver.findElements(By.id("last_name"));
        logger.info("Entering Apellido/s: [" + lastNamePassenger + "]");
        lastNameList.get(index).clear();
        lastNameList.get(index).sendKeys(lastNamePassenger);
        return this;
    }

    public PassengerSectionV3 setDocumentType(int index, String documentTypePassenger){
        List<WebElement> documentTypeList = driver.findElements(By.name("document_type"));
        logger.info("Selecting Tipo de documento: [" + documentTypePassenger + "]");
        Select tipoDeDocumento = new Select(documentTypeList.get(index));
        tipoDeDocumento.selectByVisibleText(documentTypePassenger);
        return this;
    }

    public PassengerSectionV3 setDocumentNumber(int index, String documentNumberPassenger){
        List<WebElement> documentNumberList = driver.findElements(By.id("number"));
        logger.info("Entering Número: [" + documentNumberPassenger + "]");
        documentNumberList.get(index).clear();
        documentNumberList.get(index).sendKeys(documentNumberPassenger);
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

    public PassengerSectionV3 setBirthDay(int index, String birthdayPassenger){
        List<WebElement> birthDayList = driver.findElements(By.id("birthday"));
        logger.info("Entering Fecha de Nacimiento: [" + birthdayPassenger + "]");
        birthDayList.get(index).clear();
        birthDayList.get(index).sendKeys(birthdayPassenger);
        return this;
    }

    public PassengerSectionV3 setGender(int index, String genderPassenger){
        List<WebElement> genderList = driver.findElements(By.id("gender"));
        logger.info("Selecting Sexo: [" + genderPassenger + "]");
        Select sexo = new Select(genderList.get(index));
        sexo.selectByVisibleText(genderPassenger);
        return this;
    }

    public PassengerSectionV3 setNationality(int index, String nationalityPassenger){
        List<WebElement> nationalityList = driver.findElements(By.name("nationality"));
        logger.info("Selecting Nacionalidad: [" + nationalityPassenger + "]");
        Select nacionalidad = new Select(nationalityList.get(index));
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
