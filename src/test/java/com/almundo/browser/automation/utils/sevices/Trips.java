package com.almundo.browser.automation.utils.sevices;

import com.almundo.browser.automation.base.TestBaseSetup;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by leandro.efron on 27/6/2017.
 */
public class Trips extends TestBaseSetup {

    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    private static Apikeys apikeys = new Apikeys();
    private JSONObject jsonObject;
    private String url = null;
    private String id_token = null;
    private String externalUserId = null;
    private String apiKeyHeader = null;

    public Trips(String URL, String EXTERNAL_USER_ID, String ID_TOKEN) throws IOException, ParseException {
        url = URL;
        externalUserId = EXTERNAL_USER_ID;
        id_token = ID_TOKEN;
        apiKeyHeader = apikeys.getApiKey(baseURL);
    }

    private void getJsonObject() throws IOException, ParseException {
        jsonObject = new JSONObject(parseHttpResponse(getHttpResponse()));
    }

    private HttpResponse getHttpResponse() throws IOException {
        HttpResponse httpResponse = httpClient.execute(createHttpGetRequest());
        displayStatusCode(httpResponse.getStatusLine().toString());
        return httpResponse;
    }

    private HttpResponse getHttpDeleteResponse(String tripId) throws IOException {
        HttpResponse httpResponse = httpClient.execute(createHttpDeleteRequest(tripId));
        displayStatusCode(httpResponse.getStatusLine().toString());
        return httpResponse;
    }

    private HttpGet createHttpGetRequest() throws IOException {
        logger.info("Getting trip id list from user [" + externalUserId + "]");
        HttpGet httpGetRequest = new HttpGet(url + "/trips?user.externalUserId=" + externalUserId);
        httpGetRequest.setHeader("X-Apikey", apiKeyHeader);
        return httpGetRequest;
    }

    private HttpDelete createHttpDeleteRequest(String tripId) throws IOException {
        HttpDelete httpDeleteRequest = new HttpDelete(url + "/trips/" + tripId);
        httpDeleteRequest.setHeader("X-Apikey", apiKeyHeader);
        httpDeleteRequest.setHeader("Authorization", "Bearer " + id_token);
        return httpDeleteRequest;
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

    public void cleanUserTrips() {
        try {
            getJsonObject();
            removeUserTripList(getUserTripList("content"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public List<String> getUserTripList(String section) {
        JSONArray jsonArraySection = (JSONArray) jsonObject.get(section);
        List<String> userTripList = new ArrayList<>();

        for (int i = 0; i < jsonArraySection.size(); i++) {
            JSONObject jsonObjectSection = new JSONObject((Map) jsonArraySection.get(i));
            if(jsonObjectSection.get("status").toString().equals("STARTED")) {
                userTripList.add(jsonObjectSection.get("id").toString());
            }
        }
        return userTripList;
    }

    public void removeUserTripList(List <String> tripIdList) throws IOException {
        for (String tripId : tripIdList) {
            logger.info("Removing trip id [" + tripId + "]");
            getHttpDeleteResponse(tripId);
        }
    }

}
