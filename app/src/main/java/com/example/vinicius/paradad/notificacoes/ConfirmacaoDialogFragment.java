package com.example.vinicius.paradad.notificacoes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.example.vinicius.paradad.main.MapsFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Vinicius on 13/02/2017.
 */

public class ConfirmacaoDialogFragment extends DialogFragment{

    private LatLng latLng;

    public void setLatLng(LatLng latLng){
        this.latLng= latLng;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Esta é a sua parada?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        MapsFragment.mMap.clear();

                        MarkerOptions marker= new MarkerOptions();
                        marker.position(latLng);
                        MapsFragment.mMap.addMarker(marker);
                        Toast.makeText(getActivity(),"Parada selecionada",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();


    }



}
