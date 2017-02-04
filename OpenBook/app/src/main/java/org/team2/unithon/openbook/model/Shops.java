package org.team2.unithon.openbook.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HunJin on 2017-02-04.
 */

public class Shops {
    @SerializedName("shop_id")
    int shop_id;

    @SerializedName("shop_name")
    String shop_name;

    @SerializedName("address")
    String address;

    @SerializedName("image_url")
    String image_url;

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
