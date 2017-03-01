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

/**
 * Created by leandro.efron on 24/2/2017.
 */
public class SearchBox extends TestBaseSetup {

    private DataManagement dataManagement = new DataManagement();
    private FlightsDataTrip flightsDataTrip = null;

    private String resultsListCss = ".am-autocomplete__list .ellipsis";

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

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void flights_OriginEmpty() {
        logTestTitle("Flights Search Box - Leave origin field empty - " + countryPar );

        dataManagement.getRoundTripDataTripItinerary("miami_10days_2adults_2childs_turista");

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.setDestination(dataManagement.destinationAuto, dataManagement.destinationFull);
        flightsDataTrip.clickBuscarBtn();

        logger.info("Validating message: [" + Constants.MANDATORY_FLD_MSG + "]");
        Assert.assertEquals(flightsDataTrip.originMessage.getText(), Constants.MANDATORY_FLD_MSG);
    }

    @Test
    public void flights_DestinationEmpty() {
        logTestTitle("Flights Search Box - Leave destination field empty - " + countryPar );

        dataManagement.getRoundTripDataTripItinerary("miami_10days_2adults_2childs_turista");

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.setOrigin(dataManagement.originAuto, dataManagement.originFull);
        flightsDataTrip.clickBuscarBtn();

        logger.info("Validating message: [" + Constants.MANDATORY_FLD_MSG + "]");
        Assert.assertEquals(flightsDataTrip.destinationMessage.getText(), Constants.MANDATORY_FLD_MSG);
    }

    @Test
    public void autocompleteCountry() {
        logTestTitle("Flights Search Box - Autocomplete - Enter country name - " + countryPar );
        String country = "Canadá";

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.setOrigin(country);

        PageUtils.waitListContainResults(driver, resultsListCss, 0);

        validateAutocompleteSize(10);
        validateAutocompleteContent(country);
    }

    @Test
    public void autocompleteCity() {
        logTestTitle("Flights Search Box - Autocomplete - Enter city name - " + countryPar );
        String city = "Río de Janeiro";

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.setOrigin(city);

        PageUtils.waitListContainResults(driver, resultsListCss, 0);

        validateAutocompleteSize(3);
        validateAutocompleteContent(city);
    }

    @Test
    public void autocompleteCityCode() {
        logTestTitle("Flights Search Box - Autocomplete - Enter city code - " + countryPar );
        String cityCode = "NYC";

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.setOrigin(cityCode);

        PageUtils.waitListContainResults(driver, resultsListCss, 0);

        validateAutocompleteSize(1);
        validateAutocompleteContent("Nueva York, Nueva York, Estados Unidos de América");
    }

    @Test
    public void autocompleteAirport() {
        logTestTitle("Flights Search Box - Autocomplete - Enter airport name - " + countryPar );
        String airport = "Fiumicino";

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.setOrigin(airport);

        PageUtils.waitListContainResults(driver, resultsListCss, 0);

        validateAutocompleteSize(1);
        validateAutocompleteContent(airport);
    }

    @Test
    public void autocompleteAirportCode() {
        logTestTitle("Flights Search Box - Autocomplete - Enter airport code - " + countryPar );
        String airportCode = "JFK";

        flightsDataTrip = basePage.flightsDataTrip();
        flightsDataTrip.setOrigin(airportCode);

        PageUtils.waitListContainResults(driver, resultsListCss, 0);

        validateAutocompleteSize(1);
        validateAutocompleteContent(airportCode);
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
