package com.example.alex.upick;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EnterActivity extends AppCompatActivity {
    Button btn;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        getSupportActionBar().setTitle("Enter");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getSupportFragmentManager();
                EstablishmentDialogFragment edf = new EstablishmentDialogFragment();
                edf.show(fm,"TAG");
            }
        });


    }

    private void init() {
    btn = findViewById(R.id.btn_coming);
    }
}
