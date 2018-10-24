package com.inclass_06.group25_hw04;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main_Activity extends AppCompatActivity {

    final static String imdb_url_key = "movie_url";

    EditText search_text;
    Button search_imdb_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        getSupportActionBar().setTitle("IMDb Movie App");

        setContentView(R.layout.activity_main);


        search_text = (EditText) findViewById(R.id.movie_search_txt);
        search_imdb_button = (Button) findViewById(R.id.search_imdb_btn);







        search_imdb_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isConnectedOnline()) {


                    String movie_name = search_text.getText().toString();

                    if (movie_name.isEmpty()) {
                        search_text.setError("Enter the movie name");
                    } else {
                        String imdb_url = "http://www.omdbapi.com/?type=movie&s=" + movie_name;

                        //new GetMovieAsync(Main_Activity.this).execute(imdb_url);

                        Intent in = new Intent(Main_Activity.this, Search_Movies.class);
                        in.putExtra(imdb_url_key, imdb_url);
                        startActivityForResult(in, 1);
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "Not Connected to Internet", Toast.LENGTH_SHORT).show();

            }
        });








    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                String msg = data.getStringExtra("error_msg");
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

        }
    }



    private boolean isConnectedOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }


}
