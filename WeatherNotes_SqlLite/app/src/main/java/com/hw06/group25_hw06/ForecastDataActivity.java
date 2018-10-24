package com.hw06.group25_hw06;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hw06.group25_hw06.database.DatabaseDataManager;

import java.util.ArrayList;

public class ForecastDataActivity extends AppCompatActivity {

    Weather wObj;
    TextView floc;
    ListView Wday_lv;

    ImageView note_iv;

    DatabaseDataManager dm;

    ArrayList<Note> note_array = new ArrayList<Note>();

    public static final String NoteDate = "Note_Date";
    public static final String NoteCity = "Note_City";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_data);

        if (getIntent().getExtras() != null) {

            wObj = (Weather) getIntent().getSerializableExtra(AndroidTabLayoutActivity.Weather_Obj);

        }



        String state = wObj.getState().toUpperCase();
        String city = wObj.getCity().replace(" ", "_");

        String day_url = "http://api.wunderground.com/api/"+MainActivity.api_key+"/forecast10day/q/"+state+"/"+city+".xml";

        floc = (TextView) findViewById(R.id.floc_value);

        floc.setText(wObj.getCity() + ", " + wObj.getState());

        Log.d("Result", day_url);

        new GetWeatherForecastAsync(ForecastDataActivity.this).execute(day_url);

        dm = new DatabaseDataManager(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {

               Toast.makeText(ForecastDataActivity.this, "Note Saved Succesfully", Toast.LENGTH_SHORT).show();

           }

        }
    }


    public void WeatherForecastList(final ArrayList<Day_Forecast> adf) {

        Wday_lv = (ListView) findViewById(R.id.forecast_listView);
        Wday_lv.setBackgroundColor(Color.rgb(240, 240, 240));

        WeatherForecastAdapter adapter = new WeatherForecastAdapter(this, R.layout.row_forecast_layout, adf);
        Wday_lv.setAdapter(adapter);

        Wday_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent add_note_intent = new Intent(ForecastDataActivity.this, AddNoteActivity.class);

                add_note_intent.putExtra(NoteDate, adf.get(position).getDate());
                add_note_intent.putExtra(NoteCity, wObj.getCity());
                add_note_intent.putExtra("pos", position);
                //note_iv = (ImageView) view.findViewById(R.id.noteBook_icon);

                //note_iv.setImageResource(R.drawable.note);

                startActivityForResult(add_note_intent, 2);

                note_iv = (ImageView) view.findViewById(R.id.noteBook_icon);

                note_iv.setImageResource(R.drawable.note);


            }
        });

    }


}
