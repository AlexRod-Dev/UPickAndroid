package com.example.alex.upick.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.alex.upick.R;

import java.util.Locale;

public class ConfigurationsActivity extends AppCompatActivity {

    Spinner spinLanguage;
    String[] arraySpin = new String[] {"Escolha a linguagem",
            "English", "Português"};
    SharedPreferences prefs;
    Intent i;
    Locale locale;
    SharedPreferences.Editor editor;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurations);
        getSupportActionBar().setTitle("Configurations");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        init();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.xml.spinner_item, arraySpin);
        adapter.setDropDownViewResource(R.xml.spinner_item);

        spinLanguage.setAdapter(adapter);

        locale = getResources().getConfiguration().locale;

        spinLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("English"))
                {


                    if(locale.getLanguage().equals("en-us")){

                    }else{
                        editor.putString("language", "en-US");
                        editor.apply();

                        updateLanguage(getApplicationContext(),"en");

                        restartapp();
                    }



                }else if(selectedItem.equals("Português")){

                    if(locale.getLanguage().equals("pt-pt")){

                    }else{

                        editor.putString("language", "pt-pt");
                        editor.apply();

                        updateLanguage(getApplicationContext(),"pt");

                        restartapp();
                    }


                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

    }

    private void restartapp() {
        i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }


    public static void updateLanguage(Context ctx, String lang)
    {

        Configuration cfg = new Configuration();
        if (!TextUtils.isEmpty(lang))
            cfg.locale = new Locale(lang);
        else
            cfg.locale = Locale.getDefault();


        ctx.getResources().updateConfiguration(cfg, null);
    }



    private void init() {
        spinLanguage = findViewById(R.id.spin_language);

    }

}
