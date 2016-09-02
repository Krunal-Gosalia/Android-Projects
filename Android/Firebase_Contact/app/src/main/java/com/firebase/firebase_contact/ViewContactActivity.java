package com.firebase.firebase_contact;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewContactActivity extends AppCompatActivity {

    User user;

    TextView title, name, email_id, phone;
    ImageView profile_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        getSupportActionBar().setTitle("View Contact");

        setContentView(R.layout.activity_view_contact);

        title = (TextView) findViewById(R.id.contactNameTitle_textView);
        name = (TextView) findViewById(R.id.contactName_value);
        phone = (TextView) findViewById(R.id.contactPhone_value);
        email_id = (TextView) findViewById(R.id.contactEmail_value);


        if(getIntent().getExtras()!=null)
        {
            user = (User) getIntent().getSerializableExtra("UserDetails");

            title.setText(user.getFullName());
            name.setText(user.getFullName());
            phone.setText(user.getPhoneNumber());
            email_id.setText(user.getEmail());




        }


    }
}
