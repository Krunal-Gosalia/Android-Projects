package com.hw06.group25_hw06;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hw06.group25_hw06.database.DatabaseDataManager;

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

                    Weather weather = new Weather(city_txt.getText().toString(), state_txt.getText().toString());



                    DatabaseDataManager dm = new DatabaseDataManager(Add_City.this);
                    dm.saveCity(weather);

                    //Toast.makeText(Add_City.this, ""+city_id, Toast.LENGTH_SHORT).show();

                    setResult(RESULT_OK, i);


                    finish();
                }

            }
        });







    }


}
