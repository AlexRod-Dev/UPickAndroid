package com.example.alex.upick;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MenuActivity extends AppCompatActivity {
    ImageView btnEnter,btnFav,btnConfig,btnAbout;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().setTitle("Menu");

        init();

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MenuActivity.this,EnterActivity.class);
                startActivity(i);

            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MenuActivity.this,FavoritesActivity.class);
                startActivity(i);
            }
        });

        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MenuActivity.this,ConfigurationsActivity.class);
                startActivity(i);

            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MenuActivity.this,AboutActivity.class);
                startActivity(i);

            }
        });



    }

    private void init() {
    btnEnter = findViewById(R.id.btn_enter);
    btnConfig = findViewById(R.id.btn_configs);
    btnFav = findViewById(R.id.btn_favorites);
    btnAbout = findViewById(R.id.btn_about);

    }
}
