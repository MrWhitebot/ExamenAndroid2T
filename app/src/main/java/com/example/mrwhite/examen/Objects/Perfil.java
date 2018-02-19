package com.example.mrwhite.examen.Objects;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by manuel.blanco on 17/02/2018.
 */

public class Perfil {
    public String nombre;
    public double lat,lon;
    public int id,telefono;
    public Marker perfilMarker;
    public Perfil(){}


    public Perfil(int id, String nombre, double lat, double lon, int telefono) {
        this.id = id;
        this.nombre = nombre;
        this.lat = lat;
        this.lon = lon;
        this.telefono = telefono;
    }

    public void setMarker(Marker marker){
        this.perfilMarker = marker;
    }
    public Marker getMarkerEnsalada(){
        return perfilMarker;
    }


}
