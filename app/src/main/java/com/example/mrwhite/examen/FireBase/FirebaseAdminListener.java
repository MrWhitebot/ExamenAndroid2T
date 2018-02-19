package com.example.mrwhite.examen.FireBase;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by manuel.blanco on 13/02/2018.
 */

public interface FirebaseAdminListener {
    public void fireBaseAdminbranchDownload(String branch, DataSnapshot dataSnapshot);
}
