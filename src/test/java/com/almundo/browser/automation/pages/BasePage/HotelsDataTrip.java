package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.pages.ResultsPage.HotelsResultsPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;


/**
 * Created by leandro.efron on 5/12/2016.
 */
public class HotelsDataTrip extends BasePage {

    public HotelsDataTrip(WebDriver driver) {
        super(driver);
    }

    //############################################### Locators ##############################################

    @FindBy(id = "destination-hotels")
    private WebElement destinationHotelTxt;

    @FindBy(css = "input[name^='am-range-datepicker-from']")
    private WebElement checkinCalendar;

    @FindBy(css = "input[name^='am-range-datepicker-to']")
    private WebElement checkoutCalendar;

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

    /******************************* Getters *******************************/

    public WebElement getCheckinCalendar(){ return checkinCalendar;}

    public WebElement getCheckoutCalendar(){return checkoutCalendar;}

    //############################################### Actions ###############################################

    public HotelsDataTrip setDestination(String destinationAuto, String destinationFull) {
        PageUtils.waitElementForVisibility(driver, destinationHotelTxt, 10, "Destination text field");
        logger.info("Entering Hotel Destination: [" + destinationFull + "]");
        destinationHotelTxt.clear();
        destinationHotelTxt.sendKeys(destinationAuto);
        selectAutoCompleteOption(destinationFull);
        return this;
    }

    public HotelsDataTrip selectPassenger(int adults, int childs, int rooms) {
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

    public HotelsResultsPage clickBuscarBtn() {
        logger.info("Clicking on button: [Buscar]");
        buscarBtn.click();
        return initHotelsResultsPage();
    }
}
