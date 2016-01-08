package com.vmware.evorack.evoalert.network;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by pandeyh on 1/7/2016.
 */
public class RestHandler {

    public String getJsonData(String uri) {
        RestClient client = new RestClient(uri);  //Write your url here

        client.addHeader("content-type", "application/json"); // Here I am specifying that the key-value pairs are sent in the JSON format
        String response = "";
        try {
             response = client.executeGet(); // In case your server sends any response back, it will be saved in this response string.
            /* The response is string, which can be only one alert -- json object, or all -- jsron array*/
            JSONArray ja = new JSONArray(response);
            JSONObject jo = new JSONObject(response);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
