package com.firebase.firebase_contact;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class ConversationActivity extends AppCompatActivity {

    ListView ConversationListView;
    Firebase ref;

    String email_add;
    public User sender;

    User sender_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        getSupportActionBar().setTitle("Stay In Touch");
        setContentView(R.layout.activity_conversation);

        ConversationListView = (ListView) findViewById(R.id.Conversation_listView);

        ref = new Firebase("https://firstappkrunal.firebaseio.com/");

        final ArrayList<User> UserList = new ArrayList<User>();
        email_add = getIntent().getStringExtra("UserEmail");

        Firebase ref1 = ref.child("username");

        //final User[] sender = new User[1];


        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //System.out.println("There are " + snapshot.getChildrenCount() + " users");
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {

                    User userfact = userSnapshot.getValue(User.class);
                    if (!userfact.getEmail().equals(email_add)) {
                        User user = new User(userfact.getEmail(), userfact.getFullName(), userfact.getPassword(), userfact.getPhoneNumber(), userfact.getPicture());
                        UserList.add(user);


                    } else if (userfact.getEmail().equals(email_add)) {
                        //System.out.println("Hello I am called");
                        sender_user = new User(userfact.getEmail(), userfact.getFullName(), userfact.getPassword(), userfact.getPhoneNumber(), userfact.getPicture());

                        load_data(sender_user);
                    }

                }

                //System.out.println("I am called again " + sender_user.getFullName());
                UserAdapter adapter = new UserAdapter(ConversationActivity.this, R.layout.row_conversation, UserList, sender_user);
                ConversationListView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
                adapter.setNotifyOnChange(true);


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("Read failed: " + firebaseError.getMessage());
            }
        });

        //System.out.println("I am called again " + sender_user.getFullName());

        /*


*/


        ConversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final Firebase message_ref = ref.child("Messages");


                message_ref.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot msgSnapShot : dataSnapshot.getChildren()) {

                            Message msgfact = msgSnapShot.getValue(Message.class);
                            if (msgfact.getSender().equals(UserList.get(position).getFullName()) && msgfact.getReceiver().equals(sender_user.getFullName()) && msgfact.getMessage_read().equals("false")) {

                                message_ref.child(msgSnapShot.getKey().toString() + "/message_read").setValue("true");
                            }

                        }


                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


                Intent i = new Intent(ConversationActivity.this, MessagesActivity.class);
                i.putExtra("UserObj", UserList.get(position));
                i.putExtra("SenderUserObj", sender_user);


                startActivity(i);
            }
        });


    }

    public void load_data(User sender) {
        this.sender = sender;
        //System.out.println("Load Data Called - " + this.sender.getFullName());

    }


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_edit_profile) {

            Intent mark_i = new Intent(ConversationActivity.this, EditProfile.class);
            mark_i.putExtra("email", email_add);
            mark_i.putExtra("user",sender_user);

            startActivity(mark_i);
            return true;

        } else if (id == R.id.menu_logout) {

            ref.unauth();
            Toast.makeText(ConversationActivity.this, "Logged-out Successfully", Toast.LENGTH_SHORT).show();
            finish();
            Intent in = new Intent(ConversationActivity.this, MainActivity.class);
            startActivity(in);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void callPhone(View v) {


    }


}
