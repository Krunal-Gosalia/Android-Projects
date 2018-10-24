package com.hw06.group25_hw06;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class HourlyDataActivity extends AppCompatActivity {

    Weather wObj;

    ListView Whourly_lv;
    TextView loc_tv, tp_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hourly_data);

        if (getIntent().getExtras() != null) {

            wObj = (Weather) getIntent().getSerializableExtra(AndroidTabLayoutActivity.Weather_Obj);

        }


        String state = wObj.getState().toUpperCase();
        String city = wObj.getCity().replace(" ", "_");



        String hourly_url = "http://api.wunderground.com/api/"+MainActivity.api_key+"/hourly/q/"+state+"/"+city+".xml";



        loc_tv = (TextView) findViewById(R.id.hour_loc_value);

        loc_tv.setText(wObj.getCity()+", "+wObj.getState());

        new GetWeatherAsync(HourlyDataActivity.this).execute(hourly_url);

    }

    public void WeatherForecastList(final ArrayList<Hourly_forecast> ahf) {

        if(ahf.size() > 0) {

            int max_temp = Integer.parseInt(ahf.get(0).getTemperature()), min_temp = Integer.parseInt(ahf.get(0).getTemperature());
            for (int i = 0; i < ahf.size(); i++) {

                if (max_temp < Integer.parseInt(ahf.get(i).getTemperature()))
                    max_temp = Integer.parseInt(ahf.get(i).getTemperature());

                if (min_temp > Integer.parseInt(ahf.get(i).getTemperature()))
                    min_temp = Integer.parseInt(ahf.get(i).getTemperature());
            }

            wObj.setMinTemp(min_temp);
            wObj.setMax_temp(max_temp);


            Whourly_lv = (ListView) findViewById(R.id.Whourly_listView);

            Whourly_lv.setBackgroundColor(Color.rgb(240, 240, 240));

            wObj.setHf(ahf);
            WeatherHourlyAdapter adapter = new WeatherHourlyAdapter(this, R.layout.row_hourly_layout, ahf);
            Whourly_lv.setAdapter(adapter);

            Whourly_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent in = new Intent(HourlyDataActivity.this, WeatherDetailActivity.class);

                    in.putExtra("HourlyArrayList", (ArrayList<Hourly_forecast>) ahf);
                    in.putExtra("position", position);
                    in.putExtra("WeatherObj", wObj);


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
