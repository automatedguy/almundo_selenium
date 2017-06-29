package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
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

    public AgreementPage(WebDriver driver) {
        super(driver);
    }

    //############################################### Locators ##############################################

    @FindBy(css = ".epp-ctn--lg.info")
    public WebElement agreementInfo;

    //############################################### Actions ##############################################

    /******************* URLs Validations **********************/
    public boolean agreementUrlOk(){
        logger.info("Validating Agreement Page URL.");
        if(getUrl().contains(getCountryUrl())) {
            return true;
        }else{
            return false;
        }
    }

    private String getUrl(){
        logger.info("Getting Current Agreement Page URL From Browser: " + countryPar);
        return driver.getCurrentUrl();
    }

    private String getCountryUrl(){
        String countryDomain = null;
        logger.info("Getting Expected Agreement Page URL For Comparison: " + countryPar);
        switch(countryPar){
            case "ARGENTINA": countryDomain = "almundo.com.ar/legales/condiciones-generales";
            break;
            case "COLOMBIA": countryDomain = "almundo.com.co/legales/condiciones-generales";
            break;
            case "MEXICO": countryDomain = "almundo.com.mx/legales/condiciones-generales";
            break;
        }
        return countryDomain;
    }

    /******************* Agreement Text Validations **********************/
    public boolean agreementOk(){
        logger.info("Validating Agreement Terms and Conditions.");
        if(getAgreement().equals(getCountryAgreement())){
           return true;
        }else{
            return false;
        }
    }

    private String getAgreement(){
        logger.info("Getting The Agreement Text From Page For Comparison.");
        PageUtils.waitElementForVisibility(driver, By.cssSelector(".epp-ctn.cover-top>h1"),10, "Agreement Text.");
        return agreementInfo.getText().replace(" ","");
    }

    private String getCountryAgreement() {
        String agreementText = "";
        logger.info("Getting The Expected Agreement Text For Comparison  From File: " + DATA_PATH + countryPar.toLowerCase() + "_agreement.txt");
        try {
            agreementText = new String(Files.readAllBytes(Paths.get(DATA_PATH + countryPar.toLowerCase() + "_agreement.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return agreementText.replace(" ","");
    }

    public AgreementPage closeAgreementPage(){
        logger.info("Closing Agreement Page Tab.");
        driver.close();
        PageUtils.switchToParentTab(driver);
        return this;
    }
}