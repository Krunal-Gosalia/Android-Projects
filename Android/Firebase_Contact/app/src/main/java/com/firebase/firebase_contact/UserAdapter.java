package com.firebase.firebase_contact;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.List;

/**
 * Created by Krunal on 16-04-2016.
 */
public class UserAdapter extends ArrayAdapter<User> {
    List<User> mData;
    Context mContent;
    int mRes;
    User sender;
    boolean flag = false;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mRes, parent, false);
        }
        final User user = mData.get(position);

        ImageView profile_pic = (ImageView) convertView.findViewById(R.id.RowProfile_imageView);


        TextView uname = (TextView) convertView.findViewById(R.id.RowName);
        uname.setText(user.getFullName());

        final ImageView read_status = (ImageView) convertView.findViewById(R.id.RowReadStatus_imageView);

        ImageView call_icon = (ImageView) convertView.findViewById(R.id.RowCall_imageView);


        call_icon.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {


                   Intent callIntent = new Intent(Intent.ACTION_CALL);
                   callIntent.setData(Uri.parse("tel:"+user.getPhoneNumber()));
                   callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   mContent.startActivity(callIntent);








            }
        });








        Firebase ref = new Firebase("https://firstappkrunal.firebaseio.com/");

        Firebase message_ref = ref.child("Messages");

        message_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //System.out.println("There are " + snapshot.getChildrenCount() + " users");
                for (DataSnapshot msgSnapshot : snapshot.getChildren()) {

                    Message msgfact = msgSnapshot.getValue(Message.class);
                    if (msgfact.getSender().equals(user.getFullName()) && msgfact.getReceiver().equals(sender.getFullName()) && msgfact.getMessage_read().equals("false")) {;
                        System.out.println(user.getFullName());
                        flag = true;

                    }

                }
                setFlag(flag, read_status);

                flag = false;

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("Read failed: " + firebaseError.getMessage());
            }
        });


        message_ref.addChildEventListener(new ChildEventListener() {



            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {



                Message msgfact = dataSnapshot.getValue(Message.class);






            }



            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                /*
                Message msgfact = dataSnapshot.getValue(Message.class);

                if (msgfact.getSender().equals(user.getFullName()) && msgfact.getReceiver().equals(sender.getFullName()) && msgfact.getMessage_read().equals("false")) {


                    setFlag(true, read_status);

                }
                */



            }



            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }


        });







        return convertView;
    }

    public void setFlag(boolean flag, ImageView read_status)
    {
        if(flag == true) {
            read_status.setImageResource(R.drawable.redbubble);
        }
        else
            read_status.setImageResource(R.drawable.bluebubble);


        flag = false;
    }


    public UserAdapter(Context context, int resource, List<User> objects, User sender)
    {
        super(context, resource, objects);

        this.sender = sender;
        //System.out.println("Constructor "+sender.getFullName());
        this.mContent = context;
        this.mData = objects;
        this.mRes = resource;

    }
}
