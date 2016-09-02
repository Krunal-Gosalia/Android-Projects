package com.hw06.group25_hw06.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Krunal on 14-03-2016.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "weather.db";
    static final int DB_VERSION = 1;


    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CityTable.onCreate(db);
        NotesTable.onCreate(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        CityTable.onUpgrade(db, oldVersion, newVersion);

    }
}
