package com.leo_art.weatherboy;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.leo_art.weatherboy.location.LocationEngine;
import com.leo_art.weatherboy.model.Settings;
import com.leo_art.weatherboy.networkUtils.RequestManager;
import com.leo_art.weatherboy.utils.DatabaseUtils;

import io.realm.Realm;
import io.realm.RealmResults;

public class WeatherApplication extends Application {
    public static final String TAG = "WeatherApplication";

    private static WeatherApplication instance;

    public static WeatherApplication getInstance(){
        return instance;
    }

    public Settings getSettings(Context context) {
        Realm realm = Realm.getInstance(context);
        return realm.allObjects(Settings.class).first();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RequestManager.getInstance(this);
        LocationEngine.getInstance(this);

        Realm realm = Realm.getInstance(this);
        RealmResults<Settings> settingsResult = realm.allObjects(Settings.class);

        if(settingsResult.size() == 0){ // app running first time
            DatabaseUtils.writeInitialData(this);
            settingsResult = realm.allObjects(Settings.class);
        }

        Settings settings = settingsResult.first();

        if(settings.getCity() == null || settings.getCity().equals("")){
            LocationEngine.getInstance(this).startLocationListener();
        }
    }

    @Override
    public void onLowMemory() {
        Log.e(TAG, "On Low Memory Called!!!");

        RequestManager.getInstance().doRequest().getmRequestQueue().getCache().clear();
    }

    @Override
    public void onTrimMemory(int level) {
        switch (level) {
            case TRIM_MEMORY_COMPLETE:
                Log.w(TAG,
                        "Trim Memory Complete:  the process is not an expendable background process, but the device is "
                                + "running low on memory. Your running process should free up unneeded resources to "
                                + "allow that memory to be used elsewhere.");
                break;

            case TRIM_MEMORY_BACKGROUND:
                Log.w(TAG,
                        "Trim Memory Background:  the process is not an expendable background process, but the device is "
                                + "running low on memory. Your running process should free up unneeded resources to allow that "
                                + "memory to be used elsewhere.");
                break;

            case TRIM_MEMORY_MODERATE:
                Log.w(TAG,
                        "Trim Memory moderate:  the process is not an expendable background process, but the device is "
                                + "running low on memory. Your running process should free up unneeded resources to allow that "
                                + "memory to be used elsewhere.");
                break;

            case TRIM_MEMORY_RUNNING_CRITICAL:
                Log.w(TAG,
                        "Trim memory running critical:  the process is not an expendable background process, but the "
                                + "device is running low on memory. Your running process should free up unneeded resources to allow "
                                + "that memory to be used elsewhere.");
                break;

            case TRIM_MEMORY_RUNNING_LOW:
                Log.w(TAG,
                        " the process is not an expendable background process, but the device is running low on memory. "
                                + "Your running process should free up unneeded resources to allow that"
                                + " memory to be used elsewhere.");
                break;

            case TRIM_MEMORY_UI_HIDDEN:
                Log.w(TAG,
                        "Trim memory UI hidden: the process had been showing a user interface, and is no longer doing so. "
                                + "Large allocations with the UI should be released at this point to allow memory to be better managed.");
                break;

            default:
                break;
        }
        super.onTrimMemory(level);
    }

    @Override
    public void onTerminate() {
        Log.d(TAG, "Terminating WeatherApplication App");
        super.onTerminate();
        Log.d(TAG, "App Terminated.");
    }


}
