package com.almundo.browser.automation.pages.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by leandro.efron on 5/12/2016.
 */
public class HotelesDataTrip {

    //############################################### Locators ##############################################

    @FindBy(id = "destination-hotels")
    public WebElement destinationTxt;

    @FindBy(id = "checkin-hotels")
    public WebElement checkinCalendar;

    @FindBy(id = "checkout-hotels")
    public WebElement checkoutCalendar;

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

    //############################################### Actions ###############################################


}
