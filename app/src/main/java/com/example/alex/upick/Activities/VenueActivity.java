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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alex.upick.Adapters.RecyclerListMusicAdapter;
import com.example.alex.upick.Adapters.ViewPagerAdapter;
import com.example.alex.upick.Fragments.TokenDialogFragment;
import com.example.alex.upick.Fragments.VenueDialogFragment;
import com.example.alex.upick.Interfaces.RetrofitClient;
import com.example.alex.upick.Interfaces.RetrofitInterface;
import com.example.alex.upick.Models.Music;
import com.example.alex.upick.Models.News;
import com.example.alex.upick.Models.Queue;
import com.example.alex.upick.Models.Venue;
import com.example.alex.upick.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VenueActivity extends AppCompatActivity {
    FloatingActionButton fabAddMusic;
    ImageView btnFav;
    TextView txtMarquee;
    Intent i;
    RecyclerView recyclerListMusic;
    RecyclerListMusicAdapter adapter;
    List<Music> musicList = new ArrayList<>();
    SharedPreferences prefs;
    String jsonVenue;

    RetrofitInterface myApi;
    int count = 0;
    Venue venue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue);


        Bundle extras = getIntent().getExtras();

        jsonVenue = extras.getString("venue");

        venue = new Gson().fromJson(jsonVenue, Venue.class);



        getSupportActionBar().setTitle(venue.getName());




        createMusics();
        init();


        //init API

        Retrofit retrofit = RetrofitClient.getInstance();
        myApi = retrofit.create(RetrofitInterface.class);

        Call<ArrayList<News>> mService = myApi.getNews(venue.getId(),"application/json",LoginActivity.auth_key);
        mService.enqueue(new Callback<ArrayList<News>>() {
            @Override
            public void onResponse(Call<ArrayList<News>> call, final Response<ArrayList<News>> response) {


                if(response.body()!=null){
                    for(int i = 0; i<response.body().size(); i++) {

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

        getQueue();

        txtMarquee.setSelected(true);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        if(prefs.getBoolean("news",true)){
            txtMarquee.setVisibility(View.VISIBLE);
            txtMarquee.setEnabled(true);

        }else{
            txtMarquee.setVisibility(View.GONE);
            txtMarquee.setEnabled(false);
        }




        adapter = new RecyclerListMusicAdapter(musicList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerListMusic.setLayoutManager(mLayoutManager);
        recyclerListMusic.setItemAnimator(new DefaultItemAnimator());
        recyclerListMusic.setAdapter(adapter);

        fabAddMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  i = new Intent(VenueActivity.this,AddMusicActivity.class);
                 startActivity(i);
            }
        });


        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnFav.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_icon_not_fav).getConstantState()){

                    btnFav.setImageResource(R.drawable.ic_icon_fav);
                }else{

                    btnFav.setImageResource(R.drawable.ic_icon_not_fav);
                }

            }
        });

    }

    private void getQueue() {

        Call<ArrayList<Queue>> mService = myApi.getQueue(venue.getId(),"application/json",LoginActivity.auth_key);
        mService.enqueue(new Callback<ArrayList<Queue>>() {
            @Override
        public void onResponse(Call<ArrayList<Queue>> call, Response<ArrayList<Queue>> response) {
                if(response.body()!=null){
                    response.body().size();


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
    }

    private void createMusics() {

        Music music = new Music("imediatamente", "Papironoo", "3:40", "100", R.drawable.img_album2);
        musicList.add(music);
        music = new Music("Music 2 ", "Autor 2", "2:40", "35", R.drawable.images);
        musicList.add(music);
        music = new Music("Music 3", "Autor 3", "5:40", "30", R.drawable.img_album3);
        musicList.add(music);
        music = new Music("Music 4", "Autor 4", "1:20", "27", R.drawable.img_album4);
        musicList.add(music);
        music = new Music("Music 5", "Autor 5", "00:40", "22", R.drawable.img_album2);
        musicList.add(music);
        music = new Music("Music 6", "Autor 6", "7:30", "9", R.drawable.img_album3);
        musicList.add(music);
        music = new Music("Music 7", "Autor 7", "4:20", "2", R.drawable.img_album4);
        musicList.add(music);

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
                i = new Intent(VenueActivity.this,MenuActivity.class);
                startActivity(i);
                finish();
                return true;

            case R.id.menu_info:

                FragmentManager fm = getSupportFragmentManager();
                VenueDialogFragment vdf = new VenueDialogFragment();
                vdf.show(fm,"TAG");
        }
        return super.onOptionsItemSelected(item);
    }
}
