package com.hw05.group25_hw05;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Add_City extends AppCompatActivity {

    Button save_btn;
    EditText city_txt, state_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        getSupportActionBar().setTitle("Add City");
        setContentView(R.layout.activity_add_city);

        save_btn = (Button) findViewById(R.id.save_btn);
        city_txt = (EditText) findViewById(R.id.city_txt);
        state_txt = (EditText) findViewById(R.id.state_txt);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String city = city_txt.getText().toString().replace(" ", "_");
                if(state_txt.getText().toString().isEmpty())
                    state_txt.setError("Enter the state");
                else if(city_txt.getText().toString().isEmpty())
                    city_txt.setError("Enter the city");
                else {

                    Intent i = getIntent();

                    i.putExtra("state", state_txt.getText().toString());
                    i.putExtra("city", city_txt.getText().toString());
                    setResult(RESULT_OK, i);


                    finish();
                }

            }
        });







    }
}
