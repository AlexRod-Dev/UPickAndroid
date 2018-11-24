package com.example.alex.upick;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class EstablishmentDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup vg, Bundle b){

        View view = li.inflate(R.layout.dialog_establishment,null);

        Button btnEnter = view.findViewById(R.id.btn_enter);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),EstablishmentActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        return view;

    }
}
