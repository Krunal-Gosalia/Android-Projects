package com.firebase.firebase_contact;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MessagesActivity extends AppCompatActivity {

    User user, sender;

    Button send_button;
    EditText message_text;
    ListView message_listview;

    Firebase ref, message_ref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        setContentView(R.layout.activity_messages);

        if(getIntent().getExtras()!=null)
        {
            user = (User) getIntent().getSerializableExtra("UserObj");
            sender =  (User) getIntent().getSerializableExtra("SenderUserObj");
        }

        getSupportActionBar().setTitle(user.getFullName());


        //System.out.println("To - " + user.getFullName() + " - from " + sender.getFullName());

        send_button = (Button) findViewById(R.id.SendMessage_button);
        message_text = (EditText) findViewById(R.id.send_message_editText);

        //System.out.println(send_button.getText().toString());

        message_listview = (ListView) findViewById(R.id.messages_listView);

        ref = new Firebase("https://firstappkrunal.firebaseio.com/");

        final ArrayList<Message> MessageList = new ArrayList<Message>();

        message_ref = ref.child("Messages");


        message_ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                System.out.println("onChildAdded");

                Message msgfact = dataSnapshot.getValue(Message.class);

                if (msgfact.getSender().equals(sender.getFullName()) && msgfact.getReceiver().equals(user.getFullName())) {

                    Message m = new Message(msgfact.getTime_stamp(), msgfact.getMessage_read(), msgfact.getMessage_text(), msgfact.getReceiver(), msgfact.getSender());
                    m.setPost_id(dataSnapshot.getKey().toString());
                    MessageList.add(m);
                } else if (msgfact.getSender().equals(user.getFullName()) && msgfact.getReceiver().equals(sender.getFullName())) {
                    Message m = new Message(msgfact.getTime_stamp(), msgfact.getMessage_read(), msgfact.getMessage_text(), msgfact.getReceiver(), msgfact.getSender());
                    m.setPost_id(dataSnapshot.getKey().toString());
                    MessageList.add(m);



                }




                setAdapter(MessageList);



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                System.out.println("onChildChanged");


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //MessageList.clear();
                Message msgfact = dataSnapshot.getValue(Message.class);

                Iterator<Message> it = MessageList.iterator();
                while (it.hasNext()) {
                    Message msg = it.next();
                    if (msg.getPost_id().equals(dataSnapshot.getKey())) {
                        it.remove();
                    }
                }

                setAdapter(MessageList);
                //System.out.println(dataSnapshot.getKey());

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


/*
        message_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot msgSnapshot : snapshot.getChildren()) {

                    Message msgfact = msgSnapshot.getValue(Message.class);
                    if (msgfact.getSender().equals(sender.getFullName()) && msgfact.getReceiver().equals(user.getFullName())) {

                        Message m = new Message(msgfact.getTime_stamp(), msgfact.getMessage_read(), msgfact.getMessage_text(), msgfact.getReceiver(), msgfact.getSender());
                        m.setPost_id(msgSnapshot.getKey().toString());
                        MessageList.add(m);
                    } else if (msgfact.getSender().equals(user.getFullName()) && msgfact.getReceiver().equals(sender.getFullName())) {
                        Message m = new Message(msgfact.getTime_stamp(), msgfact.getMessage_read(), msgfact.getMessage_text(), msgfact.getReceiver(), msgfact.getSender());
                        m.setPost_id(msgSnapshot.getKey().toString());
                        MessageList.add(m);

                    }
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
*/


        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //System.out.println("Called Onclick");

                String message = message_text.getText().toString();
                String timeStamp = new SimpleDateFormat("MMM dd, yyyy hh.mm.ss a").format(new Date());

                //System.out.println(timeStamp);


                if (!message.isEmpty()) {
                    message_ref = ref.child("Messages");
                    Firebase newMessageRef = message_ref.push();

                    // Add some data to the new location
                    final Map<String, String> post1 = new HashMap<String, String>();

                    post1.put("time_stamp", timeStamp);
                    post1.put("message_read", "false");
                    post1.put("message_text", message);
                    post1.put("receiver", user.getFullName());
                    post1.put("sender", sender.getFullName());
                    newMessageRef.setValue(post1);


                    // Get the unique ID generated by push()
                    String postId = newMessageRef.getKey();

                    message_text.setText("");






                }


            }
        });

        //System.out.println(MessageList.size());





        /*
        ImageView del = (ImageView) findViewById(R.id.mrow_delete);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Message msg = (Message) v.getTag();




            }
        });*/


    }

    public void getData()
    {
        ref = new Firebase("https://firstappkrunal.firebaseio.com/");


        final ArrayList<Message> MessageList = new ArrayList<Message>();

        message_ref = ref.child("Messages");

        message_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //
                for (DataSnapshot msgSnapshot : snapshot.getChildren()) {

                    Message msgfact = msgSnapshot.getValue(Message.class);
                    if (msgfact.getSender().equals(sender.getFullName()) && msgfact.getReceiver().equals(user.getFullName())) {

                        Message m = new Message(msgfact.getTime_stamp(), msgfact.getMessage_read(), msgfact.getMessage_text(), msgfact.getReceiver(), msgfact.getSender());
                        m.setPost_id(msgSnapshot.getKey().toString());
                        MessageList.add(m);
                    } else if (msgfact.getSender().equals(user.getFullName()) && msgfact.getReceiver().equals(sender.getFullName())) {
                        Message m = new Message(msgfact.getTime_stamp(), msgfact.getMessage_read(), msgfact.getMessage_text(), msgfact.getReceiver(), msgfact.getSender());
                        m.setPost_id(msgSnapshot.getKey().toString());
                        MessageList.add(m);

                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        final RowMessageAdapter adapter = new RowMessageAdapter(this, R.layout.row_message, MessageList, sender.getFullName());

        message_listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public void setAdapter(ArrayList<Message> MessageList)
    {
        RowMessageAdapter adapter = new RowMessageAdapter(this, R.layout.row_message, MessageList, sender.getFullName());

        message_listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_view_contact) {


            Intent mark_i = new Intent(MessagesActivity.this, ViewContactActivity.class);
            mark_i.putExtra("UserDetails",user);

            startActivity(mark_i);
            return true;

        } else if(id == R.id.menu_call_contact){

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + user.getPhoneNumber()));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(callIntent);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
