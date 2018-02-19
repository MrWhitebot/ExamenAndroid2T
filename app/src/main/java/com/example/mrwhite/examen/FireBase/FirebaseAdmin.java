package com.example.mrwhite.examen.FireBase;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by manuel.blanco on 13/02/2018.
 */

public class FirebaseAdmin {
    //Listener
    public FirebaseAdminListener listener;
    public FirebaseUser user;
    public FirebaseAuth mAuth;
    public FirebaseDatabase database;
    public DatabaseReference myRefPerfiles;


    //Constructor
    public  FirebaseAdmin(){
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRefPerfiles = database.getReference();

    }
    public void downloadAndObserveBranch(final String branch) {
        DatabaseReference refBranch = myRefPerfiles.child(branch);
        refBranch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                listener.fireBaseAdminbranchDownload(branch,dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                listener.fireBaseAdminbranchDownload(branch,null);
            }
        });
    }

    public void setListener(FirebaseAdminListener listener){
        this.listener = listener;
    }


}