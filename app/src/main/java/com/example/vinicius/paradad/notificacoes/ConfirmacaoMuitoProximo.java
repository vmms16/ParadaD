package com.example.vinicius.paradad.notificacoes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.vinicius.paradad.R;
import com.example.vinicius.paradad.main.MapsFragment;

/**
 * Created by Vinicius on 12/03/2017.
 */

public class ConfirmacaoMuitoProximo extends DialogFragment {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        LayoutInflater li = getActivity().getLayoutInflater();

        View title= li.inflate(R.layout.activity_title,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setCustomTitle(title);

        View view =li.inflate(R.layout.activity_notificacao, null);
        TextView texto = (TextView)view.findViewById(R.id.textoNotificacao);
        texto.setText("Sua parada está muito próxima!!".toUpperCase());

        builder.setView(view);
        final AlertDialog alerta = builder.create();

        view.findViewById(R.id.confirmacao).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MapsFragment.vb.cancel();
                alerta.dismiss();
            }
        });

        return alerta;

    }



}
