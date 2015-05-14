package com.leo_art.weatherboy.model;

import com.leo_art.weatherboy.WeatherApplication;

import io.realm.Realm;
import io.realm.RealmObject;

public class Settings extends RealmObject{
    private double longtitude;
    private double latitude;
    private String city;

    private String country;
    private String metric;
    private int currentHeroId;

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public int getCurrentHeroId() {
        return currentHeroId;
    }

    public void setCurrentHeroId(int currentHeroId) {
        this.currentHeroId = currentHeroId;
    }

   // public enum Degree = {CELSIUS, KELVIN}
}
