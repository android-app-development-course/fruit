package com.interjoy.framework;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Fruit {

    private String name;
    private String fruitinfo;
    private byte[] fruitBmp;
    private int imageId;
    private long dbid;
    public Fruit(String name, byte[] fruitBmp, long dbid,String fruitinfo){
        this.name=name;
        this.fruitBmp=fruitBmp;
        this.dbid=dbid;
        this.fruitinfo=fruitinfo;
    }
    public Fruit(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public Fruit(String name, int imageId, String fruitinfo ) {
        this.name = name;
        this.fruitinfo=fruitinfo;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getFruitinfo(){return fruitinfo;}

    public byte[] getFruitBmp(){return fruitBmp;}

}