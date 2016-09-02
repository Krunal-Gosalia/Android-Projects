package com.hw06.group25_hw06;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Krunal on 04-03-2016.
 */
public class Weather implements Serializable {
    String city, state;
    int minTemp;

    public int getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(int currentTemp) {
        this.currentTemp = currentTemp;
    }

    int currentTemp;

    public Weather() {
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(int max_temp) {
        this.max_temp = max_temp;
    }

    int max_temp;
    ArrayList<Hourly_forecast> hf;




    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Weather(String city, String state) {
        this.city = city;
        this.state = state;
    }

    public ArrayList<Hourly_forecast> getHf() {

        return hf;

    }

    public void setHf(ArrayList<Hourly_forecast> hf) {
        this.hf = hf;
    }

    public Weather(String state, String city, ArrayList<Hourly_forecast> hf) {

        this.state = state;
        this.city = city;
        this.hf = hf;
    }

    @Override
    public String toString() {
        return city + ", "+state;
    }



}
