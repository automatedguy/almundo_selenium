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

import static com.almundo.browser.automation.utils.Constants.APIKEY_URL;

/**
 * Created by gabi on 20/04/17.
 */
public class Apikeys extends TestBaseSetup {

    CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    private String getName(String currentUrl){
        String name = null;
        int mask = currentUrl.contains("ccr.") ? 1 : 0;
        mask |= currentUrl.contains("sucursales.") ? 2 : 0;
        mask |= currentUrl.contains("icbcstore.") ? 4 : 0;

        switch (mask) {
            case 1:
            case 1 | 2:
            case 1 | 4:
            case 1 | 2 | 4:
                name = "ccr";
                break;
            case 2:
            case 2 | 4:
                name = "retail";
                break;
            case 4:
                name = "icbc-store";
                break;
            default:
                name = "almundo-web";
        }
        return name;
    }

    private HttpGet createHttpRequest(){
        HttpGet httpGetRequest = new HttpGet(APIKEY_URL);
        return httpGetRequest;
    }

    private HttpResponse getHttpResponse() {
        HttpResponse httpResponse = null;
        try { httpResponse = httpClient.execute(createHttpRequest());
        } catch (IOException e) { e.printStackTrace();
        } return httpResponse;
    }

    private JSONArray parseHttpResponse() throws IOException, ParseException {
        Object json; JSONParser parser = new JSONParser();
        json = parser.parse(EntityUtils.toString(getHttpResponse().getEntity(), "UTF-8"));
        JSONArray jsonArray = (JSONArray) json;
        return jsonArray;
    }

    private JSONArray getApikeysList(){
        JSONArray jsonArray = null;
        try { jsonArray = parseHttpResponse();
        } catch (IOException e) { e.printStackTrace();
        } catch (ParseException e) { e.printStackTrace();
        } return jsonArray;
    }

    private void displayApikeyInfo(JSONObject jsonApikeySection){
        logger.info("Name: " + "[" + JsonPath.read(jsonApikeySection, "$.name") + "]");
        logger.info("Brand: " + "[" + JsonPath.read(jsonApikeySection, "$.channel") + "]");
        logger.info("Channel: " + "[" + JsonPath.read(jsonApikeySection, "$.brand") + "]");
        logger.info("Value (X-Apikey): " + "[" + JsonPath.read(jsonApikeySection, "$.value") + "]");
    }

    public String getApiKey(String currentUrl){
        logger.info("Getting Api Key info");
        JSONArray apikeyList = getApikeysList();
        JSONObject jsonApikeySection;
        boolean apikeyFound = false;
        int index = -1;
        String name =getName(currentUrl);
        do{ index++; jsonApikeySection = new JSONObject((Map) apikeyList.get(index));
            if(JsonPath.read(jsonApikeySection, "$.name").equals(name)){ apikeyFound = true;}
        }while(!apikeyFound);
        displayApikeyInfo(jsonApikeySection);
        return JsonPath.read(jsonApikeySection, "$.value");
    }

}