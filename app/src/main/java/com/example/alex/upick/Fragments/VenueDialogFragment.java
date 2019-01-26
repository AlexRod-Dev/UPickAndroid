package com.example.alex.upick.Fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alex.upick.Models.Venue;
import com.example.alex.upick.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class VenueDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup vg, Bundle b){

        View view = li.inflate(R.layout.dialog_venue,null);

        final Bundle mArgs = getArguments();

        final String jsonVenue = mArgs.getString("venue");

        Venue venue = new Gson().fromJson(jsonVenue, Venue.class);

        String description = venue.getDescription();
        String name= venue.getName();
        TextView lbNome = view.findViewById(R.id.lb_nome);
        TextView lbDescription = view.findViewById(R.id.lb_description);
        ImageView imgVenue = view.findViewById(R.id.img_venue);

        if(!venue.getImagepath().equals("")){
            Picasso.get().load(venue.getImagepath()).into(imgVenue);
        }

        lbNome.setText(name);
        lbDescription.setText(description);



        return view;

    }
}