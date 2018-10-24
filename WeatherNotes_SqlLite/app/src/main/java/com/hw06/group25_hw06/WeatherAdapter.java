package com.hw06.group25_hw06;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krunal on 20-03-2016.
 */
public class WeatherAdapter extends ArrayAdapter<Weather>
{

    ArrayList<Weather> mData;
    Context mContent;
    int mRes;

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mRes, parent, false);
        }

        Weather w = mData.get(position);

        TextView city1 = (TextView) convertView.findViewById(R.id.city_item);
        TextView temp1 = (TextView) convertView.findViewById(R.id.temp_item);


        city1.setText(w.getCity().toString());
        temp1.setText(w.getCurrentTemp()+" \u2109");













        return convertView;
    }

    public WeatherAdapter(Context context, int resource, ArrayList<Weather> objects)
    {
        super(context, resource, objects);

        this.mContent = context;
        this.mData = objects;
        this.mRes = resource;

    }

}
