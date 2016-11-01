package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.locators.pages.HomePageMap;
import com.almundo.browser.automation.locators.pages.VueloHotelPageMap;
import com.almundo.browser.automation.pages.HomePage;
import com.almundo.browser.automation.pages.VueloHotelPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by gabrielcespedes on 01/11/16.
 */
public class VueloHotelPageTest extends TestBaseSetup {

    private WebDriver driver;

    String TRIPS_FECHA_SALIDA_CAL = "departure-trips";
    String TRIPS_FECHA_REGRESO_CAL = "arrival-trips";

    @BeforeClass
    public void setUp() {
        driver=getDriver();
    }


   /* @Test
    public void calendarTestSample() throws InterruptedException {
        driver.findElement(By.id("checkin-hotels")).click();
        PageUtils.waitForSaucePicture(7000);

        String dayDate = "20";

        String string = String.format("//a[text()='%s']", dayDate );

        System.out.println(string);

        // driver.findElement(By.xpath("//span[@class='ui-icon ui-icon-circle-triangle-e']")).click();

        PageUtils.waitForSaucePicture(7000);

        driver.findElement(By.xpath(string)).click();

        PageUtils.waitForSaucePicture(7000);
    }*/

    @Test
    public void vueloHotelReservationHappyPathTest() throws InterruptedException {
        PageUtils.waitForVisibilityOfElementLocated(driver, 20, HomePageMap.VUELO_HOTEL_ICO.getBy());

        HomePage.vueloHotelTab(driver).click();

        VueloHotelPage.originFlightTxt(driver).sendKeys(TestBaseSetup.originAutoComplete);
        PageUtils.waitForSaucePicture(1000);
        PageUtils.selectFromAutoCompleteSuggestions(driver, VueloHotelPageMap.ORIGIN_FULL_PAR.getBy());

        VueloHotelPage.destinationFlightTxt(driver).sendKeys(TestBaseSetup.destinationAutoComplete);
        PageUtils.waitForSaucePicture(1000);
        PageUtils.selectFromAutoCompleteSuggestions(driver, VueloHotelPageMap.DESTINATION_FULL_PAR.getBy());

        PageUtils.selectDateFromCalendar(driver, TRIPS_FECHA_SALIDA_CAL, departureflight);
        PageUtils.waitForSaucePicture(1000);

        PageUtils.selectDateFromCalendar(driver, TRIPS_FECHA_REGRESO_CAL, returnFlight);
        PageUtils.waitForSaucePicture(1000);

        VueloHotelPage.buscarBtn(driver).click();

        PageUtils.waitForSaucePicture(10000);

    }
}
