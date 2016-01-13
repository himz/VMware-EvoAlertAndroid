package com.vmware.evorack.evoalert.model;

/**
 * Created by pandeyh on 1/12/2016.
 */
public class AlertItem {
    public final String id;
    public final String content;
    public final String details;

    public AlertItem(String id, String content, String details) {
        this.id = id;
        this.content = content;
        this.details = details;
    }

    @Override
    public String toString() {
        return content;
    }
}

