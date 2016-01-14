package com.vmware.evorack.evoalert.utils;

import com.vmware.evorack.evoalert.model.AlertItem;
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
import java.util.Date;
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
            for (int i = 0; i < ja.length(); i++) {
                JSONObject messages = ja.getJSONObject(i);
                //JSONArray m1 = messages.getJSONArray("messages");
                e = getEventFromTheJsonObject(messages);
                if (e != null)
                    eventList.add(e);
            }
        } catch (JSONException e) {
            System.out.println("Error creating EventList from the response");
        }
        return eventList;
    }

    public List<AlertItem> getAlertItemList(String alertResponseJson) {
        List<AlertItem> alertItemList = new ArrayList<AlertItem>();
        JSONArray ja = null;
        try {
            ja = new JSONArray(alertResponseJson);
            AlertItem alertItem;
            for (int i = 0; i < ja.length(); i++) {
                JSONObject messages = ja.getJSONObject(i);
                //JSONArray m1 = messages.getJSONArray("messages");
                alertItem = getAlertItemFromTheJsonObject(messages);
                if (alertItem != null)
                    alertItemList.add(alertItem);
            }
        } catch (JSONException e) {
            System.out.println("Error creating EventList from the response");
        }
        return alertItemList;
    }

    /**
     * Overloaded function to create alertitem list from the global jsonobject list
     *
     * @return
     */
    public static List<AlertItem> getAlertItemList() {
        List<AlertItem> alertItemList = new ArrayList<AlertItem>();
        JSONArray ja = null;
        try {
            AlertItem alertItem;
            for (JSONObject messages:App.globalAlertJsonObjectList) {
                alertItem = getAlertItemFromTheJsonObject(messages);
                if (alertItem != null) {
                    /* This is the only place when the item is being added to the list */
                    /* add uniqueID to the current item */
                    alertItem.setUniqueId(App.alertUniqueIdCounter++);
                    alertItemList.add(alertItem);
                }
            }
        } catch (Exception e) {
            System.out.println("Error creating EventList from the response");
        }
        return alertItemList;
    }

    private static AlertItem getAlertItemFromTheJsonObject(JSONObject messages) {
        AlertItem ai = new AlertItem();
        try {
            JSONArray ja = messages.getJSONArray("messages");
            JSONObject eventJsonObject = ja.getJSONObject(0);
            JSONArray fields = eventJsonObject.getJSONArray("fields");
            ai.setDetails(eventJsonObject.getString("text"));
            String contentString;
            String nameString;
            List<EventCategory> ecl = new ArrayList<EventCategory>();
            for (int i = 0; i < fields.length(); i++) {
                JSONObject tempJsonObject = fields.getJSONObject(i);
                contentString = tempJsonObject.getString("content");
                nameString = tempJsonObject.getString("name");
                if (nameString.equals("eventName")) {
                    ai.setAlertName(contentString);
                } else if (nameString.equals("eventTimeStamp")) {

                    ai.setContent(contentString);
                    System.out.println(ai.getContent());
                    long longdate = Long.parseLong(ai.getContent());
                    Date date = new Date(longdate);
                    //Date date = new Date(contentString);
                    Timestamp t = new Timestamp(date.getTime());
                    ai.setTime(t);
                } else if (nameString.equals("hostname")) {
                    ai.setLocation(contentString);
                }
            }
        } catch (Exception e) {
            /* Do Nothing */
            System.out.println("Error in parsing the alertitem");
            return null;
        }
        return ai;
    }


    /**
     * Input the json arary for the particular message, and sends out the complete event out of it.
     *
     * @param messages
     * @return
     */
    private Event getEventFromTheJsonObject(JSONObject messages) {
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
