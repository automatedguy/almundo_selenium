package com.almundo.browser.automation.pages.CheckOutPageV3;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.data.DataManagement;
import com.almundo.browser.automation.pages.BasePage.LoginPopUp;
import com.almundo.browser.automation.pages.CheckOutPageV3.Retail.*;
import com.almundo.browser.automation.pages.SummaryPage.SummaryPage;
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
import static com.almundo.browser.automation.utils.PageUtils.*;

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
    private boolean checkoutWizard = false;
    private boolean checkoutWizardSummary = false;
    private boolean checkoutWizardCpd =  false;
    private boolean checkoutWizardCpds = false;
    private boolean checkoutWizardSummaryCpds = false;
    private boolean checkoutWizardSummarySfc = false;
    private boolean retailCheckoutOld = false;

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

    public FloridaPaymentSection floridaPaymentSection(){return initFloridaPaymentSection();}

    public FloridaCreditCard floridaCreditCard(){return initFloridaCreditCard();}

    public FloridaOther floridaAnother(){return initFloridaAnother();}

    public FloridaDebitCard floridaDebitCard(){return initFloridaDebitCard();}

    public PaymentTwoCreditCardsV3 paymentTwoCreditCardsV3(){return initPaymentTwoCreditCardsV3();}

    public PaymentSectionComboRetailV3 paymentSectionComboRetailV3(){return initPaymentSectionComboRetailV3();}

    public PaymentSectionGridV3 paymentSectionGridV3(){return initPaymentSectionGridV3();}

    public CreditCardDataV3 creditCardDataV3(){return initCreditCardDataV3();}

    public CreditCardDataRetailV3 creditCardDataRetailV3(){return initCreditCardDataRetailV3();}

    public TodoPagoDataV3 todoPagoDataV3(){return initDebitCardDataV3();}

    public VisaDebitCardDataV3 visaDebitCardDataV3(){return initVisaDebitCardDataV3();}

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

    @FindBy(css = "checkout-page club-almundo-login  button")
    private WebElement ingresarBtn;

    @FindBy(css = "input.button-buy")
    public WebElement comprarBtn;

    @FindBy(css = "body checkout-page .am-wizard-footer div button.button-next")
    public WebElement comprarWizardBtn;

    @FindBy(css = ".price__amount")
    public WebElement totalPrice;

    @FindBy(css = ".main-title")
    public WebElement mainTitleLbl;

    @FindBy(css = "div:nth-child(1) > label > a")
    private WebElement terminosCondiciones;

    @FindBy(css = ".flight-cluster > div:nth-child(1) .origin .iata")
    public WebElement originAirport;

    @FindBy(css = ".flight-cluster > div:nth-child(1) .destination .iata")
    public WebElement destinationAirport;

    @FindBy(css = ".flight-cluster > div:nth-child(1) .airline")
    public WebElement airlineName;

    @FindBy(css = ".flight-cluster > div:nth-child(1) .title > span.date")
    public WebElement startDate;

    @FindBy(css = ".flight-cluster > div:nth-child(2) .title > span.date")
    public WebElement endDate;

    private final String siguienteBtnLct = ".am-wizard-footer button";
    @FindBy(css = siguienteBtnLct)
    private WebElement siguienteBtn;

    private final String siguienteBisBtnLct = ".am-wizard-footer .button-next";
    @FindBy(css = siguienteBisBtnLct)
    private WebElement siguienteBisBtn;

    private final String comprarStepsBtnLct = ".am-wizard-footer .button-next";
    @FindBy(css = comprarStepsBtnLct)
    public WebElement comprarStepsBtn;

    private final String anteriorBtnLct = "checkout-page .button-before";
    @FindBy(css = anteriorBtnLct)
    private WebElement anteriorBtn;

    private final String siguienteSummaryBtnLct = "checkout-page .am-wizard-footer .button-next.ng-binding";
    @FindBy(css = siguienteSummaryBtnLct)
    private WebElement siguienteSummaryBtn;

    String thanksPageConfirmation = ".thanks-page-payment am-alert am-alert-title ng-transclude";

    //############################################### Actions ##############################################

    public LoginPopUp clickIngresarBtn(){
        logger.info("Clicking on [Ingresar] button.");
        ingresarBtn.click();
        return initLoginPopUp();
    }

    @SuppressWarnings("Duplicates")
    public CheckOutPageV3 clickSiguiente(){
        try{
            if(breakDownSectionV3().insuranceRdb.get(0).isDisplayed()) {
            scrollToElement(driver, siguienteBtn);
            waitWithTryCatch(driver, siguienteBtnLct, "Siguiente", 5);
            logger.info("Clicking on [Siguiente] button.");
            siguienteBtn.click();
            waitImplicitly(2000);
            }
        }catch(IndexOutOfBoundsException ouch){
            logger.warn("Insurance selection not available.");
        }
        return this;
    }

    @SuppressWarnings("Duplicates")
    public CheckOutPageV3 clickSiguienteTrips(){
        try{
            if(breakDownSectionV3().addTransferRdb.isDisplayed()) {
                scrollToElement(driver, siguienteBtn);
                waitWithTryCatch(driver, siguienteBtnLct, "Siguiente", 5);
                logger.info("Clicking on [Siguiente] button.");
                siguienteBtn.click();
                waitImplicitly(2000);
        }
        }catch (NoSuchElementException ouch){
            logger.warn("Add transfer radio button.");
        }
        return this;
    }

    @SuppressWarnings("Duplicates")
    public CheckOutPageV3 clickSiguienteSummary(){
        try {
            scrollToElement(driver, siguienteSummaryBtn);
            waitWithTryCatch(driver, siguienteSummaryBtnLct, "Siguiente (To Summary)", 5);
            logger.info("Clicking on [Siguiente] (To Summary) button.");
            siguienteSummaryBtn.click();
            waitImplicitly(2000);
        }catch(NoSuchElementException ouc){
            waitWithTryCatch(driver, "checkout-page am-wizard-checkout .am-wizard-footer button.button-next", "Siguiente (To Summary)", 5).click();
        }
        return this;
    }


    public CheckOutPageV3 clickSiguienteBis(){
        scrollToElement(driver, siguienteBisBtn);
        waitWithTryCatch(driver, siguienteBisBtnLct, "Siguiente (Bis)", 5);
        logger.info("Clicking on [Siguiente] (Bis) button.");
        siguienteBisBtn.click();
        waitImplicitly(2000);
        return this;
    }

    public CheckOutPageV3 clickAnterior(){
        waitImplicitly(2000);
        scrollToElement(driver, anteriorBtn);
        waitWithTryCatch(driver, anteriorBtnLct, "Anterior", 5);
        waitElementForClickable(driver, anteriorBtn, 10, "Anterior Clickable");
        logger.info("Clicking on [Anterior] button.");
        anteriorBtn.click();
        waitImplicitly(2000);
        return this;
    }

    public ThanksPageV3 clickComprarBtn(){
        logger.info("Final breakdown when booking is:[" + breakDownSectionV3().getFinalPriceString() + "]");
        if((baseURL.contains("st.almundo") || baseURL.contains("staging.almundo") || baseURL.contains("dv.almundo")) && submitReservation) {
            logger.info("Clicking on Comprar Button");
            waitImplicitly(6000);
            if(checkoutWizard){
                comprarWizardBtn.click();
            }
            else {
                try {
                    comprarBtn.click();
                } catch (NoSuchElementException ouch) {
                    comprarWizardBtn.click();
                }
            }
        } else {
            logger.info("Condition is not approved to submit reservation");
        }
        return initConfirmationPageV3();
    }

    @SuppressWarnings("Duplicates")
    public ThanksPageV3 clickComprarStepsBtn(){
        if((baseURL.contains("st.almundo") || baseURL.contains("staging.almundo") || baseURL.contains("dv.almundo")) && submitReservation) {
            scrollToElement(driver, comprarStepsBtn);
            waitWithTryCatch(driver, comprarStepsBtnLct, "Comprar button", 5);
            logger.info("Clicking on Comprar Button");
            waitImplicitly(1000);
            comprarStepsBtn.click();
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
        breakDownSectionV3().dealWithInsurance(addInsurance);
        breakDownSectionV3().dealWithTransfer(addTransfer);
        if(paymentData.contains("pago_dividido$")) {
            paymentSelectorRetailV3().selectPaymentMethod(PAGO_DIVIDIDO);
            paymentSelectorRetailSplitV3().populateSplittedPaymentInfo(getPaymentDataList(paymentData.replace("pago_dividido$","")), breakDownSectionV3().getFinalPrice());
        }
        else if (paymentData.contains("link_de_pago$") && retailCheckoutOld) {
            setUrlParameter("&slp=1");
            paymentSelectorRetailV3().selectPaymentMethod(LINK_DE_PAGO);
            String actualCheckoutUrl = paymentSelectorLinkV3().populateLinkDePagoInfo();

            if(paymentData.contains("rewards$")){
                dataManagement.setUsersDataList();
                JSONObject userData = dataManagement.setUserData("email");
                LoginPopUp loginPopUp  = clickIngresarBtn();
                loginPopUp.loginUser(userData.get("userEmail").toString(), userData.get("password").toString());
                loginPopUp.ingresarBtn.click();
                paymentData = clubAlmundoRewards().useRewardsYesClick(paymentData);
            }

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
            if(paymentData.contains(DEPOSIT)){
                paymentSelectorRetailV3().selectDepositRdb();
            } else if (paymentData.contains(TRANSFER)){
                paymentSelectorRetailV3().selectTransferRdb();
            } else if (paymentData.contains(CASH)){
                paymentSelectorRetailV3().selectCashRdb();
            } else if (retailCheckoutOld){
                if(!paymentData.contains(DESTINATION)) {
                    paymentSelectorRetailV3().selectCreditRbd();
                    paymentSectionComboRetailV3().populatePaymentSectionV3(paymentData);
                }
                creditCardDataRetailV3().populateCreditCardData(paymentData, true);

            } else if (!paymentData.contains(DESTINATION)){
                if (!retailCheckoutOld) {
                    logger.info("Running Retail New");

                    if(paymentData.contains("link_de_pago$")){
                        setUrlParameter("&slp=1");
                        floridaPaymentSection().linkDePagoClick();
                        floridaPaymentSection().enterEmailDelCliente(CUSTOMER_EMAIL);
                        floridaPaymentSection().enviarBtnClick();
                        paymentData = paymentData.replace("link_de_pago$","");
                        String actualCheckoutUrl = paymentSelectorLinkV3().populateLinkDePago();

                        if(paymentData.contains("two_cards")) {
                            dealWithTwoCards(paymentData);
                        } else  {
                            paymentSelectorV3().selectOneCreditCardRdb();
                            paymentSectionGridV3().populatePaymentSectionV3(paymentData.replace("link_de_pago$", ""), ".card-container-1");
                            creditCardDataV3().populateCreditCardData(paymentData.replace("link_de_pago$", ""), ".card-container-1");
                        }

                        acceptConditions();
                        clickComprarBtn();
                        waitWithTryCatch(driver, thanksPageConfirmation, "Payment confirmation", 10);
                        driver.navigate().to(actualCheckoutUrl);

                    } else {
                        List<String> paymentDataList = getPaymentDataList(paymentData);

                        int priceToPay = 0;
                        boolean isLastPayment = false;

                        if (paymentDataList.size() > 1) {
                            priceToPay = breakDownSectionV3().getFinalPrice() / paymentDataList.size();
                        } else {
                            isLastPayment = true;
                        }

                        int index = 0;
                        for (String paymentFormData : paymentDataList) {

                            if (paymentFormData.equals("Depósito") || paymentFormData.equals("Transferencia") || paymentFormData.equals("Efectivo")) {
                                floridaPaymentSection().otroMedioDePagoClick(paymentFormData);
                                floridaAnother().setOtherInfo(paymentFormData, String.valueOf(priceToPay), index, isLastPayment);
                            } else if (paymentFormData.equals("Débito")) {
                                floridaPaymentSection().tarjetaDeDebitoClick();
                                floridaCreditCard().populateCreditCardInfo(paymentFormData, String.valueOf(priceToPay), index, isLastPayment);
                            } else {
                                floridaPaymentSection().tarjetaDeCreditoClick();
                                floridaCreditCard().populateCreditCardInfo(paymentFormData, String.valueOf(priceToPay), index, isLastPayment);
                            }
                            index = ++index;
                            if((index + 1)  >= paymentDataList.size()){
                                isLastPayment = true;
                            }
                        }
                    }
                }
            }
        }
    }

    private void dealWithTwoCards(String paymentData){
        paymentSelectorV3().selectTwoCreditCardsRdb();
        paymentTwoCreditCardsV3().populateTwoCreditCards(getPaymentDataList(paymentData.replace("two_cards$","")), breakDownSectionV3().getFinalPrice());
        breakDownSectionV3().dealWithInsurance(addInsurance);
        if(refillPaymentData){
            paymentTwoCreditCardsV3().populateTwoCreditCards(getPaymentDataList(paymentData.replace("two_cards$","")), breakDownSectionV3().getFinalPrice());
        }
    }

    private void dealWithGridAndCombos(String paymentData){
        breakDownSectionV3().dealWithInsurance(addInsurance);
        breakDownSectionV3().dealWithTransfer(addTransfer);
        paymentSelectorV3().clickCambiarFopLnk();
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

    private void dealWithVisaDebitCard(String paymentData){
        paymentSelectorV3().selectVisaDebit();
        visaDebitCardDataV3().populateDebitCardData(paymentData);
    }

    private void dealWithPaymentForm(String paymentData){
        paymentData = paymentSelectorV3().setPromocode(paymentData);
        if(isRetailChannel()){
            dealWithRetail(paymentData);
        } else if (paymentData.equals(VISA_DEBIT)) {
            dealWithVisaDebitCard(paymentData);
        } else if (paymentData.contains(DEBIT)) {
            dealWithDebitCard(paymentData);
        } else if (paymentData.contains(TWO_CARDS)){
            // setUrlParameter("&stc=1");
            dealWithTwoCards(paymentData);
        } else {
            dealWithGridAndCombos(paymentData);
        }
    }

    /************* Checkout full Population Methods Calls (Dynamic Checkout) *************/

    @SuppressWarnings("Duplicates")
    public SummaryPage setCheckoutWizardInfoSummary(JSONArray passengerList,
                                                String paymentData,
                                                JSONObject billingData,
                                                JSONObject contactData, String productCheckOutPage){
        getCheckOutPageElements(productCheckOutPage);
        setInputDef();
        breakDownSectionV3().dealWithInsurance(addInsurance);
        if(method.contains("Flights")) {
            clickSiguiente();
        }
        if(method.contains("Trips")) {
            clickSiguienteTrips();
        }
        passengerSection().populatePassengerSection(passengerList);
        emergencyContact().populateEmergencyContact(contactData);
        contactSection().populateContactSection(contactData);
        clickSiguienteBis();
        dealWithPaymentForm(paymentData);
        if (!paymentData.contains(DESTINATION)) {
            billingSection().populateBillingSection(billingData);
        }
        clickSiguienteSummary();
        return initSummaryPage();
    }

    @SuppressWarnings("Duplicates")
    public ThanksPageV3 setCheckoutWizardInfoSummaryCpds(JSONArray passengerList,
                                                    String paymentData,
                                                    JSONObject billingData,
                                                    JSONObject contactData, String productCheckOutPage){
        getCheckOutPageElements(productCheckOutPage);
        setInputDef();
        breakDownSectionV3().dealWithInsurance(addInsurance);
        breakDownSectionV3().dealWithTransfer(addTransfer);
        if(method.contains("Flights") || method.contains("flights")) {
            clickSiguiente();
        }
        if(method.contains("Trips")) {
            clickSiguienteTrips();
        }
        dealWithPaymentForm(paymentData);
        if (!paymentData.contains(DESTINATION)) {
            billingSection().populateBillingSection(billingData);
        }
        clickSiguienteBis();
        passengerSection().populatePassengerSection(passengerList);
        emergencyContact().populateEmergencyContact(contactData);
        contactSection().populateContactSection(contactData);
        clickSiguienteSummary();
        acceptConditions();
        return initConfirmationPageV3();
    }

    @SuppressWarnings("Duplicates")
    public ThanksPageV3 setCheckoutWizardInfoSummaryCpd(JSONArray passengerList,
                                                         String paymentData,
                                                         JSONObject billingData,
                                                         JSONObject contactData, String productCheckOutPage){
        getCheckOutPageElements(productCheckOutPage);
        setInputDef();
        breakDownSectionV3().dealWithInsurance(addInsurance);
        breakDownSectionV3().dealWithTransfer(addTransfer);
        if(method.contains("Flights") || method.contains("flights")) {
            clickSiguiente();
        }
        if(method.contains("Trips")) {
            clickSiguienteTrips();
        }
        dealWithPaymentForm(paymentData);
        if (!paymentData.contains(DESTINATION)) {
            billingSection().populateBillingSection(billingData);
        }
        clickSiguienteBis();
        passengerSection().populatePassengerSection(passengerList);
        emergencyContact().populateEmergencyContact(contactData);
        contactSection().populateContactSection(contactData);
        acceptConditions();
        return initConfirmationPageV3();
    }

    @SuppressWarnings("Duplicates")
    public CheckOutPageV3 setCheckoutWizardInfo(JSONArray passengerList,
                                                String paymentData,
                                                JSONObject billingData,
                                                JSONObject contactData, String productCheckOutPage){
        getCheckOutPageElements(productCheckOutPage);
        setInputDef();
        breakDownSectionV3().dealWithInsurance(addInsurance);
        if(method.contains("Flights")) {
            clickSiguiente();
        }
        if(method.contains("Trips")) {
            clickSiguienteTrips();
        }
        passengerSection().populatePassengerSection(passengerList);
        emergencyContact().populateEmergencyContact(contactData);
        contactSection().populateContactSection(contactData);
        clickSiguienteBis();
        dealWithPaymentForm(paymentData);
        if (!paymentData.contains(DESTINATION)) {
            billingSection().populateBillingSection(billingData);
        }
        acceptConditions();
        return this;
    }

    @SuppressWarnings("Duplicates")
    public CheckOutPageV3 setCheckoutInfoSummarySfc(JSONArray passengerList,
                                                    String paymentData,
                                                    JSONObject billingData,
                                                    JSONObject contactData, String productCheckOutPage){
        getCheckOutPageElements(productCheckOutPage);
        setInputDef();
        breakDownSectionV3().dealWithInsurance(addInsurance);
        dealWithPaymentForm(paymentData);
        passengerSection().populatePassengerSection(passengerList);
        emergencyContact().populateEmergencyContact(contactData);
        if (!paymentData.contains(DESTINATION)) {
            billingSection().populateBillingSection(billingData);
        }
        contactSection().populateContactSection(contactData);
        acceptConditions();
        return this;
    }



    /*************** Checkout full Population Methods Calls (2 Cards - Trips) ***********/

    public CheckOutPageV3 setCheckoutWizardInfo(JSONArray passengerList,
                                                String paymentData1,
                                                String paymentData2,
                                                JSONObject billingData,
                                                JSONObject contactData, String productCheckOutPage){
        getCheckOutPageElements(productCheckOutPage);
        setInputDef();
        breakDownSectionV3().dealWithInsurance(addInsurance);
        if(method.contains("Flights")) {
            clickSiguiente();
        }
        if(method.contains("Trips")) {
            clickSiguienteTrips();
        }
        passengerSection().populatePassengerSection(passengerList);
        emergencyContact().populateEmergencyContact(contactData);
        contactSection().populateContactSection(contactData);
        clickSiguienteBis();

        paymentSelectorV3().selectTwoCreditCardsRdb();

        paymentSectionGridV3().populatePaymentSectionV3(paymentData1, ".card-container-1");
        creditCardDataV3().populateCreditCardData(paymentData1, ".card-container-1");
        paymentSectionGridV3().populatePaymentSectionV3(paymentData2, ".card-container-2");
        creditCardDataV3().populateCreditCardData(paymentData2, ".card-container-2");

        billingSection().populateBillingSection(billingData);
        acceptConditions();
        return this;
    }

    public CheckOutPageV3 setCheckOutInfo(JSONArray passengerList,
                                          String paymentData,
                                          JSONObject billingData,
                                          JSONObject contactData,
                                          String productCheckOutPage) {
        setCheckOutSections(getCheckoutUrl());
        if(checkoutWizard){
            setCheckoutWizardInfo(passengerList, paymentData, billingData, contactData, productCheckOutPage);
        } else if(checkoutWizardSummary) {
            setCheckoutWizardInfoSummary(passengerList, paymentData, billingData, contactData, productCheckOutPage);
        } else if(checkoutWizardSummaryCpds) {
            setCheckoutWizardInfoSummaryCpds(passengerList, paymentData, billingData, contactData, productCheckOutPage);
        } else if(checkoutWizardSummarySfc) {
            setCheckoutInfoSummarySfc(passengerList, paymentData, billingData, contactData, productCheckOutPage);
        } else if (checkoutWizardCpd) {
            setCheckoutWizardInfoSummaryCpd(passengerList, paymentData, billingData, contactData, productCheckOutPage);
        } else{
            getCheckOutPageElements(productCheckOutPage);
            setInputDef();
            dealWithPaymentForm(paymentData);
            passengerSection().populatePassengerSection(passengerList);
            emergencyContact().populateEmergencyContact(contactData);
            if (!paymentData.contains(DESTINATION)) {
                billingSection().populateBillingSection(billingData);
            }
            contactSection().populateContactSection(contactData);
            if (isRetailChannel) {
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
        boolean payWithLink = false;
        String thanksPageConfirmation = "";
        String actualCheckoutUrl = "";
        getCheckOutPageElements(productCheckOutPage);
        redirectCheckout();
        setCheckOutSections(getCheckoutUrl());
        breakDownSectionV3().dealWithTransfer(addTransfer);
        setInputDef();
        if(paymentData1.contains("link_de_pago$")){
            setUrlParameter("&slp=1");
            payWithLink = true;
            thanksPageConfirmation = ".thanks-page-payment am-alert am-alert-title ng-transclude";
            paymentSelectorRetailV3().selectPaymentMethod(LINK_DE_PAGO);
            actualCheckoutUrl = paymentSelectorLinkV3().populateLinkDePagoInfo();
            paymentData1 = paymentData1.replace("link_de_pago$","");
        }

        if (paymentSelectorSvd) {
            paymentSelectorV3().selectTwoCreditCardsRdb();
        } else if (paymentSelectorV3().selectTwoCreditCardsRdbIsDisplayed()) {
            paymentSelectorV3().selectTwoCreditCardsRdb();
        }

        if (creditCardComboSc) {
            paymentSectionComboV3().populatePaymentSectionV3(paymentData1, ".credit-card-selector-1");
            creditCardDataV3().populateCreditCardData(paymentData1, ".credit-card-selector-1");
            paymentSectionComboV3().populatePaymentSectionV3(paymentData2, ".credit-card-selector-2");
            creditCardDataV3().populateCreditCardData(paymentData2, ".credit-card-selector-2");
        } else {
            paymentSectionGridV3().populatePaymentSectionV3(paymentData1, ".card-container-1");
            creditCardDataV3().populateCreditCardData(paymentData1, ".card-container-1");
            paymentSectionGridV3().populatePaymentSectionV3(paymentData2, ".card-container-2");
            creditCardDataV3().populateCreditCardData(paymentData2, ".card-container-2");
        }

        if(payWithLink){
            acceptConditions();
            clickComprarBtn();
            waitWithTryCatch(driver, thanksPageConfirmation, "Payment confirmation", 10);
            driver.navigate().to(actualCheckoutUrl);
        }

        passengerSection().populatePassengerSection(passengerList);
        emergencyContact().populateEmergencyContact(contactData);
        billingSection().populateBillingSection(billingData);
        contactSection().populateContactSection(contactData);
        if(isRetailChannel()){
            agentSectionV3().setAgentEmail(AGENT_EMAIL);
        }
        acceptConditions();
        return this;
    }

    public CheckOutPageV3 acceptConditions(){
        FooterSectionV3 footerSection = initFooterSectionV3();
        logger.info("---------- Checking options Footer Section ----------");
        footerSection.acceptTermsAndConditions();
        if(isElementRequiered(checkOutPageElements, "accepted")) {
            try {
            footerSection.acceptItinerary();
            footerSection.clickConfirmarBtn();
            waitImplicitly(1500);
            }catch(NoSuchElementException ouch){
                logger.info("Accept itinerary didn't show up.");
            }
        }
        return this;
    }

    public AgreementPage termAndConditionsClick(){
        logger.info("Clicking on Terms and Conditions Link...");
        waitImplicitly(1000);
        if(countryPar.equals(COLOMBIA)){
            try {
                terminosCondiciones = driver.findElement(By.cssSelector("div:nth-child(1) > label > a:nth-child(3)"));
            }catch(NoSuchElementException ouch){
                logger.info("Terms and conditions didn't show up.");
            }
        }
        terminosCondiciones.click();
        PageUtils.switchToNewTab(driver);
        return initTermsAndConditonsPage();
    }

    public void setInputDef() {
        try {
             if(baseURL.contains("st.almundo")) {
                inputDef = new InputDefinitions(API_STG_URL + "api/v3/cart/" + getCartId() + "/input-definitions?site=" + countryPar.substring(0, 3) + "&language=es");
            } else if (baseURL.contains("dv.almundo")) {
                 inputDef = new InputDefinitions(DEV_STG_URL + "api/v3/cart/" + getCartId() + "/input-definitions?site=" + countryPar.substring(0, 3) + "&language=es"); }
            else {
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
        logger.info("Setting checkout configuration...");
        if(checkoutUrl.contains("svd=1")){
            logger.info("[svd=1] is enabled.");
            paymentSelectorSvd = true;
        } else{
            logger.info("[svd=1] is not enabled."); }

        if(checkoutUrl.contains("sc=1")){
            logger.info("[sc=1] is enabled.");
            creditCardComboSc =  true;
        } else {
            logger.info("[sc=1] is not enabled."); }

        if(checkoutUrl.contains("vuelohotel")){
            logger.info("[Selector de pago] is enabled.");
            paymentSelectorSvd = true;
        }else {
            logger.info("[Selector de pago] is not enabled.");}

        if(checkoutUrl.contains("sw=cdp")){
            if(checkoutUrl.contains("sw=cdps")){
                logger.info("[Checkout Wizard Summary] is enabled.");
                checkoutWizardSummary = true;
            }else {
                logger.info("[Checkout Wizard] is enabled.");
                checkoutWizard = true;
            }
        } else {
            logger.info("[Checkout Wizard] is not enabled.");
        }

        if(checkoutUrl.contains("sw=cpd")){
            logger.info("[Checkout Wizard Cpd] is enabled.");
            checkoutWizardCpd = true;
        } else{
            logger.info("[Checkout Wizard Cpd] is not enabled.");
        }

        if(checkoutUrl.contains("sw=cpd")){
            if(checkoutUrl.contains("sw=cpds")){
                logger.info("[Checkout Wizard Summary] is enabled (cpds).");
                checkoutWizardSummaryCpds = true;
            }else {
                logger.info("[Checkout Wizard] is enabled cpd.");
                checkoutWizardCpds = true;
            }
        } else {
            logger.info("[Checkout Wizard] (cpds) is not enabled.");
        }

        if(checkoutUrl.contains("sfc=1")){
            logger.info("[Fast Checkout] is enabled sfc=1");
            checkoutWizardSummarySfc = true;
        }else{
            logger.info("[Fast Checkout] is not enabled sfc=1");
        }

        if(checkoutUrl.contains("ssp=1")){
            logger.info("[Retail Old] Version is enabled ssp=1");
            retailCheckoutOld = true;
        } else {
            logger.info("Retail Old Version not found ssp=1");
        }
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
        if(baseURL.contains("ccr") || baseURL.contains("sucursales")){
            isRetailChannel = true;
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
        return isRetailChannel;
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
            logger.info("Waiting for checkout load complete.");
            breakDownSectionV3().getFinalPrice();
            logger.info("Waiting some second in case of redirection...");
            waitImplicitly(3000);
            breakDownSectionV3().getFinalPrice();
            PageUtils.waitUrlContains(driver, 10, "checkout", "Checkout V3");
            checkoutUrl =  driver.getCurrentUrl();
        } catch(Exception time) {
            logger.info("Checkout V3 was not loaded.");
            setResultSauceLabs(FAILED);
        }
        return checkoutUrl;
    }
}