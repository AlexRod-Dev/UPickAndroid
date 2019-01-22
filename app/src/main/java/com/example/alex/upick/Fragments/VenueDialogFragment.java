package com.example.alex.upick.Fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alex.upick.R;

public class VenueDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup vg, Bundle b){

        View view = li.inflate(R.layout.dialog_venue,null);



        return view;

    }
}