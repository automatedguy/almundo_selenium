package com.almundo.browser.automation.utils.sevices;

import com.almundo.browser.automation.base.TestBaseSetup;
import com.jayway.jsonpath.JsonPath;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
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
public class InputDefinitions extends TestBaseSetup{

    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    private String url = null;
    private JSONObject jsonObject;
    public static Apikeys apikeys = new Apikeys();

    public InputDefinitions(String URL) throws IOException, ParseException {
        url = URL;
        getJsonObject();
    }

    private void getJsonObject() throws IOException, ParseException {
        jsonObject = new JSONObject(parseHttpResponse(getHttpResponse()));
    }

    private JSONObject parseHttpResponse(HttpResponse httpResponse) throws IOException, ParseException {
        Object json;
        JSONObject jsonObject;
        JSONParser parser = new JSONParser();
        json = parser.parse(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
        jsonObject = (JSONObject) json;
        return jsonObject;
    }

    private HttpResponse getHttpResponse() throws IOException {
        HttpResponse httpResponse = httpClient.execute(createHttpRequest());
        displayStatusCode(httpResponse.getStatusLine().toString());
        return httpResponse;
    }

    private HttpGet createHttpRequest() throws IOException {
        HttpGet httpGetRequest = new HttpGet(url);
        httpGetRequest.setHeader("X-Apikey", apikeys.getApiKey(baseURL));
        httpGetRequest.setHeader("Version", "v3");
        return httpGetRequest;
    }

    private void displayStatusCode(String statusCode) {
        logger.info("HTTP Response Status Code: " + statusCode);
    }

    public boolean isRequired(String section, String field, int index) {
        boolean isRequired = false;
        JSONArray jsonArraySection = (JSONArray) jsonObject.get(section);
        JSONObject jsonObjectSection = new JSONObject((Map) jsonArraySection.get(index));
        if(JsonPath.read(jsonObjectSection, field) != null &&
                jsonObjectSection.get(field).toString().contains("required")){isRequired = true;}
        return isRequired;
    }

    public boolean isRequired(String section, String subSection, String field) {
        boolean isRequired = false;
        JSONArray jsonArraySection = (JSONArray) jsonObject.get(section);
        JSONObject jsonObjectSection = new JSONObject ((Map)jsonArraySection.get(0));
        JSONObject jsonObjectSubSection = new JSONObject((Map) jsonObjectSection.get(subSection));
        if(JsonPath.read(jsonObjectSubSection, field) != null &&
                jsonObjectSubSection.get(field).toString().contains("required")){isRequired = true;}
        return isRequired;
    }

    public boolean isRequired(String section, String subSection, String field, int index) {
        boolean isRequired = false;
        JSONArray jsonArraySection = (JSONArray) jsonObject.get(section);
        JSONObject jsonObjectSection = new JSONObject ((Map)jsonArraySection.get(0));
        JSONArray jsonArraySubSection = (JSONArray) jsonObjectSection.get(subSection);
        JSONObject jsonObjectSubSection = new JSONObject((Map) jsonArraySubSection.get(index));

        if(JsonPath.read(jsonObjectSubSection, field) != null &&
                jsonObjectSubSection.get(field).toString().contains("required")) {
            isRequired = true;
        }
        return isRequired;
    }

    public boolean isRequired(String section) {
        boolean isRequired = false;
        if(JsonPath.read(jsonObject, section) != null && jsonObject.get(section).toString().contains("required")){
            isRequired = true;
        }
        return isRequired;
    }
}