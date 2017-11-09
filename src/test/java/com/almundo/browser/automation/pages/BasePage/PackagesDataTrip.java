package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.pages.ResultsPage.PackagesResultsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

import static com.almundo.browser.automation.utils.PageUtils.waitElementForClickable;
import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;
import static com.almundo.browser.automation.utils.PageUtils.waitSelectContainsResults;

public class PackagesDataTrip extends BasePage {

    public PackagesDataTrip(WebDriver driver) {
        super(driver);
    }

    /************************* Locators *************************/

    @FindBy(css = "#search-pkg-origin")
    private WebElement salgoDeDdl;

    @FindBy(css = "#search-pkg-dest_value")
    private WebElement voyATxt;

    @FindBy(css = "#switchSeason")
    private WebElement temporadaRdb;

    @FindBy(css = "#search-pkg-season")
    private WebElement temporadaDdl;

    @FindBy(css = "#switchDate")
    private WebElement fechaExacataRdb;

    @FindBy(css = "#search-pkg-pax div:nth-child(2) > span.add")
    public WebElement addAdultBtn;

    @FindBy(css = "#search-pkg-pax div:nth-child(3) > span.add")
    public WebElement addChildBtn;

    /************************* Actions *************************/


    public PackagesDataTrip selectOrigin(String origin){
        logger.info("Selecting origin: [" + origin + "]");
        Select selectOriginSelect =  new Select(salgoDeDdl);
        waitSelectContainsResults(selectOriginSelect, "Origin List", 5, 2);
        selectOriginSelect.selectByVisibleText(origin);
        return this;
    }

    public PackagesDataTrip setDestination(int  destination){
        logger.info("Clicking on: [Voy A]");
        voyATxt.click();
        waitImplicitly(4000);
        WebElement destinationTxt = driver.findElement(By.cssSelector("#search-pkg-dest_dropdown > div:nth-child(" + destination + ")"));
        logger.info("In this case going to: [" + destinationTxt.getText() + "]");
        waitElementForClickable(driver, destinationTxt, 3, "Full Destination");
        destinationTxt.click();
        return this;
    }

    public PackagesDataTrip setSeason(int season){
        Select selectSeasonSelect = new Select(temporadaDdl);
        logger.info("In this case selecting season: ["
                + selectSeasonSelect.getOptions().get(season).getText() + "]");
        waitSelectContainsResults(selectSeasonSelect, "Seasons List", 5, 2);
        selectSeasonSelect.selectByIndex(season);
        return this;
    }

    private PackagesDataTrip selectExactDate(){
        logger.info("Selecting [Fecha exacta]");
        fechaExacataRdb.click();
        return this;
    }

    @SuppressWarnings("Duplicates")
    public PackagesDataTrip setPassenger(int adults, int childs, int rooms) {
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

    public PackagesResultsPage clickBuscarBtn() {
        logger.info("Clicking on button: [Buscar]");
        clickBuscar();
        return initPackagesResultsPage();
    }
}
