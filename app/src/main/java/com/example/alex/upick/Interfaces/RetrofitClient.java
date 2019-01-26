package com.example.alex.upick.Interfaces;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit ourInstance;

    public static Retrofit getInstance() {
        if(ourInstance==null)
            ourInstance = new Retrofit.Builder()
                    //10.0.2.2  -> ip para emulador//
                    //192.168.0.101 -> quarto//
                    //192.168.1.xxx -> sala//
                    .baseUrl("http://192.168.0.101:8888/upickweb/api/web/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

        return ourInstance;
    }

    private RetrofitClient() {
    }
}
