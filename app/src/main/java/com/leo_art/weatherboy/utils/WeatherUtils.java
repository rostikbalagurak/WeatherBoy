package com.leo_art.weatherboy.utils;

/**
 * Created by bogdan on 4/23/15.
 */
public class WeatherUtils {

    public static String formForectast(String city, int days) {
        return "http://api.openweathermap.org/data/2.5/forecast/daily?q=".concat(city).concat("&cnt=" + days + "&mode=json");
    }

}
