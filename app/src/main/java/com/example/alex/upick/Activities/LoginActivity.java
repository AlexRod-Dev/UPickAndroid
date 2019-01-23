package com.example.alex.upick.Activities;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.upick.Interfaces.RetrofitClient;
import com.example.alex.upick.Interfaces.RetrofitInterface;
import com.example.alex.upick.Models.News;
import com.example.alex.upick.Models.User;
import com.example.alex.upick.R;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    TextView txtRegister;
    EditText txtPassword;
    EditText txtUserName;
    Button btnLogin;
    ImageView imgLogo;
    Intent i;
    RetrofitInterface myApi;

    byte[] data = null;
    String namePass;
    public static String auth_key = null;
    public static int userId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle(R.string.string_login);


        init();


        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotation_animation);
                imgLogo.startAnimation(animation);

                Retrofit retrofit = RetrofitClient.getInstance();
                myApi = retrofit.create(RetrofitInterface.class);

                namePass = txtUserName.getText() + ":" + txtPassword.getText();


                try {
                    data = namePass.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String encodedString = Base64.encodeToString(data, Base64.NO_WRAP);
                String authorization = "Basic " + encodedString;
                String accept = "application/json";


                Call<User> mService = myApi.getLogin(txtUserName.getText().toString(), accept, authorization);
                mService.enqueue(new Callback<User>() {


                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {


                        if (response.body() != null) {

                            auth_key = "Bearer "+response.body().getAuth_key();
                            userId = response.body().getId();
                            i = new Intent(LoginActivity.this,MenuActivity.class);
                            startActivity(i);
                            finish();


                        }else{
                            Toast.makeText(getApplicationContext(),"Username or password wrong",Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),R.string.string_verify_connection,Toast.LENGTH_LONG).show();
                    }


                });
            }
        });
    }






    private void init() {

        txtRegister = findViewById(R.id.lb_register);
        btnLogin = findViewById(R.id.btn_register);
        txtPassword = findViewById(R.id.txt_password);
        txtUserName = findViewById(R.id.txt_username);
        imgLogo = findViewById(R.id.img_logo);
    }
}

