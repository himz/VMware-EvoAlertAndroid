package com.vmware.evorack.evoalert;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.vmware.evorack.evoalert.model.AlertItem;
import com.vmware.evorack.evoalert.utils.App;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class AlertDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alert_detail);
        String s = getIntent().getStringExtra("alertItemId");
        Integer alertItemId = getIntent().getIntExtra("alertItemId", 1);
        AlertItem ai = getAlertFromID(alertItemId);
        TextView txtAlertName = (TextView) findViewById(R.id.txtAlertName);
        TextView txtAlertDescription = (TextView) findViewById(R.id.txtAlertDescription);
        TextView txtAlertHostName = (TextView) findViewById(R.id.txtAlertHostName);
        TextView txtAlertLocation = (TextView) findViewById(R.id.txtAlertLocation);
        TextView txtAlertTime = (TextView) findViewById(R.id.txtAlertTime);
        if(ai.getAlertName() != null)
            txtAlertName.setText(ai.getAlertName());
        if(ai.getDetails() != null)
            txtAlertDescription.setText(ai.getDetails());
        if(ai.getLocation() != null)
            txtAlertHostName.setText(ai.getLocation());
        if(ai.getLocation() != null)
            txtAlertLocation.setText(ai.getLocation());
        Timestamp ts = ai.getTime();
        Date date = new Date(ts.getTime());
        txtAlertTime.setText(date.toString());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed in the action bar.
                // Create a simple intent that starts the hierarchical parent activity and
                // use NavUtils in the Support Package to ensure proper handling of Up.
                Intent upIntent = new Intent(this, MainActivity.class);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is not part of the application's task, so create a new task
                    // with a synthesized back stack.
                    TaskStackBuilder.from(this)
                            // If there are ancestor activities, they should be added here.
                            .addNextIntent(upIntent)
                            .startActivities();
                    finish();
                } else {
                    // This activity is part of the application's task, so simply
                    // navigate up to the hierarchical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public AlertItem getAlertFromID(Integer alertItemId) {
        for(AlertItem a1: App.globalAlertItemList) {
            if(alertItemId == a1.getUniqueId())
                return a1;
        }
        return null;
    }
}
