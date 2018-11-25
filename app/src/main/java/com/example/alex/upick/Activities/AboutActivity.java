package com.example.alex.upick.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.alex.upick.R;

public class AboutActivity extends AppCompatActivity {

    CardView cardAbout,cardInstructions;
    TextView txtAbout,txtInstructions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setTitle("About");


        init();

        cardAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!txtAbout.isShown()){
                    Animation a = AnimationUtils.loadAnimation(AboutActivity.this, R.anim.slide_down);
                    a.setDuration(500);
                    txtInstructions.setVisibility(View.GONE);

                    if(a != null) {
                        a.reset();

                        if (txtAbout != null) {

                            txtAbout.clearAnimation();
                            txtAbout.startAnimation(a);
                        }
                    }
                }

                txtAbout.setVisibility( txtAbout.isShown() ? View.GONE : View.VISIBLE );

            }
        });

        cardInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!txtInstructions.isShown()){
                    Animation a = AnimationUtils.loadAnimation(AboutActivity.this, R.anim.slide_down);
                    a.setDuration(500);
                    txtAbout.setVisibility(View.GONE);
                    if(a != null) {
                        a.reset();

                        if (txtInstructions != null) {

                            txtInstructions.clearAnimation();
                            txtInstructions.startAnimation(a);
                        }
                    }
                }

                txtInstructions.setVisibility( txtInstructions.isShown() ? View.GONE : View.VISIBLE );

            }
        });
    }

    private void init() {
        cardAbout = findViewById(R.id.card_about);
        cardInstructions = findViewById(R.id.card_instructions);
        txtAbout = findViewById(R.id.txt_about);
        txtInstructions = findViewById(R.id.txt_instructions);
    }
}
