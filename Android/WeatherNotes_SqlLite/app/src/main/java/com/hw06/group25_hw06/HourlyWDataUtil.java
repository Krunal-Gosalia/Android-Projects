package com.hw06.group25_hw06;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Krunal on 22-02-2016.
 */
public class HourlyWDataUtil {



    static public class WeatherPullParser
    {

        StringBuilder xmlInnerText;


        static public ArrayList<Hourly_forecast> parseHourlyData(InputStream in) throws XmlPullParserException, IOException {


            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");

            Hourly_forecast hf = null;
            ArrayList<Hourly_forecast> HourlyForecastArrayList = new ArrayList<Hourly_forecast>();
            String flag = null;



            int event = parser.getEventType();

            while(event!=XmlPullParser.END_DOCUMENT)
            {
                switch (event)
                {
                    case XmlPullParser.START_TAG:





                        if(parser.getName().equals("forecast")){
                            hf = new Hourly_forecast();

                        }

                        if(parser.getName().equals("civil")) {
                            hf.setTime(parser.nextText().trim());

                        }



                        if(parser.getName().equals("temp"))
                            flag = "temp";

                        if(parser.getName().equals("english") && flag == "temp")
                        {
                            hf.setTemperature(parser.nextText());
                            flag = null;
                        }

                        if(parser.getName().equals("dewpoint"))
                            flag = "dew";

                        if(parser.getName().equals("english") && flag == "dew")
                        {
                            hf.setDewpoint(parser.nextText());
                            flag = null;
                        }

                        if(parser.getName().equals("condition")) {
                            hf.setClouds(parser.nextText().trim());

                        }

                        if(parser.getName().equals("icon_url"))
                            hf.setIconUrl(parser.nextText());

                        if(parser.getName().equals("wspd")) {
                            parser.nextTag();
                            if(parser.getName().equals("english"))
                                hf.setWindSpeed(parser.nextText());
                        }

                        if(parser.getName().equals("wdir")) {
                            parser.nextTag();
                            String wdir = "";
                            String wdir_speed = "";

                            if(parser.getName().equals("dir"))
                                wdir = parser.nextText();

                            parser.nextTag();

                            if(parser.getName().equals("degrees"))
                                wdir_speed = parser.nextText();

                            hf.setWindDirection(wdir + " " + wdir_speed);


                        }

                        if(parser.getName().equals("wx")) {
                            hf.setClimateType(parser.nextText());

                        }

                        if(parser.getName().equals("humidity"))
                            hf.setHumidity(parser.nextText());

                        if(parser.getName().equals("feelslike")) {
                            parser.nextTag();
                            if(parser.getName().equals("english"))
                                hf.setFeelsLike(parser.nextText());
                        }

                        if(parser.getName().equals("mslp"))
                            flag = "mslp";


                        if(parser.getName().equals("metric") && flag == "mslp")
                            hf.setPressure(parser.nextText());












                        break;

                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("forecast"))
                            HourlyForecastArrayList.add(hf);
                        break;

                    default:
                        break;

                }
                event = parser.next();
            }
            return HourlyForecastArrayList;


        }


        static public String parseCurrentTemp(InputStream in) throws XmlPullParserException, IOException {


            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");



            String flag = null;

            String temp = "";


            int event = parser.getEventType();

            while(event!=XmlPullParser.END_DOCUMENT)
            {
                switch (event)
                {
                    case XmlPullParser.START_TAG:





                        if(parser.getName().equals("forecast")){

                        }



                        if(parser.getName().equals("temp"))
                            flag = "temp";

                        if(parser.getName().equals("english") && flag == "temp")
                        {
                            temp = parser.nextText();
                            return temp;

                        }




                        break;

                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("forecast"))
                            break;

                    default:
                        break;

                }
                event = parser.next();
            }
            return temp;


        }


    }
}
