package com.example.alex.upick.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alex.upick.EnterActivity;
import com.example.alex.upick.Models.Establishment;
import com.example.alex.upick.R;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    private ArrayList<Establishment> establishmentList;
    private LayoutInflater inflater;
    private Context context;


    public ViewPagerAdapter(ArrayList<Establishment> establishmentList, Context context) {
        this.establishmentList = establishmentList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return establishmentList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.card_establishment, container, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EnterActivity)context).clickedCard(position);
            }
        });

        ImageView imageView = view.findViewById(R.id.image);
        TextView name = view.findViewById(R.id.name);

        imageView.setImageResource(establishmentList.get(position).getImage());
        name.setText(establishmentList.get(position).getName());

        container.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
