package com.example.alex.upick.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.alex.upick.Adapters.ViewPagerAdapter;
import com.example.alex.upick.Fragments.EstablishmentDialogFragment;
import com.example.alex.upick.Models.Establishment;
import com.example.alex.upick.R;

import java.util.ArrayList;

public class EnterActivity extends AppCompatActivity {
    ViewPager viewPager;
    ArrayList<Establishment> establishmentList = new ArrayList<>();
    ViewPagerAdapter adapter;
    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        getSupportActionBar().setTitle("Enter");


        init();
        createList();

        adapter = new ViewPagerAdapter(establishmentList,EnterActivity.this);


        viewPager.setAdapter(adapter);

        float scale = getResources().getDisplayMetrics().density;
        int leftDpAsPixels = (int) (67*scale + 0.5f);
        int rightDpAsPixels = (int) (45*scale + 0.5f);

        viewPager.setPadding(leftDpAsPixels,0,rightDpAsPixels,0);

        viewPager.setClipToPadding (false);
        viewPager.setCurrentItem(Math.round(establishmentList.size()/2));


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int i) {

                //TENTAR MUDAR COR DO PAGER ATUAL
                //TENTAR FAZER SCROLL INFINITO

                //View view = viewPager.getFocusedChild();
                //
                //
                // CardView card = view.findViewById(R.id.cardView);


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    public void clickedCard(int position){
        FragmentManager fm = getSupportFragmentManager();
        EstablishmentDialogFragment edf = new EstablishmentDialogFragment();
        edf.show(fm,"TAG");
    }

    private void createList() {
        establishmentList.add(new Establishment(R.drawable.coreto,"CoretoBar"));
        establishmentList.add(new Establishment(R.drawable.coreto,"Nicole"));
        establishmentList.add(new Establishment(R.drawable.coreto,"Décadas"));
        establishmentList.add(new Establishment(R.drawable.coreto,"ComoAssim"));
        establishmentList.add(new Establishment(R.drawable.coreto,"Bago Doce"));
        establishmentList.add(new Establishment(R.drawable.coreto,"Desigual"));
        establishmentList.add(new Establishment(R.drawable.coreto,"Flamingo"));
    }

    private void init() {
        viewPager = findViewById(R.id.establishment_viewpager);
    }
}
