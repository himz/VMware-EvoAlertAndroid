package com.vmware.evorack.evoalert.utils;

import com.vmware.vrack.common.event.Body;
import com.vmware.vrack.common.event.Event;
import com.vmware.vrack.common.event.Header;
import com.vmware.vrack.common.event.enums.EventCatalog;
import com.vmware.vrack.common.event.enums.EventCategory;
import com.vmware.vrack.common.event.enums.EventSeverity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by pandeyh on 1/12/2016.
 */
public class AlertHelperFunctions {

    /**
     * Input the response json string from the sts server, and gives back the event list out
     * of the function.
     *
     * @param alertResponseJson
     * @return
     */
    private List<Event> getEventObjectList(String alertResponseJson) {
        List<Event> eventList = new ArrayList<Event>();
        JSONArray ja = null;
        try {
            ja = new JSONArray(alertResponseJson);
            Event e;
            for(int i = 0; i < ja.length(); i++) {
                JSONObject messages = ja.getJSONObject(i);
                //JSONArray m1 = messages.getJSONArray("messages");
                e = getEventFromTheJsonArray(messages);
                if(e != null)
                    eventList.add(e);
            }
        } catch (JSONException e) {
            System.out.println("Error creating EventList from the response");
        }
        return eventList;
    }

    private List<Event> getAlertItemList(String alertResponseJson) {
        List<Event> eventList = new ArrayList<Event>();
        JSONArray ja = null;
        try {
            ja = new JSONArray(alertResponseJson);
            Event e;
            for(int i = 0; i < ja.length(); i++) {
                JSONObject messages = ja.getJSONObject(i);
                //JSONArray m1 = messages.getJSONArray("messages");
                e = getEventFromTheJsonArray(messages);
                if(e != null)
                    eventList.add(e);
            }
        } catch (JSONException e) {
            System.out.println("Error creating EventList from the response");
        }
        return eventList;
    }

    /**
     * Input the json arary for the particular message, and sends out the complete event out of it.
     *
     * @param messages
     * @return
     */
    private Event getEventFromTheJsonArray(JSONObject messages) {
        Header header = new Header();
        Body body = new Body();
        Event event = new Event();
        try {
            JSONArray ja = messages.getJSONArray("messages");
            JSONObject eventJsonObject = ja.getJSONObject(0);
            JSONArray fields = eventJsonObject.getJSONArray("fields");
            body.setDescription(eventJsonObject.getString("text"));
            String contentString;
            String nameString;
            List<EventCategory> ecl = new ArrayList<EventCategory>();
            for (int i = 0; i < fields.length(); i++) {
                JSONObject tempJsonObject = fields.getJSONObject(i);
                contentString = tempJsonObject.getString("content");
                nameString = tempJsonObject.getString("name");
                if (nameString.equals("RACK_NAME")) {
                } else if (nameString.equals("value")) {
                    Map<String, String> dataHashMap = new HashMap<String, String>();
                    dataHashMap.put("value", contentString);
                    body.setData(dataHashMap);
                } else if (nameString.equals("CPU")) {
                } else if (nameString.equals("eventName")) {
                    header.setEventName(EventCatalog.getByStr(contentString));
                } else if (nameString.equals("severity")) {
                    header.setSeverity(EventSeverity.valueOf(contentString));
                } else if (nameString.equals("RACK")) {
                } else if (nameString.equals("eventTimeStamp")) {
                    header.setEventTimeStamp(Timestamp.valueOf(contentString));
                } else if (nameString.equals("SERVER")) {
                } else if (nameString.equals("eventCategory")) {
                    StringTokenizer st2 = new StringTokenizer(contentString, ",");
                    while (st2.hasMoreElements()) {
                        ecl.add(EventCategory.getByStr((String) st2.nextElement()));
                    }
                    header.setEventCategoryList(ecl);
                } else if (nameString.equals("version")) {
                    header.setVersion(tempJsonObject.getString("content"));
                }
            }
        } catch (Exception e) {
            /* Do Nothing */
            return null;
        }
        event.setBody(body);
        event.setHeader(header);
        return event;
    }

}
