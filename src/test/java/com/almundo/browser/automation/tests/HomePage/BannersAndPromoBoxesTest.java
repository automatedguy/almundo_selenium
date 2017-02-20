package com.almundo.browser.automation.tests.HomePage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.PromoPage;
import com.almundo.browser.automation.utils.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by leandro.efron on 31/1/2017.
 */
public class BannersAndPromoBoxesTest extends TestBaseSetup {

    private PromoPage promoPage = null;

    @BeforeClass
    private void initBasePageElement() {
        basePage = initBasePage();
    }

    @BeforeMethod
    private void closeLoginPopUp(){
        LoginPopUp loginPopUp = initLoginPopUp();
        loginPopUp.clickCloseLoginBtn();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void openMainLeftBanner () {
        logTestTitle("HomePage - Open Home Main left banner - " + countryPar );
        String promo_path = basePage.mainLeftBannerLnk.findElement(By.cssSelector("a")).getAttribute("data-ng-href");
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
    }

    @Test
    public void openMainRightBanner () {
        logTestTitle("HomePage - Open Home Main right banner - " + countryPar );
        String promoDisplayed_path = basePage.mainRightBannerLnk.findElement(By.cssSelector(" li[aria-hidden='false']>a")).getAttribute("data-ng-href");
        if(promoDisplayed_path.contains("https://goo.")) {
            logger.info("Can't validate URL for: [" + promoDisplayed_path + "]");
        } else {
            promoPage = basePage.clickMainRightBannerLnk();
            PageUtils.waitImplicitly(2000);
            logger.info("Promo selected: [" + promoDisplayed_path + "]");

            logger.info("Validating URL [" + driver.getCurrentUrl() + "] contains [" + promoDisplayed_path + "]");
            Assert.assertTrue(driver.getCurrentUrl().contains(promoDisplayed_path));
        }
    }

    @Test
    public void openPromoBox () {
        logTestTitle("HomePage - Open Promotion box - " + countryPar );
        String promo_path = null;
        List<WebElement> promoBoxList = driver.findElements(By.cssSelector(".promo-section .promo-ctn"));
        for (WebElement promoBox: promoBoxList) {
            if (promoBox.isDisplayed()) {
                logger.info("Clicking on Promotion box");
                PageUtils.scrollToElement(driver, promoBox);
                promo_path = promoBox.getAttribute("ng-href");
                logger.info("Promo selected: [" + promo_path + "]");
                promoBox.click();
                break;
            }
        }
        promoPage = initPromoPage();
        PageUtils.waitImplicitly(2000);
        logger.info("Validating URL [" + driver.getCurrentUrl() + "] contains [" + promo_path + "]");
        Assert.assertTrue(driver.getCurrentUrl().contains(promo_path));
    }

    @Test
    public void openHomeMedioLeftBanner () {
        if (countryPar.equals("ARGENTINA")) {
            logTestTitle("HomePage - Open Home Medio left banner - " + countryPar);
            String promo_path = basePage.homeMedioLeftBannerLnk.findElement(By.cssSelector("a")).getAttribute("data-ng-href");
            promoPage = basePage.clickHomeMedioLeftBannerLnk();
            PageUtils.waitImplicitly(2000);
            logger.info("Validating URL [" + driver.getCurrentUrl() + "]");
            Assert.assertEquals(driver.getCurrentUrl(), promo_path, "Incorrect URL");
        }
    }

    @Test
    public void openHomeMedioRightBanner () {
        if (countryPar.equals("ARGENTINA")) {
            logTestTitle("HomePage - Open Home Medio right banner - " + countryPar);
            String promo_path = basePage.homeMedioRightBannerLnk.findElement(By.cssSelector("a")).getAttribute("data-ng-href");
            promoPage = basePage.clickHomeMedioRightBannerLnk();
            PageUtils.waitImplicitly(2000);
            logger.info("Validating URL [" + driver.getCurrentUrl() + "]");
            Assert.assertEquals(driver.getCurrentUrl(), promo_path, "Incorrect URL");
        }
    }

    @Test
    public void validateBannersDisplayed () {
        logTestTitle("HomePage - Validate banners are displayed - " + countryPar );
        logger.info("Validating Home Principal banners are displayed");
        List<WebElement> bannerNamesList = driver.findElements(By.cssSelector("#main-content .banner-list"));
        Assert.assertTrue(bannerNamesList.size()>0);
        for (WebElement bannerName : bannerNamesList) {
            logger.info("Banner Name: [" + bannerName.getAttribute("name") + "]");
            Assert.assertTrue(bannerName.findElements(By.cssSelector(".banner-item")).size()>0);
            logger.info("Banner Items: " + bannerName.findElements(By.cssSelector(".banner-item")).size());
        }
        if (countryPar.equals("ARGENTINA")) {
            System.out.println();
            logger.info("Validating Home Medio banners are displayed");
            List<WebElement> bannerAdsNamesList = driver.findElements(By.cssSelector(".bannersads-container .banner-list"));
            Assert.assertTrue(bannerAdsNamesList.size() > 0);
            for (WebElement bannerAdsName : bannerAdsNamesList) {
                logger.info("Banner Name: [" + bannerAdsName.getAttribute("name") + "]");
                Assert.assertTrue(bannerAdsName.findElements(By.cssSelector(".banner-item")).size() > 0);
                logger.info("Banner Items: " + bannerAdsName.findElements(By.cssSelector(".banner-item")).size());
            }
        }
    }

    @Test
    public void validateMainRightBannerDataLinks () {
        logTestTitle("HomePage - Validate Main right banners contain data links in carousel - " + countryPar );
        List <WebElement> elementList = driver.findElements(By.cssSelector("ul[name$='Home Principal Buscador Derecha'] a"));
        for (int i=1; i<=elementList.size(); i++) {
            String dataLinkBanner = elementList.get(i-1).getAttribute("data-ng-href");
            Assert.assertFalse(dataLinkBanner.equals(""), "Data link banner [" + i + "] is empty");
            logger.info("Data link banner [" + i + "] - [" + dataLinkBanner + "]");
        }
    }

    @Test
    public void validatePromoBoxesDisplayed () {
        logTestTitle("HomePage - Validate promotion boxes are displayed - " + countryPar );
        logger.info("Validating promotion boxes are displayed");
        List<WebElement> promoBoxList = driver.findElements(By.cssSelector(".promo-section .promo-ctn"));
        Assert.assertTrue(promoBoxList.size()>0);
        for (WebElement promoBox: promoBoxList) {
            if (promoBox.isDisplayed()) {
                logger.info("Promo box: [" + promoBox.getAttribute("ng-href") + "] is displayed");
            }
        }
    }
}
