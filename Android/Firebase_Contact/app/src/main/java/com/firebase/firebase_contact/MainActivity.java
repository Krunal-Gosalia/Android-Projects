package com.firebase.firebase_contact;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class MainActivity extends AppCompatActivity {

    EditText email_value, pass_value;
    Button login_btn, new_account_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        getSupportActionBar().setTitle("Stay In Touch (Login)");

        Firebase.setAndroidContext(this);

        setContentView(R.layout.activity_main);

        email_value = (EditText) findViewById(R.id.Email_editText);
        pass_value = (EditText) findViewById(R.id.Password_editText);


        login_btn = (Button) findViewById(R.id.Login_button);
        new_account_btn = (Button) findViewById(R.id.new_Acc_button);

        final Firebase ref = new Firebase("https://firstappkrunal.firebaseio.com/");

        new_account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(in);


            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = email_value.getText().toString();
                final String password = pass_value.getText().toString();


// Create a handler to handle the result of the authentication
                Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {

                        System.out.println("Authenticated successful");
                        Intent in = new Intent(MainActivity.this, ConversationActivity.class);
                        in.putExtra("UserEmail",email);
                        startActivity(in);


                        // Authenticated successfully with payload authData
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        // Authenticated failed with error firebaseError
                        System.out.println("Failed"+ email);
                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

                    }
                };


// Or with an email/password combination
                ref.authWithPassword(email, password, authResultHandler);
                    System.out.print(authResultHandler);



            }
        });





    }
}
