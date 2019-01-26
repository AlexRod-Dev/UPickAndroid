package com.example.alex.upick.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.alex.upick.R;

import java.util.Locale;

public class ConfigurationsActivity extends AppCompatActivity {

    Spinner spinLanguage;
    CheckBox cbNews,cbNotifications;
    String[] arraySpin = new String[]{"English", "Português"};
    SharedPreferences prefs;
    Intent i;
    Locale locale;
    SharedPreferences.Editor editor;
    AlertDialog.Builder builder;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurations);
        getSupportActionBar().setTitle(R.string.string_configurations);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        init();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();
        locale = getResources().getConfiguration().locale;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.xml.spinner_item, arraySpin);
        adapter.setDropDownViewResource(R.xml.spinner_item);
        spinLanguage.setAdapter(adapter);

        setAtributes();


        spinLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("English")) {


                    if (locale.getLanguage().equals("en")) {


                    } else {

                        builder = new AlertDialog.Builder(ConfigurationsActivity.this);
                        builder.setTitle("Alterar Idioma?")
                                .setMessage("Tem a certeza que quer alterar o idioma agora?")
                                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        editor.putString("language", "en");
                                        editor.apply();

                                        updateLanguage(getApplicationContext(), "en");

                                        restartapp();

                                    }
                                })
                                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        setAtributes();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }


                } else if (selectedItem.equals("Português")) {

                    if (locale.getLanguage().equals("pt")) {


                    } else {

                        builder = new AlertDialog.Builder(ConfigurationsActivity.this);
                        builder.setTitle("Change Language?")
                                .setMessage("Are you sure you want to change Language?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        editor.putString("language", "pt");
                                        editor.apply();

                                        updateLanguage(getApplicationContext(), "pt");

                                        restartapp();

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        setAtributes();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    }


                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cbNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbNews.isChecked()) {
                    editor.putBoolean("news", true);
                    editor.apply();
                } else {
                    editor.putBoolean("news", false);
                    editor.apply();
                }
            }
        });

        cbNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbNotifications.isChecked()) {
                    editor.putBoolean("notifications", true);
                    editor.apply();
                } else {
                    editor.putBoolean("notifications", false);
                    editor.apply();
                }
            }
        });

    }

    private void setAtributes() {
        if (prefs.getBoolean("news", true)) {
            cbNews.setChecked(true);
        } else {
            cbNews.setChecked(false);
        }

        if (locale.getLanguage().equals("en")) {
            spinLanguage.setSelection(0);

        } else {
            spinLanguage.setSelection(1);
        }

        if(prefs.getBoolean("notifications",true)){
            cbNotifications.setChecked(true);
        }else{
            cbNotifications.setChecked(false);
        }


    }


    private void restartapp() {
        i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(i);
    }


    public static void updateLanguage(Context ctx, String lang) {

        Configuration cfg = new Configuration();
        if (!TextUtils.isEmpty(lang))
            cfg.locale = new Locale(lang);
        else
            cfg.locale = Locale.getDefault();


        ctx.getResources().updateConfiguration(cfg, null);
    }


    private void init() {
        spinLanguage = findViewById(R.id.spin_language);
        cbNews = findViewById(R.id.cb_news);
        cbNotifications = findViewById(R.id.cb_notification);

    }
    @Override
    public void onBackPressed()
    {
        i = new Intent(ConfigurationsActivity.this, MenuActivity.class);
        startActivity(i);
        finish();
    }


}
