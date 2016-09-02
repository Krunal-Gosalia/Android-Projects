package com.hw06.group25_hw06.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Krunal on 20-03-2016.
 */
public class CityTable {

    static final String TABLE_NAME = "city";
    static final String City_ID = "key";
    static final String City_Name = "city";
    static final String City_State_Name = "state";

    static public void onCreate(SQLiteDatabase db){

        //Log.d("Table_Created", "City Table");

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + CityTable.TABLE_NAME + " (");
        sb.append(CityTable.City_ID + " INTEGER primary key autoincrement, ");
        sb.append(CityTable.City_Name + " text not null, ");
        sb.append(CityTable.City_State_Name + " text ");
        sb.append(");");
        db.execSQL(sb.toString());

    }



    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + CityTable.TABLE_NAME);
        CityTable.onCreate(db);
    }
}
