package com.almundo.browser.automation.utils;

import com.jayway.jsonpath.JsonPath;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created by gabi on 20/04/17.
 */
public class ApikeyManager {

    private HttpClient httpClient = new DefaultHttpClient();
    private String apiKeyEndPoint = "http://abs.almundo.it:8080/abs/security/apikeys";
    public String apiKeyHeader;

    private HttpGet createHttpRequest(){
        HttpGet httpGetRequest = new HttpGet(apiKeyEndPoint);
        return httpGetRequest;
    }

    private HttpResponse getHttpResponse() {
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(createHttpRequest());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    private JSONArray parseHttpResponse() throws IOException, ParseException {
        Object json;
        JSONParser parser = new JSONParser();
        json = parser.parse(EntityUtils.toString(getHttpResponse().getEntity(), "UTF-8"));
        JSONArray jsonArray = (JSONArray) json;
        return jsonArray;
    }

    private JSONArray getApikeysArray(){
        JSONArray jsonArray = null;
        try {
            jsonArray = parseHttpResponse();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    private String getApiKey(JSONArray jsonArray){
        JSONObject jsonApikeySection;
        boolean apikeyFound = false;
        int index = -1;
        do{
            index++;
            jsonApikeySection = new JSONObject((Map) jsonArray.get(index));
            if(JsonPath.read(jsonApikeySection, "$.name").equals("ccr-corporate")){
                JsonPath.read(jsonApikeySection, "$.value");
                apikeyFound = true;}
        }while(!apikeyFound);
        return JsonPath.read(jsonApikeySection, "$.value");
    }

    private  void closeConnection(){
        httpClient.getConnectionManager().shutdown();
    }

    @Test
    public void checkApiKey(){
        apiKeyHeader = getApiKey(getApikeysArray());
        System.out.println("Your APIKEY: " + apiKeyHeader);
        closeConnection();
    }
}
