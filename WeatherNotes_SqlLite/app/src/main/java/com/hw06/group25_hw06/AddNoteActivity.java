package com.hw06.group25_hw06;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hw06.group25_hw06.database.DatabaseDataManager;

public class AddNoteActivity extends AppCompatActivity {

    DatabaseDataManager dm;
    int Note_id, pos;
    String Note_Date;

    EditText note_txt;
    Button SaveNote_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        getSupportActionBar().setTitle("Add Notes Activity");

        setContentView(R.layout.activity_add_note);

        if (getIntent().getExtras() != null) {

            Note_Date = getIntent().getStringExtra(ForecastDataActivity.NoteDate);
            String city = getIntent().getStringExtra(ForecastDataActivity.NoteCity);
             pos = getIntent().getIntExtra("pos", 0);

            dm = new DatabaseDataManager(AddNoteActivity.this);

            Note_id = dm.getCityKey(city);

        }

        note_txt = (EditText) findViewById(R.id.note_editText);
        SaveNote_btn = (Button) findViewById(R.id.save_note_button);

        SaveNote_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = note_txt.getText().toString();

                if(note.isEmpty())
                    note_txt.setError("Enter the note");
                else if(note.length() > 30)
                    note_txt.setError("Max length is 30");
                else
                {
                    Note n = new Note();
                    n.setDate(Note_Date);
                    n.setCity_key(Note_id);
                    n.setNote(note);
                    dm.saveNote(n);
                }

                Intent i = getIntent();
                i.putExtra("position", pos);
                setResult(RESULT_OK, i);
                finish();



            }
        });








    }
}
