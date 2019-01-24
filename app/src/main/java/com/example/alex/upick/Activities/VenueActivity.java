package com.example.alex.upick.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.upick.Adapters.RecyclerListMusicAdapter;
import com.example.alex.upick.Fragments.VenueDialogFragment;
import com.example.alex.upick.Interfaces.RetrofitClient;
import com.example.alex.upick.Interfaces.RetrofitInterface;
import com.example.alex.upick.Models.CurrentSong;
import com.example.alex.upick.Models.Music;
import com.example.alex.upick.Models.News;
import com.example.alex.upick.Models.Queue;
import com.example.alex.upick.Models.Track;
import com.example.alex.upick.Models.Venue;
import com.example.alex.upick.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VenueActivity extends AppCompatActivity {
    FloatingActionButton fabAddMusic;
    ImageView btnFav,imgTrack;
    TextView txtMarquee,lbMusicName,lbArtistName;
    Intent i;
    RecyclerView recyclerListMusic;
    RecyclerListMusicAdapter adapter;
    List<Music> musicList = new ArrayList<>();
    SharedPreferences prefs;
    String jsonVenue;
    String currentSongId;

    Retrofit retrofit;
    RetrofitInterface myApi;
    int count = 0;
    Venue venue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue);


        Bundle extras = getIntent().getExtras();

        if(extras != null){
            jsonVenue = extras.getString("venue");

            venue = new Gson().fromJson(jsonVenue, Venue.class);


            getSupportActionBar().setTitle(venue.getName());
        }




        init();
        refreshSpotToken();
        getCurrentSong();
        getQueue();
        getNews();



        txtMarquee.setSelected(true);
        lbMusicName.setSelected(true);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        if (prefs.getBoolean("news", true)) {
            txtMarquee.setVisibility(View.VISIBLE);
            txtMarquee.setEnabled(true);

        } else {
            txtMarquee.setVisibility(View.GONE);
            txtMarquee.setEnabled(false);
        }




        fabAddMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(VenueActivity.this, AddMusicActivity.class);
                startActivity(i);
            }
        });


        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnFav.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_icon_not_fav).getConstantState()) {

                    btnFav.setImageResource(R.drawable.ic_icon_fav);
                } else {

                    btnFav.setImageResource(R.drawable.ic_icon_not_fav);
                }

            }
        });



    }

    private void getTrack() {

        Call<JsonObject> mService = myApi.getTrack(currentSongId, "application/json", LoginActivity.auth_key);

        mService.enqueue(new Callback<JsonObject>() {


            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if(response.body() != null){
                    lbMusicName.setText(response.body().get("name").getAsString());
                    lbArtistName.setText(response.body().get("artists").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString());
                    String url = response.body().get("album").getAsJsonObject().get("images").getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString();
                    Picasso.get().load(url).into(imgTrack);


                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void refreshSpotToken() {


        Call<ArrayList> mService = myApi.getToken(LoginActivity.userId, "application/json", LoginActivity.auth_key);
        mService.enqueue(new Callback<ArrayList>() {
            @Override
            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {

            }

            @Override
            public void onFailure(Call<ArrayList> call, Throwable t) {

            }
        });

    }

    private void getNews() {

        Call<ArrayList<News>> mService = myApi.getNews(venue.getId(), "application/json", LoginActivity.auth_key);
        mService.enqueue(new Callback<ArrayList<News>>() {
            @Override
            public void onResponse(Call<ArrayList<News>> call, final Response<ArrayList<News>> response) {


                if (response.body() != null) {
                    for (int i = 0; i < response.body().size(); i++) {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                txtMarquee.setText(response.body().get(count).getMessage());
                                count++;

                            }

                        }, 5000);
                    }

                }


            }

            @Override
            public void onFailure(Call<ArrayList<News>> call, Throwable t) {

            }
        });


    }

    private void getCurrentSong() {

        Call<CurrentSong> mService = myApi.getCurrentSong(venue.getId(), "application/json", LoginActivity.auth_key);
        mService.enqueue(new Callback<CurrentSong>() {

            @Override
            public void onResponse(Call<CurrentSong> call, Response<CurrentSong> response) {

                if(response.body() != null) {
                    currentSongId = response.body().getTrack_id();
                    getTrack();
                }

            }

            @Override
            public void onFailure(Call<CurrentSong> call, Throwable t) {

            }
        });

    }

    private void getQueue() {

        Call<ArrayList<Queue>> mService = myApi.getQueue(venue.getId(), "application/json", LoginActivity.auth_key);
        mService.enqueue(new Callback<ArrayList<Queue>>() {
            @Override
            public void onResponse(Call<ArrayList<Queue>> call, Response<ArrayList<Queue>> response) {
                if (response.body() != null) {
                        final int listCount = response.body().size();
                    for(int i = 0; i < listCount;i++){



                        Call<JsonObject> mService = myApi.getTrack(response.body().get(i).getTrack_id(), "application/json", LoginActivity.auth_key);
                            mService.enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                    if(response.body()!=null){
                                        String name = response.body().get("name").getAsString();
                                        String artistName = response.body().get("artists").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
                                        String url = response.body().get("album").getAsJsonObject().get("images").getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString();
                                        String time = response.body().get("duration_ms").getAsString();

                                        Music music = new Music(name,artistName,time,"0",url);
                                        musicList.add(music);

                                    }


                                    if(musicList.size()==listCount){
                                        adapter = new RecyclerListMusicAdapter(musicList);
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                        recyclerListMusic.setLayoutManager(mLayoutManager);
                                        recyclerListMusic.setItemAnimator(new DefaultItemAnimator());
                                        recyclerListMusic.setAdapter(adapter);
                                    }


                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {

                                }
                            });
                    }



                }

            }

            @Override
            public void onFailure(Call<ArrayList<Queue>> call, Throwable t) {

            }
        });

    }

    private void init() {
        fabAddMusic = findViewById(R.id.fab_add_music);
        txtMarquee = findViewById(R.id.txt_marquee);
        btnFav = findViewById(R.id.btn_fav);
        recyclerListMusic = findViewById(R.id.recycler_list_music);
        lbMusicName = findViewById(R.id.lb_music_name);
        lbArtistName = findViewById(R.id.lb_artist_name);
        imgTrack = findViewById(R.id.img_track);
        //init API
        retrofit = RetrofitClient.getInstance();
        myApi = retrofit.create(RetrofitInterface.class);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.exit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_exit:
                i = new Intent(VenueActivity.this, MenuActivity.class);
                startActivity(i);
                finish();
                return true;

            case R.id.menu_info:

                FragmentManager fm = getSupportFragmentManager();
                VenueDialogFragment vdf = new VenueDialogFragment();
                vdf.show(fm, "TAG");
        }
        return super.onOptionsItemSelected(item);
    }
}
