package com.example.alex.upick.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.alex.upick.Interfaces.RetrofitClient;
import com.example.alex.upick.Interfaces.RetrofitInterface;
import com.example.alex.upick.R;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashActivity extends AppCompatActivity {


    SharedPreferences prefs;
    Retrofit retrofit;
    RetrofitInterface myApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        ImageView imgLogo = findViewById(R.id.img_logo);

        retrofit = RetrofitClient.getInstance();
        myApi = retrofit.create(RetrofitInterface.class);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.rotation_animation);
        imgLogo.startAnimation(animation);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String lang = prefs.getString("language", "pt-PT");
        updateLanguage(this,lang);

        Call<ArrayList> mService = myApi.getToken(LoginActivity.userId, "application/json", LoginActivity.auth_key);
        mService.enqueue(new Callback<ArrayList>() {
            @Override
            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                Intent i = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onFailure(Call<ArrayList> call, Throwable t) {

            }
        });



    }


    public static void updateLanguage(Context ctx, String lang)
    {
        Configuration cfg = new Configuration();
        if (!TextUtils.isEmpty(lang))
            cfg.locale = new Locale(lang );
        else
            cfg.locale = Locale.getDefault();

        ctx.getResources().updateConfiguration(cfg, ctx.getResources().getDisplayMetrics());
    }
}
