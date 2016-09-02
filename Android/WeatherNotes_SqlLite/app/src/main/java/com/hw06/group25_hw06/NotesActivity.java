package com.hw06.group25_hw06;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {

    ArrayList<Note> notes = new ArrayList<Note>();

    ListView note_lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        note_lv = (ListView) findViewById(R.id.saved_notes);

        if (getIntent().getExtras() != null) {

            notes = (ArrayList<Note>) getIntent().getSerializableExtra("Note_Array");

            Log.d("Result_note", notes.get(0).getNote());
            NotesAdapter adapter = new NotesAdapter(this, R.layout.row_saved_notes, notes);
            note_lv.setAdapter(adapter);



        }
    }
}
