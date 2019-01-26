package com.example.alex.upick.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.alex.upick.Adapters.RecyclerFavListMusicAdapter;
import com.example.alex.upick.Adapters.RecyclerListMusicAdapter;
import com.example.alex.upick.Interfaces.RetrofitClient;
import com.example.alex.upick.Interfaces.RetrofitInterface;
import com.example.alex.upick.Models.DAO;
import com.example.alex.upick.Models.Favorites;
import com.example.alex.upick.Models.Music;
import com.example.alex.upick.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FavoritesActivity extends AppCompatActivity {

    RecyclerView recyclerFavList;
    RecyclerFavListMusicAdapter adapter;
    List<Music> musicList = new ArrayList<>();
    List<Favorites> favList = new ArrayList<>();
    DAO operations;
    Retrofit retrofit;
    RetrofitInterface myApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        getSupportActionBar().setTitle(R.string.string_favorites);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        operations = new DAO(this);
        favList = operations.getFavorites();

        for(int i = 0 ; i< favList.size();i++){

            Call<JsonObject> mService = myApi.getTrack(favList.get(i).getTrack_id(), "application/json", LoginActivity.auth_key);
            mService.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    if(response.body()!=null){
                        String name = response.body().get("name").getAsString();
                        String artistName = response.body().get("artists").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
                        String url = response.body().get("album").getAsJsonObject().get("images").getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString();
                        String timems = response.body().get("duration_ms").getAsString();

                        String time = milisecsToTime(timems);


                        Music music = new Music(name,artistName,time,"0",url,LoginActivity.loggedUserId);
                        musicList.add(music);

                    }


                    if(musicList.size()==favList.size()){
                        adapter = new RecyclerFavListMusicAdapter(musicList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerFavList.setLayoutManager(mLayoutManager);
                        recyclerFavList.setItemAnimator(new DefaultItemAnimator());
                        recyclerFavList.setAdapter(adapter);
                    }


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });

        }




    }

    private void init() {
        recyclerFavList = findViewById(R.id.recycler_fav_list);
        //init API
        retrofit = RetrofitClient.getInstance();
        myApi = retrofit.create(RetrofitInterface.class);
    }

    private String milisecsToTime(String milisecs) {


        long min = (Long.parseLong(milisecs) / 1000) / 60;
        long sec =  ((Integer.parseInt(milisecs) / 1000)% 60);

        String secondsStr = Long.toString(sec);
        String secs;

        if (secondsStr.length() >= 2) {
            secs = secondsStr.substring(0, 2);
        } else {
            secs = "0" + secondsStr;
        }

        String minuteStr = Long.toString(min);
        String mins;

        if(minuteStr.length() >=2){
            mins = minuteStr.substring(0,2);
        }else{
            mins = "0" + minuteStr;
        }


        return  mins + " : " + secs;
    }


}
