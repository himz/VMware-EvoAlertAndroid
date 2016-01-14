package com.vmware.evorack.evoalert;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.vmware.evorack.evoalert.model.AlertItem;
import com.vmware.evorack.evoalert.model.DummyContent;
import com.vmware.evorack.evoalert.fragments.TrendingFragment;

public class MainActivity extends AppCompatActivity implements TrendingFragment.OnTrendingFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Going to send data to Trending Frag", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            /*Code to refresh data in the fragment */
                TrendingFragment trendingFrag = (TrendingFragment)
                        getSupportFragmentManager().findFragmentById(R.id.trending_fragment);
                trendingFrag.updateItemList();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTrendingFragmentInteraction(AlertItem item) {
        Toast.makeText(MainActivity.this, "AlertItemClicked: " + item.toString(), Toast.LENGTH_SHORT).show();
        Intent myintent=new Intent(MainActivity.this, AlertDetail.class).putExtra("alertItemId",item.getId());
        startActivity(myintent);
    }

}
