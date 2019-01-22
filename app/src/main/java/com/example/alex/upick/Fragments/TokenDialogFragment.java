package com.example.alex.upick.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.upick.Activities.VenueActivity;
import com.example.alex.upick.Models.Venue;
import com.example.alex.upick.R;
import com.google.gson.Gson;


public class TokenDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup vg, Bundle b){

        View view = li.inflate(R.layout.dialog_token,null);


        final Bundle mArgs = getArguments();

        final String jsonVenue = mArgs.getString("venue");

        Venue venue = new Gson().fromJson(jsonVenue, Venue.class);

        String name= venue.getName();
        final String token = venue.getToken();
        final EditText txtToken = view.findViewById(R.id.txt_token);
        TextView lbNome = view.findViewById(R.id.lb_nome);
        Button btnEnter = view.findViewById(R.id.btn_enter);

        lbNome.setText(name);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtToken.getText().toString().equals(token)){
                    Intent i = new Intent(getActivity(),VenueActivity.class);
                    i.putExtra("venue", jsonVenue);
                    startActivity(i);
                    getActivity().finish();
                }else{
                    Toast.makeText(getContext(),R.string.string_wrong_token, Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;

    }
}
