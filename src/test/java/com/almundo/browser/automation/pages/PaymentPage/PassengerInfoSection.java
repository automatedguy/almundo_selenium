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

    public PassengerInfoSection setFirstName(String firstName){
        PageUtils.waitElementLocatedforVisibility(driver, By.id(firstName), 45, "First Name text box");
        WebElement elementToPopulate = driver.findElement(By.id(firstName));
        elementToPopulate.sendKeys("Nombre");
        return this;
    }

    public PassengerInfoSection setlastName(String lastName){
        WebElement elementToPopulate = driver.findElement(By.id(lastName));
        elementToPopulate.sendKeys("Apellido");
        return this;
    }

    public PassengerInfoSection setDocumentType(String documentTypeElement, String documentType){
        WebElement elementToPopulate = driver.findElement(By.id(documentTypeElement));
        Select tipoDeDocumento = new Select(elementToPopulate);
        tipoDeDocumento.selectByVisibleText(documentType);
        return this;
    }

    public PassengerInfoSection setDocumentNumber(String documentNumber){
        WebElement elementToPopulate = driver.findElement(By.id(documentNumber));
        elementToPopulate.sendKeys("123456789");
        return this;
    }

    public PassengerInfoSection setDocumentEmisor(String documentEmisor){
        WebElement elementToPopulate = driver.findElement(By.id(documentEmisor));
        Select paisEmisorDelPasaporte = new Select(elementToPopulate);
        paisEmisorDelPasaporte.selectByVisibleText("Argentina");
        return this;
    }

    public PassengerInfoSection setDocumentExpiration(String documentExpiration){
        WebElement elementToPopulate = driver.findElement(By.id(documentExpiration));
        elementToPopulate.sendKeys("25/12/2017");
        return this;
    }

    public PassengerInfoSection setBirthDay(String birthday, String numeroPasajero){
        String typePassenger;
        WebElement elementToPopulate = driver.findElement(By.id(birthday));
        typePassenger = driver.findElement(By.cssSelector(".passenger-ctn:nth-of-type(" + numeroPasajero + ")>.passenger__info__detail>div:nth-of-type(1)>h3>span:nth-of-type(2)")).getText();
        if (typePassenger.equals("Adulto")){
            elementToPopulate.sendKeys("09/09/1979");
        } else if (typePassenger.equals("Niño")){
            elementToPopulate.sendKeys("09/09/2010");
        } else {
            elementToPopulate.sendKeys("09/09/2015");
        }
        return this;
    }

    public PassengerInfoSection setGender(String gender){
        WebElement elementToPopulate = driver.findElement(By.id(gender));
        Select sexo = new Select(elementToPopulate);
        sexo.selectByVisibleText("Femenino");
        return this;
    }

    public PassengerInfoSection setNationality(String nationality){
        WebElement elementToPopulate = driver.findElement(By.id(nationality));
        Select nacionalidad = new Select(elementToPopulate);
        nacionalidad.selectByVisibleText("Argentina");
        return this;
    }
}
