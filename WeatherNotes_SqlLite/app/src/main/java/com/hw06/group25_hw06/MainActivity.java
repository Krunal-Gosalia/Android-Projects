package com.hw06.group25_hw06;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hw06.group25_hw06.database.DatabaseDataManager;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    ArrayList<Weather> loc = new ArrayList<Weather>();
    ListView lv;
    TextView main_disp_txt;
    WeatherAdapter adapter;
    ArrayList<String> loc_list;

    DatabaseDataManager dm;

    public static final String WeatherLocURL  = "WeatherLocURL";
    public static final String api_key1 = "399d7c08a50a33a5";
    public static final String api_key = "f2e23ecf1d6fc434";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        getSupportActionBar().setTitle("Weather App");

        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.location_list);
        lv.setBackgroundColor(Color.rgb(240, 240, 240));
        main_disp_txt = (TextView) findViewById(R.id.main_disp_txt);

        dm = new DatabaseDataManager(this);

        retrieveLoc();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(MainActivity.this, AndroidTabLayoutActivity.class);

                i.putExtra("Wobj", loc.get(position));

                startActivity(i);




            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.add_city_icon)
        {
            Intent intent = new Intent(this, Add_City.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, 1);

        }
        else if(item.getItemId() == R.id.view_notes)
        {
            Intent intent1 = new Intent(this, NotesActivity.class);

            ArrayList<Note> n = dm.getAllNotes();

            intent1.putExtra("Note_Array", n);

            startActivity(intent1);

        }
        else if(item.getItemId() == R.id.clear_city)
        {
            if(dm.deleteCities())
            {
                retrieveLoc();

                Toast.makeText(MainActivity.this, "Deleted All Cities", Toast.LENGTH_SHORT).show();

            }


        }

        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                retrieveLoc();

            }

        }
    }

    public void retrieveLoc() {

        loc = dm.getCities();

        String WLocUrl = "";

        if(loc.size() > 0) {
            for (int i = 0; i < loc.size(); i++) {

                WLocUrl = "http://api.wunderground.com/api/" + api_key + "/hourly/q/" + loc.get(i).getState() + "/" + loc.get(i).getCity().replace(" ", "_") + ".xml";
                new GetTempTask().execute(WLocUrl);

                try {
                    String s = new GetTempTask().execute(WLocUrl).get();
                    loc.get(i).setCurrentTemp(Integer.parseInt(s));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }

            main_disp_txt.setEnabled(false);
            main_disp_txt.setVisibility(View.GONE);
            adapter = new WeatherAdapter(this, R.layout.row_item_main_layout, loc);
            lv.setAdapter(adapter);

            adapter.notifyDataSetChanged();
            adapter.setNotifyOnChange(true);

        }
        else
        {

            lv.setAdapter(null);

            main_disp_txt.setEnabled(true);
            main_disp_txt.setVisibility(View.VISIBLE);


        }

    }


    private class GetTempTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {

                Log.d("Called", params[0]);
                URL url = new URL(params[0]);


                HttpURLConnection con = null;

                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();

                int status_code = con.getResponseCode();

                Thread.sleep(1000);
                if (status_code == HttpURLConnection.HTTP_OK) {


                    InputStream in = con.getInputStream();

                    return HourlyWDataUtil.WeatherPullParser.parseCurrentTemp(in);

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
        protected void onPostExecute(String result) {


        }
    }
}
