package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.almundo.browser.automation.utils.Constants.REWARDS;
import static com.almundo.browser.automation.utils.PageUtils.scrollToElement;
import static com.almundo.browser.automation.utils.PageUtils.waitElementForClickable;
import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

public class ClubAlmundoRewards extends CheckOutPageV3{
    public ClubAlmundoRewards(WebDriver driver) {
        super(driver);
    }

    /**************************** Locators **********************************/

    @FindBy(css = "club-almundo-login button")
    private WebElement loginBtn;

    @FindBy(css = "am-payment-rewards div:nth-child(2) input")
    private WebElement useRewardsNo;

    @FindBy(css = "am-payment-rewards div:nth-child(3) input")
    private WebElement useRewardsYes;

    /**************************** Actions **********************************/

    public ClubAlmundoRewards userRewardsNoClick(){
        logger.info("Clicking on [No]");
        useRewardsNo.click();
        return this;
    }

    public String useRewardsYesClick(String paymentData){
        logger.info("Clicking on [¡Por supuesto!]");
        waitElementForClickable(driver, useRewardsYes, 15, "[¡Por Supuesto] radio button clickable.");
        scrollToElement(driver, useRewardsYes);
        useRewardsYes.click();
        waitImplicitly(10000);
        return paymentData.replace(REWARDS + "$","");
    }

    public LoginPopUp loginBtnClick(){
        logger.info("Clicking on [Ingresar] button.");
        loginBtn.click();
        return initLoginPopUp();
    }
}
