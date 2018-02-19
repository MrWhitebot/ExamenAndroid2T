package com.example.mrwhite.examen;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mrwhite.examen.FireBase.FirebaseAdminListener;
import com.example.mrwhite.examen.Fragments.MarkerUserInfoFragment;
import com.example.mrwhite.examen.Objects.Perfil;
import com.example.mrwhite.examen.SQLite.DatabaseHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SupportMapFragment mapFragment;
    List<Perfil> perfiles;
    MarkerUserInfoFragment markerUserInfoFragment;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public void myMethod(View v) {
        if (v.getId() == R.id.btnFotos) {
            Log.v("Metodo Linekado", "HAS PULSADO EL BOTON DE FOTOS");
        } else if (v.getId() == R.id.btnMenu) {
            Log.v("Metodo Linekado", "HAS PULSADO EL BOTON DE MENU");
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
class MainActivityEvents implements FirebaseAdminListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    MainActivity generalActivity;
    private GoogleMap mMap;


    public MainActivityEvents(MainActivity generalActivity) {
        this.generalActivity = generalActivity;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        LatLng spain = new LatLng(40.415363, -3.707398);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(spain, 5));
        // Add a marker in Sydney and move the camera


    }


    @Override
    public void fireBaseAdminbranchDownload(String branch, DataSnapshot dataSnapshot) {
        if (branch.equals("PerfilesDemo")) {
            generalActivity.perfiles = new ArrayList<>();
            GenericTypeIndicator<Map<String, Perfil>> indicator = new GenericTypeIndicator<Map<String, Perfil>>() {
            };
            Map<String, Perfil> perfilMap = dataSnapshot.getValue(indicator);

            generalActivity.databaseHandler.getReadableDatabase().execSQL("delete  from  profiles ");
            for (Map.Entry<String, Perfil> entry : perfilMap.entrySet()) {

                Perfil contact = new Perfil(entry.getValue().id, entry.getValue().nombre, entry.getValue().lat, entry.getValue().lon, entry.getValue().telefono);
                generalActivity.databaseHandler.addContact(contact);
            }

            generalActivity.perfiles = generalActivity.databaseHandler.getAllProfiles();
            agregarPinesPerfiles();


        }


    }

    public void agregarPinesPerfiles() {
        LatLng perfilPos = null;
        for (int j = 0; j < generalActivity.perfiles.size(); j++) {
            Perfil perfilTemp = generalActivity.perfiles.get(j);

            perfilPos = new LatLng(perfilTemp.lat, perfilTemp.lon);

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(perfilPos);
            markerOptions.title(perfilTemp.nombre);

            if (mMap != null) {
                Marker marker = mMap.addMarker(markerOptions);
                marker.setTag(perfilTemp);
                perfilTemp.setMarker(marker);
                if (generalActivity.perfiles.get(j).equals(6)) {

                }
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Perfil perfil = (Perfil) marker.getTag();
        generalActivity.markerUserInfoFragment.txtUserName.setText(perfil.nombre.toString());
        generalActivity.markerUserInfoFragment.txtUserTlf.setText(String.valueOf(perfil.telefono));
        return true;
    }
}
