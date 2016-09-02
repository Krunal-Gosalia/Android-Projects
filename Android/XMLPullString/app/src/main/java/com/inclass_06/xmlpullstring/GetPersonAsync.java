package com.inclass_06.xmlpullstring;

import android.os.AsyncTask;
import android.util.Log;

import org.xml.sax.SAXException;
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
public class GetPersonAsync extends AsyncTask<String, Void, ArrayList<Person>> {
    @Override
    protected ArrayList<Person> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = null;

            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            int status_code = con.getResponseCode();
            if(status_code == HttpURLConnection.HTTP_OK)
            {
                InputStream in = con.getInputStream();

                return PersonsUtil.PersonPullParser.parsePerson(in);

            }

            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Person> persons) {
        super.onPostExecute(persons);
        Log.d("Demo", persons.toString());
    }
}
