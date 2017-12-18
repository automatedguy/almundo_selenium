package com.almundo.browser.automation.utils.sevices;

import com.almundo.browser.automation.base.TestBaseSetup;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Arm extends TestBaseSetup {

    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    private String url = null;
    private String apikey = null;
    private String resCode = null;
    private JSONObject jsonObject;

    public Arm(String URL, String apiKey, String reservationCode) throws IOException, ParseException {
        url = URL;
        apikey = apiKey;
        resCode = reservationCode;
        getJsonObject();
    }

    private void getJsonObject() throws IOException, ParseException {
        jsonObject = new JSONObject(parseHttpResponse(getHttpResponse()));
    }

    @SuppressWarnings("Duplicates")
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
        return httpResponse;
    }

    private HttpGet createHttpRequest() throws IOException {
        HttpGet httpGetRequest = new HttpGet(url);
        httpGetRequest.setHeader("X-Apikey", apikey);
        httpGetRequest.setHeader("Version", "v3");
        return httpGetRequest;
    }

}
