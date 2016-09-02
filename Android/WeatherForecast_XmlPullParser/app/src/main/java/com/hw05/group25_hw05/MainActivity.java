package com.hw05.group25_hw05;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ArrayList<Weather> loc = new ArrayList<Weather>();
    ListView lv;
    TextView main_disp_txt;
    ArrayAdapter adapter;
    ArrayList<String> loc_list;

    public static final String WeatherLocURL  = "WeatherLocURL";
    private String api_key = "399d7c08a50a33a5";


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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent i = new Intent(MainActivity.this, HourlyDataActivity.class);

                String state = loc.get(position).getState().toUpperCase();
                String city = loc.get(position).getCity().replace(" ", "_");

                String WLocUrl = "http://api.wunderground.com/api/"+api_key+"/hourly/q/"+state+"/"+city+".xml";
                i.putExtra(WeatherLocURL, WLocUrl);
                i.putExtra("Wobj", loc.get(position));

                startActivity(i);




            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                adapter.remove(loc_list.get(position));
                loc.remove(position);
                adapter.notifyDataSetChanged();

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
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, 1);

        }
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                String city = data.getStringExtra("city");
                String state = data.getStringExtra("state");

                loc.add(new Weather(city, state));

                retrieveLoc(loc);
            }

        }
    }

    public void retrieveLoc(ArrayList<Weather> loc) {

        loc_list = new ArrayList<String>();
        for(int i=0; i < loc.size(); i++)
        {
            loc_list.add(loc.get(i).toString());
        }

        main_disp_txt.setEnabled(false);
        main_disp_txt.setVisibility(View.GONE);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, loc_list);
        lv.setAdapter(adapter);









    }
}
