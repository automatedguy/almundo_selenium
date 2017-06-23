package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.pages.CheckOutPage.PickUpLocationSection;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.sevices.InputDefinitions;
import com.almundo.browser.automation.utils.PageUtils;
import com.almundo.browser.automation.utils.sevices.Apikeys;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;

import static com.almundo.browser.automation.utils.Constants.*;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class CheckOutPageV3 extends TestBaseSetup {

    public CheckOutPageV3(WebDriver driver) { super.driver = driver; }

    public static JSONObject checkOutPageElements = null;
    public static String apikeyHeader =  null;
    public static Apikeys apikeys = new Apikeys();
    public static InputDefinitions inputDef = null;

    public PassengerSectionV3 passengerSection() {
        return initPassengerInfoSectionV3();
    }

    public PickUpLocationSection pickUpLocationSection() {
        return initPickUpLocationSection();
    }

    public PaymentSectionV3 paymentSection() {
        return initPaymentSectionV3();
    }

    public PaymentSectionGridV3 paymentSectionGrid() {
        return initPaymentSectionGridV3();
    }

    public BillingSectionV3 billingSection() {
        return initBillingSectionV3();
    }

    public ContactSectionV3 contactSection() {
        return initContactInfoSectionV3();
    }

    //############################################### Locators ##############################################

    @FindBy(css = ".button-buy")
    public WebElement comprarBtn;

    @FindBy(css = ".price__amount")
    public WebElement totalPrice;

    @FindBy(css = ".main-title")
    public WebElement mainTitleLbl;

    @FindBy(css = "div:nth-child(1) > label > a")
    private WebElement terminosCondiciones;

    //############################################### Actions ##############################################

    public ConfirmationPageV3 clickComprarBtn(){
        if((baseURL.contains("st.almundo") || baseURL.contains("staging.almundo")) && submitReservation) {
            PageUtils.waitElementForClickable(driver, comprarBtn, 5, "Comprar button");
            logger.info("Clicking on Comprar Button");
            comprarBtn.click();
        } else {
            logger.info("Condition is not approved to submit reservation");
        }
        return initConfirmationPageV3();
    }

    public static boolean isElementRequiered(JSONObject JSONElementsRead, String element){
        boolean isRequiered = false;
        try {
            isRequiered = Boolean.parseBoolean(JSONElementsRead.get(element).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isRequiered;
    }

    public int getTotalPrice() {
        String price = totalPrice.getText().replace(".", "");
        price = price.replace(" ", "");
        logger.info("Total Price: [" + price + "]");
        return Integer.parseInt(price);
    }

    private static void getCheckOutPageElements(String productCheckOutPage)  {
        checkOutPageElements = JsonRead.getJsonDataObject(jsonCountryPropertyObject, productCheckOutPage, "countries_properties.json");
    }

    public CheckOutPageV3 populateCheckOutPageV3(JSONArray passengerList,
                                                 String paymentData,
                                                 JSONObject billingData,
                                                 JSONObject contactData,
                                                 String productCheckOutPage) {
        getCheckOutPageElements(productCheckOutPage);
        //forceCheckoutV3();
        //forceCombosV3();
        //forceTodoPagoOff();
        //setInputDef();

        /***** Working Here *****/
        logger.info("Check point");
        PaymentSectionGridV3 paymentSectionGridV3 = initPaymentSectionGridV3();

        paymentSectionGridV3.populatePaymentSection(paymentData);

        /***** Working Here *****/

        if(countryPar.equals("ARGENTINA") && !method.contains("Trips")) {
            paymentSection().populatePaymentSectionV3(paymentData, ".card-container-1");
        }
        //paymentSection().selectPaymentOption(paymentData, ".card-container-1");
        passengerSection().populatePassengerSection(passengerList);
        //TODO: Refactor for Cars (when migrated to checkout V3)
        //pickUpLocationSection().populatePickUpLocationSection();
        billingSection().populateBillingSection(billingData);
        contactSection().populateContactSection(contactData);
        acceptConditions();
        return this;
    }

    public CheckOutPageV3 populateCheckOutPageV3(JSONArray passengerList,
                                                 String paymentData1,
                                                 String paymentData2,
                                                 JSONObject billingData,
                                                 JSONObject contactData,
                                                 String productCheckOutPage) {
        getCheckOutPageElements(productCheckOutPage);
        //forceCheckoutV3();
        forceCombosV3();
        forceTodoPagoOff();
        setInputDef();

        paymentSection().populatePaymentSectionV3(paymentData1, ".card-container-1");
        paymentSection().populatePaymentSectionV3(paymentData2, ".card-container-2");
        passengerSection().populatePassengerSection(passengerList);
        //TODO: Refactor for Cars (when migrated to checkout V3)
        //pickUpLocationSection().populatePickUpLocationSection();
        billingSection().populateBillingSection(billingData);
        contactSection().populateContactSection(contactData);
        acceptConditions();
        return this;
    }

    private CheckOutPageV3 acceptConditions(){
        FooterSectionV3 footerSection = initFooterSectionV3();
        logger.info("---------- Checking options Footer Section ----------");
        footerSection.acceptTermsAndConditions();
        if(isElementRequiered(checkOutPageElements, "accepted")) {
            footerSection.acceptItinerary();
            footerSection.clickConfirmarBtn();
        }
        return this;
    }

    public AgreementPage termAndConditionsClick(){
        logger.info("Clicking on Terms and Conditions Link...");
        PageUtils.waitImplicitly(1000);
        if(countryPar == "COLOMBIA"){
            terminosCondiciones = driver.findElement(By.cssSelector("div:nth-child(1) > label > a:nth-child(3)"));
        }
        terminosCondiciones.click();
        PageUtils.switchToNewTab(driver);
        return initTermsAndConditonsPage();
    }

    private void setInputDef() {
        try {
            String currentUrl = driver.getCurrentUrl();
            apikeyHeader = apikeys.getApiKey(currentUrl);
            if(baseURL.contains(STAGING_URL)) {
                inputDef = new InputDefinitions(API_STG_URL + "api/v3/cart/" + getCartId() + "/input-definitions?site=" + countryPar.substring(0, 3) + "&language=es");
            } else{
                inputDef = new InputDefinitions(API_PROD_URL + "api/v3/cart/" + getCartId() + "/input-definitions?site=" + countryPar.substring(0, 3) + "&language=es");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String getCartId(){
        PageUtils.waitElementForVisibility(driver, By.id("first_name"), 45, "Checkout V3");
        String currentUrl = driver.getCurrentUrl();

        logger.info("Checkout URL: " + "[" + currentUrl + "]");
        String cartId = currentUrl.substring(currentUrl.indexOf("checkout/") + 9, currentUrl.lastIndexOf("checkout/") + 33);
        return cartId;
    }
}