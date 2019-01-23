package com.example.alex.upick.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.alex.upick.Adapters.RecyclerAddMusicAdapter;
import com.example.alex.upick.Models.Music;
import com.example.alex.upick.R;

import java.util.ArrayList;
import java.util.List;

public class AddMusicActivity extends AppCompatActivity {

    RecyclerView recyclerFavList;
    RecyclerAddMusicAdapter adapter;
    List<Music> favList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_music);
        getSupportActionBar().setTitle(R.string.string_add_music);


        init();

        adapter = new RecyclerAddMusicAdapter(favList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerFavList.setLayoutManager(mLayoutManager);
        recyclerFavList.setItemAnimator(new DefaultItemAnimator());
        recyclerFavList.setAdapter(adapter);

    }

    private void init() {
        recyclerFavList = findViewById(R.id.recycler_fav_list);
    }

}
