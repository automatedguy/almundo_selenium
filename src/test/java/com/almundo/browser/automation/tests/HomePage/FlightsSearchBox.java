package com.almundo.browser.automation.tests.HomePage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.FlightsDataTrip;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.utils.Constants;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.almundo.browser.automation.utils.Constants.FlightType.*;
import static com.almundo.browser.automation.utils.Constants.Messages.MANDATORY_FLD_MSG;

/**
 * Created by leandro.efron on 24/2/2017.
 */
public class FlightsSearchBox extends TestBaseSetup {

    private DataManagement dataManagement = new DataManagement();
    private FlightsDataTrip flightsDataTrip = null;

    private String resultsListCss = ".am-autocomplete__list .ellipsis";
    private int passengerLimit = 9;
    private int flightLegLimit = 5;

    @BeforeClass
    private void initDataLists() {
        dataManagement.getFlightsDataTripList();
    }

    @BeforeMethod
    private void closeLoginPopUp(){
        if (!baseURL.contains("sucursales")) {
            LoginPopUp loginPopUp = initLoginPopUp();
            loginPopUp.clickCloseLoginBtn();
        }
        basePage.clickFlightsBtn();
    }

    /***************************** Test Cases *****************************/

    @Test
    public void originEmpty() {
        logTestTitle("Flights Search Box - Leave origin field empty");

        dataManagement.getRoundTripDataTripItinerary("miami_10days_2adults_2childs_turista");

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        flightsDataTrip.clickBuscarBtn();

        logger.info("Validating message: [" + MANDATORY_FLD_MSG + "]");
        Assert.assertEquals(flightsDataTrip.originMessage.getText(), MANDATORY_FLD_MSG.toString());
    }

    @Test
    public void destinationEmpty() {
        logTestTitle("Flights Search Box - Leave destination field empty");

        dataManagement.getRoundTripDataTripItinerary("miami_10days_2adults_2childs_turista");

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        flightsDataTrip.clickBuscarBtn();

        logger.info("Validating message: [" + MANDATORY_FLD_MSG + "]");
        Assert.assertEquals(flightsDataTrip.destinationMessage.getText(), MANDATORY_FLD_MSG.toString());
    }

    @Test
    public void autocompleteCountry() {
        logTestTitle("Flights Search Box - Autocomplete - Enter country name");
        String country = "Estados Unidos";

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.setOrigin(country);

        PageUtils.waitListContainResults(driver, resultsListCss, 0);

        validateAutocompleteSize(10);
        validateAutocompleteContent(country);
    }

    @Test
    public void autocompleteCity() {
        logTestTitle("Flights Search Box - Autocomplete - Enter city name");
        String city = "Río de Janeiro";

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.setOrigin(city);

        PageUtils.waitListContainResults(driver, resultsListCss, 0);

        validateAutocompleteSize(3);
        validateAutocompleteContent(city);
    }

    @Test
    public void autocompleteCityCode() {
        logTestTitle("Flights Search Box - Autocomplete - Enter city code");
        String cityCode = "NYC";

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.setOrigin(cityCode);

        PageUtils.waitListContainResults(driver, resultsListCss, 0);

        validateAutocompleteSize(1);
        validateAutocompleteContent("Nueva York, Nueva York, Estados Unidos de América");
    }

    @Test
    public void autocompleteAirport() {
        logTestTitle("Flights Search Box - Autocomplete - Enter airport name");
        String airport = "Fiumicino";

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.setOrigin(airport);

        PageUtils.waitListContainResults(driver, resultsListCss, 0);

        validateAutocompleteSize(1);
        validateAutocompleteContent(airport);
    }

    @Test
    public void autocompleteAirportCode() {
        logTestTitle("Flights Search Box - Autocomplete - Enter airport code");
        String airportCode = "JFK";

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.setOrigin(airportCode);

        PageUtils.waitListContainResults(driver, resultsListCss, 0);

        validateAutocompleteSize(1);
        validateAutocompleteContent(airportCode);
    }

    @Test
    public void addHotelToFlight() {
        logTestTitle("Flights Search Box - Select Add Hotel option");

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.clickAddHotelCbk();
        PageUtils.waitImplicitly(2000);

        String productSelected = driver.findElement(By.cssSelector(".item-btn--selected>a")).getText();

        logger.info("Validating Vuelos + Hotel product is selected");
        Assert.assertEquals(productSelected, "Vuelo + Hotel", "Vuelo + Hotel is not selected");

        logger.info("Validating Tipo de Vuelo drop down is not displayed");
        Assert.assertTrue(driver.findElements(By.cssSelector("select[name='type-flights]")).size()<1, "Tipo de Vuelo drop down is displayed");

        logger.info("Validating Clase de Vuelo drop down is not displayed");
        Assert.assertTrue(driver.findElements(By.cssSelector("select[name='class-flights]")).size()<1, "Clase de Vuelo drop down is displayed");
    }

    @Test
    public void flightTypeOptions() {
        logTestTitle("Flights Search Box - Validate flight type options");

        flightsDataTrip = basePage.flightsDataTrip();

        List<String> actualList  = flightsDataTrip.getFlightTypeList();
        List<String> expectedList = Constants.FLIGHT_TYPE_LIST;

        logger.info("Validating flight type options are displayed:");
        Assert.assertTrue((PageUtils.equalLists(actualList, expectedList, driver)), "Displayed options are not correct");
    }

    @Test
    public void childRangeOptions() {
        logTestTitle("Flights Search Box - Validate child range options");

        flightsDataTrip = basePage.flightsDataTrip();

        flightsDataTrip.personasTxt.click();
        flightsDataTrip.addChildBtn.click();

        List<String> actualList  = flightsDataTrip.getChildRangeList(0);
        List<String> expectedList = Constants.CHILD_RANGE_LIST;

        logger.info("Validating child range options are displayed:");
        Assert.assertTrue((PageUtils.equalLists(actualList, expectedList, driver)), "Displayed options are not correct");
    }

    @Test
    public void flightClassOptions() {
        logTestTitle("Flights Search Box - Validate flight class options");

        flightsDataTrip = basePage.flightsDataTrip();

        List<String> actualList  = flightsDataTrip.getFlightClassList();
        List<String> expectedList = Constants.FLIGHT_CLASS_LIST;

        logger.info("Validating flight class options are displayed:");
        Assert.assertTrue((PageUtils.equalLists(actualList, expectedList, driver)), "Displayed options are not correct");
    }

    @Test
    public void passengerLimitAdults() {
        logTestTitle("Flights Search Box - Validate passenger limit for adults");

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.personasTxt.click();

        logger.info("Adding 10 passengers");
        for(int i=1; i<11; i++) {
            logger.info("Adding: [1 adult]");
            flightsDataTrip.addAdultBtn.click();
        }

        int adults = Integer.parseInt(flightsDataTrip.adultsCount.getText());
        int childs = Integer.parseInt(flightsDataTrip.youngersCount.getText());

        logger.info("Validating passenger limit is [" + passengerLimit + "]");
        Assert.assertEquals(adults + childs, passengerLimit, "Passenger limit is incorrect");
    }

    @Test
    public void passengerLimitMix() {
        logTestTitle("Flights Search Box - Validate passenger limit for adults and childs");

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.personasTxt.click();

        logger.info("Adding 10 passengers");
        for(int i=1; i<5; i++) {
            logger.info("Adding: [1 adult]");
            flightsDataTrip.addAdultBtn.click();
        }

        for(int i=1; i<6; i++) {
            logger.info("Adding: [1 child]");
            flightsDataTrip.addChildBtn.click();
        }

        int adults = Integer.parseInt(flightsDataTrip.adultsCount.getText());
        int childs = Integer.parseInt(flightsDataTrip.youngersCount.getText());

        logger.info("Validating passenger limit is [" + passengerLimit + "]");
        Assert.assertEquals(adults + childs, passengerLimit, "Passenger limit is incorrect");
    }

    @Test
    public void multiDestFlightLimit() {
        logTestTitle("Flights Search Box - Validate flight limit for multi destination");

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.selectFlightType(MULTIDESTINATION);

        while(PageUtils.isElementPresent(flightsDataTrip.addLegLnk)) {
            flightsDataTrip.clickAddLegLnk();
        }

        logger.info("Validating flight leg size is [" + flightLegLimit + "}");
        Assert.assertEquals(flightsDataTrip.getLegList().size(), flightLegLimit, "Leg limit is incorrect");

        logger.info("Validating [Agregar Vuelo] link is not displayed");
        Assert.assertFalse(PageUtils.isElementPresent(flightsDataTrip.addLegLnk), "Agregar Vuelo link is displayed");

        while(PageUtils.isElementPresent(flightsDataTrip.removeLegLnk)) {
            flightsDataTrip.clickRemoveLegLnk();
        }

        logger.info("Validating flight leg size is [1]");
        Assert.assertEquals(flightsDataTrip.getLegList().size(), 1, "Flight legs are not removed");

        logger.info("Validating [Eliminar Vuelo] link is not displayed");
        Assert.assertFalse(PageUtils.isElementPresent(flightsDataTrip.removeLegLnk), "Eliminar Vuelo link is displayed");
    }


    /////////////////////////////////// TEST CASES ///////////////////////////////////

    private void validateAutocompleteSize(int value) {
        List<WebElement> autoCompleteList = driver.findElements(By.cssSelector(resultsListCss));

        logger.info("Validating autocomplete list size: [" + value + "]");
        Assert.assertEquals(autoCompleteList.size(), value, "Autocomplete list size is incorrect");
    }

    private void validateAutocompleteContent(String value) {
        List<WebElement> autoCompleteList = driver.findElements(By.cssSelector(resultsListCss));

        logger.info("Validating autocomplete list content: [" + value + "]");
        for (WebElement autoCompleteOption : autoCompleteList) {
            logger.info("Option: [" + autoCompleteOption.getText() + "]");
            Assert.assertTrue(autoCompleteOption.getText().contains(value), "Option does not contain: [" + value + "]");
        }
    }
}
