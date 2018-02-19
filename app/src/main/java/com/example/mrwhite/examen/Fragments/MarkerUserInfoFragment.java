package com.example.mrwhite.examen.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mrwhite.examen.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MarkerUserInfoFragment extends Fragment {


    public TextView txtUserName,txtUserTlf;


    public MarkerUserInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_marker_user_info, container, false);
        txtUserName = v.findViewById(R.id.txtUserName);
        txtUserTlf = v.findViewById(R.id.txtUserTlf);


        return v;
    }

}
