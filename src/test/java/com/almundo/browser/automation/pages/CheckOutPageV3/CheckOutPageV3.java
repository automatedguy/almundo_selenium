package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.CheckOutPageV3.Retail.*;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.PageUtils;
import com.almundo.browser.automation.utils.sevices.InputDefinitions;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;

import static com.almundo.browser.automation.utils.Constants.API_PROD_URL;
import static com.almundo.browser.automation.utils.Constants.API_STG_URL;
import static com.almundo.browser.automation.utils.Constants.Results.FAILED;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class CheckOutPageV3 extends TestBaseSetup {

    public CheckOutPageV3(WebDriver driver) { super.driver = driver; }


    public static JSONObject checkOutPageElements = null;
    public DataManagement dataManagement = new DataManagement();

    public static InputDefinitions inputDef = null;

    private boolean todoPagoStc = false;
    private boolean creditCardComboSc = false;
    private boolean paymentSelectorSvd = false;
    public int halfPrice = 0;

    public PaymentSelectorV3 paymentSelectorV3(){return initPaymentSelectorV3();}

    public BreakDownSectionV3 breakDownSectionV3(){return initBreakDownSectionV3();}

    public PaymentSelectorRetailSplitV3 paymentSelectorRetailSplitV3(){
        return initPaymentSelectorRetailSplitV3();
    }

    public PaymentSelectorRetailV3 paymentSelectorRetailV3(){return initPaymentSelectorRetailV3();}

    public PaymentSectionComboV3 paymentSectionComboV3(){return initPaymentSectionComboV3();}

    public PaymentSectionComboRetailV3 paymentSectionComboRetailV3(){return initPaymentSectionComboRetailV3();}

    public PaymentSectionGridV3 paymentSectionGridV3(){return initPaymentSectionGridV3();}

    public CreditCardDataV3 creditCardDataV3(){return initCreditCardDataV3();}

    public CreditCardDataRetailV3 creditCardDataRetailV3(){return initCreditCardDataRetailV3();}

    public DebitCardDataV3 debitCardDataV3(){return initDebitCardDataV3();}

    public PassengerSectionV3 passengerSection() {
        return initPassengerInfoSectionV3();
    }

    public BillingSectionV3 billingSection() {
        return initBillingSectionV3();
    }

    public ContactSectionV3 contactSection() {
        return initContactInfoSectionV3();
    }

    public AgentSectionV3 agentSectionV3(){return initAgentSectionV3();}

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

    private void dealWithPaymentForm(String paymentData){
        if(isRetailChannel()){
            if(paymentData.contains("pago_dividido")) {

                halfPrice = breakDownSectionV3().getFinalPrice()/2;

                paymentSelectorRetailV3().selectPaymentMethod("PAGO DIVIDIDO");
                String firstCreditCard = StringUtils.substringBetween(paymentData, "$", "@");
                paymentSelectorRetailSplitV3().populateSplittedCreditCardData(firstCreditCard, halfPrice);

                paymentSelectorRetailSplitV3().agregarOtroMedioDePagoClick();
                String secondCreditCard = StringUtils.substringBetween(paymentData, "@", "$");
                paymentSelectorRetailSplitV3().populateSplittedCreditCardData(secondCreditCard, halfPrice);
            }
            else {
                paymentSelectorRetailV3().selectCreditRbd();
                paymentSectionComboRetailV3().populatePaymentSectionV3(paymentData);
                creditCardDataRetailV3().populateCreditCardData(paymentData, true);
            }
        }
        else {
            if (!paymentData.contains("debit")) {
                if (paymentSelectorSvd && !countryPar.equals("COLOMBIA")) {
                    paymentSelectorV3().selectOneCreditCardRdb();
                } else {
                    if (paymentSelectorV3().selectOneCreditCardRdbIsDisplayed()) {
                        paymentSelectorV3().selectOneCreditCardRdb();
                    }
                }
                if (creditCardComboSc) {
                    paymentSectionComboV3().populatePaymentSectionV3(paymentData, ".card-container-1");
                } else {
                    paymentSectionGridV3().populatePaymentSectionV3(paymentData, ".card-container-1");
                }
                creditCardDataV3().populateCreditCardData(paymentData, ".card-container-1");
            } else {
                setUrlParameter("&svd=1");
                paymentSelectorV3().selectVisaDebit();
                debitCardDataV3().populateDebitCardData(paymentData);
            }
        }
    }

    public CheckOutPageV3 populateCheckOutPageV3(JSONArray passengerList,
                                                 String paymentData,
                                                 JSONObject billingData,
                                                 JSONObject contactData,
                                                 String productCheckOutPage) {
        getCheckOutPageElements(productCheckOutPage);
        setCheckOutSections(getCheckoutUrl());
        setInputDef();
        dealWithPaymentForm(paymentData);
        passengerSection().populatePassengerSection(passengerList);
        billingSection().populateBillingSection(billingData);
        contactSection().populateContactSection(contactData);
        if(isRetailChannel()){
            agentSectionV3().setAgentEmail("gabriel.cespedes@almundo.com");
        }
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
        setCheckOutSections(getCheckoutUrl());
        setInputDef();

        if(paymentSelectorSvd){
            paymentSelectorV3().selectTwoCreditCardsRdb();
        } else {
            if (paymentSelectorV3().selectTwoCreditCardsRdbIsDisplayed()) {
                paymentSelectorV3().selectTwoCreditCardsRdb();
            }
        }

        if(creditCardComboSc){
            paymentSectionComboV3().populatePaymentSectionV3(paymentData1, ".card-container-1");
            creditCardDataV3().populateCreditCardData(paymentData1, ".card-container-1");
            paymentSectionComboV3().populatePaymentSectionV3(paymentData2, ".card-container-2");
            creditCardDataV3().populateCreditCardData(paymentData2, ".card-container-2");
        } else{
            paymentSectionGridV3().populatePaymentSectionV3(paymentData1, ".card-container-1");
            creditCardDataV3().populateCreditCardData(paymentData1, ".card-container-1");
            paymentSectionGridV3().populatePaymentSectionV3(paymentData2, ".card-container-2");
            creditCardDataV3().populateCreditCardData(paymentData2, ".card-container-2");
        }

        passengerSection().populatePassengerSection(passengerList);
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
        if(countryPar.equals("COLOMBIA")){
            terminosCondiciones = driver.findElement(By.cssSelector("div:nth-child(1) > label > a:nth-child(3)"));
        }
        terminosCondiciones.click();
        PageUtils.switchToNewTab(driver);
        return initTermsAndConditonsPage();
    }

    private void setInputDef() {
        try {
             if(baseURL.contains("st.almundo")) {
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

    private void setCheckOutSections(String checkoutUrl){
        if(checkoutUrl.contains("svd=1")){
            logger.info("[svd=1] is enabled.");
            paymentSelectorSvd = true;
        } else{ logger.info("[svd=1] is not enabled."); }

        if(checkoutUrl.contains("sc=1")){
            logger.info("[sc=1] is enabled.");
            creditCardComboSc =  true;
        } else { logger.info("[sc=1] is not enabled."); }

        if(checkoutUrl.contains("stc=1")){
            logger.info("[stc=1] is enabled.");
            todoPagoStc = true;
        }else { logger.info("[stc=1] is not enabled."); }

        if(checkoutUrl.contains("vuelohotel")){
            logger.info("Selector de pago] is enabled.");
            paymentSelectorSvd = true;
        }else { logger.info("[Selector de pago] is not enabled.");}

    }

    public CheckOutPageV3 setUrlParameter(String parameter){
        logger.info("Adding Parameter: " + parameter);
        String currentUrl = driver.getCurrentUrl();
        if(currentUrl.contains(parameter)){
            logger.info("Parameter: " + parameter + " is already set.");
        }
        else {
            if (currentUrl.contains(parameter.substring(0, parameter.length()-1))) {
                //TODO: come up with something.
                driver.navigate().to(currentUrl.replaceAll(parameter + "(0)", "1 $1"));
            }else {
                logger.info("Refreshing page URL: " +  currentUrl+parameter);
                driver.navigate().to(currentUrl+parameter);
            }
            getCheckoutUrl();
        }
        return initCheckOutPageV3();
    }

    public Boolean isRetailChannel(){
        //TODO: Remove all the unnecessary parts here, retail apparently running against V3
        boolean retailChannel = false;
        if(baseURL.contains("ccr") || baseURL.contains("sucursales")){
            retailChannel = true;
            if(!method.contains("Trips") && !method.contains("Hotels") && !method.contains("Flights") && !method.contains("Cars")) {
                logger.info("Replacing [cart/v2] with [checkout]");
                driver.navigate().to(getCartUrl().replace("cart/v2", "checkout"));
            }
            else{
                if(!method.contains("Hotels") && !method.contains("Flights") && !method.contains("Cars") && !method.contains("Trips"))  {
                    logger.info("Replacing [cart/v2] with [checkout]");
                    driver.navigate().to(getCartUrl().replace("cart", "checkout"));
                }
            }
            getCheckoutUrl();
        }
        return retailChannel;
    }

    private String getCartUrl(){
        String cartUrl = null;
        try{
            PageUtils.waitElementForVisibility(driver, By.cssSelector("#first_name0"),30, "Cart v2 Query String Parameters.");
            PageUtils.waitUrlContains(driver, 10, "cart", "Cart V1/V2");
            cartUrl =  driver.getCurrentUrl();
        } catch(Exception time) {
            logger.info("Cart V2 was not loaded.");
            setResultSauceLabs(FAILED);
        }
        return cartUrl;
    }

    private String getCheckoutUrl(){
        String checkoutUrl = null;
        try{
            PageUtils.waitElementForVisibility(driver, By.cssSelector("#first_name"),30, "Checkout Query String Parameters.");
            PageUtils.waitUrlContains(driver, 10, "checkout", "Checkout V3");
            checkoutUrl =  driver.getCurrentUrl();
        } catch(Exception time) {
            logger.info("Checkout V3 was not loaded.");
            setResultSauceLabs(FAILED);
        }
        return checkoutUrl;
    }
}