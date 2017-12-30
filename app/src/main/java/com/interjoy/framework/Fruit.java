package com.interjoy.framework;

import android.graphics.drawable.Drawable;

public class Fruit {

    private String name;
    private String fruitinfo;
    private Drawable fruitBmp;
    private int imageId;
    private long dbid;
    public Fruit(String name, Drawable fruitBmp, long dbid){
        this.name=name;
        this.fruitBmp=fruitBmp;
        this.dbid=dbid;
    }
    public Fruit(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public Fruit(String name, String fruitinfo , int imageId) {
        this.name = name;
        this.fruitinfo=fruitinfo;
        this.imageId = imageId;
    }
    public Fruit(){

    }
    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

}