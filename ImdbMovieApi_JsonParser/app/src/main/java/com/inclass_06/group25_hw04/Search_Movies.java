package com.inclass_06.group25_hw04;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

public class Search_Movies extends AppCompatActivity {


    String url;

    ScrollView sv;
    LinearLayout title_linear;
    TextView tv;
    Intent in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        getSupportActionBar().setTitle("Search Movies");*/

        super.onCreate(savedInstanceState);



        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
            getSupportActionBar().setTitle("Search Movie");

        }



        setContentView(R.layout.activity_search_movies);

        if (getIntent().getExtras() != null) {
            url = getIntent().getExtras().getString(Main_Activity.imdb_url_key);
        }

        new GetMovieAsync(Search_Movies.this).execute(url);

        title_linear = (LinearLayout) findViewById(R.id.title_linear);
        sv = (ScrollView) findViewById(R.id.scrollView1);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();



    }

    public void movie_list(final ArrayList<Movie> n) {

        if(n != null) {


            for (int i = 0; i < n.size(); i++) {
                final int id = i;
                tv = new TextView(this);

                tv.setText(n.get(i).getTitle() + " (" + n.get(i).getYear() + ")");
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, 0);

                tv.setLayoutParams(params);
                tv.setPadding(20, 50, 20, 50);
                tv.setTextSize(14);
                tv.setTextColor(Color.BLACK);
                tv.setTypeface(Typeface.create(Typeface.SERIF, Typeface.NORMAL));

                GradientDrawable gd = new GradientDrawable();
                gd.setColor(Color.TRANSPARENT);

                gd.setStroke(1, Color.GRAY); // Changes this drawbale to use a single color instead of a gradient


                tv.setBackgroundDrawable(gd);
                tv.setGravity(Gravity.CENTER_VERTICAL);
                tv.setId(i);

                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        in = new Intent(Search_Movies.this, Movie_Details.class);

                        in.putExtra("movieList", n);
                        in.putExtra("id", id);
                        startActivity(in);
                    }
                });


                title_linear.addView(tv);


            }
        }
        else
        {
            Intent i = getIntent();

            i.putExtra("error_msg", "No Results Found");
            setResult(RESULT_OK, i);


            finish();

        }
    }

}
