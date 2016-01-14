package com.vmware.evorack.evoalert.model;

import java.sql.Timestamp;

/**
 * Created by pandeyh on 1/12/2016.
 */
public class AlertItem {
    String id;
    String content;
    String details;
    String alertName;
    Timestamp time;
    String location; //Start with storing hostname here.
    int uniqueId;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AlertItem(){
        super();
    }
    public AlertItem(String id, String content, String details) {
        this.id = id;
        this.content = content;
        this.details = details;
    }

    public AlertItem(String id, String alertName, Timestamp time, String location) {
        this.id = id;
        this.alertName = alertName;
        this.time = time;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getAlertName() {
        return alertName;
    }

    public void setAlertName(String alertName) {
        this.alertName = alertName;
        this.id = alertName;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public String toString() {
        return content;
    }
}

