package com.leo_art.weatherboy.model;

/**
 * Created by bogdan on 4/20/15.
 */
public class Status {

    public enum Name {VERY_COLD, COLD, COOL, NORMAL, WARM, HOT}

    public Status(double minTemperature, double maxTemperature, Name name, String image) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.name = name;
        this.image = image;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private double minTemperature;
    private double maxTemperature;
    private Name name;
    private String image;
}
