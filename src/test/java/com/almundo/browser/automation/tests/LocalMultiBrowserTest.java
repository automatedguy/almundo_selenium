package com.almundo.browser.automation.tests;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.components.CalendarComponent;
import com.almundo.browser.automation.locators.pages.HomePageMap;
import com.almundo.browser.automation.locators.pages.PaymentPageMap;
import com.almundo.browser.automation.pages.HomePage;
import com.almundo.browser.automation.pages.PaymentPage;
import com.almundo.browser.automation.pages.VuelosPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
    public void vuelosReservationHappyPathTest() throws InterruptedException {
        PageUtils.waitForVisibilityOfElementLocated(driver, 10, HomePageMap.HOTELES_ICO.getBy());

        PageUtils.waitForVisibilityOfElementLocated(driver, 30, HomePageMap.VUELOS_ICO.getBy());
        HomePage.vuelosTab(driver).click();

        VuelosPage.vuelosPageOriginFlightsTxtBox(driver).sendKeys("Buenos Aires, Buenos Aires, Argentina");
        PageUtils.waitForSaucePicture(2000);
        VuelosPage.vuelosPageOriginFlightsTxtBox(driver).sendKeys(Keys.RETURN);

        VuelosPage.vuelosPageDestinationFlightsTxtBox(driver).sendKeys("Las Vegas, Nevada, Estados Unidos de América");
        PageUtils.waitForSaucePicture(2000);
        VuelosPage.vuelosPageDestinationFlightsTxtBox(driver).sendKeys(Keys.RETURN);

        VuelosPage.tipoVueloDdl(driver).click();

        /* Normalize in one method Seleccionar IDA */
        CalendarComponent.salidaCalendar(driver).click();
        CalendarComponent.salidaTriangleCalendar(driver).click();
        PageUtils.waitForSaucePicture(3000);
        CalendarComponent.salidaDateCalendar(driver).click();
        PageUtils.waitForSaucePicture(3000);

        /* Normalize in one method Seleccionar VUELTA */
        CalendarComponent.regresoCalendar(driver).click();
        CalendarComponent.regresoTriangleCalendar(driver).click();
        PageUtils.waitForSaucePicture(3000);
        CalendarComponent.regresoDateCalendar(driver).click();
        PageUtils.waitForSaucePicture(3000);

        VuelosPage.vuelosPageBuscarBtn(driver).click();

        PageUtils.waitForSaucePicture(20000);

        VuelosPage.comprarTickets(driver);

        PageUtils.waitForSaucePicture(20000);

        PaymentPage.populatePassenger(driver, Integer.valueOf(TestBaseSetup.numPassengers));

        PageUtils.waitForSaucePicture(20000);

        PageUtils.moveToElement(driver, PaymentPageMap.VER_BANCOS_LNK.getBy());

        PaymentPage.populateCreditCardPayments(driver);

        PaymentPage.populateCreditCardOwnerData(driver);

        PaymentPage.populateBillingInformation(driver);

        PaymentPage.acceptTermsConditions(driver);

        PaymentPage.comprarBtn(driver).click();

        PageUtils.waitForSaucePicture(20000);
    }
}
