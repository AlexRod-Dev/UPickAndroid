package com.example.alex.upick.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.alex.upick.Adapters.RecyclerListMusicAdapter;
import com.example.alex.upick.Models.Music;
import com.example.alex.upick.R;

import java.util.ArrayList;
import java.util.List;

public class AddMusicActivity extends AppCompatActivity {

    RecyclerView recyclerFavList;
    RecyclerListMusicAdapter adapter;
    List<Music> favList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_music);
        getSupportActionBar().setTitle("Add Music");


        init();
        createMusics();
        adapter = new RecyclerListMusicAdapter(favList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerFavList.setLayoutManager(mLayoutManager);
        recyclerFavList.setItemAnimator(new DefaultItemAnimator());
        recyclerFavList.setAdapter(adapter);

    }

    private void init() {
        recyclerFavList = findViewById(R.id.recycler_fav_list);
    }

    private void createMusics() {

        Music music = new Music("faleceu imediatamente", "Papironoo", "3:40", "100", R.drawable.img_album2);
        favList.add(music);
        music = new Music("Music 2 ", "Autor 2", "2:40", "35", R.drawable.images);
        favList.add(music);
        music = new Music("Music 3", "Autor 3", "5:40", "30", R.drawable.img_album3);
        favList.add(music);
        music = new Music("Music 4", "Autor 4", "1:20", "27", R.drawable.img_album4);
        favList.add(music);
        music = new Music("Music 5", "Autor 5", "00:40", "22", R.drawable.img_album2);
        favList.add(music);
        music = new Music("Music 6", "Autor 6", "7:30", "9", R.drawable.img_album3);
        favList.add(music);
        music = new Music("Music 7", "Autor 7", "4:20", "2", R.drawable.img_album4);
        favList.add(music);

    }
}
