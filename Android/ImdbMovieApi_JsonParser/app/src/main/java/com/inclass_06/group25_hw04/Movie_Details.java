package com.inclass_06.group25_hw04;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public class Movie_Details extends AppCompatActivity {

    ArrayList<Movie> m;
    int id;
    ImageView iv;

    ImageButton prev, next;
    Button finish;
    TextView title, rel_date, genre, director, actor, plot;
    RatingBar rb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle("Movie Detail");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_movie_details);

        if (getIntent().getExtras() != null) {
            m = (ArrayList<Movie>) getIntent().getSerializableExtra("movieList");
            id = getIntent().getExtras().getInt("id");

            String movie_details_url = "http://www.omdbapi.com/?i="+m.get(id).getImdbID();

            new GetMovieDetailsAsync(Movie_Details.this).execute(movie_details_url);

            title = (TextView) findViewById(R.id.movie_title_text);
            rel_date = (TextView) findViewById(R.id.release_value_text);
            genre = (TextView) findViewById(R.id.genre_value_text);
            director = (TextView) findViewById(R.id.director_value_text);
            actor = (TextView) findViewById(R.id.actor_value_text);
            plot = (TextView) findViewById(R.id.plot_value_text);
            iv = (ImageView) findViewById(R.id.movie_thumb_imageView);
            rb = (RatingBar) findViewById(R.id.ratingBar);
            prev = (ImageButton) findViewById(R.id.prev_button);
            next = (ImageButton) findViewById(R.id.next_button);
            finish = (Button) findViewById(R.id.finish_button);

            new DownloadFilesTask(Movie_Details.this).execute(m.get(id).getPoster());

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String web_view_url = "http://m.imdb.com/title/" + m.get(id).getImdbID();

                    //new GetMovieAsync(Main_Activity.this).execute(imdb_url);

                    Intent in = new Intent(Movie_Details.this, MovieDet_Web.class);
                    in.putExtra("web_view_link", web_view_url);
                    startActivity(in);

                }
            });


            prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (id > 0) {

                        id = id - 1;

                        String movie_details_url = "http://www.omdbapi.com/?i=" + m.get(id).getImdbID();

                        new GetMovieDetailsAsync(Movie_Details.this).execute(movie_details_url);
                        new DownloadFilesTask(Movie_Details.this).execute(m.get(id).getPoster());

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "First movie on the list", Toast.LENGTH_SHORT).show();
                    }


                }
            });


            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (id < m.size()-1) {

                        id = id + 1;

                        String movie_details_url = "http://www.omdbapi.com/?i=" + m.get(id).getImdbID();

                        new GetMovieDetailsAsync(Movie_Details.this).execute(movie_details_url);
                        new DownloadFilesTask(Movie_Details.this).execute(m.get(id).getPoster());

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Last movie on the list", Toast.LENGTH_SHORT).show();
                    }




                }
            });

            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });



        }
    }

    public void MovieDet(final Movie m){

        String release_date_val = "";
        title.setText(m.getTitle());

        Log.d("Result", "" + m.getReleased().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Date date = null;
        try {
            date = sdf.parse(m.getReleased().toString());

            SimpleDateFormat sdf1 = new SimpleDateFormat("MMM dd yyyy");
            release_date_val = sdf1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d("Result", "" + rel_date);


        rel_date.setText(release_date_val);
        genre.setText(m.getGenre());
        director.setText(m.getDirector());
        actor.setText(m.getActors());
        plot.setText(m.getPlot());
        //Log.d("Result", "" + (int) (m.getImdbRating() / 2));
        rb.setRating((int) (m.getImdbRating()/2));

        Drawable progress = rb.getProgressDrawable();
        DrawableCompat.setTint(progress, Color.CYAN);







    }

    private class DownloadFilesTask extends AsyncTask<String, Void, Bitmap> {


        public DownloadFilesTask(Movie_Details movie_details) {
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                Bitmap bitmap = BitmapFactory.decodeStream(con.getInputStream());
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }



        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);

            if(result != null){
                iv.setImageBitmap(result);
            }




        }
    }
}
