package com.inclass_06.group25_hw04;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Created by Krunal on 22-02-2016.
 */
public class MoviesUtil {

    static public class MovieJSONParser
    {


        StringBuilder xmlInnerText;


        static public ArrayList<Movie> parseMovie(String in) throws JSONException {

            ArrayList<Movie> MovieList = null;

            JSONObject root = new JSONObject(in);
            JSONArray MoviesJSONArray = root.getJSONArray("Search");

            if(MoviesJSONArray.length() > 0) {

                MovieList = new ArrayList<Movie>();


                for (int i = 0; i < MoviesJSONArray.length(); i++) {

                    JSONObject movieJsonObj = MoviesJSONArray.getJSONObject(i);
                    Movie movie = new Movie();

                    //Movie.createMovie(MovieJsonObj); another way
                    movie.setTitle(movieJsonObj.getString("Title"));
                    movie.setImdbID(movieJsonObj.getString("imdbID"));
                    movie.setPoster(movieJsonObj.getString("Poster"));
                    movie.setType(movieJsonObj.getString("Type"));
                    movie.setYear(movieJsonObj.getInt("Year"));

                    MovieList.add(movie);

                }
            }
            else
            {
                return MovieList;
            }



            Collections.sort(MovieList, new Comparator<Movie>() {

                public int compare(Movie m1, Movie m2)
                {

                    return (int) (m2.getYear() - m1.getYear());
                }

            });


            return MovieList;



        }


        public static Movie parseMovieDetails(String s) throws JSONException {

            Movie movie = new Movie();
            JSONObject root = new JSONObject(s);
            //JSONArray MoviesJSONArray = root.getJSONArray("Search");

            if(root.length() > 0) {

                //Movie.createMovie(MovieJsonObj); another way
                movie.setTitle(root.getString("Title"));
                movie.setImdbID(root.getString("imdbID"));
                movie.setPoster(root.getString("Poster"));
                movie.setType(root.getString("Type"));
                movie.setYear(root.getInt("Year"));

                movie.setReleased(root.getString("Released"));
                movie.setGenre(root.getString("Genre"));
                movie.setDirector(root.getString("Director"));

                movie.setPlot(root.getString("Plot"));
                movie.setPoster(root.getString("Poster"));
                movie.setActors(root.getString("Actors"));
                movie.setImdbRating(root.getDouble("imdbRating"));



            }

            return movie;
        }
    }


}
