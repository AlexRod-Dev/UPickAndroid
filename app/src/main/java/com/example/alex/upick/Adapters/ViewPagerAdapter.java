package com.example.alex.upick.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alex.upick.Activities.EnterActivity;
import com.example.alex.upick.Activities.LoginActivity;
import com.example.alex.upick.Models.Venue;
import com.example.alex.upick.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    private ArrayList<Venue> venueList;
    private LayoutInflater inflater;
    private Context context;


    public ViewPagerAdapter(ArrayList<Venue> venueList, Context context) {
        this.venueList = venueList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return venueList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.card_venue, container, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EnterActivity)context).clickedCard(position);
            }
        });

        ImageView imageView = view.findViewById(R.id.image);
        TextView name = view.findViewById(R.id.name);

        //imageView.setImageResource(venueList.get(position).getImage());
        name.setText(venueList.get(position).getName());
        if(!venueList.get(position).getImagepath().equals("")){

           // Picasso.get().load(venueList.get(position).getImagepath()).into(imageView);
            Picasso.get().load(LoginActivity.ipServidor+"/frontend/web/img/venue_logos/"+venueList.get(position).getImagepath()).into(imageView);
        }else{
            Picasso.get().load(LoginActivity.ipServidor+"/frontend/web/img/venue_logos/not_available.png").into(imageView);
        }


        container.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
