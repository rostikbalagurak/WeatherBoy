package com.leo_art.weatherboy.model;

import java.io.Serializable;

public class Hero implements Serializable{
    public enum Sex {MALE, FEMALE}

    private Sex sex;
    private String name;
    private String imageTypeVeryCold;
    private String imageTypeCold;
    private String imageTypeCool;
    private String imageTypeNormal;
    private String imageTypeWarm;
    private String imageTypeHot;
    private boolean isPaid;
    private double price;

    public Hero(String name, String imageSrc){
        this.name = name;
        this.imageTypeCold = imageSrc;
    }

    public Hero(){}

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageTypeVeryCold() {
        return imageTypeVeryCold;
    }

    public void setImageTypeVeryCold(String imageTypeVeryCold) {
        this.imageTypeVeryCold = imageTypeVeryCold;
    }

    public String getImageTypeCold() {
        return imageTypeCold;
    }

    public void setImageTypeCold(String imageTypeCold) {
        this.imageTypeCold = imageTypeCold;
    }

    public String getImageTypeCool() {
        return imageTypeCool;
    }

    public void setImageTypeCool(String imageTypeCool) {
        this.imageTypeCool = imageTypeCool;
    }

    public String getImageTypeNormal() {
        return imageTypeNormal;
    }

    public void setImageTypeNormal(String imageTypeNormal) {
        this.imageTypeNormal = imageTypeNormal;
    }

    public String getImageTypeWarm() {
        return imageTypeWarm;
    }

    public void setImageTypeWarm(String imageTypeWarm) {
        this.imageTypeWarm = imageTypeWarm;
    }

    public String getImageTypeHot() {
        return imageTypeHot;
    }

    public void setImageTypeHot(String imageTypeHot) {
        this.imageTypeHot = imageTypeHot;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
