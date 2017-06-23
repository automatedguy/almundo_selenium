package com.almundo.browser.automation.pages.AlmundoTrips.DataTrips;

import com.almundo.browser.automation.pages.BasePage.BasePage;
import com.almundo.browser.automation.pages.ResultsPage.HotelsResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;


/**
 * Created by leandro.efron on 5/12/2016.
 */
public class TripsHotelsData extends BasePage {

    public TripsHotelsData(WebDriver driver) {
        super(driver);
    }

    /***************************** Locators *****************************/

    @FindBy(css = ".hotels-search-form am-autocomplete #destination")
    public WebElement destinationHotelTxt;

    /****** Hotels Calendars ******/

    @FindBy(id = "departure")
    public WebElement checkinCalendarHotels;

    @FindBy(id = "return")
    public WebElement checkoutCalendarHotels;

    @FindBy(css = ".row-rooms>.sub")
    public WebElement subRoomBtn;

    @FindBy(css = ".row-rooms>.add")
    public WebElement addRoomBtn;

    @FindBy(css = ".row-room-details>div:nth-of-type(1)>.sub")
    public WebElement subAdultBtn;

    @FindBy(css = ".row-room-details>div:nth-of-type(1)>.add")
    public WebElement addAdultBtn;

    @FindBy(css = ".row-room-details>div:nth-of-type(2)>.sub")
    public WebElement subChildBtn;

    @FindBy(css = ".row-room-details>div:nth-of-type(2)>.add")
    public WebElement addChildBtn;

    @FindBy(css = ".hotels-search-form .btn.btn-primary.btn-submit")
    public WebElement buscarHotelesBtn;

    /***************************** Actions *****************************/

    public TripsHotelsData setDestination(String destinationAuto, String destinationFull) {
        PageUtils.waitElementForVisibility(driver, destinationHotelTxt, 10, "Destination text field");
        logger.info("Entering Hotel Destination: [" + destinationFull + "]");
        destinationHotelTxt.clear();
        destinationHotelTxt.sendKeys(destinationAuto);
        selectAutoCompleteOption(destinationFull);
        return this;
    }

    public TripsHotelsData selectPassenger(int adults, int childs, int rooms) {
        personasTxt.click();
        if (adults>2){
            for(int i=1; i<adults; i++) {
                logger.info("Adding: [1 adult]");
                addAdultBtn.click();
            }
        }

        if (childs>0){
            for(int i=0; i<childs; i++) {
                logger.info("Adding: [1 child]");
                addChildBtn.click();
            }

            List<WebElement> dropDownList = driver.findElements(By.cssSelector(".row-yougers-details>.input--block"));
            for(int i=0; i<childs; i++) {
                Random rand = new Random();
                int randomNum = rand.nextInt((17 - 1) + 1) + 1;
                Select dropdown = new Select (dropDownList.get(i));
                dropdown.selectByVisibleText(String.valueOf(randomNum));
            }
        }
        listoBtn.click();

        logger.info("Total Adults: [" + adults + "]");
        logger.info("Total Childs: [" + childs + "]");
        return this;
    }

    public HotelsResultsPage clickBuscarHotelesBtn(){
        logger.info("Clicking on [Buscar] button (For Hotels)");
        waitImplicitly(1000);
        buscarHotelesBtn.click();
        return initHotelsResultsPage();
    }
}
