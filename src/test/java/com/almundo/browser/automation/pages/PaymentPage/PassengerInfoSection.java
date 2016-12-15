package com.almundo.browser.automation.pages.PaymentPage;

import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by gabrielcespedes on 12/12/16.
 */
public class PassengerInfoSection extends PaymentPage {
    public PassengerInfoSection(WebDriver driver) {
        super(driver);
    }

    //############################################### Actions ###############################################

    public PassengerInfoSection setFirstName(String firstName, String firstNamePassenger){
        PageUtils.waitElementForVisibility(driver, By.id(firstName), 45, "First Name text box");
        WebElement elementToPopulate = driver.findElement(By.id(firstName));
        elementToPopulate.sendKeys(firstNamePassenger);
        return this;
    }

    public PassengerInfoSection setlastName(String lastName, String lastNamePassenger){
        WebElement elementToPopulate = driver.findElement(By.id(lastName));
        elementToPopulate.sendKeys(lastNamePassenger);
        return this;
    }

    public PassengerInfoSection setDocumentType(String documentType, String documentTypePassenger){
        WebElement elementToPopulate = driver.findElement(By.id(documentType));
        Select tipoDeDocumento = new Select(elementToPopulate);
        tipoDeDocumento.selectByVisibleText(documentTypePassenger);
        return this;
    }

    public PassengerInfoSection setDocumentNumber(String documentNumber, String documentNumberPassenger){
        WebElement elementToPopulate = driver.findElement(By.id(documentNumber));
        elementToPopulate.sendKeys(documentNumberPassenger);
        return this;
    }

    public PassengerInfoSection setDocumentEmisor(String documentEmisor, String documentEmisorPassenger){
        WebElement elementToPopulate = driver.findElement(By.id(documentEmisor));
        Select paisEmisorDelPasaporte = new Select(elementToPopulate);
        paisEmisorDelPasaporte.selectByVisibleText(documentEmisorPassenger);
        return this;
    }

    public PassengerInfoSection setDocumentExpiration(String documentExpiration, String documentExpirationPassenger){
        WebElement elementToPopulate = driver.findElement(By.id(documentExpiration));
        elementToPopulate.sendKeys(documentExpirationPassenger);
        return this;
    }

    public PassengerInfoSection setBirthDay(String birthday, String birthdayPassenger){
        WebElement elementToPopulate = driver.findElement(By.id(birthday));
        elementToPopulate.sendKeys(birthdayPassenger);
        return this;
    }

    public PassengerInfoSection setGender(String gender, String genderPassenger){
        WebElement elementToPopulate = driver.findElement(By.id(gender));
        Select sexo = new Select(elementToPopulate);
        sexo.selectByVisibleText(genderPassenger);
        return this;
    }

    public PassengerInfoSection setNationality(String nationality, String nationalityPassenger){
        WebElement elementToPopulate = driver.findElement(By.id(nationality));
        Select nacionalidad = new Select(elementToPopulate);
        nacionalidad.selectByVisibleText(nationalityPassenger);
        return this;
    }
}
