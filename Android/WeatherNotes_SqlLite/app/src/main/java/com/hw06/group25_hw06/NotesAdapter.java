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

import java.util.List;

/**
 * Created by Krunal on 29-02-2016.
 */
public class NotesAdapter extends ArrayAdapter<Note> {

    List<Note> mData;
    Context mContent;
    int mRes;



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mRes, parent, false);
        }

        Note n = mData.get(position);

        TextView title, ndate;

        title = (TextView) convertView.findViewById(R.id.snote_title);
        ndate = (TextView) convertView.findViewById(R.id.snote_date);

        Log.d("Result_note", n.getNote());

        title.setText(n.getNote());
        ndate.setText(n.getDate());
















        return convertView;
    }

    public NotesAdapter(Context context, int resource, List<Note> objects) {
        super(context, resource, objects);

        this.mContent = context;
        this.mData = objects;
        this.mRes = resource;

    }
}
