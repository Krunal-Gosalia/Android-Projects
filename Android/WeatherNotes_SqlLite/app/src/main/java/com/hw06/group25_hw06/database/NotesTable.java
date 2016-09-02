package com.hw06.group25_hw06.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Krunal on 20-03-2016.
 */
public class NotesTable {

    static final String TABLE_NAME = "notes";
    static final String Notes_ID = "city_key";
    static final String Notes_Date = "note_date";
    static final String Notes_Text = "note";

    static public void onCreate(SQLiteDatabase db){

        //Log.d("Table_Created", "Notes Table");

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + NotesTable.TABLE_NAME + " (");
        sb.append(NotesTable.Notes_ID + " INTEGER not null, ");
        sb.append(NotesTable.Notes_Date + " text not null, ");
        sb.append(NotesTable.Notes_Text + " text not null");
        sb.append(");");
        db.execSQL(sb.toString());

        //Log.d("Result", "Note Table should be created");

    }



    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + NotesTable.TABLE_NAME);
        CityTable.onCreate(db);
    }
}
