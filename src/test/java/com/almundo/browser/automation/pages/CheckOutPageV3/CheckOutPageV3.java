package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.CheckOutPageV3.Retail.*;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.PageUtils;
import com.almundo.browser.automation.utils.sevices.InputDefinitions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.almundo.browser.automation.utils.Constants.*;
import static com.almundo.browser.automation.utils.Constants.Results.FAILED;
import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;
import static com.almundo.browser.automation.utils.PageUtils.waitWithTryCatch;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class CheckOutPageV3 extends TestBaseSetup {

    public CheckOutPageV3(WebDriver driver) { super.driver = driver; }


    public static JSONObject checkOutPageElements = null;
    public DataManagement dataManagement = new DataManagement();

    public static InputDefinitions inputDef = null;

    private boolean creditCardComboSc = false;
    private boolean paymentSelectorSvd = false;

    public ClubAlmundoRewards clubAlmundoRewards() {return initclubAlmundoRewards();}

    public PaymentSelectorV3 paymentSelectorV3(){return initPaymentSelectorV3();}

    public BreakDownSectionV3 breakDownSectionV3(){return initBreakDownSectionV3();}

    public PaymentSelectorRetailSplitV3 paymentSelectorRetailSplitV3(){
        return initPaymentSelectorRetailSplitV3();
    }

    public PaymentSelectorLinkV3 paymentSelectorLinkV3(){
        return initPaymentSelectorLinkV3();
    }

    public PaymentSelectorRetailV3 paymentSelectorRetailV3(){return initPaymentSelectorRetailV3();}

    public PaymentSectionComboV3 paymentSectionComboV3(){return initPaymentSectionComboV3();}

    public PaymentTwoCreditCardsV3 paymentTwoCreditCardsV3(){return initPaymentTwoCreditCardsV3();}

    public PaymentSectionComboRetailV3 paymentSectionComboRetailV3(){return initPaymentSectionComboRetailV3();}

    public PaymentSectionGridV3 paymentSectionGridV3(){return initPaymentSectionGridV3();}

    public CreditCardDataV3 creditCardDataV3(){return initCreditCardDataV3();}

    public CreditCardDataRetailV3 creditCardDataRetailV3(){return initCreditCardDataRetailV3();}

    public TodoPagoDataV3 todoPagoDataV3(){return initDebitCardDataV3();}

    public PassengerSectionV3 passengerSection() {
        return initPassengerInfoSectionV3();
    }

    public EmergencyContact emergencyContact(){
        return initEmergencyContactSection();
    }

    public BillingSectionV3 billingSection() {
        return initBillingSectionV3();
    }

    public ContactSectionV3 contactSection() {
        return initContactInfoSectionV3();
    }

    public AgentSectionV3 agentSectionV3(){return initAgentSectionV3();}

    public String setTotalCuota(int paymentAmount){
        StringBuilder str = new StringBuilder(Integer.toString(paymentAmount));
        int idx = str.length() - 3;
        while (idx > 0)
        {
            str.insert(idx, ".");
            idx = idx - 3;
        }
        return str.toString();
    }

    //############################################### Locators ##############################################

    @FindBy(css = "input.button-buy")
    public WebElement comprarBtn;

    @FindBy(css = ".price__amount")
    public WebElement totalPrice;

    @FindBy(css = ".main-title")
    public WebElement mainTitleLbl;

    @FindBy(css = "div:nth-child(1) > label > a")
    private WebElement terminosCondiciones;

    @FindBy(css = "#product-resume product-detail am-flights-cluster > div > div:nth-child(1) am-flight-choice .origin .iata.hint--top-right")
    public WebElement originAirport;

    @FindBy(css = "#product-resume product-detail am-flights-cluster > div > div:nth-child(2) am-flight-choice .origin .iata.hint--top-right")
    public WebElement destinationAirport;

    @FindBy(css = "#product-resume am-flights-cluster div:nth-child(1) > div > div > label am-flight-choice .airline > span")
    public WebElement airlineName;

    @FindBy(css = "#product-resume section > div:nth-child(2) > product-detail am-flights-cluster > div > div:nth-child(1) > h3 > span.date")
    public WebElement startDate;

    @FindBy(css = "#product-resume section > div:nth-child(2) > product-detail am-flights-cluster > div > div:nth-child(2) > h3 > span.date")
    public WebElement endDate;

    //############################################### Actions ##############################################

    public ThanksPageV3 clickComprarBtn(){
        if((baseURL.contains("st.almundo") || baseURL.contains("staging.almundo")) && submitReservation) {
            PageUtils.waitElementForClickable(driver, comprarBtn, 5, "Comprar button");
            logger.info("Clicking on Comprar Button");
            waitImplicitly(1000);
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

    public boolean isTodoPagoEnabled(){
        if(!countryPar.equals(MEXICO)){
            try{
                logger.info("Checking if [TodoPago] is Enabled.");
                driver.findElement(By.cssSelector("div.logos-container > img.bank"));
            }catch(NoSuchElementException ouch){
                logger.info("[TodoPago] is not enabled.");
                return false;
            }
            logger.info("[TodoPago] is enabled.");
            return true;
        }else{
            return false;
        }
    }

    private static void getCheckOutPageElements(String productCheckOutPage)  {
        checkOutPageElements = JsonRead.getJsonDataObject(jsonCountryPropertyObject, productCheckOutPage, "countries_properties.json");
    }

    private List<String> getPaymentDataList(String paymentData){
        final Pattern pattern = Pattern.compile("[\\$\"]");
        final String[] result = pattern.split(paymentData);
        List<String> paymentDataList = Arrays.asList(result);
        return paymentDataList;
    }

    /*********************** Checkout Payment Population Methods ***********************/
    /************ For the different payment forms credit, combos, grid, etc ************/

    private void dealWithRetail(String paymentData){
        if(paymentData.contains("pago_dividido$")) {
            paymentSelectorRetailV3().selectPaymentMethod(PAGO_DIVIDIDO);
            paymentSelectorRetailSplitV3().populateSplittedPaymentInfo(getPaymentDataList(paymentData.replace("pago_dividido$","")), breakDownSectionV3().getFinalPrice());
        }
        else if (paymentData.contains("link_de_pago$")) {
            setUrlParameter("&slp=1");
            String thanksPageConfirmation = ".thanks-page-payment am-alert am-alert-title ng-transclude";
            paymentSelectorRetailV3().selectPaymentMethod(LINK_DE_PAGO);
            String actualCheckoutUrl = paymentSelectorLinkV3().populateLinkDePagoInfo();

            if(paymentData.contains("two_cards")) {
                dealWithTwoCards(paymentData.replace("link_de_pago$", ""));
            } else  {
                paymentSelectorV3().selectOneCreditCardRdb();
                paymentSectionGridV3().populatePaymentSectionV3(paymentData.replace("link_de_pago$", ""), ".card-container-1");
                creditCardDataV3().populateCreditCardData(paymentData.replace("link_de_pago$", ""), ".card-container-1");
            }

            acceptConditions();
            clickComprarBtn();
            waitWithTryCatch(driver, thanksPageConfirmation, "Payment confirmation", 10);
            driver.navigate().to(actualCheckoutUrl);
        }
        else {
            paymentSelectorRetailV3().selectCreditRbd();
            paymentSectionComboRetailV3().populatePaymentSectionV3(paymentData);
            creditCardDataRetailV3().populateCreditCardData(paymentData, true);
        }
    }

    private void dealWithTwoCards(String paymentData){
        paymentSelectorV3().selectTwoCreditCardsRdb();
        paymentTwoCreditCardsV3().populateTwoCreditCards(getPaymentDataList(paymentData.replace("two_cards$","")), breakDownSectionV3().getFinalPrice());
        breakDownSectionV3().dealWithInsurance(addInsurance);
        if(addInsurance){
            paymentTwoCreditCardsV3().populateTwoCreditCards(getPaymentDataList(paymentData.replace("two_cards$","")), breakDownSectionV3().getFinalPrice());
        }
    }

    private void dealWithGridAndCombos(String paymentData){
        if(paymentData.contains(REWARDS)){
            paymentData = clubAlmundoRewards().useRewardsYesClick(paymentData);
        }

        if (paymentSelectorSvd && !countryPar.equals(COLOMBIA)) {
            paymentSelectorV3().selectOneCreditCardRdb();
        } else if (paymentSelectorV3().selectOneCreditCardRdbIsDisplayed()) {
                paymentSelectorV3().selectOneCreditCardRdb(); }

        if (creditCardComboSc && !paymentData.contains(DESTINATION)) {
            paymentSectionComboV3().populatePaymentSectionV3(paymentData, ".card-container-1");
        } else if (!paymentData.contains(DESTINATION)){
            paymentSectionGridV3().populatePaymentSectionV3(paymentData, ".card-container-1"); }

        if (paymentData.contains(TODOPAGO)){
            todoPagoDataV3().populateTodoPagoData(paymentData);
        }else {
            creditCardDataV3().populateCreditCardData(paymentData, ".card-container-1");
        }
    }

    private void dealWithDebitCard(String paymentData){
        setUrlParameter("&svd=1");
        paymentSelectorV3().selectVisaDebit();
        todoPagoDataV3().populateTodoPagoData(paymentData);
    }

    private void dealWithPaymentForm(String paymentData){
        if(isRetailChannel()){
            dealWithRetail(paymentData);
        } else if (paymentData.contains(DEBIT)) {
            dealWithDebitCard(paymentData);
        } else if (paymentData.contains(TWO_CARDS)){
            setUrlParameter("&stc=1");
            dealWithTwoCards(paymentData);
        } else {
            dealWithGridAndCombos(paymentData);
        }
    }

    /************* Checkout full Population Methods Calls (Dynamic Checkout) *************/

    public CheckOutPageV3 setCheckOutInfo(JSONArray passengerList,
                                          String paymentData,
                                          JSONObject billingData,
                                          JSONObject contactData,
                                          String productCheckOutPage) {
        getCheckOutPageElements(productCheckOutPage);
        redirectCheckout();
        setCheckOutSections(getCheckoutUrl());
        if(checkoutFill){
            setInputDef();
            dealWithPaymentForm(paymentData);
            passengerSection().populatePassengerSection(passengerList);
            emergencyContact().populateEmergencyContact(contactData);
            if(!paymentData.contains(DESTINATION)) {
                billingSection().populateBillingSection(billingData);
            }
            contactSection().populateContactSection(contactData);
            if(isRetailChannel()){
                agentSectionV3().setAgentEmail(AGENT_EMAIL);
            }
            acceptConditions();
        }
        return this;
    }

    /*************** Checkout full Population Methods Calls (2 Cards - Trips) ***********/

    public CheckOutPageV3 setCheckOutInfo(JSONArray passengerList,
                                          String paymentData1,
                                          String paymentData2,
                                          JSONObject billingData,
                                          JSONObject contactData,
                                          String productCheckOutPage) {
        getCheckOutPageElements(productCheckOutPage);
        redirectCheckout();
        setCheckOutSections(getCheckoutUrl());
        if(checkoutFill) {
            setInputDef();

            if (paymentSelectorSvd) {
                paymentSelectorV3().selectTwoCreditCardsRdb();
            } else if (paymentSelectorV3().selectTwoCreditCardsRdbIsDisplayed()) {
                paymentSelectorV3().selectTwoCreditCardsRdb();
            }

            if (creditCardComboSc) {
                paymentSectionComboV3().populatePaymentSectionV3(paymentData1, ".card-container-1");
                creditCardDataV3().populateCreditCardData(paymentData1, ".card-container-1");
                paymentSectionComboV3().populatePaymentSectionV3(paymentData2, ".card-container-2");
                creditCardDataV3().populateCreditCardData(paymentData2, ".card-container-2");
            } else {
                paymentSectionGridV3().populatePaymentSectionV3(paymentData1, ".card-container-1");
                creditCardDataV3().populateCreditCardData(paymentData1, ".card-container-1");
                paymentSectionGridV3().populatePaymentSectionV3(paymentData2, ".card-container-2");
                creditCardDataV3().populateCreditCardData(paymentData2, ".card-container-2");
            }

            passengerSection().populatePassengerSection(passengerList);
            emergencyContact().populateEmergencyContact(contactData);
            billingSection().populateBillingSection(billingData);
            contactSection().populateContactSection(contactData);
            acceptConditions();
        }
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
        waitImplicitly(1000);
        if(countryPar.equals(COLOMBIA)){
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

        if(checkoutUrl.contains("vuelohotel")){
            logger.info("[Selector de pago] is enabled.");
            paymentSelectorSvd = true;
        }else { logger.info("[Selector de pago] is not enabled.");}
    }

    public CheckOutPageV3 redirectCheckout(){
        if(redirectCheckout) {
            getCheckoutUrl();
            logger.info("Redirecting Checkout to: [" + CHECKOUT_HOST + "]");
            String currentUrl = driver.getCurrentUrl();
            driver.navigate().to(currentUrl.replace(baseURL, CHECKOUT_HOST));
            getCheckoutUrl();
        }else{
            logger.info("Not doing checkout redirection.");
        }
        return initCheckOutPageV3();
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