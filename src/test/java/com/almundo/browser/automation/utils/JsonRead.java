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


    public static JSONObject getJsonFile(String dataFileName) {

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {

            Object data = parser.parse(new FileReader(Constants.RESOURCES_PATH + dataFileName));
            jsonObject = (JSONObject) data;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
