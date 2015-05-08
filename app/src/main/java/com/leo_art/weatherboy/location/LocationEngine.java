package com.leo_art.weatherboy.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by bogdan on 4/23/15.
 */
public class LocationEngine implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener, LocationAbstractEngine {

    private static final String TAG = "LocationGoogleEngine";

    protected static LocationEngine mInstance;

    private final Context context;

    private LocationListener mLocalLocationListener;

    public static LocationEngine getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LocationEngine(context.getApplicationContext());
        }
        return mInstance;
    }

    private LocationEngine(Context context) {
        this.context = context;
        buildGoogleApiClient();
    }


    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void stopLocationListener() {
        Log.e(TAG, "stop google location listener");
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void startLocationListener() {
        Log.e(TAG, "start google location listener ");
        Log.e(TAG, "connected = " + mGoogleApiClient.isConnected() + ", connecting = " + mGoogleApiClient.isConnecting());
        if (mGoogleApiClient.isConnected() && mLastLocation == null)
            mGoogleApiClient.reconnect();
        else
            mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
//        mGoogleApiClient.isConnected();
//        mGoogleApiClient.isConnecting();
        Log.e(TAG, "ON CONNECTED" + "connected = " + mGoogleApiClient.isConnected() + ", connecting = " + mGoogleApiClient.isConnecting());
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLocalLocationListener != null)
            mLocalLocationListener.onLocationChanged(mLastLocation);
        //startLocationListener();
        if (mLastLocation != null) {
            //Toast.makeText(context, "GOOGLE LOCATION NOW IS GOOD", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "ERROR GOOGLE LOCATION", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.e(TAG, "Connection suspended " + cause);
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        synchronized (mLastLocation) {
            mLastLocation = location;
            if (mLocalLocationListener != null)
                mLocalLocationListener.onLocationChanged(location);
        }
        //  AppCardApp.getInstance().nearByAndStoresCanBeUpdated = false;
        // locationClient.removeLocationUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "connection failed" + connectionResult);

    }

    public Location getLocation() {
        return mLastLocation;
    }

    public LatLng getLatlng() {
        if (mLastLocation == null) return null;
        return new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
    }

    public String getCityName() {
        String cityName = "";
        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();

            Geocoder gcd = new Geocoder(context, Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(latitude, longitude, 1);
                if (addresses != null) {
                    if (addresses.size() > 0)
                        cityName = addresses.get(0).getLocality();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cityName;
    }

    @Override
    public void setLocationChangelistener(LocationListener listener) {
        mLocalLocationListener = listener;
    }

    @Override
    public void release() {
        stopLocationListener();
        mGoogleApiClient = null;
        mInstance = null;
    }

}
