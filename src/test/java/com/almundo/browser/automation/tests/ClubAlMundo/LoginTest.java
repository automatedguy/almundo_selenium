package com.almundo.browser.automation.tests.ClubAlMundo;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.utils.PageUtils;
import org.testng.Assert;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        logger.info("Validating user name is displayed: [" + userData.get("name").toString() + "]");
        Assert.assertEquals(userData.get("name").toString(), basePage.headerSection().textLnk.getText());

        basePage.headerSection().clickMyAccountMenuLnk();

        List<String> actualList  = basePage.headerSection().getMyAccountMenuList();
        List<String> expectedList;

        if(countryPar.equals("ARGENTINA")) {
            expectedList = new ArrayList<>(Arrays.asList("Perfil", "Medios de Pago", "Reservas", "Mis gustos", "Mis puntos", "Cerrar sesión"));
        } else if(countryPar.equals("COLOMBIA")) {
            expectedList = new ArrayList<>(Arrays.asList("Perfil", "Formas de pago", "Reservas", "Cerrar sesión"));
        }
            else {
                expectedList = new ArrayList<>(Arrays.asList("Perfil", "Medios de Pago", "Reservas", "Cerrar sesión"));
            }

        logger.info("Validating My Account menu options are displayed:");
        Assert.assertTrue((PageUtils.equalLists(actualList, expectedList)), "Displayed options are not correct");

        logger.info("Logging out user");
        basePage.headerSection().clickMyAccountMenuOption("Cerrar sesión");
        PageUtils.waitImplicitly(4000);

        logger.info("Validating user is logged out");
        Assert.assertEquals("Ingresar", basePage.headerSection().textLnk.getText());
    }
}