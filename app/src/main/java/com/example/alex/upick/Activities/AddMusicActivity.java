package com.example.alex.upick.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.example.alex.upick.Adapters.RecyclerAddMusicAdapter;
import com.example.alex.upick.Adapters.RecyclerFavListMusicAdapter;
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
    RecyclerView recyclerAddList;
    RadioButton rbSearch,rbFav;
    List<Favorites> favList = new ArrayList<>();
    List<Music> searchList = new ArrayList<>();
    List<Music> musicList = new ArrayList<>();
    RetrofitInterface myApi;
    Retrofit retrofit;
    Intent i;
    Venue venue;
    DAO operations;
    String track_id;
    Music musicl;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_music);
        getSupportActionBar().setTitle(R.string.string_add_music);


        init();
        btnSearch.setEnabled(false);
        txtSearch.setEnabled(false);

        showFavList();
        venue = new Gson().fromJson(getIntent().getExtras().getString("venue"), Venue.class);

        rbFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFavList();

                txtSearch.setText("");
                searchList.clear();

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerAddList.setLayoutManager(mLayoutManager);
                recyclerAddList.setItemAnimator(new DefaultItemAnimator());

                recyclerAddList.setAdapter(new RecyclerFavListMusicAdapter(musicList, new RecyclerFavListMusicAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Music music) {

                        musicl = music;

                        builder = new AlertDialog.Builder(AddMusicActivity.this);
                        builder.setTitle("Deseja adicionar aos favoritos?")
                                .setMessage(music.getNome() +" \n " + music.getAutor())
                                .setPositiveButton("Favorites", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        if(operations.existFav(musicl.getTrack_id())){

                                        }else{
                                            Favorites fav = new Favorites(0,musicl.getTrack_id(),musicl.getUser_id(),"",musicl.getAutor(),musicl.getNome());

                                            operations.AddFavorites(fav);
                                            Toast.makeText(getApplicationContext(),"Musica adicionada aos favoritos", Toast.LENGTH_LONG).show();

                                        }


                                    }
                                })
                                .setNegativeButton("Queue", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setIcon(R.drawable.ic_icon_fav)
                                .show();

                    }
                }));

                btnSearch.setEnabled(false);
                txtSearch.setEnabled(false);

            }
        });

        rbSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchList.clear();

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerAddList.setLayoutManager(mLayoutManager);
                recyclerAddList.setItemAnimator(new DefaultItemAnimator());
                recyclerAddList.setAdapter(new RecyclerAddMusicAdapter(searchList, new RecyclerAddMusicAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Music music) {


                        musicl = music;

                        if(operations.existFav(musicl.getTrack_id())){
                            Toast.makeText(getApplicationContext(),R.string.string_already_favorites,Toast.LENGTH_LONG).show();

                        }else {

                            builder = new AlertDialog.Builder(AddMusicActivity.this);
                            builder.setTitle("Deseja adicionar aos favoritos?")
                                    .setMessage(music.getNome() + " \n " + music.getAutor())
                                    .setPositiveButton("Favorites", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            if (operations.existFav(musicl.getTrack_id())) {


                                            } else {
                                                Favorites fav = new Favorites(0, musicl.getTrack_id(), musicl.getUser_id(), "", musicl.getAutor(), musicl.getNome());

                                                operations.AddFavorites(fav);
                                                Toast.makeText(getApplicationContext(), R.string.string_favorite_added, Toast.LENGTH_LONG).show();

                                            }


                                        }
                                    })
                                    .setNegativeButton("Queue", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                                //adicionar musica a queue
                                        }
                                    })
                                    .setIcon(R.drawable.ic_icon_fav)
                                    .show();
                        }
                    }
                }));

                btnSearch.setEnabled(true);
                txtSearch.setEnabled(true);
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
        musicList.clear();

        for(int i = 0 ; i< favList.size();i++){

            track_id = favList.get(i).getTrack_id();

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


                        Music music = new Music(name,artistName,time,"0",url,track_id,LoginActivity.loggedUserId);
                        musicList.add(music);

                    }


                    if(musicList.size()==favList.size()){

                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerAddList.setLayoutManager(mLayoutManager);
                        recyclerAddList.setItemAnimator(new DefaultItemAnimator());
                        recyclerAddList.setAdapter(new RecyclerFavListMusicAdapter(musicList, new RecyclerFavListMusicAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Music music) {



                                builder = new AlertDialog.Builder(AddMusicActivity.this);
                                builder.setTitle("adicionar à lista de reprodução?")
                                        .setMessage(music.getNome() +" \n " + music.getAutor())
                                        .setPositiveButton(R.string.string_yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                Call<String> mService = myApi.addToQueue(musicl.getTrack_id(),musicl.getUser_id(),venue.getId(), "application/json", LoginActivity.auth_key);
                                                mService.enqueue(new Callback<String>() {


                                                    @Override
                                                    public void onResponse(Call<String> call, Response<String> response) {

                                                        if(response.body().equals("success")){
                                                            Toast.makeText(getApplicationContext(),"Musica adicionada à lista de reprodução", Toast.LENGTH_LONG).show();

                                                        }else{
                                                            Toast.makeText(getApplicationContext(),"Não foi possivel adicionar", Toast.LENGTH_LONG).show();

                                                        }

                                                    }

                                                    @Override
                                                    public void onFailure(Call<String> call, Throwable t) {

                                                    }
                                                });


                                            }
                                        })
                                        .setNegativeButton(R.string.string_no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {




                                            }
                                        })
                                        .setIcon(R.drawable.ic_fav_add_dark)
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

    private void search(String text) {


        if(rbSearch.isChecked()) {


            Call<JsonObject> mService = myApi.getSearch(text, "application/json", LoginActivity.auth_key);
            mService.enqueue(new Callback<JsonObject>() {


                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                    if (response.body().getAsJsonObject().get("tracks").getAsJsonObject().get("items").getAsJsonArray().size() >= 5) {

                        for (int i = 0; i < 7; i++) {

                            String name = response.body().getAsJsonObject().get("tracks").getAsJsonObject().get("items").getAsJsonArray().get(i).getAsJsonObject().get("name").getAsString();
                            String autor = response.body().getAsJsonObject().get("tracks").getAsJsonObject().get("items").getAsJsonArray().get(i).getAsJsonObject().get("artists").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
                            String timems = response.body().getAsJsonObject().get("tracks").getAsJsonObject().get("items").getAsJsonArray().get(i).getAsJsonObject().get("duration_ms").getAsString();
                            String img = response.body().getAsJsonObject().get("tracks").getAsJsonObject().get("items").getAsJsonArray().get(i).getAsJsonObject().get("album").getAsJsonObject().get("images").getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString();
                            track_id = response.body().getAsJsonObject().get("tracks").getAsJsonObject().get("items").getAsJsonArray().get(i).getAsJsonObject().get("id").getAsString();
                            String time = milisecsToTime(timems);

                            Music music = new Music(name, autor, time, "0", img,track_id,LoginActivity.loggedUserId);
                            searchList.add(music);

                            if (searchList.size() == 7) {

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerAddList.setLayoutManager(mLayoutManager);
                                recyclerAddList.setItemAnimator(new DefaultItemAnimator());
                                recyclerAddList.setAdapter(new RecyclerAddMusicAdapter(searchList, new RecyclerAddMusicAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(Music music) {

                                        musicl = music;


                                            builder = new AlertDialog.Builder(AddMusicActivity.this);
                                            builder.setTitle("adicionar onde")
                                                    .setMessage(music.getNome() +" \n " + music.getAutor())
                                                    .setPositiveButton(R.string.string_favorites, new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {

                                                            if(operations.existFav(musicl.getTrack_id())){
                                                                Toast.makeText(getApplicationContext(),R.string.string_already_favorites,Toast.LENGTH_LONG).show();

                                                            }else{
                                                                Favorites fav = new Favorites(0,musicl.getTrack_id(),musicl.getUser_id(),"",musicl.getAutor(),musicl.getNome());

                                                                operations.AddFavorites(fav);
                                                                Toast.makeText(getApplicationContext(),R.string.string_favorite_added, Toast.LENGTH_LONG).show();

                                                            }


                                                        }
                                                    })
                                                    .setNegativeButton("Queue", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {

                                                            Call<String> mService = myApi.addToQueue(musicl.getTrack_id(),musicl.getUser_id(),venue.getId(), "application/json", LoginActivity.auth_key);
                                                            mService.enqueue(new Callback<String>() {


                                                                @Override
                                                                public void onResponse(Call<String> call, Response<String> response) {

                                                                if(response.body().equals("success")){
                                                                    Toast.makeText(getApplicationContext(),"Musica adicionada à lista de reprodução", Toast.LENGTH_LONG).show();

                                                                }else{
                                                                    Toast.makeText(getApplicationContext(),"Não foi possivel adicionar", Toast.LENGTH_LONG).show();

                                                                }

                                                                }

                                                                @Override
                                                                public void onFailure(Call<String> call, Throwable t) {

                                                                }
                                                            });


                                                        }
                                                    })
                                                    .setIcon(R.drawable.ic_fav_add_dark)
                                                    .show();





                                    }
                                }));
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
        recyclerAddList = findViewById(R.id.recycler_add_list);
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
        i = new Intent(AddMusicActivity.this, VenueActivity.class);
        Bundle extras = new Bundle();
        extras.putString("venue", new Gson().toJson(venue));
        i.putExtras(extras);
        startActivity(i);
        finish();
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
