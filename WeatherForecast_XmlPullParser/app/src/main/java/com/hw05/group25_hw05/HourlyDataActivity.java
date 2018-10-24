package com.hw05.group25_hw05;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class HourlyDataActivity extends AppCompatActivity {

    String hourly_url;
    Weather w;

    ListView Whourly_lv;
    TextView loc_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        getSupportActionBar().setTitle("HourlyData Activity");
        setContentView(R.layout.activity_hourly_data);

        loc_tv = (TextView) findViewById(R.id.hour_loc_value);

        if (getIntent().getExtras() != null) {
            hourly_url = getIntent().getExtras().getString(MainActivity.WeatherLocURL);
            w = (Weather) getIntent().getSerializableExtra("Wobj");
        }

        Log.d("Result", hourly_url);

        loc_tv.setText(w.getCity()+", "+w.getState());


        new GetWeatherAsync(HourlyDataActivity.this).execute(hourly_url);

    }

    public void WeatherForecastList(final ArrayList<Hourly_forecast> ahf) {

        if(ahf.size() > 0) {

            int max_temp = Integer.parseInt(ahf.get(0).getTemperature()), min_temp = Integer.parseInt(ahf.get(0).getTemperature());
            ;
            for (int i = 0; i < ahf.size(); i++) {

                if (max_temp < Integer.parseInt(ahf.get(i).getTemperature()))
                    max_temp = Integer.parseInt(ahf.get(i).getTemperature());

                if (min_temp > Integer.parseInt(ahf.get(i).getTemperature()))
                    min_temp = Integer.parseInt(ahf.get(i).getTemperature());
            }

            Log.d("Result", "min = " + min_temp);
            Log.d("Result", "max = " + max_temp);

            w.setMinTemp(min_temp);
            w.setMax_temp(max_temp);


            Whourly_lv = (ListView) findViewById(R.id.Whourly_listView);

            Whourly_lv.setBackgroundColor(Color.rgb(240, 240, 240));

            w.setHf(ahf);
            WeatherHourlyAdapter adapter = new WeatherHourlyAdapter(this, R.layout.row_item_layout, ahf);
            Whourly_lv.setAdapter(adapter);

            Whourly_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent in = new Intent(HourlyDataActivity.this, WeatherDetailActivity.class);

                    in.putExtra("HourlyArrayList", (ArrayList<Hourly_forecast>) ahf);
                    in.putExtra("position", position);
                    in.putExtra("WeatherObj", w);


                    startActivity(in);

                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No cities match your query", Toast.LENGTH_LONG).show();
            finish();
        }








    }
}
