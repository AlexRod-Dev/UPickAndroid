package com.example.alex.upick.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alex.upick.R;

public class RegisterActivity extends AppCompatActivity {

    TextView txtLogin,txtUsername,txtEmail,txtPassword;
    Button btnRegister;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle(R.string.string_register);

        init();




        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void init() {
        txtLogin = findViewById(R.id.lb_login);
        txtUsername = findViewById(R.id.txt_username);
        txtEmail = findViewById(R.id.txt_email);
        txtPassword = findViewById(R.id.txt_password);
        btnRegister = findViewById(R.id.btn_register);

    }
}
