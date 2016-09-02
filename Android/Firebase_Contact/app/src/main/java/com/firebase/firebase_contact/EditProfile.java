package com.firebase.firebase_contact;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditProfile extends AppCompatActivity {


    EditText nameET, emailET, numberET, passwordET;
    Button update, cancel;
    TextView firstLastname;
    ImageView image;
    Uri selectedImageUri;
    Bitmap bitmap;
    Firebase ref;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ref = new Firebase("https://firstappkrunal.firebaseio.com/");

            nameET = (EditText) findViewById(R.id.editTextName);
            emailET = (EditText) findViewById(R.id.editTextEmail);
            numberET = (EditText) findViewById(R.id.editTextNumber);
            passwordET = (EditText) findViewById(R.id.editTextPassword);


            update = (Button) findViewById(R.id.buttonUpdate);
            cancel = (Button) findViewById(R.id.buttonCancel);

            firstLastname = (TextView) findViewById(R.id.FirstLastNameTV);

            image = (ImageView) findViewById(R.id.imageView);

        if(getIntent().getExtras()!=null) {
            user = (User) getIntent().getSerializableExtra("user");
            //user = (User) getIntent().getSerializableExtra("UserDetails");

            firstLastname.setText(user.getFullName());
            nameET.setText(user.getFullName());
            numberET.setText(user.getPhoneNumber());
            emailET.setText(user.getEmail());
            passwordET.setText(user.getPassword());

        }
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    finish();


                }
            });

            update.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                              Toast.makeText(EditProfile.this, "Update Successful", Toast.LENGTH_SHORT).show();
                                              Intent i = new Intent(EditProfile.this,ConversationActivity.class);
                                              startActivity(i);
                                          }
                                      });

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(
                            Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 1);
                }
            });
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
                selectedImageUri = data.getData();
                image.setImageURI(selectedImageUri);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String myBase64Image = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);

        }


    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}


