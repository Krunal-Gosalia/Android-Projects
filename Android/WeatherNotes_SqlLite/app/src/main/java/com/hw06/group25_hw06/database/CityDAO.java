package com.hw06.group25_hw06.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hw06.group25_hw06.Weather;

import java.util.ArrayList;

/**
 * Created by Krunal on 20-03-2016.
 */
public class CityDAO {

    private SQLiteDatabase db;

    public CityDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(Weather weather){
        ContentValues values = new ContentValues();
        values.put(CityTable.City_Name, weather.getCity());
        values.put(CityTable.City_State_Name, weather.getState());


        return db.insert(CityTable.TABLE_NAME, null, values);
    }

    public ArrayList<Weather> getAll()
    {
        ArrayList<Weather> Cities = new ArrayList<Weather>();

        Cursor c = db.query(true, CityTable.TABLE_NAME, new String[]{CityTable.City_Name, CityTable.City_State_Name}, null, null, null, null, null, null);

        if(c != null && c.moveToFirst())
        {
            do
            {
                Weather weather = buildNoteFromCursor(c);

                if(weather != null)
                    Cities.add(weather);

            }while (c.moveToNext());

            if(!c.isClosed())
                c.close();
        }

        return Cities;

    }



    private Weather buildNoteFromCursor(Cursor c)
    {
        Weather weather = null;
        if(c != null)
        {
            weather = new Weather();
            weather.setCity(c.getString(c.getColumnIndex(CityTable.City_Name)));
            weather.setState(c.getString(c.getColumnIndex(CityTable.City_State_Name)));

        }

        return weather;

    }

    public int getKey(String city) {

        String query = "SELECT  "+CityTable.City_ID+" FROM " + CityTable.TABLE_NAME +" where "+CityTable.City_Name +"='"+city+"'";

        int key = 0;
        //Log.d("Result", query);

        Cursor c = db.rawQuery(query, null);
        if(c != null && c.moveToFirst()) {
             key = c.getInt(c.getColumnIndex(CityTable.City_ID));
        }
        c.close();

        return key;
        //return key;
    }


    public boolean deleteAll() {

        db.delete(CityTable.TABLE_NAME, null, null);
        return true;
    }
}
