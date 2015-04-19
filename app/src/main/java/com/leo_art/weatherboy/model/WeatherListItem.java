package com.leo_art.weatherboy.model;

/**
 * Created by Rostik on 03.04.2015.
 */
public class WeatherListItem {
    private String day;
    private String date;
    private String nightTemperature;
    private String dayTemperature;

    public WeatherListItem(String day, String date, String dayTemperature, String nightTemperature) {
        this.day = day;
        this.date = date;
        this.dayTemperature = dayTemperature;
        this.nightTemperature = nightTemperature;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNightTemperature() {
        return nightTemperature;
    }

    public void setNightTemperature(String nightTemperature) {
        this.nightTemperature = nightTemperature;
    }

    public String getDayTemperature() {
        return dayTemperature;
    }

    public void setDayTemperature(String dayTemperature) {
        this.dayTemperature = dayTemperature;
    }
}
