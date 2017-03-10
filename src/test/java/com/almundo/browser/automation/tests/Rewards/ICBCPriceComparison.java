package com.almundo.browser.automation.tests.Rewards;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPage.CheckOutPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static com.almundo.browser.automation.utils.Constants.*;

/**
 * Created by leandro.efron on 10/3/2017.
 */
public class ICBCPriceComparison extends TestBaseSetup {

    private CheckOutPage checkOutPage = null;

    private CheckOutPage openCart(String cartId){
        driver.navigate().to(PROD_URL + "cart/v2/" + cartId);
        return initCheckOutPage();
    }

    @Test
    public void bue_mia_latam() {

        checkOutPage = openCart(cartId);

        List<WebElement> results = driver.findElements(By.cssSelector(".cards__definition__header>div:nth-of-type(1)>.display-table>p:nth-of-type(1)"));

        for (WebElement result : results) {
            logger.info("Result is: " + result.getText());
        }

    }
}
