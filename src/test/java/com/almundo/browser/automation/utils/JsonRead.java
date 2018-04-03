package com.almundo.browser.automation.utils;

import com.almundo.browser.automation.base.TestBaseSetup;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by leandro.efron on 23/11/2016.
 */
public class JsonRead extends TestBaseSetup {

    public static JSONObject getJsonFile(String jsonFileName) {

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            Object data = parser.parse(new FileReader(Constants.DATA_PATH + jsonFileName));
            logger.info("Getting JSON file: [" + jsonFileName + "]");
            jsonObject = (JSONObject) data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("Error reading: [" + jsonFileName + "]");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject getJsonDataObject (JSONObject jsonDataObject, String value, String jsonFileName) {
        JSONObject jsonObject = null;
        try {
            logger.info("Getting JSON data object/value: [" + jsonFileName + "/" + value + "]");
            jsonObject = (JSONObject) jsonDataObject.get(value);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error reading Object/Value: [" + value + "]");
        }
        return jsonObject;
    }
}