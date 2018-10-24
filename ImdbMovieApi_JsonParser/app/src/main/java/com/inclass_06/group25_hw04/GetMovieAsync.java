package com.inclass_06.group25_hw04;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Krunal on 22-02-2016.
 */
public class GetMovieAsync extends AsyncTask<String, Void, ArrayList<Movie>> {

    ProgressDialog progressDialog;
    Search_Movies act;

    public GetMovieAsync(Search_Movies activity)
    {
        this.act = activity;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(this.act);
        progressDialog.setMessage("Loading Movie List ...");
        progressDialog.show();

    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = null;

            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            Thread.sleep(3000);

            int status_code = con.getResponseCode();
            if(status_code == HttpURLConnection.HTTP_OK)
            {
                Log.d("Result", "Connected");
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while(line != null)
                {
                    sb.append(line);
                    line = br.readLine();
                }

                return MoviesUtil.MovieJSONParser.parseMovie(sb.toString());




            }

            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;

    }


    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);
        progressDialog.dismiss();
        act.movie_list(movies);
    }
}
