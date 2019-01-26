package com.example.alex.upick.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.alex.upick.Adapters.RecyclerAddMusicAdapter;
import com.example.alex.upick.Interfaces.RetrofitClient;
import com.example.alex.upick.Interfaces.RetrofitInterface;
import com.example.alex.upick.Models.DAO;
import com.example.alex.upick.Models.Favorites;
import com.example.alex.upick.Models.Music;
import com.example.alex.upick.Models.Venue;
import com.example.alex.upick.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddMusicActivity extends AppCompatActivity {
    EditText txtSearch;
    Button btnSearch;
    RecyclerView recyclerFavList;
    RecyclerAddMusicAdapter adapter;
    RadioButton rbSearch,rbFav;
   // List<Music> favList = new ArrayList<>();
    List<Favorites> favList = new ArrayList<>();
    List<Music> searchList = new ArrayList<>();
    List<Music> musicList = new ArrayList<>();
    RetrofitInterface myApi;
    Retrofit retrofit;
    Intent i;
    Venue venue;
    DAO operations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_music);
        getSupportActionBar().setTitle(R.string.string_add_music);


        init();

        showFavList();
        venue = new Gson().fromJson(getIntent().getExtras().getString("venue"), Venue.class);

        rbFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFavList();

                txtSearch.setText("");
                searchList.clear();
                adapter = new RecyclerAddMusicAdapter(searchList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerFavList.setLayoutManager(mLayoutManager);
                recyclerFavList.setItemAnimator(new DefaultItemAnimator());
                recyclerFavList.setAdapter(adapter);


            }
        });

        rbSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchList.clear();
                adapter = new RecyclerAddMusicAdapter(searchList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerFavList.setLayoutManager(mLayoutManager);
                recyclerFavList.setItemAnimator(new DefaultItemAnimator());
                recyclerFavList.setAdapter(adapter);
            }
        });



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchList.clear();
                if(!txtSearch.getText().toString().equals("")){
                    search(txtSearch.getText().toString());
                    btnSearch.setEnabled(false);
                }



            }
        });

    }

    private void showFavList() {

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
                        adapter = new RecyclerAddMusicAdapter(musicList);
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

    private void search(String text) {


        if(rbSearch.isChecked()) {


            Call<JsonObject> mService = myApi.getSearch(text, "application/json", LoginActivity.auth_key);
            mService.enqueue(new Callback<JsonObject>() {


                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                    if (response.body().getAsJsonObject().get("tracks").getAsJsonObject().get("items").getAsJsonArray().size() >= 5) {

                        for (int i = 0; i < 5; i++) {

                            String name = response.body().getAsJsonObject().get("tracks").getAsJsonObject().get("items").getAsJsonArray().get(i).getAsJsonObject().get("name").getAsString();
                            String autor = response.body().getAsJsonObject().get("tracks").getAsJsonObject().get("items").getAsJsonArray().get(i).getAsJsonObject().get("artists").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
                            String timems = response.body().getAsJsonObject().get("tracks").getAsJsonObject().get("items").getAsJsonArray().get(i).getAsJsonObject().get("duration_ms").getAsString();
                            String img = response.body().getAsJsonObject().get("tracks").getAsJsonObject().get("items").getAsJsonArray().get(i).getAsJsonObject().get("album").getAsJsonObject().get("images").getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString();

                            String time = milisecsToTime(timems);

                            Music music = new Music(name, autor, time, "0", img,LoginActivity.loggedUserId);
                            searchList.add(music);

                            if (searchList.size() == 5) {
                                adapter = new RecyclerAddMusicAdapter(searchList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerFavList.setLayoutManager(mLayoutManager);
                                recyclerFavList.setItemAnimator(new DefaultItemAnimator());
                                recyclerFavList.setAdapter(adapter);
                                btnSearch.setEnabled(true);
                            }

                        }

                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    btnSearch.setEnabled(true);
                }
            });

        }
    }

    private void init() {
        recyclerFavList = findViewById(R.id.recycler_fav_list);
        txtSearch = findViewById(R.id.txt_search);
        btnSearch = findViewById(R.id.btn_search);
        rbSearch = findViewById(R.id.rb_search);
        rbFav = findViewById(R.id.rb_fav);
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

    @Override
    public void onBackPressed() {
       //nao fazer nada
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                i = new Intent(AddMusicActivity.this, VenueActivity.class);
                Bundle extras = new Bundle();
                extras.putString("venue", new Gson().toJson(venue));
                i.putExtras(extras);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
