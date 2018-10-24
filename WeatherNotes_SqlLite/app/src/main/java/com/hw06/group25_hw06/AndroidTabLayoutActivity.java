package com.hw06.group25_hw06;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AndroidTabLayoutActivity extends TabActivity {

    public static final String Weather_Obj = "wObj";
    public static Weather wObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getActionBar().setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_android_tab_layout);

        if (getIntent().getExtras() != null) {

            wObj = (Weather) getIntent().getSerializableExtra("Wobj");

        }

        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);

        TabSpec Hourly_tab = tabHost.newTabSpec("Hourly");
        TabSpec Forecast = tabHost.newTabSpec("Forecast");

        Intent forecast_intent = new Intent(AndroidTabLayoutActivity.this, ForecastDataActivity.class);
        forecast_intent.putExtra(Weather_Obj, wObj);

        Forecast.setIndicator("Forecast").setContent(forecast_intent);

        Intent hourly_intent = new Intent(AndroidTabLayoutActivity.this, HourlyDataActivity.class);
        hourly_intent.putExtra(Weather_Obj, wObj);
        Hourly_tab.setIndicator("Hourly Data").setContent(hourly_intent);

        tabHost.addTab(Hourly_tab);
        tabHost.addTab(Forecast);
    }
}
