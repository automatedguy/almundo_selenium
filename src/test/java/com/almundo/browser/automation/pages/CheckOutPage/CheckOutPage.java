package com.almundo.browser.automation.pages.CheckOutPage;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.utils.JsonRead;
import com.almundo.browser.automation.utils.PageUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by gabrielcespedes on 04/11/16.
 */
public class CheckOutPage extends TestBaseSetup {

    public CheckOutPage(WebDriver driver) { super.driver = driver; }

    public static JSONObject checkOutPageElements = null;

    public PassengerSection passengerSection() {
        return initPassengerInfoSection();
    }

    public PickUpLocationSection pickUpLocationSection() {
        return initPickUpLocationSection();
    }

    public PaymentSection paymentSection() {
        return initPaymentSection();
    }

    public BillingSection billingSection() {
        return initBillingSection();
    }

    public ContactSection contactSection() {
        return initContactInfoSection();
    }

    //############################################### Locators ##############################################

    @FindBy(css = ".button.button--lg.button--secondary")
    public WebElement comprarBtn;

    @FindBy(css = ".price__amount")
    public WebElement totalPrice;

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

    @FindBy(id = "assistance_yes")
    public WebElement assistanceRdb;

    //############################################### Actions ##############################################

    public ConfirmationPage clickComprarBtn(){
        if((baseURL.contains("st.almundo") || baseURL.contains("staging.almundo")) && submitReservation) {
            logger.info("Clicking on Comprar Button");
            comprarBtn.click();
        } else {
            logger.info("Condition is not approved to submit reservation");
        }
        return initConfirmationPage();
    }

    public CheckOutPage selectAssistanceRdb(){
        PageUtils.waitElementForVisibility(driver, By.id("assistance_yes"), 45, "Include Insurance Radio Button.");
        logger.info("Clicking on Insurance Assistance");
        assistanceRdb.click();
        return this;
    }

    public int getTotalPrice() {
        logger.info("Total Price: [" + totalPrice.getText() + "]");
        String price = (totalPrice.getText().replace(".", ""));
        price = price.replaceAll("\\s","");
        return Integer.parseInt(price);
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

    private static void getCheckOutPageElements(String productCheckOutPage)  {
        checkOutPageElements = JsonRead.getJsonDataObject(jsonCountryPropertyObject, productCheckOutPage, "countries_properties.json");
    }

    public CheckOutPage populateCheckOutPage(JSONArray passengerList,
                                             JSONObject paymentData,
                                             JSONObject billingData,
                                             JSONObject contactData,
                                             String productCheckOutPage,
                                             boolean includeAssistance)
    {
        getCheckOutPageElements(productCheckOutPage);
        waitForCheckoutLoad();
        if(includeAssistance){selectAssistanceRdb();}
        passengerSection().populatePassengerSection(passengerList);
        pickUpLocationSection().populatePickUpLocationSection();
        paymentSection().selectPaymentOption(paymentData, productCheckOutPage);
        billingSection().populateBillingSection(billingData);
        contactSection().populateContactSection(contactData);
        acceptConditions();
        return this;
    }

    public void waitForCheckoutLoad(){
        PageUtils.waitElementForVisibility(driver, By.id("first_name0"), 45, "Checkout Page");
        logger.info("Checkout URL: " + "[" + driver.getCurrentUrl() + "]");
    }

    private CheckOutPage acceptConditions(){
        FooterSection footerSection = initFooterSection();
        logger.info("---------- Checking Footer Section options ----------");
        footerSection.acceptTermsAndConditions();
        if(isElementRequiered(checkOutPageElements, "accepted")) {
            footerSection.acceptItinerary();
            footerSection.confirmarClick();
        }
        if(isElementRequiered(checkOutPageElements, "agentEmail")) {
            footerSection.setEmailTxt("gabriel.cespedes@almundo.com");
        }
        return this;
    }
}
