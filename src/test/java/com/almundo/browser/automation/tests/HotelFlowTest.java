package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.flows.HotelFlow;
import com.almundo.browser.automation.locators.flows.BaseFlowMap;
import com.almundo.browser.automation.locators.flows.HotelFlowMap;
import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.pages.BasePage.HotelesDataTrip;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class HotelFlowTest extends TestBaseSetup {

    public HotelFlow hotelFlow = new HotelFlow(driver);
    private BasePage basePage = null;

    private JSONObject hotelesList = null;
    private JSONObject hotel = null;

    private String destinationAuto;
    private String destinationFull;
    private By destinationFullFinal;
    private int startDate;
    private int endDate;
    private int adults;
    private int childs;
    private int rooms;


    @BeforeClass
    private void getHotelesListDataObject() {
        hotelesList = (JSONObject) dataTestObject.get("hoteles");
    }

    private void getHotelDataObject(String combination) {
        hotel = (JSONObject) hotelesList.get(combination);

        destinationAuto = hotel.get("destinationAuto").toString();
        destinationFull = hotel.get("destinationFull").toString();
        destinationFullFinal = By.xpath(String.format("//span[contains(.,'%s')]", destinationFull));

        startDate = Integer.parseInt(hotel.get("startDate").toString());
        endDate = Integer.parseInt(hotel.get("endDate").toString());

        adults = Integer.parseInt(hotel.get("adults").toString());
        childs = Integer.parseInt(hotel.get("childs").toString());

        rooms = Integer.parseInt(hotel.get("rooms").toString());

    }

    @BeforeMethod
    private void initBasePageObject() {
        basePage = initBasePage();
    }

    @Test
    public void hotelReservationFirstOptionFlow() throws InterruptedException {
        getHotelDataObject("miami_10days_2adults_2childs_1room");

        PageUtils.waitElementForVisibility(driver, basePage.hotelesIcon, 10, "Hoteles icon");
        basePage.hotelesIcon.click();

        basePage.hotelesDataTrip().setDestination(destinationAuto);
        basePage.hotelesDataTrip().selectFromAutoCompleteSuggestions(driver, destinationFullFinal);

        basePage.hotelesDataTrip().selectDateFromCalendar(basePage.hotelesDataTrip().checkinCalendar, startDate);
        basePage.hotelesDataTrip().selectDateFromCalendar(basePage.hotelesDataTrip().checkoutCalendar, endDate);

        //hotelFlow.waitForVisibilityOfElementLocated(driver, 15, BaseFlowMap.HOTELES_ICO.getBy());
        //hotelFlow.clickOn(driver, BaseFlowMap.HOTELES_ICO.getBy());

        //hotelFlow.enterText(driver, destinationAuto, HotelFlowMap.DESTINATION_TXT.getBy());
        //hotelFlow.waitForVisibilityOfElementLocated(driver, 10, destinationFullFinal);
        //hotelFlow.selectFromAutoCompleteSuggestions(driver, destinationFullFinal);

        //hotelFlow.selectDateFromCalendar(driver, HotelFlowMap.HOTEL_FECHA_SALIDA_CAL.getBy(), startDate);
        //hotelFlow.selectDateFromCalendar(driver, HotelFlowMap.HOTEL_FECHA_REGRESO_CAL.getBy(), endDate);

        //numPassengers = hotelFlow.selectPassenger(driver, adults, childs, rooms);

        numPassengers = basePage.hotelesDataTrip().selectPassenger(driver, adults, childs, rooms);

        basePage.hotelesDataTrip().buscarBtn.click();

        //hotelFlow.clickOn(driver, BaseFlowMap.BUSCAR_BTN.getBy());

        if(hotelFlow.noVacancy(driver)) {
            System.out.println("No Vacancy");
        }
        else {

            hotelFlow.doHotelReservationFlow(driver);
            hotelFlow.paymentPage.populatePaymentInfo(driver, numPassengers);
        }
    }
}
