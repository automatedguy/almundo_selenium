package com.almundo.browser.automation.data;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.almundo.browser.automation.utils.JsonRead;
import org.json.simple.JSONObject;

/**
 * Created by leandro.efron on 10/2/2017.
 */
public class DataManagement extends TestBaseSetup {

    private JSONObject vueloDataTripList = null;
    private JSONObject vueloDataTripItinerary = null;

    private JSONObject hotelesDataTripList = null;
    private JSONObject hotelDataTripItinerary = null;

    public JSONObject vueloHotelDataTripList = null;
    public JSONObject vueloHotelDataTripItinerary = null;

    public JSONObject autosDataTripList = null;
    public JSONObject autosDataTripItinerary = null;

    public String originAuto;
    public String originFull;
    public String destinationAuto;
    public String destinationFull;
    public int startDate;
    public int endDate;
    public int adults;
    public int childs;
    public int rooms;
    public String childAgeRange;
    public String flightClass;
    public String pickUpTime;
    public String dropOffTime;
    public String ageRange;

    //VUELOS
    public void getVuelosDataTripList() {
        vueloDataTripList = JsonRead.getJsonDataObject(jsonDataObject, "vuelos", countryPar.toLowerCase() + "_data.json");
    }

    public void getVuelosDataTripItinerary(String dataSet) {
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

    //HOTELES
    public void getHotelesDataTripList() {
        hotelesDataTripList = JsonRead.getJsonDataObject(jsonDataObject, "hoteles", countryPar.toLowerCase() + "_data.json");
    }

    public void getHotelDataTripItinerary(String dataSet) {
        hotelDataTripItinerary = JsonRead.getJsonDataObject(hotelesDataTripList, dataSet, countryPar.toLowerCase() + "_data.json");

        destinationAuto = hotelDataTripItinerary.get("destinationAuto").toString();
        destinationFull = hotelDataTripItinerary.get("destinationFull").toString();

        startDate = Integer.parseInt(hotelDataTripItinerary.get("startDate").toString());
        endDate = Integer.parseInt(hotelDataTripItinerary.get("endDate").toString());

        adults = Integer.parseInt(hotelDataTripItinerary.get("adults").toString());
        childs = Integer.parseInt(hotelDataTripItinerary.get("childs").toString());

        rooms = Integer.parseInt(hotelDataTripItinerary.get("rooms").toString());
    }

    //VUELO+HOTEL
    public void getVueloHotelDataTripList() {
        vueloHotelDataTripList = JsonRead.getJsonDataObject(jsonDataObject, "vueloHotel", countryPar.toLowerCase() + "_data.json");
    }

    public void getVueloHotelDataTripItinerary(String dataSet) {
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

    //AUTOS
    public void getAutosDataTripList() {
        autosDataTripList = JsonRead.getJsonDataObject(jsonDataObject, "autos", countryPar.toLowerCase() + "_data.json");
    }

    public void getAutosDataTripItinerary(String dataSet) {
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
}
