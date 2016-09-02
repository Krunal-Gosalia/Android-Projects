package com.hw05.group25_hw05;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WeatherDetailActivity extends AppCompatActivity {

    ArrayList<Hourly_forecast> ahf_details = new ArrayList<Hourly_forecast>();
    int position;
    String city, state;
    Weather w;
    Hourly_forecast hf;
    int min_tmp, max_tmp;

    TextView loc, temp, climate, max_temp, min_temp, feels, humidity, dew, pressure, clouds, winds;
    ImageButton next, prev;
    ImageView Wicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        getSupportActionBar().setTitle("Details Activity");
        setContentView(R.layout.activity_weather_detail);

        loc = (TextView) findViewById(R.id.details_location_value_tview);
        temp = (TextView) findViewById(R.id.details_temp_value_tview);
        max_temp = (TextView) findViewById(R.id.max_temp_value);
        min_temp = (TextView) findViewById(R.id.min_temp_value);
        climate = (TextView) findViewById(R.id.climate_type_value);
        feels = (TextView) findViewById(R.id.feels_value);
        humidity = (TextView) findViewById(R.id.humidity_value);
        dew = (TextView) findViewById(R.id.dew_value);
        pressure = (TextView) findViewById(R.id.pressure_value);
        clouds = (TextView) findViewById(R.id.clouds_value);
        winds = (TextView) findViewById(R.id.winds_value);
        Wicon = (ImageView) findViewById(R.id.details_WIcon_iview);
        prev = (ImageButton) findViewById(R.id.prev_button);
        next = (ImageButton) findViewById(R.id.next_button);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position < ahf_details.size()-1) {
                    position = position + 1;
                    retrieve(position);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Last on the list", Toast.LENGTH_SHORT).show();
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position > 0) {
                    position = position - 1;
                    retrieve(position);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "First on the list", Toast.LENGTH_SHORT).show();
                }

            }
        });




        if (getIntent().getExtras() != null) {

            ahf_details = (ArrayList<Hourly_forecast>) getIntent().getSerializableExtra("HourlyArrayList");
            position = getIntent().getExtras().getInt("position", 0);
            w = (Weather) getIntent().getSerializableExtra("WeatherObj");

            city = w.getCity();
            state = w.getState();
            min_tmp = w.getMinTemp();
            max_tmp = w.getMax_temp();

            retrieve(position);

        }

    }

    private void retrieve(int pos) {
        hf = ahf_details.get(pos);
        //Log.d("Result", hf.getClimateType());

        loc.setText(city + ", " + state + " (" + hf.getTime() + ")");
        temp.setText(hf.getTemperature() + "\u2109");
        climate.setText(hf.getClimateType());

        feels.setText(hf.getFeelsLike()+" Fahrenheit");
        humidity.setText(hf.getHumidity() + " %");
        dew.setText(hf.getDewpoint()+" Fahrenheit");
        pressure.setText(hf.getPressure()+ " hPa");
        clouds.setText(hf.getClouds());

        String windDir[] = hf.getWindDirection().split(" ");

        String direction = "";
        for(int i=0; i < windDir[0].length(); i++)
        {

            if(i == 1)
                direction = direction + "-";

            switch (windDir[0].charAt(i))
            {
                case 'N':
                    direction = direction + "North";
                    break;

                case 'S':
                    direction = direction + "South";
                    break;

                case 'E':
                    direction = direction + "East";
                    break;

                case 'W':
                    direction = direction + "West";


            }

        }

        winds.setText(hf.getWindSpeed()+" mph, "+direction);
        min_temp.setText(min_tmp+" Fahrenheit");
        max_temp.setText(max_tmp+" Fahrenheit");

        Picasso.with(this).load(hf.getIconUrl()).resize(60,60).into(Wicon);
    }
}
