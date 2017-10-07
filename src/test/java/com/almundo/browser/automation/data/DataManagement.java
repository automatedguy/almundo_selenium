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

    private JSONObject trippersDataTripList = null;
    private JSONObject trippersDataTripItinerary = null;

    private JSONObject almundoTripInfo = null;

    private JSONObject autosDataTripList = null;
    private JSONObject autosDataTripItinerary = null;

    private JSONObject packagesDataTripList = null;
    private JSONObject packagesDataTripItinerary = null;

    private JSONObject assistanceDataTripList = null;
    private JSONObject assistanceDataTripItinerary = null;

    public JSONObject passengersList = null;
    private JSONObject passengerData = null;
    public JSONArray passengerJsonList = new JSONArray();

    private JSONObject paymentList = null;

    private JSONObject contactList = null;

    private static JSONObject billingsList = null;

    private static JSONObject usersDataList = null;

    private String originAuto;
    private String originAuto2;
    private String originAuto3;

    private String originFull;
    private String originFull2;
    private String originFull3;

    private String destinationAuto;
    private String destinationAuto2;
    private String destinationAuto3;

    private String destinationFull;
    private String destinationFull2;
    private String destinationFull3;

    public int startDate;
    public int startDate2;
    public int startDate3;

    public int endDate;
    public int adults;
    public int childs;

    public int rooms;
    public String tripType;
    public int persons;
    public String childAgeRange;
    public String flightClass;
    public String pickUpTime;
    public String dropOffTime;
    public String ageRange;

    public String tripName;

    public String eventName;
    public String eventDescription;

    /******************* Getters *******************/

     public String getOriginAuto(){
         return originAuto;
     }

     public String getOriginAuto2(){
         return originAuto2;
     }

    public String getOriginAuto3(){
        return originAuto3;
    }

    public String getOriginFull(){
        return originFull;
    }

    public String getOriginFull2(){
        return originFull2;
    }

    public String getOriginFull3(){
        return originFull3;
    }

    public String getDestinationAuto(){
        return destinationAuto;
    }

    public String getDestinationAuto2(){
        return destinationAuto2;
    }

    public String getDestinationAuto3(){
        return destinationAuto3;
    }

    public String getDestinationFull(){
        return destinationFull;
    }

    public String getDestinationFull2(){
        return destinationFull2;
    }

    public String getDestinationFull3(){
        return destinationFull3;
    }


    //------------------------- HOME PAGE -----------------------------

    //VUELOS DATA TRIP

    public void setFlightsItineraryData(){
        setFlightsDataTripList();
        setPassengersList();
        setPaymentList();
        setBillingList();
        setContactList();
        setUsersDataList();
    }

    public void setFlightsDataTripList() {
        vueloDataTripList = JsonRead.getJsonDataObject(jsonDataObject, "flights", countryPar.toLowerCase() + "_data.json");
    }

    public void setOneWayDataTripItinerary(String dataSet) {
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

    public void setRoundTripDataTripItinerary(String dataSet) {
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

    public void setMultiDestDataTripItinerary(String dataSet) {
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

    public void setPackagesInitneraryData(){
        setPackagesDataTripList();
        setPassengersList();
        setPaymentList();
        setBillingList();
        setContactList();
        setUsersDataList();
    }

    public void setAssistanceItineraryData(){
        setAssistanceDataTripList();
        setPassengersList();
        setPaymentList();
        setBillingList();
        setContactList();
        setUsersDataList();
    }

    //HOTELES DATA TRIP

    public void setHotelsItineraryData(){
        setHotelsDataTripList();
        setPassengersList();
        setPaymentList();
        setBillingList();
        setContactList();
        setUsersDataList();
    }

    public void setHotelsDataTripList() {
        hotelesDataTripList = JsonRead.getJsonDataObject(jsonDataObject, "hotels", countryPar.toLowerCase() + "_data.json");
    }

    public void setHotelsDataTripItinerary(String dataSet) {
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

    public void setCarsItineraryData(){
        setCarsDataTripList();
        setPassengersList();
        setPaymentList();
        setBillingList();
        setContactList();
    }

    public void setTripsItineraryData(){
        setUsersDataList();
        setTripsDataTripList();
        setPassengersList();
        setPaymentList();
        setBillingList();
        setContactList();
    }

    public void setTrippersItineraryData(){
        setUsersDataList();
        setTripsDataTripList();
        setPassengersList();
        setPaymentList();
        setBillingList();
        setContactList();
    }

    public void setTripsDataTripList() {
        vueloHotelDataTripList = JsonRead.getJsonDataObject(jsonDataObject, "trips", countryPar.toLowerCase() + "_data.json");
    }

    public void setTripsDataTripItinerary(String dataSet) {
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

    public void setTrippersDataTripList() {
        trippersDataTripList = JsonRead.getJsonDataObject(jsonDataObject, "trippers", countryPar.toLowerCase() + "_data.json");
    }

    public void setAlmundoTripInfo(String dataSet){

        almundoTripInfo = JsonRead.getJsonDataObject(trippersDataTripList, dataSet, countryPar.toLowerCase() + "_data.json");

        tripName = almundoTripInfo.get("tripName").toString();
        destinationAuto = almundoTripInfo.get("destinationAuto").toString();
        destinationFull = almundoTripInfo.get("destinationFull").toString();
        startDate = Integer.parseInt(almundoTripInfo.get("startDate").toString());
        endDate = Integer.parseInt(almundoTripInfo.get("endDate").toString());
    }

    public void setAlmundoDataTripsItinerary(String dataSet) {
        trippersDataTripItinerary = JsonRead.getJsonDataObject(trippersDataTripList, dataSet, countryPar.toLowerCase() + "_data.json");

        eventName = trippersDataTripItinerary.get("eventName").toString();
        eventDescription = trippersDataTripItinerary.get("eventDescription").toString();

        originAuto = trippersDataTripItinerary.get("originAuto").toString();
        originFull = trippersDataTripItinerary.get("originFull").toString();

        destinationAuto = trippersDataTripItinerary.get("destinationAuto").toString();
        destinationFull = trippersDataTripItinerary.get("destinationFull").toString();

        startDate = Integer.parseInt(trippersDataTripItinerary.get("startDate").toString());
        endDate = Integer.parseInt(trippersDataTripItinerary.get("endDate").toString());

        pickUpTime = trippersDataTripItinerary.get("pickUpTime").toString();
        dropOffTime = trippersDataTripItinerary.get("dropOffTime").toString();

    }

    //AUTOS DATA TRIP

    public void setCarsDataTripList() {
        autosDataTripList = JsonRead.getJsonDataObject(jsonDataObject, "cars", countryPar.toLowerCase() + "_data.json");
    }

    public void setCarsDataTripItinerary(String dataSet) {
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


    // PAQUETES DATA TRIP
    public void setPackagesDataTripList() {
        packagesDataTripList = JsonRead.getJsonDataObject(jsonDataObject, "packages", countryPar.toLowerCase() + "_data.json");
    }

    public void setPackagesDataTripItinerary(String dataSet){
        packagesDataTripItinerary = JsonRead.getJsonDataObject(packagesDataTripList, dataSet, countryPar.toLowerCase() + "_data.json");

        originFull = packagesDataTripItinerary.get("originFull").toString();

        adults = Integer.parseInt(packagesDataTripItinerary.get("adults").toString());
        childs = Integer.parseInt(packagesDataTripItinerary.get("childs").toString());

        rooms = Integer.parseInt(packagesDataTripItinerary.get("rooms").toString());
    }

    //ASSISTANCE DATA TRIP

    public void setAssistanceDataTripList() {
        assistanceDataTripList = JsonRead.getJsonDataObject(jsonDataObject, "assistance", countryPar.toLowerCase() + "_data.json");
    }

    public void setAssistanceDataTripItinerary(String dataSet) {
        assistanceDataTripItinerary = JsonRead.getJsonDataObject(assistanceDataTripList, dataSet, countryPar.toLowerCase() + "_data.json");

        tripType = assistanceDataTripItinerary.get("tripType").toString();

        destinationAuto = assistanceDataTripItinerary.get("destinationAuto").toString();
        destinationFull = assistanceDataTripItinerary.get("destinationFull").toString();

        startDate = Integer.parseInt(assistanceDataTripItinerary.get("startDate").toString());
        endDate = Integer.parseInt(assistanceDataTripItinerary.get("endDate").toString());

        persons = Integer.parseInt(assistanceDataTripItinerary.get("persons").toString());
    }


    //LOGIN
    public void setUsersDataList() {
        usersDataList = JsonRead.getJsonDataObject(jsonDataObject, "users", countryPar.toLowerCase() + "_data.json");
    }

    public JSONObject setUserData(String dataSet) {
        JSONObject userData = JsonRead.getJsonDataObject(usersDataList, dataSet, countryPar.toLowerCase() + "_data.json");
        return userData;
    }

    //------------------------- CHECKOUT -----------------------------

    //PASSENGER DATA
    public void setPassengersList()  {
        passengersList = JsonRead.getJsonDataObject(jsonDataObject, "passengers", countryPar.toLowerCase() + "_data.json");
    }

    public void setPassengerData(String dataSet)  {
        passengerData = JsonRead.getJsonDataObject(passengersList, dataSet, countryPar.toLowerCase() + "_data.json");
        passengerJsonList.add(passengerData);
    }

    //PAYMENT DATA
    public void setPaymentList()  {
        paymentList = JsonRead.getJsonDataObject(jsonDataObject, "payment", countryPar.toLowerCase() + "_data.json");
    }

    public JSONObject setPaymentData(String dataSet)  {
        JSONObject paymentData = JsonRead.getJsonDataObject(paymentList, dataSet, countryPar.toLowerCase() + "_data.json");
        return paymentData;
    }

    //BILLING DATA
    public void setBillingList()  {
        billingsList = JsonRead.getJsonDataObject(jsonDataObject, "billings", countryPar.toLowerCase() + "_data.json");
    }

    public JSONObject setBillingData(String dataSet)  {
        if(countryPar.equals("COLOMBIA") &&
                dataSet.equals("local_Billing_sucursales") && method.contains("Trips.") &&
                (baseURL.contains("ccr.") || baseURL.contains("sucursales."))) {
            logger.info("Changing local billing info COLOMBIA channel/brand: " + baseURL);
            dataSet="local_Billing_sucursales_trips";
        }
        JSONObject billingData = JsonRead.getJsonDataObject(billingsList, dataSet, countryPar.toLowerCase() + "_data.json");
        return billingData;
    }

    //CONTACT DATA
    public void setContactList()  {
        contactList = JsonRead.getJsonDataObject(jsonDataObject, "contacts", countryPar.toLowerCase() + "_data.json");
    }

    public JSONObject setContactData(String dataSet)  {
        JSONObject contactData = JsonRead.getJsonDataObject(contactList, dataSet, countryPar.toLowerCase() + "_data.json");
        return contactData;
    }
}
