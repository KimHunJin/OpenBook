package org.team2.unithon.openbook.network;

import org.team2.unithon.openbook.utils.StaticServerUrl;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HunJin on 2017-01-16.
 */

public class RestApiBuilder {
    public static RestAPI buildRetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticServerUrl.URL2)
                // Data converter
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build();

        return retrofit.create(RestAPI.class);
    }
}
