package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.flows.HotelFlow;
import com.almundo.browser.automation.locators.flows.BaseFlowMap;
import com.almundo.browser.automation.locators.flows.HotelFlowMap;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 04/11/16.
 */

public class HotelFlowTest extends TestBaseSetup {

    public HotelFlow hotelFlow = new HotelFlow(driver);

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

    @Test
    public void hotelReservationFirstOptionFlow() throws InterruptedException {
        getHotelDataObject("local_10days_2adults_1room");

        hotelFlow.waitForVisibilityOfElementLocated(driver, 15, BaseFlowMap.HOTELES_ICO.getBy());
        hotelFlow.clickOn(driver, BaseFlowMap.HOTELES_ICO.getBy());

        hotelFlow.enterText(driver, destinationAuto, HotelFlowMap.DESTINATION_TXT.getBy());
        hotelFlow.waitForVisibilityOfElementLocated(driver, 10, destinationFullFinal);
        hotelFlow.selectFromAutoCompleteSuggestions(driver, destinationFullFinal);

        hotelFlow.selectDateFromCalendar(driver, HotelFlowMap.HOTEL_FECHA_SALIDA_CAL.getBy(), startDate);
        hotelFlow.selectDateFromCalendar(driver, HotelFlowMap.HOTEL_FECHA_REGRESO_CAL.getBy(), endDate);

        numPassengers = hotelFlow.selectPassenger(driver, adults, childs, rooms);

        hotelFlow.clickOn(driver, BaseFlowMap.BUSCAR_BTN.getBy());

        if(hotelFlow.noVacancy(driver)) {
            System.out.println("No Vacancy");
        }
        else {
            hotelFlow.doHotelReservationFlow(driver);
            hotelFlow.paymentPage.populatePaymentInfo(driver, numPassengers);
        }
    }
}
