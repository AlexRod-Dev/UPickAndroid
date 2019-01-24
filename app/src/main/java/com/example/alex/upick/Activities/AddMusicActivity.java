package com.example.alex.upick.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.alex.upick.Adapters.RecyclerAddMusicAdapter;
import com.example.alex.upick.Interfaces.RetrofitClient;
import com.example.alex.upick.Interfaces.RetrofitInterface;
import com.example.alex.upick.Models.CurrentSong;
import com.example.alex.upick.Models.Music;
import com.example.alex.upick.R;
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
    List<Music> favList = new ArrayList<>();
    List<Music> searchList = new ArrayList<>();
    RetrofitInterface myApi;
    Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_music);
        getSupportActionBar().setTitle(R.string.string_add_music);


        init();

        rbFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchList.clear();
                search(txtSearch.getText().toString());


            }
        });

    }

    private void search(String text) {

        Call<JsonObject> mService = myApi.getSearch(text, "application/json", LoginActivity.auth_key);
        mService.enqueue(new Callback<JsonObject>() {


            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                if (response.body().getAsJsonObject().get("tracks").getAsJsonObject().get("items").getAsJsonArray().size() >= 5) {

                    for (int i = 0; i < 5; i++) {

                        String name = response.body().getAsJsonObject().get("tracks").getAsJsonObject().get("items").getAsJsonArray().get(i).getAsJsonObject().get("name").getAsString();
                        String autor = response.body().getAsJsonObject().get("tracks").getAsJsonObject().get("items").getAsJsonArray().get(i).getAsJsonObject().get("artists").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
                        String time = response.body().getAsJsonObject().get("tracks").getAsJsonObject().get("items").getAsJsonArray().get(i).getAsJsonObject().get("duration_ms").getAsString();
                        String img = response.body().getAsJsonObject().get("tracks").getAsJsonObject().get("items").getAsJsonArray().get(i).getAsJsonObject().get("album").getAsJsonObject().get("images").getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString();


                        Music music = new Music(name, autor, time, "0", img);
                        searchList.add(music);

                        if (searchList.size() == 5) {
                            adapter = new RecyclerAddMusicAdapter(searchList);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerFavList.setLayoutManager(mLayoutManager);
                            recyclerFavList.setItemAnimator(new DefaultItemAnimator());
                            recyclerFavList.setAdapter(adapter);

                        }

                    }

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });


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

}
