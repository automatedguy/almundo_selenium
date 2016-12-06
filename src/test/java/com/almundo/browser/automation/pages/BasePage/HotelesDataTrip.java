package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


/**
 * Created by leandro.efron on 5/12/2016.
 */
public class HotelesDataTrip extends BasePage {

    public HotelesDataTrip(WebDriver driver) {
        super(driver);
    }

    //############################################### Locators ##############################################

    @FindBy(id = "destination-hotels")
    public WebElement destinationTxt;

    @FindBy(id = "checkin-hotels")
    public WebElement checkinCalendar;

    @FindBy(id = "checkout-hotels")
    public WebElement checkoutCalendar;

    @FindBy(css = ".ui-datepicker-month")
    public WebElement monthLbl;

    @FindBy(css = ".ui-datepicker-year")
    public WebElement yearLbl;

    @FindBy(css = ".ui-icon.ui-icon-circle-triangle-e")
    public WebElement nextCalBtn;

    @FindBy(css = ".search__input")
    public WebElement personasTxt;

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

    @FindBy(css = ".button.button--sm")
    public WebElement listoBtn;

    //############################################### Actions ###############################################

    public HotelesDataTrip setDestination(String destination) {
        PageUtils.waitElementForVisibility(driver, destinationTxt, 10, "Destination text field");
        logger.info("Entering Destination: [" + destination + "]");
        destinationTxt.clear();
        destinationTxt.sendKeys(destination);

        return this;
    }

    public int selectPassenger(int adults, int childs, int rooms) {
        personasTxt.click();

        if (adults>2){
            for(int i=1; i<adults; i++) {
                logger.info("Adding 1 adult");
                addAdultBtn.click();
            }
        }

        if (childs>0){
            for(int i=0; i<childs; i++) {
                logger.info("Adding 1 child");
                addChildBtn.click();
            }
        }
        listoBtn.click();

        return adults + childs;
    }
}