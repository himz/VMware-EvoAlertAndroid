package com.vmware.evorack.evoalert.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.vmware.evorack.evoalert.model.AlertItem;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to store all the global variables.
 * Mainly handling database connections for now
 * As a general practice, start moving shared state among actiities to this
 * class.
 *
 * @author himanshu
 */


public class App extends Application {
    // Debugging switch
    public static final boolean APPDEBUG = false;

    // Debugging tag for the application
    public static final String APPTAG = "EvoAlert";


	/* List of global variables, to be shared across activities */
    private static SharedPreferences preferences;
    public static String  globalAlertJsonString;
    public static List<JSONObject> globalAlertJsonObjectList;
    public static List<AlertItem> globalAlertItemList;
    public static int alertUniqueIdCounter;

	@Override
	public void onCreate() {
		super.onCreate();
		Context ctx = getApplicationContext();
        globalAlertJsonObjectList = new ArrayList<JSONObject>();
        globalAlertItemList = new ArrayList<AlertItem>();
        alertUniqueIdCounter = 0;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
}