package com.almundo.browser.automation.base;

import com.almundo.browser.automation.utils.PageUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class PageBaseSetup {

    protected WebDriver driver;

    public static Logger logger = Logger.getLogger( TestBaseSetup.class );

    public boolean nothingFound(WebDriver driver){
        if(!driver.findElements(By.linkText("Ver listado de sucursales")).isEmpty()){
            logger.warn("No Results found - acercate a nuestras sucursales");
            return true;
        }
        else{
            return false;
        }
    }

    public boolean noVacancy(WebDriver driver){
        try {
            By noVacancyMsg = By.xpath("//span[contains(.,'Lo sentimos. No encontramos disponibilidad para tu búsqueda')]");
            PageUtils.waitForVisibilityOfElementLocated(driver, 5, noVacancyMsg);

        } catch (TimeoutException timeOut){
            logger.info("There is vacancy.");
            return false;
        }
        return true;
    }


}
