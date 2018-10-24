package com.firebase.firebase_contact;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Krunal on 17-04-2016.
 */
public class RowMessageAdapter extends ArrayAdapter<Message> {

    List<Message> mData;
    Context mContent;
    int mRes;
    String sender_name;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mRes, parent, false);
        }

        final Message msg = mData.get(position);

        ImageView delete_icon = (ImageView) convertView.findViewById(R.id.mrow_delete);

        String msgdatetxt = null;
        TextView name = (TextView) convertView.findViewById(R.id.mrow_name);
        TextView message_txt = (TextView) convertView.findViewById(R.id.mrow_message);
        TextView msg_date = (TextView) convertView.findViewById(R.id.mrow_date);

        name.setText(msg.getSender());
        message_txt.setText(msg.getMessage_text());

        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hh.mm.ss a");
        try {
            Date date = format.parse(msg.getTime_stamp());

            SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy HH:mm");
             msgdatetxt = format1.format(date);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        msg_date.setText(msgdatetxt);


        if(msg.getSender().equals(sender_name))
        {
            delete_icon.setEnabled(true);
            delete_icon.setImageResource(R.drawable.delete);
            delete_icon.setTag(msg);
            convertView.setBackgroundColor(Color.parseColor("#DCDCDC"));
        }
        else
        {
            delete_icon.setEnabled(false);
        }





        delete_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase ref = new Firebase("https://firstappkrunal.firebaseio.com/Messages");
                ref.child(msg.getPost_id()).removeValue();


            }
        });




        return convertView;
    }

    public RowMessageAdapter(Context context, int resource, List<Message> objects, String sender_name)
    {
        super(context, resource, objects);

        this.sender_name = sender_name;
        this.mContent = context;
        this.mData = objects;
        this.mRes = resource;

    }


}
