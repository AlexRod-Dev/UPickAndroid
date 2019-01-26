package com.example.alex.upick.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.alex.upick.R;

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
                finish();

            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MenuActivity.this,FavoritesActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MenuActivity.this,ConfigurationsActivity.class);
                startActivity(i);
                finish();

            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MenuActivity.this,AboutActivity.class);
                startActivity(i);
                finish();
            }
        });



    }

    private void init() {

        btnEnter = findViewById(R.id.btn_enter);
        btnConfig = findViewById(R.id.btn_configs);
        btnFav = findViewById(R.id.btn_favorites);
        btnAbout = findViewById(R.id.btn_about);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_exit:
                i = new Intent(MenuActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
