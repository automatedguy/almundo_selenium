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


//    public PageBaseSetup selectDateFromCalendar(WebDriver driver, By idCalendar, int daysAhead){
//
//        driver.findElement(idCalendar).click();
//        List<WebElement> availableDates = driver.findElements(BaseFlowMap.AVAILABLE_DATES_CAL.getBy());
//        int totalAvailableDates = availableDates.size();
//
//        if(totalAvailableDates >= daysAhead){
//            logger.info("Selected date: " + availableDates.get(daysAhead-1).getText() + " " + driver.findElement(BaseFlowMap.CALENDAR_MONTH.getBy()).getText() + " " + driver.findElement(BaseFlowMap.CALENDAR_YEAR.getBy()).getText());
//            availableDates.get(daysAhead-1).click();
//        }
//        else{
//            daysAhead = daysAhead - totalAvailableDates;
//            driver.findElement(BaseFlowMap.CALENDAR_NEXT_CAL.getBy()).click();
//            List<WebElement> availableDatesNextCal = driver.findElements(BaseFlowMap.AVAILABLE_DATES_CAL.getBy());
//            logger.info("Selected date: " + availableDatesNextCal.get(daysAhead-1).getText() + " " + driver.findElement(BaseFlowMap.CALENDAR_MONTH.getBy()).getText() + " " + driver.findElement(BaseFlowMap.CALENDAR_YEAR.getBy()).getText());
//            availableDatesNextCal.get(daysAhead-1).click();
//        }
//        return this;
//    }

//    public int selectPassenger(WebDriver driver, int adults, int childs) {
//        driver.findElement(BaseFlowMap.PERSONAS_TXT.getBy()).click();
//
//        if (adults>1){
//            for(int i=1; i<adults; i++) {
//                logger.info("Adding 1 adult");
//                driver.findElement(VueloFlowMap.ADD_ADULT_BTN.getBy()).click();
//            }
//        }
//
//        if (childs>0){
//            for(int i=0; i<childs; i++) {
//                logger.info("Adding 1 child");
//                driver.findElement(VueloFlowMap.ADD_CHILD_BTN.getBy()).click();
//            }
//        }
//
//        driver.findElement(BaseFlowMap.LISTO_BTN.getBy()).click();
//        return adults + childs;
//    }

//    public int selectPassenger(WebDriver driver, int adults, int childs, int rooms) {
//        driver.findElement(BaseFlowMap.PERSONAS_TXT.getBy()).click();
//
//        if (adults>2){
//            for(int i=1; i<adults; i++) {
//                logger.info("Adding 1 adult");
//                driver.findElement(HotelFlowMap.ADD_ADULT_BTN.getBy()).click();
//            }
//        }
//
//        if (childs>0){
//            for(int i=0; i<childs; i++) {
//                logger.info("Adding 1 child");
//                driver.findElement(HotelFlowMap.ADD_CHILD_BTN.getBy()).click();
//            }
//        }
//
//        driver.findElement(BaseFlowMap.LISTO_BTN.getBy()).click();
//
//        return adults + childs;
//    }


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
