package com.hw06.group25_hw06.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hw06.group25_hw06.Note;

import java.util.ArrayList;

/**
 * Created by Krunal on 20-03-2016.
 */
public class NoteDAO {

    private SQLiteDatabase db;

    public NoteDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(Note note){
        ContentValues values = new ContentValues();
        values.put(NotesTable.Notes_ID, note.getCity_key());
        values.put(NotesTable.Notes_Date, note.getDate());
        values.put(NotesTable.Notes_Text, note.getNote());


        return db.insert(NotesTable.TABLE_NAME, null, values);
    }

    public ArrayList<Note> getAll()
    {
        ArrayList<Note> notes = new ArrayList<Note>();

        Cursor c = db.query(true, NotesTable.TABLE_NAME, new String[]{NotesTable.Notes_ID, NotesTable.Notes_Text, NotesTable.Notes_Date}, null, null, null, null, null, null);

        if(c != null && c.moveToFirst())
        {
            do
            {
                Note note = buildNoteFromCursor(c);

                if(note != null)
                    notes.add(note);

            }while (c.moveToNext());

            if(!c.isClosed())
                c.close();
        }

        return notes;

    }



    private Note buildNoteFromCursor(Cursor c)
    {
        Note note = null;
        if(c != null)
        {
            note = new Note();
            note.setCity_key(c.getInt(c.getColumnIndex(NotesTable.Notes_ID)));
            note.setDate(c.getString(c.getColumnIndex(NotesTable.Notes_Date)));
            note.setNote(c.getString(c.getColumnIndex(NotesTable.Notes_Text)));
        }

        return note;

    }



}
