package com.example.alex.upick.Interfaces;

import com.example.alex.upick.Models.CurrentSong;
import com.example.alex.upick.Models.Music;
import com.example.alex.upick.Models.News;
import com.example.alex.upick.Models.Queue;
import com.example.alex.upick.Models.Track;
import com.example.alex.upick.Models.User;
import com.example.alex.upick.Models.Venue;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {


        @GET("venues")
        Call<ArrayList<Venue>> getVenues(@Header("accept") String accept,@Header("authorization") String auth_key);


        @GET("venues/{id}/fetchnews")
        Call<ArrayList<News>> getNews(@Path("id") int id, @Header("accept") String accept,@Header("authorization") String auth_key);

        @GET("queue/{venueid}/getqueue")
        Call<ArrayList<Queue>> getQueue(@Path("venueid") int id, @Header("accept") String accept, @Header("authorization") String auth_key);


        @GET("currentsong/{venueid}/getcurrentsong")
        Call<CurrentSong> getCurrentSong(@Path("venueid") int id, @Header("accept") String accept, @Header("authorization") String auth_key);

        @GET("queue/{trackid}/{userid}/{venueid}/addtoqueue")
        Call<String> addToQueue(@Path("trackid") String track_id, @Path("userid") int user_id, @Path("venueid") int venue_id,@Header("accept") String accept, @Header("authorization") String auth_key);

        @FormUrlEncoded
        @POST("spotify/refresh")
        Call<ArrayList> getToken(@Field("id") int id, @Header("accept") String accept, @Header("authorization") String auth_key);

        @FormUrlEncoded
        @POST("spotify/gettrack")
        Call<JsonObject> getTrack(@Field("track_id") String track_id, @Header("accept") String accept, @Header("authorization") String auth_key);


        @FormUrlEncoded
        @POST("spotify/search")
        Call<JsonObject> getSearch(@Field("search") String search, @Header("accept") String accept, @Header("authorization") String auth_key);



        @FormUrlEncoded
        @POST("user/login")
        //void getLogin(@Field("username") String username, @Header("accept") String acept, @Header("authorization") String encodedString);
        Call<User> getLogin(@Field("username") String username, @Header("accept") String accept, @Header("authorization") String encodedString);


   // @POST("queue")
   // Call<ArrayList<>>
}



