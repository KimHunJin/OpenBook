package org.team2.unithon.openbook.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HunJin on 2017-02-04.
 */

public class TestClass {

    @SerializedName("owner_name")
    String owner_name;

    @SerializedName("extra")
    String extra;

    @SerializedName("id")
    String id;

    @SerializedName("x")
    String x;

    @SerializedName("y")
    String y;

    @SerializedName("images")
    String[] images;

    @SerializedName("store_name")
    String store_name;

    @SerializedName("location")
    String locaiton;

    @SerializedName("open_time")
    String open_time;

    @SerializedName("close_time")
    String close_time;

    @SerializedName("phone")
    String phone;


    public String getLocaiton() {
        return locaiton;
    }

    public void setLocaiton(String locaiton) {
        this.locaiton = locaiton;
    }

    public String getOpen_time() {
        return open_time;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public String getClose_time() {
        return close_time;
    }

    public void setClose_time(String close_time) {
        this.close_time = close_time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
