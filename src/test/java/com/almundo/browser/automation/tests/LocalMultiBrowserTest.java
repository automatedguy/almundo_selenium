package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.locators.HomePageMap;
import com.almundo.browser.automation.locators.HotelesPageMap;
import com.almundo.browser.automation.locators.testsmaps.HotelesTestMap;
import com.almundo.browser.automation.pages.HomePage;
import com.almundo.browser.automation.pages.HotelesPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.TimeoutException;

/**
 * Created by gabrielcespedes on 26/10/16.
 */
public class LocalMultiBrowserTest extends TestBaseSetup {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver=getDriver();
    }

    @Test
    public void hotelesReservationHappyPathTest() throws InterruptedException {
        PageUtils.waitForVisibilityOfElementLocated(driver, 10, HomePageMap.HOTELES_ICO.getBy());

        HomePage.hotelesTab(driver).click();

        PageUtils.waitForVisibilityOfElementLocated(driver, 10, HotelesPageMap.DESTINATION_TXT.getBy());

        HotelesPage.hotelDestinationTxtBox(driver).sendKeys("Rio");

        PageUtils.waitForVisibilityOfElementLocated(driver, 10, HotelesTestMap.DESTINATION_CITY_SUG.getBy());

        HotelesPage.selectCityFromAutoCompleteSuggestions(driver, HotelesTestMap.DESTINATION_CITY_SUG.getBy());

        HotelesPage.buscarBtn(driver).click();

        try {
            PageUtils.waitForVisibilityOfElementLocated(driver, 5, HotelesPageMap.VER_HOTEL_BTN.getBy());
            HotelesPage.verHotelBtn(driver).click();
            PageUtils.waitForSaucePicture(10000);
        }
        catch (TimeoutException timeOut) {
            PageUtils.waitForVisibilityOfElementLocated(driver, 5, HotelesPageMap.VER_HABITACIONES_BTN.getBy());
            HotelesPage.verHabitacionesBtn(driver).click();
            PageUtils.waitForSaucePicture(10000);
        }

        try {
            PageUtils.waitForVisibilityOfElementLocated(driver, 5, HotelesPageMap.VER_HABITACIONES_BTN.getBy());
            HotelesPage.verHabitacionesBtn(driver).click();
            PageUtils.waitForSaucePicture(10000);
        }
        catch (TimeoutException timeOut) {
            PageUtils.waitForVisibilityOfElementLocated(driver, 5, HotelesPageMap.RESERVAR_AHORA_BTN.getBy());
            HotelesPage.reservarAhoraBtn(driver).click();
            PageUtils.waitForSaucePicture(10000);
        }

        try {
            PageUtils.waitForVisibilityOfElementLocated(driver, 5, HotelesPageMap.RESERVAR_AHORA_BTN.getBy());
            HotelesPage.reservarAhoraBtn(driver).click();
            PageUtils.waitForSaucePicture(10000);
        }
        catch (TimeoutException timeOut) {
            System.out.println("The other reservation flow :)");
            try{
                PageUtils.waitForVisibilityOfElementLocated(driver, 5, HotelesPageMap.RESERVAR_AHORA2_BTN.getBy());
                HotelesPage.reservarAhora2Btn(driver).click();
                PageUtils.waitForSaucePicture(10000);
            }
            catch (TimeoutException timeOut2) {
                System.out.println("Something is wrong in the test flow");
            }
        }
        finally {
            PaymentPage.populatePassenger(driver, 2);
            PageUtils.waitForSaucePicture(10000);
        }

    }
}
