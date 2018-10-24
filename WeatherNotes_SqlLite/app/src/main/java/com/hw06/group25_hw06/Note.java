package com.hw06.group25_hw06;

import java.io.Serializable;

/**
 * Created by Krunal on 22-03-2016.
 */
public class Note implements Serializable {

    String note, date;
    int city_key;

    public int getCity_key() {
        return city_key;
    }

    public void setCity_key(int city_key) {
        this.city_key = city_key;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Note() {

    }
}
