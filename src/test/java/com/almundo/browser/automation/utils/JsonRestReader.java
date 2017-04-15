package com.almundo.browser.automation.utils;

import com.almundo.browser.automation.base.TestBaseSetup;
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

import java.io.IOException;
import java.util.Map;

/**
 * Created by gabi on 13/04/17.
 */
public class JsonRestReader extends TestBaseSetup{

    private HttpClient httpClient = new DefaultHttpClient();
    private String url = null;
    private JSONObject jsonObject;

    public JsonRestReader(String URL) throws IOException, ParseException {
        url = URL;
        createHttpRequest();
        getJsonObject();
        closeConnection();
    }

    private HttpGet createHttpRequest() throws IOException {
        HttpGet httpGetRequest = new HttpGet(url);
        httpGetRequest.setHeader("X-Apikey", "581262206dae13c5c47b9af9");
        httpGetRequest.setHeader("Version", "v3");
        return httpGetRequest;
    }

    private void closeConnection() {
        httpClient.getConnectionManager().shutdown();
    }

    private HttpResponse getHttpResponse() throws IOException {
        HttpResponse httpResponse = httpClient.execute(createHttpRequest());
        displayStatusCode(httpResponse.getStatusLine().toString());
        return httpResponse;
    }

    private void displayStatusCode(String statusCode) {
        logger.info("HTTP Response Status Code: " + statusCode);
    }

    private JSONObject parseHttpResponse(HttpResponse httpResponse) throws IOException, ParseException {
        Object json;
        JSONObject jsonObject;
        JSONParser parser = new JSONParser();
        json = parser.parse(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
        jsonObject = (JSONObject) json;
        return jsonObject;
    }

    private void getJsonObject() throws IOException, ParseException {
        jsonObject = new JSONObject(parseHttpResponse(getHttpResponse()));
    }

    public boolean isRequired(String section, String field, int index) throws IOException, ParseException {
        boolean isRequired;
        JSONArray jsonArraySection = (JSONArray) jsonObject.get(section);
        JSONObject jsonObjectSection = new JSONObject((Map) jsonArraySection.get(index));
        if(JsonPath.read(jsonObjectSection, field) != null &&
                jsonObjectSection.get(field).toString().contains("required")){
            isRequired = JsonPath.read(jsonObjectSection, "$." + field + ".required");
        } else
            { isRequired = false;}
        return isRequired;
    }
}