package com.almundo.browser.automation.data;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.utils.JsonRead;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by leandro.efron on 10/2/2017.
 */
public class DataManagement extends TestBaseSetup {

    private JSONObject vueloDataTripList = null;
    private JSONObject vueloDataTripItinerary = null;

    private JSONObject hotelesDataTripList = null;
    private JSONObject hotelDataTripItinerary = null;

    private JSONObject vueloHotelDataTripList = null;
    private JSONObject vueloHotelDataTripItinerary = null;

    private JSONObject autosDataTripList = null;
    private JSONObject autosDataTripItinerary = null;

    public JSONObject passengersList = null;
    private JSONObject passengerData = null;
    public JSONArray passengerJsonList = new JSONArray();

    private JSONObject paymentList = null;

    private JSONObject contactList = null;

    private static JSONObject billingsList = null;

    private static JSONObject usersDataList = null;

    public String originAuto;
    public String originFull;
    public String originAuto2;
    public String originFull2;
    public String originAuto3;
    public String originFull3;
    public String destinationAuto;
    public String destinationFull;
    public String destinationAuto2;
    public String destinationFull2;
    public String destinationAuto3;
    public String destinationFull3;
    public int startDate;
    public int startDate2;
    public int startDate3;
    public int endDate;
    public int adults;
    public int childs;
    public int rooms;
    public String childAgeRange;
    public String flightClass;
    public String pickUpTime;
    public String dropOffTime;
    public String ageRange;

    //------------------------- HOME PAGE -----------------------------
    //VUELOS DATA TRIP

    public void getFlightsItineraryData(){
        getFlightsDataTripList();
        getPassengersList();
        getPaymentList();
        getBillingList();
        getContactList();
    }

    public void getFlightsDataTripList() {
        vueloDataTripList = JsonRead.getJsonDataObject(jsonDataObject, "flights", countryPar.toLowerCase() + "_data.json");
    }

    public void getOneWayDataTripItinerary(String dataSet) {
        vueloDataTripItinerary = JsonRead.getJsonDataObject(vueloDataTripList, dataSet, countryPar.toLowerCase() + "_data.json");

        originAuto = vueloDataTripItinerary.get("originAuto").toString();
        originFull = vueloDataTripItinerary.get("originFull").toString();

        destinationAuto = vueloDataTripItinerary.get("destinationAuto").toString();
        destinationFull = vueloDataTripItinerary.get("destinationFull").toString();

        startDate = Integer.parseInt(vueloDataTripItinerary.get("startDate").toString());

        adults = Integer.parseInt(vueloDataTripItinerary.get("adults").toString());
        childs = Integer.parseInt(vueloDataTripItinerary.get("childs").toString());
        childAgeRange = vueloDataTripItinerary.get("childAgeRange").toString();

        flightClass = vueloDataTripItinerary.get("flightClass").toString();
    }

    public void getRoundTripDataTripItinerary(String dataSet) {
        vueloDataTripItinerary = JsonRead.getJsonDataObject(vueloDataTripList, dataSet, countryPar.toLowerCase() + "_data.json");

        originAuto = vueloDataTripItinerary.get("originAuto").toString();
        originFull = vueloDataTripItinerary.get("originFull").toString();

        destinationAuto = vueloDataTripItinerary.get("destinationAuto").toString();
        destinationFull = vueloDataTripItinerary.get("destinationFull").toString();

        startDate = Integer.parseInt(vueloDataTripItinerary.get("startDate").toString());
        endDate = Integer.parseInt(vueloDataTripItinerary.get("endDate").toString());

        adults = Integer.parseInt(vueloDataTripItinerary.get("adults").toString());
        childs = Integer.parseInt(vueloDataTripItinerary.get("childs").toString());
        childAgeRange = vueloDataTripItinerary.get("childAgeRange").toString();

        flightClass = vueloDataTripItinerary.get("flightClass").toString();
    }

    public void getMultiDestDataTripItinerary(String dataSet) {
        vueloDataTripItinerary = JsonRead.getJsonDataObject(vueloDataTripList, dataSet, countryPar.toLowerCase() + "_data.json");

        originAuto = vueloDataTripItinerary.get("originAuto").toString();
        originFull = vueloDataTripItinerary.get("originFull").toString();

        originAuto2 = vueloDataTripItinerary.get("originAuto2").toString();
        originFull2 = vueloDataTripItinerary.get("originFull2").toString();

        originAuto3 = vueloDataTripItinerary.get("originAuto3").toString();
        originFull3 = vueloDataTripItinerary.get("originFull3").toString();

        destinationAuto = vueloDataTripItinerary.get("destinationAuto").toString();
        destinationFull = vueloDataTripItinerary.get("destinationFull").toString();

        destinationAuto2 = vueloDataTripItinerary.get("destinationAuto2").toString();
        destinationFull2 = vueloDataTripItinerary.get("destinationFull2").toString();

        destinationAuto3 = vueloDataTripItinerary.get("destinationAuto3").toString();
        destinationFull3 = vueloDataTripItinerary.get("destinationFull3").toString();

        startDate = Integer.parseInt(vueloDataTripItinerary.get("startDate").toString());
        startDate2 = Integer.parseInt(vueloDataTripItinerary.get("startDate2").toString());
        startDate3 = Integer.parseInt(vueloDataTripItinerary.get("startDate3").toString());

        adults = Integer.parseInt(vueloDataTripItinerary.get("adults").toString());
        childs = Integer.parseInt(vueloDataTripItinerary.get("childs").toString());
        childAgeRange = vueloDataTripItinerary.get("childAgeRange").toString();

        flightClass = vueloDataTripItinerary.get("flightClass").toString();
    }

    //HOTELES DATA TRIP

    public void getHotelsItineraryData(){
        getHotelsDataTripList();
        getPassengersList();
        getPaymentList();
        getBillingList();
        getContactList();
    }

    public void getHotelsDataTripList() {
        hotelesDataTripList = JsonRead.getJsonDataObject(jsonDataObject, "hotels", countryPar.toLowerCase() + "_data.json");
    }

    public void getHotelsDataTripItinerary(String dataSet) {
        hotelDataTripItinerary = JsonRead.getJsonDataObject(hotelesDataTripList, dataSet, countryPar.toLowerCase() + "_data.json");

        destinationAuto = hotelDataTripItinerary.get("destinationAuto").toString();
        destinationFull = hotelDataTripItinerary.get("destinationFull").toString();

        startDate = Integer.parseInt(hotelDataTripItinerary.get("startDate").toString());
        endDate = Integer.parseInt(hotelDataTripItinerary.get("endDate").toString());

        adults = Integer.parseInt(hotelDataTripItinerary.get("adults").toString());
        childs = Integer.parseInt(hotelDataTripItinerary.get("childs").toString());

        rooms = Integer.parseInt(hotelDataTripItinerary.get("rooms").toString());
    }

    //VUELO+HOTEL DATA TRIP

    public void getCarsItineraryData(){
        getCarsDataTripList();
        getPassengersList();
        getPaymentList();
        getBillingList();
        getContactList();
    }

    public void getTripsItineraryData(){
        getUsersDataList();
        getTripsDataTripList();
        getPassengersList();
        getPaymentList();
        getBillingList();
        getContactList();
    }

    public void getTripsDataTripList() {
        vueloHotelDataTripList = JsonRead.getJsonDataObject(jsonDataObject, "trips", countryPar.toLowerCase() + "_data.json");
    }

    public void getTripsDataTripItinerary(String dataSet) {
        vueloHotelDataTripItinerary = JsonRead.getJsonDataObject(vueloHotelDataTripList, dataSet, countryPar.toLowerCase() + "_data.json");

        originAuto = vueloHotelDataTripItinerary.get("originAuto").toString();
        originFull = vueloHotelDataTripItinerary.get("originFull").toString();

        destinationAuto = vueloHotelDataTripItinerary.get("destinationAuto").toString();
        destinationFull = vueloHotelDataTripItinerary.get("destinationFull").toString();

        startDate = Integer.parseInt(vueloHotelDataTripItinerary.get("startDate").toString());
        endDate = Integer.parseInt(vueloHotelDataTripItinerary.get("endDate").toString());

        adults = Integer.parseInt(vueloHotelDataTripItinerary.get("adults").toString());
        childs = Integer.parseInt(vueloHotelDataTripItinerary.get("childs").toString());

        rooms = Integer.parseInt(vueloHotelDataTripItinerary.get("rooms").toString());
    }

    //AUTOS DATA TRIP

    public void getCarsDataTripList() {
        autosDataTripList = JsonRead.getJsonDataObject(jsonDataObject, "cars", countryPar.toLowerCase() + "_data.json");
    }

    public void getCarsDataTripItinerary(String dataSet) {
        autosDataTripItinerary = JsonRead.getJsonDataObject(autosDataTripList, dataSet, countryPar.toLowerCase() + "_data.json");

        originAuto = autosDataTripItinerary.get("originAuto").toString();
        originFull = autosDataTripItinerary.get("originFull").toString();

        destinationAuto = autosDataTripItinerary.get("destinationAuto").toString();
        destinationFull = autosDataTripItinerary.get("destinationFull").toString();

        startDate = Integer.parseInt(autosDataTripItinerary.get("startDate").toString());
        endDate = Integer.parseInt(autosDataTripItinerary.get("endDate").toString());

        pickUpTime = autosDataTripItinerary.get("pickUpTime").toString();
        dropOffTime = autosDataTripItinerary.get("dropOffTime").toString();

        ageRange = autosDataTripItinerary.get("ageRange").toString();
    }

    //LOGIN
    public void getUsersDataList() {
        usersDataList = JsonRead.getJsonDataObject(jsonDataObject, "users", countryPar.toLowerCase() + "_data.json");
    }

    public JSONObject getUserData(String dataSet) {
        JSONObject userData = JsonRead.getJsonDataObject(usersDataList, dataSet, countryPar.toLowerCase() + "_data.json");
        return userData;
    }

    //------------------------- CHECKOUT -----------------------------
    //PASSENGER DATA
    public void getPassengersList()  {
        passengersList = JsonRead.getJsonDataObject(jsonDataObject, "passengers", countryPar.toLowerCase() + "_data.json");
    }

    public void getPassengerData(String dataSet)  {
        passengerData = JsonRead.getJsonDataObject(passengersList, dataSet, countryPar.toLowerCase() + "_data.json");
        passengerJsonList.add(passengerData);
    }

    //PAYMENT DATA
    public void getPaymentList()  {
        paymentList = JsonRead.getJsonDataObject(jsonDataObject, "payment", countryPar.toLowerCase() + "_data.json");
    }

    public JSONObject getPaymentData(String dataSet)  {
        JSONObject paymentData = JsonRead.getJsonDataObject(paymentList, dataSet, countryPar.toLowerCase() + "_data.json");
        return paymentData;
    }

    //BILLING DATA
    public void getBillingList()  {
        billingsList = JsonRead.getJsonDataObject(jsonDataObject, "billings", countryPar.toLowerCase() + "_data.json");
    }

    public JSONObject getBillingData(String dataSet)  {
        JSONObject billingData = JsonRead.getJsonDataObject(billingsList, dataSet, countryPar.toLowerCase() + "_data.json");
        return billingData;
    }

    //CONTACT DATA
    public void getContactList()  {
        contactList = JsonRead.getJsonDataObject(jsonDataObject, "contacts", countryPar.toLowerCase() + "_data.json");
    }

    public JSONObject getContactData(String dataSet)  {
        JSONObject contactData = JsonRead.getJsonDataObject(contactList, dataSet, countryPar.toLowerCase() + "_data.json");
        return contactData;
    }
}
