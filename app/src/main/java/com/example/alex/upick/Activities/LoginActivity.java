package com.example.alex.upick.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alex.upick.R;

public class LoginActivity extends AppCompatActivity {

    TextView txtRegister;
    Button btnLogin;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");

        init();

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(LoginActivity.this,MenuActivity.class);
                startActivity(i);
                finish();
            }
        });


    }

    private void init() {

        txtRegister = findViewById(R.id.lb_register);
        btnLogin = findViewById(R.id.btn_register);

    }
}
