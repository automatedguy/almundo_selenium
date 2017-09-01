package com.almundo.browser.automation.tests.HomePage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.PromoPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.almundo.browser.automation.utils.Constants.ARGENTINA;
import static com.almundo.browser.automation.utils.Constants.Results.PASSED;

/**
 * Created by leandro.efron on 31/1/2017.
 */
public class BannersAndPromoBoxesTest extends TestBaseSetup {

    private PromoPage promoPage = null;

    @BeforeMethod
    private void closeLoginPopUp(){
        if (!baseURL.contains("sucursales")) {
            LoginPopUp loginPopUp = initLoginPopUp();
            loginPopUp.clickCloseLoginBtn();
        }
    }

    /***************************** Test Cases *****************************/

    @Test
    public void openMainLeftBanner () {
        logTestTitle("Open Home Main left banner");
        String promo_path = basePage.mainLeftBannerLnk.findElement(By.cssSelector("a")).getAttribute("href");
        if(promo_path.contains("https://goo.")) {
            logger.info("Can't validate URL for: [" + promo_path + "]");
        } else {
            promoPage = basePage.clickMainLeftBannerLnk();
            PageUtils.waitImplicitly(2000);

            logger.info("Validating URL [" + driver.getCurrentUrl() + "] contains [" + promo_path + "]");
            Assert.assertTrue(driver.getCurrentUrl().contains(promo_path));

            logger.info("Validating URL item property is enabled");
            Assert.assertTrue(promoPage.urlItemProperty.isEnabled());
        }
        setResultSauceLabs(PASSED);
    }

    @Test
    public void openMainRightBanner () {
        logTestTitle("Open Home Main right banner");
        String promoDisplayed_path = basePage.mainRightBannerLnk.findElement(By.cssSelector(" li[aria-hidden='false']>a")).getAttribute("href");
        String promoDisplayed_path_fix = promoDisplayed_path.replaceAll("\\s","");

        if(promoDisplayed_path_fix.contains("https://goo.")) {
            logger.info("Can't validate URL for: [" + promoDisplayed_path_fix + "]");
        } else {
            promoPage = basePage.clickMainRightBannerLnk();
            PageUtils.waitImplicitly(2000);
            logger.info("Promo selected: [" + promoDisplayed_path_fix + "]");

            logger.info("Validating URL [" + driver.getCurrentUrl() + "] contains [" + promoDisplayed_path_fix + "]");
            Assert.assertTrue(driver.getCurrentUrl().contains(promoDisplayed_path_fix), "Current URL does not contain expected data");
        }
        setResultSauceLabs(PASSED);
    }

    @Test
    public void openPromoBox () {
        logTestTitle("Open Promotion box");
        String promo_path = null;
        List<WebElement> promoBoxList = driver.findElements(By.cssSelector(".promo-section .promo-ctn"));
        for (WebElement promoBox: promoBoxList) {
            if (promoBox.isDisplayed()) {
                logger.info("Clicking on Promotion box");
                PageUtils.scrollToElement(driver, promoBox);
                promo_path = promoBox.getAttribute("href");
                logger.info("Promo selected: [" + promo_path + "]");
                promoBox.click();
                break;
            }
        }
        promoPage = initPromoPage();
        PageUtils.waitImplicitly(2000);
        logger.info("Validating URL [" + driver.getCurrentUrl() + "] contains [" + promo_path + "]");
        Assert.assertTrue(driver.getCurrentUrl().contains(promo_path));
        setResultSauceLabs(PASSED);
    }

    @Test
    public void openHomeMedioLeftBanner () {
        if (countryPar.equals(ARGENTINA)) {
            logTestTitle("Open Home Medio left banner");
            String promo_path = basePage.homeMedioLeftBannerLnk.findElement(By.cssSelector("a")).getAttribute("href");
            promoPage = basePage.clickHomeMedioLeftBannerLnk();
            PageUtils.waitImplicitly(2000);
            logger.info("Validating URL [" + driver.getCurrentUrl() + "]");
            Assert.assertEquals(driver.getCurrentUrl(), promo_path, "Incorrect URL");
        }
        setResultSauceLabs(PASSED);
    }

/*    @Test
    public void openHomeMedioRightBanner () {
        logger.info("Wait");
        if (countryPar.equals("ARGENTINA")) {
            logTestTitle("HomePage - Open Home Medio right banner - " + countryPar);
            String promo_path = basePage.homeMedioRightBannerLnk.findElement(By.cssSelector("a")).getAttribute("href");
            promoPage = basePage.clickHomeMedioRightBannerLnk();
            PageUtils.waitImplicitly(2000);
            logger.info("Validating URL [" + driver.getCurrentUrl() + "]");
            Assert.assertEquals(driver.getCurrentUrl(), promo_path, "Incorrect URL");
        }
    }*/

    @Test
    public void validateCarouselBanners () {
        logTestTitle("Validate carousel banners are displayed");
        logger.info("Validating Home Principal carousel banners are displayed");
        List<WebElement> bannerNamesList = driver.findElements(By.cssSelector("#main-content .banner-list"));
        Assert.assertTrue(bannerNamesList.size()>0);

        for (WebElement bannerName : bannerNamesList) {
            logger.info("Banner Name: [" + bannerName.getAttribute("name") + "]");
            Assert.assertTrue(bannerName.findElements(By.cssSelector(".banner-item")).size()>0);
            logger.info("Banner Items: " + bannerName.findElements(By.cssSelector(".banner-item")).size());
        }
        setResultSauceLabs(PASSED);
    }

    @Test
    public void validateMainRightBannerDataLinks () {
        logTestTitle("Validate Main right banners contain data links in carousel");
        List <WebElement> elementList = driver.findElements(By.cssSelector("ul[name$='Home Principal Buscador Derecha'] a"));
        for (int i=1; i<=elementList.size(); i++) {
            String dataLinkBanner = elementList.get(i-1).getAttribute("href");
            Assert.assertFalse(dataLinkBanner.equals(""), "Data link banner [" + i + "] is empty");
            logger.info("Data link banner [" + i + "] - [" + dataLinkBanner + "]");
        }
        setResultSauceLabs(PASSED);
    }

    @Test
    public void validatePromoBoxesDisplayed () {
        logTestTitle("Validate promotion boxes are displayed");
        logger.info("Validating promotion boxes are displayed");
        List<WebElement> promoBoxList = driver.findElements(By.cssSelector(".promo-section .promo-ctn"));
        Assert.assertTrue(promoBoxList.size()>0);
        for (WebElement promoBox: promoBoxList) {
            if (promoBox.isDisplayed()) {
                Assert.assertTrue(promoBox.getAttribute("href") != null, "Promo box attribute is null");
                logger.info("Promo box: [" + promoBox.getAttribute("href") + "] is displayed");
            }
        }
        setResultSauceLabs(PASSED);
    }
}