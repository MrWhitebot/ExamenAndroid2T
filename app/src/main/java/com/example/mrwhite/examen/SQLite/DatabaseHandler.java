package com.example.mrwhite.examen.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mrwhite.examen.Objects.Perfil;
import com.google.firebase.crash.FirebaseCrash;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by manuel.blanco on 18/02/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "ProfileManager";

    // Contacts table name
    private static final String TABLE_PERFILES = "profiles";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NOMBRE= "nombre";
    private static final String KEY_TELEFONO = "telefono";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LON = "lon";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PERFILES + "("
                + KEY_ID + " INTEGER PRIMARY KEY ,"
                + KEY_NOMBRE + " TEXT,"
                + KEY_TELEFONO + " INTEGER,"
                + KEY_LAT + " REAL,"
                + KEY_LON + " REAL"+
                ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERFILES);

        // Create tables again
        onCreate(db);
    }


    public void addContact(Perfil perfil){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_ID, perfil.id);
            values.put(KEY_NOMBRE, perfil.nombre);
            values.put(KEY_TELEFONO, perfil.telefono);
            values.put(KEY_LAT, perfil.lat);
            values.put(KEY_LON, perfil.lon);
            Log.v("VALUE PERFIL", values.toString());

            db.insert(TABLE_PERFILES, null, values);
            db.close();
        }catch (SQLException e){
            FirebaseCrash.report(new Exception("Add Contacts To Table SQLite exception: "+e.getMessage()));
        }
    }
    public List getAllProfiles(){
        List<Perfil> perfilMap = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_PERFILES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Perfil perfil = new Perfil();
                perfil.id = (Integer.parseInt(cursor.getString(0)));
                perfil.nombre = cursor.getString(1);
                perfil.telefono = (Integer.parseInt(cursor.getString(2)));
                perfil.lat = (Double.parseDouble(cursor.getString(3)));
                Log.v("LAT USUARIO","NOMBRE" + cursor.getString(1)+ " LAT: "+cursor.getString(3)+" LON: " +cursor.getString(4));
                perfil.lon = (Double.parseDouble(cursor.getString(4)));

                perfilMap.add(perfil);
            } while (cursor.moveToNext());
        }



        return  perfilMap;
    }

}
