package com.leo_art.weatherboy.location;

import android.location.Location;
import android.location.LocationListener;

/**
 * Created by bogdan on 4/23/15.
 */
public interface LocationAbstractEngine {
    public void stopLocationListener();

    public void startLocationListener();

    public Location getLocation();

    public void setLocationChangelistener(LocationListener listener);


    public void release();

}
