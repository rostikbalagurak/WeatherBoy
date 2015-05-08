package com.leo_art.weatherboy.utils;

/**
 * Created by bogdan on 4/20/15.
 */
public class Converter {

    public static double farToCel(double far) {
        return ((far - 32) * 5) / 9;
    }

    public static double kelvinToCel(double kel) {
        return kel - 273.15;
    }

}
