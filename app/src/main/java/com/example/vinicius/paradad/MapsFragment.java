package com.example.vinicius.paradad;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.HttpURLConnection;
import java.util.List;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener, LocationListener {

    private GoogleMap mMap;
    private Location currentPosition;
    private MarkerOptions markerCurrentPosition;
    private LocationManager locationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapClickListener(this);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, this);

        currentPosition = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

        double currentLat = currentPosition.getLatitude();
        double currentLng = currentPosition.getLongitude();

        LatLng currentCoordinates = new LatLng(currentLat, currentLng);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentCoordinates, 16);
        mMap.moveCamera(cameraUpdate);

    }

    @Override
    public void onMapClick(LatLng clickedPoint) {

        double latitude = clickedPoint.latitude;
        double longitude = clickedPoint.longitude;

        String latitudeString = String.valueOf(latitude);
        String longitudeString = String.valueOf(longitude);

        String url_parada_json =
                "https://maps.googleapis.com/maps/api/place/radarsearch/json?location="
                        +latitudeString+","+longitudeString+
                        "&radius=50&types=bus_station&key=AIzaSyBNaYNpbJWoW5CmK4jYhUDHGdWfAlwLf2o";
        new DownloadJSON().execute(url_parada_json);

    }

        /*

       String message =  "Latitude: " + latitudeString + " / Longitude: " +
                longitudeString;

        Toast.makeText(getActivity(),  message, Toast.LENGTH_SHORT).show();

        */

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public static void imprimeId(String id){

        String idParada = id;

    }
}
