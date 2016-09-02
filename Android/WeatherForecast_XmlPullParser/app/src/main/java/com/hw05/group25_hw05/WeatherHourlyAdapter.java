package com.hw05.group25_hw05;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Krunal on 29-02-2016.
 */
public class WeatherHourlyAdapter extends ArrayAdapter<Hourly_forecast> {

    List<Hourly_forecast> mData;
    Context mContent;
    int mRes;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mRes, parent, false);
        }

        Hourly_forecast hf = mData.get(position);

        TextView time_tv = (TextView) convertView.findViewById(R.id.time_tview);
        time_tv.setText(hf.getTime());

        TextView climate_type = (TextView) convertView.findViewById(R.id.climate_tview);
        climate_type.setText(hf.getClimateType());

        TextView temperature = (TextView) convertView.findViewById(R.id.temp_tview);
        temperature.setText(hf.getTemperature()+ "\u2109"); //temperatureValue.setText((result) + " \u2109");

        ImageView iv = (ImageView) convertView.findViewById(R.id.climate_icon);

        Picasso.with(mContent).load(hf.getIconUrl()).resize(40,40).into(iv);











        return convertView;
    }

    public WeatherHourlyAdapter(Context context, int resource, List<Hourly_forecast> objects) {
        super(context, resource, objects);

        this.mContent = context;
        this.mData = objects;
        this.mRes = resource;

    }
}
