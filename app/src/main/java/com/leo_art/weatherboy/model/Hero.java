package com.leo_art.weatherboy.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Hero extends RealmObject implements Serializable{
    public static final int MALE = 0;
    public static final int FEMALE = 1;

    @PrimaryKey
    private int id;
    private String name;
    private int imageTypeVeryCold;
    private int imageTypeCold;
    private int imageTypeCool;
    private int imageTypeNormal;
    private int imageTypeWarm;
    private int imageTypeHot;
    private int sex;

    private boolean isPaid;
    private double price;

    public Hero(String name, int imageResource){
        this.name = name;
        this.imageTypeCold = imageResource;
    }

    public Hero(String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Hero(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageTypeVeryCold() {
        return imageTypeVeryCold;
    }

    public void setImageTypeVeryCold(int imageTypeVeryCold) {
        this.imageTypeVeryCold = imageTypeVeryCold;
    }

    public int getImageTypeCold() {
        return imageTypeCold;
    }

    public void setImageTypeCold(int imageTypeCold) {
        this.imageTypeCold = imageTypeCold;
    }

    public int getImageTypeCool() {
        return imageTypeCool;
    }

    public void setImageTypeCool(int imageTypeCool) {
        this.imageTypeCool = imageTypeCool;
    }

    public int getImageTypeNormal() {
        return imageTypeNormal;
    }

    public void setImageTypeNormal(int imageTypeNormal) {
        this.imageTypeNormal = imageTypeNormal;
    }

    public int getImageTypeWarm() {
        return imageTypeWarm;
    }

    public void setImageTypeWarm(int imageTypeWarm) {
        this.imageTypeWarm = imageTypeWarm;
    }

    public int getImageTypeHot() {
        return imageTypeHot;
    }

    public void setImageTypeHot(int imageTypeHot) {
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
