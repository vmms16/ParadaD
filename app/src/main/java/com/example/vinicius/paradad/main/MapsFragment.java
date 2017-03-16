package com.example.vinicius.paradad.main;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;

import com.example.vinicius.paradad.Parada;
import com.example.vinicius.paradad.R;
import com.example.vinicius.paradad.Sessao;
import com.example.vinicius.paradad.json.HttpRequest;
import com.example.vinicius.paradad.notificacoes.ConfirmacaoChegou;
import com.example.vinicius.paradad.notificacoes.ConfirmacaoDialogFragment;
import com.example.vinicius.paradad.notificacoes.ConfirmacaoMuitoProximo;
import com.example.vinicius.paradad.notificacoes.ConfirmacaoProximo;
import com.example.vinicius.paradad.notificacoes.TipoNotificacao;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener, LocationListener {

    public static GoogleMap mMap;
    private static Sessao sessao = Sessao.getInstancia();
    private Location currentPosition;
    private MarkerOptions markerCurrentPosition;
    public static MarkerOptions alarme;
    private LocationManager locationManager;

    public static Vibrator vb;

    public static TipoNotificacao tipo= TipoNotificacao.proximo;


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
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 5000, 0, this);

        currentPosition = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

        double currentLat = currentPosition.getLatitude();
        double currentLng = currentPosition.getLongitude();

        LatLng currentCoordinates = new LatLng(currentLat, currentLng);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentCoordinates, 16);
        mMap.moveCamera(cameraUpdate);

    }

    @Override
    public void onMapClick(LatLng clickedPoint) {


        new DownloadJSON().execute(clickedPoint);


    }


    @Override
    public void onLocationChanged(Location location) {

        if (alarme != null) {

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            LatLng coordinates = new LatLng(latitude, longitude);


            new DownloadJSONDistance().execute(coordinates);
        }

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

    public static void ativaAlarme(){
        mMap.clear();

        MarkerOptions options = new MarkerOptions();

        Parada paradaSelecionada = sessao.getParada();
        LatLng posicao = paradaSelecionada.getLocation();
        options.position(posicao);
        options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_alarm24));

        mMap.addMarker(options);
        alarme = options;
        tipo = TipoNotificacao.proximo;
    }

    public class DownloadJSON extends AsyncTask<LatLng, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(LatLng... params) {

            return HttpRequest.jsonDownload(params[0]);

        }


        @Override
        protected void onPostExecute(JSONObject json) {

            try {
                if (json != null) {

                    Parada parada = HttpRequest.lerJson(json);
                    sessao.setParada(parada);

                    ConfirmacaoDialogFragment dialog = new ConfirmacaoDialogFragment();
                    dialog.show(getFragmentManager(), "tag_1");


                }
                //Toast.makeText(getActivity(), parada, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }


    public class DownloadJSONDistance extends AsyncTask<LatLng, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(LatLng... params) {

            return HttpRequest.obterDistancia(params[0]);

        }

        @Override
        protected void onPostExecute(Integer distancia) {

            Toast.makeText(getActivity(), "Distância: " + distancia, Toast.LENGTH_SHORT).show();

            vb= (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);


            if (distancia <=430 && tipo==TipoNotificacao.chegou) {

                vb.vibrate(5000000);

                ConfirmacaoChegou dialog = new ConfirmacaoChegou();
                dialog.show(getFragmentManager(), "tag_chegou");

                tipo=TipoNotificacao.nenhum;



            }else {
                if (distancia<=470 && tipo== TipoNotificacao.muitoProximo) {

                    vb.vibrate(5000000);

                    ConfirmacaoMuitoProximo dialog = new ConfirmacaoMuitoProximo();
                    dialog.show(getFragmentManager(), "tag_muito_proximo");

                    tipo=TipoNotificacao.chegou;


                }else if(distancia<=500 && tipo==TipoNotificacao.proximo){

                    vb.vibrate(5000000);

                    ConfirmacaoProximo dialog = new ConfirmacaoProximo();
                    dialog.show(getFragmentManager(), "tag_proximo");


                    tipo=TipoNotificacao.muitoProximo;


                }
            }

        }


    }
}