package com.example.alex.upick.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.alex.upick.R;

public class ConfigurationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurations);
        getSupportActionBar().setTitle("Configurations");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
