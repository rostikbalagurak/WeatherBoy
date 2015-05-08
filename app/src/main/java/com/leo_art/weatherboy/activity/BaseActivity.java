package com.leo_art.weatherboy.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.leo_art.weatherboy.Constants;
import com.leo_art.weatherboy.WeatherApplication;
import com.leo_art.weatherboy.location.LocationEngine;
import com.leo_art.weatherboy.model.Settings;
import com.leo_art.weatherboy.model.Weather;

/**
 * Created by bogdan on 4/23/15.
 */
public class BaseActivity extends FragmentActivity {

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onPause() {
        super.onPause();
        LocationEngine.getInstance(this).stopLocationListener();
    }

    /**
     * checking if network is available
     *
     * @param context
     * @return true/false
     */
    protected boolean isNetworkOn(Context context) {
        ConnectivityManager connMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }
}
