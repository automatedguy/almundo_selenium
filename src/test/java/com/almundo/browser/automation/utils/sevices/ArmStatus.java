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

import static com.almundo.browser.automation.utils.Constants.Results.FAILED;
import static com.almundo.browser.automation.utils.PageUtils.printStarsSeparator;
import static com.almundo.browser.automation.utils.PageUtils.waitImplicitly;

public class ArmStatus extends TestBaseSetup {

    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    private String abacoURL = null;
    private String apikey = null;
    private String resCode = null;
    private JSONObject jsonObject;
    Apikeys apikeys = new Apikeys();

    private int attempt = 0;
    private final int MAX_ATTEMPTS = 10;
    private final int INTERVAL = 7000;

    public ArmStatus(String reservationCode) {
        abacoURL = "http://apist.almundo.it:8080/api/arm/reservations";
        apikey = apikeys.getApiKey(baseURL);
        resCode = reservationCode;
    }

    private boolean isFinishOk(String stage, String status){
        printStarsSeparator();
        logger.info("Current reservation state: [" + stage + "]");
        logger.info("Current reservation status: [" + status + "]");
        printStarsSeparator();
        if(stage.equals("FINISH") && status.equals("OK")){
            logger.info("Reservation Status is OK!");
            printStarsSeparator();
            return true;
        }
        else{
            logger.info("Reservation is not [FINISHED - OK] yet...");
            printStarsSeparator();
            waitImplicitly(INTERVAL);
            return false;
        }
    }

    public boolean isReservationOk(){
        boolean finishOk = false;
        printStarsSeparator();
        logger.info("Max Attempts is: [" + MAX_ATTEMPTS + "]");
        logger.info("Time interval between attempt is: [" + INTERVAL + "] milliseconds.");
        printStarsSeparator();
        while((attempt <= MAX_ATTEMPTS) && !finishOk){
            try {
                attempt = ++attempt;
                jsonObject = getJsonObject();
                logger.info("Attempt number: [" + attempt + "]");
                finishOk = isFinishOk((String) jsonObject.get("stage") , (String) jsonObject.get("status"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(!finishOk){
            logger.info("Max attempt [" + MAX_ATTEMPTS + "] has been reached, failing the test :(");
            setResultSauceLabs(FAILED);
        }
        return finishOk;
    }

    private JSONObject getJsonObject() throws IOException, ParseException {
        return new JSONObject(parseHttpResponse(getHttpResponse()));
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
        HttpGet httpGetRequest = new HttpGet(abacoURL + "/"+ resCode);
        httpGetRequest.setHeader("X-Apikey", apikey);
        httpGetRequest.setHeader("Version", "v3");
        return httpGetRequest;
    }
}
