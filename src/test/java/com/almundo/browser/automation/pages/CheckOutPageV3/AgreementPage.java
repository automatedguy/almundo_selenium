package com.almundo.browser.automation.pages.CheckOutPageV3;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.almundo.browser.automation.utils.Constants.DATA_PATH;

/**
 * Created by gabrielcespedes on 11/05/17.
 */
public class AgreementPage extends CheckOutPageV3 {

    public String TermsAndConditionURL;

    public AgreementPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".epp-ctn--lg.info")
    public WebElement conditionsInfo;

    public String getUrl(){
        TermsAndConditionURL = driver.getCurrentUrl();
        return TermsAndConditionURL;
    }

    public String getCountryUrl(){
        String countryDomain = null;
        switch(countryPar){
            case "ARGENTINA": countryDomain = "almundo.com.ar/legales/condiciones-generales";
            break;
            case "COLOMBIA": countryDomain = "almundo.com.co/legales/politica-de-privacidad";
            break;
            case "MEXICO": countryDomain = "almundo.com.mx/legales/condiciones-generales";
            break;
        }
        return countryDomain;
    }


    public String getCountryAgreementText() {
        String agreementText = "";
        try {
            agreementText = new String(Files.readAllBytes(Paths.get(DATA_PATH + countryPar.toLowerCase() + "_agreement.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return agreementText.replace(" ","");
    }

    public String getAgreement(){
        return conditionsInfo.getText().replace(" ","");
    }

    public AgreementPage closeTermsAndConditions(){
        driver.close();
        return this;
    }
}