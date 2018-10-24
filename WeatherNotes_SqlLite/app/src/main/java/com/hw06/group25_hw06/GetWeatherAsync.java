package com.hw06.group25_hw06;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Krunal on 22-02-2016.
 */
public class GetWeatherAsync extends AsyncTask<String, Void, ArrayList<Hourly_forecast>> {

    ProgressDialog progressDialog;
    HourlyDataActivity act;

    public GetWeatherAsync(HourlyDataActivity activity)
    {
        this.act = activity;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(this.act);
        progressDialog.setMessage("Loading Hourly Data...");
        progressDialog.show();

    }

    @Override
    protected ArrayList<Hourly_forecast> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = null;

            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            int status_code = con.getResponseCode();

            Thread.sleep(1000);
            if(status_code == HttpURLConnection.HTTP_OK)
            {


                InputStream in = con.getInputStream();

                return HourlyWDataUtil.WeatherPullParser.parseHourlyData(in);

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }


    @Override
    protected void onPostExecute(ArrayList<Hourly_forecast> w) {
        super.onPostExecute(w);
        progressDialog.dismiss();
        this.act.WeatherForecastList(w);
    }
}
