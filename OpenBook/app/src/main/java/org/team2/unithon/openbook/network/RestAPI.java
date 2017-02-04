package org.team2.unithon.openbook.network;

import org.team2.unithon.openbook.model.GPSPost;
import org.team2.unithon.openbook.model.Test;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by HunJin on 2017-01-16.
 */

public interface RestAPI {

    @GET("shops")
    Observable<GPSPost> getGpsPostObservable();

    @FormUrlEncoded
    @POST("/")
    Call<GPSPost> setToken(
            @FieldMap Map<String , String> setToken
    );

    @FormUrlEncoded
    @POST("map")
    Call<Test> getImages(
            @FieldMap Map<String, String> getImage
    );

    @FormUrlEncoded
    @POST("map/one")
    Call<Test> getOneInfo(
            @FieldMap Map<String, String> getOneInfo
    );

}
