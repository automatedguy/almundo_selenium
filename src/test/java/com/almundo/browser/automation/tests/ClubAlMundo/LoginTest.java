package com.almundo.browser.automation.tests.ClubAlMundo;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.utils.Constants;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.almundo.browser.automation.utils.Constants.Results.PASSED;
import static com.almundo.browser.automation.utils.PageUtils.isElementClickable;
import static com.almundo.browser.automation.utils.PageUtils.userNameOk;

/**
 * Created by leandro.efron on 8/2/2017.
 */
public class LoginTest extends TestBaseSetup {

    private LoginPopUp loginPopUp = null;

    private DataManagement dataManagement = new DataManagement();

    @BeforeClass
    private void initUserList() {
        dataManagement.getUsersDataList();
    }

    @BeforeMethod
    private void initLoginPopUpElement(){
        loginPopUp = initLoginPopUp();
    }

    /////////////////////////////////// TEST CASES ///////////////////////////////////

    @Test
    public void login_email () {
        logTestTitle("Club AlMundo - Login with email - " + countryPar );

        JSONObject userData = dataManagement.getUserData("email");
        loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());
        basePage = loginPopUp.clickIngresarBtn();

        Assert.assertTrue(userNameOk(userData.get("name").toString(), basePage.headerSection().textLoggedIntLnk.getText()));

        basePage.headerSection().clickMyAccountMenuLnk();

        List<String> actualList  = basePage.headerSection().getMyAccountMenuList();
        List<String> expectedList;

        if(countryPar.equals("ARGENTINA")) {
            expectedList = Constants.USER_MENU_LIST_AR;
        } else if(countryPar.equals("COLOMBIA")) {
            expectedList = Constants.USER_MENU_LIST_CO;
        } else {
                expectedList = Constants.USER_MENU_LIST_MX;
        }

        logger.info("Validating My Account menu options are displayed:");
        Assert.assertTrue((PageUtils.equalLists(actualList, expectedList, driver)), "Displayed options are not correct");

        logger.info("Logging out user");
        basePage.headerSection().clickMyAccountMenuOption("Cerrar sesion");
        PageUtils.waitImplicitly(4000);

        logger.info("Validating user is logged out");
        Assert.assertTrue(isElementClickable(driver, basePage.headerSection().textLoggedOutLnk, 10, "Login Link (from header)"));

        setResultSauceLabs(PASSED);
    }
}