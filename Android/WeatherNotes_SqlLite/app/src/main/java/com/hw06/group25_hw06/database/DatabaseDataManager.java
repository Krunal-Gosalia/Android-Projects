package com.hw06.group25_hw06.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hw06.group25_hw06.Note;
import com.hw06.group25_hw06.Weather;

import java.util.ArrayList;

/**
 * Created by Krunal on 14-03-2016.
 */
public class DatabaseDataManager {

    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private CityDAO cityDAO;
    private NoteDAO noteDAO;

    public DatabaseDataManager(Context mContext)
    {
        this.mContext = mContext;
        dbOpenHelper = new DatabaseOpenHelper(this.mContext);
        db = dbOpenHelper.getWritableDatabase();
        cityDAO = new CityDAO(db);
        noteDAO = new NoteDAO(db);

    }

    public void close()
    {
        if(db!= null)
            db.close();
    }

    public long saveCity(Weather weather)
    {
        return this.cityDAO.save(weather);
    }

    public long saveNote(Note note)
    {
        return this.noteDAO.save(note);
    }



    public ArrayList<Weather> getCities()
    {
        return this.cityDAO.getAll();
    }

    public int getCityKey(String city) { return this.cityDAO.getKey(city);}


    public ArrayList<Note> getAllNotes() {
        return this.noteDAO.getAll();
    }

    public boolean deleteCities() {
        return this.cityDAO.deleteAll();
    }
}
