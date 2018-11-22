package com.example.alex.upick;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.alex.upick.Adapters.RecyclerListMusicAdapter;
import com.example.alex.upick.Models.Music;

import java.util.ArrayList;
import java.util.List;

public class EstablishmentActivity extends AppCompatActivity {
    FloatingActionButton fabAddMusic;
    TextView txtMarquee;
    Intent i;
    RecyclerView recyclerListMusic;
    RecyclerListMusicAdapter adapter;
    List<Music> musicList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment);
        getSupportActionBar().setTitle("Coreto Bar");


        recyclerListMusic = findViewById(R.id.recycler_list_music);

        createMusics();
        init();

        txtMarquee.setSelected(true);
        adapter = new RecyclerListMusicAdapter(musicList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerListMusic.setLayoutManager(mLayoutManager);
        recyclerListMusic.setItemAnimator(new DefaultItemAnimator());
        recyclerListMusic.setAdapter(adapter);

        fabAddMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  i = new Intent(EstablishmentActivity.this,AddMusicActivity.class);
                 startActivity(i);
            }
        });


    }

    private void init() {
        fabAddMusic = findViewById(R.id.fab_add_music);
        txtMarquee = findViewById(R.id.txt_marquee);
    }

    private void createMusics() {

        Music music = new Music("faleceu imediatamente", "Papironoo", "3:40", "100", R.drawable.img_album2);
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
        inflater.inflate(R.menu.establishment_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.establishment_exit:
                i = new Intent(EstablishmentActivity.this,MenuActivity.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
