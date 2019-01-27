package com.example.alex.upick.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.alex.upick.Adapters.RecyclerFavListMusicAdapter;
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
    Intent i;
    String track_id;
    Music musicl;
    AlertDialog.Builder builder;
    SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        getSupportActionBar().setTitle(R.string.string_favorites);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        carregarLista();


        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                carregarLista();
                swipeRefresh.setRefreshing(false);
            }
        });



    }

    private void carregarLista() {

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
                        track_id = response.body().get("id").getAsString();
                        String time = milisecsToTime(timems);


                        Music music = new Music(name,artistName,time,"0",url,track_id,LoginActivity.loggedUserId);
                        musicList.add(music);

                    }


                    if(musicList.size()==favList.size()){

                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerFavList.setLayoutManager(mLayoutManager);
                        recyclerFavList.setItemAnimator(new DefaultItemAnimator());




                        recyclerFavList.setAdapter(new RecyclerFavListMusicAdapter(musicList, new RecyclerFavListMusicAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Music music) {

                                musicl = music;

                                builder = new AlertDialog.Builder(FavoritesActivity.this);
                                builder.setTitle(R.string.string_remove_favorites)
                                        .setMessage(music.getNome() +" \n " + music.getAutor())
                                        .setPositiveButton(R.string.string_yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                operations.deleteFavorites(musicl.getTrack_id());

                                                Toast.makeText(getApplicationContext(),R.string.string_removed_favorites, Toast.LENGTH_LONG).show();
                                                carregarLista();
                                            }
                                        })
                                        .setNegativeButton(R.string.string_no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .setIcon(R.drawable.ic_icon_not_fav)
                                        .show();
                            }
                        }));
                    }


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });

        }
    }

    private void init() {
        recyclerFavList = findViewById(R.id.recycler_add_list);
        //init API
        retrofit = RetrofitClient.getInstance();
        myApi = retrofit.create(RetrofitInterface.class);
        operations = new DAO(this);
        swipeRefresh = findViewById(R.id.swipeRefresh);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                i = new Intent(FavoritesActivity.this, MenuActivity.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        i = new Intent(FavoritesActivity.this, MenuActivity.class);
        startActivity(i);
        finish();
    }


}
