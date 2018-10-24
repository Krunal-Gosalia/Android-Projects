package com.hw06.group25_hw06;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Krunal on 21-03-2016.
 */
public class DayForecastUtil {

    static public class WeatherPullParser {

        StringBuilder xmlInnerText;


        static public ArrayList<Day_Forecast> parseDayData(InputStream in) throws XmlPullParserException, IOException {

            boolean flag = false;

            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");

            //parser.require(XmlPullParser.START_TAG, null, "simpleforecast");
            parser.nextTag();

            Day_Forecast df = null;
            ArrayList<Day_Forecast> DayForecastArrayList = new ArrayList<Day_Forecast>();



            int event = parser.getEventType();


            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {

                    case XmlPullParser.START_TAG:

                        if(parser.getName().equals("simpleforecast"))
                        {
                            flag = true;
                        }

                        if (parser.getName().equals("forecastday") && flag == true) {
                            df = new Day_Forecast();

                        }


                        if (parser.getName().equals("pretty") && flag == true) {


                            String[] date_txt = parser.nextText().split(" on ");
                            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

                            Date date = null;

                            try
                            {
                                date = sdf.parse(date_txt[1]);
                                SimpleDateFormat sdf1 = new SimpleDateFormat("MMMM dd");
                                df.setDate(sdf1.format(date));
                            }
                            catch (ParseException e)
                            {
                                e.printStackTrace();

                            }
                            //df.setDate(parser.nextText());
                        }

                        if (parser.getName().equals("high") && flag == true) {
                            parser.nextTag();
                            if (parser.getName().equals("fahrenheit"))
                                df.setHightemp(parser.nextText());
                        }

                        if (parser.getName().equals("low") && flag == true) {
                            parser.nextTag();
                            if (parser.getName().equals("fahrenheit"))
                                df.setLowTemp(parser.nextText());
                        }

                        if (parser.getName().equals("conditions") && flag == true) {
                            df.setClouds(parser.nextText());
                        }

                        if (parser.getName().equals("icon_url") && flag == true) {
                            //Log.d("print","icon Called");
                            df.setIconUrl(parser.nextText());
                        }

                        if (parser.getName().equals("maxwind") && flag == true) {
                            parser.nextTag();

                            if (parser.getName().equals("mph"))
                                df.setMaxwindSpeed(parser.nextText());

                            if (parser.getName().equals("dir"))
                                df.setWindDirection(parser.nextText());

                        }

                        if (parser.getName().equals("avehumidity") && flag == true)
                            df.setAvghumidity(parser.nextText());


                        break;

                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("forecastday") && flag == true)
                            DayForecastArrayList.add(df);
                        break;

                    default:
                        break;

                }
                event = parser.next();
            }

            //parser.require(XmlPullParser.END_TAG, null, "simpleforecast");
            return DayForecastArrayList;


        }
    }
}
