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
 * Created by Krunal on 21-03-2016.
 */
public class GetWeatherForecastAsync extends AsyncTask<String, Void, ArrayList<Day_Forecast>> {
    ProgressDialog progressDialog;
    ForecastDataActivity act;

    public GetWeatherForecastAsync(ForecastDataActivity activity)
    {
        this.act = activity;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(this.act);
        progressDialog.setMessage("Loading Forecast Data...");
        progressDialog.show();

    }

    @Override
    protected ArrayList<Day_Forecast> doInBackground(String... params) {
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

                return DayForecastUtil.WeatherPullParser.parseDayData(in);

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
    protected void onPostExecute(ArrayList<Day_Forecast> dw) {
        super.onPostExecute(dw);
        progressDialog.dismiss();
        this.act.WeatherForecastList(dw);
    }
}
