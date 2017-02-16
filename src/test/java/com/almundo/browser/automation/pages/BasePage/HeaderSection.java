package com.almundo.browser.automation.pages.BasePage;

import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leandro.efron on 5/12/2016.
 */
public class HeaderSection extends BasePage {

    public HeaderSection(WebDriver driver) {
        super(driver);
    }

    //############################################### Locators ##############################################

    @FindBy(css = ".account .pointer")
    public WebElement myAccountMenuLnk;

    @FindBy(css = ".account>a>span:nth-of-type(2)")
    public WebElement textLnk;

    //############################################### Actions ###############################################

    public HeaderSection clickMyAccountMenuLnk() {
        PageUtils.waitElementForVisibility(driver, myAccountMenuLnk, 10, "My account menu link");
        logger.info("Clicking on My Account menu link");
        myAccountMenuLnk.click();
        return this;
    }

    public List<String> getMyAccountMenuList() {
        List<String> stringList = new ArrayList<>();
        List<WebElement> ElementList = driver.findElements(By.cssSelector(".sub-menu-my-account .link.pointer"));

        for (WebElement result : ElementList) {
            String newResult = result.getText();
            stringList.add(newResult);
        }
        return stringList;
    }

    public void clickMyAccountMenuOption(String option) {
        List<WebElement> results = driver.findElements(By.cssSelector(".sub-menu-my-account .link.pointer"));

        for (WebElement result : results) {
            if (result.getText().equals(option)) {
                logger.info("Clicking on [" + option + "] option");
                result.click();
                break;
            }
        }
    }

}
