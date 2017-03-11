package com.example.vinicius.paradad.notificacoes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.example.vinicius.paradad.main.MapsFragment;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Vinicius on 11/03/2017.
 */

public class ConfirmacaoProximo extends DialogFragment {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // Use the Builder class for convenient dialog construction


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Sua parada esta proxima")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MapsFragment.vb.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();

    }


}
