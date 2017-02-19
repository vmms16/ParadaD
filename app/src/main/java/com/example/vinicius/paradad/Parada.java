package com.example.vinicius.paradad;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Vinicius on 19/02/2017.
 */

public class Parada {

    private LatLng location;
    private String id;
    private String place_id;
    private String reference;

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
