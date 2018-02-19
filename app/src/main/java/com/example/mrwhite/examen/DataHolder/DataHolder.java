package com.example.mrwhite.examen.DataHolder;


import com.example.mrwhite.examen.FireBase.FirebaseAdmin;

import org.json.JSONObject;


/**
 * Created by manuel.blanco on 13/02/2018.
 */

public class DataHolder {
    public static  DataHolder instance = new DataHolder();
    public static  JSONObject jsonObjectTwitter;
    public static FirebaseAdmin firebaseAdmin;
    public FirebaseAdmin fireBaseAdmin;
    public DataHolder(){
        fireBaseAdmin = new FirebaseAdmin();
    }

    }

