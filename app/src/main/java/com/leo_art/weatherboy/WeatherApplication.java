package com.leo_art.weatherboy;

import android.app.Application;

import com.leo_art.weatherboy.networkUtils.RequestManager;

public class WeatherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RequestManager.getInstance(this);
    }
}
