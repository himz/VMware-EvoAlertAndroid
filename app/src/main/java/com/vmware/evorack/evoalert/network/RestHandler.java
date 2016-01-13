package com.vmware.evorack.evoalert.network;

import com.vmware.vrack.common.event.Body;
import com.vmware.vrack.common.event.Event;
import com.vmware.vrack.common.event.Header;
import com.vmware.vrack.common.event.enums.EventCatalog;
import com.vmware.vrack.common.event.enums.EventCategory;
import com.vmware.vrack.common.event.enums.EventSeverity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
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


            /* Iterate through the */

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


}
