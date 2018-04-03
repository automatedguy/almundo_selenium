package com.almundo.browser.automation.pages.CheckOutPageV3.Retail;

import com.almundo.browser.automation.pages.CheckOutPageV3.CheckOutPageV3;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 24/07/17.
 */
public class AgentSectionV3 extends CheckOutPageV3 {

    public AgentSectionV3(WebDriver driver) {
        super(driver);
    }

    /******************** Locators ********************/

    @FindBy(css = "agent-contact-form #email")
    WebElement agentEmailTxt;

    /******************** Actions ********************/

    public AgentSectionV3 setAgentEmail(String agentEmail){
        logger.info("Entering Almundo agent Email: " + agentEmail);
        if(isElementRequiered(checkOutPageElements, "agentEmail")) {
            agentEmailTxt.sendKeys(agentEmail);
        }
        return this;
    }
}
