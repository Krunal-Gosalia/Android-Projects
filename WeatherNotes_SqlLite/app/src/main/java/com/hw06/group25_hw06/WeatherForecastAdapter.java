package com.hw06.group25_hw06;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Krunal on 21-03-2016.
 */
public class WeatherForecastAdapter extends ArrayAdapter<Day_Forecast> {
    List<Day_Forecast> mData;
    Context mContent;
    int mRes;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mRes, parent, false);
        }

        Day_Forecast df = mData.get(position);

        TextView fdate, fcloud, fMax, fMin;
        ImageView fiv;

        fdate = (TextView) convertView.findViewById(R.id.fdate_tview);
        fcloud = (TextView) convertView.findViewById(R.id.fclimate_tview);
        fMax = (TextView) convertView.findViewById(R.id.fMaxTemp_tview);
        fMin = (TextView) convertView.findViewById(R.id.fMinTemp_tview);
        fiv = (ImageView) convertView.findViewById(R.id.fclimate_icon);


        fcloud.setText(df.getClouds());
        fMax.setText(df.getHightemp() +" \u2109");
        fMin.setText(df.getLowTemp()+" \u2109");

        fdate.setText(df.getDate());



        Picasso.with(mContent).load(df.getIconUrl()).resize(40,40).into(fiv);











        return convertView;
    }

    public WeatherForecastAdapter(Context context, int resource, List<Day_Forecast> objects) {
        super(context, resource, objects);

        this.mContent = context;
        this.mData = objects;
        this.mRes = resource;

    }
}
